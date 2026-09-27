# Artifact Toolbox

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `artifact-toolbox` · Family: **Control** · Rules: `swedish-fe-burn-london-v1`
Aliases: Transmute toolbox.

## Identity and construction

Transmute Artifact converts expendable artifacts into context-specific tools. Selection, sacrifice value and the mana-value difference are all part of the decision.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/artifact-toolbox) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

A blue control fixture provides singleton utility targets, a few robot finishers, and permission. Some targets are narrow; the correct search can be 'do not Transmute yet'.

```forge-deck
[Main]
4 Transmute Artifact
4 Counterspell
3 Copy Artifact
3 Mana Vault
2 Su-Chi
2 Triskelion
2 Jayemdae Tome
1 Icy Manipulator
1 Nevinyrral's Disk
1 Disrupting Scepter
1 Mirror Universe
1 Millstone
1 Tawnos's Coffin
1 Ivory Tower
1 Chaos Orb
1 Ancestral Recall
1 Time Walk
1 Mana Drain
1 Braingeyser
1 Sol Ring
1 Mox Sapphire
1 Black Lotus
4 Mishra's Factory
1 Strip Mine
3 City of Brass
17 Island
[Sideboard]
3 Blue Elemental Blast
3 Control Magic
3 Energy Flux
2 Hurkyl's Recall
2 Boomerang
2 Tormod's Crypt
```

## Mulligan priorities

Keep blue mana and a development/survival line. Transmute without an artifact to sacrifice and enough mana for the intended target is not a self-contained answer.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 2 Island;1 Mana Vault;1 Su-Chi;1 Counterspell;1 Transmute Artifact;1 Jayemdae Tome | Keep | - | A real accelerated threat supplies pressure and future toolbox material. |
| H2 | Draw / unknown | 0 | 3 Island;1 Counterspell;1 Mana Vault;1 Triskelion;1 Icy Manipulator | Keep | - | Stable mana and acceleration can reach board-impact artifacts. |
| H3 | Play or draw / unknown | 0 | 4 Transmute Artifact; 3 Counterspell | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 3 City of Brass; 4 Island | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 2 Island;1 Mana Vault;1 Su-Chi;1 Counterspell;1 Transmute Artifact;1 Jayemdae Tome | Keep six | 1 Jayemdae Tome | Keep the immediate line and flexibility over redundant expensive draw. |
| H6 | Draw / unknown | 2 | 3 Island;1 Counterspell;1 Mana Vault;1 Triskelion;1 Icy Manipulator | Keep five | 1 Triskelion;1 Icy Manipulator | At five keep reliable mana, permission and acceleration; this is a defensive fallback, not a high-value seven. |

## Sequencing and resources

- **Early:** Develop usable blue and an artifact with real value before searching for a hypothetical perfect answer.
- **Middle:** Transmute sacrifices during resolution, not as an additional casting cost. Budget the difference between the searched artifact's mana value and the sacrificed one's.
- **Late:** Choose a clock once stabilized; accumulating utility pieces without using them lets opposing draw engines overtake you.
- **Mana burn:** Su-Chi sacrificed during Transmute triggers mana only after the spell finishes resolving; it cannot pay that same Transmute's mana-value difference. Later mana still needs a same-phase use.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Search for immediate stabilization only if the tutor sequence is fast enough; a tapped Disk is not an instant sweeper. |
| Midrange | Coffin, Icy or a robot can change combat; choose by the specific board. |
| Control | Tome or Scepter can be better than another removal artifact when the board is quiet. |
| Combo | Find a relevant engine answer, not a generic value piece; permission may be the faster response. |
| Prison | A toolbox requires mana access and a legal sacrifice; do not assume every lock can be escaped by searching. |

### Named exceptions and common mistakes

- Atog: artifact removal and sacrifice responses make expensive utility plans fragile; stabilize the creature threat too.
- The Beast: adding Guardian Beast changes protection and world-enchantment vulnerabilities; it is a distinct variant, not implicit in this list.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| UR Burn / draw | 3 Blue Elemental Blast;2 Control Magic | 1 Mirror Universe;1 Millstone;1 Disrupting Scepter;2 Transmute Artifact | Trim slow or narrow setup for immediate interaction and a board swing. |
| Artifact Aggro / play | 3 Control Magic;2 Hurkyl's Recall | 1 Mirror Universe;1 Millstone;1 Disrupting Scepter;1 Ivory Tower;1 Jayemdae Tome | Use tempo against their board without automatically boarding Flux against your own artifact-heavy deck. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / artifact-toolbox-D1 | Transmute is proposed | Evaluate sacrifice, target availability and additional mana together | Countered Transmute does not sacrifice as a casting cost | Resolution structure matters (R) |
| 90 / artifact-toolbox-D2 | Su-Chi is the intended sacrifice to fund the same search | Do not count its death trigger toward the in-resolution payment | Other floating mana can pay | The trigger waits until after resolution (R) |
| 80 / artifact-toolbox-D3 | Several toolbox targets solve different problems | Choose the one preventing loss or enabling the shortest secure finish | Do not overvalue a named combo without its other piece | Tutor value is state-dependent (H) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Transmute Artifact is countered before resolving. The intended sacrifice artifact remains; do not remove it while paying casting costs.
- **S2** Transmute sacrifices Su-Chi and searches for a six-mana Triskelion. Its death-trigger mana cannot pay the two-mana difference during that same resolution.

## Evidence and implementation boundary

- [Wak-Wak: Artifact Toolbox](https://www.wak-wak.se/9394decks/artifact-toolbox): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Transmute Artifact](../../forge-gui/res/cardsfolder/t/transmute_artifact.txt), [Counterspell](../../forge-gui/res/cardsfolder/c/counterspell.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
