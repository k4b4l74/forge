package forge.screens.home.arena;

import forge.gamemodes.aisimulation.ArenaConfiguration;
import forge.gamemodes.aisimulation.ArenaProtocol;
import forge.gamemodes.aisimulation.ArenaResult;
import forge.gamemodes.aisimulation.ArenaSchedule;
import forge.gamemodes.aisimulation.ArenaStore;
import forge.gamemodes.aisimulation.ArenaWorker;
import forge.view.ArenaWorkerMain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.lang.management.ManagementFactory;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ArenaProcessWorker implements ArenaWorker {
    private final Process process;
    private final BufferedWriter requests;
    private final BlockingQueue<ArenaProtocol> responses = new LinkedBlockingQueue<>(4);
    private final AtomicBoolean closed = new AtomicBoolean();
    private final ArenaConfiguration configuration;
    private final int startupTimeout;
    private boolean ready;

    public ArenaProcessWorker(final ArenaStore store, final int number, final Path assets) throws IOException {
        this(store, number, assets, ArenaWorkerMain.class, 180);
    }

    ArenaProcessWorker(final ArenaStore store, final int number, final Path assets,
                       final Class<?> entryPoint, final int startupTimeout) throws IOException {
        configuration = store.configuration();
        this.startupTimeout = startupTimeout;
        final Path home = store.directory().resolve("workers/" + number);
        Files.createDirectories(home.resolve("profile"));
        ArenaFiles.copyTree(store.directory().resolve("inputs/custom"), home.resolve("profile/custom"));
        ArenaFiles.copyTree(store.directory().resolve("inputs/preferences"), home.resolve("profile/preferences"));
        final List<String> arguments = new ArrayList<>();
        for (final String argument : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
            if (argument.startsWith("--add-opens=") || argument.startsWith("--add-exports=")) {
                arguments.add(argument);
            }
        }
        arguments.addAll(List.of("-Xms128m", "-Xmx" + configuration.heapMegabytes() + "m", "-XX:+DisableExplicitGC",
                "-Djava.awt.headless=true", "-Dfile.encoding=UTF-8", "-Dio.netty.tryReflectionSetAccessible=true",
                "-Dforge.userDir=" + home.resolve("profile"), "-Dforge.cacheDir=" + home.resolve("cache"),
                "-cp", System.getProperty("java.class.path"), entryPoint.getName(),
                store.directory().toString(), assets.toAbsolutePath().toString()));
        final Path argumentFile = home.resolve("java.args");
        Files.writeString(argumentFile, String.join("\n", arguments.stream().map(ArenaProcessWorker::quoteArgument).toList()),
                StandardCharsets.UTF_8);
        final String executable = System.getProperty("os.name").startsWith("Windows") ? "java.exe" : "java";
        final ProcessBuilder builder = new ProcessBuilder(Path.of(System.getProperty("java.home"), "bin", executable).toString(),
                "@" + argumentFile);
        for (final String variable : List.of("JAVA_TOOL_OPTIONS", "JDK_JAVA_OPTIONS", "_JAVA_OPTIONS")) {
            builder.environment().remove(variable);
        }
        process = builder.start();
        requests = new BufferedWriter(new OutputStreamWriter(process.getOutputStream(), StandardCharsets.UTF_8));
        final Thread output = new Thread(this::readResponses, "ai-arena-protocol-" + number);
        output.setDaemon(true);
        output.start();
        final Thread diagnostics = new Thread(() -> drainDiagnostics(home.resolve("worker.log")), "ai-arena-log-" + number);
        diagnostics.setDaemon(true);
        diagnostics.start();
    }

    private static String quoteArgument(final String value) {
        return "\"" + value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r") + "\"";
    }

    private void readResponses() {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = input.readLine()) != null) {
                if (line.length() > 1024 * 1024) { throw new IOException("Oversized worker response"); }
                final ArenaProtocol response = ArenaStore.JSON.fromJson(line, ArenaProtocol.class);
                if (response == null || response.version() != ArenaProtocol.VERSION || !responses.offer(response)) {
                    throw new IOException("Invalid worker protocol response");
                }
            }
            responses.offer(ArenaProtocol.failure("Worker exited; see its worker.log"));
        } catch (Exception exception) {
            responses.offer(ArenaProtocol.failure(exception.toString()));
        }
    }

    private void drainDiagnostics(final Path path) {
        try (InputStream input = process.getErrorStream(); OutputStream output = Files.newOutputStream(path)) {
            final byte[] buffer = new byte[8192];
            int remaining = 1024 * 1024;
            int count;
            while ((count = input.read(buffer)) != -1) {
                final int write = Math.min(remaining, count);
                if (write > 0) { output.write(buffer, 0, write); }
                remaining -= write;
            }
        } catch (IOException exception) {
            if (!closed.get()) { exception.printStackTrace(); }
        }
    }

    private ArenaProtocol receive(final int seconds) throws IOException, InterruptedException, TimeoutException {
        final ArenaProtocol response = responses.poll(seconds, TimeUnit.SECONDS);
        if (response == null) { throw new TimeoutException("AI Arena worker deadline exceeded"); }
        if ("failure".equals(response.type())) { throw new IOException(response.error()); }
        return response;
    }

    @Override
    public ArenaResult play(final ArenaSchedule.Task task) throws Exception {
        if (!ready) {
            try {
                final ArenaProtocol response = receive(startupTimeout);
                if (!"ready".equals(response.type())) { throw new IOException("Expected worker readiness message"); }
                ready = true;
            } catch (Exception exception) {
                close();
                throw new IOException("AI Arena worker could not initialize: " + exception.getMessage(), exception);
            }
        }
        final long start = System.nanoTime();
        try {
            if (closed.get()) { throw new IOException("Worker stopped"); }
            requests.write(ArenaStore.JSON.toJson(ArenaProtocol.job(task)));
            requests.newLine();
            requests.flush();
            final ArenaProtocol response = receive(configuration.timeoutSeconds());
            if (!"result".equals(response.type()) || response.result() == null
                    || response.result().gameId() != task.gameId()) {
                throw new IOException("Expected result for game " + task.gameId());
            }
            response.result().validate(configuration);
            return response.result();
        } catch (Exception exception) {
            close();
            return new ArenaResult(task.gameId(), exception instanceof TimeoutException ? ArenaResult.Outcome.TIMEOUT : ArenaResult.Outcome.ERROR,
                    -1, -1, 0, TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start), exception.toString());
        }
    }

    @Override
    public void close() {
        if (closed.compareAndSet(false, true)) {
            process.destroyForcibly();
            responses.offer(ArenaProtocol.failure("Worker stopped"));
        }
    }

    ProcessHandle handle() { return process.toHandle(); }
}
