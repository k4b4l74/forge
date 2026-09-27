# Fork Combo

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `fork-combo` · Family: **Combo** · Rules: `swedish-fe-burn-london-v1`
Aliases: Fork recursion.

## Identity and construction

Fork amplifies key spells while draw and recursion chain extra turns toward a finish. The historical name does not establish an infinite loop under modern card text.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/fork-combo) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

A UGR fixture uses four Forks and Recalls, the single Time Walk, draw engines and Fireball. Every Recall has real mana/discard requirements and its exile clause must be tracked.

```forge-deck
[Main]
4 Fork
4 Recall
4 Howling Mine
2 Sylvan Library
2 Fastbond
3 Fireball
4 Counterspell
3 Mana Vault
1 Regrowth
1 Time Walk
1 Ancestral Recall
1 Timetwister
1 Wheel of Fortune
1 Sol Ring
1 Mox Ruby
1 Mox Sapphire
1 Mox Emerald
1 Black Lotus
1 Chaos Orb
4 Volcanic Island
4 Tropical Island
3 City of Brass
2 Taiga
1 Strip Mine
9 Island
[Sideboard]
3 Red Elemental Blast
3 Blue Elemental Blast
3 Shatter
2 Tranquility
2 Boomerang
2 Tormod's Crypt
```

## Mulligan priorities

Seek development and card flow rather than Forks without copy targets. RR, U and green recovery compete; colorless acceleration alone cannot start the chain.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 1 Volcanic Island;1 Tropical Island;1 Island;1 Howling Mine;1 Counterspell;1 Fork;1 Fireball | Keep | - | Stable development can establish draw while holding interaction; Fork is a future tool. |
| H2 | Draw / unknown | 0 | 1 Volcanic Island;1 Tropical Island;1 Island;1 Mana Vault;1 Howling Mine;1 Recall;1 Fireball | Keep | - | Mana and draw can build resources before recursion matters. |
| H3 | Play or draw / unknown | 0 | 4 Fork; 3 Recall | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 Volcanic Island; 3 Tropical Island | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 1 Volcanic Island;1 Tropical Island;1 Island;1 Howling Mine;1 Counterspell;1 Fork;1 Fireball | Keep six | 1 Fireball | Keep the engine and interaction; a finisher can be found later. |
| H6 | Draw / unknown | 2 | 1 Volcanic Island;1 Tropical Island;1 Island;1 Mana Vault;1 Howling Mine;1 Recall;1 Fireball | Keep five | 1 Recall;1 Fireball | At five retain the setup rather than dependent recovery and finishers. |

## Sequencing and resources

- **Early:** Develop enough colored mana and cards to make copying worthwhile; do not spend Fork just because a legal target appears.
- **Middle:** Copy Time Walk only while it is on the stack. Model additional turns, draw capacity, recursion payments and cards discarded rather than asserting a loop from card names.
- **Late:** When enough mana and damage are available, finish instead of taking redundant turns. Keep finite action bounds for any future automated planner.
- **Mana burn:** Vault adds colorless but Fork requires RR. Unspent accelerated mana can burn before the next turn, so extra turns are not a way to carry the pool forward.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | The setup is slow; prioritize survival and a timely turn chain over maximum theoretical card advantage. |
| Midrange | Use extra turns to convert resources into an actual kill before large creatures race you. |
| Control | Protect key spells and avoid giving better Mine draws to the opponent without compensation. |
| Combo | Identify which engine is faster and hold permission for the opposing payoff. |
| Prison | Mana denial can prevent the colored copy chain even when artifacts make abundant colorless. |

### Named exceptions and common mistakes

- Twiddlevault: both use extra turns, but Vault/Twiddle is a permanent-based untap engine; don't transfer its sequencing to Fork.
- UR Burn: attempting a long chain at low life risks dying to instant burn; a smaller protected finish can be better.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| UR Burn / draw | 3 Blue Elemental Blast;3 Red Elemental Blast | 2 Fastbond;2 Sylvan Library;2 Recall | Reduce life payments and clumsy recursion while improving interaction. |
| The Deck / play | 3 Red Elemental Blast;2 Tranquility | 2 Fastbond;2 Fireball;1 Mana Vault | Keep one main finisher and recursion while contesting counters and lock enchantments. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / fork-combo-D1 | Time Walk is on the stack and Fork is available with RR | Consider copying when the extra turn is useful | Copying is impossible after the original resolves | Fork needs a spell on the stack (R) |
| 90 / fork-combo-D2 | A proposed recursion loop consumes mana, cards or exiles its own pieces | Track the decreasing resources and terminate the plan | Only a proven repeatable state can justify a loop shortcut | Do not hallucinate infinite turns (H) |
| 80 / fork-combo-D3 | A direct finish is available through known effects | Prefer it over unnecessary engine iterations | Preserve protection when needed | Bound runtime and reduce failure opportunities (H) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Time Walk is still on the stack; you cast Fork targeting it and both resolve without interference. Account for two extra-turn effects, not permission to keep copying a resolved card.
- **S2** A proposed line repeatedly casts Recall but never decrements discard fodder or accounts for exile. Reject that line as an unproven loop.

## Evidence and implementation boundary

- [Wak-Wak: Fork Combo](https://www.wak-wak.se/9394decks/fork-combo): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Fork](../../forge-gui/res/cardsfolder/f/fork.txt), [Recall](../../forge-gui/res/cardsfolder/r/recall.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
