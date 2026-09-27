package forge.screens.home.arena;

import forge.ai.AiProfileUtil;
import forge.deck.Deck;
import forge.deck.DeckProxy;
import forge.gamemodes.aisimulation.ArenaConfiguration;
import forge.gamemodes.aisimulation.ArenaExport;
import forge.gamemodes.aisimulation.ArenaRunner;
import forge.gamemodes.aisimulation.ArenaStore;
import forge.gui.framework.ICDoc;
import forge.localinstance.properties.ForgeConstants;
import forge.toolbox.FOptionPane;
import forge.util.Localizer;

import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Stream;

public enum CSubmenuAiArena implements ICDoc {
    SINGLETON_INSTANCE;

    private final ExecutorService background = Executors.newSingleThreadExecutor(runnable -> {
        final Thread thread = new Thread(runnable, "ai-arena-storage");
        thread.setDaemon(true);
        return thread;
    });
    private volatile ArenaRunner runner;
    private ArenaStore store;
    private boolean initialized;
    private boolean busy;
    private boolean historyLoading;
    private final Timer refresh = new Timer(500, event -> tick());

    private VSubmenuAiArena view() { return VSubmenuAiArena.SINGLETON_INSTANCE; }
    private String text(final String key) { return Localizer.getInstance().getMessage(key); }
    private boolean running() { return runner != null && runner.isRunning(); }

    @Override public void register() { }

    @Override
    public void initialize() {
        if (initialized) { return; }
        initialized = true;
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            final ArenaRunner current = runner;
            if (current != null) { current.close(); }
        }, "ai-arena-shutdown"));
        refresh.start();
        refreshDecks();
    }

    @Override
    public void update() {
        initialize();
        if (!busy) { refreshDecks(); }
        tick();
    }

    public void refreshDecks() {
        final List<DeckProxy> available = new ArrayList<>();
        DeckProxy.getAllConstructedDecks().forEach(available::add);
        available.sort(Comparator.comparing(DeckProxy::getPath).thenComparing(DeckProxy::getName));
        view().setDecks(available);
    }

    public void start() {
        if (busy || running()) { return; }
        try {
            final List<VSubmenuAiArena.Selection> selections = view().selection();
            final int comparisonProfiles = view().comparisonProfiles();
            ArenaConfiguration.gameCount(selections.size(), view().gamesPerPair(), comparisonProfiles);
            if (view().runName().isBlank()) { throw new IllegalArgumentException(text("lblArenaNameRequired")); }
            final List<ArenaConfiguration.Entrant> entrants = new ArrayList<>();
            final List<Path> deckFiles = new ArrayList<>();
            final Path deckRoot = Path.of(ForgeConstants.DECK_CONSTRUCTED_DIR).toAbsolutePath().normalize();
            for (final VSubmenuAiArena.Selection selection : selections) {
                if (!AiProfileUtil.getAvailableProfiles().contains(selection.profile())) {
                    throw new IllegalArgumentException(text("lblArenaUnknownProfile") + ": " + selection.profile());
                }
                final Deck deck = selection.deck().getDeck();
                entrants.add(new ArenaConfiguration.Entrant(deck.getName(), selection.deck().getPath() + "/" + deck.getName(), selection.profile()));
                final Path file = deckRoot.resolve(selection.deck().getPath().replaceFirst("^/", ""))
                        .resolve(deck.getBestFileName() + ".dck").normalize();
                if (!file.startsWith(deckRoot)) { throw new IOException("Deck must be inside the Constructed deck folder"); }
                deckFiles.add(file);
            }
            final String name = view().runName();
            final int games = view().gamesPerPair();
            final int workers = view().workerCount();
            final int heap = view().workerHeap();
            final int timeout = view().timeoutSeconds();
            final int gamesPerMatch = view().gamesPerMatch();
            work(text("lblArenaPreparing"), () -> {
                final List<Deck> snapshots = new ArrayList<>();
                final Map<Path, Deck> loaded = new HashMap<>();
                for (int index = 0; index < deckFiles.size(); index++) {
                    final Path file = deckFiles.get(index);
                    if (!loaded.containsKey(file)) {
                        loaded.put(file, ArenaFiles.loadDeckSnapshot(file, entrants.get(index).name()));
                    }
                    snapshots.add(loaded.get(file));
                }
                return launch(ArenaFiles.create(name, entrants, snapshots, games, workers, heap, timeout, gamesPerMatch, comparisonProfiles));
            }, this::acceptRun);
        } catch (Exception exception) {
            error(exception);
        }
    }

    private record Loaded(ArenaStore store, ArenaRunner runner) { }

    private Loaded launch(final ArenaStore next) throws IOException {
        final ArenaRunner nextRunner = new ArenaRunner(next,
                number -> new ArenaProcessWorker(next, number, Path.of(ForgeConstants.ASSETS_DIR)));
        nextRunner.start();
        runner = nextRunner;
        return new Loaded(next, nextRunner);
    }

    private void acceptRun(final Loaded loaded) {
        store = loaded.store();
        runner = loaded.runner();
        view().showResults();
        tick();
    }

    public void pause() {
        if (running()) { runner.pause(); }
    }

    public void cancel() {
        if (running() && FOptionPane.showConfirmDialog(text("lblArenaCancelConfirm"), text("lblAiArena"))) {
            runner.cancel();
        }
    }

    public void resume() {
        if (store == null || running() || busy) { return; }
        final ArenaStore selected = store;
        work(text("lblArenaVerifying"), () -> {
            ArenaFiles.verifyResume(selected);
            return launch(selected);
        }, this::acceptRun);
    }

    public void copyRun() {
        if (store == null || running() || busy) { return; }
        final ArenaStore selected = store;
        work(text("lblArenaPreparing"), () -> launch(ArenaFiles.copyRun(selected)), this::acceptRun);
    }

    public void export() {
        if (store == null || busy) { return; }
        final ArenaStore selected = store;
        work(text("lblArenaExporting"), () -> ArenaExport.export(selected), destination -> {
            FOptionPane.showMessageDialog(text("lblArenaExported") + "\n" + destination,
                    text("lblAiArena"), FOptionPane.INFORMATION_ICON);
            tick();
        });
    }

    public void refreshHistory() {
        if (historyLoading) { return; }
        historyLoading = true;
        background.submit(() -> {
            final List<VSubmenuAiArena.History> entries = new ArrayList<>();
            final List<String> failures = new ArrayList<>();
            try {
                if (Files.isDirectory(ArenaFiles.root())) {
                    try (Stream<Path> paths = Files.list(ArenaFiles.root())) {
                        for (final Path path : paths.filter(Files::isDirectory).toList()) {
                            if (!Files.isRegularFile(path.resolve("run.json"))) { continue; }
                            try (ArenaStore saved = new ArenaStore(path)) {
                                entries.add(new VSubmenuAiArena.History(path, saved.configuration(), saved.metadata(), saved.completed()));
                            } catch (IOException exception) {
                                failures.add(exception.getMessage());
                            }
                        }
                    }
                }
                entries.sort(Comparator.comparingLong((VSubmenuAiArena.History entry) -> entry.configuration().createdAt()).reversed());
            } catch (IOException exception) {
                failures.add(exception.getMessage());
            }
            SwingUtilities.invokeLater(() -> {
                historyLoading = false;
                view().setHistory(entries);
                if (!failures.isEmpty()) { error(new IOException(String.join("\n", failures))); }
            });
        });
    }

    public void openHistory() {
        if (busy) { return; }
        if (running()) {
            error(new IllegalStateException(text("lblArenaPauseBeforeOpen")));
            return;
        }
        final VSubmenuAiArena.History selected = view().selectedHistory();
        if (selected == null) { return; }
        work(text("lblArenaLoading"), () -> new ArenaStore(selected.directory()), loaded -> {
            store = loaded;
            runner = null;
            view().showResults();
            tick();
        });
    }

    private <Result> void work(final String message, final Callable<Result> operation, final Consumer<Result> success) {
        busy = true;
        view().showBusy(message);
        view().showControls(running(), true, store);
        background.submit(() -> {
            try {
                final Result result = operation.call();
                SwingUtilities.invokeLater(() -> {
                    busy = false;
                    view().finishBusy();
                    success.accept(result);
                    tick();
                });
            } catch (Exception exception) {
                SwingUtilities.invokeLater(() -> {
                    busy = false;
                    view().finishBusy();
                    error(exception);
                    tick();
                });
            }
        });
    }

    private void tick() {
        if (busy) { return; }
        view().showControls(running(), false, store);
        if (store != null) {
            ArenaRunner.Progress progress;
            if (runner != null) {
                progress = runner.progress();
            } else {
                final ArenaStore.Metadata metadata = store.metadata();
                final ArenaStore.State state = metadata.state() == ArenaStore.State.RUNNING || metadata.state() == ArenaStore.State.PAUSING
                        ? ArenaStore.State.PAUSED : metadata.state();
                progress = new ArenaRunner.Progress(state, store.completed(), store.configuration().totalGames(),
                        metadata.elapsedMillis(), List.of(), metadata.message());
            }
            view().display(store, progress);
        }
    }

    private void error(final Exception exception) {
        FOptionPane.showMessageDialog(exception.getMessage() == null ? exception.toString() : exception.getMessage(),
                text("lblAiArena"), FOptionPane.ERROR_ICON);
    }
}
