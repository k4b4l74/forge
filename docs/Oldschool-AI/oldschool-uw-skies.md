# UW Skies

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `uw-skies` · Family: **Midrange** · Rules: `swedish-fe-burn-london-v1`
Aliases: UW flyers, Blue White Skies.

## Identity and construction

A higher density of efficient fliers makes this a pressure-control deck rather than The Deck with a different name. Serra's vigilance can stabilize while maintaining attacks.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/uw-skies) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

Efreet and Serra are the main clocks, supported by white answers and blue permission. The fixture avoids black/red splashes so WW, UU and basic-land protection remain realistic.

```forge-deck
[Main]
4 Serendib Efreet
4 Serra Angel
3 Flying Men
4 Swords to Plowshares
4 Disenchant
4 Counterspell
1 Mana Drain
1 Ancestral Recall
1 Time Walk
1 Balance
1 Braingeyser
1 Mox Pearl
1 Mox Sapphire
1 Black Lotus
1 Sol Ring
1 Chaos Orb
4 Tundra
3 City of Brass
3 Mishra's Factory
1 Strip Mine
6 Plains
10 Island
[Sideboard]
3 Blue Elemental Blast
2 Circle of Protection: Red
2 Divine Offering
2 Dust to Dust
2 Control Magic
2 Tormod's Crypt
2 Jayemdae Tome
```

## Mulligan priorities

Keep a colored plan plus either a cheap threat or enough interaction to reach a flier. Four Serra Angels and lands are not the same as a supported curve. Count WW and UU separately.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 1 Tundra;1 Island;1 Plains;1 Flying Men;1 Serendib Efreet;1 Swords to Plowshares;1 Counterspell | Keep | - | The mana supports both early pressure and a protected three-drop. |
| H2 | Draw / unknown | 0 | 1 Tundra;1 Island;1 Plains;1 Serendib Efreet;1 Swords to Plowshares;1 Disenchant;1 Serra Angel | Keep | - | Answers buy time for an evasive clock; Serra is a later payoff. |
| H3 | Play or draw / unknown | 0 | 4 Serendib Efreet; 3 Serra Angel | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 Tundra; 3 City of Brass | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 1 Tundra;1 Island;1 Plains;1 Flying Men;1 Serendib Efreet;1 Swords to Plowshares;1 Counterspell | Keep six | 1 Flying Men | Keep Efreet and interaction with reliable sources. |
| H6 | Draw / unknown | 2 | 1 Tundra;1 Island;1 Plains;1 Serendib Efreet;1 Swords to Plowshares;1 Disenchant;1 Serra Angel | Keep five | 1 Serra Angel;1 Disenchant | At five keep three sources, Efreet and broad creature removal. |

## Sequencing and resources

- **Early:** Choose between starting a flier and keeping an answer based on the actual race; permission without pressure can surrender initiative.
- **Middle:** Serra can attack without tapping, so evaluate its remaining defensive contribution correctly. Efreet self-damage still constrains racing.
- **Late:** Avoid tapping out for a redundant five-drop when a counter or Tome activation in a boarded game wins the resource exchange more safely.
- **Mana burn:** Mana Drain's payout may cast Serra, but does not supply WW. Predict generic and colored costs independently before welcoming excess colorless mana.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Use Swords and vigilant blockers to stabilize, then attack in the air. |
| Midrange | Fliers change ground stalls; keep a legal answer for the opposing evasive threat. |
| Control | Force answers with one flier at a time and avoid turning Wrath into a huge exchange. |
| Combo | Pressure plus counters can stop setup; save Disenchant for the crucial engine. |
| Prison | White removal breaks many enchantment/artifact locks if you maintain mana access. |

### Named exceptions and common mistakes

- The Abyss: even Serra and Efreet are nonartifact; keep Disenchant for the world enchantment rather than assuming size protects them.
- UR Burn: a lower life total from Efreet can make holding up protection better than deploying a second self-damaging clock.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| UR Burn / draw | 3 Blue Elemental Blast;2 Circle of Protection: Red | 3 Flying Men;2 Serra Angel | Reduce vulnerable small bodies and expensive threats while protecting life. |
| The Deck / play | 2 Jayemdae Tome | 2 Swords to Plowshares | Add a resource engine while retaining removal for observed Serra or Factory lines. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / uw-skies-D1 | Serra is ready to attack and still needed as a blocker | Include vigilance in attack evaluation | Tapping effects and removal can still remove the defense | Attacking does not inherently tap Serra (R) |
| 90 / uw-skies-D2 | A second Efreet increases both damage clocks | Calculate your own upkeep damage before deployment | A verified faster kill can justify the risk | More power may reduce your survival time (H) |
| 80 / uw-skies-D3 | Opponent's ground board cannot block fliers | Preserve answers for evasive threats or race-changing effects | Immediate ground lethal still requires action | Not all large blockers matter equally (H) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** You control an untapped Serra Angel and no relevant effects. Attacking with it does not tap it; the defense evaluator should still consider it as a potential blocker next turn.
- **S2** Mana Drain supplies five colorless and your only untapped lands are Islands. Serra Angel is still not castable without two white mana.

## Evidence and implementation boundary

- [Wak-Wak: UW Skies](https://www.wak-wak.se/9394decks/uw-skies): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Serendib Efreet](../../forge-gui/res/cardsfolder/s/serendib_efreet.txt), [Serra Angel](../../forge-gui/res/cardsfolder/s/serra_angel.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
