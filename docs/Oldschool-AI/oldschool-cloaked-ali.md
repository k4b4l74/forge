# Cloaked Ali

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `cloaked-ali` · Family: **Combo** · Rules: `swedish-fe-burn-london-v1`
Aliases: Ali from Cairo control.

## Identity and construction

Ali from Cairo constrains lethal damage, while Spectral Cloak protects an untapped Ali from targeting. Neither card prevents every way to lose.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/cloaked-ali) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

A UR control shell surrounds Ali with counters, burn and draw. Cloak gives shroud only while the creature is untapped; Anti-Magic Aura and Jade Monolith are alternative defensive tools with their own targeting restrictions.

```forge-deck
[Main]
4 Ali from Cairo
4 Spectral Cloak
2 Anti-Magic Aura
1 Jade Monolith
4 Counterspell
4 Lightning Bolt
2 Psionic Blast
3 Jayemdae Tome
1 Ancestral Recall
1 Time Walk
1 Mana Drain
1 Braingeyser
1 Mox Sapphire
1 Mox Ruby
1 Black Lotus
1 Sol Ring
1 Chaos Orb
4 Volcanic Island
3 City of Brass
3 Mishra's Factory
1 Strip Mine
7 Mountain
9 Island
[Sideboard]
3 Red Elemental Blast
3 Blue Elemental Blast
3 Shatter
2 Control Magic
2 Blood Moon
2 Tormod's Crypt
```

## Mulligan priorities

Keep castable early interaction and colors; Ali costs 2RR and Cloak UU. Ali plus Cloaks on colorless sources is not a lock and not a good opening hand.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 1 Volcanic Island;1 Island;1 Mountain;1 Lightning Bolt;1 Counterspell;1 Ali from Cairo;1 Spectral Cloak | Keep | - | Both colors and early answers can bridge into protected Ali. |
| H2 | Draw / unknown | 0 | 1 Volcanic Island;1 Island;1 Mountain;1 Lightning Bolt;1 Counterspell;1 Jayemdae Tome;1 Ali from Cairo | Keep | - | A conventional control start can develop before the damage shield. |
| H3 | Play or draw / unknown | 0 | 4 Ali from Cairo; 3 Spectral Cloak | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 Volcanic Island; 3 City of Brass | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 1 Volcanic Island;1 Island;1 Mountain;1 Lightning Bolt;1 Counterspell;1 Ali from Cairo;1 Spectral Cloak | Keep six | 1 Spectral Cloak | Do not retain dependent protection instead of functional mana and interaction. |
| H6 | Draw / unknown | 2 | 1 Volcanic Island;1 Island;1 Mountain;1 Lightning Bolt;1 Counterspell;1 Jayemdae Tome;1 Ali from Cairo | Keep five | 1 Jayemdae Tome;1 Ali from Cairo | At five retain mana and survival rather than both four-drops. |

## Sequencing and resources

- **Early:** Control the early game before deploying Ali. Even after it resolves, removal in response to Cloak can expose the whole plan.
- **Middle:** Keep Cloaked Ali untapped when shroud matters; an unnecessary attack can switch that protection off.
- **Late:** Use Factory and burn to finish instead of remaining at one life indefinitely. Board-wide removal, life loss, decking and non-targeted effects still matter.
- **Mana burn:** Ali only modifies damage. At one life, one unspent mana at phase end still kills you under this contract; never treat the damage shield as permission to float excess.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Ali can blunt damage after setup, but fast decks can remove it or win before it arrives. |
| Midrange | Protect the creature from appropriate removal; do not attack it into needless danger. |
| Control | Counters, Wrath and enchantment answers defeat simplistic protection; deploy in a supported window. |
| Combo | Many engines win without ordinary damage; keep actual interaction available. |
| Prison | A protected creature does not guarantee usable mana or a way out of an untap lock. |

### Named exceptions and common mistakes

- The Deck: Wrath does not target, and a Swedish Orb choice is not a targeting action; Cloak is not a universal defense.
- Distress: Dreams and Warp Artifact deal damage, but mana burn and other direct life-loss effects bypass Ali.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| Sligh / draw | 3 Blue Elemental Blast;2 Control Magic | 2 Psionic Blast;1 Jade Monolith;2 Jayemdae Tome | Reduce self-damage and slow setup while improving red interaction. |
| The Deck / play | 3 Red Elemental Blast;2 Blood Moon | 4 Lightning Bolt;1 Jade Monolith | For creature-light control, pressure mana and blue spells instead of relying on narrow creature damage. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / cloaked-ali-D1 | Ali is cloaked and an attack would tap it | Keep it untapped when shroud is required | A verified winning attack can justify exposure | Cloak checks tapped status (R) |
| 90 / cloaked-ali-D2 | Ali is active at one life and mana will empty | Spend only through legal safe sinks or avoid generating excess | Ali does not stop the loss | Mana burn is not damage (R) |
| 80 / cloaked-ali-D3 | Opponent's answer is non-targeted | Do not count Cloak as protection | A different counter or response may work | Shroud does not stop sweepers (R) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Ali is active at one life and you end the phase with one unused mana. Under this rules contract you lose; Ali does not prevent mana-burn life loss.
- **S2** Untapped Ali is enchanted with Spectral Cloak. If you attack and tap it, shroud is no longer granted by that Aura.

## Evidence and implementation boundary

- [Wak-Wak: Cloaked Ali](https://www.wak-wak.se/9394decks/cloaked-ali): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Ali from Cairo](../../forge-gui/res/cardsfolder/a/ali_from_cairo.txt), [Spectral Cloak](../../forge-gui/res/cardsfolder/s/spectral_cloak.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
