# The Beast

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `the-beast` · Family: **Control** · Rules: `swedish-fe-burn-london-v1`
Aliases: Guardian Beast control.

## Identity and construction

Guardian Beast protects untapped-controller noncreature artifacts from certain interactions while a toolbox controls the board. Beast is not a blanket shield for all permanents.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/the-beast-1) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

A UB Transmute shell supplies Disk, Orb, draw and utility artifacts. The fixture avoids The Abyss because it can remove Beast and conflicts with a simplistic protection plan.

```forge-deck
[Main]
3 Guardian Beast
4 Transmute Artifact
3 Counterspell
3 Copy Artifact
2 Jayemdae Tome
2 Nevinyrral's Disk
1 Chaos Orb
1 Icy Manipulator
1 Disrupting Scepter
1 Mirror Universe
1 Su-Chi
1 Triskelion
3 Mana Vault
1 Demonic Tutor
1 Mind Twist
1 Ancestral Recall
1 Time Walk
1 Sol Ring
1 Mox Sapphire
1 Mox Jet
1 Black Lotus
1 Braingeyser
4 Underground Sea
4 City of Brass
4 Mishra's Factory
1 Strip Mine
6 Swamp
6 Island
[Sideboard]
3 Blue Elemental Blast
3 Gloom
2 Hurkyl's Recall
2 Control Magic
2 Tormod's Crypt
3 Terror
```

## Mulligan priorities

Keep a functional control/development line without needing Beast immediately. Beast plus only expensive artifacts is a slow hand; it is a four-mana creature vulnerable to ordinary creature removal.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 1 Underground Sea;1 Island;1 Swamp;1 Mana Vault;1 Guardian Beast;1 Counterspell;1 Nevinyrral's Disk | Keep | - | Acceleration can establish the protection/reset relationship with real colored mana. |
| H2 | Draw / unknown | 0 | 1 Underground Sea;2 Island;1 Counterspell;1 Mana Vault;1 Su-Chi;1 Transmute Artifact | Keep | - | A robot line can function before Beast appears. |
| H3 | Play or draw / unknown | 0 | 3 Guardian Beast; 4 Transmute Artifact | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 Underground Sea; 3 City of Brass | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 1 Underground Sea;1 Island;1 Swamp;1 Mana Vault;1 Guardian Beast;1 Counterspell;1 Nevinyrral's Disk | Keep six | 1 Nevinyrral's Disk | Retain interaction and development rather than a second expensive dependency. |
| H6 | Draw / unknown | 2 | 1 Underground Sea;2 Island;1 Counterspell;1 Mana Vault;1 Su-Chi;1 Transmute Artifact | Keep five | 1 Island;1 Transmute Artifact | Two sources, Vault, a robot and permission remain coherent at five. |

## Sequencing and resources

- **Early:** Establish mana and interaction before investing in Beast; keep it untapped when its protection matters.
- **Middle:** Beast protects noncreature artifacts while untapped. Attacking with it turns protection off, and animating an artifact may remove its protected status.
- **Late:** Disk can leave protected artifacts standing for that destruction event, but Beast itself can die. Do not project indefinite repeated resets without rebuilding protection.
- **Mana burn:** Mana Vault and Su-Chi need real expenditure plans. Beast prevents some destruction, not life loss from excess mana or upkeep damage.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Use a reset only if you survive the setup; Beast's body is not a universal blocker. |
| Midrange | Preserve the protection source from creature removal before relying on artifact superiority. |
| Control | Protect an engine and draw resources; don't expose Beast merely for two combat damage. |
| Combo | The toolbox needs specific relevant targets; indestructibility does not stop an opposing combo. |
| Prison | Protection cannot make a taxed or frozen ability usable; separate surviving permanents from usable resources. |

### Named exceptions and common mistakes

- Chaos Orb: Swedish single-permanent selection and Forge's random multi-hit implementation differ; never advertise a guaranteed reusable Orb lock from this documentation.
- The Deck: Swords exiles Beast; its artifact protection does not protect itself or stop artifact exile/bounce.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| UR Burn / draw | 3 Blue Elemental Blast;2 Control Magic | 1 Mirror Universe;1 Disrupting Scepter;2 Transmute Artifact;1 Guardian Beast | Reduce slow dependencies while improving life-preserving interaction. |
| Artifact Aggro / play | 2 Control Magic;2 Hurkyl's Recall | 1 Mirror Universe;1 Disrupting Scepter;1 Braingeyser;1 Jayemdae Tome | Favor board tempo over expensive value against fast robots. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / the-beast-D1 | Beast is untapped and artifact protection matters | Keep it untapped instead of making a low-value attack | A verified winning attack can override | Protection depends on Beast remaining untapped (R) |
| 90 / the-beast-D2 | A protected artifact becomes a creature | Recheck whether Beast still grants it protection | The effect covers noncreature artifacts only | Animation can remove the shield (R) |
| 80 / the-beast-D3 | Disk will destroy Beast and other permanents | Evaluate the current event and the resulting unprotected board separately | Other effects may preserve Beast | One asymmetric reset is not an infinite lock (H) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** Untapped Beast and an unanimated Disk are on your battlefield. An ordinary destroy effect aimed at Disk can be stopped by its granted indestructibility; Swords aimed at Beast is not stopped.
- **S2** You attack with Beast, tapping it. Do not continue granting its untapped-dependent protection to your artifacts during the opponent's response window.

## Evidence and implementation boundary

- [Wak-Wak: The Beast](https://www.wak-wak.se/9394decks/the-beast-1): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Guardian Beast](../../forge-gui/res/cardsfolder/g/guardian_beast.txt), [Transmute Artifact](../../forge-gui/res/cardsfolder/t/transmute_artifact.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
