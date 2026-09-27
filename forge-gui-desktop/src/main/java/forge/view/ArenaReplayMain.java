package forge.view;

import forge.GuiDesktop;
import forge.gamemodes.aisimulation.ArenaConfiguration;
import forge.gamemodes.aisimulation.ArenaExport;
import forge.gamemodes.aisimulation.ArenaResult;
import forge.gamemodes.aisimulation.ArenaSchedule;
import forge.gamemodes.aisimulation.ArenaStore;
import forge.gui.GuiBase;
import forge.screens.home.arena.ArenaFiles;
import forge.screens.home.arena.ArenaProcessWorker;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public final class ArenaReplayMain {
    private ArenaReplayMain() { }

    public static void main(final String[] arguments) throws Exception {
        if (arguments.length != 4) {
            throw new IllegalArgumentException("Usage: ArenaReplayMain SOURCE_RUN ASSETS NEW_OUTPUT_DIRECTORY MATCH_IDS_COMMA_SEPARATED");
        }
        final Path source = Path.of(arguments[0]).toRealPath();
        final Path assets = Path.of(arguments[1]).toRealPath();
        final Path requested = Path.of(arguments[2]).toAbsolutePath().normalize();
        final Path destination = requested.getParent().toRealPath().resolve(requested.getFileName());
        if (destination.startsWith(source)) { throw new IllegalArgumentException("Replay output must be outside the source run"); }
        final ArenaStore original = new ArenaStore(source);
        ArenaFiles.verifySnapshots(original);
        final ArenaConfiguration previous = original.configuration();
        final Set<Integer> selected = new HashSet<>();
        if (arguments[3].length() > 65536) { throw new IllegalArgumentException("Oversized trace selection"); }
        for (final String identifier : arguments[3].split(",", -1)) {
            final int match = Integer.parseInt(identifier.trim());
            ArenaSchedule.task(previous, match);
            selected.add(match);
        }
        if (selected.size() > 1000) { throw new IllegalArgumentException("Select at most 1000 traced matches"); }
        GuiBase.setInterface(new GuiDesktop() {
            @Override
            public String getAssetsDir() { return assets + File.separator; }
        });
        final String fingerprint = ArenaFiles.environmentFingerprint();
        Files.createDirectory(destination);
        ArenaFiles.copyTree(source.resolve("inputs"), destination.resolve("inputs"));
        Files.writeString(destination.resolve("trace-matches.txt"), arguments[3]);
        final ArenaConfiguration configuration = new ArenaConfiguration(ArenaConfiguration.VERSION, UUID.randomUUID().toString(),
                "Diagnostic replay: " + previous.name(), System.currentTimeMillis(), previous.seed(), fingerprint,
                previous.snapshotFingerprint(), previous.gamesPerPair(), 1, previous.heapMegabytes(), previous.timeoutSeconds(),
                previous.entrants(), previous.gamesPerMatch(), previous.comparisonProfiles());
        ArenaStore.writeJson(destination.resolve("replay.json"), Map.of("sourceRun", previous.id(),
                "sourceFingerprint", previous.fingerprint(), "currentFingerprint", fingerprint,
                "matches", selected.stream().sorted().toList(), "note", "Diagnostic rerun using current code; not a historical replay guarantee"));
        System.out.println("Replaying selected matches with CURRENT code in " + destination + "; original run remains untouched.");
        final long started = System.nanoTime();
        try (ArenaStore store = ArenaStore.create(destination, configuration)) {
            store.claim();
            try (ArenaProcessWorker worker = new ArenaProcessWorker(store, 0, assets)) {
                for (final int identifier : selected.stream().sorted().toList()) {
                    final ArenaResult result = worker.play(ArenaSchedule.task(configuration, identifier));
                    store.append(result);
                    System.out.println(identifier + ": " + result.detail());
                    if (!result.isValidGame()) { throw new IllegalStateException(result.detail()); }
                }
            } catch (Exception exception) {
                store.update(ArenaStore.State.FAILED, TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - started), exception.toString());
                throw exception;
            }
            store.update(selected.size() == configuration.totalGames() ? ArenaStore.State.COMPLETED : ArenaStore.State.PAUSED,
                    TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - started), "Selected diagnostic matches finished; not a full benchmark");
            System.out.println("Export: " + ArenaExport.export(store));
        }
    }
}
