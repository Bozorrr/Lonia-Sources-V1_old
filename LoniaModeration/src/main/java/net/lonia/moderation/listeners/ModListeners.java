package net.lonia.moderation.listeners;

import net.lonia.core.LoniaCore;
import net.lonia.core.item.manager.ItemManager;
import net.lonia.core.item.manager.ModItemManager;
import net.lonia.core.player.account.Account;
import net.lonia.core.player.account.UserData;
import net.lonia.core.player.sanction.SanctionManager;
import net.lonia.core.player.staff.StaffManager;
import net.lonia.core.server.it.ServerItemManager;
import net.lonia.core.tools.Title;
import net.lonia.moderation.LoniaModeration;
import net.lonia.moderation.gui.GuiManager;
import net.lonia.moderation.gui.staff.admin.AdminMenu;
import net.lonia.moderation.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ModListeners implements Listener {

    public static final Set<Player> freezePlayers = new HashSet<>();
    static final Set<Player> antiSpam = new HashSet<>();

    public static void executeTaskInLoop(Player player) {
        long intervalTicks = Math.round(0.5 * 20);
        long totalTicks = Math.round(30.0 * 20);

        final Title title = new Title();
        final String title1 = "§1§l❄ §6☻ §bFREEZE §6☻ §1§l❄";
        final String subtitle1 = "§b⭐ §3Vous ne pouvez plus bouger ! §e㋡";

        final String title2 = "§9§l❄ §c☻ §3FREEZE §c☻ §9§l❄";
        final String subtitle2 = "§e㋡ §3Vous ne pouvez plus bouger ! §b⭐";

        new BukkitRunnable() {
            long elapsedTicks = 0;
            boolean toggle = false;

            @Override
            public void run() {
                if (!freezePlayers.contains(player)) {
                    title.sendTitle(player, 0, 10, 0, " ", " ");
                    cancel();
                    return;
                }

                if (toggle) {
                    title.sendTitle(player, 0, 100, 0, title1, subtitle1);
                } else {
                    title.sendTitle(player, 0, 100, 0, title2, subtitle2);
                }

                toggle = !toggle;
                elapsedTicks += intervalTicks;

                if (elapsedTicks >= totalTicks) {
                    freezePlayers.remove(player);
                    cancel();
                }
            }
        }.runTaskTimer(LoniaModeration.getInstance(), 0, intervalTicks);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onJoin(PlayerJoinEvent event) {

        Bukkit.getScheduler().runTaskLater(LoniaModeration.getInstance(), () -> {
            event.setJoinMessage(null);

            final Player player = event.getPlayer();
            final UUID uuid = player.getUniqueId();

            if (StaffManager.isInMod(uuid)) {
                LoniaModeration.getInstance().getModerators().add(player.getUniqueId());

                final PlayerManager pm = new PlayerManager(player);
                pm.init();
                pm.getInventory();
                pm.saveInventory();

                player.setGameMode(GameMode.SURVIVAL);
                player.setAllowFlight(true);
                player.setFlying(true);

                if (StaffManager.isInVanish(uuid)) {
                    PlayerManager.vanish(uuid);
                }
            }
        }, 6L);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onQuit(PlayerQuitEvent event) {

        event.setQuitMessage(null);

        final Player player = event.getPlayer();

        if (StaffManager.isInMod(player.getUniqueId()))
            Bukkit.getOnlinePlayers().forEach(players -> players.showPlayer(player));

        LoniaModeration.getInstance().getModerators().remove(player.getUniqueId());

    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final ItemStack it = player.getInventory().getItemInHand();

        if (it != null) {
            if (it.hasItemMeta()) {
                final ItemMeta meta = it.getItemMeta();
                if (meta.hasDisplayName()) {
                    final String displayName = meta.getDisplayName();
                    final GuiManager guiManager = LoniaModeration.getInstance().getGuiManager();

                    if (displayName.equals(ItemManager.Item_StaffMenu.getItemMeta().getDisplayName())) {
                        ServerItemManager.update();
                        guiManager.open(player, AdminMenu.class);
                    } else if (displayName.equals(ModItemManager.SPEC_MOD.getItemMeta().getDisplayName())) {
                        if (player.getGameMode() == GameMode.SPECTATOR) return;
                        player.setGameMode(GameMode.SPECTATOR);
                        player.sendMessage("§9[Staff] §bVous êtes en mode spectateur pendant 3 secondes.");
                        player.updateInventory();

                        Bukkit.getScheduler().runTaskLater(LoniaModeration.getInstance(), () -> {
                            player.setGameMode(GameMode.SURVIVAL);
                            player.setAllowFlight(true);
                            player.setFlying(true);

                        }, 3 * 20L);

                        player.updateInventory();

                    } else if (displayName.equals(ModItemManager.VANISH.getItemMeta().getDisplayName())) {
                        if (antiSpam.contains(player)) return;
                        antiSpam.add(player);

                        PlayerManager.unvanish(player.getUniqueId());
                        player.getInventory().setItem(4, ModItemManager.UN_VANISH);

                        Bukkit.getScheduler().runTaskLater(LoniaModeration.getInstance(), () -> antiSpam.remove(player), 1L);
                    } else if (displayName.equals(ModItemManager.UN_VANISH.getItemMeta().getDisplayName())) {
                        if (antiSpam.contains(player)) return;
                        antiSpam.add(player);

                        PlayerManager.vanish(player.getUniqueId());
                        player.getInventory().setItem(4, ModItemManager.VANISH);

                        Bukkit.getScheduler().runTaskLater(LoniaModeration.getInstance(), () -> antiSpam.remove(player), 1L);
                    }
                    else if (displayName.equals(ModItemManager.KB_SWORD.getItemMeta().getDisplayName())) {
                        player.getInventory().setItem(0, ModItemManager.KB_SWORD);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        final Player player = event.getPlayer();

        if (player.getInventory().getItemInHand() == null) return;

        final ItemStack it = player.getInventory().getItemInHand();
        if (!it.hasItemMeta()) return;

        final ItemMeta meta = it.getItemMeta();

        if (!meta.hasDisplayName()) return;
        final String displayName = meta.getDisplayName();

        if (!(event.getRightClicked() instanceof Player)) return;

        event.setCancelled(true);

        final Player target = (Player) event.getRightClicked();

        if (displayName.equals(ModItemManager.CHECK.getItemMeta().getDisplayName())) {
            player.performCommand("check " + target.getDisplayName());
        }
        else if (displayName.equals(ModItemManager.FREEZE.getItemMeta().getDisplayName())) {
            if (antiSpam.contains(player)) return;
            antiSpam.add(player);

            player.performCommand("freeze " + target.getDisplayName());

            Bukkit.getScheduler().runTaskLater(LoniaModeration.getInstance(), () -> antiSpam.remove(player), 1L);
        }
        else if (displayName.equals(ModItemManager.KILL_SWORD.getItemMeta().getDisplayName())) {
            if (antiSpam.contains(player)) return;
            antiSpam.add(player);

            target.setHealth(0.0);
            target.sendMessage("§9[Sanction] §3Vous venez d'être tué par un membre de notre équipe.");

            Bukkit.getScheduler().runTaskLater(LoniaModeration.getInstance(), () -> antiSpam.remove(player), 1L);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryClick(InventoryClickEvent event) {
        final ItemStack it = event.getCurrentItem();

        if (it == null) return;

        if (it.hasItemMeta()) {
            final ItemMeta meta = it.getItemMeta();

            if (meta.hasDisplayName()) {
                final String displayName = meta.getDisplayName();

                final ItemStack[] items = new ItemStack[]{ItemManager.Item_StaffMenu, ModItemManager.KB_SWORD, ModItemManager.CHECK, ModItemManager.SPEC_MOD, ModItemManager.VANISH, ModItemManager.UN_VANISH, ModItemManager.FREEZE, ModItemManager.SUPER_PICKAXE, ModItemManager.KILL_SWORD};

                Arrays.stream(items).filter(item -> item.getItemMeta().getDisplayName().equals(displayName)).map(item -> true).forEach(event::setCancelled);
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onMoveItemItems(InventoryMoveItemEvent event) {
        final ItemStack it = event.getItem();

        if (it == null) return;
        if (!it.hasItemMeta()) return;

        final ItemMeta meta = it.getItemMeta();

        if (meta.hasDisplayName()) {
            final String displayName = meta.getDisplayName();

            final ItemStack[] items = new ItemStack[]{ItemManager.Item_StaffMenu, ModItemManager.KB_SWORD, ModItemManager.CHECK, ModItemManager.SPEC_MOD, ModItemManager.VANISH, ModItemManager.UN_VANISH, ModItemManager.FREEZE, ModItemManager.SUPER_PICKAXE, ModItemManager.KILL_SWORD};

            Arrays.stream(items).filter(item -> item.getItemMeta().getDisplayName().equals(displayName)).map(item -> true).forEach(event::setCancelled);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void whenDropItem(PlayerDropItemEvent event) {
        final ItemStack it = event.getItemDrop().getItemStack();

        if (it == null) return;
        if (!it.hasItemMeta()) return;

        final ItemMeta meta = it.getItemMeta();

        if (meta.hasDisplayName()) {
            final String displayName = meta.getDisplayName();

            final ItemStack[] items = new ItemStack[]{ItemManager.Item_StaffMenu, ModItemManager.KB_SWORD, ModItemManager.CHECK, ModItemManager.SPEC_MOD, ModItemManager.VANISH, ModItemManager.UN_VANISH, ModItemManager.FREEZE, ModItemManager.SUPER_PICKAXE, ModItemManager.KILL_SWORD};

            Arrays.stream(items).filter(item -> item.getItemMeta().getDisplayName().equals(displayName)).map(item -> true).forEach(event::setCancelled);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onMove(PlayerMoveEvent event) {
        final Player player = event.getPlayer();

        if (freezePlayers.contains(player)) {
            event.setCancelled(true);
        }
    }
}
