package forge.ai;

import forge.game.Game;
import forge.game.card.Card;
import forge.game.card.CardCollection;
import forge.game.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public final class LearnedGameplay {
    public static final int MAX_ACTIONS = 128;

    public interface Policy {
        int choose(Player player, String kind, List<List<Card>> options, int teacher);
        default void skipped(Player player, String reason) { }
    }

    private static final Map<Game, Policy> POLICIES = Collections.synchronizedMap(new WeakHashMap<>());

    private LearnedGameplay() { }

    public static AutoCloseable install(final Game game, final Policy policy) {
        synchronized (POLICIES) {
            if (POLICIES.containsKey(game)) { throw new IllegalStateException("A gameplay policy is already installed"); }
            POLICIES.put(game, policy);
        }
        return () -> POLICIES.remove(game, policy);
    }

    public static Card target(final Player player, final List<Card> candidates, final Card teacher) {
        final Policy policy = POLICIES.get(player.getGame());
        if (policy == null || teacher == null || candidates.size() < 2) { return teacher; }
        if (candidates.size() > MAX_ACTIONS) {
            policy.skipped(player, "target-action-limit");
            return teacher;
        }
        final int baseline = candidates.indexOf(teacher);
        if (baseline < 0) { return teacher; }
        final List<List<Card>> options = candidates.stream().map(List::of).toList();
        final int choice = policy.choose(player, "reanimate-proposal", options, baseline);
        return choice >= 0 && choice < candidates.size() ? candidates.get(choice) : teacher;
    }

    public static CardCollection discard(final Player player, final CardCollection candidates, final CardCollection teacher) {
        final Policy policy = POLICIES.get(player.getGame());
        if (policy == null || teacher.isEmpty() || candidates.size() <= teacher.size()) { return teacher; }
        long combinations = 1;
        final int count = Math.min(teacher.size(), candidates.size() - teacher.size());
        for (int index = 1; index <= count; index++) {
            combinations = combinations * (candidates.size() - count + index) / index;
            if (combinations > MAX_ACTIONS) {
                policy.skipped(player, "discard-action-limit");
                return teacher;
            }
        }
        final List<List<Card>> options = new ArrayList<>();
        subsets(new ArrayList<>(candidates), teacher.size(), 0, new ArrayList<>(), options);
        int baseline = -1;
        for (int index = 0; index < options.size(); index++) {
            if (options.get(index).containsAll(teacher)) { baseline = index; break; }
        }
        if (baseline < 0) { return teacher; }
        final int choice = policy.choose(player, "discard-choice", options, baseline);
        return choice >= 0 && choice < options.size() && choice != baseline ? new CardCollection(options.get(choice)) : teacher;
    }

    private static void subsets(final List<Card> cards, final int remaining, final int start,
                                final List<Card> selected, final List<List<Card>> options) {
        if (remaining == 0) { options.add(List.copyOf(selected)); return; }
        for (int index = start; index <= cards.size() - remaining; index++) {
            selected.add(cards.get(index));
            subsets(cards, remaining - 1, index + 1, selected, options);
            selected.remove(selected.size() - 1);
        }
    }
}
