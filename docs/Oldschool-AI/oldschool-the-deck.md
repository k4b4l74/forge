# The Deck

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `the-deck` · Family: **Control** · Rules: `swedish-fe-burn-london-v1`
Aliases: Five-color control, UWx Control, The deck.

## Identity and construction

Flexible answers and incremental card advantage support a small number of finishers. It wins by making opposing threats manageable, not by countering every spell or hoarding every card.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/the-deck) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

This authored WUB fixture fixes a conventional answer/Tome/Factory core with two Serra Angels. It is not the imported Di Fazio list: no red Fireball or green Regrowth splash is assumed.

```forge-deck
[Main]
3 Jayemdae Tome
4 Counterspell
4 Swords to Plowshares
4 Disenchant
2 Wrath of God
2 Serra Angel
1 The Abyss
2 Fellwar Stone
1 Ancestral Recall
1 Mana Drain
1 Demonic Tutor
1 Mind Twist
1 Balance
1 Time Walk
1 Braingeyser
1 Mox Pearl
1 Mox Sapphire
1 Mox Jet
1 Black Lotus
1 Sol Ring
1 Chaos Orb
4 Tundra
3 Underground Sea
2 Scrubland
3 City of Brass
4 Mishra's Factory
1 Strip Mine
1 Library of Alexandria
2 Plains
5 Island
[Sideboard]
3 Blue Elemental Blast
2 Circle of Protection: Red
2 Divine Offering
2 Dust to Dust
2 Control Magic
2 Tormod's Crypt
1 Amnesia
1 Disrupting Scepter
```

## Mulligan priorities

Keep stable colored sources and relevant early interaction. Library is a reason to consider a slow hand only if it can actually stay active and you survive; it is not a universal trump over Vise or land denial.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 1 Tundra;1 Island;1 Underground Sea;1 Swords to Plowshares;1 Disenchant;1 Counterspell;1 Jayemdae Tome | Keep | - | Three sources cover early answers and a later draw engine. |
| H2 | Draw / unknown | 0 | 1 Tundra;1 Island;1 Plains;1 Swords to Plowshares;1 Counterspell;1 Serra Angel;1 Wrath of God | Keep | - | Early answers bridge to a reset or finisher. |
| H3 | Play or draw / unknown | 0 | 3 Jayemdae Tome; 4 Counterspell | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 Tundra; 3 Underground Sea | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 1 Tundra;1 Island;1 Underground Sea;1 Swords to Plowshares;1 Disenchant;1 Counterspell;1 Jayemdae Tome | Keep six | 1 Jayemdae Tome | Survive and hit mana before insisting on an expensive engine. |
| H6 | Draw / unknown | 2 | 1 Tundra;1 Island;1 Plains;1 Swords to Plowshares;1 Counterspell;1 Serra Angel;1 Wrath of God | Keep five | 1 Serra Angel;1 Wrath of God | At five, keep reliable mana and interaction rather than both costly cards. |

## Sequencing and resources

- **Early:** Identify the opposing plan from revealed cards and answer what actually threatens resources or life. Use colored development to keep both W and UU options.
- **Middle:** Activate Tome when the mana is genuinely spare; the correct end-step draw can still be wrong if it leaves you unable to stop lethal.
- **Late:** Convert control into a finite clock with Serra or Factory. Avoid timing out behind a nominal soft lock; a win must be reached in the engine.
- **Mana burn:** Before Mana Drain, predict which generic costs can absorb its delayed mana. Countering a harmless expensive spell can cost life if no useful sink exists.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Trade efficiently and reach a sweeper or dominant blocker before pursuing maximum cards. |
| Midrange | Swords, counters and sweepers answer different threats; do not spend the only exile answer on a trivial target. |
| Control | Protect mana/card engines, pressure with Factory when safe, and preserve a real win condition. |
| Combo | Hold the correct interaction for engine or payoff; creature removal can be dead. |
| Prison | Map each lock's escape window and reserve the needed colors rather than drawing without a plan. |

### Named exceptions and common mistakes

- Atog: remove the creature before its artifact sacrifices become a real lethal threat; avoid unnecessary artifact destruction that simply becomes pump.
- Twiddlevault: normal creature-control heuristics are weak; Disenchant and permission on the turn engine matter much more than Wrath.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| UR Burn / draw | 3 Blue Elemental Blast;2 Circle of Protection: Red | 2 Wrath of God;1 The Abyss;1 Braingeyser;1 Jayemdae Tome | Reduce slow cards and protect life while retaining sufficient draw. |
| Twiddlevault / play | 2 Divine Offering;2 Dust to Dust;1 Amnesia;1 Disrupting Scepter | 4 Swords to Plowshares;2 Wrath of God | For a confirmed creatureless engine, replace dead removal with artifact and hand interaction. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / the-deck-D1 | Tome can draw but leaves no answer to visible or known lethal | Hold mana instead of activating | Drawing the only possible out may be correct when holding cannot help | Card advantage is subordinate to survival (H) |
| 90 / the-deck-D2 | A low-impact spell is on the stack | Preserve the unique answer for the opponent's actual engine | Counter if that spell creates a demonstrable resource bottleneck | Do not counter everything (H) |
| 80 / the-deck-D3 | Control is established with a safe Factory or Serra clock | Start converting advantage into a win | Keep necessary defense and interaction | Avoid purposeless prolongation (H) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Opponent has a known lethal instant sequence; activating Tome would consume your only Counterspell mana. Prefer retaining the counter unless drawing is the only route to survival.
- **S2** Opponent's confirmed creatureless Vault deck is presented for sideboarding. Remove creature-only sweepers before cutting Disenchant or counters.

## Evidence and implementation boundary

- [Wak-Wak: The Deck](https://www.wak-wak.se/9394decks/the-deck): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Jayemdae Tome](../../forge-gui/res/cardsfolder/j/jayemdae_tome.txt), [Counterspell](../../forge-gui/res/cardsfolder/c/counterspell.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
