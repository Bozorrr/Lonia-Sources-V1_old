package net.lonia.rush.game;

import java.util.Arrays;

public enum GameState {
    WAITING("waiting"),
    NOT_ENOUGH_PLAYER("not_enough_player"),
    ENOUGH_PLAYER("enough_player"),
    PLAYING("playing"),
    ENDING("ending");

    private final String name;

    GameState(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public static GameState getByName(String name) {
        return Arrays.stream(values()).filter(state -> state.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
