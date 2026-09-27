# Turbo Fog

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `turbo-fog` · Family: **Prison** · Rules: `swedish-fe-burn-london-v1`
Aliases: Fog prison.

## Identity and construction

Repeated combat prevention buys draw steps for a finite mill finish. Drawing more Fogs is an aspiration, not a proof that combat is locked forever.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/2018-5-11/turbo-fog) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

The GW fixture uses Fog, Holy Day and Festival with Mine/Sylvan and Millstone. It omits the black Darkness splash and includes main-deck red prevention because Fog does not stop burn.

```forge-deck
[Main]
4 Fog
4 Holy Day
4 Festival
4 Howling Mine
3 Millstone
3 Sylvan Library
2 Moat
2 Circle of Protection: Red
4 Swords to Plowshares
3 Disenchant
1 Mox Pearl
1 Mox Emerald
1 Black Lotus
1 Sol Ring
1 Regrowth
1 Balance
4 Savannah
4 City of Brass
1 Strip Mine
5 Forest
7 Plains
[Sideboard]
3 Divine Offering
2 Dust to Dust
1 Disenchant
2 Circle of Protection: Red
2 Serra Angel
2 Armageddon
1 Ivory Tower
2 Tormod's Crypt
```

## Mulligan priorities

Keep mana, a sustainable draw plan and immediate survival. All Fogs without card flow eventually run out; all Mines without defense accelerate the opponent's lethal turn.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 1 Savannah;1 Forest;1 Plains;1 Fog;1 Holy Day;1 Howling Mine;1 Millstone | Keep | - | Draw, prevention and a finish are present with stable colors. |
| H2 | Draw / unknown | 0 | 1 Savannah;1 Forest;1 Plains;1 Holy Day;1 Festival;1 Sylvan Library;1 Swords to Plowshares | Keep | - | Immediate defense supports development into selection. |
| H3 | Play or draw / unknown | 0 | 4 Fog; 3 Holy Day | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 Savannah; 3 City of Brass | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 1 Savannah;1 Forest;1 Plains;1 Fog;1 Holy Day;1 Howling Mine;1 Millstone | Keep six | 1 Millstone | Find the finisher after the survival engine is operating. |
| H6 | Draw / unknown | 2 | 1 Savannah;1 Forest;1 Plains;1 Holy Day;1 Festival;1 Sylvan Library;1 Swords to Plowshares | Keep five | 1 Festival;1 Plains | At five retain two sources, Library and two flexible defensive cards. |

## Sequencing and resources

- **Early:** Develop draw without dying to the first attack. Festival must be cast during the opponent's upkeep; it is not an interchangeable combat-step Fog.
- **Middle:** Preserve prevention for damage that matters and track how many future turns are covered. Countermagic or discard can break a one-Fog-per-turn plan.
- **Late:** Mill toward an actual failed opposing draw while protecting your own library. An empty library alone is not already a loss until a required draw fails.
- **Mana burn:** Holding W/G for prevention matters more than tapping all sources early. Circles cannot prevent mana-burn life loss, and beginning-phase mana does not carry into main.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Prevent the attack that would create a losing race, but identify burn or activated damage outside combat. |
| Midrange | Single large attackers consume a Fog just like a swarm; removal can reduce long-term prevention demand. |
| Control | Many Fogs are dead cards; transform toward pressure and answer draw engines rather than extending a losing resource game. |
| Combo | Combat-only prevention is mostly irrelevant to noncombat combo; use specific permanent answers. |
| Prison | Mines and mill can function through some attack restrictions, but untap taxes can cut off your mana and prevention chain. |

### Named exceptions and common mistakes

- Sligh: Fog does not stop Bolt, and Moat does not stop flying creatures or noncombat damage.
- The Deck: repeated Mine draws may give them more answers than you gain Fogs; card flow must be evaluated for both sides.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| Sligh / draw | 2 Circle of Protection: Red;1 Ivory Tower | 3 Festival | Replace timing-constrained attack denial with burn-relevant defenses. |
| The Deck / play | 2 Serra Angel;2 Armageddon;1 Disenchant | 4 Festival;1 Fog | Add a real pressure plan while reducing combat-only dead cards. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / turbo-fog-D1 | Festival is in hand after opponent's upkeep has ended | Do not propose casting it this turn | Use a different legal prevention effect if available | Its timing window is narrower than Fog (R) |
| 90 / turbo-fog-D2 | An attack deals no meaningful damage and future Fogs are scarce | Conserve prevention | Small damage can still matter against known burn | Spend finite defense against relevant clocks (H) |
| 80 / turbo-fog-D3 | Opponent's library is empty but no draw has failed | Continue to ensure the next required draw fails | Replacement effects or reshuffles may change the result | Empty library is not an immediate loss (R) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Opponent has declared attackers and Festival is your only prevention card. Its upkeep-only window has passed; the policy must not cast it now.
- **S2** You mill the opponent's last two cards during their main phase. They are not yet lost solely from an empty library; protect the line through their next required draw.

## Evidence and implementation boundary

- [Wak-Wak: Turbo Fog](https://www.wak-wak.se/9394decks/2018-5-11/turbo-fog): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Fog](../../forge-gui/res/cardsfolder/f/fog.txt), [Holy Day](../../forge-gui/res/cardsfolder/h/holy_day.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
