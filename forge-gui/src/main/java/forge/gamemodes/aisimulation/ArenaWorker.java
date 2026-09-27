package forge.gamemodes.aisimulation;

import java.io.IOException;

public interface ArenaWorker extends AutoCloseable {
    ArenaResult play(ArenaSchedule.Task task) throws Exception;
    @Override
    void close();

    @FunctionalInterface
    interface Factory {
        ArenaWorker create(int workerNumber) throws IOException;
    }
}
