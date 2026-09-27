# UR Burn

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `ur-burn` · Family: **Aggro** · Rules: `swedish-fe-burn-london-v1`
Aliases: Counterburn, UR Control, Blue Red Burn.

## Identity and construction

Evasive pressure and burn combine with permission. A creature-heavy Counterburn build and a slower Scepter control build share tools but require different threat and resource priorities.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/ur-burn) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

The fixture selects Efreet/Flying Men pressure plus counters, with a basic-heavy mana base for sideboard Moon. It is authored, not Stephen Menendian's four-Strip-Mine EC tournament list.

```forge-deck
[Main]
4 Serendib Efreet
3 Flying Men
4 Lightning Bolt
4 Chain Lightning
3 Psionic Blast
4 Counterspell
2 Shatter
1 Ancestral Recall
1 Time Walk
1 Braingeyser
1 Mana Drain
1 Mox Sapphire
1 Mox Ruby
1 Black Lotus
1 Chaos Orb
4 Volcanic Island
3 Mishra's Factory
1 Strip Mine
5 Mountain
15 Island
[Sideboard]
3 Red Elemental Blast
3 Blue Elemental Blast
3 Energy Flux
2 Control Magic
2 Blood Moon
2 Tormod's Crypt
```

## Mulligan priorities

Keep a blue/red development path and either pressure or immediate interaction. Colorless lands do not cast UU; one land with Ancestral is a risk-aware exception, not a universal keep rule.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 1 Island;1 Volcanic Island;1 Mountain;1 Flying Men;1 Serendib Efreet;1 Lightning Bolt;1 Counterspell | Keep | - | Both pressure and interaction are supported without relying on a draw spell. |
| H2 | Draw / unknown | 0 | 1 Island;1 Volcanic Island;1 Serendib Efreet;1 Lightning Bolt;1 Chain Lightning;1 Counterspell;1 Psionic Blast | Keep | - | Cheap removal bridges toward Efreet while keeping UU access. |
| H3 | Play or draw / unknown | 0 | 4 Serendib Efreet; 3 Flying Men | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 Volcanic Island; 3 Mountain | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 1 Island;1 Volcanic Island;1 Mountain;1 Flying Men;1 Serendib Efreet;1 Lightning Bolt;1 Counterspell | Keep six | 1 Serendib Efreet | Keep early action and stable mana for the counter. |
| H6 | Draw / unknown | 2 | 1 Island;1 Volcanic Island;1 Serendib Efreet;1 Lightning Bolt;1 Chain Lightning;1 Counterspell;1 Psionic Blast | Keep five | 1 Psionic Blast;1 Chain Lightning | Retain two lands, a clock, Bolt and permission rather than redundant burn. |

## Sequencing and resources

- **Early:** Against a fast creature opener, remove what shortens your life clock before deploying a flier; against slow control, establish pressure.
- **Middle:** Protect Efreet only when it is the clock that matters. Counter a card engine or decisive hate rather than a low-impact spell merely because UU is untapped.
- **Late:** Switch from board control to face damage when a verified burn sequence wins. Keep enough life for Efreet and Blast recoil.
- **Mana burn:** Mana Drain adds colorless in a later main phase, not immediately. A large countered spell can force burn if Braingeyser, creatures or other generic costs cannot spend it.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Burn their efficient attackers and stabilize before racing; a flier need not attack into a losing return clock. |
| Midrange | Counter or steal large threats rather than always using two burn spells. |
| Control | Use a clock and protect resources; decide whether to stop Tome or its support rather than countering every spell. |
| Combo | Hold permission for the critical engine or payoff while attacking. |
| Prison | Moon is not a universal lock answer; Flux is useful against artifacts but taxes your own Moxen. |

### Named exceptions and common mistakes

- Candleflare: symmetrical mana multiplication can turn defensive taps into mana-burn liabilities; a pilot report illustrates this hazard, but its results do not establish this fixture's matchup.
- The Deck: Swords and white enchantments are not countered by Red Elemental Blast. Preserve Counterspell for nonblue must-answer cards.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| White Weenie / draw | 2 Control Magic | 2 Shatter | Against a confirmed low-artifact white list, replace narrow artifact removal with a stabilizing creature swing; retain Shatter if their build has substantial artifacts. |
| The Deck / play | 3 Red Elemental Blast;2 Blood Moon | 3 Psionic Blast;2 Chain Lightning | Reduce recoil and low-flexibility burn while improving blue fights and mana pressure. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / ur-burn-D1 | Countering a large spell with Mana Drain is optional | Forecast the next main phase's colorless expenditure | Stopping lethal takes priority over avoiding burn | Delayed mana is not automatically pure upside (H) |
| 90 / ur-burn-D2 | A burn spell can target a blocker or opponent | Choose the target that best improves the verified clock | Remove an immediate lethal attacker before speculative reach | Role changes with the race (H) |
| 80 / ur-burn-D3 | Opponent presents a nonblue must-answer spell | Do not count Red Elemental Blast as an answer | Use valid permission or another legal interaction | Color-specific counters are not universal (R) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** You Mana Drain a six-mana spell and later receive six colorless, but can spend only two before your main phase ends. Forecast four life lost under this contract.
- **S2** Opponent casts Moat; your only counter-like card is Red Elemental Blast. It cannot counter that white spell.

## Evidence and implementation boundary

- [Wak-Wak: UR Burn](https://www.wak-wak.se/9394decks/ur-burn): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.
- [Stephen Menendian, Eternal Weekend 2017 report](https://www.eternalcentral.com/three-in-a-row-top-8-at-eternal-weekend-2017-old-school-tournament/): pilot discussion of control roles and a mana-burn game; EC restrictions differ from this contract.

Local card definitions: [Serendib Efreet](../../forge-gui/res/cardsfolder/s/serendib_efreet.txt), [Flying Men](../../forge-gui/res/cardsfolder/f/flying_men.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
