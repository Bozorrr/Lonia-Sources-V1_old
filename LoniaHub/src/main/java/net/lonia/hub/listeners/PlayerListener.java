package net.lonia.hub.listeners;

import net.lonia.core.LoniaCore;
import net.lonia.core.item.manager.ItemManager;
import net.lonia.core.player.account.Account;
import net.lonia.core.player.account.DGroup;
import net.lonia.core.player.sanction.SanctionManager;
import net.lonia.core.player.sanction.SanctionType;
import net.lonia.core.rank.PermissionLevel;
import net.lonia.core.rank.Rank;
import net.lonia.core.server.account.ServerAccount;
import net.lonia.core.server.type.ServerType;
import net.lonia.core.tools.HeaderFooter;
import net.lonia.core.tools.Title;
import net.lonia.hub.LoniaHub;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        final UUID uuid = player.getUniqueId();

        if (SanctionManager.isSanctionActive(uuid, SanctionType.BAN)) {
            return;
        }

        event.setJoinMessage(null);

        final Account account = LoniaCore.get().getAccountManager().getAccount(player);
        account.getUserData().setIsInGame(0);

        teleportToSpawn(player);
        player.getInventory().clear();
        player.getEquipment().setHelmet(null);
        player.getEquipment().setChestplate(null);
        player.getEquipment().setLeggings(null);
        player.getEquipment().setBoots(null);
        player.setExp(0.0F);
        player.setLevel(0);
        player.setHealth(20.0D);
        player.setFoodLevel(20);
        player.setGameMode(GameMode.ADVENTURE);

        if (account.getUserData().getDataRank().getRank().hasPermissionLevel(PermissionLevel.VIP))
            LoniaCore.get().getServer().getOnlinePlayers().forEach(p -> p.sendMessage("§b[+] " + account.getUserData().getDataRank().getRank().getPrefix() + player.getName() + "§f s'est connecté !"));

        Title title = new Title();
        title.sendTitle(player, 5, 25, 5, "§9§lLonia", "§bBon jeu sur lonia !");

        LoniaHub.getInstance().getScoreboardManager().onLogin(player);

        HeaderFooter hf = new HeaderFooter("\n §9§lLonia \n", "\n §3Discord: §f/discord \n §3Site internet: §f/site \n\n §b§n" + LoniaHub.getInstance().getServer().getServerName() + "§b \n");
        hf.send(player);

        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 5, 5);

        player.getInventory().setItem(1, ItemManager.Main_Menu);
        player.getInventory().setItem(4, ItemManager.Shop);
        player.getInventory().setItem(7, account.getDataShop().getSKULL());

        if (account.getUserData().getDataRank().getRank().getPermissionLevel().getPower() >= PermissionLevel.CONSTRUCTEUR.getPower())
            player.getInventory().setItem(2, ItemManager.Item_StaffMenu);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();

        final UUID uuid = player.getUniqueId();

        SanctionManager.updateMute(uuid);
        if (SanctionManager.isSanctionActive(uuid, SanctionType.MUTE)) {
            event.setCancelled(true);
            return;
        }

        final Account account = LoniaCore.get().getAccountManager().getAccount(player);
        final Rank rank = account.getUserData().getDataRank().getRank();

        if (DGroup.isInGroup(player.getUniqueId().toString())) {
            if ((rank.hasPermissionLevel(PermissionLevel.RESPONSSABLE) && event.getMessage().startsWith("!"))
                    || (!rank.hasPermissionLevel(PermissionLevel.RESPONSSABLE) && event.getMessage().startsWith("&"))) {
                event.setCancelled(true);
                return;
            }
        }
        if (rank.hasPermissionLevel(PermissionLevel.CONSTRUCTEUR) && event.getMessage().startsWith("$")) {
            event.setCancelled(true);
            return;
        }

        String message = event.getMessage().replace("%", "%%").replace("<3", "§c❤");

        if (rank.hasPermissionLevel(PermissionLevel.RESPONSSABLE)) {
            message = message.replaceAll("(?<!\\\\)&", "§").replaceAll("\\\\&", "&").replace("\\n", "\n");
        } else {
            message = ChatColor.WHITE + message;
        }

        String encodedMessage = message.replace("§", "&");

        TextComponent reportButton = new TextComponent("⚠");
        reportButton.setColor(net.md_5.bungee.api.ChatColor.RED);
        reportButton.setBold(true);
        reportButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/reportMessage " + player.getName() + " " + encodedMessage));
        reportButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Cliquez pour signaler ce message").color(ChatColor.RED).create()));

        TextComponent msg = new TextComponent("");
        msg.addExtra(reportButton);
        msg.addExtra(new TextComponent(String.format(" " + rank.getPrefix() + "%1$s§f: " + message, player.getName())));

        LoniaCore.get().getServer().getOnlinePlayers().forEach(p -> p.spigot().sendMessage(msg));

        FileConfiguration configuration = YamlConfiguration.loadConfiguration(new File("D:\\Lonia\\data\\messages.yml"));
        long id = configuration.getLong("id");
        configuration.set(player.getUniqueId().toString(), id+1 + "." + msg.toString());
        configuration.set(player.getUniqueId().toString(), id+1 + "." + getCurrentDate());
    }

    String getCurrentDate() {
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new java.util.Date());
    }


    @EventHandler(priority = EventPriority.LOW)
    public void onDeath(PlayerDeathEvent event) {
        final Player target = event.getEntity();

        if (target.getKiller() == null) {
            event.setDeathMessage(null);
            return;
        }

        event.setKeepInventory(true);
        target.setLevel(0);
        target.setExp(0);

        event.setDroppedExp(0);
        event.getDrops().clear();

        target.spigot().respawn();
        teleportToSpawn(target);

        event.setDeathMessage(null);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onDamageByPlayer(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        final ServerAccount serverAccount = LoniaCore.get().getServerAccount();
        final Player player = (Player) event.getEntity();

        if (serverAccount.getDataServer().getServerType().equals(ServerType.LOBBY)) {
            if (event.getDamager() instanceof Player) {
                final Account account = LoniaCore.get().getAccountManager().getAccount(player);
                if (!account.getUserData().getDataRank().getRank().hasPermissionLevel(PermissionLevel.MODERATEUR)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        if (LoniaCore.get().getServerAccount().getDataServer().getServerType().equals(ServerType.LOBBY)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onVoid(PlayerMoveEvent event) {
        final Player player = event.getPlayer();

        ServerAccount serverAccount = LoniaCore.get().getServerAccount();

        if (serverAccount.getDataServer().getServerType().getName().equalsIgnoreCase(ServerType.LOBBY.getName()))
            if (player.getLocation().getBlockY() < 0)
                teleportToSpawn(player);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        final UUID uuid = player.getUniqueId();

        if (SanctionManager.isSanctionActive(uuid, SanctionType.BAN)) {
            return;
        }

        final Account account = LoniaCore.get().getAccountManager().getAccount(player);

        LoniaHub.getInstance().getScoreboardManager().onLogout(player);

        event.setQuitMessage(null);

        if (account.getUserData().getDataRank().getRank().hasPermissionLevel(PermissionLevel.VIP))
            LoniaCore.get().getServer().getOnlinePlayers().forEach(p -> p.sendMessage("§3[-] " + account.getUserData().getDataRank().getRank().getPrefix() + player.getName() + "§f s'est déconnecté."));

    }

    /*private void instantDeath(Player player) {
        Bukkit.getScheduler().runTaskLater(LoniaHub.getInstance(), () -> {
            PacketPlayInClientCommand packet = new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN);
            ((CraftPlayer) player).getHandle().playerConnection.a(packet);
        }, 1L);
    }*/

    private void teleportToSpawn(Player player) {
        final File file = new File(LoniaCore.get().getDataFolder(), "config.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        final ConfigurationSection configurationSection = configuration.getConfigurationSection("spawn.");

        if (configurationSection != null) {
            final World world = Bukkit.getWorld(configurationSection.getString("world"));

            double x = configurationSection.getDouble("x");
            double y = configurationSection.getDouble("y");
            double z = configurationSection.getDouble("z");
            float yaw = (float) configurationSection.getDouble("yaw");
            float pitch = (float) configurationSection.getDouble("pitch");

            final Location spawn = new Location(world, x, y, z, yaw, pitch);
            player.teleport(spawn);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onMobSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL) {
            event.setCancelled(true);
        }
    }
}
