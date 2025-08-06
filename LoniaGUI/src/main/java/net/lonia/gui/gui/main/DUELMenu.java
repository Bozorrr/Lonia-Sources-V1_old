package net.lonia.gui.gui.main;

import net.lonia.core.item.builder.ItemBuilder;
import net.lonia.core.item.manager.ItemManager;
import net.lonia.core.server.it.ServerItemManager;
import net.lonia.core.utils.GuiBuilder;
import net.lonia.gui.LoniaGui;
import net.lonia.gui.gui.GuiManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DUELMenu implements GuiBuilder {

    private final ItemStack PVP_ICO = new ItemBuilder(Material.IRON_SWORD).setName("§bPVP - Arènes/Duels").hideItemFlag().toItemStack();

    @Override
    public String name() {
        return "§7§lPVP - Arènes/Duels";
    }

    @Override
    public int getSize() {
        return 27;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        inv.setItem(0, PVP_ICO);
        inv.setItem(11, ServerItemManager.DUELPVP);
        inv.setItem(13, ServerItemManager.ARENEC);
        inv.setItem(15, ServerItemManager.ARENEE);
        inv.setItem(26, ItemManager.RETURN);
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
        final GuiManager guiManager = LoniaGui.getInstance().getGuiManager();

        if (displayName.equals(ItemManager.RETURN.getItemMeta().getDisplayName())) {
            guiManager.open(player, MainMenu.class);
        }
    }
}
