package forge.ai;

import com.google.common.collect.HashMultiset;
import forge.deck.Deck;
import forge.deck.DeckSection;
import forge.game.Game;
import forge.game.GameRules;
import forge.game.GameType;
import forge.game.Match;
import forge.game.ability.ApiType;
import forge.game.card.Card;
import forge.game.card.CardCollection;
import forge.game.player.Player;
import forge.game.spellability.SpellAbility;
import forge.game.zone.ZoneType;
import forge.item.PaperCard;
import forge.model.FModel;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class OldschoolReanimatorAiTest extends AITest {
    private Player specialist(final Game game) {
        final Player player = game.getPlayers().get(1);
        ((LobbyPlayerAi) player.getLobbyPlayer()).setAiProfile("OS Reanimator");
        return player;
    }

    private CardCollection hand(final Player player, final String... names) {
        final CardCollection cards = new CardCollection();
        for (final String name : names) { cards.add(addCardToZone(name, player, ZoneType.Hand)); }
        return cards;
    }

    @Test
    public void rejectsBazaarOnlyManaAndKeepsTheRetainedReanimationPackage() {
        final Game game = initAndCreateGame();
        final Player player = specialist(game);
        final CardCollection bad = hand(player, "Bazaar of Baghdad", "Bazaar of Baghdad", "Bazaar of Baghdad",
                "Animate Dead", "Triskelion", "Rasputin Dreamweaver", "Copy Artifact");
        assertFalse(OldschoolReanimatorAi.keepHand(bad, 0));
        final CardCollection good = hand(player, "Bayou", "Underground Sea", "Bazaar of Baghdad", "Animate Dead",
                "Triskelion", "Rasputin Dreamweaver", "Copy Artifact");
        assertTrue(OldschoolReanimatorAi.keepHand(good, 2));
        final CardCollection kept = new CardCollection(good);
        final CardCollection bottom = OldschoolReanimatorAi.bottomCards(good, 2);
        assertEquals(bottom.size(), 2);
        kept.removeAll(bottom);
        for (final String name : List.of("Bayou", "Underground Sea", "Bazaar of Baghdad", "Animate Dead")) {
            assertTrue(kept.anyMatch(card -> name.equals(card.getName())), name);
        }
        assertEquals(good.size(), 7);
    }

    @Test
    public void protectsAnimateDeadAndManaWhenLoadingTheGraveyard() {
        final Game game = initAndCreateGame();
        final Player player = specialist(game);
        final CardCollection cards = hand(player, "Bayou", "Underground Sea", "Animate Dead", "Triskelion", "Copy Artifact");
        final CardCollection discards = OldschoolReanimatorAi.discard(player, cards, 1);
        assertEquals(discards.getFirst().getName(), "Triskelion");
        assertEquals(cards.size(), 5);
        fillLibrary(player, 10);
        assertTrue(OldschoolReanimatorAi.useBazaar(player));
    }

    @Test
    public void versionTwoDoesNotKeepCopyArtifactAsUnsupportedDevelopment() {
        final Game game = initAndCreateGame();
        final Player player = specialist(game);
        final CardCollection cards = hand(player, "Bayou", "Underground Sea", "Bazaar of Baghdad", "Copy Artifact",
                "Triskelion", "Rasputin Dreamweaver", "Erhnam Djinn");
        assertTrue(OldschoolReanimatorAi.keepHand(player, 0));
        assertTrue(OldschoolReanimatorAi.keepHand(cards, 0));
        ((LobbyPlayerAi) player.getLobbyPlayer()).setAiProfile("OS Reanimator v2");
        assertTrue(OldschoolReanimatorAi.enabled(player));
        assertFalse(OldschoolReanimatorAi.keepHand(player, 0));
        assertFalse(player.getController().mulliganKeepHand(player, 0));
        assertTrue(OldschoolReanimatorAi.keepHand(player, 4));
        ((LobbyPlayerAi) player.getLobbyPlayer()).setAiProfile("OS Reanimator");
        assertTrue(OldschoolReanimatorAi.keepHand(player, 0));
    }

    @Test
    public void versionTwoKeepsSupportedCopiesAndIntactReanimationPackages() {
        final Game game = initAndCreateGame();
        final Player player = specialist(game);
        ((LobbyPlayerAi) player.getLobbyPlayer()).setAiProfile("OS Reanimator v2");
        final CardCollection packageHand = hand(player, "Bayou", "Underground Sea", "Bazaar of Baghdad", "Animate Dead",
                "Triskelion", "Rasputin Dreamweaver", "Copy Artifact");
        assertTrue(OldschoolReanimatorAi.keepHand(player, 2));
        final CardCollection bottom = OldschoolReanimatorAi.bottomCards(player, packageHand, 2);
        assertEquals(bottom.size(), 2);
        assertFalse(bottom.anyMatch(card -> "Animate Dead".equals(card.getName()) || "Bazaar of Baghdad".equals(card.getName())));
        final Player other = game.getPlayers().get(0);
        ((LobbyPlayerAi) other.getLobbyPlayer()).setAiProfile("OS Reanimator v2");
        hand(other, "Bayou", "Underground Sea", "Mox Jet", "Copy Artifact", "Triskelion", "Rasputin Dreamweaver", "Animate Dead");
        assertTrue(OldschoolReanimatorAi.keepHand(other, 0));
    }

    @Test
    public void decisionTracingIsScopedAndDoesNotExposeOpposingHandContents() {
        final Game game = initAndCreateGame();
        final Player player = specialist(game);
        final Player opponent = game.getPlayers().get(0);
        hand(player, "Bayou", "Underground Sea", "Bazaar of Baghdad", "Animate Dead", "Triskelion", "Recall", "Copy Artifact");
        hand(opponent, "Lightning Bolt");
        addCard("Savannah Lions", opponent);
        final List<AiDecisionTrace.Entry> entries = new ArrayList<>();
        final boolean untraced = player.getController().mulliganKeepHand(player, 0);
        assertFalse(AiDecisionTrace.active());
        try (AiDecisionTrace ignored = AiDecisionTrace.open(entries::add)) {
            assertEquals(player.getController().mulliganKeepHand(player, 0), untraced);
            assertEquals(entries.size(), 1);
            assertTrue(entries.get(0).hand().contains("Animate Dead"));
            assertEquals(entries.get(0).opponents().get(0).handSize(), 1);
            assertTrue(entries.get(0).opponents().get(0).battlefield().contains("Savannah Lions"));
            assertFalse(entries.get(0).toString().contains("Lightning Bolt"));
        }
        assertFalse(AiDecisionTrace.active());
        player.getController().mulliganKeepHand(player, 0);
        assertEquals(entries.size(), 1);
    }

    @Test
    public void distinguishesManaPayoffFromStabilizationAndCopiesFreshRobotCounters() {
        final Game game = initAndCreateGame();
        final Player player = specialist(game);
        player.setLife(20, null);
        final Card rasputin = addCardToZone("Rasputin Dreamweaver", player, ZoneType.Graveyard);
        final Card robot = addCardToZone("Triskelion", player, ZoneType.Graveyard);
        hand(player, "Triskelion");
        assertEquals(OldschoolReanimatorAi.reanimationTarget(player, List.of(robot, rasputin)), rasputin);
        player.setLife(5, null);
        assertEquals(OldschoolReanimatorAi.reanimationTarget(player, List.of(rasputin, robot)), robot);
        final Card depleted = addCard("Triskelion", player);
        final Card ring = addCard("Sol Ring", player);
        final Card copy = addCardToZone("Copy Artifact", player, ZoneType.Hand);
        final SpellAbility ability = new SpellAbility.EmptySa(ApiType.Clone, copy, player);
        assertEquals(SpellApiToAi.Converter.get(ApiType.Clone).chooseSingleEntity(player, ability,
                List.of(ring, depleted), false, null, null), depleted);
        assertTrue(OldschoolReanimatorAi.enabled(player));
        ((LobbyPlayerAi) player.getLobbyPlayer()).setAiProfile("Default");
        assertFalse(OldschoolReanimatorAi.enabled(player));
    }

    @Test
    public void boardsFromObservedCardsAndPreservesEveryPhysicalCard() {
        initAndCreateGame();
        final Deck deck = new Deck("KabaL sideboard fixture");
        deck.getMain().add("Swamp", 20);
        deck.getMain().add("Animate Dead", 4);
        deck.getMain().add("Triskelion", 4);
        deck.getMain().add("Erhnam Djinn", 4);
        deck.getMain().add("Rasputin Dreamweaver", 3);
        deck.getMain().add("Copy Artifact", 3);
        deck.getMain().add("Psionic Blast", 3);
        deck.getMain().add("Recall", 1);
        deck.getMain().add("Island", 18);
        deck.getOrCreate(DeckSection.Sideboard).add("Blue Elemental Blast", 2);
        deck.get(DeckSection.Sideboard).add("Circle of Protection: Red", 2);
        deck.get(DeckSection.Sideboard).add("Red Elemental Blast", 2);
        deck.get(DeckSection.Sideboard).add("Disenchant", 2);
        deck.get(DeckSection.Sideboard).add("Swords to Plowshares", 3);
        deck.get(DeckSection.Sideboard).add("Underworld Dreams", 4);
        final List<PaperCard> all = new ArrayList<>(deck.getMain().toFlatList());
        all.addAll(deck.get(DeckSection.Sideboard).toFlatList());
        final PaperCard bolt = FModel.getMagicDb().getCommonCards().getCard("Lightning Bolt");
        final List<PaperCard> main = OldschoolReanimatorAi.sideboard(deck, List.of(bolt));
        assertEquals(main.size(), 60);
        assertEquals(main.stream().filter(card -> "Blue Elemental Blast".equals(card.getName())).count(), 2L);
        assertEquals(main.stream().filter(card -> "Circle of Protection: Red".equals(card.getName())).count(), 2L);
        assertEquals(main.stream().filter(card -> "Animate Dead".equals(card.getName())).count(), 4L);
        final List<PaperCard> remaining = new ArrayList<>(all);
        for (final PaperCard card : main) { assertTrue(remaining.remove(card)); }
        assertEquals(remaining.size(), 15);
        assertEquals(deck.getMain().countAll(), 60);
        final List<PaperCard> noKnowledge = OldschoolReanimatorAi.sideboard(deck, List.of());
        assertEquals(HashMultiset.create(noKnowledge), HashMultiset.create(deck.getMain().toFlatList()));
    }

    @Test
    public void targetsOpponentWithAncestralOnlyForADreamsFinish() {
        final Game game = initAndCreateGame();
        final Player player = specialist(game);
        final Player opponent = game.getPlayers().get(0);
        fillLibrary(opponent, 10);
        addCard("Underworld Dreams", player);
        addCard("Underworld Dreams", player);
        final Card ancestral = addCardToZone("Ancestral Recall", player, ZoneType.Hand);
        final SpellAbility ability = ancestral.getSpellAbilities().get(0);
        ability.setActivatingPlayer(player);
        opponent.setLife(7, null);
        assertFalse(OldschoolReanimatorAi.targetDreamsFinish(player, ability));
        opponent.setLife(6, null);
        assertTrue(OldschoolReanimatorAi.targetDreamsFinish(player, ability));
        assertEquals(ability.getTargets().getFirstTargetedPlayer(), opponent);
        ((LobbyPlayerAi) player.getLobbyPlayer()).setAiProfile("Default");
        assertFalse(OldschoolReanimatorAi.targetDreamsFinish(player, ability));
    }

    @Test
    public void opponentKnowledgeExcludesHiddenZonesAndResetsBetweenMatches() {
        final Game game = initAndCreateGame();
        final Player player = specialist(game);
        final Player opponent = game.getPlayers().get(0);
        addCardToZone("Lightning Bolt", opponent, ZoneType.Hand);
        addCardToZone("Counterspell", opponent, ZoneType.Library);
        addCardToZone("Swords to Plowshares", opponent, ZoneType.Graveyard);
        final OldschoolReanimatorAi.MatchKnowledge knowledge = new OldschoolReanimatorAi.MatchKnowledge();
        knowledge.begin(game.getMatch());
        knowledge.observe(player);
        assertEquals(knowledge.seenCards().stream().map(PaperCard::getName).toList(), List.of("Swords to Plowshares"));
        knowledge.begin(new Match(new GameRules(GameType.Constructed), game.getMatch().getPlayers(), "Other match"));
        assertTrue(knowledge.seenCards().isEmpty());
    }
}
