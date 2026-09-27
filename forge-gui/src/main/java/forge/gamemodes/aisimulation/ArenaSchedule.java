package forge.gamemodes.aisimulation;

public final class ArenaSchedule {
    private ArenaSchedule() { }

    public record Task(int gameId, int left, int right, int firstToChoose, long seed) { }

    public static Task task(final ArenaConfiguration configuration, final int gameId) {
        if (gameId < 0 || gameId >= configuration.totalGames()) {
            throw new IllegalArgumentException("Unknown scheduled game: " + gameId);
        }
        int pair = gameId / configuration.gamesPerPair();
        int left = 0;
        while (pair >= configuration.entrants().size() - left - 1) {
            pair -= configuration.entrants().size() - left - 1;
            left++;
        }
        final int right = left + pair + 1;
        final int first = ((gameId % configuration.gamesPerPair() + left + right) & 1) == 0 ? left : right;
        long seed = configuration.seed() + 0x9E3779B97F4A7C15L * (gameId + 1L);
        seed = (seed ^ (seed >>> 30)) * 0xBF58476D1CE4E5B9L;
        seed = (seed ^ (seed >>> 27)) * 0x94D049BB133111EBL;
        return new Task(gameId, left, right, first, seed ^ (seed >>> 31));
    }
}
