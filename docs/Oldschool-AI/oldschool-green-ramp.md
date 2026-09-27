# Green Ramp

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `green-ramp` · Family: **Midrange** · Rules: `swedish-fe-burn-london-v1`
Aliases: Mono Green Ramp, Big Green.

## Identity and construction

Green mana development enables oversized threats and mana sinks. It differs from the imported Mono Green tempo list, which has more small creatures and land disruption.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/green-ramp) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

Elves, Birds, Wild Growth and Gaea's Touch support Erhnam, Triskelion, Bees and Force of Nature. Basic Forest density makes Touch useful; no assumption of later untap engines or draw spells.

```forge-deck
[Main]
4 Llanowar Elves
4 Birds of Paradise
4 Wild Growth
2 Gaea's Touch
4 Erhnam Djinn
3 Triskelion
2 Force of Nature
2 Killer Bees
3 Sylvan Library
1 Regrowth
1 Sol Ring
1 Mox Emerald
1 Black Lotus
1 Chaos Orb
2 Mishra's Factory
1 Strip Mine
24 Forest
[Sideboard]
3 Crumble
3 Tranquility
3 Whirling Dervish
2 Hurricane
2 Avoid Fate
2 Tormod's Crypt
```

## Mulligan priorities

Keep ramp plus a payoff, not only one half. Two Forests and an Elf are more reliable than a one-Forest hand relying on multiple auras against known removal or land destruction.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 2 Forest;1 Llanowar Elves;1 Wild Growth;1 Erhnam Djinn;1 Sylvan Library;1 Triskelion | Keep | - | The mana curve reaches a threat while retaining an engine. |
| H2 | Draw / unknown | 0 | 3 Forest;1 Birds of Paradise;1 Erhnam Djinn;1 Killer Bees;1 Force of Nature | Keep | - | Three lands and Bird provide development with a useful mana sink. |
| H3 | Play or draw / unknown | 0 | 4 Llanowar Elves; 3 Birds of Paradise | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 7 Forest | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 2 Forest;1 Llanowar Elves;1 Wild Growth;1 Erhnam Djinn;1 Sylvan Library;1 Triskelion | Keep six | 1 Triskelion | Preserve the achievable four-drop rather than the more distant six-drop. |
| H6 | Draw / unknown | 2 | 3 Forest;1 Birds of Paradise;1 Erhnam Djinn;1 Killer Bees;1 Force of Nature | Keep five | 1 Force of Nature;1 Killer Bees | At five, reliable mana plus Bird and Erhnam remains functional. |

## Sequencing and resources

- **Early:** Prefer a ramp sequence that still functions if one creature or enchanted land is removed. Don't put all Growths on one exposed land without a benefit.
- **Middle:** Choose the payoff fitting the board: Triskelion for small creatures, a large blocker to stabilize, Bees for evasion and excess mana.
- **Late:** Force of Nature needs GGGG every upkeep or deals damage. Keep that obligation separate from mana you intend to spend on new spells.
- **Mana burn:** Bees are a legal pump sink but cannot spend future-phase mana early. Touch can be sacrificed for GG only when that mana has a useful expenditure; multiplying mana is not itself value.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Become a blocker deck first; Triskelion turns surplus mana into removal. |
| Midrange | Larger threats can dominate the ground, but protection, evasion and theft change their value. |
| Control | Do not commit every payoff into Wrath; answer Moat/Abyss with Tranquility after board. |
| Combo | A slow giant is not disruption; accelerate a short clock and find a relevant answer. |
| Prison | Mana creatures help against land untap restrictions, but enchantment removal must be cast before you lose all access. |

### Named exceptions and common mistakes

- UR Burn: Force's upkeep and Library payments can put you within burn range despite huge blockers.
- The Deck: Control Magic stealing a large creature changes both clocks; choose whether a smaller diversified board is safer.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| The Deck / play | 3 Tranquility;2 Avoid Fate | 2 Force of Nature;2 Gaea's Touch;1 Wild Growth | Reduce clumsy upkeep threats and excess acceleration; Fate is only narrow targeted-spell protection. |
| Artifact Aggro / draw | 3 Crumble | 2 Gaea's Touch;1 Sylvan Library | Use cheap interaction before their acceleration overwhelms your board. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / green-ramp-D1 | Force of Nature upkeep is approaching | Reserve GGGG or budget the eight damage explicitly | Damage prevention may change the result; mana burn does not pay upkeep | Upkeep is part of the threat's cost (R) |
| 90 / green-ramp-D2 | Several threats are castable from surplus mana | Choose the one solving the current board, not largest mana value | Immediate lethal or a needed evasive clock overrides | Convert ramp into relevant impact (H) |
| 80 / green-ramp-D3 | A Growth-stacked land is the only productive source | Avoid unnecessary further concentration | Efficiency may justify risk if a win is imminent | Land denial can erase multiple cards (H) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** You control Force of Nature at 8 life with no prevention and cannot pay GGGG. Its upkeep damage is lethal; don't spend the needed green on an optional activation first.
- **S2** Opponent has three 1/1 attackers and you can cast either Erhnam or Triskelion. Prefer testing Triskelion's immediate removal line over evaluating creatures only by printed power.

## Evidence and implementation boundary

- [Wak-Wak: Green Ramp](https://www.wak-wak.se/9394decks/green-ramp): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Llanowar Elves](../../forge-gui/res/cardsfolder/l/llanowar_elves.txt), [Birds of Paradise](../../forge-gui/res/cardsfolder/b/birds_of_paradise.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
