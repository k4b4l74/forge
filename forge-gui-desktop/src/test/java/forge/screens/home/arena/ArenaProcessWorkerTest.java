package forge.screens.home.arena;

import forge.ai.AiDecisionTrace;
import forge.gamemodes.aisimulation.ArenaConfiguration;
import forge.gamemodes.aisimulation.ArenaFingerprint;
import forge.gamemodes.aisimulation.ArenaProtocol;
import forge.gamemodes.aisimulation.ArenaResult;
import forge.gamemodes.aisimulation.ArenaRunner;
import forge.gamemodes.aisimulation.ArenaSchedule;
import forge.gamemodes.aisimulation.ArenaStore;
import forge.view.ArenaDecisionTrace;
import forge.view.ArenaLearningSession;
import forge.view.ArenaReplayMain;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.expectThrows;

public class ArenaProcessWorkerTest {
    private Path directory;
    private final Path assets = Path.of("../forge-gui").toAbsolutePath().normalize();

    @BeforeMethod
    public void prepare() throws IOException {
        directory = Files.createTempDirectory("arena process with spaces ");
        Files.createDirectories(directory.resolve("inputs/decks"));
        Files.createDirectories(directory.resolve("inputs/profiles"));
        Files.copy(assets.resolve("res/ai/Default.ai"), directory.resolve("inputs/profiles/Default.ai"));
        Files.copy(assets.resolve("res/ai/Reckless.ai"), directory.resolve("inputs/profiles/Reckless.ai"));
        Files.writeString(directory.resolve("inputs/decks/0.dck"), "[metadata]\nName=Red\n[Main]\n24 Mountain\n"
                + "4 Lightning Bolt\n4 Shock\n4 Raging Goblin\n4 Goblin Piker\n4 Hill Giant\n4 Fire Elemental\n"
                + "4 Volcanic Hammer\n4 Goblin Arsonist\n4 Incinerate\n");
        Files.writeString(directory.resolve("inputs/decks/1.dck"), "[metadata]\nName=White\n[Main]\n24 Plains\n"
                + "4 Savannah Lions\n4 Benalish Hero\n4 Elite Vanguard\n4 Silvercoat Lion\n4 Glory Seeker\n"
                + "4 Serra Angel\n4 Swords to Plowshares\n4 Pacifism\n4 Suntail Hawk\n");
    }

    @AfterMethod
    public void cleanup() throws IOException {
        try (Stream<Path> paths = Files.walk(directory)) {
            for (final Path path : paths.sorted(Comparator.reverseOrder()).toList()) { Files.deleteIfExists(path); }
        }
    }

    private ArenaStore create(final int games, final int timeout) throws IOException {
        return create(games, timeout, 1);
    }

    private ArenaStore create(final int games, final int timeout, final int gamesPerMatch) throws IOException {
        return create(games, timeout, gamesPerMatch, "Default");
    }

    private ArenaStore create(final int games, final int timeout, final int gamesPerMatch, final String leftProfile) throws IOException {
        final List<ArenaConfiguration.Entrant> entrants = List.of(
                new ArenaConfiguration.Entrant("Red", "Red", leftProfile),
                new ArenaConfiguration.Entrant("White", "White", "Reckless"));
        return ArenaStore.create(directory, new ArenaConfiguration(ArenaConfiguration.VERSION, "worker-test", "Worker test", 0, 4321,
                "test", ArenaFingerprint.calculate(List.of(directory.resolve("inputs"))), games, 2, 1024, timeout, entrants, gamesPerMatch));
    }

    @Test
    public void validatesTraceSelectionsAndCapsOutput() throws Exception {
        try (ArenaStore store = create(1100, 90)) {
            final ArenaConfiguration configuration = store.configuration();
            final Path selection = directory.resolve("trace-matches.txt");
            assertTrue(ArenaDecisionTrace.selectedMatches(directory, configuration).isEmpty());
            Files.writeString(selection, "0, 1\n1\t2");
            assertEquals(ArenaDecisionTrace.selectedMatches(directory, configuration), Set.of(0, 1, 2));
            for (final String invalid : List.of("-1", "1100", "abc", "0 ".repeat(32769),
                    IntStream.rangeClosed(0, 1000).mapToObj(Integer::toString).collect(Collectors.joining(",")))) {
                Files.writeString(selection, invalid);
                expectThrows(IOException.class, () -> ArenaDecisionTrace.selectedMatches(directory, configuration));
            }
            try (ArenaDecisionTrace trace = new ArenaDecisionTrace(directory, configuration, ArenaSchedule.task(configuration, 0))) {
                trace.write("large".repeat(500000));
                trace.write("should not appear");
            }
            assertFalse(AiDecisionTrace.active());
            try (Stream<Path> paths = Files.list(directory.resolve("traces"))) {
                final Path trace = paths.findFirst().orElseThrow();
                assertTrue(Files.size(trace) < 2 * 1024 * 1024 + 100);
                final String text = Files.readString(trace);
                assertTrue(text.contains("\"kind\":\"truncated\""));
                assertFalse(text.contains("should not appear"));
            }
        }
    }

    @Test(timeOut = 180000)
    public void diagnosticReplayPreservesSourceAndRefusesExistingOutput() throws Exception {
        try (ArenaStore initial = create(2, 90)) {
            final Path source = directory.resolve("source");
            final Path output = directory.resolve("replay");
            ArenaFiles.copyTree(directory.resolve("inputs"), source.resolve("inputs"));
            try (ArenaStore original = ArenaStore.create(source, initial.configuration())) {
                final String before = ArenaFingerprint.calculate(List.of(source));
                final String[] arguments = {source.toString(), assets.toString(), output.toString(), "0"};
                ArenaReplayMain.main(arguments);
                assertEquals(ArenaFingerprint.calculate(List.of(source)), before);
                assertEquals(original.completed(), 0);
                try (ArenaStore replay = new ArenaStore(output)) {
                    assertEquals(replay.completed(), 1);
                    assertEquals(replay.metadata().state(), ArenaStore.State.PAUSED);
                    assertTrue(replay.results().get(0).isValidGame());
                }
                expectThrows(java.nio.file.FileAlreadyExistsException.class, () -> ArenaReplayMain.main(arguments));
                arguments[2] = source.resolve("nested").toString();
                expectThrows(IllegalArgumentException.class, () -> ArenaReplayMain.main(arguments));
            }
        }
    }

    @Test(timeOut = 180000)
    public void playsKabalReanimatorWithTheDedicatedProfile() throws Exception {
        Files.copy(assets.resolve("res/ai/OS Reanimator.ai"), directory.resolve("inputs/profiles/OS Reanimator.ai"));
        Files.writeString(directory.resolve("inputs/decks/0.dck"), "[metadata]\nName=KabaL\n[Main]\n"
                + "1 Ancestral Recall\n4 Animate Dead\n1 Balance\n4 Bayou\n3 Bazaar of Baghdad\n3 Birds of Paradise\n"
                + "1 Black Lotus\n1 Braingeyser\n1 Chaos Orb\n4 City of Brass\n3 Copy Artifact\n1 Demonic Tutor\n"
                + "4 Erhnam Djinn\n1 Mana Drain\n1 Mox Emerald\n1 Mox Jet\n1 Mox Pearl\n1 Mox Ruby\n1 Mox Sapphire\n"
                + "3 Psionic Blast\n3 Rasputin Dreamweaver\n1 Recall\n1 Regrowth\n1 Sol Ring\n1 Time Walk\n"
                + "1 Timetwister\n4 Triskelion\n4 Tropical Island\n3 Underground Sea\n1 Wheel of Fortune\n[Sideboard]\n"
                + "2 Blue Elemental Blast\n2 Circle of Protection: Red\n2 Disenchant\n2 Red Elemental Blast\n"
                + "3 Swords to Plowshares\n4 Underworld Dreams\n");
        try (ArenaStore store = create(1, 90, 3, "OS Reanimator");
             ArenaProcessWorker worker = new ArenaProcessWorker(store, 0, assets)) {
            final ArenaResult result = worker.play(ArenaSchedule.task(store.configuration(), 0));
            assertTrue(result.isValidGame(), result.toString());
            result.validate(store.configuration());
            assertEquals(result.games().get(0).leftSideboardCards(), 0);
            assertTrue(result.games().stream().skip(1).anyMatch(game -> game.leftSideboardCards() > 0), result.toString());
            ArenaStore.writeJson(directory.resolve("inputs/learning.json"), new ArenaLearningSession.Settings(1,
                    "collect", List.of("OS Reanimator"), "", "", 1000));
            try (ArenaProcessWorker collector = new ArenaProcessWorker(store, 1, assets)) {
                final ArenaResult collected = collector.play(ArenaSchedule.task(store.configuration(), 0));
                assertTrue(collected.isValidGame(), collected.toString());
                assertEquals(collected.winner(), result.winner());
                assertEquals(collected.turns(), result.turns());
                assertEquals(collected.games().size(), result.games().size());
                for (int index = 0; index < result.games().size(); index++) {
                    assertEquals(collected.games().get(index).winner(), result.games().get(index).winner());
                }
            }
            try (Stream<Path> paths = Files.list(directory.resolve("learning"))) {
                final List<String> logs = new ArrayList<>();
                for (final Path path : paths.toList()) { logs.add(Files.readString(path)); }
                assertEquals(logs.size(), result.games().size());
                assertTrue(logs.stream().anyMatch(log -> log.contains("\"reason\":\"teacher\"")));
                assertTrue(logs.stream().allMatch(log -> log.contains("\"kind\":\"outcome\"")));
                for (final String log : logs) {
                    final String outcome = log.lines().reduce((previous, last) -> last).orElseThrow();
                    final var rewards = ArenaStore.JSON.fromJson(outcome, com.google.gson.JsonObject.class).getAsJsonObject("rewards");
                    assertEquals(rewards.size(), 2);
                    assertEquals(rewards.get("Arena-0").getAsInt() + rewards.get("Arena-1").getAsInt(), 0);
                }
            }
        }
    }

    @Test(timeOut = 180000)
    public void comparesTheSameDeckWithDifferentProfilesInBo3() throws Exception {
        Files.move(directory.resolve("inputs/decks/1.dck"), directory.resolve("inputs/decks/2.dck"));
        Files.copy(directory.resolve("inputs/decks/0.dck"), directory.resolve("inputs/decks/1.dck"));
        final List<ArenaConfiguration.Entrant> entrants = List.of(
                new ArenaConfiguration.Entrant("Red", "Red", "Default"),
                new ArenaConfiguration.Entrant("Red", "Red", "Reckless"),
                new ArenaConfiguration.Entrant("White", "White", "Default"));
        final String fingerprint = ArenaFingerprint.calculate(List.of(directory.resolve("inputs")));
        final ArenaConfiguration configuration = new ArenaConfiguration(ArenaConfiguration.VERSION, "comparison-worker", "Compare", 0,
                4321, "test", fingerprint, 1, 1, 1024, 60, entrants, 3, 2);
        try (ArenaStore store = ArenaStore.create(directory, configuration);
             ArenaProcessWorker worker = new ArenaProcessWorker(store, 0, assets)) {
            for (int match = 0; match < configuration.totalGames(); match++) {
                final ArenaSchedule.Task task = ArenaSchedule.task(configuration, match);
                final ArenaResult result = worker.play(task);
                assertTrue(result.isValidGame(), result.toString());
                result.validate(configuration);
                assertEquals(result.startingPlayer(), task.firstToChoose());
                assertTrue(result.games().size() >= 2);
                assertEquals(result.games().get(0).leftSideboardCards(), 0);
            }
            assertEquals(ArenaFingerprint.calculate(List.of(directory.resolve("inputs"))), fingerprint);
        }
    }

    @Test(timeOut = 180000)
    public void selectedTracesStaySeparateAndDoNotChangeSeededOutcomes() throws Exception {
        try (ArenaStore store = create(1, 90, 3)) {
            final ArenaSchedule.Task task = ArenaSchedule.task(store.configuration(), 0);
            final ArenaResult baseline;
            try (ArenaProcessWorker worker = new ArenaProcessWorker(store, 0, assets)) { baseline = worker.play(task); }
            assertTrue(baseline.isValidGame(), baseline.toString());
            assertFalse(Files.exists(directory.resolve("traces")));
            Files.writeString(directory.resolve("trace-matches.txt"), "0");
            final ArenaResult traced;
            try (ArenaProcessWorker worker = new ArenaProcessWorker(store, 1, assets)) { traced = worker.play(task); }
            assertTrue(traced.isValidGame(), traced.toString());
            assertEquals(traced.winner(), baseline.winner());
            assertEquals(traced.turns(), baseline.turns());
            assertEquals(traced.games().size(), baseline.games().size());
            for (int index = 0; index < traced.games().size(); index++) {
                assertEquals(traced.games().get(index).winner(), baseline.games().get(index).winner());
                assertEquals(traced.games().get(index).startingPlayer(), baseline.games().get(index).startingPlayer());
            }
            final Path trace;
            try (Stream<Path> paths = Files.list(directory.resolve("traces"))) { trace = paths.findFirst().orElseThrow(); }
            final String contents = Files.readString(trace);
            assertTrue(contents.contains("\"decision\":\"mulligan\""));
            assertTrue(contents.contains("\"kind\":\"game-log\""));
            assertTrue(contents.contains("\"kind\":\"result\""));
            assertTrue(contents.contains("\"game\":2"));
            assertTrue(Files.size(trace) < 2 * 1024 * 1024 + 100);
        }
    }

    @Test(timeOut = 180000)
    public void playsBo3AndRestoresDecksForTheNextMatch() throws Exception {
        Files.writeString(directory.resolve("inputs/profiles/Default.ai"), "\nSIDEBOARDING_CHANCE_ON_WIN=100\n",
                java.nio.file.StandardOpenOption.APPEND);
        Files.writeString(directory.resolve("inputs/decks/0.dck"), "[metadata]\nName=Red\n"
                + "AiHints=SideboardingPlan$ Shock->Lightning Bolt\n[Main]\n24 Mountain\n4 Shock\n"
                + "4 Raging Goblin\n4 Goblin Piker\n4 Hill Giant\n4 Fire Elemental\n4 Volcanic Hammer\n"
                + "4 Goblin Arsonist\n4 Incinerate\n4 Grizzly Bears\n[Sideboard]\n4 Lightning Bolt\n");
        try (ArenaStore store = create(2, 60, 3);
             ArenaProcessWorker worker = new ArenaProcessWorker(store, 0, assets)) {
            final String before = ArenaFingerprint.calculate(List.of(directory.resolve("inputs")));
            for (int match = 0; match < 2; match++) {
                final ArenaResult result = worker.play(ArenaSchedule.task(store.configuration(), match));
                assertTrue(result.isValidGame(), result.toString());
                result.validate(store.configuration());
                assertTrue(result.games().size() >= 2);
                assertEquals(result.games().get(0).leftSideboardCards(), 0);
                assertEquals(result.games().get(1).leftSideboardCards(), 4);
                assertEquals(result.games().stream().filter(game -> game.winner() == result.winner()).count(), 2L);
                assertEquals(result.startingPlayer(), ArenaSchedule.task(store.configuration(), match).firstToChoose());
                for (int index = 1; index < result.games().size(); index++) {
                    if (result.games().get(index - 1).winner() != -1) {
                        assertNotEquals(result.games().get(index).startingPlayer(), result.games().get(index - 1).winner());
                    }
                }
            }
            assertEquals(ArenaFingerprint.calculate(List.of(directory.resolve("inputs"))), before);
        }
    }

    @Test(timeOut = 180000)
    public void playsIndependentHeadlessGamesInParallelAndPreservesSnapshots() throws Exception {
        try (ArenaStore store = create(4, 30)) {
            final String before = ArenaFingerprint.calculate(List.of(directory.resolve("inputs")));
            final List<ProcessHandle> children = Collections.synchronizedList(new ArrayList<>());
            try (ArenaRunner runner = new ArenaRunner(store, number -> {
                final ArenaProcessWorker worker = new ArenaProcessWorker(store, number, assets);
                children.add(worker.handle());
                return worker;
            })) {
                runner.start();
                final long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(150);
                while (runner.isRunning() && System.nanoTime() < deadline) { Thread.sleep(100); }
                assertFalse(runner.isRunning(), "Worker tournament did not finish");
                assertEquals(runner.progress().state(), ArenaStore.State.COMPLETED, runner.progress().message());
                assertEquals(store.completed(), 4);
                for (final ArenaResult result : store.results()) {
                    assertTrue(result.isValidGame(), result.toString());
                    assertEquals(result.startingPlayer(), ArenaSchedule.task(store.configuration(), result.gameId()).firstToChoose());
                }
            }
            for (final ProcessHandle child : children) { child.onExit().get(10, TimeUnit.SECONDS); }
            assertEquals(ArenaFingerprint.calculate(List.of(directory.resolve("inputs"))), before);
            assertTrue(Files.isDirectory(directory.resolve("workers/0/profile")));
            assertTrue(Files.isDirectory(directory.resolve("workers/1/profile")));
            Files.writeString(directory.resolve("inputs/decks/0.dck"), "changed");
            expectThrows(IOException.class, () -> ArenaFiles.verifySnapshots(store));
        }
    }

    @Test(timeOut = 20000)
    public void hardTimeoutKillsHungProcessInsteadOfRecordingADraw() throws Exception {
        try (ArenaStore store = create(1, 1);
             ArenaProcessWorker worker = new ArenaProcessWorker(store, 0, assets, HungWorker.class, 10)) {
            final ArenaResult result = worker.play(ArenaSchedule.task(store.configuration(), 0));
            assertEquals(result.outcome(), ArenaResult.Outcome.TIMEOUT);
            assertEquals(result.winner(), -1);
            worker.handle().onExit().get(5, TimeUnit.SECONDS);
            assertFalse(worker.handle().isAlive());
        }
    }

    @Test(timeOut = 20000)
    public void crashAndStartupFailureAreNotDraws() throws Exception {
        try (ArenaStore store = create(1, 5);
             ArenaProcessWorker worker = new ArenaProcessWorker(store, 0, assets, CrashedWorker.class, 10)) {
            assertEquals(worker.play(ArenaSchedule.task(store.configuration(), 0)).outcome(), ArenaResult.Outcome.ERROR);
            worker.handle().onExit().get(5, TimeUnit.SECONDS);
        }
        try (ArenaStore store = new ArenaStore(directory);
             ArenaProcessWorker worker = new ArenaProcessWorker(store, 1, assets, FailedStartup.class, 10)) {
            expectThrows(IOException.class, () -> worker.play(ArenaSchedule.task(store.configuration(), 0)));
            worker.handle().onExit().get(5, TimeUnit.SECONDS);
        }
    }

    public static final class HungWorker {
        public static void main(final String[] args) throws Exception {
            System.out.println(ArenaStore.JSON.toJson(ArenaProtocol.ready()));
            new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8)).readLine();
            Thread.sleep(60000);
        }
    }

    public static final class CrashedWorker {
        public static void main(final String[] args) throws Exception {
            System.out.println(ArenaStore.JSON.toJson(ArenaProtocol.ready()));
            new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8)).readLine();
            System.exit(7);
        }
    }

    public static final class FailedStartup {
        public static void main(final String[] args) { System.exit(8); }
    }
}
