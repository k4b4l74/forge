package forge.view;

import forge.GuiDesktop;
import forge.ai.LearnedObservation;
import forge.gamemodes.aisimulation.ArenaConfiguration;
import forge.gamemodes.aisimulation.ArenaExport;
import forge.gamemodes.aisimulation.ArenaFingerprint;
import forge.gamemodes.aisimulation.ArenaRunner;
import forge.gamemodes.aisimulation.ArenaStandings;
import forge.gamemodes.aisimulation.ArenaStore;
import forge.gui.GuiBase;
import forge.screens.home.arena.ArenaFiles;
import forge.screens.home.arena.ArenaProcessWorker;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class ArenaLearningMain {
    private ArenaLearningMain() { }

    public static void main(final String[] arguments) throws Exception {
        if (arguments.length != 6 && arguments.length != 8) {
            throw new IllegalArgumentException("Usage: ArenaLearningMain SOURCE_RUN ASSETS NEW_OUTPUT collect|evaluate|evaluate-bo3 MATCHES_PER_OPPONENT SEED [MODEL_DIRECTORY ENDPOINT]");
        }
        final boolean collect = "collect".equals(arguments[3]);
        final int gamesPerMatch = "evaluate-bo3".equals(arguments[3]) ? 3 : 1;
        if ((!collect && !List.of("evaluate", "evaluate-bo3").contains(arguments[3])) || arguments.length != (collect ? 6 : 8)) {
            throw new IllegalArgumentException("Collect takes six arguments; evaluate also requires a model directory and endpoint");
        }
        final int matches = Integer.parseInt(arguments[4]);
        final long seed = Long.parseLong(arguments[5]);
        if (matches < 1 || matches > 10000) { throw new IllegalArgumentException("Use 1-10000 matches per opponent"); }
        final int workers = Integer.parseInt(System.getProperty("forge.learning.workers", "2"));
        if (workers < 1 || workers > 8) { throw new IllegalArgumentException("Use 1-8 learning workers"); }
        final Path source = Path.of(arguments[0]).toRealPath();
        final Path assets = Path.of(arguments[1]).toRealPath();
        final Path requested = Path.of(arguments[2]).toAbsolutePath().normalize();
        final Path directory = requested.getParent().toRealPath().resolve(requested.getFileName());
        if (directory.startsWith(source)) { throw new IllegalArgumentException("Output must be outside the source run"); }
        try (ArenaStore original = new ArenaStore(source)) {
            ArenaFiles.verifySnapshots(original);
            final ArenaConfiguration previous = original.configuration();
            if (!previous.isComparison()) { throw new IllegalArgumentException("Use an existing test-deck comparison run"); }
            GuiBase.setInterface(new GuiDesktop() {
                @Override
                public String getAssetsDir() { return assets + File.separator; }
            });
            final String fingerprint = ArenaFiles.environmentFingerprint();
            Files.createDirectory(directory);
            final Path inputs = directory.resolve("inputs");
            Files.createDirectories(inputs.resolve("decks"));
            Files.createDirectories(inputs.resolve("profiles"));
            ArenaFiles.copyTree(source.resolve("inputs/preferences"), inputs.resolve("preferences"));
            ArenaFiles.copyTree(source.resolve("inputs/custom"), inputs.resolve("custom"));
            final List<ArenaConfiguration.Entrant> entrants = new ArrayList<>();
            final List<String> profiles = collect ? List.of("Default", "OS Reanimator v2")
                    : List.of("Default", "OS Reanimator v2", ArenaLearningSession.PROFILE);
            final ArenaConfiguration.Entrant deck = previous.entrants().get(0);
            for (final String profile : profiles) {
                Files.copy(source.resolve("inputs/decks/0.dck"), inputs.resolve("decks/" + entrants.size() + ".dck"));
                entrants.add(new ArenaConfiguration.Entrant(deck.name(), deck.source(), profile));
                Files.copy(assets.resolve("res/ai/" + profile + ".ai"), inputs.resolve("profiles/" + profile + ".ai"));
            }
            for (int index = previous.comparisonProfiles(); index < previous.entrants().size(); index++) {
                final ArenaConfiguration.Entrant opponent = previous.entrants().get(index);
                Files.copy(source.resolve("inputs/decks/" + index + ".dck"), inputs.resolve("decks/" + entrants.size() + ".dck"));
                entrants.add(opponent);
                final String profile = opponent.profile() + ".ai";
                Files.copy(source.resolve("inputs/profiles").resolve(profile), inputs.resolve("profiles").resolve(profile),
                        StandardCopyOption.REPLACE_EXISTING);
            }
            if (collect) {
                ArenaStore.writeJson(inputs.resolve("learning.json"), new ArenaLearningSession.Settings(LearnedObservation.SCHEMA,
                        "collect", List.of("OS Reanimator v2"), "", "", 5000));
            } else {
                ArenaLearningSession.snapshotModel(inputs, Path.of(arguments[6]).toRealPath(), arguments[7]);
            }
            final ArenaConfiguration configuration = new ArenaConfiguration(ArenaConfiguration.VERSION, UUID.randomUUID().toString(),
                    "MageZero " + arguments[3] + " BO" + gamesPerMatch, System.currentTimeMillis(), seed, fingerprint,
                    ArenaFingerprint.calculate(List.of(inputs)), matches, workers, 2048, 500, entrants, gamesPerMatch, profiles.size());
            try (ArenaStore store = ArenaStore.create(directory, configuration);
                 ArenaRunner runner = new ArenaRunner(store, number -> new ArenaProcessWorker(store, number, assets))) {
                runner.start();
                int reported = -1;
                while (runner.isRunning()) {
                    Thread.sleep(1000);
                    final int completed = runner.progress().finished();
                    if (completed / 50 != reported) {
                        reported = completed / 50;
                        System.out.println(completed + "/" + configuration.totalGames());
                    }
                }
                if (runner.progress().state() != ArenaStore.State.COMPLETED
                        || store.results().stream().anyMatch(result -> !result.isValidGame())) {
                    throw new IllegalStateException("Incomplete experiment: " + runner.progress());
                }
                final ArenaStandings standings = new ArenaStandings(configuration, store.results());
                for (int index = 0; index < profiles.size(); index++) {
                    final ArenaStandings.Score score = standings.total(index);
                    System.out.println(profiles.get(index) + ": " + score.wins + "/" + score.validGames());
                }
                System.out.println("Export: " + ArenaExport.export(store));
            }
        }
    }
}
