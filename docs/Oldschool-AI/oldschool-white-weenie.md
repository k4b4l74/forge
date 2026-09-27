# White Weenie

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `white-weenie` · Family: **Aggro** · Rules: `swedish-fe-burn-london-v1`
Aliases: WW, Mono White Aggro.

## Identity and construction

Cheap white creatures plus global pump, with Swords and Disenchant protecting the attack. Fallen Empires adds Javelineers and Order of Leitbur without changing the basic role.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/white-weenie) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

A mono-white fixture combines first strike, Crusade and two Armageddons. It keeps a high Plains count for WW and avoids pretending a creature-only draw can answer The Abyss.

```forge-deck
[Main]
4 Savannah Lions
4 White Knight
4 Icatian Javelineers
4 Order of Leitbur
4 Tundra Wolves
4 Crusade
4 Swords to Plowshares
3 Disenchant
2 Armageddon
1 Land Tax
1 Mox Pearl
1 Black Lotus
1 Chaos Orb
2 Mishra's Factory
1 Strip Mine
20 Plains
[Sideboard]
3 Spirit Link
2 Circle of Protection: Red
1 Disenchant
2 Divine Offering
2 Dust to Dust
2 Armageddon
1 Serra Angel
2 Tormod's Crypt
```

## Mulligan priorities

Seek two white sources and a one-two curve. Crusades without creatures and creatures without enough W/WW are traps. On the draw versus burn, removal or first strike can be more important than maximum power.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 2 Plains;1 Savannah Lions;1 White Knight;1 Icatian Javelineers;1 Crusade;1 Swords to Plowshares | Keep | - | The curve is live and both combat and removal lines are available. |
| H2 | Draw / unknown | 0 | 3 Plains;1 Savannah Lions;1 White Knight;1 Disenchant;1 Swords to Plowshares | Keep | - | Reliable white mana and answers support a modest clock. |
| H3 | Play or draw / unknown | 0 | 4 Savannah Lions; 3 White Knight | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 7 Plains | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 2 Plains;1 Savannah Lions;1 White Knight;1 Icatian Javelineers;1 Crusade;1 Swords to Plowshares | Keep six | 1 Crusade | Retain independent creatures and removal rather than the conditional anthem. |
| H6 | Draw / unknown | 2 | 3 Plains;1 Savannah Lions;1 White Knight;1 Disenchant;1 Swords to Plowshares | Keep five | 1 Plains;1 Disenchant | At five against unknown, retain two lands, two creatures and broad creature removal. |

## Sequencing and resources

- **Early:** Lead with the body suited to the opponent: Javelineers can control x/1s, Lions maximizes early damage.
- **Middle:** Add Crusade when its immediate combat improvement exceeds another body. Remember it also boosts opposing white creatures.
- **Late:** Armageddon requires a winning board, not mere access to four mana. Save Disenchant for effects that invalidate the entire creature plan.
- **Mana burn:** Order's pump gives a possible main/combat sink, but Crusade and Armageddon cannot spend mana floated out of combat. Avoid unnecessary Lotus overproduction.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | First strike and removal can shift you into the stabilizing role; beware global Crusade symmetry. |
| Midrange | Go wide or use Swords on the dominant blocker instead of making repeated unfavorable trades. |
| Control | Deploy a clock without emptying into Wrath; keep enchantment answers. |
| Combo | Present pressure and Disenchant their critical artifact or enchantment; do not rely on lifegain to beat noncombat engines. |
| Prison | White has broad permanent answers, but they require keeping white mana available before the lock closes. |

### Named exceptions and common mistakes

- The Deck: The Abyss is a primary threat, not just another value permanent. A pilot report records it deciding games; that observation supports testing answer conservation, not a win-rate claim.
- Mono Black: protection on White Knight and Order changes targeted removal; still respect Disk, which does not target.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| Sligh / draw | 3 Spirit Link;2 Circle of Protection: Red | 2 Armageddon;3 Crusade | Prioritize survival; Link does not prevent lethal damage before its trigger resolves. |
| The Deck / play | 1 Disenchant;2 Armageddon;1 Serra Angel | 4 Swords to Plowshares | For a confirmed creature-light build, exchange excess removal for resilience and pressure. Retain Swords if Serra or robots are observed. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / white-weenie-D1 | Opponent's white blockers also benefit from Crusade | Calculate both sides of combat before casting | An immediate winning attack can justify the symmetry | Crusade is not one-sided (R) |
| 90 / white-weenie-D2 | Known sweeper can remove your developed board | Hold a follow-up creature if existing pressure is sufficient | Commit if it produces a credible immediate win | Reduce recovery cost (H) |
| 80 / white-weenie-D3 | Disenchant is your only out to visible Abyss or Moat | Reserve it for the lock rather than a redundant Mox | Emergency mana denial can override | Protect the attack's viability (H) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Both you and the opponent control White Knight. Casting Crusade boosts both; do not score only your own creature.
- **S2** You have Spirit Link on an opponent's attacker and are at 2 life facing 3 unprevented damage. Its life-gain trigger does not save you from the preceding state-based loss.

## Evidence and implementation boundary

- [Wak-Wak: White Weenie](https://www.wak-wak.se/9394decks/white-weenie): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.
- [Grant Casleton, White Weenie Weekend](https://www.wak-wak.se/blog/2017-10-31/white-weenie-weekend): pilot observation of The Abyss being decisive; EC event context, not a Swedish-FE benchmark.

Local card definitions: [Savannah Lions](../../forge-gui/res/cardsfolder/s/savannah_lions.txt), [White Knight](../../forge-gui/res/cardsfolder/w/white_knight.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
