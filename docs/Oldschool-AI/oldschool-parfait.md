# Parfait

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `parfait` · Family: **Prison** · Rules: `swedish-fe-burn-london-v1`
Aliases: Winter Orb Parfait, Artifact prison.

## Identity and construction

Winter Orb restricts land untaps while tap effects create asymmetry; Mine can supply selective extra draws. The lock is maintained through timing, not simply by owning the named cards.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/parfait) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

A mono-white artifact-prison fixture uses Relic Barrier/Icy, white removal, Vise and Millstone. Modern card-specific untapped checks apply to Orb and Mine, not every artifact.

```forge-deck
[Main]
4 Winter Orb
4 Howling Mine
4 Relic Barrier
3 Icy Manipulator
3 Black Vise
2 Millstone
2 Ivory Tower
4 Swords to Plowshares
3 Disenchant
2 Wrath of God
1 Balance
1 Mox Pearl
1 Sol Ring
1 Black Lotus
1 Chaos Orb
4 Mishra's Factory
1 Strip Mine
1 Library of Alexandria
18 Plains
[Sideboard]
3 Divine Offering
2 Dust to Dust
2 Circle of Protection: Red
2 Spirit Link
2 Armageddon
2 Serra Angel
1 Disenchant
1 Tormod's Crypt
```

## Mulligan priorities

Keep mana, early survival and a coherent piece of asymmetry. Orb without nonland mana or a tapper may restrict you as much as the opponent.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 3 Plains;1 Relic Barrier;1 Winter Orb;1 Howling Mine;1 Swords to Plowshares | Keep | - | The hand can develop the timed engine with removal to bridge the early turns. |
| H2 | Draw / unknown | 0 | 3 Plains;1 Swords to Plowshares;1 Disenchant;1 Howling Mine;1 Icy Manipulator | Keep | - | Stable white answers support a slower engine setup. |
| H3 | Play or draw / unknown | 0 | 4 Winter Orb; 3 Howling Mine | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 7 Plains | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 3 Plains;1 Relic Barrier;1 Winter Orb;1 Howling Mine;1 Swords to Plowshares | Keep six | 1 Howling Mine | Keep survival and the untap-control pair before adding symmetric draw. |
| H6 | Draw / unknown | 2 | 3 Plains;1 Swords to Plowshares;1 Disenchant;1 Howling Mine;1 Icy Manipulator | Keep five | 1 Icy Manipulator;1 Plains | At five keep two lands, immediate interaction and draw. |

## Sequencing and resources

- **Early:** Develop the mana/tapper relationship before Orb unless its symmetry already favors you. White removal buys the necessary turns.
- **Middle:** Tap Orb before your untap step, commonly in the opponent's end step. There is no normal priority window inside untap to repair missed timing.
- **Late:** Use Mine's untapped condition deliberately: deny the opponent's extra draw when the card costs more than Vise damage helps. Mill or Factory supplies a real finish.
- **Mana burn:** Icy costs one plus tapping; Relic Barrier's activation does not spend mana. Neither choice should be modeled as an unlimited sink for surplus colorless mana.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Answer creatures already deployed; restricting future mana does not remove the board. |
| Midrange | Set up asymmetry while preventing a single large threat from dominating combat. |
| Control | Protect the engine and preserve a finite win condition; repeated soft-lock turns are not wins. |
| Combo | Nonland engines may operate through Orb; target their actual artifacts or payoff. |
| Prison | Timing and which player untaps profitably decide the mirror more than simply having another Orb. |

### Named exceptions and common mistakes

- Artifact Aggro: abundant rock mana can ignore the land restriction; board for their threats and acceleration.
- Stasis: if untap steps are skipped entirely, tapping Winter Orb does not restore them; the effects are not interchangeable.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| Sligh / draw | 2 Circle of Protection: Red;2 Spirit Link | 2 Millstone;2 Icy Manipulator | Reduce slow engine pieces until life is stabilized. |
| The Deck / play | 2 Serra Angel;2 Armageddon;1 Disenchant | 4 Swords to Plowshares;1 Wrath of God | For low-creature control, introduce pressure and extra lock interaction. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / parfait-D1 | Orb will constrain your upcoming untap and a tapper is ready | Tap Orb before the untap step begins | Don't sacrifice necessary defense if the cost is greater | Restriction depends on Orb being untapped (R) |
| 90 / parfait-D2 | Untap has already begun with Orb untapped | Do not invent a priority action to tap it first | Plan at the preceding legal window next time | No normal priority during untap (R) |
| 80 / parfait-D3 | Mine gives the opponent a valuable extra card | Consider tapping it before the draw-step trigger condition | Allow it when Vise damage or another objective is more important | Selective draw and damage goals compete (H) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** At the opponent's end step, you control untapped Relic Barrier and Winter Orb. Tapping Orb now can let all your lands untap next turn; waiting until inside untap is too late.
- **S2** Stasis is active. A tapped Winter Orb does not cause a skipped untap step to happen.

## Evidence and implementation boundary

- [Wak-Wak: Parfait](https://www.wak-wak.se/9394decks/parfait): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Winter Orb](../../forge-gui/res/cardsfolder/w/winter_orb.txt), [Howling Mine](../../forge-gui/res/cardsfolder/h/howling_mine.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
