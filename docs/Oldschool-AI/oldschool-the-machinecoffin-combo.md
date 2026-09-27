# The Machine/Coffin Combo

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `the-machinecoffin-combo` · Family: **Combo** · Rules: `swedish-fe-burn-london-v1`
Aliases: Coffin Combo, The Machine, Tawnos's Coffin.

## Identity and construction

Coffin temporarily exiles a creature while recording counters, enabling Triskelion or Tetravus to return with both recorded and new entry counters. Repetition still requires mana and untaps.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/the-machinecoffin-combo) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

The UW artifact fixture uses Coffin, robots and Transmute with white answers. Hell's Caretaker is a separate sacrifice/upkeep variant, not an assumed extra engine in these 60 cards.

```forge-deck
[Main]
3 Tawnos's Coffin
4 Triskelion
4 Tetravus
3 Transmute Artifact
3 Copy Artifact
4 Mana Vault
4 Swords to Plowshares
3 Disenchant
2 Counterspell
1 Ancestral Recall
1 Time Walk
1 Sol Ring
1 Mox Sapphire
1 Mox Pearl
1 Black Lotus
1 Chaos Orb
4 Tundra
4 City of Brass
3 Mishra's Factory
1 Strip Mine
5 Plains
6 Island
[Sideboard]
3 Blue Elemental Blast
1 Disenchant
3 Divine Offering
2 Dust to Dust
2 Control Magic
2 Tormod's Crypt
2 Jayemdae Tome
```

## Mulligan priorities

Require usable mana and a real robot or survival tool. Coffin without a creature and three activation mana is a slow artifact, not an assembled combo.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 1 Tundra;1 Island;1 Plains;1 Mana Vault;1 Triskelion;1 Swords to Plowshares;1 Tawnos's Coffin | Keep | - | Acceleration supports an impactful robot while removal bridges setup. |
| H2 | Draw / unknown | 0 | 1 Tundra;1 Island;1 Plains;1 Mana Vault;1 Tetravus;1 Disenchant;1 Counterspell | Keep | - | Stable colors support either a robot line or interaction. |
| H3 | Play or draw / unknown | 0 | 3 Tawnos's Coffin; 4 Triskelion | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 Tundra; 3 City of Brass | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 1 Tundra;1 Island;1 Plains;1 Mana Vault;1 Triskelion;1 Swords to Plowshares;1 Tawnos's Coffin | Keep six | 1 Tawnos's Coffin | The robot and removal work independently; the amplifier can come later. |
| H6 | Draw / unknown | 2 | 1 Tundra;1 Island;1 Plains;1 Mana Vault;1 Tetravus;1 Disenchant;1 Counterspell | Keep five | 1 Tetravus;1 Disenchant | At five preserve mana, acceleration and permission while drawing toward a payoff. |

## Sequencing and resources

- **Early:** Use robots as real threats rather than waiting forever for the full engine. Coffin can also temporarily remove an opposing creature.
- **Middle:** Choose whether counters should be spent before exile or preserved to increase the returning total. The answer depends on immediate lethal, blockers and expected removal.
- **Late:** Keep Coffin tapped to retain an exiled opposing threat, or untap it to return your own payload. The returned creature is tapped, so do not promise an immediate normal attack.
- **Mana burn:** Vault's three colorless can pay the Coffin activation, but a main-phase activation does not bank mana for the next turn's return. Plan costs per activation.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Triskelion's immediate removal may matter more than preserving every counter for growth. |
| Midrange | Use Coffin as control or value according to the board; exiling your only blocker can be a mistake. |
| Control | Protect the artifact engine and keep independent threats after Disenchant or sweepers. |
| Combo | A slow counter-growing engine may lose the race; interact with the actual opposing combo. |
| Prison | Coffin needs tapping, untapping and mana; surviving artifacts under a lock are not necessarily operational. |

### Named exceptions and common mistakes

- Robots / Henrik Storm: the Coffin package is one strategic axis in a larger control list; do not classify every Su-Chi deck as this combo.
- Reanimator: exile/return under Coffin and death/reanimation are different zone changes and preserve different information.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| UR Burn / draw | 3 Blue Elemental Blast;2 Jayemdae Tome | 2 Tawnos's Coffin;2 Transmute Artifact;1 Tetravus | Reduce slow multi-piece commitments and add protection/recovery. |
| Artifact Aggro / play | 3 Divine Offering;2 Dust to Dust | 3 Transmute Artifact;2 Tawnos's Coffin | Trade setup time for direct interaction against a faster robot board. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / the-machinecoffin-combo-D1 | Coffin can exile your Triskelion | Compare immediate counter damage with preserving counters for return | Do not remove your only required blocker before surviving combat | Stored counters and immediate value compete (H) |
| 90 / the-machinecoffin-combo-D2 | An opposing creature is exiled by your Coffin | Keep Coffin tapped if continued exile is more valuable | Untap for another urgent use only after evaluating the return | Untapping returns the remembered card (R) |
| 80 / the-machinecoffin-combo-D3 | A proposed loop requires Coffin to untap repeatedly | Budget each untap and activation | No free repeated cycle is implied by the card names | Bound the engine's action sequence (H) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Triskelion has two +1/+1 counters when Coffin exiles it. On an ordinary return, retain those noted counters and add its three entry counters: five total, absent other effects.
- **S2** Coffin holds an opposing creature. Choosing to untap Coffin causes its return process; do not simultaneously score the creature as permanently removed.

## Evidence and implementation boundary

- [Wak-Wak: The Machine/Coffin Combo](https://www.wak-wak.se/9394decks/the-machinecoffin-combo): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Tawnos's Coffin](../../forge-gui/res/cardsfolder/t/tawnoss_coffin.txt), [Triskelion](../../forge-gui/res/cardsfolder/t/triskelion.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
