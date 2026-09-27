# Field of Dreams

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `field-of-dreams` · Family: **Prison** · Rules: `swedish-fe-burn-london-v1`
Aliases: Conceding Dreams, Field/Sindbad.

## Identity and construction

Public top cards let Millstone and Sindbad make selective decisions. The goal is to deny important draws while improving your own and eventually winning, not making an opponent concede.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/conceding-dreams) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

The UG fixture uses Field, Millstone, Sindbad and Sylvan with permission and temporary interaction. It deliberately keeps a real mill route and limited creature pressure rather than scoring information alone as a lock.

```forge-deck
[Main]
4 Field of Dreams
4 Millstone
4 Sindbad
4 Counterspell
3 Sylvan Library
3 Birds of Paradise
3 Boomerang
2 Control Magic
2 Fog
1 Regrowth
1 Ancestral Recall
1 Time Walk
1 Mana Drain
1 Mox Sapphire
1 Mox Emerald
1 Sol Ring
1 Black Lotus
1 Chaos Orb
4 Tropical Island
4 City of Brass
1 Strip Mine
3 Mishra's Factory
4 Forest
6 Island
[Sideboard]
3 Blue Elemental Blast
3 Crumble
3 Tranquility
2 Control Magic
2 Fog
2 Tormod's Crypt
```

## Mulligan priorities

Keep mana and a functional engine component with survival. Field alone is information, not cards, pressure or denial. Millstone without information can still mill but must not pretend to know replacement draws.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 1 Tropical Island;1 Island;1 Forest;1 Field of Dreams;1 Sindbad;1 Millstone;1 Counterspell | Keep | - | The information engine and mana are present, with permission for a critical play. |
| H2 | Draw / unknown | 0 | 1 Tropical Island;1 Island;1 Forest;1 Birds of Paradise;1 Counterspell;1 Boomerang;1 Sylvan Library | Keep | - | A normal interactive development hand can find the information engine later. |
| H3 | Play or draw / unknown | 0 | 4 Field of Dreams; 3 Millstone | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 Tropical Island; 3 City of Brass | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 1 Tropical Island;1 Island;1 Forest;1 Field of Dreams;1 Sindbad;1 Millstone;1 Counterspell | Keep six | 1 Millstone | Keep development and interaction before an expensive repeated activation. |
| H6 | Draw / unknown | 2 | 1 Tropical Island;1 Island;1 Forest;1 Birds of Paradise;1 Counterspell;1 Boomerang;1 Sylvan Library | Keep five | 1 Birds of Paradise;1 Forest | Two sources, permission, bounce and Library retain a coherent five. |

## Sequencing and resources

- **Early:** Use public information to decide whether milling improves the opponent's next draw. Leaving a useless top card can be better than replacing it with an unknown card.
- **Middle:** Sindbad draws and reveals, then discards if the card is not a land. A known land is reliable retained value; a spell may still be worth cycling away only for a specific reason.
- **Late:** Track remaining library sizes, shuffle effects and a real finish. Never keep milling solely because an activation is available, and never treat concession as an AI win condition.
- **Mana burn:** Millstone costs two and taps; multiple activations require multiple ready objects or untaps. Do not float excess mana expecting one Millstone to consume it repeatedly.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Information does not block attackers; use Fog/bounce or theft to survive before optimizing draws. |
| Midrange | Deny the next meaningful threat rather than every land; their current board still sets the clock. |
| Control | Shuffle/tutor effects can defeat top control; preserve counters for game-changing cards. |
| Combo | Mill a known critical piece when that helps, but graveyard recursion can make milling actively helpful. |
| Prison | Competing engines and mana constraints decide whether you can use the information advantage. |

### Named exceptions and common mistakes

- Reanimator: milling a known fatty may provide exactly the graveyard target they want; denial requires knowing the zone that matters.
- The Deck: do not mill a revealed redundant land just to use mana if it might expose Ancestral or a needed answer.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| Sligh / draw | 3 Blue Elemental Blast;2 Fog | 3 Field of Dreams;2 Millstone | Prioritize staying alive rather than slow draw denial. |
| Artifact Aggro / play | 3 Crumble;2 Control Magic | 3 Field of Dreams;2 Sindbad | React to the developed board and reduce fragile information setup. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / field-of-dreams-D1 | Opponent's visible top card is low impact | Consider leaving it rather than milling | Mill for a verified deck-out finish or if the next relevant objective requires it | Unknown replacement may be better for them (H) |
| 90 / field-of-dreams-D2 | Sindbad can draw a publicly known land | Value the retained land according to current mana needs | A different urgent activation may have priority | Nonland draws are discarded by its ability (R) |
| 80 / field-of-dreams-D3 | A proposed denial plan inspects an unrevealed next card | Reject the hidden-information dependency | Public reveals and legitimately known cards are allowed | No omniscient library access (R) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Field reveals an Island on top of your library; Sindbad can draw it and retain it because it is a land.
- **S2** Opponent's top card is a redundant land and they have enough mana. The policy must allow 'do not mill' instead of automatically giving them a chance at a useful unknown replacement.

## Evidence and implementation boundary

- [Wak-Wak: Field of Dreams](https://www.wak-wak.se/9394decks/conceding-dreams): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Field of Dreams](../../forge-gui/res/cardsfolder/f/field_of_dreams.txt), [Millstone](../../forge-gui/res/cardsfolder/m/millstone.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
