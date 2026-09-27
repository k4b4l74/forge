# Sligh

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `sligh` · Family: **Aggro** · Rules: `swedish-fe-burn-london-v1`
Aliases: Mono Red Aggro, Red Deck Wins.

## Identity and construction

Red creatures on a practical curve turn available mana into repeated damage; burn removes blockers or supplies the last points. This is not automatically an all-spells burn deck.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/sligh) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

A straightforward nontribal curve uses Flarg, Ironclaw Orcs, Ball Lightning and Granite Gargoyle. Ball Lightning needs RRR; Factory is limited so it does not dominate opening mana.

```forge-deck
[Main]
4 Goblins of the Flarg
4 Ironclaw Orcs
4 Ball Lightning
3 Granite Gargoyle
4 Lightning Bolt
4 Chain Lightning
3 Black Vise
2 Fireball
2 Blood Moon
1 Wheel of Fortune
1 Mox Ruby
1 Black Lotus
1 Chaos Orb
2 Mishra's Factory
1 Strip Mine
23 Mountain
[Sideboard]
3 Red Elemental Blast
3 Shatter
2 Earthquake
2 Blood Moon
2 Tormod's Crypt
3 Detonate
```

## Mulligan priorities

Prefer a one-drop and two red sources to a pile of three-drops. Ball Lightning is not a turn-three plan without three red mana; excessive colorless lands break the curve.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 2 Mountain;1 Goblins of the Flarg;1 Ironclaw Orcs;1 Lightning Bolt;1 Chain Lightning;1 Ball Lightning | Keep | - | A one-two curve creates damage even if the third source is delayed. |
| H2 | Draw / unknown | 0 | 3 Mountain;1 Goblins of the Flarg;1 Ironclaw Orcs;1 Lightning Bolt;1 Fireball | Keep | - | Mana is stable and two creatures start work before the finisher. |
| H3 | Play or draw / unknown | 0 | 4 Goblins of the Flarg; 3 Ironclaw Orcs | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 7 Mountain | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 2 Mountain;1 Goblins of the Flarg;1 Ironclaw Orcs;1 Lightning Bolt;1 Chain Lightning;1 Ball Lightning | Keep six | 1 Ball Lightning | Keep the low curve and burn rather than an RRR requirement. |
| H6 | Draw / unknown | 2 | 3 Mountain;1 Goblins of the Flarg;1 Ironclaw Orcs;1 Lightning Bolt;1 Fireball | Keep five | 1 Fireball;1 Mountain | A coherent two-land, two-creature, Bolt five is better than retaining the slow sink. |

## Sequencing and resources

- **Early:** Lead with repeatable creature damage before one-shot face burn. Plan the red requirements of the next two turns.
- **Middle:** Remove a blocker if doing so unlocks multiple attacks; do not send Ball Lightning into a first-striker that kills it before normal combat damage.
- **Late:** When the opponent is within reach, preserve red mana and burn for a verified finish instead of deploying a summoning-sick creature.
- **Mana burn:** Ball Lightning can consume Lotus's RRR exactly; do not crack Lotus in combat expecting to cast it after combat. Unused phase-end mana cannot be banked.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Become the remover when their creature curve outclasses yours; race only with a credible damage advantage. |
| Midrange | Burn plus an attack may beat a large blocker more efficiently than two burn spells alone. |
| Control | Stretch answers with cheap creatures, then finish with burn before Tome takes over. |
| Combo | Maximize the fastest real clock and use a specific engine answer when it costs less time than racing. |
| Prison | Blood Moon can slow nonbasic-heavy decks but does not disable artifact mana or remove enchantment locks. |

### Named exceptions and common mistakes

- White Weenie: White Knight makes Ball Lightning poor without prior removal; evaluate first strike explicitly.
- The Deck: Circle of Protection: Red demands artifact damage, mana pressure, or a different plan; repeated Bolts into paid prevention do nothing.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| The Deck / play | 3 Red Elemental Blast;2 Blood Moon | 3 Granite Gargoyle;2 Fireball | Lower the curve and interrupt blue control while retaining direct damage. |
| Artifact Aggro / draw | 3 Shatter;2 Detonate | 2 Blood Moon;3 Granite Gargoyle | Spend fewer turns on slow evasive threats; dismantle their acceleration or robot blockers. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / sligh-D1 | A creature can attack multiple times if one blocker is removed | Compare future damage with burning the opponent now | Choose face burn when it is immediate lethal through known effects | Repeated damage can exceed a single spell (H) |
| 90 / sligh-D2 | Ball Lightning faces a first-strike blocker with lethal power | Do not treat trample as protection from first strike | Remove or neutralize the blocker first | A dead attacker deals no later normal combat damage (R) |
| 80 / sligh-D3 | Opponent is in verified burn range | Keep enough red mana for the finishing sequence | Account for prevention and known counterplay | Do not delay a credible win for marginal board growth (H) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Opponent blocks your Ball Lightning with White Knight and no modifiers. The first-strike damage kills the 6/1 before it can deal normal damage; do not count four trample damage.
- **S2** Opponent at 3 has no relevant protection and you have Bolt plus an untapped Mountain. Prefer the immediate finishing spell to casting a creature that cannot attack this turn.

## Evidence and implementation boundary

- [Wak-Wak: Sligh](https://www.wak-wak.se/9394decks/sligh): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Goblins of the Flarg](../../forge-gui/res/cardsfolder/g/goblins_of_the_flarg.txt), [Ironclaw Orcs](../../forge-gui/res/cardsfolder/i/ironclaw_orcs.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
