# Enchantress

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `enchantress` · Family: **Combo** · Rules: `swedish-fe-burn-london-v1`
Aliases: Verduran Enchantress.

## Identity and construction

Verduran Enchantress turns cast enchantment spells into cards, allowing a dense enchantment package to build resources toward a noncombat finish. Without Enchantress, some hands are merely setup.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/enchantress) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

The RG fixture fixes a Sylvan/Fastbond/Dark Heart/Mana Flare route into Mirror and Fireball. It does not assume later Enchantress cards or automatic protection from targeted removal.

```forge-deck
[Main]
4 Verduran Enchantress
4 Wild Growth
4 Fastbond
4 Sylvan Library
3 Dark Heart of the Wood
3 Mana Flare
2 Mirror Universe
2 Fireball
4 Birds of Paradise
3 Fog
1 Regrowth
1 Mox Emerald
1 Mox Ruby
1 Black Lotus
1 Sol Ring
4 Taiga
4 City of Brass
3 Mountain
1 Strip Mine
10 Forest
[Sideboard]
3 Red Elemental Blast
3 Crumble
3 Tranquility
2 Avoid Fate
2 Whirling Dervish
2 Tormod's Crypt
```

## Mulligan priorities

Look for green development plus Enchantress or another useful engine. Multiple Fastbonds without draw are redundant; a seven full of expensive finishers is not rescued by one mana enchantment.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 2 Forest;1 Taiga;1 Birds of Paradise;1 Verduran Enchantress;1 Wild Growth;1 Sylvan Library | Keep | - | A supported draw creature has enchantments to convert into cards. |
| H2 | Draw / unknown | 0 | 2 Forest;1 Taiga;1 Verduran Enchantress;1 Wild Growth;1 Fog;1 Fireball | Keep | - | Stable mana, a cast-trigger engine and a defensive turn are present. |
| H3 | Play or draw / unknown | 0 | 4 Verduran Enchantress; 3 Wild Growth | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 Taiga; 3 City of Brass | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 2 Forest;1 Taiga;1 Birds of Paradise;1 Verduran Enchantress;1 Wild Growth;1 Sylvan Library | Keep six | 1 Sylvan Library | Keep the immediate creature/cheap-enchantment sequence. |
| H6 | Draw / unknown | 2 | 2 Forest;1 Taiga;1 Verduran Enchantress;1 Wild Growth;1 Fog;1 Fireball | Keep five | 1 Fireball;1 Fog | At five retain the mana, Enchantress and cheap trigger rather than a distant finisher. |

## Sequencing and resources

- **Early:** Sequence Enchantress before nonessential enchantments when the extra draw is worth the delay; don't postpone necessary acceleration if that strands the engine.
- **Middle:** Count enchantment cast triggers even if the spell is countered, while respecting removal before later casts. Multiple Sylvans need careful draw-step handling, not assumed free cards.
- **Late:** Finish once mana and burn are sufficient; Mirror requires a safe next upkeep. Enchantress draws are optional: decline them when drawing would lose, rather than refusing an otherwise useful enchantment spell.
- **Mana burn:** Mana Flare helps both players and magnifies every tapped land. Avoid overproducing while cycling enchantments; the draw trigger does not spend mana.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Fog buys a turn but not immunity to burn; build toward a timely finish rather than perpetual setup. |
| Midrange | Creature removal on Enchantress is central; force value from the first few enchantment casts when possible. |
| Control | Protect the draw engine and reserve enchantment resets for necessity, since Tranquility destroys your own infrastructure. |
| Combo | Compare engine speed and use targeted disruption; drawing many cards is not itself a win. |
| Prison | An engine behind an untap lock may be unusable; preserve colored mana and actual answers. |

### Named exceptions and common mistakes

- The Deck: countering an enchantment spell does not erase an already triggered Enchantress draw, but killing Enchantress stops future triggers.
- MirrorBall: both may use Sylvan/Mirror, but this fixture's card advantage depends heavily on keeping a nonartifact creature alive.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| UR Burn / draw | 3 Red Elemental Blast;2 Avoid Fate | 2 Mirror Universe;3 Mana Flare | Reduce slow and symmetric acceleration while protecting relevant engine turns. |
| Artifact Aggro / play | 3 Crumble | 2 Mirror Universe;1 Mana Flare | Buy time against fast artifacts instead of retaining all expensive payoff routes. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / enchantress-D1 | Enchantress is in play as you cast an enchantment | Account for its cast trigger separately from the spell | If removed before casting, no future trigger exists | Countering the spell does not counter the trigger (R) |
| 90 / enchantress-D2 | An Enchantress trigger resolves with an empty library | Decline its optional draw | Other mandatory draw effects still need separate evaluation | The card says you may draw; casting the enchantment need not cause self-decking (R) |
| 80 / enchantress-D3 | An enchantment can be delayed until after Enchantress | Wait when development/survival does not suffer | Necessary early Wild Growth may correctly precede it | Sequence for value without sacrificing tempo (H) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Enchantress is on the battlefield when you cast Wild Growth; the opponent counters Growth. The already-triggered optional draw still exists.
- **S2** You have one card left in library and two Enchantresses. Casting an enchantment creates two optional draw triggers; you may draw once and decline the second. Do not force a second draw or reject the enchantment solely because two triggers exist.

## Evidence and implementation boundary

- [Wak-Wak: Enchantress](https://www.wak-wak.se/9394decks/enchantress): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Verduran Enchantress](../../forge-gui/res/cardsfolder/v/verduran_enchantress.txt), [Wild Growth](../../forge-gui/res/cardsfolder/w/wild_growth.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
