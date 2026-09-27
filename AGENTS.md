# Working on Forge

## Read this first

Forge is a Java application for playing Magic: The Gathering, with a shared rules
engine, computer opponents, Swing and libGDX clients, and a large scripted card
database. Adventure mode is part of the libGDX client, including on desktop.
This is a Maven multi-module repository; the checkout directory name does not
imply a separate architecture or a restricted card format.

Before editing:

1. Read `CONTRIBUTING.md`, then the relevant module's `pom.xml` and nearby code.
2. Locate the behavior in the card scripts, engine, AI, shared application layer,
   or platform UI before choosing where to fix it.
3. Find an existing analogous implementation and relevant tests. Many tests for
   shared code live in `forge-gui-desktop`, not alongside the production module.
4. Check `git status --short` and preserve unrelated local changes.

Use the checked-in POMs, workflows, and implementation as the source of truth
when older prose documentation disagrees. In particular, rules execution lives
in `forge-game`; `forge-core` primarily supplies card definitions and utilities.

## Module map and dependency direction

The main dependency chain is shown below; arrows mean "depends on". Modules also
declare direct dependencies on lower layers where needed.

```text
forge-gui-desktop -----------------------> forge-gui
forge-gui-mobile-dev -> forge-gui-mobile -> forge-gui
forge-gui-android ---> forge-gui-mobile
forge-gui-ios -------> forge-gui-mobile
forge-gui -> forge-ai -> forge-game -> forge-core
```

| Module | Responsibility and starting points |
| --- | --- |
| `forge-core` | Card definitions, editions, printing database, mana representations, basic deck/item abstractions, localization and utilities. Start with `forge.StaticData`, `forge.CardStorageReader`, `forge.card.CardRules`, `forge.card.CardDb`, and `forge.item.PaperCard`. |
| `forge-game` | Mutable game state and rules execution: `forge.game.Game`, `Match`, `GameAction`, and packages for abilities, cards, players, phases, combat, costs, mana, zones, triggers, replacements, static abilities, and keywords. Also owns `forge.trackable`. |
| `forge-ai` | Computer decisions: `forge.ai.PlayerControllerAi`, `AiController`, `ComputerUtil*`, combat controllers, `ability/`, and `simulation/`. `SpellApiToAi` maps engine APIs to AI implementations. |
| `forge-gui` | Shared application services and UI contracts, not the Swing renderer. Includes `forge.model.FModel`, `forge.gui.GuiBase`, `forge.player.PlayerControllerHuman`, deck generation/storage, match orchestration, quest, limited, planar conquest, puzzles, networking, and all shared `res/` assets. |
| `forge-gui-desktop` | Swing client. Entry point `forge.view.Main`; `forge.GuiDesktop` implements platform services. UI code includes `forge.screens`, `forge.control`, `forge.view`, and `forge.toolbox`. Hosts most tests. |
| `forge-gui-mobile` | Shared libGDX client: `forge.Forge`, `forge.GuiMobile`, screens/toolbox/assets, and `forge.adventure` gameplay. |
| `forge-gui-mobile-dev` | LWJGL3 desktop launcher for the libGDX client and Adventure: `forge.app.Main`. |
| `forge-gui-android` | Android launcher/integration: `forge.app.Main`; Android packaging profiles and bundled platform libraries. |
| `forge-gui-ios` | iOS launcher `forge.ios.Main`, RoboVM/MobiVM integration, and bytecode compatibility pipeline. Only joins the root reactor with `-Pios`. |
| `adventure-editor` | Swing tools for editing Adventure data; entry point `forge.adventure.Main`. Depends on shared GUI and mobile modules. |
| `forge-lda` | Topic-model tooling for deck archetypes/generation; entry point `forge.lda.LDAModelGenerator`. |
| `forge-installer` | Distribution assembly and installers, combining desktop and libGDX desktop builds. |

Most Java modules use `src/main/java`. Exceptions: `forge-gui-mobile`,
`forge-gui-mobile-dev`, `forge-gui-android`, `forge-gui-ios`, and `forge-lda` use
`src` as their Java source root. Check the POM before creating a source directory.
Tests use `src/test/java`.

Keep dependency direction intact. Engine and AI changes should not introduce
dependencies on Swing, libGDX, or `FModel`. Shared application code should use
`GuiBase`/`IGuiBase` and game UI interfaces instead of concrete platform classes.

## How the application fits together

### Startup, assets, and user data

- Swing startup is in `forge-gui-desktop/src/main/java/forge/view/Main.java`.
  It installs `GuiDesktop` through `GuiBase.setInterface`, then initializes
  application singletons and controllers. `FModel.initialize` sets up preferences,
  localization, card readers, the database, and mode data.
- Initialization order matters: `ForgeConstants.ASSETS_DIR` calls
  `GuiBase.getInterface().getAssetsDir()` during class initialization. Set the
  platform interface before accessing constants or initializing the model.
- `FModel` is a shared service holder with memoized data. `StaticData` owns card
  databases. Card loading can be lazy; bulk processing and tests that need all
  scripts must account for `FPref.LOAD_CARD_SCRIPTS_LAZILY`. Custom card/token
  readers currently load eagerly.
- In a development build, `GuiDesktop.getAssetsDir()` resolves `../forge-gui/`.
  Run the Swing application with `forge-gui-desktop` as the working directory.
  Missing cards/languages/assets may be a working-directory problem.
- Resource paths and profile paths are defined in
  `forge-gui/src/main/java/forge/localinstance/properties/ForgeConstants.java`
  and `ForgeProfileProperties.java`. User decks, preferences, saves, and downloaded
  images are separate from checked-in assets. Use these abstractions rather than
  hardcoded machine paths. The configuration template is
  `forge-gui/forge.profile.properties.example`; the actual profile is ignored.
- Treat bundled assets as readable application data. Persist user changes through
  the user/profile paths; iOS in particular uses a read-only app bundle.

### Card definitions to running games

```text
forge-gui/res/cardsfolder/**/*.txt
  -> CardStorageReader / CardRules.Reader
  -> CardRules + CardDb / PaperCard (StaticData)
  -> CardFactory -> in-game Card / CardState
  -> AbilityFactory -> SpellAbility -> ApiType -> *Effect.resolve
```

`PaperCard` represents a printing/collectible card; `CardRules` describes its
definition; `forge.game.card.Card` is a mutable object in a particular game.
Keep that distinction when changing deck/database code versus gameplay code.

`Match` manages games and outcomes under `GameRules`. `Game` owns players, state,
actions, phase handling, triggers, replacements, events, and a `GameView`.
`GameAction` contains central operations such as zone changes and state-based
checks. Use existing game operations for mutations so their triggers,
replacement effects, last-known information, and view updates remain consistent.
Zone-change operations can return a different card object; follow their return
values and existing callers rather than assuming identity is unchanged.

For rules work, start under `forge-game/src/main/java/forge/game/`:

| Concern | Location |
| --- | --- |
| Ability parsing, numeric/defined expressions, resolution | `ability/AbilityFactory.java`, `ability/AbilityUtils.java`, `ability/ApiType.java`, `ability/effects/` |
| Spell state, targeting, restrictions | `spellability/` |
| Card construction and keyword expansion | `card/CardFactory.java`, `card/CardFactoryUtil.java`, `keyword/` |
| Trigger dispatch | `trigger/TriggerHandler.java`, `trigger/TriggerType.java`, concrete trigger classes |
| Replacement effects | `replacement/ReplacementHandler.java` and concrete replacement classes |
| Continuous effects and restrictions | `staticability/` |
| Turn progression and combat | `phase/PhaseHandler.java`, `combat/` |
| Costs, payment, and movement | `cost/`, `mana/`, `zone/`, `GameAction.java` |

Player choices go through `forge.game.player.PlayerController`. Its AI
implementation is in `forge-ai`; the human implementation is in `forge-gui`.
Keep rules legality separate from AI desirability. When adding an `ApiType`,
check its effect implementation and the matching `SpellApiToAi` registration/
`SpellAbilityAi` behavior; legal new effects also need usable AI decisions.

### UI, threading, and network state

- Shared UI contracts are in `forge-gui/src/main/java/forge/gui/interfaces/`.
  `forge.gamemodes.match.HostedMatch` connects matches to application/UI services.
- Use `forge.gui.FThreads` and existing platform scheduling helpers for GUI
  work. They route to Swing or libGDX behavior through `GuiBase`. Do not block
  the GUI thread with game loops, downloads, or bulk data loading.
- `GameView`, `CardView`, and `PlayerView` expose state through `forge.trackable`.
  Trackable properties support GUI updates and network delta synchronization.
  Preserve update paths and dirty tracking when adding or changing displayed
  state. A frozen tracker queues writes, so reads can still see the old value.
- Multiplayer code is in
  `forge-gui/src/main/java/forge/gamemodes/net/`, including client/server handlers,
  `ProtocolMethod`, `TrackableSerializer`, and delta synchronization. Wire data
  uses Java serialization with `WireClassFilter` and `WireStreamLimits`.
  Protocol changes must account for both endpoints, serialized views, validation,
  and full-state/delta paths; do not bypass the existing deserialization checks.

### Adventure

Adventure gameplay lives in `forge-gui-mobile/src/forge/adventure/`, with
`scene/`, `stage/`, `world/`, `player/`, `data/`, and `util/` packages. Its assets
are in `forge-gui/res/adventure/`: shared `common/` resources and plane-specific
content such as `Shandalar/`. `forge.adventure.util.Config` resolves plane
resources with common fallbacks. Reuse that lookup rather than bypassing it.
Data changes may also affect `adventure-editor` and existing saved games.

## Card scripts and other resources

The card database is source code expressed as text, not generated Java. For a
single-card bug, first inspect its script and compare cards with similar effects.
Prefer an existing scripting API when it expresses the intended behavior.
Changing `Oracle:` text alone does not implement an ability.

- Card definitions: `forge-gui/res/cardsfolder/`, usually in letter subfolders.
- Token definitions: `forge-gui/res/tokenscripts/`.
- Printing/set information and rarity: `forge-gui/res/editions/`.
- Format definitions: `forge-gui/res/formats/`; shared type/keyword lists:
  `forge-gui/res/lists/`.
- AI profiles/data: `forge-gui/res/ai/`; draft and deck data also live under
  `res/draft/`, `res/deckgendecks/`, `res/geneticaidecks/`, and mode directories.
- UI translations: `forge-gui/res/languages/`, including `en-US.properties`.
- Puzzle scenarios: `forge-gui/res/puzzle/`; Adventure data: `res/adventure/`.
- Maintenance/import scripts: `forge-gui/tools/`. Read a tool before running it;
  some download external datasets or rewrite many resources.

Card script syntax and conventions:

- Records use `Key:Value`; ability parameters use `Key$ Value | Key$ Value`.
  `A:` declares abilities, `K:` keywords, `T:` triggers, `R:` replacements,
  `S:` static abilities, and `SVar:` named strings/subabilities/expressions.
- Ability record prefixes include `SP$` (spell), `AB$` (activated ability),
  `DB$` (subability), and `ST$` (static ability record). Follow the relevant
  parser and a working analogous card instead of inventing parameter names.
- For example, `forge-gui/res/cardsfolder/l/lightning_bolt.txt` uses
  `A:SP$ DealDamage | ValidTgts$ Any | NumDmg$ 3 | ...`.
  `forge-gui/res/cardsfolder/e/elvish_visionary.txt` shows a trigger referring to
  an `SVar` subability through `Execute$`.
- Use lowercase filenames, underscores for spaces, and omit special characters.
  Card scripts use LF line endings. Avoid blank lines except between card faces;
  multi-face definitions use `ALTERNATE` with `AlternateMode:` metadata.
- Put AI SVars just before `Oracle:` and omit redundant default parameters,
  following `docs/Card-scripting-API/Card-scripting-API.md`.

The scripting reference is local under `docs/Card-scripting-API/`, with separate
files for abilities, costs, targeting, restrictions, triggers, replacements, and
statics. Check the Java parser/effect if a detail is missing or outdated. Shared
effect changes can affect many cards, so consider targeting, optional/mandatory
choices, owner versus controller, zone transitions, multiplayer, and AI behavior.

## Build and run

Prerequisites: JDK 17+ and Maven 3.8.1+. The parent targets Java 17; normal CI
tests with Java 17 and 21. There is no checked-in Maven wrapper. Check
`java -version` and `mvn -version` before diagnosing build failures.

Run Maven commands from the repository root. `.mvn/maven.config` selects
`.mvn/local-settings.xml`. Use `-pl` to select modules and `-am` to include their
reactor dependencies; building a child in isolation can use stale installed
Forge dependencies.

```sh
# Compile the Swing client and shared dependencies
mvn -B -pl forge-gui-desktop -am compile

# Compile both desktop clients and their shared dependencies
mvn -B -pl "forge-gui-desktop,forge-gui-mobile-dev" -am compile

# Run the small engine-local test suite
mvn -B -pl forge-game -am test

# Run desktop-hosted tests, including shared engine and AI coverage
mvn -B -pl forge-gui-desktop -am test

# Run one existing AI test, allowing upstream modules without that test
mvn -B -pl forge-gui-desktop -am "-Dtest=DamageDealAiTest" "-Dsurefire.failIfNoSpecifiedTests=false" test

# Full normal CI test command (Linux CI also starts Xvfb)
mvn -U -B clean test

# Full Windows/Linux distribution build documented by CONTRIBUTING.md
mvn -U -B clean -P windows-linux install
```

`compile`/`test` are generally enough for focused development; `package`,
`verify`, and `install` also invoke packaging work in relevant modules.
`-DskipTests` skips test execution and is not evidence that tests pass.
The parent runs Checkstyle during `validate`, including test sources; the root
`checkstyle.xml` currently checks redundant and unused imports.

For a containerized Swing client, the root `Dockerfile` and `docker-compose.yml`
build and run with Java 21 and expose a local noVNC desktop. Settings come from
`.env` (copy `.env.example` on a fresh clone). See `docs/Docker-Setup.md` for WSL
commands, browser access, and persistent `data/profile` and `data/cache` bind mounts. The image compiles the desktop
reactor without running tests or release packaging. Validate container changes
with `docker compose config`, an image build, and an application startup check.

IDE launch configurations:

| Client/tool | Main class | Classpath module / working directory |
| --- | --- | --- |
| Swing | `forge.view.Main` | `forge-gui-desktop` |
| libGDX desktop / Adventure | `forge.app.Main` | `forge-gui-mobile-dev` |
| Adventure editor | `forge.adventure.Main` | `adventure-editor` |

Use the VM options from the appropriate POM/launcher and
`docs/Development/IntelliJ-setup/IntelliJ-setup.md`; reflective libraries require
several `--add-opens` options on modern Java. Launcher templates for packaged
distributions live in the modules' `src/main/config/` directories.

The Swing entry point also supports `sim` and `parse` command-line modes.
The Swing **AI Arena** submenu uses `forge.gamemodes.aisimulation` for scheduling,
storage, and scoring, and `forge.screens.home.arena` for UI and process launching.
`forge.view.ArenaWorkerMain` runs independent games in isolated JVMs. Do not replace
these workers with concurrent games in the GUI JVM: RNG and engine identifiers
have shared static state. Runs are stored below `USER_DIR/ai-simulations`; see
`docs/AI-Arena.md` for recovery, scoring, and focused test commands.
See `docs/AI.md` for simulation arguments. `parse` is a maintenance dispatch in
`CardReaderExperiments`, not a general read-only card validation command: bare
`parse` does nothing, and operations such as `updateAbilityManaSymbols` rewrite
scripts. The `server` branch in this entry point is a placeholder, not the
implemented multiplayer lobby/server stack.

Platform builds:

- Android uses Maven profiles in `forge-gui-android/pom.xml`; APK builds need the
  Android SDK and release signing needs external credentials. Read the POM and
  `.github/workflows/test-android-build.yml` for current configuration. Do not
  assume Java APIs supported on desktop are available on Android.
- iOS requires locally bootstrapped compatibility artifacts, so `-Pios` alone
  is not a fresh-clone setup. Follow `docs/Development/iOS-Builds.md` and
  `forge-gui-ios/pipeline/ios-pipeline.sh`. The compatibility gate runs
  `bash forge-gui-ios/pipeline/ios-pipeline.sh audit` on Linux without Apple tools;
  device/simulator builds require the Apple toolchain. Shared Java/dependency
  changes can affect this gate even when desktop compilation succeeds.

## Validation strategy

- Tests use TestNG; desktop tests also use Mockito. Reuse existing helpers and
  test style instead of introducing a new framework.
- `forge-game/src/test/java/` has small ability-key and mana-payment tests.
  Most card database, rules, AI, deck, GUI, and network coverage lives in
  `forge-gui-desktop/src/test/java/` even when production code is elsewhere.
- Useful fixtures there: `forge.ai.AITest` initializes the model/database and
  creates game states; `forge.ai.simulation.SimulationTest` supports AI simulation;
  `forge.gamesimulationtests.BaseGameSimulationTest` and its `util/` package
  support scripted scenarios; `forge.card.CardMockTestCase` supports card mocks.
  Respect fixture cleanup and one-time initialization. Do not interrupt shared
  card loading inside a test timeout or enable parallel tests casually.
- For a targeted Maven run across `-am` dependencies, include
  `-Dsurefire.failIfNoSpecifiedTests=false` and verify the intended test actually
  ran in `target/surefire-reports/`. A successful invocation with no matching
  tests is not validation.
- Some tests need a display. `.github/workflows/test-build.yaml` starts Xvfb
  before `mvn -U -B clean test`; do the equivalent on headless Linux for GUI
  coverage. Headless support for command-line modes does not make every GUI
  test headless-compatible.
- Network batch/stress tests are explicitly gated by `-Drun.stress.tests=true`.
  Read `docs/Development/Network-Testing.md` for scenarios and limits before
  running batches; they can spawn multiple JVMs and real local TCP connections.
- Start with relevant existing tests, then broaden when shared behavior warrants
  it. For script/UI/Adventure changes, use an appropriate in-game scenario or
  manual smoke check as well; compilation alone does not exercise resources.
- Follow `CONTRIBUTING.md`: avoid unnecessary unit/wiring tests. Add regression
  coverage when it protects meaningful behavior, using the existing suites.
- Report what was actually checked and any environment limitations. Do not claim
  a platform build or gameplay scenario was verified unless it was run.

## Change conventions and useful references

- Follow nearby Java style; much of the code uses four spaces, same-line braces,
  and `final` parameters/locals. Avoid unrelated formatting and import churn.
- Keep source/resource text UTF-8 and preserve format-specific conventions.
  Use `Localizer.getInstance().getMessage(...)` and language keys for new UI
  strings. Reuse skin-aware components/colors. See
  `docs/Development/UI-Guidelines.md` for placement of settings and controls.
- Preserve save/deck serialization compatibility when modifying persistent data.
  For shared preferences or UI contracts, inspect both Swing and libGDX callers.
- Do not commit generated `target/`, test reports, downloaded images, local
  profiles, IDE metadata, or signing material. `forge-gui/README.txt` explicitly
  identifies itself as release-bot output derived from `release-files/`.
- `docs/` supplies the published wiki through `.github/workflows/sync-wiki.yml`.
  Keep its relative documentation links usable in the repository.
- Per `CONTRIBUTING.md`, disclose substantial coding-agent contributions in a
  pull request body or co-author attribution when preparing a PR.

Search narrowly: this repository contains tens of thousands of card/resource
files. Prefer `rg` on a module or resource subdirectory. If unavailable, use
`git grep -n` for tracked text and `git ls-files` for filenames; in PowerShell,
use `Get-Content -Encoding UTF8` and `Select-String`. Useful starting searches:

```sh
git ls-files '*lightning_bolt*'
git grep -n 'ApiType.DealDamage' -- forge-game forge-ai
git grep -n 'LOAD_CARD_SCRIPTS_LAZILY' -- forge-gui forge-gui-desktop
```

Additional local references: `docs/Creating-a-custom-Card.md`,
`docs/Creating-a-custom-Set.md`, `docs/File-Formats.md`,
`docs/Development/DevMode.md`, `docs/Development/Deck-Generation.md`,
`docs/Development/ownership.md`, and `docs/Adventure/`.
