package net.lonia.rush.listeners;

import net.lonia.core.LoniaCore;
import net.lonia.core.player.account.Account;
import net.lonia.rush.LoniaRush;
import net.lonia.rush.game.Game;
import net.lonia.rush.game.GameManager;
import net.lonia.rush.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        event.setQuitMessage(null);

        LoniaCore.get().getServerAccount().getDataServer().setConnectedPlayerCount(Bukkit.getOnlinePlayers().size() - 1);

        final GameManager gameManager = LoniaRush.INSTANCE.getGameManager();
        Game game = gameManager.getGameByPlayer(player);

        if (game.isState(GameState.NOT_ENOUGH_PLAYER) || game.isState(GameState.ENOUGH_PLAYER)) {
            game.getPlayerInGame().remove(player.getDisplayName());

            game.removePlayerData(player);

            if (game.getPlayerInGame() != null) {
                game.getPlayerInGame().forEach(players -> Bukkit.getPlayer(players).sendMessage("§3[-] " + game.getPlayerData(player).getRank().getPrefix() + player.getDisplayName() + " §fa quitté la file d'attente. " + getColor(game) + "(" + game.getPlayerInGame().size() + "/4)"));
            }

            LoniaRush.INSTANCE.getScoreboardManager().onLogout(player);
        }
    }

    String getColor(Game game) {
        String color = "";
        switch (game.getPlayerInGame().size()) {
            case 1:
                color = "§c";
                break;
            case 2:
            case 3:
                color = "§e";
                break;
            case 4:
                color = "§a";
                break;
            default:
                break;
        }
        return color;
    }
}
