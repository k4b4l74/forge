# Erhnamgeddon

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `erhnamgeddon` · Family: **Midrange** · Rules: `swedish-fe-burn-london-v1`
Aliases: Erhnam Armageddon, GW Geddon.

## Identity and construction

Develop a meaningful creature advantage with nonland mana, then deny land recovery through Armageddon. Casting the namesake reset while behind is not the plan.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/erhnamgeddon) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

The GW fixture uses Birds and Elves with Erhnam/Serra, white removal and three Armageddons. It avoids the additional colors in the imported powered multicolor variant.

```forge-deck
[Main]
4 Birds of Paradise
3 Llanowar Elves
4 Erhnam Djinn
3 Serra Angel
3 Armageddon
4 Swords to Plowshares
4 Disenchant
2 Sylvan Library
2 Giant Growth
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
5 Plains
8 Forest
[Sideboard]
3 Spirit Link
2 Circle of Protection: Red
3 Divine Offering
2 Dust to Dust
2 Whirling Dervish
1 Armageddon
2 Tormod's Crypt
```

## Mulligan priorities

Keep mana development and a threat, not three land resets with no board. A hand that can cast Armageddon but cannot establish a superior clock is functionally missing its payoff.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 1 Savannah;1 Forest;1 Plains;1 Birds of Paradise;1 Erhnam Djinn;1 Armageddon;1 Swords to Plowshares | Keep | - | Acceleration into a threat makes the reset a potential advantage. |
| H2 | Draw / unknown | 0 | 1 Savannah;1 Forest;1 Plains;1 Llanowar Elves;1 Erhnam Djinn;1 Disenchant;1 Serra Angel | Keep | - | Stable mana and a four-drop provide a midrange plan without forcing Geddon. |
| H3 | Play or draw / unknown | 0 | 4 Birds of Paradise; 3 Llanowar Elves | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 Savannah; 3 City of Brass | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 1 Savannah;1 Forest;1 Plains;1 Birds of Paradise;1 Erhnam Djinn;1 Armageddon;1 Swords to Plowshares | Keep six | 1 Armageddon | Keep a functional creature plan before the conditional finisher. |
| H6 | Draw / unknown | 2 | 1 Savannah;1 Forest;1 Plains;1 Llanowar Elves;1 Erhnam Djinn;1 Disenchant;1 Serra Angel | Keep five | 1 Serra Angel;1 Disenchant | At five retain mana, acceleration and the reachable threat. |

## Sequencing and resources

- **Early:** Build reusable nonland mana and a threatening body while protecting critical colors.
- **Middle:** Compare post-Geddon boards, not pre-Geddon mana totals. A robot deck with rocks may recover better than you despite having fewer lands.
- **Late:** Once ahead, close the game rather than draw more setup. Hold a land when doing so does not delay the immediate plan and improves post-reset recovery.
- **Mana burn:** Floating land mana before Geddon is useful only with a same-main-phase follow-up. Plan the exact white and green costs before destroying sources.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Stabilize first; destroying lands does not remove attackers already killing you. |
| Midrange | Assess which side retains mana creatures, artifacts and the superior clock after the reset. |
| Control | Pressure plus Geddon can punish expensive answers, but cast it through permission only with a reason. |
| Combo | Geddon is weak against some artifact engines; Disenchant may matter more than land denial. |
| Prison | Use broad white answers and surviving mana creatures; reset lands only if it improves your escape. |

### Named exceptions and common mistakes

- Robots: Armageddon can leave their Vaults and giant creatures intact while disabling your removal.
- The Deck: an active Moat blanks Erhnam after a reset; ensure Serra or Disenchant supplies a real winning line.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| Sligh / draw | 3 Spirit Link;2 Circle of Protection: Red | 3 Armageddon;2 Sylvan Library | Remove risky reset and life-payment cards until the race is stabilized. |
| Artifact Aggro / play | 3 Divine Offering;2 Dust to Dust | 3 Armageddon;2 Giant Growth | Fight the mana and threats that survive land destruction. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / erhnamgeddon-D1 | Armageddon is available | Evaluate post-resolution clocks and surviving mana | Do not cast solely because it is the named strategy | A reset must create asymmetry (H) |
| 90 / erhnamgeddon-D2 | A ready Erhnam is stopped by Moat | Keep mana for Disenchant or find a flying clock before resetting lands | A separate verified win can override | Land denial without a clock is not a lock (H) |
| 80 / erhnamgeddon-D3 | Opponent's developed mana is mostly rocks | Prefer artifact interaction to land destruction | A particular colored land may still matter | Target the actual resource base (H) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Opponent has Su-Chi and two mana rocks; you have only Birds and lands. Armageddon does not create a favorable board by itself.
- **S2** You have Erhnam, opponent has Moat, and your only answer is Disenchant. Do not destroy the white sources you need before ensuring the Moat can be removed.

## Evidence and implementation boundary

- [Wak-Wak: Erhnamgeddon](https://www.wak-wak.se/9394decks/erhnamgeddon): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Birds of Paradise](../../forge-gui/res/cardsfolder/b/birds_of_paradise.txt), [Llanowar Elves](../../forge-gui/res/cardsfolder/l/llanowar_elves.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
