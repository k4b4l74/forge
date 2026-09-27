package forge.ai;

import forge.game.card.Card;
import forge.game.player.Player;
import forge.game.zone.ZoneType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class AiDecisionTrace implements AutoCloseable {
    public record Opponent(String player, int life, int handSize, List<String> battlefield, List<String> graveyard) { }
    public record Entry(int turn, String phase, String player, String profile, String decision, String detail,
                        int life, List<String> hand, List<String> battlefield, List<String> graveyard,
                        List<Opponent> opponents) { }

    private static final ThreadLocal<Consumer<Entry>> CURRENT = new ThreadLocal<>();
    private final Consumer<Entry> previous;

    private AiDecisionTrace(final Consumer<Entry> consumer) {
        previous = CURRENT.get();
        CURRENT.set(consumer);
    }

    public static AiDecisionTrace open(final Consumer<Entry> consumer) { return new AiDecisionTrace(consumer); }

    public static boolean active() { return CURRENT.get() != null; }

    public static List<String> cards(final Iterable<Card> cards) {
        final List<String> names = new ArrayList<>();
        for (final Card card : cards) { names.add(card.isFaceDown() ? "Face-down card" : card.getName()); }
        return names;
    }

    public static void record(final Player player, final String decision, final String detail) {
        final Consumer<Entry> consumer = CURRENT.get();
        if (consumer == null) { return; }
        final List<Opponent> opponents = new ArrayList<>();
        for (final Player opponent : player.getOpponents()) {
            opponents.add(new Opponent(opponent.getName(), opponent.getLife(), opponent.getCardsIn(ZoneType.Hand).size(),
                    cards(opponent.getCardsIn(ZoneType.Battlefield)), cards(opponent.getCardsIn(ZoneType.Graveyard))));
        }
        consumer.accept(new Entry(player.getGame().getPhaseHandler().getTurn(),
                String.valueOf(player.getGame().getPhaseHandler().getPhase()), player.getName(),
                player.getLobbyPlayer() instanceof LobbyPlayerAi ai ? ai.getAiProfile() : "Human",
                decision, detail, player.getLife(), cards(player.getCardsIn(ZoneType.Hand)),
                cards(player.getCardsIn(ZoneType.Battlefield)), cards(player.getCardsIn(ZoneType.Graveyard)), opponents));
    }

    @Override
    public void close() {
        if (previous == null) { CURRENT.remove(); }
        else { CURRENT.set(previous); }
    }
}
