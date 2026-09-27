package forge.gamemodes.aisimulation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public final class ArenaRunner implements AutoCloseable {
    public record Progress(ArenaStore.State state, int finished, int total, long elapsedMillis,
                           List<ArenaSchedule.Task> active, String message) { }

    private record Completion(int workerNumber, ArenaResult result) { }

    private final ArenaStore store;
    private final ArenaWorker.Factory factory;
    private final Map<Integer, ArenaWorker> workers = new ConcurrentHashMap<>();
    private final Map<Integer, ArenaSchedule.Task> active = new ConcurrentHashMap<>();
    private volatile boolean pause;
    private volatile boolean cancel;
    private volatile boolean running;
    private volatile long startedNanos;
    private volatile long previousElapsed;
    private volatile Progress lastProgress;
    private Thread coordinator;

    public ArenaRunner(final ArenaStore store, final ArenaWorker.Factory factory) {
        this.store = store;
        this.factory = factory;
        final ArenaStore.Metadata metadata = store.metadata();
        lastProgress = new Progress(metadata.state(), store.completed(), store.configuration().totalGames(),
                metadata.elapsedMillis(), List.of(), metadata.message());
    }

    public synchronized void start() throws IOException {
        if (running) {
            throw new IOException("A tournament is already running");
        }
        store.claim();
        pause = false;
        cancel = false;
        previousElapsed = store.metadata().elapsedMillis();
        startedNanos = System.nanoTime();
        running = true;
        coordinator = new Thread(this::execute, "ai-arena-coordinator");
        coordinator.setDaemon(true);
        coordinator.start();
    }

    public boolean isRunning() { return running; }
    public void pause() { pause = true; }
    public void cancel() {
        cancel = true;
        workers.values().forEach(ArenaWorker::close);
    }

    public Progress progress() {
        final Progress snapshot = lastProgress;
        return running ? new Progress(pause ? ArenaStore.State.PAUSING : snapshot.state(), store.completed(),
                store.configuration().totalGames(), elapsed(), new ArrayList<>(active.values()), snapshot.message()) : snapshot;
    }

    private long elapsed() {
        return previousElapsed + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startedNanos);
    }

    private void execute() {
        final ExecutorService executor = Executors.newFixedThreadPool(store.configuration().workers(), runnable -> {
            final Thread thread = new Thread(runnable, "ai-arena-worker-bridge");
            thread.setDaemon(true);
            return thread;
        });
        final ExecutorCompletionService<Completion> completions = new ExecutorCompletionService<>(executor);
        ArenaStore.State finalState = ArenaStore.State.FAILED;
        String message = "";
        try {
            publish(ArenaStore.State.RUNNING, "");
            int nextGame = 0;
            final List<Integer> available = new ArrayList<>();
            for (int workerNumber = 0; workerNumber < store.configuration().workers(); workerNumber++) {
                available.add(workerNumber);
            }
            while (!cancel) {
                while (!pause && !cancel && !available.isEmpty()) {
                    while (nextGame < store.configuration().totalGames() && store.contains(nextGame)) {
                        nextGame++;
                    }
                    if (nextGame == store.configuration().totalGames()) { break; }
                    final int workerNumber = available.remove(available.size() - 1);
                    final ArenaSchedule.Task task = ArenaSchedule.task(store.configuration(), nextGame++);
                    active.put(workerNumber, task);
                    completions.submit(() -> new Completion(workerNumber, play(workerNumber, task)));
                }
                if (active.isEmpty()) { break; }
                final Future<Completion> completed = completions.poll(100, TimeUnit.MILLISECONDS);
                if (completed == null) { continue; }
                final Completion completion = completed.get();
                if (cancel) { break; }
                store.append(completion.result());
                active.remove(completion.workerNumber());
                available.add(completion.workerNumber());
                publish(pause ? ArenaStore.State.PAUSING : ArenaStore.State.RUNNING, "");
            }
            finalState = cancel ? ArenaStore.State.CANCELLED
                    : store.completed() == store.configuration().totalGames() ? ArenaStore.State.COMPLETED : ArenaStore.State.PAUSED;
        } catch (Exception exception) {
            message = exception.getCause() == null ? exception.toString() : exception.getCause().toString();
            if (cancel) { finalState = ArenaStore.State.CANCELLED; }
        } finally {
            cancel = true;
            workers.values().forEach(ArenaWorker::close);
            executor.shutdownNow();
            try {
                executor.awaitTermination(10, TimeUnit.SECONDS);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            }
            workers.values().forEach(ArenaWorker::close);
            workers.clear();
            active.clear();
            try {
                publish(finalState, message);
            } catch (IOException exception) {
                lastProgress = new Progress(ArenaStore.State.FAILED, store.completed(), store.configuration().totalGames(),
                        elapsed(), List.of(), exception.toString());
            }
            try { store.close(); } catch (IOException exception) { exception.printStackTrace(); }
            running = false;
        }
    }

    private ArenaResult play(final int workerNumber, final ArenaSchedule.Task task) throws Exception {
        ArenaWorker worker = workers.get(workerNumber);
        if (worker == null) {
            worker = factory.create(workerNumber);
            workers.put(workerNumber, worker);
        }
        if (cancel) {
            worker.close();
            throw new IOException("Tournament stopped");
        }
        final ArenaResult result = worker.play(task);
        if (result.gameId() != task.gameId()) {
            throw new IOException("Worker returned the wrong game ID");
        }
        result.validate(store.configuration());
        if (!result.isValidGame()) {
            worker.close();
            workers.remove(workerNumber, worker);
        }
        return result;
    }

    private void publish(final ArenaStore.State state, final String message) throws IOException {
        store.update(state, elapsed(), message);
        lastProgress = new Progress(state, store.completed(), store.configuration().totalGames(), elapsed(),
                new ArrayList<>(active.values()), message);
    }

    @Override
    public void close() {
        cancel();
        final Thread thread = coordinator;
        if (thread != null && thread != Thread.currentThread()) {
            try { thread.join(5000); } catch (InterruptedException exception) { Thread.currentThread().interrupt(); }
        }
    }
}
