package net.lonia.rush.game;

import net.lonia.rush.LoniaRush;
import net.lonia.rush.game.player.PlayerData;
import org.bukkit.entity.Player;

import java.util.*;

public class Game {
    private final int gameId;
    private final Map<UUID, PlayerData> playersData;
    private final LoniaRush plugin;
    private String gameName;
    private GameState state;

    private GameAutoStart autoStartRunnable;
    private boolean isAutoStartRunning = false;

    private final List<String> playerInGame = new ArrayList<>();
    private final List<String> connectedPlayers = new ArrayList<>();
    private final List<String> orangeTeamPlayers = new ArrayList<>();
    private final List<String> violetTeamPlayers = new ArrayList<>();
    private final List<String> deadPlayers = new ArrayList<>();
    private final List<String> specPlayers = new ArrayList<>();

    private boolean hasOrangeBed;
    private boolean hasVioletBed;

    public Game(LoniaRush plugin, int gameId, String gameName) {
        this.plugin = plugin;
        this.gameId = gameId;
        this.playersData = new HashMap<>();
        this.gameName = gameName + "-" + gameId;
        this.state = GameState.NOT_ENOUGH_PLAYER;
        this.hasOrangeBed = true;
        this.hasVioletBed = true;
    }

    public void startAutoStart() {
        if (!isAutoStartRunning) {
            autoStartRunnable = new GameAutoStart(plugin, this);
            autoStartRunnable.runTaskTimer(plugin, 0, 20);
            isAutoStartRunning = true;
        }
    }

    public void stopAutoStart() {
        if (isAutoStartRunning && autoStartRunnable != null) {
            autoStartRunnable.cancel();
            isAutoStartRunning = false;
        }
    }

    public int getGameId() { return this.gameId; }
    public String getGameName() { return this.gameName; }
    public GameState getState() { return state; }

    public GameAutoStart getAutoStartRunnable() { return this.autoStartRunnable; }

    public void setGameName(String gameName) { this.gameName = gameName; }
    public void setState(GameState state) { this.state = state; }

    public boolean isState(GameState state) { return this.state == state; }

    public void addPlayerData(Player player) { this.playersData.put(player.getUniqueId(), new PlayerData(player)); }
    public void removePlayerData(Player player) { playersData.remove(player.getUniqueId()); }
    public PlayerData getPlayerData(Player player) { return this.playersData.get(player.getUniqueId()); }
    public Map<UUID, PlayerData> getPlayersData() { return this.playersData; }

    public List<String> getPlayerInGame() { return this.playerInGame; }
    public List<String> getConnectedPlayers() { return this.connectedPlayers; }

    public List<String> getOrangeTeamPlayers() { return this.orangeTeamPlayers; }
    public List<String> getVioletTeamPlayers() { return this.violetTeamPlayers; }

    public boolean hasOrangeBed() { return this.hasOrangeBed; }
    public boolean hasVioletBed() { return this.hasVioletBed; }

    public void setHasOrangeBed(boolean hasOrangeBed) { this.hasOrangeBed = hasOrangeBed; }
    public void setHasVioletBed(boolean hasVioletBed) { this.hasVioletBed = hasVioletBed; }

    public List<String> getDeadPlayers() { return this.deadPlayers; }
    public List<String> getSpecPlayers() { return this.specPlayers; }

    public boolean isAutoStartRunning() { return this.isAutoStartRunning; }
}
