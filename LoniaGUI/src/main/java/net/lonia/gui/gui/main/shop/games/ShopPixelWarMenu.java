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

public class ShopPixelWarMenu implements GuiBuilder {
    ItemStack MONEY;
    final ItemStack ADDED_COLORS = new ItemBuilder(Material.STAINED_CLAY).setName("§eBlocs d'argile").setLore("").toItemStack();

    @Override
    public String name() {
        return "§fBoutique du PixelWar";
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

        inv.setItem(0, MONEY);
        inv.setItem(13, ADDED_COLORS);
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
