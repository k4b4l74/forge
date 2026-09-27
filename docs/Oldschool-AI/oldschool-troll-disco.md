# Troll Disco

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `troll-disco` · Family: **Midrange** · Rules: `swedish-fe-burn-london-v1`
Aliases: Troll Disk, Disco Troll, Troll Disco.

## Identity and construction

Sedge Troll survives a planned Nevinyrral's Disk reset through regeneration while much of the opposing board disappears. Spare black mana is an essential component of the engine.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/troll-disco) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

A BR FE-enabled list combines Troll/Specter and Hymn with four Disks. It omits enchantment-based pumps because Disk destroys them, and does not copy the imported incomplete 55-card list.

```forge-deck
[Main]
4 Sedge Troll
4 Hypnotic Specter
4 Order of the Ebon Hand
4 Nevinyrral's Disk
4 Dark Ritual
4 Hymn to Tourach
4 Lightning Bolt
2 Fireball
1 Demonic Tutor
1 Mind Twist
1 Sol Ring
1 Mox Jet
1 Mox Ruby
1 Black Lotus
4 Badlands
3 City of Brass
2 Mishra's Factory
1 Strip Mine
3 Mountain
11 Swamp
[Sideboard]
3 Red Elemental Blast
3 Shatter
3 Gloom
2 Terror
2 Earthquake
2 Tormod's Crypt
```

## Mulligan priorities

Require colored development and a play before a slow Disk setup. Multiple Disks without a creature or survival tool invite free attacks. A Troll without a Swamp is smaller and cannot regenerate without black mana.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 1 Swamp;1 Badlands;1 Dark Ritual;1 Hypnotic Specter;1 Sedge Troll;1 Lightning Bolt;1 Nevinyrral's Disk | Keep | - | Specter can start early while Bolt and the Troll/Disk plan bridge the game. |
| H2 | Draw / unknown | 0 | 1 Swamp;1 Badlands;1 Mountain;1 Sedge Troll;1 Lightning Bolt;1 Hymn to Tourach;1 Nevinyrral's Disk | Keep | - | Stable colors support interaction before the reset. |
| H3 | Play or draw / unknown | 0 | 4 Sedge Troll; 3 Hypnotic Specter | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 Badlands; 3 City of Brass | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 1 Swamp;1 Badlands;1 Dark Ritual;1 Hypnotic Specter;1 Sedge Troll;1 Lightning Bolt;1 Nevinyrral's Disk | Keep six | 1 Nevinyrral's Disk | At six, preserve immediate development rather than a tapped four-drop. |
| H6 | Draw / unknown | 2 | 1 Swamp;1 Badlands;1 Mountain;1 Sedge Troll;1 Lightning Bolt;1 Hymn to Tourach;1 Nevinyrral's Disk | Keep five | 1 Nevinyrral's Disk;1 Hymn to Tourach | Keep three lands, Troll and Bolt at five instead of two slow setup cards. |

## Sequencing and resources

- **Early:** Deploy a threat or disruption first when possible; Disk enters tapped and does not immediately protect you.
- **Middle:** Before activating Disk, reserve one black per Troll you intend to save, plus its activation mana. Establish regeneration before resolution.
- **Late:** After the reset, use surviving Trolls and unanimated Factories to finish. Rebuilding with redundant permanents before your own Disk wastes cards.
- **Mana burn:** If black is floated before Disk destroys Mox Jet, spend it on regeneration in the same phase. Do not generate surplus Ritual mana merely to activate a one-mana Disk.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Stabilize with blockers and Bolt; Disk is a catch-up tool only if you live until it untaps. |
| Midrange | Plan an asymmetric reset; opposing regeneration or indestructibility can spoil the expected advantage. |
| Control | Use discard and a clock; Disk can answer enchantments but is vulnerable while tapped. |
| Combo | Pressure plus hand disruption is usually faster than an unanswered combo; Disk may be too slow. |
| Prison | Disk can remove many lock pieces but requires an untap and legal activation under the lock. |

### Named exceptions and common mistakes

- The Deck: Swords exiles Troll despite regeneration, and Wrath forbids it; don't treat Troll as universally resilient.
- White Weenie: do not animate Factory before your Disk resolves, or it becomes an artifact creature destroyed by the sweep.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| The Deck / play | 3 Red Elemental Blast;3 Gloom | 4 Lightning Bolt;2 Fireball | For a confirmed creature-light list, trade burn for control disruption; retain burn if aggressive transformations appear. |
| Artifact Aggro / draw | 3 Shatter | 3 Hymn to Tourach | Against an all-robot build, Terror remains in the sideboard because its targets would be illegal; Shatter directly addresses their board. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / troll-disco-D1 | You can activate Disk and want Troll to survive | Create a regeneration shield before Disk resolves | Exile or no-regeneration effects do not use that shield | Timing and separate black payment matter (R) |
| 90 / troll-disco-D2 | Factory is currently only a land and Disk will resolve | Leave it unanimated through the reset | Animate only if a different urgent legal line requires it | Animation exposes it to the sweep (R) |
| 80 / troll-disco-D3 | A second Disk is in hand while the first is ready | Hold the redundant copy until needed | A replacement against known removal can justify deployment | Avoid destroying your own rebuilding card (H) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Disk is ready; you control Sedge Troll and an untapped Swamp plus enough other activation mana. Spend B to regenerate, then activate Disk; the Troll can survive ordinary destruction.
- **S2** You control an unanimated Factory when Disk resolves. It remains a land and survives; animating it first would expose it to destruction.

## Evidence and implementation boundary

- [Wak-Wak: Troll Disco](https://www.wak-wak.se/9394decks/troll-disco): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Sedge Troll](../../forge-gui/res/cardsfolder/s/sedge_troll.txt), [Hypnotic Specter](../../forge-gui/res/cardsfolder/h/hypnotic_specter.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
