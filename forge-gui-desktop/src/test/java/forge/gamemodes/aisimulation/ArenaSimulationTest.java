package forge.gamemodes.aisimulation;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.SkipException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.expectThrows;

public class ArenaSimulationTest {
    private Path directory;

    @BeforeMethod
    public void createDirectory() throws IOException { directory = Files.createTempDirectory("arena-test-"); }

    @AfterMethod
    public void removeDirectory() throws IOException {
        try (Stream<Path> paths = Files.walk(directory)) {
            for (final Path path : paths.sorted(Comparator.reverseOrder()).toList()) { Files.deleteIfExists(path); }
        }
    }

    private ArenaConfiguration configuration(final int entrants, final int games, final int workers) {
        final List<ArenaConfiguration.Entrant> decks = new ArrayList<>();
        for (int entrant = 0; entrant < entrants; entrant++) {
            decks.add(new ArenaConfiguration.Entrant("Deck " + entrant, "folder/" + entrant, "Default"));
        }
        return new ArenaConfiguration(1, "test-run", "Test", 0, 1234, "build", "inputs", games, workers, 512, 2, decks);
    }

    @Test(timeOut = 10000)
    public void retriesTransientWindowsAccessDenialsWithoutLosingThePreviousJson() throws Exception {
        if (!System.getProperty("os.name").startsWith("Windows")) { throw new SkipException("Windows file replacement semantics"); }
        final Path destination = directory.resolve("status.json");
        Files.writeString(destination, "old");
        Files.setAttribute(destination, "dos:readonly", true);
        final var executor = Executors.newSingleThreadScheduledExecutor();
        try {
            final Path probe = directory.resolve("probe.tmp");
            Files.writeString(probe, "probe");
            expectThrows(AccessDeniedException.class, () -> Files.move(probe, destination,
                    StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING));
            assertEquals(Files.readString(destination), "old");
            final var unlock = executor.schedule(() -> Files.setAttribute(destination, "dos:readonly", false), 150, TimeUnit.MILLISECONDS);
            ArenaStore.writeJson(destination, Map.of("state", "new"));
            unlock.get(5, TimeUnit.SECONDS);
            assertEquals(Files.readString(destination), "{\"state\":\"new\"}");
        } finally {
            executor.shutdownNow();
            Files.setAttribute(destination, "dos:readonly", false);
        }
    }

    private ArenaConfiguration comparison(final int matches, final int gamesPerMatch) {
        return new ArenaConfiguration(ArenaConfiguration.VERSION, "comparison", "Compare KabaL", 0, 1234,
                "build", "inputs", matches, 2, 512, 120, List.of(
                new ArenaConfiguration.Entrant("KabaL", "folder/KabaL", "Default"),
                new ArenaConfiguration.Entrant("KabaL", "folder/KabaL", "OS Reanimator"),
                new ArenaConfiguration.Entrant("Red", "folder/Red", "Reckless"),
                new ArenaConfiguration.Entrant("Control", "folder/Control", "Cautious")), gamesPerMatch, 2);
    }

    @Test
    public void comparesOnlyTestProfilesAgainstOpponentsWithMatchedSeedsAndStarts() {
        for (final int matches : List.of(5, 6)) {
            final ArenaConfiguration configuration = comparison(matches, 3);
            final Map<String, Integer> counts = new HashMap<>();
            final Map<String, Integer> starts = new HashMap<>();
            assertEquals(configuration.totalGames(), 4 * matches);
            for (int match = 0; match < configuration.totalGames(); match += 2) {
                final ArenaSchedule.Task baseline = ArenaSchedule.task(configuration, match);
                final ArenaSchedule.Task candidate = ArenaSchedule.task(configuration, match + 1);
                assertEquals(baseline.left(), 0);
                assertEquals(candidate.left(), 1);
                assertEquals(baseline.right(), candidate.right());
                assertEquals(baseline.seed(), candidate.seed());
                assertEquals(baseline.firstToChoose() == baseline.left(), candidate.firstToChoose() == candidate.left());
                for (final ArenaSchedule.Task task : List.of(baseline, candidate)) {
                    assertTrue(task.right() >= 2);
                    assertTrue(configuration.hasPairing(task.left(), task.right()));
                    final String pair = task.left() + ":" + task.right();
                    counts.merge(pair, 1, Integer::sum);
                    if (task.firstToChoose() == task.left()) { starts.merge(pair, 1, Integer::sum); }
                }
            }
            assertEquals(counts.size(), 4);
            for (final String pair : counts.keySet()) {
                assertEquals(counts.get(pair).intValue(), matches);
                assertTrue(Math.abs(2 * starts.get(pair) - matches) <= 1);
            }
            assertFalse(configuration.hasPairing(0, 1));
            assertFalse(configuration.hasPairing(2, 3));
        }
    }

    @Test
    public void validatesComparisonDeckIdentityAndPreservesOlderConfigurations() {
        final ArenaConfiguration valid = comparison(10, 1);
        final List<ArenaConfiguration.Entrant> entrants = new ArrayList<>(valid.entrants());
        entrants.set(1, entrants.get(0));
        expectThrows(IllegalArgumentException.class, () -> new ArenaConfiguration(ArenaConfiguration.VERSION,
                "invalid", "Invalid", 0, 1, "build", "inputs", 1, 1, 512, 10, entrants, 1, 2));
        entrants.set(1, new ArenaConfiguration.Entrant("Other", "folder/Other", "OS Reanimator"));
        expectThrows(IllegalArgumentException.class, () -> new ArenaConfiguration(ArenaConfiguration.VERSION,
                "invalid", "Invalid", 0, 1, "build", "inputs", 1, 1, 512, 10, entrants, 1, 2));
        expectThrows(IllegalArgumentException.class, () -> ArenaConfiguration.gameCount(3, 10, 1));
        expectThrows(IllegalArgumentException.class, () -> ArenaConfiguration.gameCount(2, 10, 2));
        expectThrows(IllegalArgumentException.class, () -> ArenaConfiguration.gameCount(100000, 100000, 50000));
        for (final int version : List.of(1, 2)) {
            final ArenaConfiguration old = new ArenaConfiguration(version, "legacy", "Legacy", 0, 1,
                    "build", "inputs", 4, 1, 512, 10, configuration(2, 4, 1).entrants(), version == 1 ? 1 : 3);
            final String json = ArenaStore.JSON.toJson(old).replace(",\"comparisonProfiles\":0", "");
            final ArenaConfiguration restored = ArenaStore.JSON.fromJson(json, ArenaConfiguration.class);
            assertFalse(restored.isComparison());
            assertEquals(restored.gamesPerMatch(), old.gamesPerMatch());
            assertEquals(ArenaSchedule.task(restored, 3), ArenaSchedule.task(old, 3));
        }
    }

    @Test
    public void persistsAndExportsSeparateAiResultsWithoutRankingTheOpponentPool() throws Exception {
        final ArenaConfiguration configuration = comparison(2, 1);
        try (ArenaStore store = ArenaStore.create(directory, configuration)) {
            store.claim();
            for (int match = 0; match < configuration.totalGames(); match++) {
                final ArenaSchedule.Task task = ArenaSchedule.task(configuration, match);
                if (match == 0) {
                    store.append(new ArenaResult(match, ArenaResult.Outcome.ERROR, -1, -1, 0, 0, "failed"));
                } else {
                    store.append(new ArenaResult(match, ArenaResult.Outcome.WIN, task.left() == 1 ? task.left() : task.right(),
                            task.firstToChoose(), 5, 10, "test"));
                }
            }
            final ArenaStandings standings = new ArenaStandings(configuration, store.results());
            assertEquals(standings.ranking(), List.of(1, 0));
            assertEquals(standings.total(1).wins, 4);
            assertEquals(standings.total(0).losses, 3);
            assertEquals(standings.total(0).errors, 1);
            assertEquals(standings.pairing(0, 1).validGames(), 0);
            final Path exported = ArenaExport.export(store);
            assertEquals(Files.readAllLines(exported.resolve("standings.csv")).size(), 3);
            assertEquals(Files.readAllLines(exported.resolve("matchups.csv")).size(), 9);
            for (final String file : List.of("matchups.csv", "matches.csv", "games.csv")) {
                final String content = Files.readString(exported.resolve(file));
                assertTrue(content.contains("folder/KabaL [Default]"), file);
                assertTrue(content.contains("folder/KabaL [OS Reanimator]"), file);
            }
        }
        try (ArenaStore restored = new ArenaStore(directory)) {
            assertEquals(restored.configuration(), configuration);
            assertEquals(restored.completed(), configuration.totalGames());
            assertEquals(ArenaSchedule.task(restored.configuration(), 7), ArenaSchedule.task(configuration, 7));
        }
    }

    @Test
    public void schedulesEveryPairOnceAndBalancesStarts() {
        final ArenaConfiguration configuration = configuration(10, 100, 2);
        final Map<String, Integer> pairCounts = new HashMap<>();
        final Map<String, Integer> leftStarts = new HashMap<>();
        final int[] appearances = new int[10];
        assertEquals(configuration.totalGames(), 4500);
        for (int game = 0; game < configuration.totalGames(); game++) {
            final ArenaSchedule.Task task = ArenaSchedule.task(configuration, game);
            assertTrue(task.left() < task.right());
            final String pair = task.left() + ":" + task.right();
            pairCounts.merge(pair, 1, Integer::sum);
            if (task.firstToChoose() == task.left()) { leftStarts.merge(pair, 1, Integer::sum); }
            appearances[task.left()]++;
            appearances[task.right()]++;
            assertEquals(ArenaSchedule.task(configuration, game), task);
        }
        assertEquals(pairCounts.size(), 45);
        for (final String pair : pairCounts.keySet()) {
            assertEquals(pairCounts.get(pair).intValue(), 100);
            assertEquals(leftStarts.get(pair).intValue(), 50);
        }
        for (final int count : appearances) { assertEquals(count, 900); }
    }

    @Test
    public void preservesLegacyBo1AndValidatesCompleteBo3Matches() throws Exception {
        final ArenaConfiguration legacy = configuration(2, 3, 1);
        final String oldJson = ArenaStore.JSON.toJson(legacy).replace(",\"gamesPerMatch\":1", "");
        assertEquals(ArenaStore.JSON.fromJson(oldJson, ArenaConfiguration.class).gamesPerMatch(), 1);
        final ArenaConfiguration bo3 = new ArenaConfiguration(ArenaConfiguration.VERSION, "bo3-test", "BO3", 0,
                1234, "build", "inputs", 3, 1, 512, 120, legacy.entrants(), 3);
        final ArenaResult.GameResult left = new ArenaResult.GameResult(ArenaResult.Outcome.WIN, 0, 1, 5, 10, "win");
        final ArenaResult.GameResult right = new ArenaResult.GameResult(ArenaResult.Outcome.WIN, 1, 0, 5, 10, "win");
        final ArenaResult.GameResult draw = new ArenaResult.GameResult(ArenaResult.Outcome.DRAW, -1, 1, 5, 10, "draw");
        final ArenaResult sweep = new ArenaResult(0, ArenaResult.Outcome.WIN, 0, 1, 10, 20, "2-0", List.of(left, left));
        final ArenaResult decider = new ArenaResult(1, ArenaResult.Outcome.WIN, 0, 1, 15, 30, "2-1", List.of(left, right, left));
        final ArenaResult replay = new ArenaResult(2, ArenaResult.Outcome.WIN, 0, 1, 15, 30, "2-0", List.of(draw, left, left));
        sweep.validate(bo3);
        decider.validate(bo3);
        replay.validate(bo3);
        expectThrows(IllegalArgumentException.class, () -> new ArenaResult(0, ArenaResult.Outcome.WIN, 0, 1,
                15, 30, "extra game", List.of(left, left, right)).validate(bo3));
        expectThrows(IllegalArgumentException.class, () -> new ArenaResult(0, ArenaResult.Outcome.WIN, 1, 1,
                10, 20, "wrong winner", List.of(left, left)).validate(bo3));
        expectThrows(IllegalArgumentException.class, () -> new ArenaResult(0, ArenaResult.Outcome.WIN, 0, 1,
                5, 10, "incomplete", List.of(left)).validate(bo3));
        expectThrows(IllegalArgumentException.class, () -> new ArenaResult(0, ArenaResult.Outcome.WIN, 0, 1,
                5, 10, "no games").validate(bo3));
        final ArenaStandings standings = new ArenaStandings(bo3, List.of(sweep, decider, replay));
        assertEquals(standings.total(0).wins, 3);
        assertEquals(standings.total(1).losses, 3);
        try (ArenaStore store = ArenaStore.create(directory, bo3)) {
            store.claim();
            store.append(decider);
            final Path export = ArenaExport.export(store);
            assertEquals(Files.readAllLines(export.resolve("games.csv")).size(), 4);
            assertEquals(Files.readAllLines(export.resolve("matches.csv")).size(), 2);
        }
        try (ArenaStore restored = new ArenaStore(directory)) {
            assertEquals(restored.configuration().gamesPerMatch(), 3);
            assertEquals(restored.results().get(0), decider);
        }
    }

    @Test
    public void handlesOddGamesAndRejectsInvalidSchedules() {
        final ArenaConfiguration configuration = configuration(3, 5, 1);
        for (int pair = 0; pair < 3; pair++) {
            int starts = 0;
            for (int game = pair * 5; game < (pair + 1) * 5; game++) {
                final ArenaSchedule.Task task = ArenaSchedule.task(configuration, game);
                if (task.firstToChoose() == task.left()) { starts++; }
            }
            assertTrue(starts == 2 || starts == 3);
        }
        expectThrows(IllegalArgumentException.class, () -> configuration(1, 100, 1));
        expectThrows(IllegalArgumentException.class, () -> configuration(2, 0, 1));
        expectThrows(IllegalArgumentException.class, () -> ArenaSchedule.task(configuration, 15));
        expectThrows(IllegalArgumentException.class, () -> ArenaConfiguration.gameCount(100000, 100000));
    }

    @Test
    public void scoresRealDrawsButNotFailuresOrDuplicateResults() {
        final ArenaConfiguration configuration = configuration(2, 5, 1);
        final ArenaResult win = new ArenaResult(0, ArenaResult.Outcome.WIN, 0, 1, 8, 2000, "win");
        final List<ArenaResult> results = List.of(win, win,
                new ArenaResult(1, ArenaResult.Outcome.DRAW, -1, 0, 10, 4000, "draw"),
                new ArenaResult(2, ArenaResult.Outcome.TIMEOUT, -1, -1, 0, 120000, "timeout"),
                new ArenaResult(3, ArenaResult.Outcome.ERROR, -1, -1, 0, 50, "error"));
        final ArenaStandings standings = new ArenaStandings(configuration, results);
        assertEquals(standings.total(0).validGames(), 2);
        assertEquals(standings.total(0).score(), 0.75);
        assertEquals(standings.total(0).winRate(), 0.5);
        assertEquals(standings.total(0).averageTurns(), 9.0);
        assertEquals(standings.total(0).averageSeconds(), 3.0);
        assertEquals(standings.total(1).score(), 0.25);
        assertEquals(standings.total(0).timeouts, 1);
        assertEquals(standings.total(0).errors, 1);
        assertEquals(standings.pairing(0, 1).wins, 1);
        expectThrows(IllegalArgumentException.class,
                () -> new ArenaResult(0, ArenaResult.Outcome.WIN, 4, 0, 1, 1, "").validate(configuration));
    }

    @Test
    public void recoversOnlyCommittedRecordsAndPreventsDuplicateWriters() throws Exception {
        final ArenaConfiguration configuration = configuration(2, 3, 1);
        final ArenaResult first = win(ArenaSchedule.task(configuration, 0));
        try (ArenaStore store = ArenaStore.create(directory, configuration)) {
            store.claim();
            store.append(first);
            store.append(first);
            assertEquals(store.completed(), 1);
            try (ArenaStore other = new ArenaStore(directory)) {
                expectThrows(IOException.class, other::claim);
            }
        }
        Files.writeString(directory.resolve("games.jsonl"), "{\"gameId\":1", StandardOpenOption.APPEND);
        try (ArenaStore recovered = new ArenaStore(directory)) {
            assertEquals(recovered.completed(), 1);
            recovered.claim();
            recovered.append(win(ArenaSchedule.task(configuration, 1)));
        }
        try (ArenaStore complete = new ArenaStore(directory)) { assertEquals(complete.completed(), 2); }
    }

    @Test(timeOut = 15000)
    public void pausesAndResumesWithoutReplayingCommittedGames() throws Exception {
        final ArenaStore store = ArenaStore.create(directory, configuration(3, 4, 2));
        final CountDownLatch started = new CountDownLatch(2);
        final CountDownLatch release = new CountDownLatch(1);
        final AtomicInteger calls = new AtomicInteger();
        final ArenaWorker.Factory factory = number -> new ArenaWorker() {
            @Override public ArenaResult play(final ArenaSchedule.Task task) throws InterruptedException {
                started.countDown();
                release.await();
                calls.incrementAndGet();
                return win(task);
            }
            @Override public void close() { release.countDown(); }
        };
        try (ArenaRunner runner = new ArenaRunner(store, factory)) {
            runner.start();
            assertTrue(started.await(5, TimeUnit.SECONDS));
            runner.pause();
            release.countDown();
            awaitStopped(runner);
            assertEquals(store.completed(), 2);
            assertEquals(runner.progress().state(), ArenaStore.State.PAUSED);
        }
        try (ArenaStore recovered = new ArenaStore(directory); ArenaRunner resumed = new ArenaRunner(recovered, factory)) {
            resumed.start();
            awaitStopped(resumed);
            assertEquals(resumed.progress().state(), ArenaStore.State.COMPLETED);
            assertEquals(recovered.completed(), 12);
            assertEquals(calls.get(), 12);
        }
    }

    @Test
    public void claimingAStaleReportReloadsElapsedTime() throws Exception {
        try (ArenaStore original = ArenaStore.create(directory, configuration(2, 3, 1));
             ArenaStore previouslyOpened = new ArenaStore(directory)) {
            original.claim();
            original.update(ArenaStore.State.PAUSED, 4567, "");
            original.close();
            assertEquals(previouslyOpened.metadata().elapsedMillis(), 0L);
            previouslyOpened.claim();
            assertEquals(previouslyOpened.metadata().elapsedMillis(), 4567L);
            assertEquals(previouslyOpened.metadata().state(), ArenaStore.State.PAUSED);
        }
    }

    @Test(timeOut = 15000)
    public void cancelsActiveGamesAndStopsOnInitializationFailure() throws Exception {
        final ArenaStore store = ArenaStore.create(directory, configuration(2, 4, 1));
        final CountDownLatch entered = new CountDownLatch(1);
        final CountDownLatch released = new CountDownLatch(1);
        try (ArenaRunner runner = new ArenaRunner(store, number -> new ArenaWorker() {
            @Override public ArenaResult play(final ArenaSchedule.Task task) throws InterruptedException {
                entered.countDown();
                released.await();
                return win(task);
            }
            @Override public void close() { released.countDown(); }
        })) {
            runner.start();
            assertTrue(entered.await(5, TimeUnit.SECONDS));
            runner.cancel();
            awaitStopped(runner);
            assertEquals(store.completed(), 0);
            assertEquals(runner.progress().state(), ArenaStore.State.CANCELLED);
        }
        try (ArenaRunner failed = new ArenaRunner(new ArenaStore(directory), number -> { throw new IOException("Cannot initialize"); })) {
            failed.start();
            awaitStopped(failed);
            assertEquals(failed.progress().state(), ArenaStore.State.FAILED);
            assertTrue(failed.progress().message().contains("Cannot initialize"));
        }
    }

    @Test
    public void fingerprintsDetectChangedInputsAndExportsIncludeFailures() throws Exception {
        final Path snapshot = Files.createDirectory(directory.resolve("inputs"));
        Files.writeString(snapshot.resolve("deck.dck"), "original");
        final String before = ArenaFingerprint.calculate(List.of(snapshot));
        Files.writeString(snapshot.resolve("deck.dck"), "modified");
        assertFalse(before.equals(ArenaFingerprint.calculate(List.of(snapshot))));
        try (ArenaStore store = ArenaStore.create(directory, configuration(2, 2, 1))) {
            store.claim();
            store.append(new ArenaResult(0, ArenaResult.Outcome.ERROR, -1, -1, 0, 10, "=formula,\"quoted\""));
            final Path export = ArenaExport.export(store);
            assertTrue(Files.readString(export.resolve("games.csv")).contains("'="));
            assertTrue(Files.readString(export.resolve("standings.csv")).contains("Errors"));
            assertTrue(Files.isRegularFile(export.resolve("matchups.csv")));
        }
    }

    private static ArenaResult win(final ArenaSchedule.Task task) {
        return new ArenaResult(task.gameId(), ArenaResult.Outcome.WIN, task.left(), task.firstToChoose(), 5, 10, "test");
    }

    private static void awaitStopped(final ArenaRunner runner) throws InterruptedException {
        final long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(10);
        while (runner.isRunning() && System.nanoTime() < deadline) { Thread.sleep(20); }
        assertFalse(runner.isRunning(), "Runner did not stop");
    }
}
