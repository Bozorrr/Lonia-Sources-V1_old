package net.lonia.gui.gui.main;

import net.lonia.core.utils.GuiBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class LobbyMenu implements GuiBuilder {

    @Override
    public String name() {
        return "";
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public void contents(Player player, Inventory inv) {

    }

    @Override
    public void onClick(Player player, Inventory inv, InventoryView invView, ItemStack current, int slot) {

    }
}
