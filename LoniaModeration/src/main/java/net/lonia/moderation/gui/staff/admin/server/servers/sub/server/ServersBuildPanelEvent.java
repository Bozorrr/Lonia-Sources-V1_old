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

public class ServersBuildPanelEvent implements GuiBuilder {

    public static final ItemStack buildIco = new ItemBuilder(Material.BRICK).setName("§bBuild").toItemStack();


    @Override
    public String name() {
        return "§7§lBuild";
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        inv.setItem(0, buildIco);
        inv.setItem(11, ServerItemManager.BUILD1);
        inv.setItem(12, ServerItemManager.BUILD2);
        inv.setItem(13, ServerItemManager.BUILD3);
        inv.setItem(14, ServerItemManager.BUILD4);
        inv.setItem(15, ServerItemManager.BUILD5);
        inv.setItem(20, ServerItemManager.BUILD6);
        inv.setItem(21, ServerItemManager.BUILD7);
        inv.setItem(22, ServerItemManager.BUILD8);
        inv.setItem(23, ServerItemManager.BUILD9);
        inv.setItem(24, ServerItemManager.BUILD10);
        inv.setItem(29, ServerItemManager.BUILD11);
        inv.setItem(30, ServerItemManager.BUILD12);
        inv.setItem(53, ItemManager.RETURN);
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

        if (displayName.equalsIgnoreCase(ServerItemManager.BUILD1.getItemMeta().getDisplayName()))
            ServerManager.connectToServer(ServerManager.build1, player);
        else if (displayName.equalsIgnoreCase(ServerItemManager.BUILD2.getItemMeta().getDisplayName()))
            ServerManager.connectToServer(ServerManager.build2, player);
        else if (displayName.equalsIgnoreCase(ServerItemManager.BUILD3.getItemMeta().getDisplayName()))
            ServerManager.connectToServer(ServerManager.build3, player);
        else if (displayName.equalsIgnoreCase(ServerItemManager.BUILD4.getItemMeta().getDisplayName()))
            ServerManager.connectToServer(ServerManager.build4, player);
        else if (displayName.equalsIgnoreCase(ServerItemManager.BUILD5.getItemMeta().getDisplayName()))
            ServerManager.connectToServer(ServerManager.build5, player);
        else if (displayName.equalsIgnoreCase(ServerItemManager.BUILD6.getItemMeta().getDisplayName()))
            ServerManager.connectToServer(ServerManager.build6, player);
        else if (displayName.equalsIgnoreCase(ServerItemManager.BUILD7.getItemMeta().getDisplayName()))
            ServerManager.connectToServer(ServerManager.build7, player);
        else if (displayName.equalsIgnoreCase(ServerItemManager.BUILD8.getItemMeta().getDisplayName()))
            ServerManager.connectToServer(ServerManager.build8, player);
        else if (displayName.equalsIgnoreCase(ServerItemManager.BUILD9.getItemMeta().getDisplayName()))
            ServerManager.connectToServer(ServerManager.build9, player);
        else if (displayName.equalsIgnoreCase(ServerItemManager.BUILD10.getItemMeta().getDisplayName()))
            ServerManager.connectToServer(ServerManager.build10, player);
        else if (displayName.equalsIgnoreCase(ServerItemManager.BUILD11.getItemMeta().getDisplayName()))
            ServerManager.connectToServer(ServerManager.build11, player);
        else if (displayName.equalsIgnoreCase(ServerItemManager.BUILD12.getItemMeta().getDisplayName()))
            ServerManager.connectToServer(ServerManager.build12, player);
        if (displayName.equals(ItemManager.RETURN.getItemMeta().getDisplayName())) {
            LoniaModeration.getInstance().getGuiManager().open(player, ServersPanelEvent.class);
        }
    }
}
