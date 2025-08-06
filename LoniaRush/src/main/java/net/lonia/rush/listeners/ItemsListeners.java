package net.lonia.rush.listeners;

import net.lonia.core.item.builder.ItemBuilder;
import net.lonia.rush.LoniaRush;
import net.lonia.rush.gui.GuiManager;
import net.lonia.rush.gui.TeamGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemsListeners implements Listener {

    final ItemStack TEAMS = new ItemBuilder(Material.WOOL).setName("§9§lÉquipes").toItemStack();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final ItemStack it = player.getInventory().getItemInHand();

        if (it == null) return;
        if (!it.hasItemMeta()) return;

        final ItemMeta meta = it.getItemMeta();
        if (meta.hasDisplayName()) {
            final String displayName = meta.getDisplayName();
            final GuiManager guiManager = LoniaRush.INSTANCE.getGuiManager();

            if (displayName.equals(TEAMS.getItemMeta().getDisplayName())) {
                guiManager.open(player, TeamGUI.class);
            }
            event.setCancelled(true);
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        final ItemStack it = event.getCurrentItem();

        if (it == null) return;
        if (!it.hasItemMeta()) return;

        final ItemMeta meta = it.getItemMeta();

        if (meta.hasDisplayName()) {
            final String displayName = meta.getDisplayName();

            if (displayName.equals(TEAMS.getItemMeta().getDisplayName()))
                event.setCancelled(true);
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMoveItemItems(InventoryMoveItemEvent event) {
        final ItemStack it = event.getItem();

        if (it == null) return;
        if (!it.hasItemMeta()) return;

        final ItemMeta meta = it.getItemMeta();

        if (meta.hasDisplayName()) {
            final String displayName = meta.getDisplayName();

            if (displayName.equals(TEAMS.getItemMeta().getDisplayName()))
                event.setCancelled(true);
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void whenDropItem(PlayerDropItemEvent event) {
        final ItemStack it = event.getItemDrop().getItemStack();

        if (it == null) return;
        if (!it.hasItemMeta()) return;

        final ItemMeta meta = it.getItemMeta();

        if (meta.hasDisplayName()) {
            final String displayName = meta.getDisplayName();

            if (displayName.equals(TEAMS.getItemMeta().getDisplayName()))
                event.setCancelled(true);
        }

    }
}
