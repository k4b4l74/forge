# Suicide Blue

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `suicide-blue` · Family: **Aggro** · Rules: `swedish-fe-burn-london-v1`
Aliases: Blue aggro, Mono Blue Aggro.

## Identity and construction

Cheap evasive creatures and temporary enhancements create a short clock, with counters and bounce buying attacks. Efreet and Psionic Blast impose a real self-damage budget.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/suicide-blue) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

Flying Men and Serendib Efreet carry Mutation; blue interaction replaces red burn. The fixture is mono-blue and deliberately omits off-color answers to enchantments.

```forge-deck
[Main]
4 Flying Men
4 Serendib Efreet
4 Unstable Mutation
4 Psionic Blast
4 Counterspell
3 Boomerang
2 Unsummon
1 Ancestral Recall
1 Time Walk
1 Mox Sapphire
1 Black Lotus
1 Sol Ring
1 Chaos Orb
3 Mishra's Factory
1 Strip Mine
25 Island
[Sideboard]
3 Blue Elemental Blast
3 Energy Flux
2 Control Magic
1 Boomerang
2 Tormod's Crypt
4 Psychic Purge
```

## Mulligan priorities

A clock plus blue mana is essential; all reactive cards with no threat become a poor control deck. Counterspell needs UU, whereas a Factory plus Island only casts some of the hand.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 2 Island;1 Flying Men;1 Unstable Mutation;1 Serendib Efreet;1 Counterspell;1 Psionic Blast | Keep | - | Start pressure immediately and choose between protection or Efreet as mana develops. |
| H2 | Draw / unknown | 0 | 3 Island;1 Flying Men;1 Serendib Efreet;1 Boomerang;1 Psionic Blast | Keep | - | Stable mana supports a flier and tempo interaction. |
| H3 | Play or draw / unknown | 0 | 4 Flying Men; 3 Serendib Efreet | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 7 Island | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 2 Island;1 Flying Men;1 Unstable Mutation;1 Serendib Efreet;1 Counterspell;1 Psionic Blast | Keep six | 1 Serendib Efreet | Protect the cheap clock rather than relying on the third land. |
| H6 | Draw / unknown | 2 | 3 Island;1 Flying Men;1 Serendib Efreet;1 Boomerang;1 Psionic Blast | Keep five | 1 Island;1 Serendib Efreet | Keep two blue sources, cheap pressure, bounce and reach at five. |

## Sequencing and resources

- **Early:** Cast a small flier before enhancing it; against open removal, wait to attach Mutation until protection or immediate damage justifies the risk.
- **Middle:** Counter the spell that changes the race, not every spell. Bounce is strongest when the opponent cannot simply replay the permanent without losing an attack.
- **Late:** Recount Efreet upkeep and Blast self-damage every turn; a nominal four-damage finisher can also kill you.
- **Mana burn:** Sol Ring cannot pay UU for Counterspell. Do not activate colorless acceleration without a generic cost to spend it on; self-damage already narrows your margin.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Race in the air while bouncing the ground threat that most shortens your clock. |
| Midrange | Prevent the stabilizing large flier or lifegain; ground size alone is less important. |
| Control | Force action with a cheap flier, then counter or bounce the key answer. |
| Combo | A clock plus held-up Counterspell is preferable to pure reactive waiting. |
| Prison | Bounce a lock at the opponent's end step only if you can exploit the opening before it is replayed. |

### Named exceptions and common mistakes

- Sligh: the matchup reverses some racing instincts; Blast and Efreet can make ordinary burn lethal to you.
- The Deck: Mutation invites a two-for-one from Swords; avoid stacking all enhancements on the same exposed creature.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| Sligh / draw | 3 Blue Elemental Blast;2 Control Magic | 3 Unstable Mutation;2 Psionic Blast | Reduce self-damage and fragile investment; Control Magic is a later stabilizer, not turn-two removal. |
| Artifact Aggro / play | 3 Energy Flux;2 Control Magic | 3 Unstable Mutation;2 Unsummon | Attack sustained artifact mana and borrow a large blocker; Flux also taxes your own rocks. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / suicide-blue-D1 | Psionic Blast would reduce your life to zero or less | Reject it unless a rules-correct outcome justifies a draw or prevents certain loss | Do not treat simultaneous lethal as a solo win | Blast damages its controller (R) |
| 90 / suicide-blue-D2 | A small flier already creates a short clock | Hold Counterspell for a race-changing answer | Deploy more pressure if the opponent has inevitability and no relevant response is represented | Tempo needs both threat and disruption (H) |
| 80 / suicide-blue-D3 | Mutation is available but opponent has open known removal | Avoid adding a second card to the vulnerable threat without a reason | Immediate lethal or adequate protection can override | Minimize exposed investment (H) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** You are at 2, opponent at 4, and Blast will deal its printed damage to both players with no replacement effects. It is not a clean win for you; classify the simultaneous lethal outcome correctly.
- **S2** Island and Factory are your only untapped sources. Counterspell in hand is not currently castable; never advertise a UU shield.

## Evidence and implementation boundary

- [Wak-Wak: Suicide Blue](https://www.wak-wak.se/9394decks/suicide-blue): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Flying Men](../../forge-gui/res/cardsfolder/f/flying_men.txt), [Serendib Efreet](../../forge-gui/res/cardsfolder/s/serendib_efreet.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
