# Kobolds!

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `kobolds` · Family: **Aggro** · Rules: `swedish-fe-burn-london-v1`
Aliases: Kobold swarm.

## Identity and construction

Zero-mana Kobolds need a power-granting lord or other payoff. A large count of free 0/1 creatures is neither meaningful damage nor a mana engine.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/kobolds) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

The fixture uses the three zero-mana Kobolds and Taskmaster/Drill Sergeant, with burn for reach. It omits expensive Rohgahh: his bonus applies only to Kobolds of Kher Keep, and his upkeep demands RRR.

```forge-deck
[Main]
4 Crimson Kobolds
4 Crookshank Kobolds
4 Kobolds of Kher Keep
4 Kobold Taskmaster
3 Kobold Drill Sergeant
4 Lightning Bolt
4 Chain Lightning
2 Blood Lust
2 Fireball
1 Wheel of Fortune
1 Mox Ruby
1 Black Lotus
1 Chaos Orb
3 Mishra's Factory
1 Strip Mine
21 Mountain
[Sideboard]
3 Red Elemental Blast
3 Shatter
2 Earthquake
2 Blood Moon
2 Tormod's Crypt
3 Detonate
```

## Mulligan priorities

Require red development and a lord or burn-supported plan. Do not keep seven free Kobolds merely because the whole hand can be deployed. Keep redundant payoffs over redundant 0/1 bodies when bottoming.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 2 Mountain;1 Crimson Kobolds;1 Crookshank Kobolds;1 Kobold Taskmaster;1 Lightning Bolt;1 Chain Lightning | Keep | - | Taskmaster turns the free bodies into a clock; burn supplies interaction. |
| H2 | Draw / unknown | 0 | 2 Mountain;1 Kobolds of Kher Keep;1 Kobold Taskmaster;1 Kobold Drill Sergeant;1 Lightning Bolt;1 Fireball | Keep | - | Two lands cast the support creatures; the draw helps toward Fireball. |
| H3 | Play or draw / unknown | 0 | 4 Crimson Kobolds; 3 Crookshank Kobolds | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 7 Mountain | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 2 Mountain;1 Crimson Kobolds;1 Crookshank Kobolds;1 Kobold Taskmaster;1 Lightning Bolt;1 Chain Lightning | Keep six | 1 Crookshank Kobolds | Retain the lord and real interaction rather than a second dependent body. |
| H6 | Draw / unknown | 2 | 2 Mountain;1 Kobolds of Kher Keep;1 Kobold Taskmaster;1 Kobold Drill Sergeant;1 Lightning Bolt;1 Fireball | Keep five | 1 Fireball;1 Kobold Drill Sergeant | The five retains two lands, a body, a power lord and Bolt; keep the essential payoff. |

## Sequencing and resources

- **Early:** Deploy only the bodies that improve the next attack; a free card still costs a card when swept. Sequence the lord where it creates immediate damage from older creatures.
- **Middle:** Protect the power source through removal timing; do not rely on Drill Sergeant alone to supply power. Use burn to remove a blocker when several attackers benefit.
- **Late:** Wheel only when your own exhausted hand improves more than the opponent's. Factory and Fireball are recovery routes after a sweep.
- **Mana burn:** Lotus produces three mana, but the zero-cost creatures spend none. Have enough red spell costs or a Fireball sink before sacrificing it.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Your baseline bodies cannot trade profitably without support; aim burn at attackers that dominate combat. |
| Midrange | Swarm around one large blocker; trample without power still deals no damage. |
| Control | Do not empty the entire hand into a known sweeper; keep a lord for rebuilding. |
| Combo | Race with actual power, not creature count, and deploy relevant artifact hate after board. |
| Prison | Remove the lock before adding more powerless bodies; Factory gives a non-spell threat under some taxes. |

### Named exceptions and common mistakes

- Troll Disco: Disk kills the lords and swarm together; hold a rebuilding package, not only spare 0/1s.
- The Abyss: a stream of cheap bodies can absorb its chosen creature destruction, but never assume the lord is protected when it is the only legal target.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| The Deck / play | 3 Red Elemental Blast;2 Blood Moon | 2 Blood Lust;2 Fireball;1 Kobold Drill Sergeant | Trade clumsy finishing cards for blue interaction and a mostly asymmetric mana constraint. |
| Artifact Aggro / draw | 3 Shatter;2 Detonate | 2 Blood Lust;2 Fireball;1 Kobold Drill Sergeant | Attack blockers and acceleration before a large artifact creature stabilizes. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / kobolds-D1 | All current Kobolds have zero power and Taskmaster is castable | Prioritize a power lord over another zero-cost body | Do not expose the only lord into certain visible lethal retaliation | Creature count alone overstates the clock (H) |
| 90 / kobolds-D2 | A sweeper has been revealed and the existing board already demands it | Retain a lord plus body in hand | Commit more only when it creates a credible earlier kill | Rebuilding needs a payoff, not merely free creatures (H) |
| 80 / kobolds-D3 | Lotus is your only mana and planned spells cost less than three total | Calculate the remainder before activating | Accept burn if necessary to avoid losing or to win now | Free spells do not consume mana (R) (R) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Two old 0/1 Kobolds are ready to attack; Taskmaster resolves in your main phase. They can attack with its power bonus; the newly cast Taskmaster itself cannot attack without haste.
- **S2** You have three normally 0/1 Kobolds, Drill Sergeant, and no other bonuses. The other Kobolds become 0/2 with trample but still deal zero combat damage; Drill Sergeant itself retains its own printed power. Do not turn trample into an invented power bonus.

## Evidence and implementation boundary

- [Wak-Wak: Kobolds!](https://www.wak-wak.se/9394decks/kobolds): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Crimson Kobolds](../../forge-gui/res/cardsfolder/c/crimson_kobolds.txt), [Crookshank Kobolds](../../forge-gui/res/cardsfolder/c/crookshank_kobolds.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
