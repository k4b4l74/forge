package forge.ai;

import com.google.common.eventbus.Subscribe;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import forge.card.ColorSet;
import forge.card.MagicColor;
import forge.deck.Deck;
import forge.deck.DeckSection;
import forge.game.Match;
import forge.game.card.Card;
import forge.game.card.CardCollection;
import forge.game.card.CardCollectionView;
import forge.game.event.GameEventCardChangeZone;
import forge.game.player.Player;
import forge.game.spellability.SpellAbility;
import forge.game.zone.ZoneType;
import forge.item.PaperCard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class OldschoolReanimatorAi {
    private OldschoolReanimatorAi() { }

    public static boolean enabled(final Player player) {
        return AiProfileUtil.getBoolProperty(player, AiProps.OS_REANIMATOR);
    }

    private static boolean named(final Iterable<Card> cards, final String name) {
        for (final Card card : cards) {
            if (name.equals(card.getName())) { return true; }
        }
        return false;
    }

    private static boolean payoff(final Card card) {
        return card.isCreature() && card.getCMC() >= 4;
    }

    private static boolean source(final Card card) {
        return !card.getManaAbilities().isEmpty() && (card.isLand() || card.getCMC() == 0);
    }

    private static int handScore(final CardCollection hand, final boolean versionTwo) {
        int sources = 0;
        int actions = 0;
        boolean black = false;
        boolean target = false;
        int targets = 0;
        int colors = 0;
        for (final Card card : hand) {
            if (source(card)) {
                sources++;
                black |= card.getManaAbilities().stream().anyMatch(ability -> ability.canProduce("B"));
                for (final byte color : MagicColor.WUBRG) {
                    if (card.getManaAbilities().stream().anyMatch(ability -> ability.canProduce(MagicColor.toShortString(color)))) {
                        colors |= color;
                    }
                }
            } else if (!card.isLand() && !"Sol Ring".equals(card.getName())) {
                actions++;
            }
            target |= payoff(card);
            if (payoff(card)) { targets++; }
        }
        if (sources > 0 && named(hand, "Sol Ring")) { sources++; }
        if (sources == 0 || actions == 0) { return -1000; }
        if (sources < 2 && hand.size() > 4) { return -500; }
        final boolean reanimate = black && target && named(hand, "Animate Dead") && named(hand, "Bazaar of Baghdad");
        boolean development = false;
        for (final Card card : hand) {
            if (!card.isLand() && !source(card) && !"Animate Dead".equals(card.getName())
                    && !"Sol Ring".equals(card.getName()) && card.getCMC() <= sources + 1
                    && card.getColor().hasNoColorsExcept(ColorSet.fromMask(colors))
                    && (!versionTwo || !"Copy Artifact".equals(card.getName())
                    || hand.anyMatch(candidate -> candidate.isArtifact() && (source(candidate)
                    || "Sol Ring".equals(candidate.getName()))))) { development = true; }
        }
        if (!reanimate && !development) { return -400; }
        int score = 30 * Math.min(sources, 3) - 45 * Math.max(0, sources - 3) + 10 * actions - 20 * Math.max(0, targets - 1);
        if (reanimate) { score += 200; }
        if (named(hand, "Ancestral Recall") || named(hand, "Demonic Tutor")) { score += 20; }
        if (target && !named(hand, "Animate Dead") && sources < 3) { score -= 25; }
        return score;
    }

    public static CardCollection bottomCards(final CardCollectionView hand, final int count) {
        return bottomCards(hand, count, false);
    }

    public static CardCollection bottomCards(final Player player, final CardCollectionView hand, final int count) {
        return bottomCards(hand, count, AiProfileUtil.getBoolProperty(player, AiProps.OS_REANIMATOR_V2));
    }

    private static CardCollection bottomCards(final CardCollectionView hand, final int count, final boolean versionTwo) {
        final List<Card> cards = new ArrayList<>();
        hand.forEach(cards::add);
        if (cards.size() > 10 || count < 0 || count > cards.size()) { return null; }
        CardCollection best = null;
        int bestScore = Integer.MIN_VALUE;
        for (int mask = 0; mask < 1 << cards.size(); mask++) {
            if (Integer.bitCount(mask) != cards.size() - count) { continue; }
            final CardCollection kept = new CardCollection();
            final CardCollection bottom = new CardCollection();
            for (int index = 0; index < cards.size(); index++) {
                if ((mask & 1 << index) != 0) { kept.add(cards.get(index)); }
                else { bottom.add(cards.get(index)); }
            }
            final int score = handScore(kept, versionTwo);
            if (score > bestScore) { bestScore = score; best = bottom; }
        }
        return best;
    }

    public static boolean keepHand(final CardCollectionView hand, final int cardsToReturn) {
        return keepHand(hand, cardsToReturn, false);
    }

    public static boolean keepHand(final Player player, final int cardsToReturn) {
        return keepHand(player.getCardsIn(ZoneType.Hand), cardsToReturn,
                AiProfileUtil.getBoolProperty(player, AiProps.OS_REANIMATOR_V2));
    }

    private static boolean keepHand(final CardCollectionView hand, final int cardsToReturn, final boolean versionTwo) {
        if (hand.size() - cardsToReturn <= 3) { return true; }
        final CardCollection bottom = bottomCards(hand, cardsToReturn, versionTwo);
        if (bottom == null) { return true; }
        final CardCollection kept = new CardCollection(hand);
        kept.removeAll(bottom);
        return handScore(kept, versionTwo) >= 0;
    }

    public static boolean useBazaar(final Player player) {
        final CardCollectionView hand = player.getCardsIn(ZoneType.Hand);
        if (player.getCardsIn(ZoneType.Library).size() < 3 || hand.size() < 3) { return false; }
        return hand.size() >= 5 || named(hand, "Animate Dead") && hand.anyMatch(OldschoolReanimatorAi::payoff);
    }

    public static CardCollection discard(final Player player, final CardCollection valid, final int count) {
        final CardCollection remaining = new CardCollection(valid);
        final CardCollection result = new CardCollection();
        boolean loaded = player.getCardsIn(ZoneType.Graveyard).anyMatch(OldschoolReanimatorAi::payoff);
        while (result.size() < count && !remaining.isEmpty()) {
            Card choice = null;
            int best = Integer.MIN_VALUE;
            for (final Card card : remaining) {
                int score = card.getCMC();
                if (!loaded && payoff(card) && named(remaining, "Animate Dead")) { score += 150; }
                if ("Animate Dead".equals(card.getName())) { score -= 120; }
                if (source(card)) { score -= 80; }
                if (card.isLand() && (player.getLandsInPlay().size() >= 4
                        || remaining.stream().filter(OldschoolReanimatorAi::source).count() > 3)) { score += 120; }
                if ("Bazaar of Baghdad".equals(card.getName()) && player.isCardInPlay("Bazaar of Baghdad")) { score += 100; }
                if (score > best) { best = score; choice = card; }
            }
            result.add(choice);
            remaining.remove(choice);
            loaded |= payoff(choice);
        }
        return LearnedGameplay.discard(player, valid, result);
    }

    public static Card reanimationTarget(final Player player, final List<Card> legalChoices) {
        Card best = null;
        int bestScore = Integer.MIN_VALUE;
        final boolean robotInHand = named(player.getCardsIn(ZoneType.Hand), "Triskelion");
        final boolean threatened = player.getLife() <= 8 || player.getOpponents().stream()
                .anyMatch(opponent -> opponent.getCreaturesInPlay().size() > player.getCreaturesInPlay().size());
        for (final Card card : legalChoices) {
            int score = ComputerUtilCard.evaluateCreature(card);
            if ("Triskelion".equals(card.getName())) { score = threatened ? 500 : 350; }
            if ("Rasputin Dreamweaver".equals(card.getName())) {
                score = robotInHand && !player.isCardInPlay("Rasputin Dreamweaver") ? 450 : 180;
            }
            if (score > bestScore) { best = card; bestScore = score; }
        }
        final Card selected = LearnedGameplay.target(player, legalChoices, best);
        if (AiDecisionTrace.active()) {
            AiDecisionTrace.record(player, "reanimate-target", "candidates=" + AiDecisionTrace.cards(legalChoices)
                    + "; chosen=" + (selected == null ? "none" : selected.getName()));
        }
        return selected;
    }

    public static Card copyTarget(final Iterable<Card> legalChoices) {
        for (final Card card : legalChoices) {
            if ("Triskelion".equals(card.getName()) && card.isArtifact()) { return card; }
        }
        return null;
    }

    public static boolean targetDreamsFinish(final Player player, final SpellAbility ability) {
        if (!enabled(player) || !ability.usesTargeting()
                || !List.of("Ancestral Recall", "Braingeyser").contains(ability.getHostCard().getName())) { return false; }
        final CardCollection dreams = new CardCollection();
        for (final Card card : player.getCardsIn(ZoneType.Battlefield)) {
            if ("Underworld Dreams".equals(card.getName()) && !card.isPhasedOut()) { dreams.add(card); }
        }
        if (dreams.isEmpty()) { return false; }
        final boolean geyser = "Braingeyser".equals(ability.getHostCard().getName());
        final Integer previousX = ability.getXManaCostPaid();
        final int maximum = geyser ? ComputerUtilCost.setMaxXValue(ability, player, false) : 3;
        for (final Player opponent : player.getOpponents()) {
            if (!ability.canTarget(opponent) || !opponent.canDraw() || !opponent.canLoseLife()
                    || opponent.cantLose() || opponent.cantLoseForZeroOrLessLife()) { continue; }
            final int perDraw = dreams.stream().mapToInt(card -> ComputerUtilCombat.predictDamageTo(opponent, 1, card, false)).sum();
            if (perDraw <= 0) { continue; }
            final int needed = (opponent.getLife() + perDraw - 1) / perDraw;
            final int draws = geyser ? needed : 3;
            if (needed <= maximum && draws > 0 && opponent.getCardsIn(ZoneType.Library).size() >= draws) {
                ability.resetTargets();
                ability.getTargets().add(opponent);
                if (geyser) { ability.setXManaCostPaid(draws); }
                return true;
            }
        }
        if (geyser) { ability.setXManaCostPaid(previousX); }
        return false;
    }

    public static List<PaperCard> sideboard(final Deck original, final Collection<PaperCard> seen) {
        final List<PaperCard> main = original.getMain().toFlatList();
        final List<PaperCard> side = original.getOrCreate(DeckSection.Sideboard).toFlatList();
        final Map<String, Integer> desired = new LinkedHashMap<>();
        final boolean red = seen.stream().anyMatch(card -> !card.getRules().getType().isLand() && card.getRules().getColor().hasRed());
        final boolean blue = seen.stream().anyMatch(card -> !card.getRules().getType().isLand() && card.getRules().getColor().hasBlue());
        final boolean creatures = seen.stream().anyMatch(card -> card.getRules().getType().isCreature());
        final boolean permanents = seen.stream().anyMatch(card -> card.getRules().getType().isArtifact() || card.getRules().getType().isEnchantment());
        if (red) { desired.put("Blue Elemental Blast", 2); desired.put("Circle of Protection: Red", 2); }
        if (blue) { desired.put("Red Elemental Blast", 2); }
        if (permanents) { desired.put("Disenchant", 2); }
        if (creatures) { desired.put("Swords to Plowshares", 3); }
        final boolean slowControl = seen.stream().anyMatch(card -> List.of("Counterspell", "Mana Drain", "The Abyss", "Jayemdae Tome").contains(card.getName()));
        final long blackLands = main.stream().filter(card -> List.of("Swamp", "Bayou", "Underground Sea", "City of Brass", "Badlands", "Scrubland").contains(card.getName())).count();
        if (slowControl && !red && !creatures && blackLands >= 8
                && main.stream().anyMatch(card -> List.of("Wheel of Fortune", "Timetwister").contains(card.getName()))) {
            desired.put("Underworld Dreams", 2);
        }
        int swaps = 0;
        for (final Map.Entry<String, Integer> entry : desired.entrySet()) {
            int needed = entry.getValue() - (int) main.stream().filter(card -> entry.getKey().equals(card.getName())).count();
            for (final PaperCard incoming : new ArrayList<>(side)) {
                if (swaps >= 6 || needed <= 0 || !entry.getKey().equals(incoming.getName())) { continue; }
                final PaperCard outgoing = main.stream().filter(card -> cutPriority(card, main) > 0)
                        .max(Comparator.comparingInt(card -> cutPriority(card, main))).orElse(null);
                if (outgoing == null) { break; }
                main.remove(outgoing);
                main.add(incoming);
                side.remove(incoming);
                side.add(outgoing);
                needed--;
                swaps++;
            }
        }
        return main;
    }

    private static int cutPriority(final PaperCard card, final List<PaperCard> main) {
        final long copies = main.stream().filter(candidate -> candidate.getName().equals(card.getName())).count();
        return switch (card.getName()) {
            case "Recall" -> 100;
            case "Psionic Blast" -> 90;
            case "Erhnam Djinn" -> copies > 2 ? 80 : 0;
            case "Rasputin Dreamweaver" -> copies > 2 ? 70 : 0;
            case "Copy Artifact" -> copies > 2 ? 60 : 0;
            default -> 0;
        };
    }

    public static final class MatchKnowledge {
        private Match match;
        private Deck original;
        private final Map<String, PaperCard> seen = new LinkedHashMap<>();

        public void begin(final Match current) {
            if (match != current) { match = current; original = null; seen.clear(); }
        }

        public Collection<PaperCard> seenCards() { return List.copyOf(seen.values()); }

        public List<PaperCard> sideboard(final Deck deck, final Match current) {
            begin(current);
            if (original == null || !pool(original).equals(pool(deck))) { original = (Deck) deck.copyTo(deck.getName()); }
            return OldschoolReanimatorAi.sideboard(original, seen.values());
        }

        private static Multiset<PaperCard> pool(final Deck deck) {
            final Multiset<PaperCard> cards = HashMultiset.create(deck.getMain().toFlatList());
            cards.addAll(deck.getOrCreate(DeckSection.Sideboard).toFlatList());
            return cards;
        }

        public void observe(final Player viewer) {
            for (final ZoneType zone : List.of(ZoneType.Battlefield, ZoneType.Graveyard, ZoneType.Exile, ZoneType.Stack)) {
                for (final Card card : viewer.getGame().getCardsIn(zone)) {
                    if (!card.isFaceDown() && card.getOwner().isOpponentOf(viewer)
                            && card.getPaperCard() instanceof PaperCard paper) {
                        seen.putIfAbsent(paper.getName(), paper);
                    }
                }
            }
        }
    }

    public static final class Observer {
        private final Player player;
        private final MatchKnowledge knowledge;

        public Observer(final Player player, final MatchKnowledge knowledge) {
            this.player = player;
            this.knowledge = knowledge;
        }

        @Subscribe
        public void onZoneChange(final GameEventCardChangeZone event) { knowledge.observe(player); }
    }
}
