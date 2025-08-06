package net.lonia.rush.game;

import net.lonia.rush.LoniaRush;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class GameManager {

    private final Map<Integer, Game> games;
    private static int ACTUAL_GAME_ID = 0;
    private final LoniaRush plugin;

    public GameManager(LoniaRush plugin) {
        this.games = new HashMap<>();
        this.plugin = plugin;
    }

    public Game createGame(String gameName) {
        incrementGameId();

        Game game = new Game(plugin, getActualGameId(), gameName);
        games.put(game.getGameId(), game);
        return game;
    }

    public void removeGame(int gameId) {
        games.remove(gameId);
    }

    public Game getGame(int gameId) {
        return games.get(gameId);
    }

    public Game getGameByName(String gameName) {
        return games.values().stream()
                .filter(game -> game.getGameName().equalsIgnoreCase(gameName))
                .findFirst()
                .orElse(getAvailableGame());
    }

    public Map<Integer, Game> getAllGames() {
        return games;
    }

    public Game getGameByPlayer(Player player) {

        return games.values().stream().filter(game -> game.getPlayerInGame().contains(player.getDisplayName())).findFirst().orElse(getAvailableGame());

    }

    public static void incrementGameId() {
        ACTUAL_GAME_ID++;
    }

    public static int getActualGameId() {
        return ACTUAL_GAME_ID;
    }

    public Game getAvailableGame() {
        for (Game game : games.values()) {
            if (game.getState() == GameState.NOT_ENOUGH_PLAYER) return game;
        }

        return createGame(LoniaRush.INSTANCE.getServer().getServerName());
    }
}
