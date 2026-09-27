# MirrorBall

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `mirrorball` · Family: **Combo** · Rules: `swedish-fe-burn-london-v1`
Aliases: Sylvan Mirror, Mirror Ball.

## Identity and construction

Spend life for resources, survive to a Mirror Universe exchange, then finish a newly low-life opponent with burn. The setup never grants permission to remain at zero life.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/sylvan-mirror) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

The RG fixture uses Sylvan, Mirror, Dark Heart and burn with artifact acceleration. Its defensive Fogs and Mazes buy combat turns but do not stop direct burn.

```forge-deck
[Main]
4 Sylvan Library
3 Mirror Universe
3 Dark Heart of the Wood
3 Mana Vault
3 Fireball
4 Lightning Bolt
2 Fog
2 Fork
2 Fastbond
2 Howling Mine
1 Wheel of Fortune
1 Regrowth
1 Sol Ring
1 Mox Emerald
1 Mox Ruby
1 Black Lotus
1 Chaos Orb
4 Taiga
4 City of Brass
3 Maze of Ith
4 Mishra's Factory
3 Mountain
7 Forest
[Sideboard]
3 Red Elemental Blast
3 Crumble
3 Tranquility
2 Fog
2 Whirling Dervish
2 Tormod's Crypt
```

## Mulligan priorities

Prefer stable development plus draw and survival. Mirror without six-mana access and a way to survive is a dead early card; paying eight life to Library is a decision, not the archetype's compulsory script.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 1 Taiga;1 Forest;1 Mountain;1 Sylvan Library;1 Lightning Bolt;1 Mana Vault;1 Mirror Universe | Keep | - | Card flow and interaction can bridge into the expensive exchange. |
| H2 | Draw / unknown | 0 | 1 Taiga;2 Forest;1 Lightning Bolt;1 Fog;1 Sylvan Library;1 Fireball | Keep | - | The hand can develop while controlling early pressure. |
| H3 | Play or draw / unknown | 0 | 4 Sylvan Library; 3 Mirror Universe | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 Taiga; 3 City of Brass | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 1 Taiga;1 Forest;1 Mountain;1 Sylvan Library;1 Lightning Bolt;1 Mana Vault;1 Mirror Universe | Keep six | 1 Mirror Universe | Keep the functioning setup rather than its slow payoff. |
| H6 | Draw / unknown | 2 | 1 Taiga;2 Forest;1 Lightning Bolt;1 Fog;1 Sylvan Library;1 Fireball | Keep five | 1 Fireball;1 Forest | Two sources, Library and two survival tools are preferable to holding the finisher. |

## Sequencing and resources

- **Early:** Use Library to improve options without entering a known burn kill range. Maze and Fog only solve combat portions of the race.
- **Middle:** Mirror must survive until your upkeep and you must remain alive. Consider the opponent's ability to lower their own life or remove Mirror before exchange.
- **Late:** After a favorable exchange, take the shortest protected burn finish; do not keep paying life merely to make an already small opposing life total smaller.
- **Mana burn:** Mana burn can lower life intentionally only within a safe, explicit budget. It cannot be used to reach zero and wait for Mirror next turn.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Buy a combat turn only when it connects to a real stabilization or exchange line; direct damage changes the margin. |
| Midrange | Large creatures may be managed by Maze while resources accumulate; multiple threats require multiple defensive tools. |
| Control | Protect Mirror and a payoff; an opponent can play around the life asymmetry. |
| Combo | Compare actual finish speeds and use relevant permanent answers after board. |
| Prison | Mana denial can make a six-mana artifact unreachable; don't keep a hand assuming future Mirror without development. |

### Named exceptions and common mistakes

- UR Burn: open red mana makes voluntary low-life lines dangerous even when Mirror is on the battlefield.
- Lich Mirror: that deck has an explicit effect allowing zero-life survival; this fixture does not.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| White Weenie / draw | 2 Fog | 2 Fork | Increase cheap defensive turns rather than hold expensive copy combinations. |
| The Deck / play | 3 Red Elemental Blast;3 Tranquility | 2 Fog;2 Fastbond;2 Dark Heart of the Wood | Reduce low-impact combat/life setup and contest counters or lock enchantments. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / mirrorball-D1 | A Library payment leaves you in a known opposing lethal range | Decline the payment unless it is your best survival chance | Unknown hands require risk estimates, not omniscience | Resources are worthless after losing (H) |
| 90 / mirrorball-D2 | Mirror is available outside your upkeep | Do not propose activating it | A different effect may alter timing, but none is assumed here | Activation timing is restricted (R) |
| 80 / mirrorball-D3 | Your intended plan burns life to zero before exchange | Reject it | This fixture has no Lich-like exemption | State-based loss occurs before a future upkeep (R) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** At 4 life, taking an extra Sylvan card for four life loses immediately absent a relevant exemption; Mirror on the battlefield does not change that.
- **S2** During your upkeep, you are at 3 and opponent at 14 with a legal ready Mirror. A successful exchange sets up a three-damage finish, but you still need a legal damage spell and response handling.

## Evidence and implementation boundary

- [Wak-Wak: MirrorBall](https://www.wak-wak.se/9394decks/sylvan-mirror): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Sylvan Library](../../forge-gui/res/cardsfolder/s/sylvan_library.txt), [Mirror Universe](../../forge-gui/res/cardsfolder/m/mirror_universe.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
