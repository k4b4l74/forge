# Twiddlevault

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `twiddlevault` · Family: **Combo** · Rules: `swedish-fe-burn-london-v1`
Aliases: Twiddle Vault, Time Vault combo.

## Identity and construction

Twiddle untaps Time Vault so it can grant another turn; draw engines and recursion aim to sustain the chain until a finish. Four Vaults and four Recalls are legal under the pinned contract.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/twiddlevault) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

The UGR fixture uses multiple Vaults, Twiddles, Mines and Recalls with Fireball. The source's historical singleton-Vault discussion predates its unrestriction; do not inherit that deck limit.

```forge-deck
[Main]
4 Time Vault
4 Twiddle
4 Howling Mine
3 Sylvan Library
3 Recall
2 Counterspell
2 Power Sink
3 Fireball
2 Transmute Artifact
4 Mana Vault
1 Ancestral Recall
1 Time Walk
1 Wheel of Fortune
1 Regrowth
1 Sol Ring
1 Mox Sapphire
1 Mox Emerald
1 Mox Ruby
1 Black Lotus
4 Volcanic Island
4 Tropical Island
4 City of Brass
1 Strip Mine
7 Island
[Sideboard]
3 Red Elemental Blast
3 Blue Elemental Blast
3 Shatter
2 Tranquility
2 Boomerang
2 Tormod's Crypt
```

## Mulligan priorities

Look for blue development, card flow and at least progress toward the turn engine. Vault without an untap plan can be inert, and Twiddles without a relevant permanent are not useful threats.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 1 Volcanic Island;1 Tropical Island;1 Island;1 Time Vault;1 Twiddle;1 Howling Mine;1 Counterspell | Keep | - | The hand has the permanent, untap spell, draw support and colors. |
| H2 | Draw / unknown | 0 | 1 Volcanic Island;1 Tropical Island;1 Island;1 Mana Vault;1 Howling Mine;1 Time Vault;1 Twiddle | Keep | - | Acceleration supports development while the turn engine is already present. |
| H3 | Play or draw / unknown | 0 | 4 Time Vault; 3 Twiddle | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 Volcanic Island; 3 Tropical Island | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 1 Volcanic Island;1 Tropical Island;1 Island;1 Time Vault;1 Twiddle;1 Howling Mine;1 Counterspell | Keep six | 1 Counterspell | At six keep the coherent engine and mana; this sacrifices protection and increases risk. |
| H6 | Draw / unknown | 2 | 1 Volcanic Island;1 Tropical Island;1 Island;1 Mana Vault;1 Howling Mine;1 Time Vault;1 Twiddle | Keep five | 1 Mana Vault;1 Island | Two colored sources plus Mine/Vault/Twiddle preserve the core at five. |

## Sequencing and resources

- **Early:** Establish a draw source and enough usable blue mana before spending the last Twiddle for a turn that finds nothing.
- **Middle:** Time Vault enters tapped and does not untap normally. Its optional skip-turn route has a real cost; prefer a useful spell-based untap when available.
- **Late:** Each extra turn needs an actual continuation. Track remaining Twiddles, recursion costs, cards and a finish; do not run an unbounded search that declares victory from having Vault.
- **Mana burn:** Mana Vault acceleration is colorless while Twiddle requires U. Extra turns do not carry unused mana across phase/turn boundaries.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Use the turn chain before lethal combat, but don't start it so early that it immediately stalls. |
| Midrange | Large creatures are irrelevant only while the chain really continues; maintain a fallback for the first interrupted turn. |
| Control | Protect Vault and draw engines from Disenchant and permission; don't expose the last untap effect without a plan. |
| Combo | Compare engine speed and disrupt their critical component while maintaining your own colored mana. |
| Prison | Untap denial does not stop Twiddle automatically, but mana restrictions may prevent casting it. |

### Named exceptions and common mistakes

- The Deck: an artifact answer in response to your untap spell can remove the engine; don't count the extra turn before the relevant abilities resolve.
- Fork Combo: that deck copies spells on the stack; Time Vault grants turns through an activated ability, which Fork cannot copy.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| UR Burn / draw | 3 Blue Elemental Blast;3 Red Elemental Blast | 3 Sylvan Library;2 Recall;1 Fireball | Reduce life payments and slow recursion; retain two finishers and an engine core. |
| The Deck / play | 3 Red Elemental Blast;2 Tranquility | 2 Fireball;2 Mana Vault;1 Sylvan Library | Keep one finish and enough mana while improving protection/lock answers. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / twiddlevault-D1 | Vault is tapped and Twiddle can legally untap it | Use the untap when the resulting extra turn advances a concrete plan | Keep U for an urgent answer if the extra turn is unproductive | Extra turns need continuation resources (H) |
| 90 / twiddlevault-D2 | An extra-turn line relies on future unknown draws | Treat continuation probabilistically, not as guaranteed | Publicly known top cards can provide legitimate evidence | Do not inspect hidden library order (H) |
| 80 / twiddlevault-D3 | Planner repeats Vault actions without consuming resources | Require a proven repeatable state or stop the branch | Finite recursion can still be useful | Bound action count and avoid fake infinite turns (H) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Time Vault enters tapped. Without Twiddle or another untap effect, it cannot immediately tap for an extra turn; its ordinary untap-step restriction still applies.
- **S2** You have a tapped Vault, Twiddle, U, and priority; untapping then tapping Vault can add one turn. That does not prove a second additional turn is available.

## Evidence and implementation boundary

- [Wak-Wak: Twiddlevault](https://www.wak-wak.se/9394decks/twiddlevault): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Time Vault](../../forge-gui/res/cardsfolder/t/time_vault.txt), [Twiddle](../../forge-gui/res/cardsfolder/t/twiddle.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
