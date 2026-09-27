# Lich Mirror

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `lich-mirror` · Family: **Combo** · Rules: `swedish-fe-burn-london-v1`
Aliases: Lich.

## Identity and construction

Lich exchanges ordinary life management for a fragile draw/survival engine; Mirror Universe can turn the resulting zero life into a finish. Losing Lich can be immediately fatal.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/2018-5-11/lich-mirror) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

The BG fixture uses Dark Heart of the Wood to turn Forests into draws under Lich, with Drain Life and Mirror as payoffs. It is a high-risk authored laboratory list, not evidence of a reliable tournament combo.

```forge-deck
[Main]
4 Birds of Paradise
3 Lich
3 Mirror Universe
4 Dark Heart of the Wood
4 Dark Ritual
3 Avoid Fate
3 Drain Life
2 Terror
2 Sylvan Library
1 Demonic Tutor
1 Mind Twist
1 Regrowth
1 Sol Ring
1 Mox Emerald
1 Mox Jet
1 Black Lotus
1 Chaos Orb
4 Bayou
4 City of Brass
1 Strip Mine
6 Forest
9 Swamp
[Sideboard]
3 Gloom
3 Crumble
3 Tranquility
2 Terror
2 Whirling Dervish
2 Tormod's Crypt
```

## Mulligan priorities

Demand black development toward BBBB and a useful survival/draw path. Multiple Liches are not protection; never keep merely because the combo names are present with no way to cast them.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 1 Swamp;1 Bayou;1 Forest;1 Birds of Paradise;1 Dark Ritual;1 Lich;1 Dark Heart of the Wood | Keep | - | Fixing and Ritual support the engine; deployment still depends on the opponent's pressure. |
| H2 | Draw / unknown | 0 | 1 Swamp;1 Bayou;1 Forest;1 Birds of Paradise;1 Terror;1 Mirror Universe;1 Dark Heart of the Wood | Keep | - | A defensive setup can develop without immediately risking Lich. |
| H3 | Play or draw / unknown | 0 | 4 Birds of Paradise; 3 Lich | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 Bayou; 3 City of Brass | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 1 Swamp;1 Bayou;1 Forest;1 Birds of Paradise;1 Dark Ritual;1 Lich;1 Dark Heart of the Wood | Keep six | 1 Lich | At six, prefer functioning resources while finding a protected payoff later. |
| H6 | Draw / unknown | 2 | 1 Swamp;1 Bayou;1 Forest;1 Birds of Paradise;1 Terror;1 Mirror Universe;1 Dark Heart of the Wood | Keep five | 1 Mirror Universe;1 Dark Heart of the Wood | At five retain mana, fixing and interaction rather than expensive dependencies. |

## Sequencing and resources

- **Early:** Develop enough permanents, black mana and relevant protection before casting Lich; life becomes zero as it enters.
- **Middle:** Dark Heart sacrifices a Forest as a cost. Under Lich, its life gain becomes draws; don't sacrifice the mana needed for the next spell or draw beyond the remaining library.
- **Late:** Mirror activates only on your upkeep. Account for the resulting replacement draws as well as the opponent's life loss; a simultaneous decking loss can spoil a nominal win.
- **Mana burn:** Mana burn is life loss, not damage: with Lich active it does not itself trigger the damage/sacrifice ability. That does not make losing Lich safe or exempt the engine from other loss conditions.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | The damage trigger consumes nontoken permanents; a wide attack can exhaust the board even while Lich permits zero life. |
| Midrange | Protect Lich from the actual removal available rather than assuming large creatures are the only danger. |
| Control | Disenchant, counters and bounce threaten the engine; find a protected window, not just four black mana. |
| Combo | Different combos can ignore your life protection; disrupt their actual win condition. |
| Prison | Mana denial may prevent both BBBB and Mirror; prioritize resource access over assembling inactive pieces. |

### Named exceptions and common mistakes

- The Deck: removing Lich can end the game; Avoid Fate only answers eligible targeted instant/Aura spells, not every removal effect.
- TaxEdge: repeated damage can force many sacrifices even though Lich stops zero-life loss; life total alone is not the survival metric.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| White Weenie / draw | 2 Terror | 2 Sylvan Library | Add early interaction for legal targets while reducing optional life/card setup. |
| The Deck / play | 3 Gloom;2 Whirling Dervish | 2 Terror;3 Drain Life | Trade narrow removal/slow drain for disruption and an alternate clock; keep the engine rather than assuming a full transformation. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / lich-mirror-D1 | Lich is castable but no survival/protection plan exists | Delay deployment when it creates an immediate losing vulnerability | An otherwise unavoidable loss may justify risk | Casting the engine can reduce safety (H) |
| 90 / lich-mirror-D2 | A Dark Heart activation would draw more cards than remain | Reject the self-decking line unless the resulting outcome is deliberately acceptable | Check all replacement and prevention effects | Lich's draws are mandatory when the gain is replaced (R) |
| 80 / lich-mirror-D3 | Mirror exchange is proposed while Lich holds you at zero | Evaluate both life changes and replacement draws | Target legality, inability to gain/lose life or decking can invalidate a clean win | A combo label is not outcome proof (H) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Lich is active at zero life, Mirror is ready during your upkeep, opponent is at 20, and you have at least 20 cards in library with no other relevant effects. Exchange can put the opponent at zero while your gain is replaced by draws; do not evaluate it as ordinary healing.
- **S2** Lich plus Dark Heart is active, and only two cards remain in your library. Sacrificing a Forest to gain three would require three replacement draws; reject it as safe card advantage.

## Evidence and implementation boundary

- [Wak-Wak: Lich Mirror](https://www.wak-wak.se/9394decks/2018-5-11/lich-mirror): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Birds of Paradise](../../forge-gui/res/cardsfolder/b/birds_of_paradise.txt), [Lich](../../forge-gui/res/cardsfolder/l/lich.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
