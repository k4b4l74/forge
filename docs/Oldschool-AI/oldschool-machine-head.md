# Machine Head

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `machine-head` · Family: **Midrange** · Rules: `swedish-fe-burn-london-v1`
Aliases: BG fatties, Black Green Midrange.

## Identity and construction

In this catalog, Machine Head means black-green accelerated large creatures, not the later red-black deck with the same name. Ritual, Birds and Elves of Deep Shadow support early Juzam or Erhnam.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/machine-head) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

The FE-enabled BG fixture adds Hymn while retaining a creature-centered finish. It uses green artifact/enchantment interaction after board rather than a white splash.

```forge-deck
[Main]
4 Birds of Paradise
3 Elves of Deep Shadow
4 Juzám Djinn
4 Erhnam Djinn
4 Dark Ritual
4 Hymn to Tourach
3 Berserk
2 Sylvan Library
2 Terror
1 Demonic Tutor
1 Mind Twist
1 Regrowth
1 Mox Jet
1 Mox Emerald
1 Black Lotus
4 Bayou
4 City of Brass
2 Mishra's Factory
1 Strip Mine
4 Forest
9 Swamp
[Sideboard]
3 Crumble
3 Tranquility
3 Gloom
2 Terror
2 Whirling Dervish
2 Tormod's Crypt
```

## Mulligan priorities

Keep durable black/green access and a route to a threat. Multiple Djinns without acceleration or sufficient lands are not a keep. Ritual does not make green and a dead Bird can collapse greedy hands.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 1 Swamp;1 Bayou;1 Dark Ritual;1 Juzám Djinn;1 Birds of Paradise;1 Hymn to Tourach;1 Terror | Keep | - | Ritual plus two black sources enables Juzam; Bird supports green follow-ups. |
| H2 | Draw / unknown | 0 | 1 Bayou;1 Forest;1 Swamp;1 Birds of Paradise;1 Erhnam Djinn;1 Terror;1 Berserk | Keep | - | Stable colors and acceleration support a large body and interaction. |
| H3 | Play or draw / unknown | 0 | 4 Birds of Paradise; 3 Elves of Deep Shadow | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 Bayou; 3 City of Brass | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 1 Swamp;1 Bayou;1 Dark Ritual;1 Juzám Djinn;1 Birds of Paradise;1 Hymn to Tourach;1 Terror | Keep six | 1 Birds of Paradise | The two lands already support the key early black sequence. |
| H6 | Draw / unknown | 2 | 1 Bayou;1 Forest;1 Swamp;1 Birds of Paradise;1 Erhnam Djinn;1 Terror;1 Berserk | Keep five | 1 Forest;1 Berserk | Retain two colors, Bird, Erhnam and removal rather than conditional burst. |

## Sequencing and resources

- **Early:** Use mana creatures to reach a threat, but assess whether spending Ritual now leaves a useful next turn.
- **Middle:** A large body often controls combat without attacking. Use discard to protect a decisive turn and save Berserk for a real favorable exchange or kill.
- **Late:** Monitor Juzam damage and Sylvan payments; winning the board does not grant unlimited time at low life.
- **Mana burn:** Ritual into Juzam requires the fourth mana as well as BBB. Do not tap extra acceleration just because the threat is expensive; spare mana at the boundary loses life.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Stabilize with a large blocker and remove evasive threats before racing. |
| Midrange | Choose trades by regeneration, protection and evasion, not only printed power. |
| Control | Discard plus a fast large threat is the plan; hold an answer for Moat/Abyss after board. |
| Combo | Use hand disruption before their payoff turn while building a clock. |
| Prison | Green sideboard removal can break enchantment locks, but you need to preserve a green source. |

### Named exceptions and common mistakes

- The Abyss: both Djinn types are nonartifact and vulnerable; simply casting more four-drops is not a durable answer.
- Mono Black: Terror cannot destroy Juzam; don't evaluate that card as generic creature removal in the mirror.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| The Deck / play | 3 Tranquility;3 Gloom | 2 Terror;3 Berserk;1 Elves of Deep Shadow | Trade narrow interaction and risky pump for anti-control tools while keeping most acceleration. |
| White Weenie / draw | 2 Terror | 2 Sylvan Library | Reduce life payments and increase legal removal targets; Terror still cannot target protection-from-black creatures. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / machine-head-D1 | Juzam's upkeep will reduce your life to zero | Seek a winning line or remove/neutralize your own liability before that upkeep | Do not assume a large blocker solves noncombat self-damage | Upkeep pressure matters (R) |
| 90 / machine-head-D2 | Terror is considered against a black or artifact creature | Reject the illegal target | Use another answer or combat plan | Printed target restrictions apply (R) |
| 80 / machine-head-D3 | A four-drop hand depends entirely on one mana creature | Price in the development failure if it dies | A favorable mulligan depth can justify risk | Do not count vulnerable fixing as guaranteed land (H) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Opponent controls Juzám Djinn and you hold Terror. Juzam is black, so Terror is not a legal solution.
- **S2** You control Juzam at 1 life with no prevention. Passing to your next upkeep is lethal to you unless you change the position; the clock evaluator must include it.

## Evidence and implementation boundary

- [Wak-Wak: Machine Head](https://www.wak-wak.se/9394decks/machine-head): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Birds of Paradise](../../forge-gui/res/cardsfolder/b/birds_of_paradise.txt), [Elves of Deep Shadow](../../forge-gui/res/cardsfolder/e/elves_of_deep_shadow.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
