# AI Arena

AI Arena is a desktop-only, two-player Constructed deck tournament simulator.
Open **Play Mode > AI Arena** in the Swing client, including the Docker/noVNC
desktop. Games run in background Java processes without battlefield windows,
animations, or card-image downloads.

## Set up a run

1. Create and save decks in the existing Constructed deck editor.
2. Open AI Arena and select at least two decks. Search includes deck names and
   subfolders; **Select visible** selects the filtered list. **Refresh** picks up
   decks saved through the application. Save editor changes before starting.
3. Choose an AI profile for each deck, or apply a profile to all rows. The list
   comes from installed named `.ai` profiles; random profile modes are not used.
4. Choose **BO1** or **BO3**, name the run, set matches per pairing, and choose
   worker, heap, and match-timeout limits. BO1 remains the default.
5. Start the tournament. Preparation validates saved decks and snapshots their
   contents, AI profiles, custom resources, and preferences. Later deck edits do
   not alter that run.

With **10 decks and 100 matches per pairing**, there are 45 unique pairings,
**4,500 scheduled matches total**, and **900 matches per deck**. There are no
self-pairings. BO1 plays one game per match. BO3 plays until one deck wins two
games, normally two or three games; drawn games do not count toward those wins
and can extend a match. The timeout bounds the entire match, including draws.

Games use normal Constructed rules and London mulligans, without ante, AI cheats
or filtered opening hands. BO1 has no sideboarding. In BO3, the existing match
engine invokes AI sideboarding before games two and three (and any draw replays);
the selected profile decides whether and what to swap. Game-one starting-player
opportunities alternate within each pairing. After a decisive game, its loser
chooses play/draw. Rules and card effects can still change the starting player.
Each individual game's actual starting player is recorded.

Every match starts from fresh deck snapshots; sideboard changes never leak into
the next pairing or modify saved decks. Select **OS Reanimator** for the dedicated
[Reanimator strategy](Oldschool-AI/OS-Reanimator-AI.md). Other named profiles keep
their existing generic sideboarding behavior; enabling BO3 does not guarantee
every profile makes a swap.

## Speed and memory

Defaults are 100 matches per pairing, BO1, two workers where available, 2,048 MB
maximum heap per worker, and a 120-second timeout per match. Increase the timeout
for slow BO3 pairings. Each worker loads the card database once and plays matches
sequentially. Workers run in separate JVMs because
the engine has shared static state and is not suitable for concurrent independent
games in a single JVM.

Worker heaps are **in addition to** the main Forge application's heap. With the
Docker defaults, the JVM heap ceilings total 4 GB for the desktop plus 2 GB per
worker; native memory and the virtual desktop need additional RAM. Start with one
or two workers and increase only when CPU and memory permit. More workers do not
guarantee proportionally higher throughput, especially for short tournaments.

The progress page shows finished attempts, active pairings, elapsed time,
throughput, and estimated remaining time. Worker startup is included in elapsed
time but excluded from the per-match timeout. Startup has a separate 180-second
deadline. A timed-out or failed worker is replaced, not allowed to keep consuming
CPU in the background. Uninitializable workers stop the run with an error.

## Results and controls

- **Pause** stops dispatching new matches and lets active matches finish.
- **Resume** schedules only matches without saved results, including after restart.
- **Cancel** stops active matches and retains committed results. Unfinished
  matches restart from game one on resume; there is no mid-match checkpoint.
- Changing screens does not stop a running tournament. Only one run is active
  per desktop session; a file lock prevents two sessions writing the same run.
- **Saved runs** reopens reports. **New run from setup** starts a fresh run using
  the saved input snapshots, without changing the original report.

Standings show match wins, losses, draws, average total turns/duration per match,
errors, and timeouts. Score is `(wins + 0.5 * draws) / valid matches`; win percentage
is `wins / valid matches`. BO1 statistics are unchanged. In BO3, a 2-0 or 2-1 is
one match win, not two leaderboard wins. The **Match results** tab shows scores.
The matchup matrix shows the row deck's score against each column deck. Empty
cells have no valid results. Tied scores retain the original entrant order.

Timeouts and errors are finished **attempts**, not wins, losses, or draws. They
are excluded from percentages and averages and shown separately. They are not
retried automatically, so a run with 100 scheduled attempts can contain fewer
than 100 valid matches. Partial games in a failed or timed-out match do not earn
leaderboard points. The failures tab includes diagnostic details.

## Persistence and exports

Runs live under `<user directory>/ai-simulations/<run ID>/`. In the provided Docker
setup this is **`data/profile/ai-simulations/` on Windows**, using the existing bind
mount. No extra Docker service, port, database, or mount is required.

- `run.json`: versioned configuration, entrants, RNG seed, and compatibility hashes.
- `inputs/`: frozen decks, AI profiles, preferences, and custom resources.
- `games.jsonl`: retained legacy filename; one committed result per scheduled
  match, now with individual game results and counts of cards boarded into each
  main deck relative to game one. An interrupted trailing record is discarded
  on recovery. Duplicate results cannot inflate standings.
- `status.json`: saved status and elapsed time.
- `workers/`: isolated worker profiles/caches and capped diagnostic logs.
- `export-*/`: **Export CSV** creates standings, matchups, `matches.csv` summaries,
  `games.csv` individual results with sideboard counts, and the configuration.

Configuration version 2 records `gamesPerMatch` (1 or 3). Older version-1 runs
load as BO1 and remain readable. Historical JSON fields `gamesPerPair`,
`gameId` and the Java `totalGames()` accessor retain their names but identify
scheduled matches. Resume still requires matching code/resource fingerprints.

Back up the entire run directory. Do not edit its input snapshots or configuration.
Resume checks snapshots and the engine/rules fingerprint; incompatible runs remain
readable, but must be restarted using **New run from setup**. Seeds aid diagnosis;
they do not guarantee identical games across different engine builds.

Worker profiles use optional JVM properties `forge.userDir` and `forge.cacheDir`.
When supplied, each overrides the corresponding profile-file value and resets
its derived deck/image directory settings. Normal launches without these properties
retain existing profile behavior. Workers do not save changes to your actual decks
or preferences. The existing `sim` command remains available and unchanged.

## Developer verification

From the repository root, with Java 17+ and Maven installed:

```sh
mvn -B -pl forge-gui-desktop -am "-Dtest=ArenaSimulationTest,ArenaProcessWorkerTest,OldschoolReanimatorAiTest" "-Dsurefire.failIfNoSpecifiedTests=false" test
```

The tests include real headless games, process crashes/timeouts, scheduling,
scoring, snapshot preservation, result recovery, locking, and pause/resume.
Also smoke-test the desktop screen with a display and check the existing engine
and headless-startup tests when changing game startup or worker bootstrapping.

For Docker, rebuild with `wsl.exe --exec docker compose up --build` from PowerShell.
Keep the terminal open when using native Docker Engine in WSL, as described in
[Docker setup](Docker-Setup.md).
