package net.lonia.moderation.gui.staff.admin.server.servers.sub.server;

import net.lonia.core.item.builder.ItemBuilder;
import net.lonia.core.item.manager.ItemManager;
import net.lonia.core.server.ServerManager;
import net.lonia.core.server.it.ServerItemManager;
import net.lonia.core.utils.GuiBuilder;
import net.lonia.moderation.LoniaModeration;
import net.lonia.moderation.gui.staff.admin.server.servers.ServersPanelEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ServersHubPanelEvent implements GuiBuilder {

    public static final ItemStack lobbyIco = new ItemBuilder(Material.BEACON).setName("§bLobby").toItemStack();


    @Override
    public String name() {
        return "§7§lHub";
    }

    @Deprecated
    public int getSize() {
        return 54;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        inv.setItem(0, lobbyIco);
        inv.setItem(11, ServerItemManager.LOBBY1);
        inv.setItem(53, ItemManager.RETURN);
    }

    @Deprecated
    public void onClick(Player player, Inventory inv, InventoryView invView, ItemStack current, int slot) {
        if (!invView.getTitle().equals(name())) return;

        if (!current.hasItemMeta()) return;
        final ItemMeta im = current.getItemMeta();

        if (!im.hasDisplayName()) return;
        final String displayName = im.getDisplayName();

        if (displayName.equalsIgnoreCase(ServerItemManager.LOBBY1.getItemMeta().getDisplayName()))
            ServerManager.connectToServer(ServerManager.lobby1, player);
        else if (displayName.equals(ItemManager.RETURN.getItemMeta().getDisplayName()))
            LoniaModeration.getInstance().getGuiManager().open(player, ServersPanelEvent.class);
    }
}
