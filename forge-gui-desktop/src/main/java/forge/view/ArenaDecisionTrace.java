package forge.view;

import forge.ai.AiDecisionTrace;
import forge.game.Game;
import forge.game.GameLogEntry;
import forge.gamemodes.aisimulation.ArenaConfiguration;
import forge.gamemodes.aisimulation.ArenaSchedule;
import forge.gamemodes.aisimulation.ArenaStore;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class ArenaDecisionTrace implements AutoCloseable {
    private final BufferedWriter output;
    private final AiDecisionTrace scope;
    private int remaining = 2 * 1024 * 1024;
    private int gameNumber;
    private boolean truncated;

    public ArenaDecisionTrace(final Path directory, final ArenaConfiguration configuration,
                              final ArenaSchedule.Task task) throws IOException {
        final Path traces = directory.resolve("traces");
        Files.createDirectories(traces);
        output = Files.newBufferedWriter(Files.createTempFile(traces, "match-" + task.gameId() + "-", ".jsonl"), StandardCharsets.UTF_8);
        try {
            write(Map.of("kind", "match", "task", task, "configuration", configuration));
        } catch (RuntimeException exception) {
            output.close();
            throw exception;
        }
        scope = AiDecisionTrace.open(entry -> write(Map.of("kind", "decision", "game", gameNumber, "entry", entry)));
    }

    public static Set<Integer> selectedMatches(final Path directory, final ArenaConfiguration configuration) throws IOException {
        final Path path = directory.resolve("trace-matches.txt");
        if (!Files.exists(path)) { return Set.of(); }
        if (Files.size(path) > 65536) { throw new IOException("Oversized trace selection"); }
        final Set<Integer> selected = new HashSet<>();
        for (final String token : Files.readString(path).trim().split("[\\s,]+")) {
            if (token.isEmpty()) { continue; }
            try {
                final int identifier = Integer.parseInt(token);
                ArenaSchedule.task(configuration, identifier);
                selected.add(identifier);
            } catch (IllegalArgumentException exception) {
                throw new IOException("Invalid trace match ID: " + token, exception);
            }
            if (selected.size() > 1000) { throw new IOException("Select at most 1000 traced matches"); }
        }
        return Set.copyOf(selected);
    }

    public void beginGame(final int number) {
        gameNumber = number;
        write(Map.of("kind", "game-start", "game", gameNumber));
    }

    public void endGame(final Game game) {
        for (final GameLogEntry entry : game.getGameLog().getAllEntries()) {
            write(Map.of("kind", "game-log", "game", gameNumber, "type", entry.type().name(), "message", entry.message()));
        }
    }

    public void write(final Object value) {
        if (truncated) { return; }
        final String json = ArenaStore.JSON.toJson(value);
        final int bytes = json.getBytes(StandardCharsets.UTF_8).length + 1;
        try {
            if (bytes > remaining) {
                output.write("{\"kind\":\"truncated\",\"limitBytes\":2097152}\n");
                truncated = true;
            } else {
                output.write(json);
                output.write('\n');
                remaining -= bytes;
            }
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    @Override
    public void close() throws IOException {
        scope.close();
        output.close();
    }
}
