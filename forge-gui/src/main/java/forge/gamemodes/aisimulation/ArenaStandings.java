package forge.gamemodes.aisimulation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class ArenaStandings {
    public static final class Score {
        public int wins;
        public int losses;
        public int draws;
        public int timeouts;
        public int errors;
        public long turns;
        public long durationMillis;

        public int validGames() { return wins + losses + draws; }
        public double score() { return validGames() == 0 ? Double.NaN : (wins + draws * 0.5) / validGames(); }
        public double winRate() { return validGames() == 0 ? Double.NaN : (double) wins / validGames(); }
        public double averageTurns() { return validGames() == 0 ? Double.NaN : (double) turns / validGames(); }
        public double averageSeconds() { return validGames() == 0 ? Double.NaN : durationMillis / 1000.0 / validGames(); }

        private void add(final ArenaResult result, final int entrant) {
            switch (result.outcome()) {
                case WIN -> { if (result.winner() == entrant) { wins++; } else { losses++; } }
                case DRAW -> draws++;
                case TIMEOUT -> timeouts++;
                case ERROR -> errors++;
            }
            if (result.isValidGame()) {
                turns += result.turns();
                durationMillis += result.durationMillis();
            }
        }
    }

    private final Score[] totals;
    private final Score[][] pairings;

    public ArenaStandings(final ArenaConfiguration configuration, final Collection<ArenaResult> results) {
        totals = new Score[configuration.entrants().size()];
        pairings = new Score[totals.length][totals.length];
        for (int row = 0; row < totals.length; row++) {
            totals[row] = new Score();
            for (int column = 0; column < totals.length; column++) {
                pairings[row][column] = new Score();
            }
        }
        final Set<Integer> seen = new HashSet<>();
        for (final ArenaResult result : results) {
            result.validate(configuration);
            if (!seen.add(result.gameId())) {
                continue;
            }
            final ArenaSchedule.Task task = ArenaSchedule.task(configuration, result.gameId());
            totals[task.left()].add(result, task.left());
            totals[task.right()].add(result, task.right());
            pairings[task.left()][task.right()].add(result, task.left());
            pairings[task.right()][task.left()].add(result, task.right());
        }
    }

    public Score total(final int entrant) { return totals[entrant]; }
    public Score pairing(final int left, final int right) { return pairings[left][right]; }

    public List<Integer> ranking() {
        final List<Integer> ranking = new ArrayList<>();
        for (int entrant = 0; entrant < totals.length; entrant++) {
            ranking.add(entrant);
        }
        ranking.sort(Comparator.<Integer>comparingDouble(entrant ->
                totals[entrant].validGames() == 0 ? -1 : totals[entrant].score()).reversed()
                .thenComparingInt(Integer::intValue));
        return ranking;
    }
}
