package forge.gamemodes.aisimulation;

import java.util.List;

public record ArenaResult(int gameId, Outcome outcome, int winner, int startingPlayer, int turns,
                          long durationMillis, String detail, List<GameResult> games) {
    public enum Outcome { WIN, DRAW, TIMEOUT, ERROR }

    public record GameResult(Outcome outcome, int winner, int startingPlayer, int turns,
                             long durationMillis, String detail, int leftSideboardCards, int rightSideboardCards) {
        public GameResult(final Outcome outcome, final int winner, final int startingPlayer, final int turns,
                          final long durationMillis, final String detail) {
            this(outcome, winner, startingPlayer, turns, durationMillis, detail, 0, 0);
        }
    }

    public ArenaResult {
        games = games == null ? List.of() : List.copyOf(games);
    }

    public ArenaResult(final int gameId, final Outcome outcome, final int winner, final int startingPlayer,
                       final int turns, final long durationMillis, final String detail) {
        this(gameId, outcome, winner, startingPlayer, turns, durationMillis, detail, List.of());
    }

    public void validate(final ArenaConfiguration configuration) {
        final ArenaSchedule.Task task = ArenaSchedule.task(configuration, gameId);
        if (outcome == null || turns < 0 || durationMillis < 0
                || (outcome == Outcome.WIN ? winner != task.left() && winner != task.right() : winner != -1)
                || startingPlayer != -1 && startingPlayer != task.left() && startingPlayer != task.right()) {
            throw new IllegalArgumentException("Invalid result for game " + gameId);
        }
        if (games.isEmpty()) {
            if (configuration.gamesPerMatch() == 3 && isValidGame()) {
                throw new IllegalArgumentException("BO3 result requires individual games");
            }
            return;
        }
        int leftWins = 0;
        int rightWins = 0;
        int totalTurns = 0;
        long gameMillis = 0;
        final int required = configuration.gamesPerMatch() / 2 + 1;
        for (final GameResult game : games) {
            if (leftWins >= required || rightWins >= required
                    || game.outcome() != Outcome.WIN && game.outcome() != Outcome.DRAW
                    || (game.outcome() == Outcome.WIN ? game.winner() != task.left() && game.winner() != task.right() : game.winner() != -1)
                    || game.startingPlayer() != task.left() && game.startingPlayer() != task.right()
                    || game.turns() < 0 || game.durationMillis() < 0
                    || game.leftSideboardCards() < 0 || game.rightSideboardCards() < 0
                    || game == games.get(0) && (game.leftSideboardCards() != 0 || game.rightSideboardCards() != 0)) {
                throw new IllegalArgumentException("Invalid individual game in match " + gameId);
            }
            if (game.winner() == task.left()) { leftWins++; }
            if (game.winner() == task.right()) { rightWins++; }
            totalTurns = Math.addExact(totalTurns, game.turns());
            gameMillis = Math.addExact(gameMillis, game.durationMillis());
        }
        if (turns != totalTurns || durationMillis < gameMillis || startingPlayer != games.get(0).startingPlayer()
                || configuration.gamesPerMatch() == 1 && games.size() != 1
                || isValidGame() && (outcome == Outcome.WIN
                    ? winner != (leftWins == required ? task.left() : rightWins == required ? task.right() : -1)
                    : configuration.gamesPerMatch() != 1 || games.get(0).outcome() != Outcome.DRAW)) {
            throw new IllegalArgumentException("Match summary disagrees with games " + gameId);
        }
    }

    public boolean isValidGame() {
        return outcome == Outcome.WIN || outcome == Outcome.DRAW;
    }
}
