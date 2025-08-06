package net.lonia.gui.gui.main.stats;

import net.lonia.core.item.builder.ItemBuilder;
import net.lonia.core.item.manager.ItemManager;
import net.lonia.core.utils.GuiBuilder;
import net.lonia.gui.LoniaGui;
import net.lonia.gui.gui.GuiManager;
import net.lonia.gui.gui.main.MainMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StatsMenu implements GuiBuilder {

    final ItemStack Stats_Global_Ico = new ItemBuilder(Material.COMPASS).setName("§bGlobal").toItemStack();

    final ItemStack Stats_PvP = new ItemBuilder(Material.IRON_SWORD).setName("§9PVP").hideItemFlag().toItemStack();
    final ItemStack Stats_RUN = new ItemBuilder(Material.FEATHER).setName("§9RUN").toItemStack();
    final ItemStack Stats_GUN = new ItemBuilder(Material.ARROW).setName("§9GUN").toItemStack();


    final ItemStack Stats_Jump = new ItemBuilder(Material.GRASS).setName("§3Jump").toItemStack();
    final ItemStack Stats_Event = new ItemBuilder(Material.APPLE).setName("§3Évènement").toItemStack();

    final ItemStack Stats_Reset = new ItemBuilder(Material.LAVA_BUCKET).setName("§cRéinitialiser").toItemStack();

    @Override
    public String name() {
        return "§7§lStatistiques";
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        inv.setItem(0, Stats_Global_Ico);

        inv.setItem(11, Stats_PvP);
        inv.setItem(15, Stats_RUN);
        inv.setItem(22, Stats_GUN);
        inv.setItem(39, Stats_Jump);
        inv.setItem(41, Stats_Event);

        inv.setItem(45, Stats_Reset);
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
        final GuiManager guiManager = LoniaGui.getInstance().getGuiManager();

        if (displayName.equals(Stats_PvP.getItemMeta().getDisplayName())) {
            //TODO: stats pvp
        } else if (displayName.equals(Stats_RUN.getItemMeta().getDisplayName())) {
            //TODO: stats run
        } else if (displayName.equals(Stats_GUN.getItemMeta().getDisplayName())) {
            //TODO: stats gun
        } else if (displayName.equals(Stats_Jump.getItemMeta().getDisplayName())) {
            //TODO: stats jump
        } else if (displayName.equals(Stats_Event.getItemMeta().getDisplayName())) {
            //TODO: stats event
        } else if (displayName.equals(Stats_Reset.getItemMeta().getDisplayName())) {
            guiManager.open(player, ResetState.class);
        } else if (displayName.equals(ItemManager.RETURN.getItemMeta().getDisplayName())) {
            guiManager.open(player, MainMenu.class);
        }
    }
}
