# Goblins

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `goblins` · Family: **Aggro** · Rules: `swedish-fe-burn-london-v1`
Aliases: Mono Red Goblins.

## Identity and construction

A tribal red curve backed by burn and, with Fallen Empires enabled, Goblin Grenade. Goblin King supplies mountainwalk as well as size; its effect can help opposing Goblins too.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/goblins) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

The fixture includes Grenade and Goblin Chirurgeon from Fallen Empires. It does not rely on later Goblin Lackey, Warchief, or Ringleader, none of which belong to this card pool.

```forge-deck
[Main]
4 Goblin Balloon Brigade
4 Goblins of the Flarg
4 Goblin King
4 Goblin Grenade
3 Goblin Chirurgeon
4 Lightning Bolt
4 Chain Lightning
3 Goblin Digging Team
2 Blood Moon
1 Wheel of Fortune
1 Mox Ruby
1 Black Lotus
1 Chaos Orb
3 Mishra's Factory
1 Strip Mine
20 Mountain
[Sideboard]
3 Red Elemental Blast
3 Shatter
2 Earthquake
2 Blood Moon
2 Tormod's Crypt
3 Detonate
```

## Mulligan priorities

Keep actual Goblins plus red sources, not Grenades without sacrifice fodder. One-land sevens need several useful one-drops and are still vulnerable to missing land two; these examples prefer two.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 2 Mountain;1 Goblin Balloon Brigade;1 Goblins of the Flarg;1 Goblin King;1 Lightning Bolt;1 Goblin Grenade | Keep | - | One-drops create a clock and supply Grenade fodder. |
| H2 | Draw / unknown | 0 | 2 Mountain;1 Goblin Balloon Brigade;1 Goblin Chirurgeon;1 Goblin Grenade;1 Lightning Bolt;1 Blood Moon | Keep | - | Cheap creatures and burn function while waiting for a third land. |
| H3 | Play or draw / unknown | 0 | 4 Goblin Balloon Brigade; 3 Goblins of the Flarg | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 7 Mountain | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 2 Mountain;1 Goblin Balloon Brigade;1 Goblins of the Flarg;1 Goblin King;1 Lightning Bolt;1 Goblin Grenade | Keep six | 1 Goblin King | On six, keep the immediate curve and reach instead of the three-drop. |
| H6 | Draw / unknown | 2 | 2 Mountain;1 Goblin Balloon Brigade;1 Goblin Chirurgeon;1 Goblin Grenade;1 Lightning Bolt;1 Blood Moon | Keep five | 1 Blood Moon;1 Goblin Chirurgeon | Two lands, a Goblin and two burn spells form a coherent five. |

## Sequencing and resources

- **Early:** Deploy the one-drop that best fits combat; preserve a Goblin for Grenade rather than trading every body automatically.
- **Middle:** Grenade sacrifices as an additional cost, so a counterspell loses both the spell and its Goblin. Use King's mountainwalk only when the defender actually controls a Mountain.
- **Late:** Count burn and combat jointly. Balloon Brigade's flying activation is sometimes better than another creature into a stalled ground board.
- **Mana burn:** Balloon Brigade can consume spare red mana usefully only if flying changes combat; do not spend the red reserved for Bolt just to avoid a harmless single burn point.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Board presence matters before face damage; King can unintentionally improve the opposing tribal board. |
| Midrange | Use evasion or Grenade to finish instead of trading several creatures into a large blocker. |
| Control | Make sweepers awkward by retaining a threat, but stop holding cards when burn already supplies lethal. |
| Combo | Race and use Shatter on the actual artifact engine when boarded. |
| Prison | A Moon plan is not a Stasis answer; hold up relevant burn while exploiting narrow escape windows. |

### Named exceptions and common mistakes

- White Weenie: first strike can invalidate apparently favorable attacks; Grenade need not target the player when a creature dominates combat.
- Mirror Goblins: King's global bonus and mountainwalk can favor the opponent's attack. Do not cast it solely because three mana is available.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| The Deck / play | 3 Red Elemental Blast;2 Blood Moon | 3 Goblin Chirurgeon;2 Goblin Grenade | Less regeneration and counter-vulnerable burst; retain two Grenades as reach. |
| Artifact Aggro / draw | 3 Shatter;2 Detonate | 2 Blood Moon;3 Goblin Digging Team | Answer artifacts rather than relying on Moon against their nonland acceleration. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / goblins-D1 | Grenade represents lethal with a Goblin available | Check the additional sacrifice cost and response risk | Do not sacrifice the only blocker if the spell cannot credibly win | Sacrifice is paid even if Grenade is countered (R) |
| 90 / goblins-D2 | Opponent also controls Goblins and Mountains | Evaluate both boards before casting King | An immediate winning attack can justify symmetry | Tribal bonuses may help the opponent (H) |
| 80 / goblins-D3 | A single ground blocker stops lethal and Brigade is ready to attack | Consider paying for flying before attackers | A flying or reach blocker may still stop it | Use abilities to turn mana into actual damage (H) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Opponent is at 5; you have a Goblin, Mountain, Grenade, and priority in your main phase. The spell can threaten lethal, but the Goblin is sacrificed on casting, not after the opponent decides whether to counter.
- **S2** Both players control Goblins and Mountains. Adding Goblin King can grant opposing Goblins mountainwalk too; the policy must evaluate the return attack.

## Evidence and implementation boundary

- [Wak-Wak: Goblins](https://www.wak-wak.se/9394decks/goblins): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Goblin Balloon Brigade](../../forge-gui/res/cardsfolder/g/goblin_balloon_brigade.txt), [Goblins of the Flarg](../../forge-gui/res/cardsfolder/g/goblins_of_the_flarg.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
