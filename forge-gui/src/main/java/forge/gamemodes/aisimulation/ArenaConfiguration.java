package forge.gamemodes.aisimulation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record ArenaConfiguration(int version, String id, String name, long createdAt, long seed,
                                 String fingerprint, String snapshotFingerprint, int gamesPerPair,
                                 int workers, int heapMegabytes, int timeoutSeconds, List<Entrant> entrants,
                                 int gamesPerMatch) {
    public static final int VERSION = 2;

    public ArenaConfiguration(final int version, final String id, final String name, final long createdAt,
                              final long seed, final String fingerprint, final String snapshotFingerprint,
                              final int gamesPerPair, final int workers, final int heapMegabytes,
                              final int timeoutSeconds, final List<Entrant> entrants) {
        this(version, id, name, createdAt, seed, fingerprint, snapshotFingerprint, gamesPerPair,
                workers, heapMegabytes, timeoutSeconds, entrants, 1);
    }

    public record Entrant(String name, String source, String profile) { }

    public ArenaConfiguration {
        if (version == 1 && gamesPerMatch == 0) { gamesPerMatch = 1; }
        if (version < 1 || version > VERSION || gamesPerMatch != 1 && gamesPerMatch != 3
                || version == 1 && gamesPerMatch != 1
                || id == null || !id.matches("[a-zA-Z0-9-]+") || name == null || name.isBlank()
                || fingerprint == null || snapshotFingerprint == null) {
            throw new IllegalArgumentException("Invalid AI Arena configuration");
        }
        entrants = List.copyOf(entrants);
        if (entrants.size() < 2 || gamesPerPair < 1 || workers < 1 || workers > 64
                || heapMegabytes < 256 || heapMegabytes > 32768 || timeoutSeconds < 1) {
            throw new IllegalArgumentException("Select at least two decks and positive game, worker and timeout settings");
        }
        final Set<String> sources = new HashSet<>();
        for (final Entrant entrant : entrants) {
            if (entrant.name() == null || entrant.source() == null || !sources.add(entrant.source())
                    || entrant.profile() == null || !entrant.profile().matches("[a-zA-Z0-9 _-]+")) {
                throw new IllegalArgumentException("Each entrant must have a distinct deck and a named AI profile");
            }
        }
        gameCount(entrants.size(), gamesPerPair);
    }

    public int totalGames() {
        return gameCount(entrants.size(), gamesPerPair);
    }

    public static int gameCount(final int decks, final int gamesPerPair) {
        final long total = Math.multiplyExact((long) decks * (decks - 1) / 2, gamesPerPair);
        if (decks < 2 || gamesPerPair < 1 || total > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Tournament size is outside the supported range");
        }
        return (int) total;
    }
}
