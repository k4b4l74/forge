# OS Reanimator runtime AI

[Strategy guide](oldschool-reanimator.md) · [AI Arena](../AI-Arena.md) · [Rules limitations](Oldschool-Implementation.md)

Select **OS Reanimator** in an AI profile selector, including each deck's AI Arena
row. Restart/rebuild the application after installing the changed code/resources.
The profile is opt-in; other profiles and saved decks are not converted.

## Baseline choice

The four existing profiles are **Default, Cautious, Reckless and Experimental**.
They are personality/heuristic configurations of the same engine, not four
ranked difficulty levels. There is no benchmark here proving an overall best.
This implementation uses **Default as the stable baseline**, rather than assuming
Experimental is strongest: its settings include more randomness, different
counterspell thresholds and speculative behavior, not a universal upgrade.

`OS Reanimator.ai` copies Default's settings, disables its shuffle-cheat option,
makes damage-spell chaining consideration deterministic, and enables the
`OS_REANIMATOR` switch. Arena additionally disables cheats at game setup.
This is a first specialized candidate, **not a claim of superiority**. Benchmark
it against unchanged Default with the same deck lists before making that claim.

## Implemented decisions

The implementation is [OldschoolReanimatorAi](../../forge-ai/src/main/java/forge/ai/OldschoolReanimatorAi.java),
called through existing controller and ability-AI hooks, not a Markdown reader.

- **London mulligans:** evaluate retained subsets for ordinary seven-card hands,
  counting mana-producing lands/free rocks rather than Bazaar as mana; favor an
  intact outlet/Animate Dead/target package while allowing colored development.
  Keep/bottom decisions share the scoring function. Stop mulliganing at three.
- **Bazaar:** avoid low-resource, aimless activations; prefer discarding a large
  creature when Animate Dead is retained, and protect mana and reanimation.
  Recall's discard choice uses the same preference. These are heuristics, not
  exact multi-turn mana planning or a solution to every discard replacement.
- **Animate Dead:** among the normal AI's filtered legal candidates, value fresh
  Triskelion counters, prioritize immediate stabilization under pressure, and
  prefer Rasputin when a Triskelion is in hand and no Rasputin is already in play.
- **Copy Artifact:** recognize a depleted Triskelion as a fresh-counter copy
  opportunity, rather than valuing only its current small body. Other artifact
  choices continue through the generic AI.
- **Dreams finish:** Ancestral Recall or a payable Braingeyser may target an
  opponent for predicted lethal Underworld Dreams damage. The estimate accounts
  for currently predicted damage prevention and loss restrictions; responses and
  draw replacements can invalidate a prediction. Nonlethal draws use normal AI.
- **Sideboarding:** collect opposing face-up cards observed in public zones
  during the match, never the opposing deck file, hand or library. Bring in
  relevant blasts, Circles, Disenchant or Swords based on those observations.
  A conservative two-Dreams pivot requires slow-control evidence, no observed
  creatures/red spells, sufficient black lands and a draw-seven in your own list.

Sideboarding starts from the match's original own deck, makes at most six
one-for-one swaps, and preserves all card identities and quantities. Cuts favor
Recall/Psionic Blast and surplus Erhnam/Rasputin/Copy Artifact, protecting mana,
Animate Dead and Triskelion. Game-three decisions can revise the game-two plan;
observations reset for a new match. If the match's available card pool changes,
the baseline is refreshed rather than inventing missing cards. Other profiles
retain Forge's existing hint-based/generic sideboarding.

## Boundaries and validation

This is not full implementation of every KabaL guide line: deliberate self-ping
counter resets, exact Rasputin spell-chain planning, Bazaar-in-response-to-Balance,
and multi-turn recursion still depend on generic AI and need further tactical
tests. Sideboard observations are a coarse policy, not archetype recognition or
an implementation of all 50 guides' sideboard tables. The fixed mana-burn and
Chaos Orb differences in the compatibility audit remain unchanged.

Focused TestNG coverage is in `OldschoolReanimatorAiTest`, `ArenaSimulationTest`
and `ArenaProcessWorkerTest`. It checks retained hands, discard/copy/reanimation
choices, Dreams damage thresholds, information boundaries, quantity-preserving
sideboarding, BO1 compatibility, BO3 scoring/persistence, and real worker matches.
Passing regression tests establishes those behaviors, not tournament strength.
