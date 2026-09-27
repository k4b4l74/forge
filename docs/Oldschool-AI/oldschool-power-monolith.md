# Power Monolith

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `power-monolith` · Family: **Combo** · Rules: `swedish-fe-burn-london-v1`
Aliases: Power Artifact combo, Basalt combo.

## Identity and construction

Power Artifact reduces Basalt Monolith's untap cost, producing a repeatable positive-colorless-mana cycle. A colored payoff, legal targets and a finite execution bound are still required.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/power-monolith) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

A UR fixture supplies redundant combo pieces, Transmute and X-spell finishes with permission. It distinguishes mana production from an actual win and avoids pretending unlimited mana is a legal runtime instruction.

```forge-deck
[Main]
4 Basalt Monolith
4 Power Artifact
4 Counterspell
3 Transmute Artifact
3 Fireball
3 Mana Vault
3 Howling Mine
2 Copy Artifact
2 Twiddle
1 Ancestral Recall
1 Time Walk
1 Mana Drain
1 Braingeyser
1 Sol Ring
1 Black Lotus
1 Mox Sapphire
1 Mox Ruby
1 Chaos Orb
1 Disintegrate
4 Volcanic Island
4 City of Brass
3 Mountain
2 Mishra's Factory
1 Strip Mine
8 Island
[Sideboard]
3 Red Elemental Blast
3 Blue Elemental Blast
3 Shatter
2 Control Magic
2 Boomerang
2 Tormod's Crypt
```

## Mulligan priorities

Require blue development, a combo component or draw/search, and a route toward red for the kill. Multiple Basalts without Power Artifact or a useful payoff are expensive mana rocks, not a complete hand.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 1 Volcanic Island;2 Island;1 Basalt Monolith;1 Power Artifact;1 Counterspell;1 Fireball | Keep | - | The full engine and payoff are supported by colors, though setup takes time. |
| H2 | Draw / unknown | 0 | 1 Volcanic Island;2 Island;1 Mana Vault;1 Basalt Monolith;1 Power Artifact;1 Fireball | Keep | - | Acceleration can deploy the engine while the colored finisher is available. |
| H3 | Play or draw / unknown | 0 | 4 Basalt Monolith; 3 Power Artifact | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 Volcanic Island; 3 City of Brass | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 1 Volcanic Island;2 Island;1 Basalt Monolith;1 Power Artifact;1 Counterspell;1 Fireball | Keep six | 1 Counterspell | At six preserve the core while acknowledging the lost protection. |
| H6 | Draw / unknown | 2 | 1 Volcanic Island;2 Island;1 Mana Vault;1 Basalt Monolith;1 Power Artifact;1 Fireball | Keep five | 1 Mana Vault;1 Island | Two sources, the pair and payoff retain a coherent five; it still needs another source or acceleration. |

## Sequencing and resources

- **Early:** Develop UU and the artifact without assuming a resolved combo. Power Artifact is an Aura vulnerable to disruption while being cast and afterward.
- **Middle:** With the Aura attached, tapping Basalt adds three colorless and untapping costs one: each completed cycle nets two and returns it untapped.
- **Late:** Generate only enough for the chosen finish plus justified protection costs. Preserve the red source; colorless quantity cannot replace a missing R or UU.
- **Mana burn:** A positive loop creates a potentially lethal burn liability if executed without a sink. Use a bounded requested amount rather than an 'infinite mana' sentinel.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Assemble quickly but use interaction when a creature will kill you before the setup turn. |
| Midrange | Large creatures impose a deadline; stealing one can buy time without being part of the combo. |
| Control | Protect the Aura/artifact relationship and avoid tapping out into a known answer. |
| Combo | Race through a specific protected payoff, not just the mana engine. |
| Prison | A tax may consume some mana, but an untap or ability restriction can disable the cycle entirely; check the actual effect. |

### Named exceptions and common mistakes

- The Deck: Disenchant can break either relevant permanent; plan priority and remaining resources before starting the cycle.
- Nether Void: paying the trigger tax may be easy with active mana production, but assembling the pair under the tax can be the hard part.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| UR Burn / draw | 3 Blue Elemental Blast;2 Control Magic | 2 Copy Artifact;2 Transmute Artifact;1 Disintegrate | Improve survival and red interaction while retaining three Fireballs. |
| The Deck / play | 3 Red Elemental Blast;2 Boomerang | 2 Twiddle;2 Fireball;1 Mana Vault | Keep a Fireball and Disintegrate while contesting permission and temporary locks. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / power-monolith-D1 | Untapped Basalt has Power Artifact attached with no other modifiers | Model a tap/untap cycle as net two colorless | Check restrictions and cost-changing effects before using the shortcut | Untap cost is reduced from three to one (R) |
| 90 / power-monolith-D2 | The loop has no castable payoff or safe sink | Do not generate arbitrary excess mana | Produce a bounded amount for a specific needed action | Mana without expenditure can lose the game (H) |
| 80 / power-monolith-D3 | A payoff still needs colored mana | Reserve that colored source before looping | Colorless cannot satisfy R or U symbols | Production and payment must be modeled separately (R) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Opponent is at 20 with no relevant responses; you have the active pair, an untapped Mountain and Fireball. Ten completed cycles net 20 colorless, then R supports Fireball for X=20; do not generate unbounded surplus.
- **S2** The pair is active but no red mana is available and Fireball is your only payoff. Unlimited colorless does not make that spell castable.

## Evidence and implementation boundary

- [Wak-Wak: Power Monolith](https://www.wak-wak.se/9394decks/power-monolith): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Basalt Monolith](../../forge-gui/res/cardsfolder/b/basalt_monolith.txt), [Power Artifact](../../forge-gui/res/cardsfolder/p/power_artifact.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
