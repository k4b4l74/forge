# Atog Smash

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `atog-smash` · Family: **Aggro** · Rules: `swedish-fe-burn-london-v1`
Aliases: Atog, Atog Aggro.

## Identity and construction

Artifact pressure and burn support Atog's sacrifice burst. Vise/Ankh damage and artifact-creature acceleration are related builds, but their sideboard cuts are not interchangeable.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/atog-smash) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

This red artifact-pressure fixture uses Vise, Ankh and Copper Tablet rather than a Workshop robot package. Its mana and burn are intentionally red-focused; multicolor imported Atog lists require their own color checks.

```forge-deck
[Main]
4 Atog
4 Black Vise
4 Ankh of Mishra
3 Copper Tablet
3 Mana Vault
4 Lightning Bolt
4 Chain Lightning
2 Fireball
1 Wheel of Fortune
1 Sol Ring
1 Mox Ruby
1 Black Lotus
1 Chaos Orb
4 Mishra's Factory
1 Strip Mine
22 Mountain
[Sideboard]
3 Red Elemental Blast
3 Shatter
2 Earthquake
2 Blood Moon
2 Tormod's Crypt
3 Detonate
```

## Mulligan priorities

Look for a turn-one pressure artifact or a castable Atog plus disposable artifacts. Mana Vault without a useful spender is neither pressure nor reliable colored mana. On the draw against creatures, prioritize Bolt over a second Vise.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 2 Mountain;1 Black Vise;1 Atog;1 Ankh of Mishra;1 Lightning Bolt;1 Copper Tablet | Keep | - | Vise into Atog/Ankh creates multiple routes to damage. |
| H2 | Draw / White Weenie | 0 | 2 Mountain;1 Atog;1 Black Vise;1 Lightning Bolt;1 Chain Lightning;1 Fireball | Keep | - | Burn can buy tempo before Atog matters; do not blindly lead Vise into an emptied hand. |
| H3 | Play or draw / unknown | 0 | 4 Atog; 3 Black Vise | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 7 Mountain | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 2 Mountain;1 Black Vise;1 Atog;1 Ankh of Mishra;1 Lightning Bolt;1 Copper Tablet | Keep six | 1 Copper Tablet | Keep the efficient opener and interaction rather than slower symmetric damage. |
| H6 | Draw / White Weenie | 2 | 2 Mountain;1 Atog;1 Black Vise;1 Lightning Bolt;1 Chain Lightning;1 Fireball | Keep five | 1 Fireball;1 Black Vise | At five on the draw, favor castable interaction and the creature over weak late Vise damage. |

## Sequencing and resources

- **Early:** Vise is strongest before the opponent empties their hand; evaluate Ankh against the land drops each player still needs.
- **Middle:** Attack with Atog before sacrificing anything. Once blocks are declared, calculate precisely what extra power changes the outcome; preserve mana rocks until their loss is justified.
- **Late:** Sacrifice harmful Tablets or dead Vises when the burst wins or saves Atog. Do not turn every permanent into pump into an available exile spell.
- **Mana burn:** Sacrificing tapped Mana Vault can remove its future damage, but tap it first only if its mana has a same-phase use. Atog's sacrifice ability itself spends no mana.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Burn can make you the temporary control deck; symmetric Ankh and Tablet may hurt the faster creature player least. |
| Midrange | Threaten a large Atog while keeping enough burn to finish through a single blocker. |
| Control | Early Vise/Ankh punishes slow development; preserve enough material to rebuild after Disenchant or Wrath. |
| Combo | Apply pressure before the engine is assembled; Shatter must hit the relevant component, not an arbitrary Mox. |
| Prison | Identify whether attacking, casting, or mana is constrained; do not assume sacrificing the board beats Moat. |

### Named exceptions and common mistakes

- The Deck: Swords can exile a fully fed Atog; sacrifice only as much as the current lethal or protection line requires.
- Reanimator: a large reanimated blocker can nullify ground pump without trample; burn reach and Crypt timing matter more than a theoretical enormous Atog.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| White Weenie / draw | 2 Earthquake | 2 Copper Tablet | Replace slow symmetric damage with a way to clear small ground creatures. Size Earthquake around your own Atog and life total; do not import Shatters against a mostly nonartifact list. |
| The Deck / play | 3 Red Elemental Blast;2 Blood Moon | 3 Copper Tablet;2 Fireball | Swap slow symmetric damage and expensive finishers for disruption; Moon can also disable your Factories. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / atog-smash-D1 | An unblocked Atog can become lethal by sacrificing two artifacts | Sacrifice the smallest sufficient set after blocks | Respect visible prevention and available interaction; do not claim hidden removal is known | Preserve recovery material (H) |
| 90 / atog-smash-D2 | Opponent has two cards and your Vise has no immediate damage prospect | Prefer sacrificing Vise over a needed colored mana rock | Keep Vise if a planned Wheel changes its value | Value depends on hand size (H) |
| 80 / atog-smash-D3 | An untapped Mana Vault is about to be sacrificed to Atog with no use for its mana | Sacrifice without first activating its mana ability | Mana for an immediate burn finisher changes the decision | Atog is not a mana sink (R) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Opponent is at 5 with no blockers or relevant effects; your unblocked 1/2 Atog has two disposable artifacts available. Two activations make 5 power; do not sacrifice a third artifact.
- **S2** Opponent is at 8 behind Moat. Atog plus ten artifacts is not a combat kill: it lacks flying; preserve resources and seek a noncombat route.

## Evidence and implementation boundary

- [Wak-Wak: Atog Smash](https://www.wak-wak.se/9394decks/atog-smash): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Atog](../../forge-gui/res/cardsfolder/a/atog.txt), [Black Vise](../../forge-gui/res/cardsfolder/b/black_vise.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
