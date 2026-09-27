package forge.view;

import forge.ai.AiDecisionTrace;
import forge.ai.LearnedGameplay;
import forge.ai.LearnedObservation;
import forge.ai.LobbyPlayerAi;
import forge.game.Game;
import forge.game.card.Card;
import forge.game.player.Player;
import forge.gamemodes.aisimulation.ArenaConfiguration;
import forge.gamemodes.aisimulation.ArenaStore;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ArenaLearningSession implements LearnedGameplay.Policy, AutoCloseable {
    public static final String PROFILE = "OS Reanimator Learned";
    public record Settings(int schema, String mode, List<String> profiles, String endpoint,
                           String modelSha256, int timeoutMillis) {
        public Settings {
            if (schema != LearnedObservation.SCHEMA || !List.of("collect", "infer").contains(mode)
                    || profiles == null || profiles.isEmpty() || profiles.stream().anyMatch(profile -> profile == null || profile.isBlank())
                    || timeoutMillis < 1 || timeoutMillis > 10000) { throw new IllegalArgumentException("Invalid learning settings"); }
            profiles = List.copyOf(profiles);
            if ("infer".equals(mode)) {
                final URI address = URI.create(endpoint);
                if (!"http".equals(address.getScheme()) || address.getHost() == null
                        || !List.of("localhost", "127.0.0.1", "[::1]", "magezero").contains(address.getHost())
                        || address.getUserInfo() != null || address.getQuery() != null || address.getFragment() != null
                        || !"/choose".equals(address.getPath()) || modelSha256 == null || !modelSha256.matches("[a-f0-9]{64}")) {
                    throw new IllegalArgumentException("Use a local MageZero endpoint and a pinned checkpoint SHA-256");
                }
            }
        }
    }

    private record Reply(int schema, String modelSha256, Integer action, Double value) { }
    private record Manifest(int schema, int buckets, int actions, String modelSha256) { }

    public static Settings snapshotModel(final Path inputs, final Path model, final String endpoint) throws Exception {
        if (Files.size(model.resolve("manifest.json")) > 65536) { throw new IOException("Oversized model manifest"); }
        final Manifest manifest = ArenaStore.JSON.fromJson(Files.readString(model.resolve("manifest.json")), Manifest.class);
        if (manifest == null || manifest.schema() != LearnedObservation.SCHEMA || manifest.buckets() != LearnedObservation.BUCKETS
                || manifest.actions() != LearnedGameplay.MAX_ACTIONS || Files.size(model.resolve("checkpoint.pt")) > 256L * 1024 * 1024) {
            throw new IOException("Invalid MageZero checkpoint bundle");
        }
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        try (var input = new DigestInputStream(Files.newInputStream(model.resolve("checkpoint.pt")), digest)) {
            input.transferTo(java.io.OutputStream.nullOutputStream());
        }
        if (!HexFormat.of().formatHex(digest.digest()).equals(manifest.modelSha256())) {
            throw new IOException("MageZero checkpoint has changed");
        }
        final Settings settings = new Settings(LearnedObservation.SCHEMA, "infer", List.of(PROFILE), endpoint, manifest.modelSha256(), 5000);
        Files.createDirectories(inputs.resolve("model"));
        for (final String file : List.of("manifest.json", "checkpoint.pt")) {
            Files.copy(model.resolve(file), inputs.resolve("model").resolve(file), StandardCopyOption.REPLACE_EXISTING);
        }
        ArenaStore.writeJson(inputs.resolve("learning.json"), settings);
        return settings;
    }

    private final Settings settings;
    private final BufferedWriter writer;
    private final AutoCloseable registration;
    private final Map<String, Integer> skips = new LinkedHashMap<>();
    private int decisions;
    private int modelDecisions;
    private int fallbacks;
    private int failures;
    private int remainingBytes = 16 * 1024 * 1024;
    private boolean truncated;
    private boolean closed;

    public static Settings readSettings(final Path directory, final ArenaConfiguration configuration) throws IOException {
        final Path file = directory.resolve("inputs/learning.json");
        if (!Files.exists(file)) {
            if (configuration.entrants().stream().anyMatch(entrant -> PROFILE.equals(entrant.profile()))) {
                throw new IOException("The learned profile requires a pinned model and inputs/learning.json; see docs/MageZero-Forge.md");
            }
            return null;
        }
        if (Files.size(file) > 65536) { throw new IOException("Oversized learning settings"); }
        final Settings settings = ArenaStore.JSON.fromJson(Files.readString(file), Settings.class);
        if (settings == null) { throw new IOException("Empty learning settings"); }
        if (configuration.entrants().stream().anyMatch(entrant -> PROFILE.equals(entrant.profile()))
                && (!"infer".equals(settings.mode()) || !settings.profiles().contains(PROFILE))) {
            throw new IOException("The learned profile must use inference, not an unlabelled heuristic baseline");
        }
        return settings;
    }

    public ArenaLearningSession(final Path directory, final Settings settings, final Game game,
                                final int matchId, final int gameNumber, final long seed) throws Exception {
        this.settings = settings;
        final Path output = directory.resolve("learning");
        Files.createDirectories(output);
        writer = Files.newBufferedWriter(Files.createTempFile(output, "match-" + matchId + "-game-" + gameNumber + "-", ".jsonl"));
        AutoCloseable installed = null;
        try {
            write(Map.of("kind", "header", "schema", LearnedObservation.SCHEMA, "buckets", LearnedObservation.BUCKETS,
                    "matchId", matchId, "game", gameNumber, "seed", seed, "settings", settings));
            installed = LearnedGameplay.install(game, this);
        } catch (Exception exception) {
            writer.close();
            throw exception;
        }
        registration = installed;
    }

    private boolean eligible(final Player player) {
        return player.getLobbyPlayer() instanceof LobbyPlayerAi ai && settings.profiles().contains(ai.getAiProfile());
    }

    @Override
    public synchronized void skipped(final Player player, final String reason) {
        if (!closed && eligible(player)) { skips.merge(reason, 1, Integer::sum); }
    }

    @Override
    public synchronized int choose(final Player player, final String kind, final List<List<Card>> options, final int teacher) {
        if (closed || !eligible(player)) { return teacher; }
        final List<Integer> features = LearnedObservation.encode(player, kind, options);
        if (features.size() > LearnedObservation.MAX_FEATURES) {
            skipped(player, "feature-limit");
            return teacher;
        }
        decisions++;
        int selected = teacher;
        String reason = "teacher";
        if ("infer".equals(settings.mode())) {
            try {
                if (failures >= 3) { throw new IOException("circuit-open"); }
                selected = predict(features, options.size());
                modelDecisions++;
                reason = "model";
            } catch (IOException | RuntimeException exception) {
                fallbacks++;
                failures++;
                reason = exception.getClass().getSimpleName() + ": " + exception.getMessage();
            }
        }
        write(Map.of("kind", "decision", "player", player.getName(), "decision", kind, "features", features,
                "actionCount", options.size(), "teacher", teacher, "selected", selected, "reason", reason,
                "options", options.stream().map(AiDecisionTrace::cards).toList()));
        return selected;
    }

    private int predict(final List<Integer> features, final int actions) throws IOException {
        final HttpURLConnection connection = (HttpURLConnection) URI.create(settings.endpoint()).toURL().openConnection();
        connection.setConnectTimeout(Math.min(1000, settings.timeoutMillis()));
        connection.setReadTimeout(settings.timeoutMillis());
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        final byte[] request = ArenaStore.JSON.toJson(Map.of("schema", LearnedObservation.SCHEMA,
                "features", features, "actionCount", actions)).getBytes(StandardCharsets.UTF_8);
        connection.setFixedLengthStreamingMode(request.length);
        try {
            try (var output = connection.getOutputStream()) { output.write(request); }
            if (connection.getResponseCode() != 200) { throw new IOException("Model HTTP status " + connection.getResponseCode()); }
            final byte[] bytes;
            try (var input = connection.getInputStream()) { bytes = input.readNBytes(8193); }
            if (bytes.length > 8192) { throw new IOException("Oversized model response"); }
            final Reply reply = ArenaStore.JSON.fromJson(new String(bytes, StandardCharsets.UTF_8), Reply.class);
            if (reply == null || reply.schema() != LearnedObservation.SCHEMA || !settings.modelSha256().equals(reply.modelSha256())
                    || reply.action() == null || reply.action() < 0 || reply.action() >= actions
                    || reply.value() == null || !Double.isFinite(reply.value()) || Math.abs(reply.value()) > 1.001) {
                throw new IOException("Invalid model identity, action or value");
            }
            return reply.action();
        } finally {
            connection.disconnect();
        }
    }

    public synchronized void finish(final Game game) {
        final Map<String, Integer> rewards = new LinkedHashMap<>();
        for (final Player player : game.getRegisteredPlayers()) {
            rewards.put(player.getName(), game.getOutcome().isDraw() ? 0
                    : game.getOutcome().isWinner(player.getRegisteredPlayer()) ? 1 : -1);
        }
        write(Map.of("kind", "outcome", "rewards", rewards, "decisions", decisions,
                "modelDecisions", modelDecisions, "fallbacks", fallbacks, "skips", skips, "truncated", truncated));
    }

    private void write(final Map<String, ?> record) {
        final String json = ArenaStore.JSON.toJson(record);
        final int bytes = json.getBytes(StandardCharsets.UTF_8).length + 1;
        if ("decision".equals(record.get("kind")) && bytes > remainingBytes) { truncated = true; return; }
        try {
            writer.write(json);
            writer.write('\n');
            remainingBytes -= bytes;
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    @Override
    public synchronized void close() throws Exception {
        closed = true;
        try { registration.close(); } finally { writer.close(); }
    }
}
