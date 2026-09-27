# Distress

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `distress` · Family: **Control** · Rules: `swedish-fe-burn-london-v1`
Aliases: Black attrition control.

## Identity and construction

Noncombat attrition uses enchantments and artifact engines to constrain resources and life. It is broader than a deck merely containing The Rack.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/distress) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

The mono-black fixture combines Dreams, Howling Mine/Relic Barrier, Warp Artifact and Pestilence with Disk. It intentionally exposes the tension between symmetric draw, life loss and board control.

```forge-deck
[Main]
4 Underworld Dreams
3 Warp Artifact
3 Pestilence
4 Howling Mine
3 Relic Barrier
4 Black Vise
4 Dark Ritual
4 Sinkhole
2 Nevinyrral's Disk
1 Demonic Tutor
1 Mind Twist
1 Sol Ring
1 Mox Jet
1 Black Lotus
4 Mishra's Factory
1 Strip Mine
19 Swamp
[Sideboard]
3 Gloom
3 Terror
3 Drain Life
2 Tormod's Crypt
2 Nevinyrral's Disk
2 Disrupting Scepter
```

## Mulligan priorities

Keep a functional engine or defensive development, not several expensive enchantments with no tempo. Dreams needs BBB; Factory-heavy hands are especially misleading.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 2 Swamp;1 Dark Ritual;1 Underworld Dreams;1 Howling Mine;1 Black Vise;1 Relic Barrier | Keep | - | Black mana and multiple connected engine pieces create a plan. |
| H2 | Draw / unknown | 0 | 3 Swamp;1 Dark Ritual;1 Pestilence;1 Howling Mine;1 Black Vise | Keep | - | Stable black development can reach a sweeper-like control tool. |
| H3 | Play or draw / unknown | 0 | 4 Underworld Dreams; 3 Warp Artifact | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 7 Swamp | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 2 Swamp;1 Dark Ritual;1 Underworld Dreams;1 Howling Mine;1 Black Vise;1 Relic Barrier | Keep six | 1 Relic Barrier | Retain the core draw/damage relationship and early Vise. |
| H6 | Draw / unknown | 2 | 3 Swamp;1 Dark Ritual;1 Pestilence;1 Howling Mine;1 Black Vise | Keep five | 1 Swamp;1 Howling Mine | At five, retain a route to Pestilence and early pressure rather than gifting cards too soon. |

## Sequencing and resources

- **Early:** Vise matters early; Dreams matters as draw events accumulate. Do not deploy Mine into an opponent better able to exploit it without compensation.
- **Middle:** Relic Barrier can tap Mine before the opponent's draw to deny its extra draw, but that also denies Dreams damage from that draw. Choose the objective explicitly.
- **Late:** Pestilence damages both players and can disappear when no creatures remain. Plan its end-step condition instead of assuming a permanent board lock.
- **Mana burn:** Pestilence is a sink only at the price of damage to both players and creatures. Avoid converting harmless spare mana into lethal self-damage just to prevent burn.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Stabilize with Pestilence/Disk; Vise gets worse once their hand empties. |
| Midrange | Attrition works only if large or protected threats cannot race it; use reset timing carefully. |
| Control | Scepter and Dreams pressure resources, but white removal can undo the expensive enchantments. |
| Combo | Mine may accelerate an opposing engine; restrict draws or remove it when the opponent benefits more. |
| Prison | Noncombat clocks bypass some attack locks, but mana taxes may prevent you establishing them. |

### Named exceptions and common mistakes

- Mono Black: Warp Artifact may lack an opposing artifact target; don't mistake enchantments for standalone damage sources.
- The Rack imported list: its creature/discard focus is different; this guide should not automatically overwrite that deck's midrange decisions.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| White Weenie / draw | 3 Terror;2 Nevinyrral's Disk | 3 Warp Artifact;2 Sinkhole | Increase board interaction; use Terror only on legal nonprotected creatures. |
| The Deck / play | 3 Gloom;2 Disrupting Scepter | 3 Pestilence;2 Nevinyrral's Disk | Against creature-light control, pressure their hand and white answers instead of symmetric sweeps. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / distress-D1 | Mine and Dreams are active and you can tap Mine before opposing draw | Compare denying a card with allowing damage | Near-lethal Dreams damage can justify the extra draw | The two objectives can conflict (H) |
| 90 / distress-D2 | Pestilence activation damages both players | Check your own survival before using it as a mana sink | A draw may be preferable to a certain loss but is not a win | Symmetric damage must be scored (R) |
| 80 / distress-D3 | No creatures remain as Pestilence's end-step condition matters | Do not assume it persists indefinitely | A legal animated Factory can affect creature presence, but costs and timing matter | The enchantment has a maintenance condition (R) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Opponent is at 10 and has a strong draw-dependent engine; Mine/Dreams would give one extra card and one extra damage. The policy must be able to prefer tapping Mine to deny the card.
- **S2** Both players are at 1 with no relevant prevention. Activating Pestilence for damage is not a solo win; account for simultaneous lethal.

## Evidence and implementation boundary

- [Wak-Wak: Distress](https://www.wak-wak.se/9394decks/distress): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Underworld Dreams](../../forge-gui/res/cardsfolder/u/underworld_dreams.txt), [Warp Artifact](../../forge-gui/res/cardsfolder/w/warp_artifact.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
