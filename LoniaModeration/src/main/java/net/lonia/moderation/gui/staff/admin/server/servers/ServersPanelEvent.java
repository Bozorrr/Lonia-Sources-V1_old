package net.lonia.moderation.gui.staff.admin.server.servers;

import net.lonia.core.item.builder.ItemBuilder;
import net.lonia.core.item.manager.ItemManager;
import net.lonia.core.utils.GuiBuilder;
import net.lonia.moderation.LoniaModeration;
import net.lonia.moderation.gui.GuiManager;
import net.lonia.moderation.gui.staff.admin.AdminMenu;
import net.lonia.moderation.gui.staff.admin.server.servers.sub.server.ServersBuildPanelEvent;
import net.lonia.moderation.gui.staff.admin.server.servers.sub.server.ServersHubPanelEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ServersPanelEvent implements GuiBuilder {

    public static final ItemStack serverList = new ItemBuilder(Material.COMMAND).setName("§bListe des serveurs").toItemStack();
    public static final ItemStack serversLobby = new ItemBuilder(Material.BEACON).setName("§3Lobby").toItemStack();
    public static final ItemStack serversBuild = new ItemBuilder(Material.BRICK).setName("§3Build").toItemStack();

    @Override
    public String name() {
        return "§7§lServers";
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        inv.setItem(0, serverList);

        inv.setItem(38, serversLobby);
        inv.setItem(41, serversBuild);

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

        if (displayName.equals(serversLobby.getItemMeta().getDisplayName())) {
            guiManager.open(player, ServersHubPanelEvent.class);
        } else if (displayName.equals(serversBuild.getItemMeta().getDisplayName())) {
            guiManager.open(player, ServersBuildPanelEvent.class);
        } else if (displayName.equals(ItemManager.RETURN.getItemMeta().getDisplayName())) {
            guiManager.open(player, AdminMenu.class);
        } else if (displayName.equals(ItemManager.CLOSE.getItemMeta().getDisplayName())) {
            player.closeInventory();
        }
    }
}
