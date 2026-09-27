package forge.gamemodes.aisimulation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record ArenaConfiguration(int version, String id, String name, long createdAt, long seed,
                                 String fingerprint, String snapshotFingerprint, int gamesPerPair,
                                 int workers, int heapMegabytes, int timeoutSeconds, List<Entrant> entrants,
                                 int gamesPerMatch, int comparisonProfiles) {
    public static final int VERSION = 3;

    public ArenaConfiguration(final int version, final String id, final String name, final long createdAt,
                              final long seed, final String fingerprint, final String snapshotFingerprint,
                              final int gamesPerPair, final int workers, final int heapMegabytes,
                              final int timeoutSeconds, final List<Entrant> entrants) {
        this(version, id, name, createdAt, seed, fingerprint, snapshotFingerprint, gamesPerPair,
                workers, heapMegabytes, timeoutSeconds, entrants, 1);
    }

    public ArenaConfiguration(final int version, final String id, final String name, final long createdAt,
                              final long seed, final String fingerprint, final String snapshotFingerprint,
                              final int gamesPerPair, final int workers, final int heapMegabytes,
                              final int timeoutSeconds, final List<Entrant> entrants, final int gamesPerMatch) {
        this(version, id, name, createdAt, seed, fingerprint, snapshotFingerprint, gamesPerPair,
                workers, heapMegabytes, timeoutSeconds, entrants, gamesPerMatch, 0);
    }

    public record Entrant(String name, String source, String profile) { }

    public ArenaConfiguration {
        if (version == 1 && gamesPerMatch == 0) { gamesPerMatch = 1; }
        if (version < 1 || version > VERSION || gamesPerMatch != 1 && gamesPerMatch != 3
                || version == 1 && gamesPerMatch != 1
                || version < 3 && comparisonProfiles != 0
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
        final Set<String> profiles = new HashSet<>();
        gameCount(entrants.size(), gamesPerPair, comparisonProfiles);
        for (int index = 0; index < entrants.size(); index++) {
            final Entrant entrant = entrants.get(index);
            if (entrant.name() == null || entrant.source() == null
                    || entrant.profile() == null || !entrant.profile().matches("[a-zA-Z0-9 _-]+")) {
                throw new IllegalArgumentException("Each entrant must have a distinct deck and a named AI profile");
            }
            if (index < comparisonProfiles) {
                if (!entrant.source().equals(entrants.get(0).source())
                        || !entrant.name().equals(entrants.get(0).name()) || !profiles.add(entrant.profile())) {
                    throw new IllegalArgumentException("Compare distinct AI profiles using the same test deck");
                }
            } else if (!sources.add(entrant.source())) {
                throw new IllegalArgumentException("Each opponent must have a distinct deck");
            }
        }
    }

    public int totalGames() {
        return gameCount(entrants.size(), gamesPerPair, comparisonProfiles);
    }

    public boolean isComparison() { return comparisonProfiles > 0; }

    public String entrantLabel(final int index) {
        final Entrant entrant = entrants.get(index);
        return isComparison() ? entrant.source() + " [" + entrant.profile() + "]" : entrant.source();
    }

    public boolean hasPairing(final int left, final int right) {
        return left != right && (!isComparison() || (left < comparisonProfiles) != (right < comparisonProfiles));
    }

    public static int gameCount(final int decks, final int gamesPerPair) {
        return gameCount(decks, gamesPerPair, 0);
    }

    public static int gameCount(final int decks, final int gamesPerPair, final int comparisonProfiles) {
        if (decks < 2 || gamesPerPair < 1) {
            throw new IllegalArgumentException("Select at least two entrants and a positive number of matches");
        }
        if (comparisonProfiles < 0 || comparisonProfiles == 1 || comparisonProfiles >= decks) {
            throw new IllegalArgumentException("Select at least two AI profiles and one opponent for an AI comparison");
        }
        final long pairings = comparisonProfiles == 0 ? (long) decks * (decks - 1) / 2
                : (long) comparisonProfiles * (decks - comparisonProfiles);
        if (pairings > Integer.MAX_VALUE / gamesPerPair) {
            throw new IllegalArgumentException("Tournament size is outside the supported range");
        }
        return (int) (pairings * gamesPerPair);
    }
}
