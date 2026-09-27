package forge.gamemodes.aisimulation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class ArenaExport {
    private ArenaExport() { }

    public static Path export(final ArenaStore store) throws IOException {
        final Path directory = Files.createTempDirectory(store.directory(), "export-");
        final ArenaConfiguration configuration = store.configuration();
        final List<ArenaResult> results = store.results();
        final ArenaStandings standings = new ArenaStandings(configuration, results);
        ArenaStore.writeJson(directory.resolve("run.json"), configuration);
        try (BufferedWriter output = Files.newBufferedWriter(directory.resolve("standings.csv"), StandardCharsets.UTF_8)) {
            row(output, "Deck", "Source", "AI", "Wins", "Losses", "Draws", "Timeouts", "Errors", "Score", "WinRate", "AverageTurns", "AverageSeconds");
            for (final int entrant : standings.ranking()) {
                final ArenaConfiguration.Entrant entry = configuration.entrants().get(entrant);
                final ArenaStandings.Score score = standings.total(entrant);
                row(output, entry.name(), entry.source(), entry.profile(), score.wins, score.losses, score.draws, score.timeouts,
                        score.errors, number(score.score()), number(score.winRate()), number(score.averageTurns()), number(score.averageSeconds()));
            }
        }
        try (BufferedWriter output = Files.newBufferedWriter(directory.resolve("matchups.csv"), StandardCharsets.UTF_8)) {
            row(output, "Deck", "Opponent", "Wins", "Losses", "Draws", "Timeouts", "Errors", "Score");
            for (int left = 0; left < configuration.entrants().size(); left++) {
                for (int right = 0; right < configuration.entrants().size(); right++) {
                    if (left == right) { continue; }
                    final ArenaStandings.Score score = standings.pairing(left, right);
                    row(output, configuration.entrants().get(left).source(), configuration.entrants().get(right).source(),
                            score.wins, score.losses, score.draws, score.timeouts, score.errors, number(score.score()));
                }
            }
        }
        try (BufferedWriter output = Files.newBufferedWriter(directory.resolve("matches.csv"), StandardCharsets.UTF_8)) {
            row(output, "Match", "DeckA", "DeckB", "Outcome", "Winner", "StartingPlayer", "Seed", "Turns", "Milliseconds", "Detail");
            for (final ArenaResult result : results) {
                final ArenaSchedule.Task task = ArenaSchedule.task(configuration, result.gameId());
                row(output, result.gameId(), configuration.entrants().get(task.left()).source(), configuration.entrants().get(task.right()).source(),
                        result.outcome().name(), entrant(configuration, result.winner()), entrant(configuration, result.startingPlayer()),
                        task.seed(), result.turns(), result.durationMillis(), result.detail());
            }
        }
        try (BufferedWriter output = Files.newBufferedWriter(directory.resolve("games.csv"), StandardCharsets.UTF_8)) {
            row(output, "Match", "GameInMatch", "DeckA", "DeckB", "Outcome", "Winner", "StartingPlayer", "Turns", "Milliseconds",
                    "Detail", "DeckASideboardCards", "DeckBSideboardCards");
            for (final ArenaResult result : results) {
                final ArenaSchedule.Task task = ArenaSchedule.task(configuration, result.gameId());
                final List<ArenaResult.GameResult> games = result.games().isEmpty()
                        ? List.of(new ArenaResult.GameResult(result.outcome(), result.winner(), result.startingPlayer(),
                            result.turns(), result.durationMillis(), result.detail())) : result.games();
                int number = 0;
                for (final ArenaResult.GameResult game : games) {
                    row(output, result.gameId(), ++number, configuration.entrants().get(task.left()).source(),
                            configuration.entrants().get(task.right()).source(), game.outcome().name(),
                            entrant(configuration, game.winner()), entrant(configuration, game.startingPlayer()),
                            game.turns(), game.durationMillis(), game.detail(), game.leftSideboardCards(), game.rightSideboardCards());
                }
            }
        }
        return directory;
    }

    private static String entrant(final ArenaConfiguration configuration, final int entrant) {
        return entrant == -1 ? "" : configuration.entrants().get(entrant).source();
    }

    private static Object number(final double number) { return Double.isFinite(number) ? number : ""; }

    private static void row(final BufferedWriter output, final Object... values) throws IOException {
        for (int column = 0; column < values.length; column++) {
            if (column > 0) { output.write(','); }
            String value = values[column] == null ? "" : values[column].toString();
            if (values[column] instanceof String && !value.isEmpty() && "=+-@\t\r\n".indexOf(value.charAt(0)) >= 0) {
                value = "'" + value;
            }
            output.write('"');
            output.write(value.replace("\"", "\"\""));
            output.write('"');
        }
        output.newLine();
    }
}
