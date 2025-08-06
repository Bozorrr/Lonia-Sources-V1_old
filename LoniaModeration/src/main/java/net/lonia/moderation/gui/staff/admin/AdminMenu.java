package net.lonia.moderation.gui.staff.admin;

import net.lonia.core.item.builder.ItemBuilder;
import net.lonia.core.item.manager.ItemManager;
import net.lonia.core.utils.GuiBuilder;
import net.lonia.moderation.LoniaModeration;
import net.lonia.moderation.gui.GuiManager;
import net.lonia.moderation.gui.staff.admin.moderation.AdminModerationGui;
import net.lonia.moderation.gui.staff.admin.server.servers.sub.ServersSubPanelEvent;
import net.lonia.moderation.gui.staff.admin.server.setupserv.sub.SetupServerSubPanelEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AdminMenu implements GuiBuilder {

    public static final ItemStack ico = new ItemBuilder(Material.FEATHER).setName("§4Menu Admin").toItemStack();
    public static final ItemStack servers = new ItemBuilder(Material.PAPER).setName("§6Liste des serveurs").toItemStack();
    public static final ItemStack severSetup = new ItemBuilder(Material.COMMAND).setName("§6Server-Setup").toItemStack();
    public static final ItemStack sanctionPanel = new ItemBuilder(Material.BOOK).setName("§cPanel de sanction").toItemStack();

    @Override
    public String name() {
        return "§4§lMenu Admin";
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        inv.setItem(0, ico);
        inv.setItem(11, servers);
        inv.setItem(12, severSetup);

        inv.setItem(13, ItemManager.UNOCCUPIED);
        inv.setItem(14, ItemManager.UNOCCUPIED);
        inv.setItem(15, ItemManager.UNOCCUPIED);
        inv.setItem(20, ItemManager.UNOCCUPIED);
        inv.setItem(21, ItemManager.UNOCCUPIED);
        inv.setItem(22, ItemManager.UNOCCUPIED);
        inv.setItem(23, ItemManager.UNOCCUPIED);
        inv.setItem(24, ItemManager.UNOCCUPIED);

        inv.setItem(38, sanctionPanel);

        inv.setItem(39, ItemManager.UNOCCUPIED);
        inv.setItem(40, ItemManager.UNOCCUPIED);
        inv.setItem(41, ItemManager.UNOCCUPIED);
        inv.setItem(42, ItemManager.UNOCCUPIED);

        inv.setItem(53, ItemManager.CLOSE);
    }

    @Override
    public void onClick(Player player, Inventory inv, InventoryView invView, ItemStack current, int slot) {
        if (!invView.getTitle().equals(name()))
            return;

        if (!current.hasItemMeta())
            return;
        final ItemMeta im = current.getItemMeta();

        if (!im.hasDisplayName())
            return;
        final String displayName = im.getDisplayName();
        final GuiManager guiManager = LoniaModeration.getInstance().getGuiManager();

        if (displayName.equals(servers.getItemMeta().getDisplayName()))
            guiManager.open(player, ServersSubPanelEvent.class);
        else if (displayName.equals(severSetup.getItemMeta().getDisplayName()))
            guiManager.open(player, SetupServerSubPanelEvent.class);
        else if (displayName.equals(sanctionPanel.getItemMeta().getDisplayName()))
            guiManager.open(player, AdminModerationGui.class);
        else if (displayName.equals(ItemManager.CLOSE.getItemMeta().getDisplayName()))
            player.closeInventory();
    }
}
