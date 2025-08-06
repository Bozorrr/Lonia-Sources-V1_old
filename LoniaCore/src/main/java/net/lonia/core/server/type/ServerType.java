package net.lonia.core.server.type;

public enum ServerType {
    NOTFOUND("NotFound"),
    DEFAULT("Default"),
    LOBBY("Lobby"),
    BUILD("Build"),

    DUEL("Duels PVP"),
    ARENEC("Arerne Classique"),
    ARENEE("Arene Effects"),
    FREECUBE("FreeCube"),
    UNEXUS("UNexus"),
    RUNORKILL("RUN or KILL"),
    RUSH("Rush"),
    JUMPADVENTURE("Jump Aventure"),
    PIXELWAR("Pixel War");

    private final String name;

    ServerType(String name) {
        this.name = name;
    }

    public static ServerType getByName(String name) {
        for (ServerType type : values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return NOTFOUND;
    }

    public String getName() {
        return this.name;
    }
}
