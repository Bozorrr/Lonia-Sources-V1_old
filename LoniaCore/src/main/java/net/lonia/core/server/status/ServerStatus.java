package net.lonia.core.server.status;

public enum ServerStatus {

    NOTFOUND("NotFound", "§4NotFound"),
    DEFAULT("Default", "§7Default"),
    LOADING("Loading", "§6Loading"),
    ONLINE("Online", "§aOnline"),
    STAFFONLY("StaffOnly", "§3StaffOnly"),
    RESTART("Restart", "§5Restart"),
    BETATEST("BetaTest", "§9BetaTest"),
    OFFLINE("Offline", "§cOffline");

    private final String name;

    private final String prefix;

    ServerStatus(String name, String prefix) {
        this.name = name;
        this.prefix = prefix;
    }

    public static ServerStatus getByName(String name) {
        for (ServerStatus status : values()) {
            if (status.getName().equals(name)) {
                return status;
            }
        }
        return NOTFOUND;
    }

    public String getName() {
        return this.name;
    }

    public String getPrefix() {
        return this.prefix;
    }
}
