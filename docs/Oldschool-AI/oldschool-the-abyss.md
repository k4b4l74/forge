# The Abyss

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `the-abyss` · Family: **Midrange** · Rules: `swedish-fe-burn-london-v1`
Aliases: Abyss Robots, Artifact Abyss.

## Identity and construction

The Abyss constrains nonartifact creatures while artifact threats survive its selection restriction. This is a board-control engine, not a sacrifice outlet.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/the-abyss) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

A UB robot shell makes the asymmetry explicit. It omits Guardian Beast, which is itself a nonartifact creature vulnerable to the enchantment, and avoids incompatible world enchantments.

```forge-deck
[Main]
4 Su-Chi
4 Triskelion
3 The Abyss
3 Copy Artifact
3 Mana Vault
4 Counterspell
2 Terror
2 Transmute Artifact
1 Ancestral Recall
1 Time Walk
1 Demonic Tutor
1 Mind Twist
1 Sol Ring
1 Mox Jet
1 Mox Sapphire
1 Black Lotus
1 Chaos Orb
4 Underground Sea
4 City of Brass
3 Mishra's Factory
1 Strip Mine
6 Island
8 Swamp
[Sideboard]
3 Blue Elemental Blast
3 Gloom
2 Control Magic
2 Hurkyl's Recall
2 Tormod's Crypt
3 Drain Life
```

## Mulligan priorities

Seek stable UB access plus a robot or early interaction. Abyss alone is slow and does little against robots, empty creature boards, or protection that removes all legal targets.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 1 Underground Sea;1 Island;1 Swamp;1 Mana Vault;1 Su-Chi;1 The Abyss;1 Counterspell | Keep | - | Acceleration establishes a robot before the control engine. |
| H2 | Draw / unknown | 0 | 1 Underground Sea;1 Island;1 Swamp;1 Su-Chi;1 Counterspell;1 Terror;1 The Abyss | Keep | - | Three lands and early answers can reach a four-mana stabilization turn. |
| H3 | Play or draw / unknown | 0 | 4 Su-Chi; 3 Triskelion | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 Underground Sea; 3 City of Brass | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 1 Underground Sea;1 Island;1 Swamp;1 Mana Vault;1 Su-Chi;1 The Abyss;1 Counterspell | Keep six | 1 The Abyss | At six, preserve the immediate threat and interaction rather than the slower enchantment. |
| H6 | Draw / unknown | 2 | 1 Underground Sea;1 Island;1 Swamp;1 Su-Chi;1 Counterspell;1 Terror;1 The Abyss | Keep five | 1 The Abyss;1 Su-Chi | At five against unknown, keep reliable mana and survival tools while drawing toward a payoff. |

## Sequencing and resources

- **Early:** Develop blue/black mana and a relevant answer before spending a turn on an inactive control piece.
- **Middle:** The Abyss removes one eligible creature on each player's upkeep, chosen by that player. It does not immediately sweep a wide board.
- **Late:** Once controlled, close with artifacts or Factory rather than giving the opponent unlimited draw steps to find Disenchant.
- **Mana burn:** Su-Chi dying after a removal exchange can add four unwanted mana. Abyss does not destroy your Su-Chi, but other effects still can.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Stabilize the first attacks before relying on incremental Abyss attrition. |
| Midrange | Artifact composition determines whether Abyss is asymmetric, irrelevant, or actively poor. |
| Control | Keep a robot clock; an expensive enchantment with no targets is not pressure. |
| Combo | Use counters and a clock; Abyss is often sideboard material rather than disruption. |
| Prison | Abyss does not answer Stasis, Orb or a noncreature engine; prioritize permission and the actual escape line. |

### Named exceptions and common mistakes

- White Weenie: protection from black can make a creature an illegal Abyss target; it is not unconditional creature sacrifice.
- Robots / Henrik Storm: both decks may have many artifacts; classify by construction instead of assuming your named engine dominates the match.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| UR Burn / draw | 3 Blue Elemental Blast;2 Control Magic | 2 Transmute Artifact;2 The Abyss;1 Copy Artifact | Improve immediate interaction and theft rather than relying on a slow destroy trigger. |
| Artifact Aggro / play | 2 Control Magic;2 Hurkyl's Recall | 3 The Abyss;1 Terror | Remove low-impact black control cards for artifact-specific tempo. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / the-abyss-D1 | Abyss has no legal nonartifact creature target on the relevant side | Assign no automatic removal value | Future creature deployments may change this | The trigger targets rather than sacrifices (R) |
| 90 / the-abyss-D2 | Opponent has several attackers and immediate lethal pressure | Use immediate interaction before a slow Abyss deployment | An accelerated Abyss can help when the next upkeep is enough | Incremental control is not a sweeper (H) |
| 80 / the-abyss-D3 | An active Abyss makes another world enchantment attractive | Evaluate which world permanent remains | Do not score both as simultaneously persistent | World rule prevents the assumed lock (R) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Opponent controls only Su-Chi. Their upkeep under The Abyss does not destroy it; artifact creatures are excluded.
- **S2** Opponent controls only White Knight. The black Abyss cannot legally target it through protection from black; do not force a sacrifice.

## Evidence and implementation boundary

- [Wak-Wak: The Abyss](https://www.wak-wak.se/9394decks/the-abyss): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Su-Chi](../../forge-gui/res/cardsfolder/s/su_chi.txt), [Triskelion](../../forge-gui/res/cardsfolder/t/triskelion.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
