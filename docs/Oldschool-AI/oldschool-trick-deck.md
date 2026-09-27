# Trick Deck

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `trick-deck` · Family: **Combo** · Rules: `swedish-fe-burn-london-v1`
Aliases: Underworld Dreams wheel, Dreams combo.

## Identity and construction

Underworld Dreams converts opposing draws into damage; wheels, Winds and Mines create those draw events. Refilling an opponent without a payoff may help them more than you.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/trick-deck) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

The BR fixture uses the one legal Wheel, four Winds, Dreams, Vise and Fork. It does not infer that Winds always draws seven: the number depends on the player's shuffled hand.

```forge-deck
[Main]
4 Underworld Dreams
4 Winds of Change
4 Howling Mine
3 Fork
4 Lightning Bolt
4 Black Vise
4 Dark Ritual
3 Sinkhole
2 Fireball
1 Wheel of Fortune
1 Demonic Tutor
1 Mind Twist
1 Sol Ring
1 Mox Jet
1 Mox Ruby
1 Black Lotus
1 Chaos Orb
4 Badlands
4 City of Brass
4 Mountain
8 Swamp
[Sideboard]
3 Red Elemental Blast
3 Shatter
3 Gloom
2 Earthquake
2 Tormod's Crypt
2 Terror
```

## Mulligan priorities

Seek BBB development for Dreams or a useful Vise/interaction start. A hand of wheels with no payoff and no pressure is not a combo keep; consider how much the opponent benefits from each refill.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 2 Swamp;1 Badlands;1 Dark Ritual;1 Underworld Dreams;1 Howling Mine;1 Lightning Bolt | Keep | - | The payoff can arrive early and the draw engine has support. |
| H2 | Draw / unknown | 0 | 1 Swamp;1 Badlands;1 Mountain;1 Black Vise;1 Lightning Bolt;1 Underworld Dreams;1 Winds of Change | Keep | - | Vise and Bolt function while developing toward the BBB enchantment. |
| H3 | Play or draw / unknown | 0 | 4 Underworld Dreams; 3 Winds of Change | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 Badlands; 3 City of Brass | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 2 Swamp;1 Badlands;1 Dark Ritual;1 Underworld Dreams;1 Howling Mine;1 Lightning Bolt | Keep six | 1 Swamp | Retain the engine, interaction and two durable colored sources. |
| H6 | Draw / unknown | 2 | 1 Swamp;1 Badlands;1 Mountain;1 Black Vise;1 Lightning Bolt;1 Underworld Dreams;1 Winds of Change | Keep five | 1 Mountain;1 Underworld Dreams | At five, preserve the cheap plan rather than a currently color-demanding payoff. |

## Sequencing and resources

- **Early:** Establish Dreams before an unnecessary wheel when the damage matters. A turn-one Vise can provide a separate pressure route.
- **Middle:** Calculate draw counts and triggers explicitly. Winds replaces each hand with the same count, whereas Wheel draws seven after discard; copying either still allows responses.
- **Late:** Use a counted damage finish and stop gifting cards once the opponent can exploit them to remove Dreams or win first.
- **Mana burn:** Ritual can cast Dreams for BBB exactly. Vault-like colorless sources in variants do not solve BBB, and Fork still needs RR after the original spell.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Use burn and sweepers to survive; an opponent emptying their hand makes Vise and Winds weaker. |
| Midrange | Board pressure sets a deadline for the draw engine; don't ignore a lethal attack while maximizing triggers. |
| Control | Refills can undo your own hand disruption; apply them only for a favorable engine or resource swing. |
| Combo | A wheel can complete the opponent's combo; prefer an immediate counted finish over speculative damage. |
| Prison | Noncombat damage bypasses attack locks, but enchantment removal and casting taxes still interrupt the engine. |

### Named exceptions and common mistakes

- Lich Mirror: Dreams deals damage, which can force sacrifices under Lich; it is not ordinary direct life loss.
- The Deck: a wheel that replaces a nearly empty control hand without enough Dreams damage may restore all their answers.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| White Weenie / draw | 2 Earthquake;2 Terror | 3 Sinkhole;1 Fork | Favor immediate board interaction over mana denial and conditional copying; respect protection-from-black targets. |
| The Deck / play | 3 Red Elemental Blast;3 Gloom | 4 Lightning Bolt;2 Fireball | Against confirmed creature-light control, preserve the draw-damage engine and contest answers. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / trick-deck-D1 | Winds is proposed as a damage source with Dreams | Count the opponent's current hand and resulting draws | Do not substitute Wheel's fixed seven-card draw | The two refill effects differ (R) |
| 90 / trick-deck-D2 | A wheel would not win and opponent has an exhausted hand | Compare the refill benefit to your engine damage | An otherwise necessary search for survival may justify it | Avoid granting resources without compensation (H) |
| 80 / trick-deck-D3 | Dreams has multiple draw triggers pending | Resolve their damage with current replacement/prevention and loss checks | Decking or other effects may end the game first | Do not merge all draws into an unsupported outcome (R) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Opponent has two cards when Winds resolves and one Dreams is active. Their two resulting draws create two Dreams triggers, not seven.
- **S2** Wheel resolves with one Dreams active and an opponent able to draw seven cards. It creates seven one-damage triggers for that opponent; evaluate responses and life totals rather than treating it as a single seven-damage spell.

## Evidence and implementation boundary

- [Wak-Wak: Trick Deck](https://www.wak-wak.se/9394decks/trick-deck): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Underworld Dreams](../../forge-gui/res/cardsfolder/u/underworld_dreams.txt), [Winds of Change](../../forge-gui/res/cardsfolder/w/winds_of_change.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
