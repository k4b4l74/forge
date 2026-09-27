import argparse
import hashlib
import importlib.util
import json
import random
import threading
from http.server import BaseHTTPRequestHandler, ThreadingHTTPServer
from pathlib import Path

import torch
from torch import nn


SCHEMA = 1
BUCKETS = 8192
ACTIONS = 128
MAX_FEATURES = 1024
UPSTREAM_COMMIT = "11a5974668c3f0f3d19559d7dcbf6e323f140808"
UPSTREAM_MODEL_SHA256 = "38d2c8b7815d1a8461ce519d425785b21750e13dabbf4cddffd3e219a5d1abc2"


def digest(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()


def load_architecture(source):
    path = source / "src/magezero/model.py"
    if digest(path) != UPSTREAM_MODEL_SHA256:
        raise ValueError("MageZero model.py differs from the pinned upstream revision")
    specification = importlib.util.spec_from_file_location("magezero_network", path)
    module = importlib.util.module_from_spec(specification)
    specification.loader.exec_module(module)
    return module.NetTransformer(num_embeddings=BUCKETS)


def validate_features(features, actions):
    if not isinstance(features, list) or not 1 <= len(features) <= MAX_FEATURES:
        raise ValueError("Invalid feature count")
    if any(type(feature) is not int or not 0 <= feature < BUCKETS for feature in features):
        raise ValueError("Invalid feature ID")
    if type(actions) is not int or not 2 <= actions <= ACTIONS:
        raise ValueError("Invalid action count")


def dataset(run):
    configuration = json.loads((run / "run.json").read_text())
    status = json.loads((run / "status.json").read_text())
    if status.get("state") != "COMPLETED" or configuration.get("gamesPerMatch") != 1:
        raise ValueError("Training requires a completed BO1 collection run")
    samples = []
    seen = set()
    fingerprint = hashlib.sha256((run / "run.json").read_bytes())
    for path in sorted((run / "learning").glob("*.jsonl")):
        contents = path.read_bytes()
        if len(contents) > 17 * 1024 * 1024:
            raise ValueError("Oversized training episode")
        records = [json.loads(line) for line in contents.splitlines()]
        if not records or records[-1].get("kind") != "outcome":
            continue
        header, outcome = records[0], records[-1]
        if header.get("schema") != SCHEMA or header.get("buckets") != BUCKETS:
            raise ValueError("Incompatible observation schema")
        if header["settings"]["mode"] != "collect" or outcome["truncated"]:
            continue
        decisions = [record for record in records[1:-1]
                     if record.get("kind") == "decision" and record["reason"] == "teacher"]
        if not decisions:
            continue
        group = str(header["seed"]) + ":" + str(header["game"])
        if group in seen:
            raise ValueError("Duplicate episode seed; select one attempt before training")
        seen.add(group)
        fingerprint.update(contents)
        validation = int(hashlib.sha256(group.encode()).hexdigest()[:8], 16) % 5 == 0
        for record in decisions:
            validate_features(record["features"], record["actionCount"])
            if not 0 <= record["teacher"] < record["actionCount"]:
                raise ValueError("Invalid teacher choice")
            reward = outcome["rewards"].get(record["player"])
            if type(reward) is not int or reward not in (-1, 0, 1):
                raise ValueError("Missing or invalid player outcome; recollect with the current Forge collector")
            samples.append({**record, "reward": reward,
                            "validation": validation, "group": group})
    training = [sample for sample in samples if not sample["validation"]]
    validation = [sample for sample in samples if sample["validation"]]
    if not training or not validation:
        raise ValueError("Collect more games: separate training and validation episodes are required")
    return training, validation, fingerprint.hexdigest()


def batch_tensors(samples, device):
    indices = []
    offsets = []
    for sample in samples:
        offsets.append(len(indices))
        indices.extend(sample["features"])
    return torch.tensor(indices, dtype=torch.long, device=device), torch.tensor(offsets, dtype=torch.long, device=device)


def score_batch(model, samples, device):
    indices, offsets = batch_tensors(samples, device)
    _, _, policy, _, value = model(indices, offsets)
    counts = torch.tensor([sample["actionCount"] for sample in samples], device=device)
    policy = policy.masked_fill(torch.arange(ACTIONS, device=device)[None, :] >= counts[:, None], float("-inf"))
    teachers = torch.tensor([sample["teacher"] for sample in samples], device=device)
    rewards = torch.tensor([sample["reward"] for sample in samples], dtype=torch.float32, device=device)
    loss = nn.functional.cross_entropy(policy, teachers) + 0.25 * nn.functional.mse_loss(value, rewards)
    return loss, int((policy.argmax(dim=1) == teachers).sum().item())


def validation_metrics(model, samples, device, batch_size):
    model.eval()
    metrics = {}
    with torch.inference_mode():
        for kind in sorted({sample["decision"] for sample in samples}):
            selected = [sample for sample in samples if sample["decision"] == kind]
            correct = 0
            total_loss = 0.0
            for start in range(0, len(selected), batch_size):
                batch = selected[start:start + batch_size]
                loss, count = score_batch(model, batch, device)
                if not torch.isfinite(loss):
                    raise ValueError("Non-finite validation loss")
                correct += count
                total_loss += float(loss.item()) * len(batch)
            metrics[kind] = {"samples": len(selected), "correct": correct,
                             "teacherAgreement": correct / len(selected), "loss": total_loss / len(selected),
                             "firstActionAgreement": sum(sample["teacher"] == 0 for sample in selected) / len(selected),
                             "uniformRandomAgreement": sum(1 / sample["actionCount"] for sample in selected) / len(selected)}
    return {"validationTeacherAgreement": sum(metric["correct"] for metric in metrics.values()) / len(samples),
            "validationLoss": sum(metric["loss"] * metric["samples"] for metric in metrics.values()) / len(samples),
            "byDecision": metrics}


def train(arguments):
    if arguments.output.exists():
        raise ValueError("Output already exists; checkpoints are immutable")
    training, validation, data_sha = dataset(arguments.run)
    torch.manual_seed(arguments.seed)
    randomizer = random.Random(arguments.seed)
    model = load_architecture(arguments.source).to(arguments.device)
    model.input_dropout = 0.0
    optimizer = torch.optim.AdamW(model.parameters(), lr=0.0001)
    history = []
    best_state = None
    best_loss = float("inf")
    selected_epoch = 0
    print(json.dumps({"trainingSamples": len(training), "validationSamples": len(validation),
                      "parameters": sum(parameter.numel() for parameter in model.parameters())}), flush=True)
    for epoch in range(arguments.epochs):
        randomizer.shuffle(training)
        model.train()
        total_loss = 0.0
        for start in range(0, len(training), arguments.batch_size):
            samples = training[start:start + arguments.batch_size]
            optimizer.zero_grad(set_to_none=True)
            loss, _ = score_batch(model, samples, arguments.device)
            if not torch.isfinite(loss):
                raise ValueError("Non-finite training loss")
            loss.backward()
            nn.utils.clip_grad_norm_(model.parameters(), 1.0)
            optimizer.step()
            total_loss += float(loss.item()) * len(samples)
        result = {"epoch": epoch + 1, "trainingLoss": total_loss / len(training),
                  **validation_metrics(model, validation, arguments.device, arguments.batch_size)}
        history.append(result)
        print(json.dumps(result), flush=True)
        if result["validationLoss"] < best_loss:
            best_loss = result["validationLoss"]
            selected_epoch = epoch + 1
            best_state = {name: parameter.detach().cpu().clone() for name, parameter in model.state_dict().items()}
        elif epoch + 1 - selected_epoch >= arguments.patience:
            break
    arguments.output.mkdir(parents=True, exist_ok=False)
    model.to("cpu")
    checkpoint = arguments.output / "checkpoint.pt"
    torch.save(best_state, checkpoint)
    configuration = json.loads((arguments.run / "run.json").read_text())
    manifest = {"schema": SCHEMA, "buckets": BUCKETS, "actions": ACTIONS,
                "upstreamCommit": UPSTREAM_COMMIT, "upstreamModelSha256": UPSTREAM_MODEL_SHA256,
                "modelSha256": digest(checkpoint), "datasetSha256": data_sha, "seed": arguments.seed,
                "trainingSamples": len(training), "validationSamples": len(validation), "history": history,
                "trainingEpisodes": len({sample["group"] for sample in training}),
                "validationEpisodes": len({sample["group"] for sample in validation}),
                "forgeRunId": configuration["id"], "forgeFingerprint": configuration["fingerprint"],
                "epochs": arguments.epochs, "batchSize": arguments.batch_size, "learningRate": 0.0001,
                "completedEpochs": len(history), "selectedEpoch": selected_epoch,
                "selectionMetric": "minimum validationLoss", "patience": arguments.patience,
                "inputDropout": 0.0, "device": arguments.device, "threads": arguments.threads,
                "method": "Heuristic imitation plus terminal-outcome value regression; NOT RL or MCTS",
                "torchVersion": str(torch.__version__), "trainedDecisions": ["discard-choice", "reanimate-proposal"]}
    (arguments.output / "manifest.json").write_text(json.dumps(manifest, indent=2) + "\n")
    print("Saved " + str(arguments.output), flush=True)


def load_checkpoint(arguments):
    manifest = json.loads((arguments.model / "manifest.json").read_text())
    checkpoint = arguments.model / "checkpoint.pt"
    if (manifest["schema"] != SCHEMA or manifest["buckets"] != BUCKETS or manifest["actions"] != ACTIONS
            or manifest["modelSha256"] != digest(checkpoint)):
        raise ValueError("Checkpoint manifest mismatch")
    if manifest["upstreamModelSha256"] != UPSTREAM_MODEL_SHA256:
        raise ValueError("Checkpoint architecture mismatch")
    model = load_architecture(arguments.source).to(arguments.device)
    model.load_state_dict(torch.load(checkpoint, map_location=arguments.device, weights_only=True))
    model.eval()
    return model, manifest


def assess(arguments):
    _, validation, data_sha = dataset(arguments.run)
    model, manifest = load_checkpoint(arguments)
    print(json.dumps({"modelSha256": manifest["modelSha256"], "datasetSha256": data_sha,
                      **validation_metrics(model, validation, arguments.device, arguments.batch_size)}), flush=True)


def serve(arguments):
    model, manifest = load_checkpoint(arguments)
    lock = threading.Lock()
    with torch.inference_mode():
        model(torch.tensor([0, 1], device=arguments.device), torch.tensor([0], device=arguments.device))

    class Handler(BaseHTTPRequestHandler):
        def reply(self, status, body):
            encoded = json.dumps(body).encode()
            self.send_response(status)
            self.send_header("Content-Type", "application/json")
            self.send_header("Content-Length", str(len(encoded)))
            self.end_headers()
            self.wfile.write(encoded)

        def do_GET(self):
            self.reply(200 if self.path == "/healthz" else 404,
                       {"schema": SCHEMA, "modelSha256": manifest["modelSha256"]})

        def do_POST(self):
            if self.path != "/choose":
                self.reply(404, {"error": "Unknown endpoint"})
                return
            try:
                length = int(self.headers.get("Content-Length", "0"))
                if not 0 < length <= 65536:
                    raise ValueError("Invalid request length")
                self.connection.settimeout(10)
                request = json.loads(self.rfile.read(length))
                if not isinstance(request, dict) or request.get("schema") != SCHEMA:
                    raise ValueError("Schema mismatch")
                validate_features(request["features"], request["actionCount"])
                with lock, torch.inference_mode():
                    indices = torch.tensor(request["features"], dtype=torch.long, device=arguments.device)
                    offsets = torch.tensor([0], dtype=torch.long, device=arguments.device)
                    _, _, policy, _, value = model(indices, offsets)
                    if not torch.isfinite(policy).all() or not torch.isfinite(value).all():
                        raise ValueError("Non-finite prediction")
                    action = int(policy[0, :request["actionCount"]].argmax().item())
                self.reply(200, {"schema": SCHEMA, "modelSha256": manifest["modelSha256"],
                                 "action": action, "value": float(value.item())})
            except (KeyError, ValueError, TypeError, TimeoutError) as exception:
                self.reply(400, {"error": str(exception)})

        def log_message(self, format_string, *values):
            return

    print(json.dumps({"listening": arguments.port, "modelSha256": manifest["modelSha256"]}), flush=True)
    ThreadingHTTPServer((arguments.host, arguments.port), Handler).serve_forever()


def main():
    parser = argparse.ArgumentParser()
    commands = parser.add_subparsers(dest="command", required=True)
    training = commands.add_parser("train")
    training.add_argument("--run", type=Path, required=True)
    training.add_argument("--output", type=Path, required=True)
    training.add_argument("--epochs", type=int, default=3)
    training.add_argument("--batch-size", type=int, default=8)
    training.add_argument("--seed", type=int, default=20260927)
    training.add_argument("--patience", type=int, default=4)
    assessment = commands.add_parser("assess")
    assessment.add_argument("--run", type=Path, required=True)
    assessment.add_argument("--model", type=Path, required=True)
    assessment.add_argument("--batch-size", type=int, default=8)
    server = commands.add_parser("serve")
    server.add_argument("--model", type=Path, required=True)
    server.add_argument("--host", default="127.0.0.1")
    server.add_argument("--port", type=int, default=8765)
    for command in (training, assessment, server):
        command.add_argument("--source", type=Path, required=True)
        command.add_argument("--device", choices=["cpu", "cuda"], default="cpu")
        command.add_argument("--threads", type=int, default=2)
    arguments = parser.parse_args()
    if not 1 <= arguments.threads <= 32:
        parser.error("Use 1-32 threads")
    if arguments.command in ("train", "assess") and not 1 <= arguments.batch_size <= 128:
        parser.error("Invalid batch size")
    if arguments.command == "train" and not (1 <= arguments.epochs <= 100 and 1 <= arguments.patience <= 100):
        parser.error("Invalid training budget")
    torch.set_num_threads(arguments.threads)
    torch.set_num_interop_threads(1)
    if arguments.command == "train":
        train(arguments)
    elif arguments.command == "assess":
        assess(arguments)
    else:
        serve(arguments)


if __name__ == "__main__":
    main()
