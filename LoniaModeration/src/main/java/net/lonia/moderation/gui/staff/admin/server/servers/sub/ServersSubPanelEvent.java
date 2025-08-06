package net.lonia.moderation.gui.staff.admin.server.servers.sub;

import net.lonia.core.item.builder.ItemBuilder;
import net.lonia.core.item.manager.ItemManager;
import net.lonia.core.utils.GuiBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class ServersSubPanelEvent implements GuiBuilder {

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

        inv.setItem(53, ItemManager.RETURN);
    }

    @Override
    public void onClick(Player player, Inventory inventory, InventoryView inventoryView, ItemStack itemStack, int i) {

    }
}
