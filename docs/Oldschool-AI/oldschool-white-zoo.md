# White Zoo

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `white-zoo` · Family: **Aggro** · Rules: `swedish-fe-burn-london-v1`
Aliases: GW Zoo, white-based Zoo.

## Identity and construction

White-led creature pressure backed by efficient permanent answers. Lions and first-strikers distinguish this fixture from blue-red burn; adding blue fliers is a variant, not a reason to remove its white identity.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/white-zoo) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

This GW fixture trades blue power and burn reach for stable white removal, green bodies, and Armageddon. Keep enough green sources for Erhnam while retaining double white for Knights.

```forge-deck
[Main]
4 Savannah Lions
4 White Knight
4 Argothian Pixies
3 Erhnam Djinn
3 Giant Growth
4 Swords to Plowshares
3 Disenchant
2 Armageddon
1 Mox Pearl
1 Mox Emerald
1 Black Lotus
1 Chaos Orb
4 Savannah
4 City of Brass
3 Mishra's Factory
1 Strip Mine
4 Forest
13 Plains
[Sideboard]
3 Spirit Link
2 Circle of Protection: Red
1 Disenchant
2 Divine Offering
2 Armageddon
3 Whirling Dervish
2 Tormod's Crypt
```

## Mulligan priorities

Prefer two usable lands, a turn-one/two creature, and either another threat or interaction. A hand of expensive bodies plus colorless lands is not a curve. Against red, a damage-free white source is worth more than a speculative third color.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 1 Plains;1 Savannah;1 Forest;1 Savannah Lions;1 White Knight;1 Swords to Plowshares;1 Giant Growth | Keep | - | Lions into Knight is available without spending the removal. |
| H2 | Draw / unknown | 0 | 1 Plains;1 Savannah;1 Savannah Lions;1 Argothian Pixies;1 Swords to Plowshares;1 Disenchant;1 Armageddon | Keep | - | Two sources and cheap plays buy time to reach the land reset. |
| H3 | Play or draw / unknown | 0 | 4 Savannah Lions; 3 White Knight | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 Savannah; 3 City of Brass | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 1 Plains;1 Savannah;1 Forest;1 Savannah Lions;1 White Knight;1 Swords to Plowshares;1 Giant Growth | Keep six | 1 Giant Growth | Keep the castable curve, three sources and removal rather than a conditional pump. |
| H6 | Draw / unknown | 2 | 1 Plains;1 Savannah;1 Savannah Lions;1 Argothian Pixies;1 Swords to Plowshares;1 Disenchant;1 Armageddon | Keep five | 1 Armageddon;1 Disenchant | At five, preserve the two-land pressure/removal core rather than expensive or situational spells. |

## Sequencing and resources

- **Early:** Lead with a colored source that casts a threat while preserving the next turn's WW. Do not lead Factory merely because it is a land.
- **Middle:** Use Swords on a blocker only when its removal opens more damage than the life gained. Hold Armageddon until your board and surviving nonland mana recover faster.
- **Late:** Activate Factory through removal-heavy boards; reserve a second threat against Wrath rather than deploying every creature.
- **Mana burn:** Do not float pre-Armageddon mana without a legal same-main-phase use. Lotus for a single small creature can lose life unnecessarily.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Trade small bodies when it preserves a larger clock; save Swords for an otherwise unmanageable attacker. |
| Midrange | Go around larger ground creatures with tempo, not repeated bad attacks into Erhnam. |
| Control | Threat plus Armageddon pressures expensive draw engines; retain Disenchant for Moat or The Abyss. |
| Combo | Present a fast clock while using Disenchant on the demonstrated engine piece. |
| Prison | Preserve white mana for lock removal; activating Factory does not pay Stasis upkeep for you. |

### Named exceptions and common mistakes

- The Deck: Moat makes ground damage irrelevant until answered; spending the last Disenchant on a Mox can lose the game.
- Atog Smash: destroy a relevant artifact before combat when useful, but account for Atog sacrificing it in response; Swords on Atog is often more decisive.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| Sligh / draw | 3 Spirit Link;2 Circle of Protection: Red | 2 Armageddon;3 Giant Growth | Replace vulnerable pump and land resets with stabilizers; Spirit Link is a life-gain trigger, not damage prevention. |
| The Deck / play | 1 Disenchant;2 Armageddon | 3 Giant Growth | Preserve threat count while improving answers to enchantment locks and pressure on mana. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / white-zoo-D1 | Armageddon is castable; your board kills in two attacks and theirs has no profitable race | Consider the land reset | Do not do it if a visible artifact threat wins the race or your only clock is an inactive Factory | Convert board advantage into constrained recovery (H) |
| 90 / white-zoo-D2 | Swords can clear the only blocker | Compare damage unlocked with life granted before using it | Hold it when that life gain lengthens rather than shortens the kill | Removal is not automatically reach (H) |
| 80 / white-zoo-D3 | Opponent has a visible Moat | Keep Disenchant available rather than spending it on a redundant mana rock | Immediate mana denial that prevents lethal can override | Ground creatures cannot solve Moat alone (H) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** You control Lions and a White Knight; opponent controls Moat and an untapped Mox. With Disenchant and sufficient white mana, remove Moat before a prospective ground attack, not the Mox.
- **S2** Opponent is at 2 life and has a 4/4 that could block your only 2/1. Swords before blockers gives them 4 life, so the subsequent unblocked 2 damage is not lethal. If you instead remove the blocker after blocks, the attacker remains blocked and, without trample, deals no damage to that player.

## Evidence and implementation boundary

- [Wak-Wak: White Zoo](https://www.wak-wak.se/9394decks/white-zoo): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Savannah Lions](../../forge-gui/res/cardsfolder/s/savannah_lions.txt), [White Knight](../../forge-gui/res/cardsfolder/w/white_knight.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
