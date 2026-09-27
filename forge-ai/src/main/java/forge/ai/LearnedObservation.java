package forge.ai;

import forge.game.card.Card;
import forge.game.player.Player;
import forge.game.zone.ZoneType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class LearnedObservation {
    public static final int SCHEMA = 1;
    public static final int BUCKETS = 8192;
    public static final int MAX_FEATURES = 1024;

    private LearnedObservation() { }

    public static List<Integer> encode(final Player player, final String kind, final List<List<Card>> options) {
        final List<String> features = new ArrayList<>();
        features.add("kind=" + kind);
        features.add("phase=" + player.getGame().getPhaseHandler().getPhase());
        features.add("our-turn=" + (player.getGame().getPhaseHandler().getPlayerTurn() == player));
        features.add("turn=" + Math.min(30, player.getGame().getPhaseHandler().getTurn()));
        features.add("life=" + Math.max(-1, Math.min(40, player.getLife())));
        features.add("library-size=" + player.getCardsIn(ZoneType.Library).size());
        cards(features, "hand", player.getCardsIn(ZoneType.Hand), false);
        cards(features, "board", player.getCardsIn(ZoneType.Battlefield), true);
        cards(features, "grave", player.getCardsIn(ZoneType.Graveyard), false);
        int opponentIndex = 0;
        for (final Player opponent : player.getOpponents()) {
            final String prefix = "opponent-" + opponentIndex++;
            features.add(prefix + ":life=" + Math.max(-1, Math.min(40, opponent.getLife())));
            features.add(prefix + ":hand-size=" + opponent.getCardsIn(ZoneType.Hand).size());
            features.add(prefix + ":library-size=" + opponent.getCardsIn(ZoneType.Library).size());
            cards(features, prefix + ":board", opponent.getCardsIn(ZoneType.Battlefield), true);
            cards(features, prefix + ":grave", opponent.getCardsIn(ZoneType.Graveyard), false);
        }
        for (int index = 0; index < options.size(); index++) {
            final String prefix = "option-" + index;
            for (final Card card : options.get(index)) {
                features.add(prefix + ":owner=" + (card.getOwner() == player));
            }
            cards(features, prefix, options.get(index), false);
        }
        return features.stream().map(feature -> Math.floorMod(feature.hashCode(), BUCKETS)).distinct().sorted().toList();
    }

    private static void cards(final List<String> features, final String prefix, final Iterable<Card> cards, final boolean board) {
        final Map<String, Integer> counts = new HashMap<>();
        for (final Card card : cards) {
            final String name = card.isFaceDown() ? "face-down" : card.getName();
            String description = prefix + ":" + name;
            if (board) {
                description += ":tapped=" + card.isTapped() + ":sick=" + card.isSick()
                        + ":pt=" + card.getNetPower() + "/" + card.getNetToughness() + ":damage=" + card.getDamage();
                card.getCounters().entrySet().forEach(entry -> features.add(prefix + ":" + name + ":counter="
                        + entry.getElement() + ":" + Math.min(10, entry.getCount())));
            }
            counts.merge(description, 1, Integer::sum);
        }
        counts.forEach((description, count) -> {
            for (int index = 1; index <= Math.min(8, count); index++) { features.add(description + ":count>=" + index); }
        });
    }
}
