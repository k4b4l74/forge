# Lands

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `lands` · Family: **Control** · Rules: `swedish-fe-burn-london-v1`
Aliases: Fastbond Lands, Old School Lands.

## Identity and construction

An unusually large land count supports Fastbond and card-flow engines, with noncombat finishers or animated lands. This is not modern Lands: no Life from the Loam, Wasteland, or Crucible.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/lands) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

A RG fixture combines Sylvan/Mine, Fastbond, Dark Heart, Mirror and Fireball. Maze is defense, not mana; the land count must not be mistaken for the usable-source count.

```forge-deck
[Main]
4 Fastbond
4 Sylvan Library
3 Howling Mine
2 Dark Heart of the Wood
3 Fireball
1 Living Plane
2 Mirror Universe
2 Candelabra of Tawnos
1 Regrowth
1 Wheel of Fortune
1 Mox Emerald
1 Mox Ruby
1 Sol Ring
1 Black Lotus
4 Taiga
4 City of Brass
4 Mishra's Factory
3 Maze of Ith
1 Library of Alexandria
1 Strip Mine
5 Mountain
11 Forest
[Sideboard]
3 Red Elemental Blast
3 Tranquility
3 Crumble
2 Whirling Dervish
2 Fog
2 Tormod's Crypt
```

## Mulligan priorities

Require a card-flow engine or purposeful Fastbond development; many lands without an engine are still a mulligan. Maze does not cast Sylvan, and Fastbond alone cannot replace cards it spends.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 1 Taiga;2 Forest;1 Mountain;1 Fastbond;1 Sylvan Library;1 Fireball | Keep | - | The extra land deployment has a draw engine and a later payoff. |
| H2 | Draw / unknown | 0 | 1 Taiga;2 Forest;1 Mountain;1 Howling Mine;1 Dark Heart of the Wood;1 Fireball | Keep | - | Four real sources and Mine support development, with a life buffer against pressure. |
| H3 | Play or draw / unknown | 0 | 4 Fastbond; 3 Sylvan Library | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 Taiga; 3 City of Brass | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 1 Taiga;2 Forest;1 Mountain;1 Fastbond;1 Sylvan Library;1 Fireball | Keep six | 1 Mountain | Taiga preserves red access while keeping the engine. |
| H6 | Draw / unknown | 2 | 1 Taiga;2 Forest;1 Mountain;1 Howling Mine;1 Dark Heart of the Wood;1 Fireball | Keep five | 1 Mountain;1 Dark Heart of the Wood | Retain three sources, draw and a finisher; reassess the life tool against a known fast opponent. |

## Sequencing and resources

- **Early:** Use Fastbond only for useful extra land drops, and tally its damage. Deploy a draw engine before exhausting a hand for little board impact.
- **Middle:** Maze buys attacks but not mana. Dark Heart converts Forests to life at the cost of your future development; Taiga is eligible but City is not.
- **Late:** Choose a real finishing route. Mirror requires surviving to your upkeep, while Living Plane can expose your whole mana base to creature removal.
- **Mana burn:** Candelabra untaps lands but taps itself, so it is not an automatic unlimited loop. Spend any generated excess before phase end; do not burn to zero while planning a future Mirror.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Maze and life tools buy time; burn or evasive creatures may bypass your nominal land defense. |
| Midrange | Preserve engine cards and convert abundant mana into a relevant Fireball rather than endless setup. |
| Control | Avoid gifting unbounded cards with Mine when they can remove your payoff; protect an engine and a finish. |
| Combo | Land count alone is no disruption; race with a real payoff or use the specific boarded answer. |
| Prison | A land-heavy base is vulnerable to Moon, Orb and Geddon; retain removal and do not overextend into a known reset. |

### Named exceptions and common mistakes

- TaxEdge: Fastbond can put you far ahead in land count, enabling the opponent's Land Tax; evaluate the cards that gives them.
- UR Burn: voluntarily lowering life for Library or Mirror can put you in instant-speed lethal range.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| UR Burn / draw | 3 Red Elemental Blast;2 Whirling Dervish | 2 Mirror Universe;1 Living Plane;2 Candelabra of Tawnos | Trim fragile slow setup and create alternate pressure; Dervish is not red protection. |
| Artifact Aggro / play | 3 Crumble;2 Fog | 2 Mirror Universe;2 Candelabra of Tawnos;1 Howling Mine | Survive fast robot development rather than accelerating both players' draw. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / lands-D1 | Fastbond permits another land but no useful play follows | Consider retaining the land and avoiding damage | Additional mana or hand-size goals can justify it | Legal extra deployment is not automatically valuable (H) |
| 90 / lands-D2 | Mirror is planned while your life is near zero | Preserve survival through the opponent's turn and next upkeep | Lich is not present in this fixture | You cannot wait at zero life (R) |
| 80 / lands-D3 | Living Plane would expose many of your lands | Evaluate sweeper and summoning-sickness consequences before casting | A concrete winning line can justify the risk | The engine can attack its own mana (H) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Your only lands are two Mazes and a Forest. You cannot count three mana for a spell; Maze has no ordinary mana ability.
- **S2** You are at 1 with Mirror on the battlefield. Intentionally burning one mana before your next upkeep loses the game; Mirror is not a delayed rescue.

## Evidence and implementation boundary

- [Wak-Wak: Lands](https://www.wak-wak.se/9394decks/lands): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Fastbond](../../forge-gui/res/cardsfolder/f/fastbond.txt), [Sylvan Library](../../forge-gui/res/cardsfolder/s/sylvan_library.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
