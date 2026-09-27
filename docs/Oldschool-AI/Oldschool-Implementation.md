# Old School AI: compatibility and implementation roadmap

[Library](Oldschool-AI.md) · [Rules contract](Oldschool-Rules.md) · [Imported decks](Oldschool-Imported-Decks.md)

Audit of this checkout, 2026-09-27. This document describes existing code and
future work. The fixtures and Markdown decision tables are not runtime
configuration. A separate [OS Reanimator runtime profile](OS-Reanimator-AI.md)
now implements a limited subset; BO3 Arena support enables between-game
sideboarding without changing the rules-contract gaps below.

## Current compatibility

| Concern | Observed implementation | Consequence for this library |
| --- | --- | --- |
| Profiles | [AiProfileUtil](../../forge-ai/src/main/java/forge/ai/AiProfileUtil.java) loads `.ai` entries using recognized `AiProps` enum keys. | Prose or arbitrary new profile keys are not executable strategy. |
| Keep decisions | [ComputerUtil.scoreHand / wantMulligan](../../forge-ai/src/main/java/forge/ai/ComputerUtil.java) remain the generic baseline; OS Reanimator adds retained-hand package scoring. | Exact multi-turn colored-mana and spell-chain planning remains incomplete. |
| London bottoms | [PlayerControllerAi](../../forge-ai/src/main/java/forge/ai/PlayerControllerAi.java) uses a shared keep/bottom scoring helper only for OS Reanimator. | Other profiles retain their generic logic; specialization does not cover every hand. |
| Sideboards | Generic profiles use hints/type/value/random swaps; OS Reanimator uses public observations and quantity-preserving swaps. | This is not a complete implementation of the guide tables or archetype recognition. |
| Arena rules | [ArenaWorkerMain](../../forge-gui-desktop/src/main/java/forge/view/ArenaWorkerMain.java) supports BO1 and first-to-two-wins BO3, London, no cheats/filtered hands, and AI sideboarding after game one in BO3. | BO1 is pre-board; BO3 is match-level scoring with individual game exports. Neither enforces the custom Old School rules below. |
| Mana-burn switch | [GameRules](../../forge-game/src/main/java/forge/game/GameRules.java) defaults mana burn off; Arena does not enable it. Normal [HostedMatch](../../forge-gui/src/main/java/forge/gamemodes/match/HostedMatch.java) copies the legacy preference. | Changing the desktop preference does not establish this contract in Arena workers. |
| Pool timing | [PhaseHandler.onPhaseEnd](../../forge-game/src/main/java/forge/game/phase/PhaseHandler.java) clears pools at transitions of [PhaseType](../../forge-game/src/main/java/forge/game/phase/PhaseType.java), whose values include individual steps. | Enabling burn still differs from the agreed **phase-end**, not step-end, convention. |
| Dexterity cards | [FlipOntoBattlefieldEffect](../../forge-game/src/main/java/forge/game/ability/effects/FlipOntoBattlefieldEffect.java) simulates random flips, misses and neighboring/multiple hits. | It is not the Swedish single-chosen-permanent Orb rule or a verified physical Falling Star model. Never report guaranteed hits. |
| Reproducibility | [ArenaSchedule](../../forge-gui/src/main/java/forge/gamemodes/aisimulation/ArenaSchedule.java) derives per-game seeds and alternates first-choice opportunities; results record the actual starting player. | Record actual play/draw and engine/resource fingerprints; a seed does not promise cross-version identical games. |

Generic Constructed legality is not a Swedish-FE legality check. Use the pinned
pool and combined main/side restriction limits before selecting benchmark decks.
Do not silently correct the user's lists. The imported-deck audit identifies
three issues and distinguishes them from legitimate non-60/non-15 lists.

## Complex-interaction review

These are rules-derived expectations checked against local card text and
implementation entry points, **not runtime-verified outcomes**. Turn them into
focused scenarios before enabling related heuristics.

| Interaction | Required expectation / adversarial case | Local reference |
| --- | --- | --- |
| Reanimator | Bazaar produces no mana. Animate Dead gives -1/-0 and its departure causes sacrifice; it does not waive Colossus's untap restriction. Check graveyard hate in response and Aura removal before valuing a guaranteed creature return. | [Bazaar](../../forge-gui/res/cardsfolder/b/bazaar_of_baghdad.txt), [Animate Dead](../../forge-gui/res/cardsfolder/a/animate_dead.txt), [Colossus](../../forge-gui/res/cardsfolder/c/colossus_of_sardia.txt) |
| Lich / Mirror | At zero with Lich, a legal Mirror exchange can lower the opponent to zero while your gain becomes draws. Check library size, gain/loss restrictions, legal targets and removal responses; decking can spoil the result. Mana burn is not Lich-triggering damage. | [Lich](../../forge-gui/res/cardsfolder/l/lich.txt), [Mirror](../../forge-gui/res/cardsfolder/m/mirror_universe.txt), [LifeExchangeEffect](../../forge-game/src/main/java/forge/game/ability/effects/LifeExchangeEffect.java) |
| Coffin / Triskelion | Recorded counters return in addition to entry counters, on a tapped creature. Two stored plus three entry counters means five. Test Coffin leaving, response removal, choosing not to untap, and owner/control changes. | [Coffin](../../forge-gui/res/cardsfolder/t/tawnoss_coffin.txt), [Triskelion](../../forge-gui/res/cardsfolder/t/triskelion.txt) |
| Transmute / Su-Chi | Sacrifice happens during resolution, not casting. Su-Chi's triggered mana cannot pay the same Transmute's in-resolution mana-value difference. | [Transmute](../../forge-gui/res/cardsfolder/t/transmute_artifact.txt), [Su-Chi](../../forge-gui/res/cardsfolder/s/su_chi.txt) |
| Extra turns | Vault enters tapped; each Twiddle and recursion step consumes resources. Fork copies a suitable spell on the stack, not a Vault activation. Recall discards during resolution and exiles itself. | [Time Vault](../../forge-gui/res/cardsfolder/t/time_vault.txt), [Fork](../../forge-gui/res/cardsfolder/f/fork.txt), [Recall](../../forge-gui/res/cardsfolder/r/recall.txt) |
| Positive mana cycle | Basalt plus Power Artifact nets two colorless per complete tap/untap cycle. Bound cycles by intended expenditure, reserve colored payoff mana, and test removal/cost-changing effects. | [Basalt](../../forge-gui/res/cardsfolder/b/basalt_monolith.txt), [Power Artifact](../../forge-gui/res/cardsfolder/p/power_artifact.txt) |
| Phase-end burn | Upkeep mana can reach draw but not main; combat-step mana can reach another step of that combat but not the next main. Test Su-Chi, Mana Drain, inability to lose life, Ali and prevention. | [PhaseHandler](../../forge-game/src/main/java/forge/game/phase/PhaseHandler.java), [ManaPool](../../forge-game/src/main/java/forge/game/mana/ManaPool.java) |
| Land Equilibrium | A replacement puts the land in and then makes its controller sacrifice a land when the pre-entry condition applies. It is neither a ban on playing lands nor a trigger allowing an intervening response. | [Land Equilibrium](../../forge-gui/res/cardsfolder/l/land_equilibrium.txt) |
| Untap locks | Tap Winter Orb before untap; Stasis skips that step entirely. Reset is restricted to the opponent's turn after upkeep. Do not confuse these effects. | [Winter Orb](../../forge-gui/res/cardsfolder/w/winter_orb.txt), [Stasis](../../forge-gui/res/cardsfolder/s/stasis.txt), [Reset](../../forge-gui/res/cardsfolder/r/reset.txt) |

## Implementation stages

1. **Establish comparable rules.** Add an explicit custom rules choice and
   eligibility validation through existing game setup paths. Resolve the
   phase/step burn gap and specify a versioned digital dexterity-card convention
   before claiming exact compatibility. Until then, label experiments as using
   the engine approximation or exclude affected decks openly.
2. **Recognize plans, not filenames.** Introduce an optional strategy context
   in `forge-ai`, using card roles and combinations to infer one or more likely
   archetypes. Preserve generic AI when confidence is low. Own deck knowledge
   is available; opposing hidden cards and unrevealed library order are not.
   Explicit open-decklist experiments must be separate from ordinary play.
3. **Implement testable decisions.** Start with The Deck, Atog and Reanimator,
   using guide IDs for traceability. Integrate retained-hand scoring, colored
   and conditional mana, roles and threat selection through existing controller,
   utility and `SpellApiToAi`/ability AI seams. Keep legality in the engine rather
   than duplicating it in a strategy override. Expand archetypes after their
   decision cases and resource-bounded combo lines work.
4. **Treat sideboarding separately.** Use actual quantities, opponent evidence
   and seat; apply swaps from the original list, preserve sizes and restrictions,
   and retain engine viability. Evaluate pre-board and post-board separately.
   BO3 Arena and the initial OS Reanimator observation-based policy now provide
   a starting point; the other guide-specific policies remain future work.
5. **Gate and measure.** Make strategy opt-in with a recorded version, comparing
   it with unchanged Default. Log rule IDs, observable reasons and resource
   budgets for failed decisions without leaking hidden information. Preserve
   isolated worker JVMs, not concurrent games inside the GUI JVM.

Keep changes in existing layers: rules in `forge-game`, decisions in `forge-ai`,
orchestration in shared GUI/Arena, controls in the desktop UI. Do not introduce
`FModel`, Swing or libGDX dependencies into the AI/engine. The runtime adds the
opt-in `OS_REANIMATOR` key and Arena configuration version 2; `catalog.json` and
`rules.json` remain documentation metadata only.

## Future benchmark protocol

- Convert H/S cases into deterministic TestNG scenarios using existing
  desktop-hosted AI/game fixtures. Test legal choices, colored costs, retained
  hands, sideboard quantities, loop bounds and hidden-information boundaries.
  Include negative cases where an attractive named combo must **not** be used.
- Freeze legal lists, sideboards, profiles, rules choice, engine/card fingerprints
  and timeouts. Use several builds per archetype, reserving some as held-out
  evaluation lists. Do not tune on the final test pairings.
- Begin with 100 attempts per pairing on each of at least three base seeds,
  alternating first-choice opportunities and stratifying by actual starting
  player. Increase samples if uncertainty remains large; initial counts are
  a sampling protocol, not proof of superiority.
- Run Default-versus-Default as a reference, then swap candidate/Default roles
  on the same deck matchups and seed schedule. Changed decisions can change RNG
  consumption: paired seeds help diagnosis but do not guarantee identical
  shuffles or independent statistical samples.
- Report per-archetype/opponent wins, losses, draws, actual play/draw, valid-game
  counts, score `(wins + 0.5 * draws) / valid games`, timeouts/errors, turns,
  throughput and uncertainty intervals. Aggregate both per game and with equal
  archetype weights so multiple similar Atog lists cannot hide other failures.
- Separate startup/warm-up cost from game throughput. Never convert timeout,
  engine exception or an unproven soft lock into a win or ordinary draw. Report
  failure rates alongside scores to avoid survivor bias, especially for combos.
- Keep pre-board, actual sideboarded matches and fixed post-board fixtures in
  separate datasets. The guide swaps are hypotheses, not evidence of sideboarding
  quality. Review regressions and held-out results before calling a candidate
  stronger or expanding its default use.

Run the offline validator for guide changes and the focused Maven tests linked
in the runtime profile for code changes. Compilation and regression tests alone
do not establish a strength claim; use held-out tournaments for that comparison.
