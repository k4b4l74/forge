# Arboria Control

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `arboria-control` · Family: **Control** · Rules: `swedish-fe-burn-london-v1`
Aliases: Arboria mill.

## Identity and construction

Arboria can deny attacks on a player who did not cast a spell or put a nontoken permanent onto the battlefield during their last turn. Activated win conditions exploit the distinction.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/arboria-control) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

The GW fixture wins with Millstone or Hive tokens while using white answers on opposing turns when possible. Tokens do not by themselves break Arboria's nontoken-permanent condition.

```forge-deck
[Main]
4 Arboria
3 Millstone
2 The Hive
4 Swords to Plowshares
4 Disenchant
2 Wrath of God
3 Sylvan Library
4 Birds of Paradise
3 Holy Day
1 Regrowth
1 Balance
1 Mox Pearl
1 Mox Emerald
1 Black Lotus
1 Sol Ring
1 Chaos Orb
4 Savannah
4 City of Brass
2 Mishra's Factory
1 Strip Mine
5 Forest
8 Plains
[Sideboard]
3 Divine Offering
2 Dust to Dust
2 Circle of Protection: Red
2 Spirit Link
2 Serra Angel
2 Armageddon
2 Tormod's Crypt
```

## Mulligan priorities

Keep mana and early survival, not Arboria plus only expensive activation engines. Arboria is not protection against burn and does not make the turn you cast it automatically safe.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 1 Savannah;1 Forest;1 Plains;1 Birds of Paradise;1 Swords to Plowshares;1 Arboria;1 Millstone | Keep | - | Development and removal bridge to the lock and a non-spell finish. |
| H2 | Draw / unknown | 0 | 1 Savannah;1 Forest;1 Plains;1 Swords to Plowshares;1 Holy Day;1 Disenchant;1 The Hive | Keep | - | Immediate defensive cards buy the time needed for a slow engine. |
| H3 | Play or draw / unknown | 0 | 4 Arboria; 3 Millstone | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 Savannah; 3 City of Brass | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 1 Savannah;1 Forest;1 Plains;1 Birds of Paradise;1 Swords to Plowshares;1 Arboria;1 Millstone | Keep six | 1 Millstone | Stabilize first and find a finish later. |
| H6 | Draw / unknown | 2 | 1 Savannah;1 Forest;1 Plains;1 Swords to Plowshares;1 Holy Day;1 Disenchant;1 The Hive | Keep five | 1 The Hive;1 Disenchant | At five, keep the mana and the immediate survival package. |

## Sequencing and resources

- **Early:** Establish mana and a defensive board before entering the no-spell/no-nontoken-permanent pattern.
- **Middle:** Once relying on Arboria, skip a routine land drop or own-turn spell when its cost is reopening attacks. Use relevant instants during the opponent's turn instead.
- **Late:** Activate Millstone or make Hive tokens while maintaining the restriction. Opponents may also become protected by taking an inactive turn, so token combat is not inevitable.
- **Mana burn:** Repeated activations can spend excess mana, but do not cast an unnecessary own-turn spell merely to avoid burn and unintentionally reopen attacks.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Survive the setup window, then distinguish combat prevention from direct burn. |
| Midrange | Arboria can invalidate combat size; retain answers for noncombat engines or effects that remove it. |
| Control | Their instants and card advantage can exploit inactive turns; mill is a plan, not a guaranteed lock. |
| Combo | Arboria often does nothing to stop a noncombat combo; attack the actual engine. |
| Prison | Competing world enchantments and mana restrictions can break your pattern; preserve an escape answer. |

### Named exceptions and common mistakes

- The Abyss: both cards are world enchantments; do not assume both persist as a combined creature lock.
- Sligh: Bolt ignores the attack restriction; holding Holy Day alone is not protection against a burn finish.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| Sligh / draw | 2 Circle of Protection: Red;2 Spirit Link | 2 The Hive;2 Sylvan Library | Reduce slow/life-hungry setup and add ways to stabilize; Link requires a creature and is not immediate prevention. |
| The Deck / play | 2 Serra Angel;2 Armageddon | 3 Holy Day;1 Wrath of God | Offer a pressure transformation when pure defensive combat cards have little value. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / arboria-control-D1 | Arboria is your only barrier to lethal attacks | Avoid own-turn spells and nontoken entries without a survival plan | An answer to a different lethal threat may require breaking the pattern | The condition refers to your last turn (R) |
| 90 / arboria-control-D2 | You can create a Hive token through its activation | Do not mark the token alone as breaking Arboria | Casting another spell or adding a nontoken permanent still does | Tokens are explicitly excluded (R) |
| 80 / arboria-control-D3 | Opponent's win condition is noncombat | Reassign value from Arboria to real interaction | Combat support creatures can still be constrained | Attack denial is not universal defense (H) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** During your turn you only activate The Hive, creating a token, and neither cast a spell nor add a nontoken permanent. Arboria's restriction can still protect you from attacks.
- **S2** During your turn you play a Plains and pass. That nontoken entry means Arboria will not stop attacks against you on the following opposing turn.

## Evidence and implementation boundary

- [Wak-Wak: Arboria Control](https://www.wak-wak.se/9394decks/arboria-control): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Arboria](../../forge-gui/res/cardsfolder/a/arboria.txt), [Millstone](../../forge-gui/res/cardsfolder/m/millstone.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
