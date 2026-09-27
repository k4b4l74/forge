# Mono Black

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `mono-black` · Family: **Midrange** · Rules: `swedish-fe-burn-london-v1`
Aliases: Monoblack, Black aggro.

## Identity and construction

Black threats, Ritual acceleration and discard attack resources together. Artifact sweepers compensate imperfectly for black's lack of direct enchantment removal.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/mono-black) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

The FE fixture uses Hymn and Order alongside Specter and Knight, with Disk as a reset. It does not assume all Mono Black decks use the same Juzam or Underworld Dreams package.

```forge-deck
[Main]
4 Hypnotic Specter
4 Black Knight
4 Order of the Ebon Hand
3 Juzám Djinn
4 Dark Ritual
4 Hymn to Tourach
4 Sinkhole
2 Terror
2 Drain Life
2 Nevinyrral's Disk
1 Demonic Tutor
1 Mind Twist
1 Mox Jet
1 Black Lotus
4 Mishra's Factory
1 Strip Mine
18 Swamp
[Sideboard]
3 Gloom
2 Terror
2 Drain Life
2 Nevinyrral's Disk
2 Tormod's Crypt
2 Disrupting Scepter
2 Paralyze
```

## Mulligan priorities

Prioritize durable black sources for BB/BBB cards. Factory is valuable late but several Factories plus Ritual may not support your hand. Avoid all-disruption hands that never establish pressure.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 2 Swamp;1 Dark Ritual;1 Hypnotic Specter;1 Black Knight;1 Hymn to Tourach;1 Terror | Keep | - | Ritual-Specter has a normal black follow-up and interaction. |
| H2 | Draw / unknown | 0 | 3 Swamp;1 Black Knight;1 Hymn to Tourach;1 Drain Life;1 Nevinyrral's Disk | Keep | - | Early development can bridge to the reset if needed. |
| H3 | Play or draw / unknown | 0 | 4 Hypnotic Specter; 3 Black Knight | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 7 Swamp | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 2 Swamp;1 Dark Ritual;1 Hypnotic Specter;1 Black Knight;1 Hymn to Tourach;1 Terror | Keep six | 1 Black Knight | Keep an accelerated clock plus hand and board interaction. |
| H6 | Draw / unknown | 2 | 3 Swamp;1 Black Knight;1 Hymn to Tourach;1 Drain Life;1 Nevinyrral's Disk | Keep five | 1 Nevinyrral's Disk;1 Swamp | Retain two lands, Knight, Hymn and Drain rather than a slow tapped artifact. |

## Sequencing and resources

- **Early:** Deploy a threat before overinvesting in denial when the opponent can rebuild faster than you.
- **Middle:** Use Sinkhole to cut a crucial color or Library, not merely the next land in a list. Disk may destroy your own pressure too.
- **Late:** Drain Life's X requires black mana under its text; Factory colorless cannot simply enlarge it. Watch Juzam self-damage.
- **Mana burn:** Ritual is not free late-game value. Cast it only when a legal spell or meaningful activation can consume the mana before the phase ends.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Stabilize with protection, first strike and removal; some white threats cannot be targeted by black spells. |
| Midrange | Distinguish artifact and black targets before valuing Terror; large bodies may require Disk. |
| Control | A clock plus Hymn/Scepter challenges their hand; Gloom makes white answers harder but does not remove resolved enchantments. |
| Combo | Discard and a quick clock are the main defense; use Crypt for graveyard engines when appropriate. |
| Prison | Disk can be an escape only if it untaps and can activate; do not assume a resolved lock automatically permits that. |

### Named exceptions and common mistakes

- White Weenie: White Knight cannot be targeted by Terror or Drain Life through protection from black; Disk is non-targeted.
- Reanimator: indiscriminate discard may stock their graveyard with good targets. With Crypt, prefer controlling the reanimation window.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| The Deck / play | 3 Gloom;2 Disrupting Scepter | 2 Terror;2 Drain Life;1 Sinkhole | For creature-light control, prioritize sustained hand pressure over narrow removal. |
| Artifact Aggro / draw | 2 Nevinyrral's Disk;2 Paralyze | 2 Terror;2 Sinkhole | Terror misses robots; Paralyze delays a legal target while Disk can reset. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / mono-black-D1 | Discard may put a large creature into a known reanimation engine | Compare disruption benefit with graveyard enablement | Removing the actual reanimation spell may still be valuable | Hand damage is not always resource denial (H) |
| 90 / mono-black-D2 | Drain Life is cast with colorless and black sources | Allocate black mana to X as required | Generic costs still allow appropriate other mana | Do not overstate Drain's size (R) |
| 80 / mono-black-D3 | An opposing protection-from-black creature dominates combat | Seek a non-targeting artifact answer or race | Do not repeatedly propose illegal black targeting | Protection changes the answer set (R) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Your four Factories and one Swamp are available for Drain Life. Do not set X as if all five sources produced black; its text restricts the X payment.
- **S2** A White Knight is the opponent's only creature. Terror cannot legally target it; a ready Disk is a different, non-targeting answer.

## Evidence and implementation boundary

- [Wak-Wak: Mono Black](https://www.wak-wak.se/9394decks/mono-black): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Hypnotic Specter](../../forge-gui/res/cardsfolder/h/hypnotic_specter.txt), [Black Knight](../../forge-gui/res/cardsfolder/b/black_knight.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
