# Black Red Aggro

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `br-aggro` · Family: **Aggro** · Rules: `swedish-fe-burn-london-v1`
Aliases: BR Aggro, Rakdos aggro.

## Identity and construction

Black disruption and efficient threats combine with red removal and reach. Compared with Mono Black, Bolt helps against small creatures and offers a finish without combat.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/br-aggro) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

This FE-enabled fixture uses Hymn and Specter rather than splashing white. Sedge Troll benefits from actual Swamps; Badlands has the needed land type.

```forge-deck
[Main]
4 Black Knight
4 Hypnotic Specter
3 Sedge Troll
3 Order of the Ebon Hand
4 Dark Ritual
4 Hymn to Tourach
4 Lightning Bolt
2 Chain Lightning
2 Fireball
1 Demonic Tutor
1 Mind Twist
1 Mox Jet
1 Mox Ruby
1 Black Lotus
4 Badlands
3 City of Brass
2 Mishra's Factory
1 Strip Mine
3 Mountain
12 Swamp
[Sideboard]
3 Red Elemental Blast
3 Shatter
3 Gloom
2 Terror
2 Earthquake
2 Tormod's Crypt
```

## Mulligan priorities

Count black sources for BB and B for regeneration, not only total mana. Ritual into a threat is useful; Ritual into nothing does not rescue a hand with no durable source.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 1 Swamp;1 Badlands;1 Dark Ritual;1 Hypnotic Specter;1 Black Knight;1 Hymn to Tourach;1 Lightning Bolt | Keep | - | Both an accelerated threat and a normal two-mana continuation are available. |
| H2 | Draw / unknown | 0 | 1 Swamp;1 Badlands;1 Mountain;1 Black Knight;1 Hymn to Tourach;1 Lightning Bolt;1 Sedge Troll | Keep | - | Three lands support both colors and the midgame threat. |
| H3 | Play or draw / unknown | 0 | 4 Black Knight; 3 Hypnotic Specter | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 Badlands; 3 City of Brass | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 1 Swamp;1 Badlands;1 Dark Ritual;1 Hypnotic Specter;1 Black Knight;1 Hymn to Tourach;1 Lightning Bolt | Keep six | 1 Black Knight | Keep disruption and a real accelerated clock. |
| H6 | Draw / unknown | 2 | 1 Swamp;1 Badlands;1 Mountain;1 Black Knight;1 Hymn to Tourach;1 Lightning Bolt;1 Sedge Troll | Keep five | 1 Mountain;1 Sedge Troll | Retain BB access, a creature, discard and Bolt at five. |

## Sequencing and resources

- **Early:** Compare Ritual-Specter with Hymn based on opposing mana and visible answers; do not assume turn-one Specter always survives.
- **Middle:** Troll demands a Swamp and spare black for regeneration. Use discard before a decisive threat when the tempo cost is tolerable.
- **Late:** Burn supplies the last points, while late Rituals can enlarge Fireball or Mind Twist only if those spells are available.
- **Mana burn:** Ritual creates BBB; sequence actual black and generic costs to consume it. A regeneration ability may be a sink, but a purposeless shield need not be better than preserving Ritual.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Use burn to keep efficient black creatures attacking; discard may be too slow after the opponent empties their hand. |
| Midrange | Threaten regeneration and attack their hand before committing to a long combat stall. |
| Control | Combine discard with a clock; Gloom taxes white answers but is not an answer to a resolved Moat. |
| Combo | Disrupt hand development and race; identify if graveyard or artifacts drive the engine. |
| Prison | Black/red lacks broad enchantment removal; prevent the lock or exploit Factory where legal. |

### Named exceptions and common mistakes

- White Weenie: Black Knight's protection from white changes blocks and Swords legality, but Crusade can let other white creatures race.
- The Deck: a resolved Moat is a serious structural weakness; don't board as though Shatter could remove it.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| The Deck / play | 3 Red Elemental Blast;3 Gloom | 2 Chain Lightning;2 Fireball;2 Order of the Ebon Hand | Favor disruption over mana-heavy ground pumping and slow burn. |
| Artifact Aggro / draw | 3 Shatter;2 Earthquake | 4 Hymn to Tourach;1 Fireball | React to board pressure instead of hands that empty quickly; Earthquake targets support creatures, not necessarily full-sized robots. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / br-aggro-D1 | Troll faces destroy-based removal and black mana is available | Regenerate before destruction resolves | No regeneration against exile, sacrifice or no-regeneration clauses | Use the correct defensive window (R) |
| 90 / br-aggro-D2 | Opponent has no cards and several attackers | Prefer developing or removing over casting Hymn | A known future draw does not make current empty-hand discard productive | Choose relevant interaction (H) |
| 80 / br-aggro-D3 | Only one reusable black source supports a double-black hand | Treat Ritual as temporary, not a second permanent source | A winning early sequence may justify the risk | Avoid overstating long-term castability (H) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Sedge Troll is targeted by Swords to Plowshares. Paying B to regenerate will not save it from exile.
- **S2** Opponent has an empty hand; you have Bolt and Hymn with a threatening small creature across the table. Prefer relevant removal to empty-hand discard.

## Evidence and implementation boundary

- [Wak-Wak: Black Red Aggro](https://www.wak-wak.se/9394decks/br-aggro): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Black Knight](../../forge-gui/res/cardsfolder/b/black_knight.txt), [Hypnotic Specter](../../forge-gui/res/cardsfolder/h/hypnotic_specter.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
