package net.lonia.rush.listeners;

import net.lonia.core.LoniaCore;
import net.lonia.core.item.builder.ItemBuilder;
import net.lonia.core.player.account.Account;
import net.lonia.rush.LoniaRush;
import net.lonia.rush.game.Game;
import net.lonia.rush.game.GameManager;
import net.lonia.rush.game.GameState;
import net.lonia.rush.game.GameTeam;
import net.lonia.rush.game.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerJoinListener implements Listener {

    final ItemStack TEAMS = new ItemBuilder(Material.WOOL).setName("§9§lÉquipes").toItemStack();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        event.setJoinMessage(null);

        LoniaCore.get().getServerAccount().getDataServer().setConnectedPlayerCount(Bukkit.getOnlinePlayers().size());

        Account account = LoniaCore.get().getAccountManager().getAccount(player);

        account.getUserData().setConnected(1);
        account.getUserData().setIsInGame(1);

        final GameManager gameManager = LoniaRush.INSTANCE.getGameManager();
        Game game = gameManager.getGameByPlayer(player);

        if (game.isState(GameState.NOT_ENOUGH_PLAYER) || game.isState(GameState.ENOUGH_PLAYER)) {
            game.getPlayerInGame().add(player.getDisplayName());

            game.addPlayerData(player);

            player.teleport(new Location(Bukkit.getWorlds().get(0), 97.5, 86, 26.5, -90F, 0F));

            game.getPlayerInGame().forEach(players -> Bukkit.getPlayer(players).sendMessage("§b[+] " + account.getUserData().getDataRank().getRank().getPrefix() + player.getDisplayName() + " §fa rejoint la file d'attente ! " + getColor(game) + "(" + game.getPlayerInGame().size() + "/4)"));

            player.setGameMode(GameMode.ADVENTURE);
            player.setLevel(0);
            player.setExp(0.0F);
            player.setMaxHealth(20.0D);
            player.setHealth(20.0D);
            player.setFoodLevel(20);
            player.setFlying(false);
            player.getInventory().clear();
            player.getEquipment().setHelmet(null);
            player.getEquipment().setChestplate(null);
            player.getEquipment().setLeggings(null);
            player.getEquipment().setBoots(null);

            player.getInventory().setItem(0, TEAMS);

            if (game.getPlayerInGame().size() == 2) {
                game.setState(GameState.ENOUGH_PLAYER);
                game.startAutoStart();
            }
        }
        else if (game.isState(GameState.PLAYING)) {
            if (game.getPlayerInGame().contains(player.getDisplayName()))
                GameTeam.teleportToSpawn(player);
            else {
                player.setGameMode(GameMode.SPECTATOR);
            }
        }

        player.sendMessage("GameName " + game.getGameName() + ", ID " + game.getGameId() + ", State " + game.getState().name());
        final PlayerData data = game.getPlayerData(player);
        player.sendMessage("PlayerData: \nbeds " + data.getBeds() + "\ndeaths" + data.getDeaths() + "\nkills" + data.getKills());

        LoniaRush.INSTANCE.getScoreboardManager().onLogin(player);
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
