# Old School AI playbook library

This library covers all **50 archetypes** in the [Wak-Wak catalog](https://www.wak-wak.se/9394/archetypes)
reviewed on 2026-09-26: 13 aggro, 8 midrange, 9 control, 13 combo and 7 prison.
It is a strategy specification for a future archetype-aware Forge AI, **not an
installed AI profile**. Nothing here makes the current AI read prose or changes
its strength. There are no measured matchup win rates.

An initial, separate [OS Reanimator runtime profile](OS-Reanimator-AI.md) now
implements a subset of the Reanimator guide. It does not execute the other
guides or establish that the candidate is stronger than Default.

## Start here

1. Read the [custom rules contract and glossary](Oldschool-Rules.md).
2. Find a guide below, or use the [22 imported-deck mappings](Oldschool-Imported-Decks.md).
3. Read its fixed reference list before using opening or sideboard examples.
4. Check the [engine compatibility audit and implementation roadmap](Oldschool-Implementation.md)
   before treating a simulation as evidence for this ruleset.
5. Use the [authoring contract](Oldschool-Template.md) when extending the library.

The contract is **Swedish restrictions + Fallen Empires + phase-end mana burn +
London mulligans**. It is intentionally not identical to a standard Swedish or
Eternal Central tournament format. The pinned definition is also available in
[rules.json](rules.json); Forge does not consume that file.

## What each guide contains

- Identity, variants, construction rationale and an authored 60 + 15 fixture.
- Six opening scenarios: seven-card keeps/rejections and exact London bottoms
  for retained six/five. H5/H6 revisit earlier hands at a different mulligan depth;
  these are 300 instructional scenarios, not 300 independent sampled games.
- Early/middle/late priorities, life/mana budgeting, and common mistakes.
- Plans against all five families, named exceptions and two balanced sideboard
  examples tied to the fixture: 100 independently applied swaps in total.
- Three observable-condition decision rules and two proposed regression
  scenarios per guide. These 150 rules and 100 board-state cases are not yet
  implemented Java behavior or passing gameplay tests.

Wak-Wak supports the archetype identities. The fixtures, keep decisions,
sideboard cuts and heuristic priorities are **authored hypotheses**, not lists
or instructions attributed to those pilots. Reported pilot observations are
linked separately where used; their event rules may differ. All fixtures are
documentation only and do not replace decks saved through the application.

## Catalog

The [small catalog](catalog.json) supplies stable IDs, display names, family,
aliases, source URLs and document filenames. Aliases help navigation; they are
not a runtime classifier or a promise that similarly named lists play alike.
The five families reproduce the source's organization rather than a rigid
in-game role: a deck can become the stabilizer or aggressor in different games.

### Aggro (13)

| Archetype | Stable ID | Aliases / related labels |
| --- | --- | --- |
| [White Zoo](oldschool-white-zoo.md) | `white-zoo` | GW Zoo, white-based Zoo |
| [Kobolds!](oldschool-kobolds.md) | `kobolds` | Kobold swarm |
| [Atog Smash](oldschool-atog-smash.md) | `atog-smash` | Atog, Atog Aggro |
| [Goblins](oldschool-goblins.md) | `goblins` | Mono Red Goblins |
| [Suicide Blue](oldschool-suicide-blue.md) | `suicide-blue` | Blue aggro, Mono Blue Aggro |
| [Sligh](oldschool-sligh.md) | `sligh` | Mono Red Aggro, Red Deck Wins |
| [Erhnam Burn'Em](oldschool-erhnam-burnem.md) | `erhnam-burnem` | GR Aggro, RG Aggro |
| [Green Blue Berserk](oldschool-green-blue-berserk.md) | `green-blue-berserk` | UG Berserk, Berserk aggro |
| [Black Red Aggro](oldschool-br-aggro.md) | `br-aggro` | BR Aggro, Rakdos aggro |
| [White Weenie](oldschool-white-weenie.md) | `white-weenie` | WW, Mono White Aggro |
| [Lestree Zoo](oldschool-lestree-zoo.md) | `lestree-zoo` | UGR Zoo, Arabian Aggro, Quicksilver (overlap) |
| [Artifact Aggro](oldschool-artifact-aggro.md) | `artifact-aggro` | Robots, Workshop Aggro |
| [UR Burn](oldschool-ur-burn.md) | `ur-burn` | Counterburn, UR Control, Blue Red Burn |

### Midrange (8)

| Archetype | Stable ID | Aliases / related labels |
| --- | --- | --- |
| [Machine Head](oldschool-machine-head.md) | `machine-head` | BG fatties, Black Green Midrange |
| [The Abyss](oldschool-the-abyss.md) | `the-abyss` | Abyss Robots, Artifact Abyss |
| [Green Ramp](oldschool-green-ramp.md) | `green-ramp` | Mono Green Ramp, Big Green |
| [Troll Disco](oldschool-troll-disco.md) | `troll-disco` | Troll Disk, Disco Troll, Troll Disco |
| [UW Skies](oldschool-uw-skies.md) | `uw-skies` | UW flyers, Blue White Skies |
| [Deadguy Ale](oldschool-deadguy-ale.md) | `deadguy-ale` | BW Midrange, Black White disruption |
| [Mono Black](oldschool-mono-black.md) | `mono-black` | Monoblack, Black aggro |
| [Erhnamgeddon](oldschool-erhnamgeddon.md) | `erhnamgeddon` | Erhnam Armageddon, GW Geddon |

### Control (9)

| Archetype | Stable ID | Aliases / related labels |
| --- | --- | --- |
| [Karma Tomb](oldschool-karma-tomb.md) | `karma-tomb` | Cyclopean Tomb control |
| [Lands](oldschool-lands.md) | `lands` | Fastbond Lands, Old School Lands |
| [Arboria Control](oldschool-arboria-control.md) | `arboria-control` | Arboria mill |
| [Leprechaun Ward](oldschool-leprechaun-ward.md) | `leprechaun-ward` | Aisling control, Green Ward |
| [Ponza](oldschool-ponza.md) | `ponza` | RGB land destruction, Land destruction |
| [Artifact Toolbox](oldschool-artifact-toolbox.md) | `artifact-toolbox` | Transmute toolbox |
| [Distress](oldschool-distress.md) | `distress` | Black attrition control |
| [The Deck](oldschool-the-deck.md) | `the-deck` | Five-color control, UWx Control, The deck |
| [The Beast](oldschool-the-beast.md) | `the-beast` | Guardian Beast control |

### Combo (13)

| Archetype | Stable ID | Aliases / related labels |
| --- | --- | --- |
| [Lich Mirror](oldschool-lich-mirror.md) | `lich-mirror` | Lich |
| [Cloaked Ali](oldschool-cloaked-ali.md) | `cloaked-ali` | Ali from Cairo control |
| [Fork Combo](oldschool-fork-combo.md) | `fork-combo` | Fork recursion |
| [Reanimator](oldschool-reanimator.md) | `reanimator` | Reanimation, Bazaar Reanimator |
| [TaxEdge](oldschool-tax-edge.md) | `tax-edge` | Tax Edge, Land Tax / Land's Edge |
| [MirrorBall](oldschool-mirrorball.md) | `mirrorball` | Sylvan Mirror, Mirror Ball |
| [Eureka!](oldschool-eureka.md) | `eureka` | Eureka fatties |
| [Enchantress](oldschool-enchantress.md) | `enchantress` | Verduran Enchantress |
| [The Machine/Coffin Combo](oldschool-the-machinecoffin-combo.md) | `the-machinecoffin-combo` | Coffin Combo, The Machine, Tawnos's Coffin |
| [Candleflare](oldschool-candleflare.md) | `candleflare` | CandleFlare, Big Red mana |
| [Twiddlevault](oldschool-twiddlevault.md) | `twiddlevault` | Twiddle Vault, Time Vault combo |
| [Power Monolith](oldschool-power-monolith.md) | `power-monolith` | Power Artifact combo, Basalt combo |
| [Trick Deck](oldschool-trick-deck.md) | `trick-deck` | Underworld Dreams wheel, Dreams combo |

### Prison (7)

| Archetype | Stable ID | Aliases / related labels |
| --- | --- | --- |
| [Turbo Fog](oldschool-turbo-fog.md) | `turbo-fog` | Fog prison |
| [Land Equilibrium](oldschool-land-equilibrium.md) | `land-equilibrium` | Equilibrium Vortex |
| [Living Plane Combo](oldschool-living-plane-combo.md) | `living-plane-combo` | Living Plane, Tim lands |
| [Parfait](oldschool-parfait.md) | `parfait` | Winter Orb Parfait, Artifact prison |
| [The Void](oldschool-the-void.md) | `the-void` | Nether Void, Black prison |
| [Stasis](oldschool-stasis.md) | `stasis` | Turbo Stasis, Stasis Kismet |
| [Field of Dreams](oldschool-field-of-dreams.md) | `field-of-dreams` | Conceding Dreams, Field/Sindbad |

## Validation and limits

From the repository root, with Node.js available:

```sh
node docs/Oldschool-AI/validate.mjs
```

No package installation, network access, Java startup or profile writes are
needed. The check reads local card scripts and the eight permitted edition
files. It checks catalog coverage, card identities, counts, restrictions,
opening hands, bottoming, sideboard availability/balance, decision/scenario
presence and local link targets. External sites can change independently.

A passing check is **structural validation**, not proof of tactical correctness,
optimal deck construction, rules-engine compatibility, or stronger play. Review
the complex-interaction checklist in the implementation roadmap and turn the
proposed scenarios into focused tests before enabling corresponding AI rules.
Neither generic Constructed acceptance nor a completed Arena game proves
compliance with this custom rules contract.
