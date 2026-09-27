# Stasis

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `stasis` · Family: **Prison** · Rules: `swedish-fe-burn-london-v1`
Aliases: Turbo Stasis, Stasis Kismet.

## Identity and construction

Stasis skips untap steps; a sustainable upkeep and release/recast plan creates asymmetry. Kismet can keep new opposing resources tapped, but the setup is not automatically permanent.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/stasis) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

The UW fixture uses Boomerang, Reset, Mine and a small Time Elemental package. It retains white answers; bounce and reset have distinct timing and resource demands.

```forge-deck
[Main]
4 Stasis
4 Howling Mine
4 Boomerang
3 Reset
3 Kismet
3 Counterspell
3 Swords to Plowshares
2 Disenchant
2 Time Elemental
1 Ancestral Recall
1 Time Walk
1 Mana Drain
1 Braingeyser
1 Mox Pearl
1 Mox Sapphire
1 Black Lotus
1 Sol Ring
1 Chaos Orb
4 Tundra
3 City of Brass
4 Plains
12 Island
[Sideboard]
3 Blue Elemental Blast
2 Control Magic
2 Disenchant
2 Divine Offering
2 Circle of Protection: Red
2 Serra Angel
2 Tormod's Crypt
```

## Mulligan priorities

Keep blue sources, defense and a sustainable path, not Stasis with no next-upkeep U. Reset is not an ordinary own-turn untap spell and cannot fix every mana-short opening.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 2 Island;1 Tundra;1 Stasis;1 Howling Mine;1 Boomerang;1 Swords to Plowshares | Keep | - | Mana, survival and a potential release plan accompany the lock. |
| H2 | Draw / unknown | 0 | 2 Island;1 Tundra;1 Counterspell;1 Swords to Plowshares;1 Howling Mine;1 Kismet | Keep | - | Interaction and draw can develop toward a supported lock. |
| H3 | Play or draw / unknown | 0 | 4 Stasis; 3 Howling Mine | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 Tundra; 3 City of Brass | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 2 Island;1 Tundra;1 Stasis;1 Howling Mine;1 Boomerang;1 Swords to Plowshares | Keep six | 1 Stasis | At six, retain a functional setup before the maintenance obligation. |
| H6 | Draw / unknown | 2 | 2 Island;1 Tundra;1 Counterspell;1 Swords to Plowshares;1 Howling Mine;1 Kismet | Keep five | 1 Kismet;1 Island | Two sources plus draw and interaction are better than an unsupported four-drop at five. |

## Sequencing and resources

- **Early:** Stabilize and count future upkeep payments before casting Stasis. A one-turn delay can be worthwhile, but don't label it permanent.
- **Middle:** Bounce your Stasis at a legal opposing end-step window when that enables your untap, then budget for recasting and maintaining it. Reset can be cast only on an opponent's turn after their upkeep.
- **Late:** Kismet affects newly entering opposing artifacts, creatures and lands; it does not retroactively tap everything. Track libraries and a finite finish rather than waiting for a concession.
- **Mana burn:** Reserve U for Stasis instead of spending it on optional actions. Mana generated in the opponent's turn will not survive to pay your next-turn upkeep under this contract.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Tap/attack commitments can create a lock window, but an existing untapped attacker still matters until it is used or answered. |
| Midrange | Large creatures cannot untap normally, but vigilance, abilities and alternative untaps change the picture. |
| Control | Counters and end-step interaction contest the release turn; protect actual upkeep and recast mana. |
| Combo | Ability-based engines may function through skipped untaps if they have their own untapping; identify the real constraint. |
| Prison | Do not confuse Stasis with Winter Orb; tapping Orb does not restore an untap step that Stasis removes. |

### Named exceptions and common mistakes

- Twiddlevault: Twiddle can untap a permanent despite skipped untap steps, so Stasis alone may not stop the turn engine.
- UW Skies: Serra's vigilance means attacking does not tap it; a Stasis plan that waits for every attacker to tap can fail.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| UR Burn / draw | 3 Blue Elemental Blast;2 Circle of Protection: Red | 2 Time Elemental;2 Kismet;1 Braingeyser | Reduce slow setup and defend life while preserving the main bounce plan. |
| The Deck / play | 2 Serra Angel;2 Disenchant | 3 Swords to Plowshares;1 Kismet | Against low-creature control, add an independent vigilant clock and more relevant answers. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / stasis-D1 | Stasis is castable but next upkeep U is unavailable | Evaluate it as a temporary delay, not an established lock | A temporary delay can still prevent lethal | Maintenance must be funded (H) |
| 90 / stasis-D2 | Reset is proposed on your turn or during opponent's upkeep | Reject the illegal timing | Wait until their turn after upkeep if still useful | Card text restricts casting (R) |
| 80 / stasis-D3 | You plan to bounce and recast Stasis | Budget bounce, next main-phase recast and next upkeep separately | Opponent interaction can interrupt the sequence | Untapping once does not prove indefinite sustainability (H) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** It is your main phase and Reset is in hand. You cannot cast it to untap lands now.
- **S2** Opponent controls untapped Serra Angel and Stasis is active. Serra can attack without tapping; do not predict that one attack strands it tapped.

## Evidence and implementation boundary

- [Wak-Wak: Stasis](https://www.wak-wak.se/9394decks/stasis): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Stasis](../../forge-gui/res/cardsfolder/s/stasis.txt), [Howling Mine](../../forge-gui/res/cardsfolder/h/howling_mine.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
