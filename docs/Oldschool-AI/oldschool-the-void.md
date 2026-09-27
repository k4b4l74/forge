# The Void

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `the-void` · Family: **Prison** · Rules: `swedish-fe-burn-london-v1`
Aliases: Nether Void, Black prison.

## Identity and construction

Deploy pressure and resource denial, then use Nether Void to make new spells expensive or countered. Activated Factory attacks can bypass the spell trigger.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/the-void) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

The mono-black FE fixture uses Ritual, Hymn and creatures before Void. It omits The Abyss rather than pretending two world enchantments form a persistent combined lock.

```forge-deck
[Main]
4 Hypnotic Specter
4 Black Knight
3 Order of the Ebon Hand
3 Nether Void
4 Dark Ritual
4 Hymn to Tourach
4 Sinkhole
2 Terror
2 Nevinyrral's Disk
1 Demonic Tutor
1 Mind Twist
1 Sol Ring
1 Mox Jet
1 Black Lotus
1 Chaos Orb
4 Mishra's Factory
1 Strip Mine
19 Swamp
[Sideboard]
3 Gloom
2 Terror
2 Nevinyrral's Disk
2 Tormod's Crypt
2 Disrupting Scepter
4 Drain Life
```

## Mulligan priorities

Keep a castable clock and black development, not Void alone. A tax effect with no board advantage may simply stop you from recovering.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 2 Swamp;1 Dark Ritual;1 Hypnotic Specter;1 Black Knight;1 Hymn to Tourach;1 Nether Void | Keep | - | A threat can precede the prison piece with follow-up disruption. |
| H2 | Draw / unknown | 0 | 3 Swamp;1 Black Knight;1 Hymn to Tourach;1 Terror;1 Nether Void | Keep | - | The mana supports a clock and interaction before Void. |
| H3 | Play or draw / unknown | 0 | 4 Hypnotic Specter; 3 Black Knight | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 7 Swamp | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 2 Swamp;1 Dark Ritual;1 Hypnotic Specter;1 Black Knight;1 Hymn to Tourach;1 Nether Void | Keep six | 1 Nether Void | At six keep the functional pre-lock plan before its conditional payoff. |
| H6 | Draw / unknown | 2 | 3 Swamp;1 Black Knight;1 Hymn to Tourach;1 Terror;1 Nether Void | Keep five | 1 Nether Void;1 Swamp | Two sources, threat and relevant disruption are the coherent five. |

## Sequencing and resources

- **Early:** Establish a threat or Factory clock before Void. Compare both players' boards and mana when choosing the lock turn.
- **Middle:** Void creates a triggered counter unless three is paid; it is not an additional casting cost. It triggers for both players' spells but not ordinary activated abilities.
- **Late:** Keep enough pressure to end the game before the opponent pays through the tax or finds a non-spell answer. Do not cast Ritual under Void as if it automatically netted two mana.
- **Mana burn:** An attempted Ritual under Void faces its own pay-three trigger before resolving; its future BBB cannot pay that trigger. Mana already floated to pay a trigger still has a phase boundary.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | A fast opposing board can make Void actively bad; use blockers or Disk first. |
| Midrange | Lock only when your existing board or Factory route wins the resulting race. |
| Control | Disrupt resources, establish pressure, then force answers through the trigger tax. |
| Combo | The trigger can constrain setup spells, but assembled activated engines can ignore it. |
| Prison | World-rule conflicts and existing activated permanents can break the assumed lock. |

### Named exceptions and common mistakes

- Power Monolith: once its ability-based mana engine is active, three extra mana may be trivial; stop assembly instead.
- The Abyss: adding the other world enchantment removes the supposed coexistence plan; keep the world rule in the evaluator.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| White Weenie / draw | 2 Terror;2 Nevinyrral's Disk | 3 Nether Void;1 Sinkhole | Shift from a slow tax plan to surviving an already-developed creature board; Terror still respects protection. |
| The Deck / play | 3 Gloom;2 Disrupting Scepter | 2 Terror;2 Nevinyrral's Disk;1 Order of the Ebon Hand | Improve sustained resource pressure against a confirmed low-creature build. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / the-void-D1 | Void would enter with you behind on board | Prefer stabilizing before locking new spells | A specific opposing spell-dependent lethal line may justify the lock | A symmetric constraint needs a favorable existing position (H) |
| 90 / the-void-D2 | A spell is cast under Void | Put the pay-three counter trigger on the stack | Do not charge it as an invented additional casting cost | The distinction affects responses and payment timing (R) |
| 80 / the-void-D3 | Factory can become a winning attacker under Void | Value its activated route without a spell tax | Activation costs and creature vulnerability still apply | Void triggers on spells, not all actions (R) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Void is active and you cast Dark Ritual with no mana left. You cannot use the unresolved Ritual's future BBB to pay Void's trigger; without another source it will be countered.
- **S2** Activating Factory is not casting a spell, so Void does not create a pay-three trigger for that activation.

## Evidence and implementation boundary

- [Wak-Wak: The Void](https://www.wak-wak.se/9394decks/the-void): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Hypnotic Specter](../../forge-gui/res/cardsfolder/h/hypnotic_specter.txt), [Black Knight](../../forge-gui/res/cardsfolder/b/black_knight.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
