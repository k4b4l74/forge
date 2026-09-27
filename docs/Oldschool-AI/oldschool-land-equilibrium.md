# Land Equilibrium

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `land-equilibrium` · Family: **Prison** · Rules: `swedish-fe-burn-london-v1`
Aliases: Equilibrium Vortex.

## Identity and construction

Land Equilibrium and Mana Vortex constrain land development while artifact mana supports a Vise/Mine plan. This is a resource imbalance, not a rule saying opponents cannot play lands.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/2018-2-11/land-equilibrium) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

The blue artifact-prison fixture uses all five singleton Moxen for nonland mana, with Vortex and Relic Barrier. Several Mox colors do not cast UU spells; count real blue sources.

```forge-deck
[Main]
4 Land Equilibrium
3 Mana Vortex
4 Howling Mine
4 Relic Barrier
4 Black Vise
4 Counterspell
3 Mana Vault
3 Copy Artifact
2 Icy Manipulator
1 Ancestral Recall
1 Time Walk
1 Sol Ring
1 Mox Sapphire
1 Mox Pearl
1 Mox Jet
1 Mox Ruby
1 Mox Emerald
1 Black Lotus
1 Chaos Orb
4 City of Brass
3 Mishra's Factory
1 Strip Mine
11 Island
[Sideboard]
3 Blue Elemental Blast
3 Control Magic
3 Hurkyl's Recall
2 Boomerang
2 Tormod's Crypt
2 Jayemdae Tome
```

## Mulligan priorities

Keep blue development and useful artifact mana/interaction. Equilibrium without asymmetry is a four-mana enchantment that may not constrain the opponent at all.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 2 Island;1 Mox Sapphire;1 Howling Mine;1 Relic Barrier;1 Land Equilibrium;1 Counterspell | Keep | - | Nonland blue supports an eventual low-land plan with interaction. |
| H2 | Draw / unknown | 0 | 3 Island;1 Mana Vault;1 Black Vise;1 Land Equilibrium;1 Counterspell | Keep | - | The hand can develop while retaining a pressure piece. |
| H3 | Play or draw / unknown | 0 | 4 Land Equilibrium; 3 Mana Vortex | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 City of Brass; 3 Island | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 2 Island;1 Mox Sapphire;1 Howling Mine;1 Relic Barrier;1 Land Equilibrium;1 Counterspell | Keep six | 1 Relic Barrier | Retain mana, draw, permission and the principal constraint. |
| H6 | Draw / unknown | 2 | 3 Island;1 Mana Vault;1 Black Vise;1 Land Equilibrium;1 Counterspell | Keep five | 1 Land Equilibrium;1 Island | At five, preserve two lands, acceleration, Vise and permission rather than insisting on the slow lock. |

## Sequencing and resources

- **Early:** Develop enough nonland mana before lowering your land count. Land drops can weaken the asymmetry and are not automatically correct.
- **Middle:** Equilibrium is a replacement: when its pre-entry comparison applies, the opponent puts the land in and then sacrifices a land. They choose the sacrifice; it need not be the new one.
- **Late:** Vortex has a cast trigger requiring a sacrifice and upkeep sacrifices afterward; it goes away when no lands remain. Close with Vise or Factory instead of assuming permanent total mana denial.
- **Mana burn:** Nonblue Moxen do not solve UU. Don't float mana expecting a response between Equilibrium's entry and sacrifice within its replacement process.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Already-deployed attackers bypass much of the lock; stabilize or steal them before overinvesting in mana denial. |
| Midrange | Nonland mana on both sides determines who benefits; large existing threats still require an answer. |
| Control | Restrict meaningful colors and card use, but expect Disenchant and artifact acceleration. |
| Combo | Many artifact combos can escape land denial; target their operational mana or engine. |
| Prison | Mirror-like prison games depend on usable mana, not land totals alone. |

### Named exceptions and common mistakes

- Robots: mana rocks and a preexisting robot can make your land lock largely irrelevant.
- The Deck: the historical source describes 'can't play lands' informally; implementation must use the current replacement text, not that shorthand.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| UR Burn / draw | 3 Blue Elemental Blast;2 Control Magic | 3 Mana Vortex;2 Land Equilibrium | Reduce slow land-denial setup when immediate survival is more important. |
| Artifact Aggro / play | 3 Hurkyl's Recall;3 Control Magic | 3 Mana Vortex;3 Land Equilibrium | Use board interaction against opponents whose mana and threats bypass land constraints. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / land-equilibrium-D1 | Opponent would put in a land with at least as many lands as you before entry | Apply entry-then-sacrifice replacement logic | No prohibition on playing it and no invented response window between those actions | Use current card text (R) |
| 90 / land-equilibrium-D2 | Vortex would remove your last usable colored source | Reassess whether the asymmetry actually favors you | A secured finish may justify the sacrifice | Do not lock yourself out first (H) |
| 80 / land-equilibrium-D3 | Opponent already has artifact mana and a lethal board | Prioritize a real board answer rather than more land denial | A mana restriction that stops lethal can still matter | Existing resources survive the nominal lock (H) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** You have one land and opponent has one before playing a second. Equilibrium applies: the new land enters, then that player sacrifices a land of their choice. Do not forbid the land play.
- **S2** Opponent controls three Moxen but no lands. Equilibrium does not disable those Mox abilities; never score the position as zero usable mana.

## Evidence and implementation boundary

- [Wak-Wak: Land Equilibrium](https://www.wak-wak.se/9394decks/2018-2-11/land-equilibrium): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Land Equilibrium](../../forge-gui/res/cardsfolder/l/land_equilibrium.txt), [Mana Vortex](../../forge-gui/res/cardsfolder/m/mana_vortex.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
