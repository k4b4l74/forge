# Lestree Zoo

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `lestree-zoo` · Family: **Aggro** · Rules: `swedish-fe-burn-london-v1`
Aliases: UGR Zoo, Arabian Aggro, Quicksilver (overlap).

## Identity and construction

A multicolor aggressive mix of Ape, Efreet, green bodies and burn. Blue cards add evasion and restricted power, but the mana must still cast the early threats.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/lestree-zoo) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

This UGR fixture preserves a high colored-source density and only two Factories. It represents the overlap with imported Quicksilver, not every deck using that name.

```forge-deck
[Main]
4 Kird Ape
4 Serendib Efreet
4 Argothian Pixies
2 Erhnam Djinn
3 Birds of Paradise
4 Lightning Bolt
4 Chain Lightning
2 Psionic Blast
2 Sylvan Library
1 Ancestral Recall
1 Time Walk
1 Regrowth
1 Mox Emerald
1 Mox Ruby
1 Mox Sapphire
1 Black Lotus
4 Taiga
4 Tropical Island
4 Volcanic Island
3 City of Brass
2 Mishra's Factory
1 Strip Mine
2 Island
4 Forest
[Sideboard]
3 Red Elemental Blast
3 Blue Elemental Blast
3 Tranquility
3 Crumble
1 Control Magic
2 Tormod's Crypt
```

## Mulligan priorities

Reject attractive three-color spells with only off-color sources. Birds is vulnerable fixing, not a land; plan a playable hand if it dies. Favor an early Ape/Pixies plus interaction over two Efreets and no blue.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 1 Taiga;1 Tropical Island;1 Kird Ape;1 Argothian Pixies;1 Lightning Bolt;1 Serendib Efreet;1 Sylvan Library | Keep | - | Both early green/red plays and future blue access are present. |
| H2 | Draw / unknown | 0 | 1 Taiga;1 Volcanic Island;1 Tropical Island;1 Kird Ape;1 Serendib Efreet;1 Lightning Bolt;1 Psionic Blast | Keep | - | Three colors and a backed-up flying threat support a flexible race. |
| H3 | Play or draw / unknown | 0 | 4 Kird Ape; 3 Serendib Efreet | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 Taiga; 3 Tropical Island | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 1 Taiga;1 Tropical Island;1 Kird Ape;1 Argothian Pixies;1 Lightning Bolt;1 Serendib Efreet;1 Sylvan Library | Keep six | 1 Sylvan Library | Retain a real curve and removal rather than optional card selection. |
| H6 | Draw / unknown | 2 | 1 Taiga;1 Volcanic Island;1 Tropical Island;1 Kird Ape;1 Serendib Efreet;1 Lightning Bolt;1 Psionic Blast | Keep five | 1 Tropical Island;1 Psionic Blast | Taiga and Volcanic keep all colors; preserve cheap pressure and Bolt while seeking land three. |

## Sequencing and resources

- **Early:** Choose land sequencing from the actual opening spells; Taiga turns on Ape, whereas Volcanic does not.
- **Middle:** Use evasive threats to shorten stalls and burn to preserve attack tempo. Reassess after an opponent shows City in a Bottle.
- **Late:** The final race must count Efreet upkeep, City pain and Blast recoil; Library's extra cards are not free.
- **Mana burn:** Multiple off-color Moxen can produce excess mana without solving the colored requirement. Pay only what the chosen sequence needs.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Take the stabilizer role if removal plus larger bodies wins the board; don't always burn face. |
| Midrange | Efreet and Pixies can sidestep ground robot defenses; protect the relevant evasive clock. |
| Control | Diversify threats without overcommitting and preserve a way through enchantment defense. |
| Combo | Race with disruption aimed at the actual engine rather than blindly playing every threat. |
| Prison | Fragile nonbasics make Moon and land denial serious; use surviving mana for the specific answer. |

### Named exceptions and common mistakes

- Robots: Pixies are often better than another large green creature because artifact blockers cannot block them.
- UR Burn: your Efreet/City/Blast life costs can make a nominal board advantage lose the race.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| UR Burn / draw | 3 Blue Elemental Blast;1 Control Magic | 2 Sylvan Library;2 Psionic Blast | Reduce voluntary life loss and add red interaction. |
| The Deck / play | 3 Red Elemental Blast;3 Tranquility | 2 Psionic Blast;2 Erhnam Djinn;2 Birds of Paradise | Lower slow-threat dependence and answer key enchantments; do not strand remaining spells by cutting more fixing. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / lestree-zoo-D1 | Several colored spells compete for the first two land drops | Choose the sequence enabling the earliest useful plays | Protect a scarce color from visible denial when possible | Color access is more important than raw land count (H) |
| 90 / lestree-zoo-D2 | A race uses Efreet and Blast while life is low | Include all self-damage before committing | Known prevention or immediate lethal can change the calculation | Avoid winning-board, losing-life lines (H) |
| 80 / lestree-zoo-D3 | Opponent reveals City in a Bottle | Revalue Arabian Nights threats and lands by the actual card text | Do not assume all old printings or all nonbasics are affected | Specific identity-based hate requires a revised plan (R) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Taiga and Volcanic Island provide all three colors between them, but only Taiga supplies the Forest type for Ape. The mana planner must distinguish types from mana colors.
- **S2** You are at 3 with an Efreet and intend to cast Psionic Blast without winning immediately. Dropping to 1 exposes lethal at the next upkeep; the policy must account for that clock.

## Evidence and implementation boundary

- [Wak-Wak: Lestree Zoo](https://www.wak-wak.se/9394decks/lestree-zoo): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Kird Ape](../../forge-gui/res/cardsfolder/k/kird_ape.txt), [Serendib Efreet](../../forge-gui/res/cardsfolder/s/serendib_efreet.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
