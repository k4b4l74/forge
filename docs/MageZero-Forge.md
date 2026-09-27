# MageZero / Forge hybrid pilot

This is an experimental, **Arena-only** learned profile, not a replacement rules
engine or a proven improvement over `OS Reanimator v2`. Forge remains responsible
for legality, game execution and generating the choices presented to the model.

## What is learned

| Decision | Controller |
| --- | --- |
| Mulligan and London bottoming | Frozen `OS Reanimator v2` heuristics |
| Reanimator discard choices, including Bazaar and Recall | Learned policy, when supported |
| Reanimation target proposals | Learned policy, when supported |
| Casting/activation timing, mana, combat, other targets and sideboarding | Existing Forge heuristics |

The adapter uses Will Wroble's actual
[MageZero `NetTransformer`](https://github.com/WillWroble/MageZero/blob/11a5974668c3f0f3d19559d7dcbf6e323f140808/src/magezero/model.py),
pinned to commit `11a5974668c3f0f3d19559d7dcbf6e323f140808`. Its source stays in a
separate clone with its upstream MIT license; it is not vendored into Forge.
`bridge.py` verifies the upstream model file's SHA-256 before importing it.

The initial checkpoint trains the target policy head by **imitating the handwritten
pilot**, and the value head by regression to terminal game outcomes. It does not
use upstream XMage adapters, pretrained weights, self-play reinforcement learning
or MCTS. The value head is not yet used to search or choose actions. Only the target
and value heads receive supervised training; unused heads are not gameplay policies.
Beating the teacher is not expected merely from copying its decisions.

The first image uses CPU PyTorch 2.6.0. A GPU is not required. CUDA training is a
separate environment setup; `--device cuda` alone does not enable it in this image.

## Information and action boundaries

Schema 1 hashes observable feature strings into 8,192 buckets using Java's
`String.hashCode` and `floorMod`. Inputs include the acting player's hand, both
players' public battlefield/graveyard, life totals, phase/turn, hand/library sizes,
and indexed candidate-card descriptions. Opponent hand identities and library
contents/order are never encoded. Face-down names are masked. This is a small,
lossy representation: hash collisions, capped counts, missing stack/mana history
and limited option descriptions constrain what it can learn.

Discard actions enumerate all subsets of the required size from Forge's supplied
candidate pool; reanimation actions index Forge's supplied legal targets. There is
no teacher-ranked top-k pruning. Forced choices stay on the existing path. More
than 128 actions or 1,024 feature tokens causes an audited heuristic fallback.
Discard ordering is preserved when collecting the teacher's original choice.

Reanimation is sometimes queried during speculative spell evaluation. These records
are labelled `reanimate-proposal`, **not necessarily executed game actions**. They
are useful imitation examples but must not be treated as RL transitions. A future
RL adapter needs action-commit/next-observation boundaries, broader legal-action
coverage and a policy for hidden information before adding search.

Inference receives only schema, features and action count, not teacher labels.
It must return an in-range choice, finite value and the expected checkpoint hash.
Requests have short timeouts; errors are logged and fall back to the frozen teacher.
After three failures in a game, that game's circuit opens. Never interpret a run
with missing inference or many fallbacks as evidence of learned-policy strength.
The endpoint is restricted to local HTTP (`localhost`, loopback or Docker's
`magezero` service); it is not an authenticated public service.

## Reproducible bootstrap

The desktop entry point `forge.view.ArenaLearningMain` accepts:

```text
SOURCE_RUN ASSETS NEW_OUTPUT collect|evaluate|evaluate-bo3 MATCHES_PER_OPPONENT SEED [MODEL_DIRECTORY ENDPOINT]
```

Use an existing **test-deck comparison** run. Its first deck and entire opponent
pool are copied; the source is never changed. New output must not exist and its
parent must exist. The bootstrap command defaults to two isolated JVM workers;
set JVM property `-Dforge.learning.workers=4` for a larger batch (allowed range 1-8).
`collect` compares Default with v2 and records only v2 choices. `evaluate` compares
Default, v2 and Learned with matched seeds/starting positions. Use `evaluate-bo3`
for BO3 evaluation; collection remains BO1. The normal Arena UI also supports
BO1 and BO3 once the model is installed.

The following commands run in WSL from the repository root, using the default
`./data` profile mount. Adjust paths if `FORGE_DATA_DIR` is customized. Replace
`SOURCE-ID` with the saved comparison run ID. Builds do not restart your current
Forge desktop.

```sh
git clone https://github.com/WillWroble/MageZero.git data/magezero/upstream
git -C data/magezero/upstream checkout 11a5974668c3f0f3d19559d7dcbf6e323f140808
docker compose --profile learning build forge magezero

docker compose run --rm --no-deps --entrypoint java forge '@/opt/forge/java.options' -Djava.awt.headless=true -cp '/opt/forge/lib/*' forge.view.ArenaLearningMain /home/forge/.forge/ai-simulations/SOURCE-ID /opt/forge/forge-gui /home/forge/.forge/ai-simulations/collect-001 collect 24 2026092711

mkdir -p data/profile/ai-models
docker run --rm --network none --cpus 4 --memory 4g \
  -v "$PWD/data/magezero/upstream:/upstream:ro" \
  -v "$PWD/data/profile/ai-simulations/collect-001:/run:ro" \
  -v "$PWD/data/profile/ai-models:/models" \
  forge-magezero:bootstrap train --source /upstream --run /run \
  --output /models/kabal-bootstrap-001 --epochs 3 --batch-size 8

docker compose --profile learning up -d --no-deps magezero
curl --fail http://127.0.0.1:8765/healthz

docker compose run --rm --no-deps --entrypoint java forge '@/opt/forge/java.options' -Djava.awt.headless=true -cp '/opt/forge/lib/*' forge.view.ArenaLearningMain /home/forge/.forge/ai-simulations/SOURCE-ID /opt/forge/forge-gui /home/forge/.forge/ai-simulations/evaluate-001 evaluate 24 2026092712 /home/forge/.forge/ai-models/kabal-bootstrap-001 http://magezero:8765/choose
```

Skip cloning when the pinned checkout already exists. Do not start the inference
service before creating the checkpoint: Docker may create an empty bind-mount
directory, and the trainer deliberately refuses an existing output directory.
Never overwrite checkpoints between or during comparison runs.

Only complete, non-truncated collection episodes are eligible for training.
The trainer requires a completed BO1 run. It reports held-out agreement and loss
separately for discards and reanimation proposals, alongside first-action and
uniform-random baselines. It saves the epoch with the lowest total validation loss,
not necessarily the final epoch; `--patience` defaults to four non-improving epochs.
The manifest records the selected/completed epochs. Never use tournament wins to
select an epoch on the same seeds later reported as a fresh evaluation.
Duplicate teacher episodes are rejected. Train/validation splitting groups all
decisions in an episode by seed and game, rather than randomly splitting decisions.
The manifest records dataset/checkpoint hashes, source revision, sample counts,
seed, PyTorch version and validation teacher agreement. Agreement is **not win rate**.
Use fresh evaluation seeds; repeated use of one validation/opponent suite is not a
reliable generalization test. BO3 splits will need match-level grouping before
extending this BO1 training workflow.

### Larger GPU training batches

For a Docker installation with NVIDIA GPU access, a separate trainer image can
use the [official PyTorch 2.6 CUDA wheels](https://pytorch.org/get-started/previous-versions/#v260).
This does not change the CPU inference image or the running desktop. Verify
`docker run --rm --gpus all --entrypoint nvidia-smi forge-magezero:bootstrap` first.

```sh
docker build --build-arg TORCH_INDEX_URL=https://download.pytorch.org/whl/cu124 -t forge-magezero:training-cuda forge-gui/tools/magezero
docker run --rm --gpus all --network none --cpus 4 --memory 8g \
  -v "$PWD/data/magezero/upstream:/upstream:ro" \
  -v "$PWD/data/magezero/runs/YOUR-COMPLETED-RUN:/run:ro" \
  -v "$PWD/data/profile/ai-models:/models" \
  forge-magezero:training-cuda train --source /upstream --run /run \
  --output /models/kabal-imitation-002 --device cuda --epochs 12 --batch-size 8 --patience 4 --seed 2026092722
```

Collection with 417 matches per opponent across 12 opponents produces 5,004 v2
teacher games plus the same number of Default controls. Use fresh run and training
seeds; replace `YOUR-COMPLETED-RUN` with the completed collection directory and
preserve each checkpoint in a new directory. To compare old/new checkpoint
imitation accuracy on the **same** held-out episodes, run `assess --source /upstream
--run /run --model /models/CHECKPOINT --device cuda` with those mounts. This never
updates weights. Held-out imitation metrics are development measurements, not a
replacement for fresh-seed gameplay evaluation.

## Using the profile in Arena

After rebuilding, save any current desktop work before recreating the Forge
container with `docker compose up -d --no-deps forge`. Start `magezero` separately
as above. Select `OS Reanimator Learned` alongside Default and v2 for the same
KabaL deck and opponent pool. The normal UI requires the bundle at
`USER_DIR/ai-models/kabal-bootstrap-001`; a native desktop defaults to
`http://127.0.0.1:8765/choose`, while Compose sets `FORGE_MAGEZERO_URL` to the service.

Every new learned run freezes `learning.json`, `manifest.json` and `checkpoint.pt`
under its input snapshot. The server must serve that exact checkpoint; mismatches
are counted as fallbacks. A learned entrant without inference configuration fails
startup rather than silently becoming a baseline. Normal non-Arena matches do not
install the model adapter: this profile behaves like v2 there.

`learning/match-*-game-*-*.jsonl` records choices, teacher choices, selected actions,
fallback reasons and terminal outcomes. Audit `modelDecisions`, `fallbacks`, `skips`
and `truncated` in outcome records alongside the ordinary Arena CSV results.
Diagnostic `traces/` logs are separate and must not be fed to the trainer.

Stop the optional service when not using it:

```sh
docker compose --profile learning stop magezero
```

## Verification and next milestone

```sh
mvn -B -pl forge-gui-desktop -am "-Dtest=OldschoolReanimatorAiTest,LearnedGameplayTest,ArenaProcessWorkerTest,ArenaSimulationTest,HeadlessStartupTest" "-Dsurefire.failIfNoSpecifiedTests=false" test
docker compose --profile learning config --quiet
```

Regression coverage checks hidden-information exclusion, unchanged mulligans,
legal choice validation, wrong-model fallback/circuit breaking and deterministic
teacher collection in real BO3 worker games. A Windows-specific test exercises
transient access-denied retries when replacing Arena progress JSON; retries are
bounded to roughly one second and persistent permission errors still fail.
An end-to-end bootstrap additionally
requires a real training run, health check and fresh-seed inference tournament.

Before promoting a candidate, require low fallback rates and a larger paired BO1
and BO3 evaluation. The next engineering milestone is committed-action trajectories
and a fuller Forge action/state adapter; then value-guided search or outcome-driven
policy training can be evaluated against this frozen imitation baseline.
