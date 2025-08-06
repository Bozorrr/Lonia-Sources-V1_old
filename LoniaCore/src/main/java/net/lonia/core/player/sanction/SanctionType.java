package net.lonia.core.player.sanction;

public enum SanctionType {
    BAN("ban"),
    MUTE("mute"),
    KICK("kick"),
    CUSTOM("custom");

    final String name;

    SanctionType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
