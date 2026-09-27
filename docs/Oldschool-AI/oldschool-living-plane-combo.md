# Living Plane Combo

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `living-plane-combo` · Family: **Prison** · Rules: `swedish-fe-burn-london-v1`
Aliases: Living Plane, Tim lands.

## Identity and construction

Living Plane turns lands into fragile creatures, allowing ping effects and other creature interaction to attack mana. The same transformation exposes your own lands.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/living-plane-combo) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

A UGR fixture uses Prodigal Sorcerer, burn and Icy with Birds as development. Crossroads is intentionally absent: it would also give the opponent's newly played land creatures haste.

```forge-deck
[Main]
4 Living Plane
4 Prodigal Sorcerer
4 Birds of Paradise
3 Fireball
4 Lightning Bolt
3 Counterspell
2 Sylvan Library
2 Icy Manipulator
1 Regrowth
1 Ancestral Recall
1 Time Walk
1 Sol Ring
1 Mox Emerald
1 Mox Sapphire
1 Mox Ruby
1 Black Lotus
1 Chaos Orb
4 Tropical Island
4 Taiga
4 Volcanic Island
3 City of Brass
2 Mishra's Factory
1 Strip Mine
3 Island
4 Forest
[Sideboard]
3 Red Elemental Blast
3 Blue Elemental Blast
3 Crumble
2 Tranquility
2 Control Magic
2 Tormod's Crypt
```

## Mulligan priorities

Keep development and a useful pinger/removal plan, not multiple Planes with no way to exploit them. A three-color opener must still cast its early spells without a surviving Bird.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 1 Tropical Island;1 Taiga;1 Island;1 Birds of Paradise;1 Prodigal Sorcerer;1 Living Plane;1 Lightning Bolt | Keep | - | Fixing and a pinger support the transformation while Bolt buys time. |
| H2 | Draw / unknown | 0 | 1 Tropical Island;1 Volcanic Island;1 Island;1 Lightning Bolt;1 Counterspell;1 Prodigal Sorcerer;1 Living Plane | Keep | - | The hand can play a normal interactive game before committing Plane. |
| H3 | Play or draw / unknown | 0 | 4 Living Plane; 3 Prodigal Sorcerer | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 Tropical Island; 3 Taiga | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 1 Tropical Island;1 Taiga;1 Island;1 Birds of Paradise;1 Prodigal Sorcerer;1 Living Plane;1 Lightning Bolt | Keep six | 1 Living Plane | Keep independent development and interaction before the symmetric engine. |
| H6 | Draw / unknown | 2 | 1 Tropical Island;1 Volcanic Island;1 Island;1 Lightning Bolt;1 Counterspell;1 Prodigal Sorcerer;1 Living Plane | Keep five | 1 Living Plane;1 Prodigal Sorcerer | At five retain mana and immediate answers instead of both setup pieces. |

## Sequencing and resources

- **Early:** Have a ready pinger or a meaningful follow-up before exposing all lands. A freshly cast Sorcerer cannot normally tap immediately.
- **Middle:** Under Plane, newly played lands are creatures affected by summoning sickness for tap-symbol abilities. Older lands may still tap normally.
- **Late:** Remove critical opposing land creatures while protecting your own mana from sweeps. An opposing Mox remains an escape route, and the last land is not necessarily their last mana.
- **Mana burn:** A land creature may tap for mana in response to removal if it is eligible; count that temporary resource and its phase-end burn rather than assuming destruction denies all immediate use.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Small-creature removal becomes land removal for both players; your own mana can collapse under their burn. |
| Midrange | A pinger can constrain mana while Icy buys attacks, but already-developed large threats remain. |
| Control | Plane can turn their creature sweeper into mass land destruction; commit only with a favorable resulting board. |
| Combo | Artifact-based engines may ignore land attrition; counters or artifact removal are still needed. |
| Prison | As a world enchantment, Plane cannot simply coexist with another world lock; evaluate the resulting surviving permanent. |

### Named exceptions and common mistakes

- Troll Disco: Disk after Plane can wipe lands too, leaving a surviving regenerating Troll; this is not automatically good for the Plane player.
- The Abyss: world-rule interaction prevents combining both enchantments indefinitely, regardless of an attractive theoretical land-destruction loop.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| UR Burn / draw | 3 Blue Elemental Blast;2 Control Magic | 3 Living Plane;2 Icy Manipulator | Reduce exposure of your lands and add direct red interaction or an alternate board swing. |
| The Deck / play | 3 Red Elemental Blast;2 Tranquility | 2 Fireball;2 Icy Manipulator;1 Living Plane | Contest permission and enchantment defenses while retaining one Fireball finish. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / living-plane-combo-D1 | Plane is active and a land entered under your control this turn | Check summoning sickness before using a tap-symbol mana ability | Haste or continuous control since turn began changes eligibility | It is now a creature as well as a land (R) |
| 90 / living-plane-combo-D2 | A pinger can destroy one opposing land creature | Choose a relevant mana bottleneck | A creature threatening lethal may be the better target | Denial should change available actions (H) |
| 80 / living-plane-combo-D3 | Casting Plane exposes more of your resources to a known sweeper | Delay or find protection unless the line is still favorable | A forced immediate win can justify exposure | Symmetric vulnerability must be scored (H) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Plane is already active when you play a Forest during your main phase. Without haste it cannot immediately use its tap-symbol mana ability.
- **S2** Your ready Prodigal Sorcerer can damage a 1/1 opposing land creature for one. Account for the opponent floating mana in response before assuming all immediate mana access disappears.

## Evidence and implementation boundary

- [Wak-Wak: Living Plane Combo](https://www.wak-wak.se/9394decks/living-plane-combo): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Living Plane](../../forge-gui/res/cardsfolder/l/living_plane.txt), [Prodigal Sorcerer](../../forge-gui/res/cardsfolder/p/prodigal_sorcerer.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
