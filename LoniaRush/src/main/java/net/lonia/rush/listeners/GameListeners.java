package net.lonia.rush.listeners;

import net.lonia.rush.LoniaRush;
import net.lonia.rush.game.Game;
import net.lonia.rush.game.GameState;
import net.lonia.rush.game.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameListeners implements Listener {

    public static final Set<Block> placedBlocks = new HashSet<>();
    public static final Map<Block, Player> tntByPlayer = new HashMap<>();

//    @EventHandler(priority = EventPriority.HIGHEST)
//    public void onExposed(EntityExplodeEvent event) {
//        if (!(event.getEntity() instanceof TNTPrimed)) return;
//
//        Set<Block> blocksToRemove = event.blockList().stream()
//                .filter(block -> !placedBlocks.contains(block) && block.getType() != Material.SANDSTONE && block.getType() != Material.BED_BLOCK)
//                .collect(Collectors.toSet());
//        event.blockList().removeAll(blocksToRemove);
//
//        final TNTPrimed tnt = (TNTPrimed) event.getEntity();
//        final Player player = (Player) tnt.getSource();
//
//        if (player != null) {
//            final Set<Block> beds = event.blockList().stream()
//                    .filter(block -> block.getType() == Material.BED_BLOCK)
//                    .collect(Collectors.toSet());
//
//            beds.forEach(bed -> {
//                byte dataValue = bed.getData();
//
//                boolean isHead = (dataValue % 2 != 0);
//
//                if (isHead) {
//                    final PlayerInfo info = PlayerInfo.getPlayerInfo(player);
//                    final Game game = info.getGame();
//
//                    if (info.getTeam().equals(Team.ORANGE)) {
//                        if (bed.getLocation().equals(Team.ORANGE.getSecondaryBed())) {
//                            Team.getPlayersInTeam(Team.ORANGE, game).forEach(this::addHeath);
//                            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.sendMessage(MessageManager.intermediateBedBreak.replace("{team}", "§eJaune").replace("{player}", "§6" + player.getDisplayName())));
//                            info.addBed();
//                        } else if (bed.getLocation().equals(Team.VIOLET.getSecondaryBed())) {
//                            Team.getPlayersInTeam(Team.ORANGE, game).forEach(this::addHeath);
//                            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.sendMessage(MessageManager.intermediateBedBreak.replace("{team}", "§dRose").replace("{player}", "§6" + player.getDisplayName())));
//                            info.addBed();
//                        }
//                    } else if (info.getTeam().equals(Team.VIOLET)) {
//                        if (bed.getLocation().equals(Team.VIOLET.getSecondaryBed())) {
//                            Team.getPlayersInTeam(Team.VIOLET, game).forEach(this::addHeath);
//                            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.sendMessage(MessageManager.intermediateBedBreak.replace("{team}", "§dRose").replace("{player}", "§5" + player.getDisplayName())));
//                            info.addBed();
//                        } else if (bed.getLocation().equals(Team.ORANGE.getSecondaryBed())) {
//                            Team.getPlayersInTeam(Team.VIOLET, game).forEach(this::addHeath);
//                            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.sendMessage(MessageManager.intermediateBedBreak.replace("{team}", "§eJaune").replace("{player}", "§5" + player.getDisplayName())));
//                            info.addBed();
//                        }
//                    }
//
//                    if (bed.getLocation().equals(Team.ORANGE.getMainBed())) {
//                        player.sendMessage("X" + bed.getLocation().getX() + "\nY" + bed.getLocation().getY() + "\nZ" + bed.getLocation().getZ());
//                        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.sendMessage("§9[Rush] §bLe lit principale de l'§6Équipe Orange§b à été détruit par §5" + player.getDisplayName()));
//                        game.setIsOrangeBedBreak(1);
//                        info.addBed();
//                    }
//                    if (bed.getLocation().equals(Team.VIOLET.getMainBed())) {
//                        player.sendMessage("X" + bed.getLocation().getX() + "\nY" + bed.getLocation().getY() + "\nZ" + bed.getLocation().getZ());
//                        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.sendMessage("§9[Rush] §bLe lit principale de l'§5Équipe Violet§b à été détruit par §6" + player.getDisplayName()));
//                        game.setIsVioletBedBreak(1);
//                        info.addBed();
//                    }
//
//                    player.sendMessage("X" + bed.getLocation().getX() + "\nY" + bed.getLocation().getY() + "\nZ" + bed.getLocation().getZ());
//                }
//            });
//        }
//    }


    public void addHeath(Player joueur) {
        double vieMaxActuelle = joueur.getMaxHealth();
        joueur.setMaxHealth(vieMaxActuelle + 4.0);
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getY() >= 90) {
            event.setCancelled(true);
            return;
        }

        if (event.getBlock().getY() <= 71) {
            event.setCancelled(true);
            return;
        }

        if (!placedBlocks.contains(event.getBlock())) {
            placedBlocks.add(event.getBlock());
            if (event.getBlock().getType().equals(Material.TNT)) {
                tntByPlayer.put(event.getBlock(), event.getPlayer());
            }
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        if (placedBlocks.contains(event.getBlock()) || event.getBlock().getType().equals(Material.SANDSTONE)) {
            placedBlocks.remove(event.getBlock());
        } else event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMobSpawn(EntitySpawnEvent event) {
        if (event.getEntityType() == EntityType.DROPPED_ITEM) return;
        if (event.getEntityType() == EntityType.VILLAGER) return;

        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamageByPlayer(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        final Player player = (Player) event.getEntity();

        final Game game = LoniaRush.INSTANCE.getGameManager().getGameByPlayer(player);

        switch (game.getState()) {
            case ENDING:
                event.setCancelled(true);
                break;
            case PLAYING:
                if (event.getDamager() instanceof Player) {
                    final PlayerData t = game.getPlayerData((Player) event.getEntity());
                    final PlayerData p = game.getPlayerData((Player) event.getDamager());
                    if (p.getTeam().equals(t.getTeam()))
                        event.setCancelled(true);
                }
                break;
            default:
                break;
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        final Player player = (Player) event.getEntity();

        final Game game = LoniaRush.INSTANCE.getGameManager().getGameByPlayer(player);

        if (game.getState() == GameState.NOT_ENOUGH_PLAYER || game.getState() == GameState.ENOUGH_PLAYER || game.getState() == GameState.ENDING) {
            event.setCancelled(true);
        }
    }

    Location getBedLocation(String name) {
        final File f = new File(LoniaRush.INSTANCE.getDataFolder(), "config.yml");
        final YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        final ConfigurationSection s = config.getConfigurationSection("beds." + name);

        final World world = Bukkit.getWorld(s.getString("world"));
        final int x = s.getInt("x");
        final int y = s.getInt("y");
        final int z = s.getInt("z");

        return new Location(world, x, y, z);
    }

}
