package net.lonia.rush.listeners;

import net.lonia.rush.LoniaRush;
import net.lonia.rush.game.Game;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.io.File;

public class PlayerMoveListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMove(PlayerMoveEvent event) {
        final Player player = event.getPlayer();

        final Game game = LoniaRush.INSTANCE.getGameManager().getGameByPlayer(player);

        if (game.getDeadPlayers().contains(player.getDisplayName())) {
            event.setCancelled(true);
            return;
        }

        if (player.getLocation().getBlockY() < 40) {
            switch (game.getState()) {
                case NOT_ENOUGH_PLAYER:
                    final File file = new File(LoniaRush.INSTANCE.getDataFolder(), "config.yml");
                    final YamlConfiguration config = YamlConfiguration.loadConfiguration(file);


                    double x = config.getDouble("coord.spawn.x");
                    double y = config.getDouble("coord.spawn.y");
                    double z = config.getDouble("coord.spawn.z");
                    float yaw = (float) config.getDouble("coord.spawn.yaw");
                    float pitch = (float) config.getDouble("coord.spawn.pitch");

                    player.teleport(new Location(player.getWorld(), x, y, z, yaw, pitch));
                    break;
                case PLAYING:
                    if (!player.getGameMode().equals(GameMode.SPECTATOR)) {
                        PlayerDeathListener.respawn(player);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
