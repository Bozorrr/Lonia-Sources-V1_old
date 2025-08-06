package net.lonia.rush.listeners;

import net.lonia.core.tools.Title;
import net.lonia.rush.LoniaRush;
import net.lonia.rush.game.Game;
import net.lonia.rush.game.GameState;
import net.lonia.rush.game.GameTeam;
import net.lonia.rush.game.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent event) {
        final Player player = event.getEntity();
        event.setDeathMessage(null);

        final Game game = LoniaRush.INSTANCE.getGameManager().getGameByPlayer(player);

        if (game.getState().equals(GameState.PLAYING)) {
            respawn(player);
            if (player.getKiller() != null)
                game.getPlayerData(player.getKiller()).addKill();
        }
    }

    /*private void instantDeath(Player player) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            PacketPlayInClientCommand packet = new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN);
            ((CraftPlayer) player).getHandle().playerConnection.a(packet);
        }, 1L);
    }*/

    static void respawn(Player player) {
        player.spigot().respawn();

        final Game game = LoniaRush.INSTANCE.getGameManager().getGameByPlayer(player);
        final PlayerData data = game.getPlayerData(player);

        final GameTeam team = data.getTeam();

        if (team.equals(GameTeam.ORANGE)) {
            if (!game.hasOrangeBed()) {
                game.getOrangeTeamPlayers().remove(player.getDisplayName());
                player.setGameMode(GameMode.SPECTATOR);
                Title t = new Title();
                t.sendTitle(player, 5, 25, 5, "§c§lVous êtes définitivement ");
            }
        }
        else if (team.equals(GameTeam.VIOLET)) {
            if (!game.hasOrangeBed()) {
                game.getVioletTeamPlayers().remove(player.getDisplayName());

                player.setGameMode(GameMode.SPECTATOR);
                Title t = new Title();
                t.sendTitle(player, 5, 25, 5, "§c§lVous êtes définitivement ");
            }
        }
        else {
            player.sendMessage("§9[Rush] §3Vous êtes mort ! Vous allez réapparaître.");

            game.getPlayerData(player).addDeath();
            game.getDeadPlayers().add(player.getDisplayName());

            game.getPlayerInGame().forEach(p -> Bukkit.getPlayer(p).hidePlayer(player));

            player.setGameMode(GameMode.SURVIVAL);
            double d = player.getMaxHealth();
            player.setHealth(d);

            GameTeam.teleportToSpawn(player);

            for (int i = 5; i > 0; i--) {
                final int secondsLeft = i;
                Bukkit.getScheduler().runTaskLater(LoniaRush.INSTANCE, () -> {
                    Title titleSender = new Title();

                    if (secondsLeft == 5)
                        titleSender.sendTitle(player, 10, 20, 10, "§c§l" + secondsLeft, "§cVous êtes mort !");
                    else titleSender.sendTitle(player, 10, 20, 10, "§c§l" + secondsLeft, " ");

                }, (5 - i) * 20L);
            }

            game.getDeadPlayers().remove(player.getDisplayName());
            game.getPlayerInGame().forEach(p -> Bukkit.getPlayer(p).showPlayer(player));
        }
    }
}
