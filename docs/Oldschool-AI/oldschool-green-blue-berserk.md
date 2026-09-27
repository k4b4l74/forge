# Green Blue Berserk

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `green-blue-berserk` · Family: **Aggro** · Rules: `swedish-fe-burn-london-v1`
Aliases: UG Berserk, Berserk aggro.

## Identity and construction

Cheap evasive threats become burst kills through pump and Berserk. The key resource is a living attacker plus protection, not the theoretical maximum size of a hand full of pump.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/green-blue-berserk) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

Sprites and Flying Men carry Growth/Mutation, supported by Pixies and Birds. Avoid Fate is narrow protection; this fixture does not treat it as a universal counterspell.

```forge-deck
[Main]
4 Scryb Sprites
4 Flying Men
4 Argothian Pixies
3 Birds of Paradise
4 Giant Growth
4 Berserk
3 Unstable Mutation
3 Avoid Fate
2 Sylvan Library
1 Ancestral Recall
1 Time Walk
1 Mox Emerald
1 Mox Sapphire
1 Black Lotus
4 Tropical Island
4 City of Brass
4 Island
12 Forest
[Sideboard]
3 Blue Elemental Blast
3 Tranquility
3 Crumble
2 Control Magic
2 Tormod's Crypt
2 Whirling Dervish
```

## Mulligan priorities

Require a castable attacker before keeping enhancement density. Two lands and an evasive one-drop outperform five pump spells with no body. Bottom redundant burst before the last creature or its colors.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 1 Tropical Island;1 Forest;1 Scryb Sprites;1 Flying Men;1 Giant Growth;1 Berserk;1 Avoid Fate | Keep | - | Two bodies make removal less devastating and both colors are available. |
| H2 | Draw / unknown | 0 | 1 Tropical Island;1 Island;1 Flying Men;1 Argothian Pixies;1 Giant Growth;1 Berserk;1 Unstable Mutation | Keep | - | Evasion plus multiple forms of pressure can exploit a slow opponent. |
| H3 | Play or draw / unknown | 0 | 4 Scryb Sprites; 3 Flying Men | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 Tropical Island; 3 City of Brass | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 1 Tropical Island;1 Forest;1 Scryb Sprites;1 Flying Men;1 Giant Growth;1 Berserk;1 Avoid Fate | Keep six | 1 Flying Men | Keep one coherent protected burst package rather than the redundant body. |
| H6 | Draw / unknown | 2 | 1 Tropical Island;1 Island;1 Flying Men;1 Argothian Pixies;1 Giant Growth;1 Berserk;1 Unstable Mutation | Keep five | 1 Argothian Pixies;1 Unstable Mutation | Two sources, a flier, Growth and Berserk retain a compact five-card plan. |

## Sequencing and resources

- **Early:** Establish a body without spending all protection mana. Birds fixes mana but does not naturally deal combat damage.
- **Middle:** Stack pump so Berserk sees the increased power when it resolves. Determine legal timing before damage; never wait until after damage to cast it.
- **Late:** If removal density is high, win through incremental attacks rather than insisting on one huge burst. Rebuild with a retained attacker.
- **Mana burn:** Keep green for both pump and protection rather than floating surplus blue. Pump spells cannot make combat mana survive into the next main phase.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Race in the air, but respect opposing burn in response to enhancements. |
| Midrange | Pixies can bypass robot blockers; against nonartifact reach or fliers, remove or outsize the actual obstacle. |
| Control | Avoid stacking multiple cards into Swords; force answers with modest pressure first. |
| Combo | Your disruption is limited, so the default role is the fast clock with a specific boarded engine answer. |
| Prison | Remove Moat or other decisive enchantments with Tranquility only when its loss of your own Mutations/Library is acceptable. |

### Named exceptions and common mistakes

- The Deck: Avoid Fate cannot counter Wrath, a counterspell, or an activated Chaos Orb ability; its text is not 'protect my creature from everything'.
- Atog: direct burn threatens both the carrier and your life. An unblocked flier is not proof a pump chain will resolve.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| UR Burn / draw | 3 Blue Elemental Blast;2 Control Magic | 3 Unstable Mutation;2 Berserk | Reduce fragile burst and add red interaction plus a later way to take Efreet. |
| The Deck / play | 3 Tranquility | 3 Unstable Mutation | Give the creature clock a route through enchantment defenses; do not destroy your own Library casually. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / green-blue-berserk-D1 | An unblocked creature and legal pump window are available | Apply additive pump before Berserk when committing | Opponent interaction may make committing incorrect | Berserk uses power on resolution (R) |
| 90 / green-blue-berserk-D2 | A removal spell is on the stack targeting your permanent | Check whether Avoid Fate can legally counter that spell | It cannot counter abilities or arbitrary sorceries | Use actual spell type and target restrictions (R) |
| 80 / green-blue-berserk-D3 | A modest clock already wins before the opponent's visible plan | Retain extra pump rather than investing every card | Commit for a verified earlier kill or to survive | Reduce two-for-one exposure (H) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Your unblocked 1/1 Sprites gets Giant Growth and then Berserk, both resolving before damage. Its power becomes 8; reversing that order yields 5, not 8.
- **S2** Wrath of God is on the stack. Avoid Fate is not a legal universal answer to it; the AI must not count that card as protection.

## Evidence and implementation boundary

- [Wak-Wak: Green Blue Berserk](https://www.wak-wak.se/9394decks/green-blue-berserk): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Scryb Sprites](../../forge-gui/res/cardsfolder/s/scryb_sprites.txt), [Flying Men](../../forge-gui/res/cardsfolder/f/flying_men.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
