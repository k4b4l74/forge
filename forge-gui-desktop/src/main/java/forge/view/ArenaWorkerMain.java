package forge.view;

import forge.GuiDesktop;
import forge.ai.AiProfileUtil;
import forge.deck.Deck;
import forge.deck.io.DeckSerializer;
import forge.game.Game;
import forge.game.GameOutcome;
import forge.game.GameRules;
import forge.game.GameType;
import forge.game.Match;
import forge.game.player.RegisteredPlayer;
import forge.gamemodes.aisimulation.ArenaConfiguration;
import forge.gamemodes.aisimulation.ArenaProtocol;
import forge.gamemodes.aisimulation.ArenaResult;
import forge.gamemodes.aisimulation.ArenaSchedule;
import forge.gamemodes.aisimulation.ArenaStore;
import forge.gui.GuiBase;
import forge.item.PaperCard;
import forge.localinstance.properties.ForgePreferences.FPref;
import forge.model.FModel;
import forge.player.GamePlayerUtil;
import forge.util.MyRandom;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public final class ArenaWorkerMain {
    private ArenaWorkerMain() { }

    public static void main(final String[] args) {
        final PrintStream protocol = System.out;
        System.setOut(System.err);
        final BlockingQueue<String> requests = new ArrayBlockingQueue<>(1);
        final Thread reader = new Thread(() -> {
            try (BufferedReader input = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
                String line;
                while ((line = input.readLine()) != null) {
                    if (line.length() > 65536 || !requests.offer(line)) {
                        throw new IllegalArgumentException("Invalid worker request queue");
                    }
                }
                System.exit(0);
            } catch (Exception exception) {
                exception.printStackTrace();
                System.exit(2);
            }
        }, "ai-arena-parent-monitor");
        reader.setDaemon(true);
        reader.start();
        try {
            final Path runDirectory = Path.of(args[0]);
            final String assets = Path.of(args[1]).toAbsolutePath() + File.separator;
            final ArenaConfiguration configuration = ArenaStore.JSON.fromJson(
                    Files.readString(runDirectory.resolve("run.json")), ArenaConfiguration.class);
            final Set<Integer> tracedMatches = ArenaDecisionTrace.selectedMatches(runDirectory, configuration);
            final ArenaLearningSession.Settings learning = ArenaLearningSession.readSettings(runDirectory, configuration);
            GuiBase.setInterface(new GuiDesktop() {
                @Override
                public String getAssetsDir() { return assets; }
            });
            FModel.initialize(null, preferences -> {
                preferences.setPref(FPref.LOAD_CARD_SCRIPTS_LAZILY, true);
                preferences.setPref(FPref.FILTERED_HANDS, false);
                preferences.setPref(FPref.MULLIGAN_RULE, "London");
                preferences.setPref(FPref.PERFORMANCE_MODE, true);
                preferences.setPref(FPref.UI_ENABLE_AI_CHEATS, false);
                preferences.setPref(FPref.UI_CURRENT_AI_PROFILE, "Default");
                preferences.setPref(FPref.MATCH_AI_SIDEBOARDING_MODE, configuration.gamesPerMatch() == 3 ? "AI" : "Off");
                return null;
            });
            AiProfileUtil.loadAllProfiles(runDirectory.resolve("inputs/profiles").toString());
            final List<Deck> decks = new ArrayList<>();
            for (int entrant = 0; entrant < configuration.entrants().size(); entrant++) {
                final Deck deck = DeckSerializer.fromFile(runDirectory.resolve("inputs/decks/" + entrant + ".dck").toFile());
                if (deck == null || deck.getMain().isEmpty()) {
                    throw new IllegalArgumentException("Unable to load deck " + entrant);
                }
                decks.add(deck);
            }
            protocol.println(ArenaStore.JSON.toJson(ArenaProtocol.ready()));
            while (true) {
                final ArenaProtocol request = ArenaStore.JSON.fromJson(requests.take(), ArenaProtocol.class);
                if (request == null || request.version() != ArenaProtocol.VERSION || !"job".equals(request.type())
                        || request.task() == null || !ArenaSchedule.task(configuration, request.task().gameId()).equals(request.task())) {
                    throw new IllegalArgumentException("Invalid worker protocol request");
                }
                final ArenaResult result;
                try (ArenaDecisionTrace trace = tracedMatches.contains(request.task().gameId())
                        ? new ArenaDecisionTrace(runDirectory, configuration, request.task()) : null) {
                    result = play(configuration, decks, request.task(), trace, runDirectory, learning);
                    if (trace != null) { trace.write(Map.of("kind", "result", "result", result)); }
                }
                protocol.println(ArenaStore.JSON.toJson(ArenaProtocol.result(result)));
                protocol.flush();
                if (!result.isValidGame()) { System.exit(2); }
            }
        } catch (Throwable exception) {
            exception.printStackTrace();
            protocol.println(ArenaStore.JSON.toJson(ArenaProtocol.failure(exception.toString())));
            protocol.flush();
            System.exit(2);
        }
    }

    private static ArenaResult play(final ArenaConfiguration configuration, final List<Deck> decks,
                                     final ArenaSchedule.Task task, final ArenaDecisionTrace trace, final Path directory,
                                     final ArenaLearningSession.Settings learning) {
        final long started = System.nanoTime();
        try {
            MyRandom.setRandom(new Random(task.seed()));
            final List<RegisteredPlayer> players = new ArrayList<>();
            for (final int entrant : List.of(task.left(), task.right())) {
                final ArenaConfiguration.Entrant entry = configuration.entrants().get(entrant);
                final Deck copy = (Deck) decks.get(entrant).copyTo(entry.name());
                players.add(new RegisteredPlayer(copy).setPlayer(GamePlayerUtil.createAiPlayer(
                        "Arena-" + entrant, 0, 0, null, entry.profile())));
            }
            final GameRules rules = new GameRules(GameType.Constructed);
            rules.setGamesPerMatch(configuration.gamesPerMatch());
            rules.setAISideboardingEnabled(configuration.gamesPerMatch() == 3);
            rules.setWarnAboutAICards(false);
            final Match match = new Match(rules, players, "AI Arena");
            final List<ArenaResult.GameResult> games = new ArrayList<>();
            do {
                final long gameStarted = System.nanoTime();
                final Game game = match.createGame();
                game.setNoGUIUser();
                if (trace != null) { trace.beginGame(games.size() + 1); }
                try (ArenaLearningSession session = learning == null ? null
                        : new ArenaLearningSession(directory, learning, game, task.gameId(), games.size() + 1, task.seed())) {
                    match.startGame(game, null, games.isEmpty()
                            ? players.get(task.firstToChoose() == task.left() ? 0 : 1) : null);
                    if (session != null && game.getOutcome() != null) { session.finish(game); }
                }
                if (trace != null) { trace.endGame(game); }
                final GameOutcome outcome = game.getOutcome();
                if (outcome == null) { throw new IllegalStateException("Game returned without an outcome"); }
                final int gameWinner = outcome.isDraw() ? -1 : outcome.isWinner(players.get(0)) ? task.left() : task.right();
                final int first = game.getStartingPlayer().getRegisteredPlayer() == players.get(0) ? task.left() : task.right();
                games.add(new ArenaResult.GameResult(outcome.isDraw() ? ArenaResult.Outcome.DRAW : ArenaResult.Outcome.WIN,
                        gameWinner, first, outcome.getLastTurnNumber(),
                        TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - gameStarted), outcome.getWinCondition().name(),
                        sideboardCards(decks.get(task.left()), players.get(0).getDeck()),
                        sideboardCards(decks.get(task.right()), players.get(1).getDeck())));
            } while (configuration.gamesPerMatch() == 3 && !match.isMatchOver());
            final int winner = match.isWonBy(players.get(0).getPlayer()) ? task.left()
                    : match.isWonBy(players.get(1).getPlayer()) ? task.right() : -1;
            return new ArenaResult(task.gameId(), winner == -1 ? ArenaResult.Outcome.DRAW : ArenaResult.Outcome.WIN,
                    winner, games.get(0).startingPlayer(), games.stream().mapToInt(ArenaResult.GameResult::turns).sum(),
                    TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - started),
                    "BO" + configuration.gamesPerMatch() + " " + match.getGamesWonBy(players.get(0).getPlayer())
                            + "-" + match.getGamesWonBy(players.get(1).getPlayer()), games);
        } catch (Throwable exception) {
            exception.printStackTrace();
            return new ArenaResult(task.gameId(), ArenaResult.Outcome.ERROR, -1, -1, 0,
                    TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - started), exception.toString());
        }
    }

    private static int sideboardCards(final Deck original, final Deck current) {
        final List<PaperCard> unchanged = original.getMain().toFlatList();
        int changed = 0;
        for (final PaperCard card : current.getMain().toFlatList()) {
            if (!unchanged.remove(card)) { changed++; }
        }
        return changed;
    }
}
