package forge.gamemodes.aisimulation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ArenaStore implements AutoCloseable {
    public static final Gson JSON = new GsonBuilder().disableHtmlEscaping().create();
    public enum State { READY, RUNNING, PAUSING, PAUSED, CANCELLED, COMPLETED, FAILED }
    public record Metadata(State state, long elapsedMillis, String message, long updatedAt) { }

    private final Path directory;
    private final ArenaConfiguration configuration;
    private final Map<Integer, ArenaResult> results = new LinkedHashMap<>();
    private Metadata metadata;
    private FileChannel lockChannel;
    private FileLock lock;
    private FileChannel journal;

    public ArenaStore(final Path directory) throws IOException {
        this.directory = directory.toAbsolutePath().normalize();
        try {
            configuration = JSON.fromJson(Files.readString(directory.resolve("run.json")), ArenaConfiguration.class);
            if (configuration == null) {
                throw new IllegalArgumentException("Missing configuration");
            }
            readMetadata();
            readResults();
        } catch (RuntimeException exception) {
            throw new IOException("Cannot read AI Arena run: " + directory, exception);
        }
    }

    public static ArenaStore create(final Path directory, final ArenaConfiguration configuration) throws IOException {
        Files.createDirectories(directory);
        if (Files.exists(directory.resolve("run.json"))) {
            throw new IOException("A run already exists here: " + directory);
        }
        writeJson(directory.resolve("run.json"), configuration);
        return new ArenaStore(directory);
    }

    public Path directory() { return directory; }
    public ArenaConfiguration configuration() { return configuration; }
    public synchronized Metadata metadata() { return metadata; }
    public synchronized List<ArenaResult> results() { return new ArrayList<>(results.values()); }
    public synchronized int completed() { return results.size(); }
    public synchronized boolean contains(final int gameId) { return results.containsKey(gameId); }

    public synchronized void claim() throws IOException {
        if (lock != null) {
            throw new IOException("This run is already open for writing");
        }
        lockChannel = FileChannel.open(directory.resolve("run.lock"), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        try {
            lock = lockChannel.tryLock();
            if (lock == null) {
                throw new IOException("Another Forge process is already running this tournament");
            }
            readMetadata();
            final long completeBytes = readResults();
            journal = FileChannel.open(directory.resolve("games.jsonl"), StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE);
            journal.truncate(completeBytes);
            journal.position(completeBytes);
        } catch (IOException | RuntimeException exception) {
            close();
            throw new IOException(exception instanceof OverlappingFileLockException
                    ? "Another runner already owns this tournament" : exception.getMessage(), exception);
        }
    }

    private void readMetadata() throws IOException {
        final Path status = directory.resolve("status.json");
        metadata = Files.exists(status) ? JSON.fromJson(Files.readString(status), Metadata.class)
                : new Metadata(State.READY, 0, "", System.currentTimeMillis());
        if (metadata == null || metadata.state() == null || metadata.elapsedMillis() < 0) {
            throw new IOException("Invalid run status: " + status);
        }
    }

    private long readResults() throws IOException {
        results.clear();
        final Path path = directory.resolve("games.jsonl");
        if (!Files.exists(path)) {
            return 0;
        }
        long position = 0;
        long completeBytes = 0;
        try (BufferedInputStream input = new BufferedInputStream(Files.newInputStream(path));
             ByteArrayOutputStream line = new ByteArrayOutputStream()) {
            int next;
            while ((next = input.read()) != -1) {
                position++;
                if (next == '\n') {
                    final ArenaResult result;
                    try {
                        result = JSON.fromJson(line.toString(StandardCharsets.UTF_8), ArenaResult.class);
                        if (result == null) {
                            throw new IllegalArgumentException("Empty result");
                        }
                        result.validate(configuration);
                    } catch (RuntimeException exception) {
                        throw new IOException("Invalid committed result in " + path, exception);
                    }
                    final ArenaResult previous = results.putIfAbsent(result.gameId(), result);
                    if (previous != null && !previous.equals(result)) {
                        throw new IOException("Conflicting results for game " + result.gameId());
                    }
                    line.reset();
                    completeBytes = position;
                } else {
                    line.write(next);
                    if (line.size() > 1024 * 1024) {
                        throw new IOException("Oversized result record in " + path);
                    }
                }
            }
        }
        return completeBytes;
    }

    public synchronized void append(final ArenaResult result) throws IOException {
        if (journal == null) {
            throw new IOException("Run is not locked for writing");
        }
        result.validate(configuration);
        final ArenaResult previous = results.get(result.gameId());
        if (previous != null) {
            if (!previous.equals(result)) {
                throw new IOException("Conflicting result for game " + result.gameId());
            }
            return;
        }
        final ByteBuffer bytes = StandardCharsets.UTF_8.encode(JSON.toJson(result) + "\n");
        while (bytes.hasRemaining()) {
            journal.write(bytes);
        }
        journal.force(true);
        results.put(result.gameId(), result);
    }

    public synchronized void update(final State state, final long elapsedMillis, final String message) throws IOException {
        if (lock == null) {
            throw new IOException("Run is not locked for writing");
        }
        final Metadata next = new Metadata(state, elapsedMillis, message, System.currentTimeMillis());
        writeJson(directory.resolve("status.json"), next);
        metadata = next;
    }

    public static void writeJson(final Path destination, final Object value) throws IOException {
        final Path temporary = Files.createTempFile(destination.getParent(), ".arena-", ".tmp");
        try {
            try (FileChannel channel = FileChannel.open(temporary, StandardOpenOption.WRITE)) {
                final ByteBuffer bytes = StandardCharsets.UTF_8.encode(JSON.toJson(value));
                while (bytes.hasRemaining()) {
                    channel.write(bytes);
                }
                channel.force(true);
            }
            for (int attempt = 0; ; attempt++) {
                try {
                    try {
                        Files.move(temporary, destination, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
                    } catch (AtomicMoveNotSupportedException exception) {
                        Files.move(temporary, destination, StandardCopyOption.REPLACE_EXISTING);
                    }
                    break;
                } catch (AccessDeniedException exception) {
                    if (attempt >= 39) { throw exception; }
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException interrupted) {
                        Thread.currentThread().interrupt();
                        throw new IOException("Interrupted while replacing " + destination, interrupted);
                    }
                }
            }
        } finally {
            Files.deleteIfExists(temporary);
        }
    }

    @Override
    public synchronized void close() throws IOException {
        try {
            if (journal != null) { journal.close(); }
        } finally {
            journal = null;
            try {
                if (lock != null && lock.isValid()) { lock.release(); }
            } finally {
                lock = null;
                if (lockChannel != null) { lockChannel.close(); }
                lockChannel = null;
            }
        }
    }
}
