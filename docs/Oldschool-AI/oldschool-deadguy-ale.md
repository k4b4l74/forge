# Deadguy Ale

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `deadguy-ale` · Family: **Midrange** · Rules: `swedish-fe-burn-london-v1`
Aliases: BW Midrange, Black White disruption.

## Identity and construction

Black hand/mana disruption and threats gain white's broad removal. Its strength is flexible interaction, not a guarantee that every opening hand with Ritual is explosive.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/deadguy-ale) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

The BW fixture uses Hymn, Sinkhole and Specter/Juzam with Swords and Disenchant. White Knight is omitted to avoid unnecessary competing WW pressure on a predominantly black early game.

```forge-deck
[Main]
4 Hypnotic Specter
3 Juzám Djinn
4 Order of the Ebon Hand
4 Dark Ritual
4 Hymn to Tourach
3 Sinkhole
4 Swords to Plowshares
3 Disenchant
1 Demonic Tutor
1 Mind Twist
1 Balance
1 Mox Jet
1 Mox Pearl
1 Black Lotus
1 Chaos Orb
4 Scrubland
3 City of Brass
3 Mishra's Factory
1 Strip Mine
3 Plains
10 Swamp
[Sideboard]
1 Disenchant
3 Divine Offering
2 Dust to Dust
2 Circle of Protection: Red
2 Spirit Link
3 Gloom
2 Tormod's Crypt
```

## Mulligan priorities

Prefer BB development plus a clock, with reachable W for answers. A Plains/Factory hand cannot cast Hymn or Specter merely because it contains two lands.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 1 Swamp;1 Scrubland;1 Dark Ritual;1 Hypnotic Specter;1 Hymn to Tourach;1 Swords to Plowshares;1 Disenchant | Keep | - | Black development and white answers are available immediately. |
| H2 | Draw / unknown | 0 | 1 Swamp;1 Scrubland;1 Plains;1 Order of the Ebon Hand;1 Hymn to Tourach;1 Swords to Plowshares;1 Juzám Djinn | Keep | - | The mana supports disruption and a two-drop while approaching Juzam. |
| H3 | Play or draw / unknown | 0 | 4 Hypnotic Specter; 3 Juzám Djinn | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 Scrubland; 3 City of Brass | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 1 Swamp;1 Scrubland;1 Dark Ritual;1 Hypnotic Specter;1 Hymn to Tourach;1 Swords to Plowshares;1 Disenchant | Keep six | 1 Disenchant | Keep the early threat, discard and creature interaction. |
| H6 | Draw / unknown | 2 | 1 Swamp;1 Scrubland;1 Plains;1 Order of the Ebon Hand;1 Hymn to Tourach;1 Swords to Plowshares;1 Juzám Djinn | Keep five | 1 Plains;1 Juzám Djinn | Two sources, threat, discard and Swords form a functional five. |

## Sequencing and resources

- **Early:** Choose discard or land denial when it actually delays the opponent's plan; a Mox-heavy opponent can ignore a single Sinkhole.
- **Middle:** Use white answers to remove what black threats cannot fight, especially artifacts and enchantments.
- **Late:** Avoid pumping Order with mana needed for a key white answer. Factory and retained threats help rebuild after sweeps.
- **Mana burn:** Ritual can pay generic portions of spells but not their W. Do not cast it expecting BBB to become Swords mana.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Remove the threat that dominates combat and stabilize before spending turns on empty-hand discard. |
| Midrange | Flexible white removal lets you answer larger bodies without multiple cards. |
| Control | Combine disruption with a clock and reserve Disenchant for locks or card engines. |
| Combo | Discard before the critical turn and keep a live answer to a permanent engine. |
| Prison | Disenchant is an escape route; ensure land denial and color choices do not cut off your own W. |

### Named exceptions and common mistakes

- Robots: Swords answers Su-Chi without a death trigger; Terror-like black removal would not.
- The Deck: Gloom also taxes your own white spells. It can be useful but demands sequencing white answers first or saving the extra mana.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| Sligh / draw | 2 Circle of Protection: Red;2 Spirit Link | 3 Sinkhole;1 Juzám Djinn | Reduce tempo-negative land denial and self-damage while adding stabilization. |
| Artifact Aggro / play | 3 Divine Offering;2 Dust to Dust | 3 Sinkhole;2 Hymn to Tourach | Attack the artifact-based board and mana rather than relying on land or hand denial. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / deadguy-ale-D1 | Opponent's mana is mainly artifacts | Revalue Sinkhole below relevant permanent interaction | A scarce colored land can still be a critical target | Land count is not total mana (H) |
| 90 / deadguy-ale-D2 | White removal and Gloom are both planned | Sequence or budget for your own white tax | Do not strand your only answer to a lock | Gloom is symmetric for white spells (R) |
| 80 / deadguy-ale-D3 | Swords can exile Su-Chi | Prefer exile when its death mana would help the opponent | Account for granted life in the clock | Exile avoids a dies trigger (R) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Opponent's Su-Chi is exiled by Swords. It does not die, so do not award its controller four death-trigger mana.
- **S2** Gloom is active and you want to cast Disenchant. Include the additional cost instead of treating it as a two-mana answer.

## Evidence and implementation boundary

- [Wak-Wak: Deadguy Ale](https://www.wak-wak.se/9394decks/deadguy-ale): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Hypnotic Specter](../../forge-gui/res/cardsfolder/h/hypnotic_specter.txt), [Juzám Djinn](../../forge-gui/res/cardsfolder/j/juzam_djinn.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
