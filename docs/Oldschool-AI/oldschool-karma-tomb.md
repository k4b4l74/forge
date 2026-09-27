# Karma Tomb

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `karma-tomb` · Family: **Control** · Rules: `swedish-fe-burn-london-v1`
Aliases: Cyclopean Tomb control.

## Identity and construction

Cyclopean Tomb converts opposing lands into Swamps while Karma converts their Swamp count into upkeep damage. This slow engine needs conventional control tools to survive its setup.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/karma-tomb) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

The UW fixture avoids Swamp-type lands so its own Karma is normally asymmetric. It includes Serra and Factory as alternate clocks rather than requiring the two-card engine every game.

```forge-deck
[Main]
4 Karma
3 Cyclopean Tomb
4 Swords to Plowshares
4 Disenchant
4 Counterspell
2 Wrath of God
2 Jayemdae Tome
2 Serra Angel
1 Ancestral Recall
1 Time Walk
1 Mana Drain
1 Balance
1 Mox Pearl
1 Mox Sapphire
1 Sol Ring
1 Black Lotus
1 Chaos Orb
4 Tundra
3 City of Brass
2 Mishra's Factory
1 Strip Mine
7 Plains
9 Island
[Sideboard]
3 Blue Elemental Blast
3 Divine Offering
2 Circle of Protection: Red
2 Control Magic
2 Tormod's Crypt
1 Amnesia
2 Serra Angel
```

## Mulligan priorities

Keep mana and survival, not three Karmas with no way to influence the first turns. Against known black, Karma needs less setup, but it still costs four and does not block.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 1 Tundra;1 Island;1 Plains;1 Swords to Plowshares;1 Counterspell;1 Cyclopean Tomb;1 Karma | Keep | - | Interaction bridges into the engine with both colors available. |
| H2 | Draw / unknown | 0 | 1 Tundra;1 Island;1 Plains;1 Swords to Plowshares;1 Disenchant;1 Jayemdae Tome;1 Serra Angel | Keep | - | This hand can play a normal control game without forcing Tomb. |
| H3 | Play or draw / unknown | 0 | 4 Karma; 3 Cyclopean Tomb | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 Tundra; 3 City of Brass | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 1 Tundra;1 Island;1 Plains;1 Swords to Plowshares;1 Counterspell;1 Cyclopean Tomb;1 Karma | Keep six | 1 Karma | Keep flexible development; the engine can be assembled later. |
| H6 | Draw / unknown | 2 | 1 Tundra;1 Island;1 Plains;1 Swords to Plowshares;1 Disenchant;1 Jayemdae Tome;1 Serra Angel | Keep five | 1 Jayemdae Tome;1 Serra Angel | At five, keep sources and immediate answers rather than expensive payoff cards. |

## Sequencing and resources

- **Early:** Answer pressure before playing a four-mana permanent that does not immediately stop attacks.
- **Middle:** Tomb activates only during your upkeep and only targets non-Swamp lands. Prefer a color-critical land when conversion also creates Karma damage.
- **Late:** Protect the established engine or finish with fliers; do not spend every turn setting up more Swamps when direct attacks already win.
- **Mana burn:** Tomb's two-mana activation occurs in upkeep. Mana left from that beginning phase cannot pay a main-phase Karma; plan mana boundaries explicitly.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Use removal and Wrath to reach a safe engine turn; racing with Karma too early often fails. |
| Midrange | Convert a key colored land while controlling the largest threat. |
| Control | Karma may do nothing until Tomb resolves; draw engines and alternate clocks matter. |
| Combo | Use permission for the payoff, not slow land conversion as your only disruption. |
| Prison | White answers break permanent locks, but upkeep-only Tomb does not remove them. |

### Named exceptions and common mistakes

- Mono Black: their natural Swamps make Karma live immediately; still respect fast Ritual threats and Disk.
- The Deck: Underground Sea and Scrubland already count as Swamps; Tomb cannot target them, and Karma may not require conversion at all.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| Sligh / draw | 3 Blue Elemental Blast;2 Circle of Protection: Red | 3 Cyclopean Tomb;2 Karma | Trim the slow package for immediate survival. |
| The Deck / play | 1 Amnesia;2 Serra Angel | 2 Wrath of God;1 Swords to Plowshares | Against a confirmed low-creature build, diversify pressure and hand interaction. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / karma-tomb-D1 | Tomb is ready outside your upkeep | Do not propose activating it | Use other legal interaction now | Activation timing is restricted (R) |
| 90 / karma-tomb-D2 | A candidate land is already a Swamp | Exclude it from Tomb targets | It still contributes to Karma damage | Target restriction and payoff are different checks (R) |
| 80 / karma-tomb-D3 | Opponent's existing Swamps already make Karma a short clock | Prioritize protection/survival over more conversion | A key mana-denial activation can still be worthwhile | Avoid redundant setup (H) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Opponent controls Underground Sea. It is already a Swamp, so Tomb cannot target it; Karma still counts it.
- **S2** Tomb untaps during your untap step. Its ability may be used during your upkeep when you have priority, not deferred to an opponent's end step.

## Evidence and implementation boundary

- [Wak-Wak: Karma Tomb](https://www.wak-wak.se/9394decks/karma-tomb): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Karma](../../forge-gui/res/cardsfolder/k/karma.txt), [Cyclopean Tomb](../../forge-gui/res/cardsfolder/c/cyclopean_tomb.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
