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

public class ShopGamesMenu implements GuiBuilder {

    ItemStack MONEY;
    final ItemStack FREECUBE = new ItemBuilder(Material.GRASS).setName("§aFreecube").setLore("").toItemStack();
    final ItemStack RUSH = new ItemBuilder(Material.BED).setName("§cRush").setLore("").toItemStack();
    final ItemStack NEXUS = new ItemBuilder(Material.BLAZE_ROD).setName("§6Nexus").setLore("").toItemStack();
    final ItemStack PIXELWAR = new ItemBuilder(Material.WOOL).setName("§fPixelWar").setLore("").toItemStack();

    @Override
    public String name() {
        return "§eBoutique de jeux";
    }

    @Override
    public int getSize() {
        return 54;
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
        inv.setItem(13, FREECUBE);
        inv.setItem(21, RUSH);
        inv.setItem(23, NEXUS);
        inv.setItem(0, PIXELWAR);
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
    }
}
