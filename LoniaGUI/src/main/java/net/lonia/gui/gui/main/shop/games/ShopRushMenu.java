package net.lonia.gui.gui.main.shop.games;

import net.lonia.core.LoniaCore;
import net.lonia.core.item.builder.ItemBuilder;
import net.lonia.core.item.manager.ItemManager;
import net.lonia.core.player.account.Account;
import net.lonia.core.player.account.UserData;
import net.lonia.core.utils.GuiBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShopRushMenu implements GuiBuilder {
    ItemStack MONEY;
    final ItemStack LVL1 = new ItemBuilder(Material.WOOD_PICKAXE).setName("§eNiveau 1").setLore("").toItemStack();
    final ItemStack LVL2 = new ItemBuilder(Material.STONE_PICKAXE).setName("§eNiveau 2").setLore("").toItemStack();
    final ItemStack LVL3 = new ItemBuilder(Material.GOLD_PICKAXE).setName("§eNiveau 3").setLore("").toItemStack();
    final ItemStack LVL4 = new ItemBuilder(Material.IRON_PICKAXE).setName("§eNiveau 4").setLore("").toItemStack();
    final ItemStack LVL5 = new ItemBuilder(Material.DIAMOND_PICKAXE).setName("§eNiveau 5").setLore("").toItemStack();
    @Override
    public String name() {
        return "§cBoutique du Rush";
    }

    @Override
    public int getSize() {
        return 27;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        final Account account = LoniaCore.get().getAccountManager().getAccount(player);
        UserData.DataEtoile dataEtoile = account.getUserData().getDataEtoile();
        UserData.DataPearl dataPearl = account.getUserData().getDataPearl();

        long etoile = dataEtoile.getEtoile();
        long pearl = dataPearl.getPearl();

        MONEY = new ItemBuilder(Material.EMERALD).setName("§eVotre argent" + etoile + pearl).setLore("").toItemStack();
        inv.setItem(11, LVL1);
        inv.setItem(12, LVL2);
        inv.setItem(13, LVL3);
        inv.setItem(14, LVL4);
        inv.setItem(15, LVL5);
        inv.setItem(26, ItemManager.CLOSE);

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
    }
}
