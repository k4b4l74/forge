# Leprechaun Ward

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `leprechaun-ward` · Family: **Control** · Rules: `swedish-fe-burn-london-v1`
Aliases: Aisling control, Green Ward.

## Identity and construction

Aisling Leprechaun or Lifelace makes relevant opposing creatures green, then protection and Circle of Protection: Green change combat. The package is narrow and sequencing-sensitive.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/leprechaun-ward) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

The GW fixture uses Force of Nature as a finish and CoP: Green as a possible answer to its upkeep damage. Green Ward protects a creature; it does not protect its controller.

```forge-deck
[Main]
4 Aisling Leprechaun
4 Green Ward
4 Lifelace
4 Circle of Protection: Green
3 Force of Nature
4 Swords to Plowshares
3 Disenchant
4 Birds of Paradise
2 Sylvan Library
1 Regrowth
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
6 Plains
[Sideboard]
3 Divine Offering
2 Dust to Dust
1 Disenchant
2 Circle of Protection: Red
2 Spirit Link
3 Whirling Dervish
2 Tormod's Crypt
```

## Mulligan priorities

Keep usable mana and independent interaction, not only Wards and Lifelaces. The combo cards need an object and time; they are poor substitutes for a board or Swords.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 1 Savannah;1 Forest;1 Plains;1 Aisling Leprechaun;1 Green Ward;1 Swords to Plowshares;1 Circle of Protection: Green | Keep | - | A creature, protection plan and independent removal are available. |
| H2 | Draw / unknown | 0 | 1 Savannah;1 Forest;1 Plains;1 Birds of Paradise;1 Swords to Plowshares;1 Circle of Protection: Green;1 Force of Nature | Keep | - | Fixing and removal can reach the large threat with a possible damage-prevention backup. |
| H3 | Play or draw / unknown | 0 | 4 Aisling Leprechaun; 3 Green Ward | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 Savannah; 3 City of Brass | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 1 Savannah;1 Forest;1 Plains;1 Aisling Leprechaun;1 Green Ward;1 Swords to Plowshares;1 Circle of Protection: Green | Keep six | 1 Green Ward | Preserve the broader survival tools before a creature-specific enhancement. |
| H6 | Draw / unknown | 2 | 1 Savannah;1 Forest;1 Plains;1 Birds of Paradise;1 Swords to Plowshares;1 Circle of Protection: Green;1 Force of Nature | Keep five | 1 Force of Nature;1 Circle of Protection: Green | At five, keep sources, acceleration and an unconditional creature answer. |

## Sequencing and resources

- **Early:** Use removal when the color-change engine would take too many turns. Ward must be cast before depending on the Leprechaun surviving combat.
- **Middle:** Identify the damage source's color at the relevant moment; converting one creature does not stop other colors or non-damage life loss.
- **Late:** Force is a real clock, but budget upkeep or prevention. A Circle activation prevents a specified source's next damage event, not everything green forever.
- **Mana burn:** CoP prevents damage, not mana-burn life loss. Do not overproduce green mana expecting the Circle to save you.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Try to stabilize specific combat lanes while preserving answers for burn or evasive threats. |
| Midrange | Protection can invalidate one large creature, but the opponent can attack elsewhere or remove the support enchantment. |
| Control | Narrow combat cards are weak; transform toward independent pressure and keep Disenchant. |
| Combo | Color-changing creatures does little against noncombat engines; find the actual permanent answer. |
| Prison | Many locks do not deal damage; maintain mana for removal rather than relying on Circle. |

### Named exceptions and common mistakes

- Green Ramp: CoP: Green naturally applies to Force and other green threats, but each source/event still requires appropriate activation.
- UR Burn: turning a creature green does not recolor Bolt in the opponent's hand; CoP: Green is not a red-burn shield.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| UR Burn / draw | 2 Circle of Protection: Red;2 Spirit Link | 4 Lifelace | Replace slow color-changing spells with relevant defensive tools. |
| The Deck / play | 1 Disenchant;3 Whirling Dervish | 4 Lifelace | Gain a more independent clock and another lock answer rather than dead conversion spells. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / leprechaun-ward-D1 | CoP: Green is considered against a damage source | Check source color and reserve its generic activation cost | Life loss and unrelated later sources are not covered | Prevention is source/event-specific (R) |
| 90 / leprechaun-ward-D2 | Ward is your only plan to keep Leprechaun alive | Ensure the enchantment is established before risky combat | Opposing non-green damage or removal can still defeat it | Do not assume the combo exists before it is assembled (H) |
| 80 / leprechaun-ward-D3 | Force upkeep cannot be paid in green | Consider a legal CoP prevention activation before damage | Requires an active Circle and available generic mana | Preventing damage differs from paying upkeep (R) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Force of Nature's upkeep trigger is pending; you will not pay GGGG, but control CoP: Green and one usable mana. Activate the Circle naming Force before its damage event to prevent that event.
- **S2** You have CoP: Green and will lose two life to mana burn. The Circle cannot prevent that loss.

## Evidence and implementation boundary

- [Wak-Wak: Leprechaun Ward](https://www.wak-wak.se/9394decks/leprechaun-ward): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Aisling Leprechaun](../../forge-gui/res/cardsfolder/a/aisling_leprechaun.txt), [Green Ward](../../forge-gui/res/cardsfolder/g/green_ward.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
