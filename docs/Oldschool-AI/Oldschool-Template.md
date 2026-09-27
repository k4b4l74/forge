# Playbook authoring contract

Use [the index](Oldschool-AI.md) and [shared rules](Oldschool-Rules.md) first.
All 50 guides are documentation, not loaded AI profiles. A proposed rule or
acceptance case is not an implemented or passing Java test.

Each `oldschool-<stable-id>.md` contains:

1. Identity, aliases, family, diagnostic cards, variants, and provenance.
2. An explicitly **authored**, fixed 60 + 15 reference fixture. It is not a
   transcription of the linked pilot's tournament list or a replacement for
   the user's saved deck. Explain construction choices and omissions.
3. Mulligan priorities, six seven-card examples, play/draw context, mulligan
   depth, exact bottom cards for retained six/five, and rationale.
4. Early, middle, and late sequencing; resource use and a mana-burn warning.
5. Plans against aggro, midrange, control, combo, prison, and named exceptions.
6. At least two exact, independently applied sideboard plans tied to the fixture,
   with balanced incoming/outgoing quantities, matchup and seat, and a reason.
7. Observable-condition decision tables (priority, action, exception, rationale,
   confidence) and concrete acceptance scenarios with expected choices.
8. Card-script references and links separating sourced identity from authored
   advice. Do not extrapolate source anecdotes into matchup win rates.

## Machine-checkable Markdown conventions

The single `forge-deck` fenced block has `[Main]` and `[Sideboard]` sections and
`count Card Name` lines. Names must match checked-in Forge card definitions.
The tables headed `## Opening hands` and `## Sideboard plans` are also read by
`validate.mjs`; preserve their header names and column order. Card lists in
table cells use `count Card Name; count Other Card`, or `-` for no cards.

Opening columns: ID, Seat / opponent, Mulligans, Seven cards, Decision, Bottom,
Reason. A decision starting with `Keep` must bottom exactly the mulligan count.
Sideboard columns: Opponent / seat, In, Out, Reason. Plans start from the base
fixture, not the result of another row. The validator checks availability,
post-board size and restrictions; it cannot prove strategic soundness.

Keep six opening cases, including genuine keeps and rejections, and retained
six/five examples. H5/H6 deliberately compare different mulligan depths to
H1/H2; these are pedagogical scenarios, not six sampled independent games.
Flood and no-mana rejection examples are controls, not a substitute for the
archetype-specific keeps. A proposed engine should test additional adversarial
hands before assigning weights.

## Review discipline

Catalog IDs are stable even if titles change. Aliases are discovery hints, not
an unconditional classification rule: Robots and Reanimator encompass hybrids.
For changes, update the fixture, hands and swaps together; run the offline check
from the repository root:

```sh
node docs/Oldschool-AI/validate.mjs
```

No Node packages, network access, Forge startup, or user-data writes are needed.
The utility checks structure, local links, card identities, allowed editions,
copy limits, main/side sizes, opening-hand availability and bottoming, swap
balance, coverage, stable IDs, and decision/scenario presence. It does not run
games, prove a line legal in every board state, or measure playing strength.
