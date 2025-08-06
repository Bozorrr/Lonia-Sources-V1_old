package net.lonia.gui.gui.main.shop;

import net.lonia.core.LoniaCore;
import net.lonia.core.item.builder.ItemBuilder;
import net.lonia.core.item.manager.ItemManager;
import net.lonia.core.message.MessageList;
import net.lonia.core.player.account.Account;
import net.lonia.core.player.account.DShop;
import net.lonia.core.rank.Ranks;
import net.lonia.core.utils.GuiBuilder;
import net.lonia.gui.LoniaGui;
import net.lonia.gui.gui.GuiManager;
import net.lonia.gui.gui.main.shop.rank.heros.PaymentHerosMenu;
import net.lonia.gui.gui.main.shop.rank.legend.PaymentLegendMenu;
import net.lonia.gui.gui.main.shop.rank.minivip.PaymentMiniVIPMenu;
import net.lonia.gui.gui.main.shop.rank.vip.PaymentVIPMenu;
import net.lonia.gui.gui.main.shop.rank.vipp.PaymentVIPPMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShopMenu implements GuiBuilder {

    final ItemStack Shop_Argent_Ico = new ItemBuilder(Material.PAPER).setName("§bArgent").toItemStack();

    final ItemStack Shop_Player = new ItemBuilder(Material.IRON_INGOT).setName("§7§lJoueur").toItemStack();
    final ItemStack Shop_MiniVIP = new ItemBuilder(Material.REDSTONE).setName("§9§lMini-VIP").toItemStack();
    final ItemStack Shop_VIP = new ItemBuilder(Material.INK_SACK, 1, 4).setName("§b§lVIP").toItemStack();
    final ItemStack Shop_VIPP = new ItemBuilder(Material.GOLD_INGOT).setName("§2§lVIP§a§l+").toItemStack();
    final ItemStack Shop_HEROS = new ItemBuilder(Material.DIAMOND).setName("§a§lHéros").toItemStack();
    final ItemStack Shop_LEGEND = new ItemBuilder(Material.EMERALD).setName("§e§lLégende").toItemStack();

    final ItemStack Shop_Youtuber = new ItemBuilder(Material.GOLDEN_CARROT).setName("§9Youtubeur").toItemStack();
    final ItemStack Shop_Staff = new ItemBuilder(Material.NETHER_BRICK).setName("§9STAFF").toItemStack();

    final ItemStack Shop_Boosters = new ItemBuilder(Material.MAGMA_CREAM).setName("§3Boosters").toItemStack();
    final ItemStack Shop_Particles = new ItemBuilder(Material.GLOWSTONE_DUST).setName("§3Particules").toItemStack();
    final ItemStack Shop_Game_Shop = new ItemBuilder(Material.GOLD_NUGGET).setName("§3Boutique de jeux").toItemStack();
    final ItemStack Shop_BOX = new ItemBuilder(Material.CHEST).setName("§3BOX").toItemStack();


    @Override
    public String name() {
        return "§e§lBoutique";
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        inv.setItem(0, Shop_Argent_Ico);

        inv.setItem(20, Shop_Player);
        inv.setItem(11, Shop_MiniVIP);
        inv.setItem(12, Shop_VIP);
        inv.setItem(13, Shop_VIPP);
        inv.setItem(14, Shop_HEROS);
        inv.setItem(15, Shop_LEGEND);

        inv.setItem(22, Shop_Youtuber);
        inv.setItem(24, Shop_Staff);

        inv.setItem(38, Shop_Boosters);
        inv.setItem(39, Shop_Particles);

        inv.setItem(41, Shop_Game_Shop);
        inv.setItem(42, Shop_BOX);

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
        final GuiManager guiManager = LoniaGui.getInstance().getGuiManager();

        final Account account = LoniaCore.get().getAccountManager().getAccount(player);

        final DShop dataShop = account.getDataShop();

        if (displayName.equals(Shop_MiniVIP.getItemMeta().getDisplayName())) {
            dataShop.setRank(Ranks.MINIVIP);
            guiManager.open(player, PaymentMiniVIPMenu.class);
        } else if (displayName.equals(Shop_VIP.getItemMeta().getDisplayName())) {
            dataShop.setRank(Ranks.VIP);
            guiManager.open(player, PaymentVIPMenu.class);
        } else if (displayName.equals(Shop_VIPP.getItemMeta().getDisplayName())) {
            dataShop.setRank(Ranks.VIPP);
            guiManager.open(player, PaymentVIPPMenu.class);
        } else if (displayName.equals(Shop_HEROS.getItemMeta().getDisplayName())) {
            dataShop.setRank(Ranks.HEROS);
            guiManager.open(player, PaymentHerosMenu.class);
        } else if (displayName.equals(Shop_LEGEND.getItemMeta().getDisplayName())) {
            dataShop.setRank(Ranks.LEGENDY);
            guiManager.open(player, PaymentLegendMenu.class);
        } else if (displayName.equals(Shop_Boosters.getItemMeta().getDisplayName())) {
            player.closeInventory();
            player.sendMessage(MessageList.invalidOption());
        } else if (displayName.equals(Shop_Particles.getItemMeta().getDisplayName())) {
            player.closeInventory();
            player.sendMessage(MessageList.invalidOption());
        } else if (displayName.equals(Shop_Game_Shop.getItemMeta().getDisplayName())) {
            player.closeInventory();
            player.sendMessage(MessageList.invalidOption());
        } else if (displayName.equals(Shop_BOX.getItemMeta().getDisplayName())) {
            player.closeInventory();
            player.sendMessage(MessageList.invalidOption());
        } else if (displayName.equals(ItemManager.CLOSE.getItemMeta().getDisplayName())) {
            player.closeInventory();
        }
    }

}
