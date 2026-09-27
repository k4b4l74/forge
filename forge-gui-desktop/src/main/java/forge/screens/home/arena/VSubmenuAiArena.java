package forge.screens.home.arena;

import forge.ai.AiProfileUtil;
import forge.deck.DeckProxy;
import forge.gamemodes.aisimulation.ArenaConfiguration;
import forge.gamemodes.aisimulation.ArenaResult;
import forge.gamemodes.aisimulation.ArenaRunner;
import forge.gamemodes.aisimulation.ArenaSchedule;
import forge.gamemodes.aisimulation.ArenaStandings;
import forge.gamemodes.aisimulation.ArenaStore;
import forge.gui.framework.DragCell;
import forge.gui.framework.DragTab;
import forge.gui.framework.EDocID;
import forge.screens.home.EMenuGroup;
import forge.screens.home.IVSubmenu;
import forge.screens.home.VHomeUI;
import forge.toolbox.FButton;
import forge.toolbox.FComboBox;
import forge.toolbox.FLabel;
import forge.toolbox.FProgressBar;
import forge.toolbox.FScrollPane;
import forge.toolbox.FSkin;
import forge.toolbox.FSpinner;
import forge.toolbox.FTextField;
import forge.util.Localizer;
import net.miginfocom.swing.MigLayout;

import javax.swing.DefaultCellEditor;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.nio.file.Path;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public enum VSubmenuAiArena implements IVSubmenu<CSubmenuAiArena> {
    SINGLETON_INSTANCE;

    public record Selection(DeckProxy deck, String profile) { }
    public record History(Path directory, ArenaConfiguration configuration, ArenaStore.Metadata metadata, int completed) { }

    private final Localizer localizer = Localizer.getInstance();
    private final DragTab tab = new DragTab(text("lblAiArena"));
    private DragCell parentCell;
    private final JPanel panel = new JPanel(new BorderLayout(8, 8));
    private final JTabbedPane tabs = new JTabbedPane();
    private final FTextField name = new FTextField.Builder().text("AI Arena").build();
    private final FTextField search = new FTextField.Builder().ghostText(text("lblArenaSearch")).build();
    private final FSpinner games = new FSpinner.Builder().minValue(1).maxValue(1000000).initialValue(100).build();
    private final FSpinner workers = new FSpinner.Builder().minValue(1).maxValue(64)
            .initialValue(Math.min(2, Runtime.getRuntime().availableProcessors())).build();
    private final FSpinner heap = new FSpinner.Builder().minValue(256).maxValue(32768).initialValue(2048).build();
    private final FSpinner timeout = new FSpinner.Builder().minValue(1).maxValue(86400).initialValue(120).build();
    private final List<DeckProxy> decks = new ArrayList<>();
    private final List<History> history = new ArrayList<>();
    private final FComboBox<String> defaultProfile = new FComboBox<>();
    private final FComboBox<String> rowProfile = new FComboBox<>();
    private final FComboBox<String> matchFormat = new FComboBox<>();
    private final DefaultTableModel deckModel = new DefaultTableModel(new Object[]{text("lblArenaSelected"),
            text("lblArenaDeck"), text("lblArenaFolder"), text("lblArenaProfile")}, 0) {
        @Override
        public boolean isCellEditable(final int row, final int column) { return column == 0 || column == 3; }
        @Override
        public Class<?> getColumnClass(final int column) { return column == 0 ? Boolean.class : String.class; }
    };
    private final JTable deckTable = table(deckModel);
    private final FLabel counts = label("");
    private final FLabel status = label(text("lblArenaReady"));
    private final FLabel activity = label("");
    private final FProgressBar progress = new FProgressBar();
    private final FButton start = button("lblArenaStart", () -> controller().start());
    private final FButton pause = button("lblArenaPause", () -> controller().pause());
    private final FButton resume = button("lblArenaResume", () -> controller().resume());
    private final FButton cancel = button("lblArenaCancel", () -> controller().cancel());
    private final FButton export = button("lblArenaExport", () -> controller().export());
    private final FButton copy = button("lblArenaNewFromSetup", () -> controller().copyRun());
    private final DefaultTableModel standingsModel = readOnly("lblArenaRank", "lblArenaDeck", "lblArenaProfile", "lblArenaValid",
            "lblArenaWins", "lblArenaLosses", "lblArenaDraws", "lblArenaScore", "lblArenaWinRate", "lblArenaTimeouts",
            "lblArenaErrors", "lblArenaTurns", "lblArenaSeconds");
    private final DefaultTableModel matrixModel = readOnly("lblArenaDeck");
    private final DefaultTableModel failuresModel = readOnly("lblArenaGame", "lblArenaPairing", "lblArenaOutcome", "lblArenaDetail");
    private final DefaultTableModel matchesModel = readOnly("lblArenaGame", "lblArenaPairing", "lblArenaWinner", "lblArenaDetail");
    private final DefaultTableModel historyModel = readOnly("lblArenaCreated", "lblArenaName", "lblArenaState", "lblArenaFinished", "lblArenaTotal");
    private final JTable historyTable = table(historyModel);
    private final JTable matrix = table(matrixModel);
    private int lastFinished = -1;
    private Path displayedRun;

    VSubmenuAiArena() {
        panel.setOpaque(false);
        tabs.setForeground(FSkin.getColor(FSkin.Colors.CLR_TEXT).getColor());
        tabs.setBackground(FSkin.getColor(FSkin.Colors.CLR_THEME2).getColor());
        tabs.addTab(text("lblArenaSetup"), setup());
        tabs.addTab(text("lblArenaResults"), results());
        tabs.addTab(text("lblArenaHistory"), savedRuns());
        tabs.addChangeListener(event -> {
            if (tabs.getSelectedIndex() == 2) { controller().refreshHistory(); }
        });
        panel.add(label(text("lblAiArena")), BorderLayout.NORTH);
        panel.add(tabs, BorderLayout.CENTER);
        deckModel.addTableModelListener(event -> updateCounts());
        games.addChangeListener(event -> updateCounts());
        matchFormat.addActionListener(event -> updateCounts());
        search.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(final DocumentEvent event) { filter(); }
            @Override public void removeUpdate(final DocumentEvent event) { filter(); }
            @Override public void changedUpdate(final DocumentEvent event) { filter(); }
        });
        progress.setShowETA(false);
        progress.setShowCount(false);
        progress.setDescription("");
        showControls(false, false, null);
    }

    private JPanel setup() {
        final JPanel setup = transparent("insets 10, fill, wrap 1");
        final JPanel heading = transparent("insets 0, fillx");
        heading.add(label(text("lblArenaName")));
        heading.add(name, "growx, pushx, w 160:240:");
        heading.add(defaultProfile, "w 130!");
        heading.add(button("lblArenaApplyProfile", () -> {
            for (int row = 0; row < deckModel.getRowCount(); row++) { deckModel.setValueAt(defaultProfile.getSelectedItem(), row, 3); }
        }));
        setup.add(heading, "growx");
        final JPanel filters = transparent("insets 0, fillx");
        filters.add(search, "growx, pushx, w 100:200:");
        filters.add(button("lblArenaRefresh", () -> controller().refreshDecks()));
        filters.add(button("lblArenaSelectAll", () -> setSelected(true)));
        filters.add(button("lblArenaClear", () -> setSelected(false)));
        setup.add(filters, "growx");
        deckTable.getColumnModel().getColumn(0).setMaxWidth(70);
        deckTable.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(rowProfile));
        setup.add(new FScrollPane(deckTable, true), "grow, push, h 90:180:");
        final JPanel settings = transparent("insets 0, fillx, wrap 4");
        matchFormat.addItem("BO1");
        matchFormat.addItem("BO3");
        settings.add(label(text("lblArenaMatchFormat"))); settings.add(matchFormat, "w 85!, growx, wrap");
        settings.add(label(text("lblArenaGamesPerPair"))); settings.add(games, "w 85!, growx");
        settings.add(label(text("lblArenaWorkers"))); settings.add(workers, "w 65!, growx");
        settings.add(label(text("lblArenaWorkerHeap"))); settings.add(heap, "w 85!, growx");
        settings.add(label(text("lblArenaTimeout"))); settings.add(timeout, "w 65!, growx");
        setup.add(settings, "growx");
        setup.add(label(text("lblArenaRules")), "growx");
        setup.add(counts, "growx");
        setup.add(start, "align right, h 30!");
        return setup;
    }

    private JPanel results() {
        final JPanel result = transparent("insets 10, fill, wrap 1");
        result.add(status, "growx");
        result.add(progress, "growx, h 22!");
        result.add(activity, "growx");
        final JPanel buttons = transparent("insets 0");
        for (final FButton button : List.of(pause, resume, cancel, copy, export)) { buttons.add(button); }
        result.add(buttons, "growx");
        final JTabbedPane reports = new JTabbedPane();
        reports.setForeground(FSkin.getColor(FSkin.Colors.CLR_TEXT).getColor());
        reports.setBackground(FSkin.getColor(FSkin.Colors.CLR_THEME2).getColor());
        final JTable standings = table(standingsModel);
        standings.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        reports.addTab(text("lblArenaStandings"), new FScrollPane(standings, true));
        matrix.setAutoCreateRowSorter(false);
        matrix.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        final DefaultTableCellRenderer matrixRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean selected,
                                                            final boolean focus, final int row, final int column) {
                super.getTableCellRendererComponent(table, value, selected, focus, row, column);
                setBackground(FSkin.getColor(FSkin.Colors.CLR_THEME2).getColor());
                setForeground(FSkin.getColor(FSkin.Colors.CLR_TEXT).getColor());
                if (value instanceof Double score) {
                    setText(String.format("%.1f%%", score * 100));
                    setBackground(FSkin.getColor(score >= 0.5 ? FSkin.Colors.CLR_ACTIVE : FSkin.Colors.CLR_INACTIVE).getColor());
                }
                return this;
            }
        };
        matrix.setDefaultRenderer(Object.class, matrixRenderer);
        matrix.setDefaultRenderer(Double.class, matrixRenderer);
        reports.addTab(text("lblArenaMatchups"), new FScrollPane(matrix, true));
        reports.addTab(text("lblArenaMatches"), new FScrollPane(table(matchesModel), true));
        reports.addTab(text("lblArenaFailures"), new FScrollPane(table(failuresModel), true));
        result.add(reports, "grow, push, h 100:200:");
        result.add(label(text("lblArenaScoring")), "growx");
        return result;
    }

    private JPanel savedRuns() {
        final JPanel saved = transparent("insets 10, fill, wrap 1");
        final JPanel buttons = transparent("insets 0");
        buttons.add(button("lblArenaRefresh", () -> controller().refreshHistory()));
        buttons.add(button("lblArenaOpen", () -> controller().openHistory()));
        saved.add(buttons, "growx");
        saved.add(new FScrollPane(historyTable, true), "grow, push, h 100:300:");
        return saved;
    }

    public void setDecks(final List<DeckProxy> available) {
        final Map<String, Object[]> previous = new HashMap<>();
        for (int row = 0; row < decks.size(); row++) {
            previous.put(decks.get(row).getUniqueKey(), new Object[]{deckModel.getValueAt(row, 0), deckModel.getValueAt(row, 3)});
        }
        final List<String> profiles = AiProfileUtil.getAvailableProfiles().stream().sorted().toList();
        defaultProfile.removeAllItems();
        rowProfile.removeAllItems();
        for (final String profile : profiles) { defaultProfile.addItem(profile); rowProfile.addItem(profile); }
        defaultProfile.setSelectedItem("Default");
        decks.clear();
        deckModel.setRowCount(0);
        for (final DeckProxy deck : available) {
            decks.add(deck);
            final Object[] old = previous.get(deck.getUniqueKey());
            deckModel.addRow(new Object[]{old != null && Boolean.TRUE.equals(old[0]), deck.getName(), deck.getPath(),
                    old == null || !profiles.contains(old[1]) ? "Default" : old[1]});
        }
        updateCounts();
    }

    private void setSelected(final boolean selected) {
        for (int row = 0; row < deckTable.getRowCount(); row++) {
            deckModel.setValueAt(selected, deckTable.convertRowIndexToModel(row), 0);
        }
    }

    @SuppressWarnings("unchecked")
    private void filter() {
        final TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) deckTable.getRowSorter();
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(search.getText()), 1, 2));
    }

    public List<Selection> selection() {
        if (deckTable.isEditing()) { deckTable.getCellEditor().stopCellEditing(); }
        final List<Selection> selection = new ArrayList<>();
        for (int row = 0; row < decks.size(); row++) {
            if (Boolean.TRUE.equals(deckModel.getValueAt(row, 0))) {
                selection.add(new Selection(decks.get(row), (String) deckModel.getValueAt(row, 3)));
            }
        }
        return selection;
    }

    private void updateCounts() {
        int count = 0;
        for (int row = 0; row < deckModel.getRowCount(); row++) {
            if (Boolean.TRUE.equals(deckModel.getValueAt(row, 0))) { count++; }
        }
        final long pairings = (long) count * (count - 1) / 2;
        counts.setText(localizer.getMessage(gamesPerMatch() == 3 ? "lblArenaCountsBO3" : "lblArenaCounts", Integer.toString(count), Long.toString(pairings),
                Long.toString(pairings * gamesPerPair()), Long.toString(Math.max(0, count - 1L) * gamesPerPair())));
    }

    public String runName() { return name.getText().trim(); }
    public int gamesPerPair() { return (Integer) games.getValue(); }
    public int gamesPerMatch() { return "BO3".equals(matchFormat.getSelectedItem()) ? 3 : 1; }
    public int workerCount() { return (Integer) workers.getValue(); }
    public int workerHeap() { return (Integer) heap.getValue(); }
    public int timeoutSeconds() { return (Integer) timeout.getValue(); }
    public void showResults() { tabs.setSelectedIndex(1); }

    public void showBusy(final String message) {
        showResults();
        status.setText(message);
        progress.setIndeterminate(true);
    }

    public void finishBusy() {
        progress.setIndeterminate(false);
        status.setText(text("lblArenaReady"));
    }

    public void showControls(final boolean running, final boolean busy, final ArenaStore store) {
        start.setEnabled(!running && !busy);
        pause.setEnabled(running && !busy);
        cancel.setEnabled(running && !busy);
        resume.setEnabled(!running && !busy && store != null && store.completed() < store.configuration().totalGames());
        copy.setEnabled(!running && !busy && store != null);
        export.setEnabled(!busy && store != null);
    }

    public void display(final ArenaStore store, final ArenaRunner.Progress snapshot) {
        progress.setIndeterminate(false);
        progress.setMaximum(snapshot.total());
        progress.setValue(snapshot.finished());
        progress.setString(snapshot.finished() + " / " + snapshot.total());
        final double perMinute = snapshot.elapsedMillis() == 0 ? 0 : snapshot.finished() * 60000.0 / snapshot.elapsedMillis();
        final String eta = perMinute <= 0 ? "—" : duration((long) ((snapshot.total() - snapshot.finished()) * 60000.0 / perMinute));
        status.setText(localizer.getMessage("lblArenaProgress", store.configuration().name() + " (BO"
                + store.configuration().gamesPerMatch() + ")", state(snapshot.state()),
                duration(snapshot.elapsedMillis()), String.format("%.1f", perMinute), eta));
        if (snapshot.message() != null && !snapshot.message().isBlank()) {
            activity.setText(snapshot.message());
            activity.setToolTipText(snapshot.message());
        } else {
            final List<String> pairings = new ArrayList<>();
            for (final ArenaSchedule.Task task : snapshot.active()) {
                pairings.add(store.configuration().entrants().get(task.left()).name() + " / "
                        + store.configuration().entrants().get(task.right()).name());
            }
            activity.setText(pairings.isEmpty() ? text("lblArenaNoActiveGames") : String.join("; ", pairings));
            activity.setToolTipText(activity.getText());
        }
        if (store.directory().equals(displayedRun) && lastFinished == snapshot.finished()) { return; }
        displayedRun = store.directory();
        lastFinished = snapshot.finished();
        final List<ArenaResult> completed = store.results();
        final ArenaStandings standings = new ArenaStandings(store.configuration(), completed);
        standingsModel.setRowCount(0);
        int rank = 0;
        for (final int entrant : standings.ranking()) {
            final ArenaConfiguration.Entrant entry = store.configuration().entrants().get(entrant);
            final ArenaStandings.Score score = standings.total(entrant);
            standingsModel.addRow(new Object[]{++rank, entry.source(), entry.profile(), score.validGames(), score.wins, score.losses,
                    score.draws, decimal(score.score() * 100), decimal(score.winRate() * 100), score.timeouts, score.errors,
                    decimal(score.averageTurns()), decimal(score.averageSeconds())});
        }
        final List<String> columns = new ArrayList<>(List.of(text("lblArenaDeck")));
        for (final ArenaConfiguration.Entrant entry : store.configuration().entrants()) { columns.add(entry.source()); }
        matrixModel.setColumnIdentifiers(columns.toArray());
        matrixModel.setRowCount(0);
        for (int left = 0; left < store.configuration().entrants().size(); left++) {
            final Object[] row = new Object[columns.size()];
            row[0] = store.configuration().entrants().get(left).source();
            for (int right = 0; right < columns.size() - 1; right++) {
                final double score = standings.pairing(left, right).score();
                row[right + 1] = left == right || Double.isNaN(score) ? "—" : score;
            }
            matrixModel.addRow(row);
        }
        matrix.getColumnModel().getColumn(0).setPreferredWidth(160);
        failuresModel.setRowCount(0);
        matchesModel.setRowCount(0);
        for (final ArenaResult result : completed) {
            final ArenaSchedule.Task pairing = ArenaSchedule.task(store.configuration(), result.gameId());
            matchesModel.addRow(new Object[]{result.gameId(), store.configuration().entrants().get(pairing.left()).source()
                    + " / " + store.configuration().entrants().get(pairing.right()).source(), result.winner() < 0 ? "-"
                    : store.configuration().entrants().get(result.winner()).source(), result.detail()});
            if (result.isValidGame()) { continue; }
            final ArenaSchedule.Task task = ArenaSchedule.task(store.configuration(), result.gameId());
            failuresModel.addRow(new Object[]{result.gameId(), store.configuration().entrants().get(task.left()).source() + " / "
                    + store.configuration().entrants().get(task.right()).source(), text("lblArena" + result.outcome().name()), result.detail()});
        }
    }

    public void setHistory(final List<History> runs) {
        history.clear();
        history.addAll(runs);
        historyModel.setRowCount(0);
        for (final History run : history) {
            final ArenaStore.State saved = run.metadata().state();
            historyModel.addRow(new Object[]{DateFormat.getDateTimeInstance().format(new Date(run.configuration().createdAt())),
                    run.configuration().name() + " (BO" + run.configuration().gamesPerMatch() + ")", saved == ArenaStore.State.RUNNING || saved == ArenaStore.State.PAUSING
                    ? text("lblArenaInterrupted") : state(saved), run.completed(), run.configuration().totalGames()});
        }
    }

    public History selectedHistory() {
        final int selected = historyTable.getSelectedRow();
        return selected < 0 ? null : history.get(historyTable.convertRowIndexToModel(selected));
    }

    private static Object decimal(final double value) { return Double.isFinite(value) ? Math.round(value * 10) / 10.0 : null; }
    private String state(final ArenaStore.State state) { return text("lblArenaState" + state.name()); }
    private static String duration(final long millis) {
        final long seconds = millis / 1000;
        return String.format("%02d:%02d:%02d", seconds / 3600, seconds / 60 % 60, seconds % 60);
    }

    private DefaultTableModel readOnly(final String... keys) {
        final List<String> columns = new ArrayList<>();
        for (final String key : keys) { columns.add(text(key)); }
        return new DefaultTableModel(columns.toArray(), 0) {
            @Override public boolean isCellEditable(final int row, final int column) { return false; }
            @Override public Class<?> getColumnClass(final int column) {
                for (int row = 0; row < getRowCount(); row++) {
                    final Object value = getValueAt(row, column);
                    if (value != null) { return value.getClass(); }
                }
                return Object.class;
            }
        };
    }

    private static JTable table(final DefaultTableModel model) {
        final JTable table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        table.setForeground(FSkin.getColor(FSkin.Colors.CLR_TEXT).getColor());
        table.setBackground(FSkin.getColor(FSkin.Colors.CLR_THEME2).getColor());
        table.setSelectionForeground(FSkin.getColor(FSkin.Colors.CLR_TEXT).getColor());
        table.setSelectionBackground(FSkin.getColor(FSkin.Colors.CLR_ACTIVE).getColor());
        table.getTableHeader().setForeground(FSkin.getColor(FSkin.Colors.CLR_TEXT).getColor());
        table.getTableHeader().setBackground(FSkin.getColor(FSkin.Colors.CLR_THEME2).getColor());
        table.setPreferredScrollableViewportSize(new Dimension(550, 180));
        return table;
    }

    private static JPanel transparent(final String layout) {
        final JPanel result = new JPanel(new MigLayout(layout));
        result.setOpaque(false);
        return result;
    }

    private FLabel label(final String caption) { return new FLabel.Builder().text(caption).build(); }
    private FButton button(final String key, final Runnable action) {
        final FButton button = new FButton(text(key));
        button.addActionListener(event -> action.run());
        return button;
    }
    private String text(final String key) { return localizer.getMessage(key); }
    private CSubmenuAiArena controller() { return CSubmenuAiArena.SINGLETON_INSTANCE; }
    @Override public EMenuGroup getGroupEnum() { return EMenuGroup.SANCTIONED; }
    @Override public String getMenuTitle() { return text("lblAiArena"); }
    @Override public EDocID getItemEnum() { return EDocID.HOME_AI_ARENA; }
    @Override public EDocID getDocumentID() { return EDocID.HOME_AI_ARENA; }
    @Override public DragTab getTabLabel() { return tab; }
    @Override public CSubmenuAiArena getLayoutControl() { return controller(); }
    @Override public void setParentCell(final DragCell cell) { parentCell = cell; }
    @Override public DragCell getParentCell() { return parentCell; }

    @Override
    public void populate() {
        final JPanel container = VHomeUI.SINGLETON_INSTANCE.getPnlDisplay();
        container.removeAll();
        container.setLayout(new BorderLayout());
        container.add(panel, BorderLayout.CENTER);
        container.revalidate();
        container.repaint();
    }
}
