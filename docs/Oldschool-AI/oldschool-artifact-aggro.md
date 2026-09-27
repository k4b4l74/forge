# Artifact Aggro

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `artifact-aggro` · Family: **Aggro** · Rules: `swedish-fe-burn-london-v1`
Aliases: Robots, Workshop Aggro.

## Identity and construction

Acceleration deploys large artifact creatures ahead of ordinary curves. Copy Artifact doubles the best existing artifact; it does not produce a robot in an empty battlefield.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/artifact-aggro) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

This blue robot fixture uses four Vaults but only the single allowed Workshop. Su-Chi, Juggernaut and Triskelion distinguish it from slow artifact-control and Coffin variants.

```forge-deck
[Main]
4 Su-Chi
4 Juggernaut
4 Triskelion
4 Mana Vault
3 Copy Artifact
3 Counterspell
2 Transmute Artifact
2 Icy Manipulator
1 Ancestral Recall
1 Time Walk
1 Braingeyser
1 Sol Ring
1 Mox Sapphire
1 Black Lotus
1 Chaos Orb
1 Mishra's Workshop
4 Mishra's Factory
1 Strip Mine
3 City of Brass
18 Island
[Sideboard]
3 Blue Elemental Blast
3 Control Magic
3 Hurkyl's Recall
2 Tormod's Crypt
2 Boomerang
2 Jayemdae Tome
```

## Mulligan priorities

Require a payoff for acceleration and an actual plan for blue costs. Workshop plus Vault is not UU for Counterspell or Transmute. Su-Chi death mana cannot be counted as unconditional safe acceleration.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 2 Island;1 Mana Vault;1 Su-Chi;1 Juggernaut;1 Counterspell;1 Copy Artifact | Keep | - | Vault produces early board power with durable blue development afterward. |
| H2 | Draw / unknown | 0 | 3 Island;1 Mana Vault;1 Su-Chi;1 Triskelion;1 Counterspell | Keep | - | A stable mana base supports both acceleration and a later six-drop. |
| H3 | Play or draw / unknown | 0 | 4 Su-Chi; 3 Juggernaut | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 3 City of Brass; 4 Island | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 2 Island;1 Mana Vault;1 Su-Chi;1 Juggernaut;1 Counterspell;1 Copy Artifact | Keep six | 1 Juggernaut | Keep the first robot, copy option and interaction. |
| H6 | Draw / unknown | 2 | 3 Island;1 Mana Vault;1 Su-Chi;1 Triskelion;1 Counterspell | Keep five | 1 Island;1 Triskelion | Retain two lands, Vault, an immediate robot and interaction rather than the most expensive payoff. |

## Sequencing and resources

- **Early:** Spend acceleration on a board-changing threat, not another mana artifact without a purpose.
- **Middle:** Use Copy Artifact only after identifying a worthwhile object and the resulting permanent's actual characteristics. Keep blue up when the next answer is likely to be Disenchant or a bounce spell.
- **Late:** Triskelion counters can remove blockers or finish; once spent, evaluate whether a 1/1 is still an attack rather than assuming printed size.
- **Mana burn:** Su-Chi adds four colorless on death. If it dies in combat, you cannot save that mana for a postcombat Triskelion. Workshop mana has spending restrictions even before it burns.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | A large early blocker can make you the control player; Triskelion clears small creatures. |
| Midrange | Prioritize board tempo and useful copies; stealing a robot can swing more than a small burn spell. |
| Control | Deploy threats in waves around sweepers and preserve Counterspell for key artifact hate. |
| Combo | Race quickly; Transmute may fetch a needed engine answer only if enough blue and sacrifice material exist. |
| Prison | Nonland mana helps against some locks, but Energy Flux attacks that same foundation. |

### Named exceptions and common mistakes

- Green-based aggro: Argothian Pixies ignores your artifact blockers. Large toughness alone does not stabilize against it.
- The Abyss: your artifact creatures survive its selection restriction, while nonartifact support creatures would not.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| UR Burn / draw | 3 Blue Elemental Blast;2 Jayemdae Tome | 2 Transmute Artifact;2 Icy Manipulator;1 Juggernaut | Add red protection and recovery without boarding self-destructive Energy Flux. |
| Artifact Aggro / play | 3 Control Magic;3 Hurkyl's Recall | 3 Counterspell;2 Icy Manipulator;1 Braingeyser | Favor board swings against a confirmed robot-heavy mirror; Recall is temporary, so exploit the attack window. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / artifact-aggro-D1 | Workshop is the only available source for a proposed activation | Reject payment if the mana restriction forbids it | Another unrestricted source can pay | Workshop is not unrestricted colorless mana (R) |
| 90 / artifact-aggro-D2 | Su-Chi will die this combat | Evaluate legal same-phase uses or unavoidable burn | Do not assume a main-phase spell can spend the pool later | Mandatory death mana changes exchanges (R) |
| 80 / artifact-aggro-D3 | Copy Artifact has no useful battlefield artifact to copy | Prefer developing a real threat or retaining the card | A useful mana-artifact copy can still be correct | Copy value depends on the board (H) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Your Su-Chi dies during combat, adding four colorless; you have no legal sink before combat ends. Under this contract you lose four life, not cast a postcombat robot with that mana.
- **S2** Opponent attacks with Argothian Pixies and your only creatures are Su-Chi and Juggernaut. Neither can block it.

## Evidence and implementation boundary

- [Wak-Wak: Artifact Aggro](https://www.wak-wak.se/9394decks/artifact-aggro): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Su-Chi](../../forge-gui/res/cardsfolder/s/su_chi.txt), [Juggernaut](../../forge-gui/res/cardsfolder/j/juggernaut.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
