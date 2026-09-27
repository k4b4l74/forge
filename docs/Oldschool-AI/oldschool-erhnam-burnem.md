# Erhnam Burn'Em

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `erhnam-burnem` · Family: **Aggro** · Rules: `swedish-fe-burn-london-v1`
Aliases: GR Aggro, RG Aggro.

## Identity and construction

Green efficient attackers backed by red removal and reach. Erhnam is the upper end of an attacking curve, not permission to keep any slow four-mana hand.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/erhnam-burnem) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

Kird Ape, Pixies and Archers supply early combat; Erhnam and burn finish. The two-color fixture leaves blue power out so development and blood-moon exposure are easier to test.

```forge-deck
[Main]
4 Kird Ape
4 Argothian Pixies
3 Elvish Archers
3 Erhnam Djinn
4 Lightning Bolt
4 Chain Lightning
3 Giant Growth
2 Berserk
2 Sylvan Library
1 Regrowth
1 Mox Emerald
1 Mox Ruby
1 Black Lotus
1 Chaos Orb
4 Taiga
3 City of Brass
2 Mishra's Factory
1 Strip Mine
5 Mountain
11 Forest
[Sideboard]
3 Red Elemental Blast
3 Tranquility
3 Crumble
2 Whirling Dervish
2 Earthquake
2 Tormod's Crypt
```

## Mulligan priorities

Keep red/green access and an early body. Kird Ape wants a Forest-type land, not merely a green-producing City. Do not keep pump spells without a creature or all four-drops on two lands.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 1 Taiga;1 Forest;1 Mountain;1 Kird Ape;1 Argothian Pixies;1 Lightning Bolt;1 Giant Growth | Keep | - | Taiga both casts Ape and supplies its Forest condition. |
| H2 | Draw / unknown | 0 | 1 Taiga;1 Forest;1 Kird Ape;1 Argothian Pixies;1 Lightning Bolt;1 Sylvan Library;1 Erhnam Djinn | Keep | - | Cheap creatures bridge into Library and the four-drop. |
| H3 | Play or draw / unknown | 0 | 4 Kird Ape; 3 Argothian Pixies | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 Taiga; 3 City of Brass | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 1 Taiga;1 Forest;1 Mountain;1 Kird Ape;1 Argothian Pixies;1 Lightning Bolt;1 Giant Growth | Keep six | 1 Giant Growth | Keep bodies and unconditional interaction. |
| H6 | Draw / unknown | 2 | 1 Taiga;1 Forest;1 Kird Ape;1 Argothian Pixies;1 Lightning Bolt;1 Sylvan Library;1 Erhnam Djinn | Keep five | 1 Erhnam Djinn;1 Sylvan Library | At five, two sources and immediate action matter more than both expensive follow-ups. |

## Sequencing and resources

- **Early:** Sequence Taiga for Ape when safe; keep a basic available when Moon is expected.
- **Middle:** Use first strike and pump to make combat profitable, but apply Berserk only in a legal window and with a reason to risk removal.
- **Late:** Library's life payments compete with opposing burn. Erhnam's forestwalk gift requires checking the return attack, not merely clicking a target.
- **Mana burn:** Avoid tapping Lotus or excess lands before choosing a pump line; losing unused mana after combat is not recoverable with a postcombat spell.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Trade where first strike or size gives an advantage and preserve burn for evasive threats. |
| Midrange | Use pump to cross a size barrier only when the exchange survives plausible removal. |
| Control | Pressure early and retain enchantment removal for Moat or The Abyss. |
| Combo | Prioritize a short clock; Crumble or Tranquility must disrupt the relevant component. |
| Prison | Keep green mana for Tranquility; nonbasic-heavy mana can make your own Moon plan inappropriate. |

### Named exceptions and common mistakes

- Robots: Argothian Pixies cannot be blocked by artifact creatures, so it may be your best clock even beside a larger Erhnam.
- Reanimator: Berserk does not bypass all blockers or lifegain; calculate trample and the opponent's reanimation targets before racing.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| The Deck / play | 3 Red Elemental Blast;3 Tranquility | 3 Giant Growth;2 Berserk;1 Elvish Archers | Reduce removal-sensitive pump and gain ways through blue answers and enchantments. |
| Artifact Aggro / draw | 3 Crumble | 2 Sylvan Library;1 Erhnam Djinn | Trade slow development for cheap artifact answers; Crumble grants life, so prefer a tempo-critical target. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / erhnam-burnem-D1 | Kird Ape is in hand and Taiga/City are both available | Prefer a Forest-type source supporting the curve | Respect visible land denial and alternate spell requirements | Green mana alone does not enlarge Ape (R) |
| 90 / erhnam-burnem-D2 | Pixies can attack through only artifact blockers | Preserve it as an evasive clock | Do not ignore nonartifact blockers or noncombat removal | Board-specific evasion can exceed raw size (H) |
| 80 / erhnam-burnem-D3 | Sylvan offers extra cards while opposing burn is represented | Pay life only within an explicit survival budget | A needed immediate answer can justify risk | Four life is a meaningful resource (H) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** You control City of Brass and Kird Ape but no Forest. Ape is 1/1, not 2/3; playing Taiga changes that condition.
- **S2** Opponent controls only Su-Chi as a potential blocker; your Argothian Pixies may attack past it. Do not spend Bolt solely to clear that artifact blocker.

## Evidence and implementation boundary

- [Wak-Wak: Erhnam Burn'Em](https://www.wak-wak.se/9394decks/erhnam-burnem): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Kird Ape](../../forge-gui/res/cardsfolder/k/kird_ape.txt), [Argothian Pixies](../../forge-gui/res/cardsfolder/a/argothian_pixies.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
