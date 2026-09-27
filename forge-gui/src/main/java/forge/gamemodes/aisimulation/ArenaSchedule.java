package forge.gamemodes.aisimulation;

public final class ArenaSchedule {
    private ArenaSchedule() { }

    public record Task(int gameId, int left, int right, int firstToChoose, long seed) { }

    public static Task task(final ArenaConfiguration configuration, final int gameId) {
        if (gameId < 0 || gameId >= configuration.totalGames()) {
            throw new IllegalArgumentException("Unknown scheduled game: " + gameId);
        }
        final int left;
        final int right;
        final int first;
        final int seedIndex;
        if (configuration.isComparison()) {
            final int comparison = gameId / configuration.comparisonProfiles();
            final int opponent = comparison / configuration.gamesPerPair();
            left = gameId % configuration.comparisonProfiles();
            right = configuration.comparisonProfiles() + opponent;
            first = ((comparison % configuration.gamesPerPair() + opponent) & 1) == 0 ? left : right;
            seedIndex = comparison;
        } else {
            int pair = gameId / configuration.gamesPerPair();
            int candidate = 0;
            while (pair >= configuration.entrants().size() - candidate - 1) {
                pair -= configuration.entrants().size() - candidate - 1;
                candidate++;
            }
            left = candidate;
            right = left + pair + 1;
            first = ((gameId % configuration.gamesPerPair() + left + right) & 1) == 0 ? left : right;
            seedIndex = gameId;
        }
        long seed = configuration.seed() + 0x9E3779B97F4A7C15L * (seedIndex + 1L);
        seed = (seed ^ (seed >>> 30)) * 0xBF58476D1CE4E5B9L;
        seed = (seed ^ (seed >>> 27)) * 0x94D049BB133111EBL;
        return new Task(gameId, left, right, first, seed ^ (seed >>> 31));
    }
}
