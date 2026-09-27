package forge.gamemodes.aisimulation;

public record ArenaProtocol(int version, String type, ArenaSchedule.Task task, ArenaResult result, String error) {
    public static final int VERSION = 1;

    public static ArenaProtocol ready() { return new ArenaProtocol(VERSION, "ready", null, null, null); }
    public static ArenaProtocol job(final ArenaSchedule.Task task) { return new ArenaProtocol(VERSION, "job", task, null, null); }
    public static ArenaProtocol result(final ArenaResult result) { return new ArenaProtocol(VERSION, "result", null, result, null); }
    public static ArenaProtocol failure(final String error) { return new ArenaProtocol(VERSION, "failure", null, null, error); }
}
