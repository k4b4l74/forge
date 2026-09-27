package forge.screens.home.arena;

import forge.StaticData;
import forge.ai.AiController;
import forge.deck.Deck;
import forge.deck.DeckFormat;
import forge.deck.io.DeckSerializer;
import forge.game.Game;
import forge.gamemodes.aisimulation.ArenaConfiguration;
import forge.gamemodes.aisimulation.ArenaFingerprint;
import forge.gamemodes.aisimulation.ArenaStore;
import forge.localinstance.properties.ForgeConstants;
import forge.model.FModel;
import forge.util.FileSection;
import forge.view.ArenaWorkerMain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public final class ArenaFiles {
    private ArenaFiles() { }

    public static Path root() { return Path.of(ForgeConstants.USER_DIR, "ai-simulations"); }

    public static Deck loadDeckSnapshot(final Path file, final String expectedName) throws IOException {
        final Deck deck = DeckSerializer.fromSections(FileSection.parseSections(Files.readAllLines(file)));
        if (deck == null || !expectedName.equals(deck.getName())) {
            throw new IOException("Saved deck changed or could not be read. Refresh the deck list: " + file);
        }
        final String problem = DeckFormat.Constructed.getDeckConformanceProblem(deck);
        if (problem != null) {
            throw new IOException(expectedName + ": " + problem);
        }
        return deck;
    }

    public static String environmentFingerprint() throws IOException {
        final Set<Path> code = new LinkedHashSet<>();
        try {
            for (final Class<?> type : List.of(StaticData.class, Game.class, AiController.class, FModel.class, ArenaWorkerMain.class)) {
                code.add(Path.of(type.getProtectionDomain().getCodeSource().getLocation().toURI()));
            }
        } catch (Exception exception) {
            throw new IOException("Unable to identify the running Forge build", exception);
        }
        final List<Path> inputs = new ArrayList<>(code);
        for (final String directory : List.of("cardsfolder", "tokenscripts", "editions", "lists", "ai")) {
            inputs.add(Path.of(ForgeConstants.RES_DIR, directory));
        }
        return ArenaFingerprint.calculate(inputs);
    }

    public static ArenaStore create(final String name, final List<ArenaConfiguration.Entrant> entrants,
                                     final List<Deck> decks, final int games, final int workers,
                                     final int heap, final int timeout) throws IOException {
        return create(name, entrants, decks, games, workers, heap, timeout, 1);
    }

    public static ArenaStore create(final String name, final List<ArenaConfiguration.Entrant> entrants,
                                     final List<Deck> decks, final int games, final int workers,
                                     final int heap, final int timeout, final int gamesPerMatch) throws IOException {
        return create(name, entrants, decks, games, workers, heap, timeout, gamesPerMatch, 0);
    }

    public static ArenaStore create(final String name, final List<ArenaConfiguration.Entrant> entrants,
                                     final List<Deck> decks, final int games, final int workers,
                                     final int heap, final int timeout, final int gamesPerMatch,
                                     final int comparisonProfiles) throws IOException {
        final String fingerprint = environmentFingerprint();
        final String id = UUID.randomUUID().toString();
        final Path directory = root().resolve(id);
        final Path inputs = directory.resolve("inputs");
        Files.createDirectories(inputs.resolve("decks"));
        Files.createDirectories(inputs.resolve("profiles"));
        Files.createDirectories(inputs.resolve("preferences"));
        for (int index = 0; index < decks.size(); index++) {
            final Path destination = inputs.resolve("decks/" + index + ".dck");
            DeckSerializer.writeDeck(decks.get(index), destination.toFile());
            if (!Files.isRegularFile(destination) || Files.size(destination) == 0) {
                throw new IOException("Failed to snapshot " + entrants.get(index).name());
            }
            final String profile = entrants.get(index).profile() + ".ai";
            Files.copy(Path.of(ForgeConstants.AI_PROFILE_DIR, profile), inputs.resolve("profiles").resolve(profile),
                    StandardCopyOption.REPLACE_EXISTING);
        }
        final Path preferences = Path.of(ForgeConstants.MAIN_PREFS_FILE);
        if (Files.isRegularFile(preferences)) {
            Files.copy(preferences, inputs.resolve("preferences/forge.preferences"));
        }
        copyTree(Path.of(ForgeConstants.USER_CUSTOM_DIR), inputs.resolve("custom"));
        final ArenaConfiguration configuration = new ArenaConfiguration(ArenaConfiguration.VERSION, id, name,
                System.currentTimeMillis(), ThreadLocalRandom.current().nextLong(), fingerprint,
                ArenaFingerprint.calculate(List.of(inputs)), games, workers, heap, timeout, entrants, gamesPerMatch, comparisonProfiles);
        return ArenaStore.create(directory, configuration);
    }

    public static ArenaStore copyRun(final ArenaStore original) throws IOException {
        final String fingerprint = environmentFingerprint();
        verifySnapshots(original);
        final String id = UUID.randomUUID().toString();
        final Path destination = root().resolve(id);
        copyTree(original.directory().resolve("inputs"), destination.resolve("inputs"));
        final ArenaConfiguration previous = original.configuration();
        return ArenaStore.create(destination, new ArenaConfiguration(ArenaConfiguration.VERSION, id, previous.name(),
                System.currentTimeMillis(), ThreadLocalRandom.current().nextLong(), fingerprint, previous.snapshotFingerprint(),
                previous.gamesPerPair(), previous.workers(), previous.heapMegabytes(), previous.timeoutSeconds(),
                previous.entrants(), previous.gamesPerMatch(), previous.comparisonProfiles()));
    }

    public static void verifySnapshots(final ArenaStore store) throws IOException {
        final String current = ArenaFingerprint.calculate(List.of(store.directory().resolve("inputs")));
        if (!current.equals(store.configuration().snapshotFingerprint())) {
            throw new IOException("Saved deck, AI or preference snapshots have changed; this run cannot be resumed");
        }
    }

    public static void verifyResume(final ArenaStore store) throws IOException {
        verifySnapshots(store);
        if (!environmentFingerprint().equals(store.configuration().fingerprint())) {
            throw new IOException("Forge code or rules changed. Start a new run from this setup instead of resuming");
        }
    }

    public static void copyTree(final Path source, final Path destination) throws IOException {
        if (!Files.exists(source)) { return; }
        try (Stream<Path> paths = Files.walk(source)) {
            for (final Path path : paths.toList()) {
                if (Files.isSymbolicLink(path)) {
                    throw new IOException("Symbolic links are not supported in simulation snapshots: " + path);
                }
                final Path target = destination.resolve(source.relativize(path));
                if (Files.isDirectory(path)) {
                    Files.createDirectories(target);
                } else {
                    Files.createDirectories(target.getParent());
                    Files.copy(path, target, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }
}
