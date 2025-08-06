package net.lonia.gui.gui.listeners;

import net.lonia.core.LoniaCore;
import net.lonia.core.item.manager.ItemManager;
import net.lonia.core.server.it.ServerItemManager;
import net.lonia.gui.LoniaGui;
import net.lonia.gui.gui.GuiManager;
import net.lonia.gui.gui.main.MainMenu;
import net.lonia.gui.gui.main.shop.ShopMenu;
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

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final ItemStack it = player.getInventory().getItemInHand();

        if (it != null) {
            if (it.hasItemMeta()) {
                final ItemMeta meta = it.getItemMeta();
                if (meta.hasDisplayName()) {
                    final String displayName = meta.getDisplayName();
                    final GuiManager guiManager = LoniaGui.getInstance().getGuiManager();

                    if (displayName.equals(ItemManager.Main_Menu.getItemMeta().getDisplayName())) {
                        ServerItemManager.update();
                        guiManager.open(player, MainMenu.class);
                    } else if (displayName.equals(ItemManager.Shop.getItemMeta().getDisplayName()))
                        guiManager.open(player, ShopMenu.class);

                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryClick(InventoryClickEvent event) {
        final ItemStack it = event.getCurrentItem();

        final ItemStack[] ITs = new ItemStack[]{ItemManager.Main_Menu, ItemManager.Shop};


        if (it == null) return;

        if (it.getType() == Material.SKULL_ITEM) event.setCancelled(true);

        if (it.hasItemMeta()) {
            final ItemMeta meta = it.getItemMeta();

            if (meta.hasDisplayName()) {
                final String displayName = meta.getDisplayName();

                for (ItemStack itemStack : ITs) {
                    if (displayName.equals(itemStack.getItemMeta().getDisplayName())) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onMoveItemItems(InventoryMoveItemEvent event) {

        Player player = null;

        if (event.getSource().getHolder() instanceof Player)
            player = (Player) event.getSource().getHolder();
        if (event.getDestination().getHolder() instanceof Player)
            player = (Player) event.getDestination().getHolder();

        final ItemStack it = event.getItem();
        final ItemStack[] ITs = new ItemStack[]{ItemManager.Main_Menu, ItemManager.Shop, LoniaCore.get().getAccountManager().getAccount(player).getDataShop().getSKULL()};

        if (it == null) return;
        if (it.hasItemMeta()) {
            final ItemMeta meta = it.getItemMeta();

            if (meta.hasDisplayName()) {
                final String displayName = meta.getDisplayName();

                for (ItemStack itemStack : ITs) {
                    if (displayName.equals(itemStack.getItemMeta().getDisplayName())) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void whenDropItem(PlayerDropItemEvent event) {
        final ItemStack it = event.getItemDrop().getItemStack();
        final ItemStack[] ITs = new ItemStack[]{ItemManager.Main_Menu, ItemManager.Shop, LoniaCore.get().getAccountManager().getAccount(event.getPlayer()).getDataShop().getSKULL()};

        if (it == null) return;
        if (it.hasItemMeta()) {
            final ItemMeta meta = it.getItemMeta();

            if (meta.hasDisplayName()) {
                final String displayName = meta.getDisplayName();

                for (ItemStack itemStack : ITs) {
                    if (displayName.equals(itemStack.getItemMeta().getDisplayName())) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
