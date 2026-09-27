# Candleflare

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `candleflare` · Family: **Combo** · Rules: `swedish-fe-burn-london-v1`
Aliases: CandleFlare, Big Red mana.

## Identity and construction

Mana Flare and Gauntlet amplify land mana, while Candelabra buys another use of lands to support large X spells. Its engine is symmetric and not inherently repeatable without limits.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/candleflare) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

A red-heavy fixture maximizes Mountains for Gauntlet and uses Fireball/Disintegrate/Fork as payoffs. Factory is a small alternate threat, not a reason to dilute red production further.

```forge-deck
[Main]
4 Candelabra of Tawnos
4 Mana Flare
3 Gauntlet of Might
4 Fireball
3 Disintegrate
3 Fork
4 Lightning Bolt
4 Howling Mine
2 Ivory Tower
1 Wheel of Fortune
1 Sol Ring
1 Mox Ruby
1 Black Lotus
1 Chaos Orb
2 Mishra's Factory
1 Strip Mine
21 Mountain
[Sideboard]
3 Red Elemental Blast
3 Shatter
2 Earthquake
2 Blood Moon
2 Tormod's Crypt
3 Detonate
```

## Mulligan priorities

Look for lands, an engine and something useful to do with the mana. Several Candelabras without land multipliers or a payoff do not create acceleration by themselves.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 3 Mountain;1 Lightning Bolt;1 Mana Flare;1 Candelabra of Tawnos;1 Fireball | Keep | - | Three lands support the multiplier and a concrete payoff, with early interaction. |
| H2 | Draw / unknown | 0 | 3 Mountain;1 Lightning Bolt;1 Howling Mine;1 Mana Flare;1 Disintegrate | Keep | - | Stable mana and card flow can assemble the big-spell turn. |
| H3 | Play or draw / unknown | 0 | 4 Candelabra of Tawnos; 3 Mana Flare | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 7 Mountain | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 3 Mountain;1 Lightning Bolt;1 Mana Flare;1 Candelabra of Tawnos;1 Fireball | Keep six | 1 Candelabra of Tawnos | Flare plus lands already provides a mana plan; preserve the payoff. |
| H6 | Draw / unknown | 2 | 3 Mountain;1 Lightning Bolt;1 Howling Mine;1 Mana Flare;1 Disintegrate | Keep five | 1 Mana Flare;1 Disintegrate | At five keep lands, interaction and draw rather than two dependent expensive cards. |

## Sequencing and resources

- **Early:** Develop enough lands and a use for the multiplier before gifting extra mana to the opponent.
- **Middle:** Candelabra taps as part of its activation cost. Compute the net mana from untapped targets and the X payment rather than recursively using the same tapped artifact.
- **Late:** Make an exact-cost lethal sequence, including Fireball's multiple-target surcharge where relevant. Avoid gratuitous extra mana that becomes burn if the plan changes.
- **Mana burn:** With both Flare and Gauntlet, a Mountain can yield three red. A one-mana defensive spell may leave two mana unused; that life loss can reverse a race.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Use cheap removal and Tower to survive; helping the opponent cast multiple threats can be fatal. |
| Midrange | Large X removal can clear a board but must compete with simply finishing the opponent. |
| Control | Protect the mana engine and payoff; an unanswered Mine can benefit the control opponent more. |
| Combo | Both sides may exploit Flare, so race using a counted one-turn expenditure rather than granting a free setup turn. |
| Prison | Orb and land-denial effects attack the multiplier's fuel; remove the relevant lock while conserving red access. |

### Named exceptions and common mistakes

- UR Burn: their instant burn can exploit your Flare and your accidental mana burn; deployment timing matters.
- Twiddlevault: extra mana may let the opponent chain turns immediately; don't regard symmetric acceleration as private value.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| White Weenie / draw | 2 Earthquake | 2 Fork | Replace conditional copies with an actual ground-board reset, counting your own life. |
| The Deck / play | 3 Red Elemental Blast;2 Blood Moon | 2 Ivory Tower;3 Disintegrate | Keep Fireballs while improving blue interaction and mana pressure. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / candleflare-D1 | A multiplier will help both players before your next action | Deploy it only with a favorable immediate plan or survival margin | A needed defensive mana increase may justify symmetry | Acceleration can help the opponent first (H) |
| 90 / candleflare-D2 | Candelabra is already tapped | Do not schedule another activation without an untap effect | A separate untapped copy is a different resource | The tap cost prevents an automatic loop (R) |
| 80 / candleflare-D3 | Generated mana exceeds the planned X spell and support costs | Reduce production where possible | Necessary responses can justify reserving mana, with burn risk counted | Aim for useful mana, not the largest number (H) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Candelabra is untapped and can untap three lands. After paying its cost and tapping it, the planner cannot activate that same copy again without an untap effect.
- **S2** With Flare and Gauntlet active, tap one Mountain to cast Bolt. After spending R, two red remain; without another use they burn at phase end.

## Evidence and implementation boundary

- [Wak-Wak: Candleflare](https://www.wak-wak.se/9394decks/candleflare): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Candelabra of Tawnos](../../forge-gui/res/cardsfolder/c/candelabra_of_tawnos.txt), [Mana Flare](../../forge-gui/res/cardsfolder/m/mana_flare.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
