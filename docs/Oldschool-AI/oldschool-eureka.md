# Eureka!

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `eureka` · Family: **Combo** · Rules: `swedish-fe-burn-london-v1`
Aliases: Eureka fatties.

## Identity and construction

Eureka converts a stocked hand of expensive permanents into a battlefield, but the opponent also participates. Choosing when to stop and which permanents to put in matters as much as casting it.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/eureka) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

The green-led fixture uses large artifacts/creatures and Crossroads for haste. Four-color upkeep support is present for Nicol Bolas, but a reanimation-free hand cannot cheat away future upkeep costs.

```forge-deck
[Main]
4 Eureka
4 Birds of Paradise
4 Llanowar Elves
3 Colossus of Sardia
3 Force of Nature
3 Shivan Dragon
2 Nicol Bolas
2 Triskelion
2 Concordant Crossroads
2 Sylvan Library
3 Avoid Fate
1 Regrowth
1 Mox Emerald
1 Black Lotus
1 Sol Ring
1 Chaos Orb
4 City of Brass
4 Tropical Island
4 Taiga
2 Badlands
9 Forest
[Sideboard]
3 Tranquility
3 Crumble
3 Red Elemental Blast
2 Fog
2 Whirling Dervish
2 Tormod's Crypt
```

## Mulligan priorities

Seek Eureka, acceleration and worthwhile permanents, or a credible hard-cast route. All fatties with no engine and all engines with no payload are different forms of a nonfunctional hand.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 2 Forest;1 Birds of Paradise;1 Eureka;1 Colossus of Sardia;1 Triskelion;1 Avoid Fate | Keep | - | Mana development, engine and payload are present, though another source is still needed for four mana. |
| H2 | Draw / unknown | 0 | 3 Forest;1 Birds of Paradise;1 Eureka;1 Colossus of Sardia;1 Force of Nature | Keep | - | Stable green development reaches the engine with two major permanents. |
| H3 | Play or draw / unknown | 0 | 4 Eureka; 3 Birds of Paradise | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 City of Brass; 3 Tropical Island | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 2 Forest;1 Birds of Paradise;1 Eureka;1 Colossus of Sardia;1 Triskelion;1 Avoid Fate | Keep six | 1 Avoid Fate | Keep the engine and enough payoff rather than narrow protection. |
| H6 | Draw / unknown | 2 | 3 Forest;1 Birds of Paradise;1 Eureka;1 Colossus of Sardia;1 Force of Nature | Keep five | 1 Forest;1 Force of Nature | At five keep two lands, Bird, Eureka and one payoff; drawing the next source is a known risk. |

## Sequencing and resources

- **Early:** Develop GG and sufficient total mana while tracking what the opponent's revealed cards might add through Eureka.
- **Middle:** Eureka alternates optional permanent entries; opponents can put in answers or better threats. Crossroads gives all creatures haste and is a world enchantment, not a private bonus.
- **Late:** After the burst, count upkeep and untap obligations. Triskelion offers immediate value; a tapped Colossus or unpaid Nicol Bolas may not sustain the clock.
- **Mana burn:** Lotus can help reach Eureka but the permanents put in by its effect do not consume spell mana. Avoid floating mana for costs you will not actually pay.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | A timely Triskelion or large blocker can stabilize; Crossroads can also haste opposing reinforcements. |
| Midrange | Compare both payloads, not only your largest creature, before offering the symmetric effect. |
| Control | Revealed Moat, Abyss or other defensive permanents can turn Eureka against you; don't auto-cast into them. |
| Combo | Give the opponent no unnecessary free engine pieces; cast only when your resulting line is stronger. |
| Prison | Free permanent entry may bypass casting taxes, but it does not remove resolved lock effects automatically. |

### Named exceptions and common mistakes

- The Deck: Eureka can put their Moat directly onto the battlefield; a pile of ground fatties may fail to attack.
- Reanimator: a hand rich in expensive creatures can benefit from your Eureka as much as yours does.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| UR Burn / draw | 3 Red Elemental Blast;2 Fog | 2 Nicol Bolas;2 Concordant Crossroads;1 Colossus of Sardia | Trim upkeep/haste risks and interact or survive while assembling the engine. |
| The Deck / play | 3 Tranquility;3 Red Elemental Blast | 3 Force of Nature;2 Concordant Crossroads;1 Colossus of Sardia | Reduce maintenance-heavy payloads and gain ways through counters and enchantment defenses. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / eureka-D1 | Eureka is castable with a strong payload | Estimate opposing known payload and answers before committing | Do not inspect hidden hands; uncertainty needs a risk estimate | Symmetric effects require both boards (H) |
| 90 / eureka-D2 | Crossroads would grant immediate haste | Evaluate all creatures and world-rule consequences | An immediate secure kill can outweigh symmetry | The effect is global (R) |
| 80 / eureka-D3 | A selected payload has upkeep or untap obligations | Plan the following turns before valuing it as a recurring clock | Immediate impact may still justify a one-shot threat | Cheating entry does not waive future costs (R) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Opponent has publicly revealed Moat and can put it in during Eureka. Do not count your ground creatures as an immediate winning attack without an answer or fliers.
- **S2** Your Colossus enters via Eureka and attacks under Crossroads. Next turn it still does not untap naturally; haste did not remove its untap restriction.

## Evidence and implementation boundary

- [Wak-Wak: Eureka!](https://www.wak-wak.se/9394decks/eureka): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Eureka](../../forge-gui/res/cardsfolder/e/eureka.txt), [Birds of Paradise](../../forge-gui/res/cardsfolder/b/birds_of_paradise.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
