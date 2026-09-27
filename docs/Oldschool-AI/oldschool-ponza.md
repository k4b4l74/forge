# Ponza

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `ponza` · Family: **Control** · Rules: `swedish-fe-burn-london-v1`
Aliases: RGB land destruction, Land destruction.

## Identity and construction

Repeated land destruction plus a clock constrains development. In this catalog the strategy may use black, red and green; it is not simply a modern mono-red Ponza list.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/ponza) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

The RGB fixture combines Sinkhole, Stone Rain, Ice Storm and artifact-mana removal with threats. It uses only one Strip Mine; nonland acceleration is a deliberate opposing escape route.

```forge-deck
[Main]
4 Birds of Paradise
3 Hypnotic Specter
3 Erhnam Djinn
4 Dark Ritual
4 Sinkhole
3 Stone Rain
3 Ice Storm
4 Lightning Bolt
2 Crumble
1 Demonic Tutor
1 Mind Twist
1 Regrowth
1 Mox Jet
1 Mox Emerald
1 Black Lotus
4 Badlands
4 Bayou
3 Taiga
3 City of Brass
2 Mishra's Factory
1 Strip Mine
2 Mountain
2 Swamp
3 Forest
[Sideboard]
3 Red Elemental Blast
3 Tranquility
2 Crumble
3 Terror
2 Earthquake
2 Tormod's Crypt
```

## Mulligan priorities

Require a colored casting sequence, not only land-destruction density. Sinkhole needs BB, Ice Storm G and Stone Rain R; three differently colored spells with one off-color source are not interchangeable plays.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 1 Swamp;1 Bayou;1 Badlands;1 Birds of Paradise;1 Sinkhole;1 Stone Rain;1 Hypnotic Specter | Keep | - | The colors support denial and a threat rather than denial alone. |
| H2 | Draw / unknown | 0 | 1 Bayou;1 Badlands;1 Taiga;1 Birds of Paradise;1 Lightning Bolt;1 Ice Storm;1 Erhnam Djinn | Keep | - | Fixing and a removal spell bridge into disruption plus pressure. |
| H3 | Play or draw / unknown | 0 | 4 Birds of Paradise; 3 Hypnotic Specter | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 Badlands; 3 Bayou | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 1 Swamp;1 Bayou;1 Badlands;1 Birds of Paradise;1 Sinkhole;1 Stone Rain;1 Hypnotic Specter | Keep six | 1 Stone Rain | Keep the cheaper denial and a clock. |
| H6 | Draw / unknown | 2 | 1 Bayou;1 Badlands;1 Taiga;1 Birds of Paradise;1 Lightning Bolt;1 Ice Storm;1 Erhnam Djinn | Keep five | 1 Ice Storm;1 Erhnam Djinn | At five, keep mana, Bird and immediate interaction rather than risking an uncastable top-heavy draw. |

## Sequencing and resources

- **Early:** Target the land that changes available plays: a missing color, Library or a critical untap source. Do not automatically choose the first nonbasic.
- **Middle:** Deploy a clock before endlessly exchanging cards for lands. Crumble can attack Mox/Vault acceleration that otherwise bypasses your plan.
- **Late:** Once the opponent has abundant mana, redirect attention to board and hand pressure; a late Stone Rain often does less than an attack backed by removal.
- **Mana burn:** Ritual cannot pay the red or green symbol of a denial spell. Plan the exact mixed-color sequence before producing BBB.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | A creature already on the table ignores land denial; use Bolt and blockers before falling behind. |
| Midrange | Deny a bottleneck color while establishing a threat; mana creatures are competing targets. |
| Control | Library is a high-value land, but several rocks can make ordinary land destruction low impact. |
| Combo | Determine whether the engine needs lands at all; artifact mana may demand Crumble instead. |
| Prison | Avoid a lock that prevents you spending your own denial spells; preserve broad green removal after board. |

### Named exceptions and common mistakes

- Robots: Workshop is restricted but their Vaults/Moxen still cast creatures; a land-only plan can miss their whole economy.
- Mono Green: mana creatures can undo a Sinkhole turn; remove the acceleration that matters rather than fetishizing lands.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| White Weenie / draw | 2 Earthquake;3 Terror | 3 Stone Rain;2 Ice Storm | Catch up to the board; Terror cannot target protection-from-black creatures, so choose legal threats. |
| The Deck / play | 3 Red Elemental Blast;3 Tranquility | 4 Lightning Bolt;2 Crumble | Against a lock-heavy creature-light build, protect threats and answer enchantments rather than burn face. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / ponza-D1 | Several lands are legal destruction targets | Choose the loss that most reduces the opponent's useful plays | A visible alternate mana source can erase that value | Attack a bottleneck, not raw land count (H) |
| 90 / ponza-D2 | Opponent has already deployed lethal attackers | Prioritize removal or blocking over routine denial | A land removal that directly prevents lethal activation can be an exception | Tempo depends on the present board (H) |
| 80 / ponza-D3 | Your denial hand has no clock and opponent has spare mana | Deploy a threat when possible | Must-answer engine denial still takes precedence | Mana denial needs a finish (H) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Opponent has one Tundra, two Islands and multiple white cards publicly revealed. Destroying Tundra can deny W; removing a redundant Island may not change their plays.
- **S2** Opponent has three Moxen and a Su-Chi; your only play is Stone Rain on a land. Do not score it as removing all of their ability to develop.

## Evidence and implementation boundary

- [Wak-Wak: Ponza](https://www.wak-wak.se/9394decks/ponza): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Birds of Paradise](../../forge-gui/res/cardsfolder/b/birds_of_paradise.txt), [Hypnotic Specter](../../forge-gui/res/cardsfolder/h/hypnotic_specter.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
