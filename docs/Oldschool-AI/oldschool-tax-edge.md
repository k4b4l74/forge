# TaxEdge

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `tax-edge` · Family: **Combo** · Rules: `swedish-fe-burn-london-v1`
Aliases: Tax Edge, Land Tax / Land's Edge.

## Identity and construction

Land Tax turns a land-count disadvantage into basic cards; Land's Edge turns discarded lands into damage. Aggressive creatures and Vise can pressure while the engine develops.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/tax-edge) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

The RW fixture deliberately includes many basics and no Factory package. Tax searches only basics, and Edge lets the opponent discard their own lands for damage too.

```forge-deck
[Main]
4 Savannah Lions
4 Icatian Javelineers
4 Black Vise
4 Land Tax
3 Land's Edge
3 Winds of Change
4 Lightning Bolt
3 Disenchant
2 Swords to Plowshares
2 Howling Mine
1 Mox Pearl
1 Mox Ruby
1 Black Lotus
1 Wheel of Fortune
4 Plateau
9 Mountain
10 Plains
[Sideboard]
3 Red Elemental Blast
2 Circle of Protection: Red
1 Disenchant
3 Divine Offering
2 Spirit Link
2 Blood Moon
2 Tormod's Crypt
```

## Mulligan priorities

Keep a real pressure or Tax plan, not Edge with no lands to discard and no castable spells. Do not keep a mana-starved hand merely because Tax exists; its land-count condition can fail.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 1 Plains;1 Plateau;1 Savannah Lions;1 Land Tax;1 Lightning Bolt;1 Land's Edge;1 Winds of Change | Keep | - | Early pressure functions independently while Tax/Edge can develop. |
| H2 | Draw / unknown | 0 | 1 Plains;1 Plateau;1 Mountain;1 Icatian Javelineers;1 Land Tax;1 Disenchant;1 Land's Edge | Keep | - | Stable colors support either pressure or the engine depending on land counts. |
| H3 | Play or draw / unknown | 0 | 4 Savannah Lions; 3 Icatian Javelineers | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 Plateau; 3 Mountain | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 1 Plains;1 Plateau;1 Savannah Lions;1 Land Tax;1 Lightning Bolt;1 Land's Edge;1 Winds of Change | Keep six | 1 Winds of Change | Keep the independent opening plays rather than optional hand cycling. |
| H6 | Draw / unknown | 2 | 1 Plains;1 Plateau;1 Mountain;1 Icatian Javelineers;1 Land Tax;1 Disenchant;1 Land's Edge | Keep five | 1 Mountain;1 Land's Edge | At five, keep cheap action and a live answer before the expensive discard outlet. |

## Sequencing and resources

- **Early:** Use Tax only when an opponent has strictly more lands, including its resolution check. Holding a land can help, but not when it strands all useful spells.
- **Middle:** Count basics remaining before valuing repeated Tax triggers. Cast Edge only when you can exploit it more safely than the opponent.
- **Late:** Discard spare lands for a verified finish while preserving necessary mana development. Winds may exchange a land-heavy hand for action, but also refills or improves the opponent's hand.
- **Mana burn:** Taxing into lands is not an excuse to tap them all. Edge's discard ability costs no mana and cannot consume an unwanted Lotus remainder.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Cheap creatures and removal carry the early turns; do not give the faster deck free Edge damage without a reason. |
| Midrange | Use land ammunition to bypass stalled combat, while respecting lifegain and opposing land reserves. |
| Control | Tax can offset resource pressure, but Disenchant on Edge or Tax breaks the package. |
| Combo | Race with a counted amount of damage rather than assuming a full grip of lands automatically wins. |
| Prison | Tax may rebuild cards under land denial, but it does not directly put the searched basics onto the battlefield. |

### Named exceptions and common mistakes

- Lands: their land count can enable Tax, but their spare lands can also make your Edge dangerous to you.
- The Deck: a control player may deliberately avoid enabling Tax; maintain an independent creature/burn plan.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| Sligh / draw | 2 Circle of Protection: Red;2 Spirit Link | 2 Howling Mine;2 Winds of Change | Reduce opponent refills while stabilizing against damage. |
| The Deck / play | 3 Red Elemental Blast;1 Disenchant;2 Blood Moon | 2 Swords to Plowshares;2 Howling Mine;2 Icatian Javelineers | For low-creature control, improve disruption and protect the remaining engine. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / tax-edge-D1 | Both players have equal land counts at Tax's relevant check | Do not grant a Tax search | An actual later count change must be evaluated in its legal window | Strictly more is required (R) |
| 90 / tax-edge-D2 | Edge would enter while opponent has more lethal land ammunition | Delay it or first secure survival | A protected immediate win can justify deployment | The activation is available to both players (R) |
| 80 / tax-edge-D3 | Tax can find three basics but fewer remain | Search only available legal cards and reduce expected future value | Searching fewer is legal | Do not create nonexistent lands (R) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** You and opponent each control two lands at the beginning of your upkeep. Land Tax does not trigger from equality.
- **S2** Opponent is at 6; you have an active Edge, three discardable lands, priority and no relevant interference. Three activations threaten six damage, but model the opponent's legal responses rather than declaring a pre-resolution win.

## Evidence and implementation boundary

- [Wak-Wak: TaxEdge](https://www.wak-wak.se/9394decks/tax-edge): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Savannah Lions](../../forge-gui/res/cardsfolder/s/savannah_lions.txt), [Icatian Javelineers](../../forge-gui/res/cardsfolder/i/icatian_javelineers.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
