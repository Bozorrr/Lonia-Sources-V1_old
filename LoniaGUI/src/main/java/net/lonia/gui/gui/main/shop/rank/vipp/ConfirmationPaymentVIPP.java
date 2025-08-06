package net.lonia.gui.gui.main.shop.rank.vipp;

import net.lonia.core.LoniaCore;
import net.lonia.core.item.builder.ItemBuilder;
import net.lonia.core.item.manager.ItemManager;
import net.lonia.core.message.MessageList;
import net.lonia.core.message.ShopMessages;
import net.lonia.core.player.account.Account;
import net.lonia.core.player.account.DShop;
import net.lonia.core.player.account.UserData;
import net.lonia.core.rank.Rank;
import net.lonia.core.rank.Ranks;
import net.lonia.core.utils.GuiBuilder;
import net.lonia.gui.LoniaGui;
import net.lonia.gui.gui.GuiManager;
import net.lonia.gui.gui.main.MainMenu;
import net.lonia.gui.gui.main.shop.rank.vip.PaymentVIPMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ConfirmationPaymentVIPP implements GuiBuilder {

    final ItemStack ConfirmationVIPP = new ItemBuilder(Material.GOLD_INGOT).setName("§bVIP+").toItemStack();

    @Override
    public String name() {
        return "§7§lVIP+";
    }

    @Override
    public int getSize() {
        return 27;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        inv.setItem(0, ConfirmationVIPP);

        inv.setItem(12, ItemManager.confirm);
        inv.setItem(14, ItemManager.cancel);

        inv.setItem(26, ItemManager.RETURN);
    }

    @Override
    public void onClick(Player player, Inventory inv, InventoryView invView, ItemStack current, int slot) {
        if (!invView.getTitle().equals(name())) return;

        if (!current.hasItemMeta()) return;

        final ItemMeta im = current.getItemMeta();

        if (!im.hasDisplayName()) return;

        final String displayName = im.getDisplayName();
        final GuiManager guiManager = LoniaGui.getInstance().getGuiManager();

        final Account account = LoniaCore.get().getAccountManager().getAccount(player);


        final DShop dataShop = account.getDataShop();
        final UserData.DataRank dataRank = account.getUserData().getDataRank();
        final UserData.DataEtoile dataEtoile = account.getUserData().getDataEtoile();
        final UserData.DataPearl dataPearl = account.getUserData().getDataPearl();

        final String payment_type = dataShop.getPayment_type();
        final Rank shopRank = dataShop.getRank();
        final Rank rank = Ranks.VIPP;

        if (displayName.equals(ItemManager.confirm.getItemMeta().getDisplayName())) {

            if (shopRank.getName().equals(rank.getName())) {

                if (dataRank.getRank().getPower() > shopRank.getPower()) {
                    if (payment_type.equals("IG")) {
                        if (dataEtoile.hasEtoile(rank.getEtoile())) {
                            dataEtoile.removeEtoile(rank.getEtoile());
                            dataRank.setRank(rank);
                            player.sendMessage(ShopMessages.buyRank(rank.getPrefix()));
                            player.closeInventory();
                        } else {
                            player.sendMessage(MessageList.notEnoughEtoile());
                        }
                    } else if (payment_type.equals("IRL")) {
                        if (dataPearl.hasPearl(rank.getArgent())) {
                            dataPearl.removePearl(rank.getArgent());
                            dataRank.setRank(rank);
                            player.sendMessage(ShopMessages.buyRank(rank.getPrefix()));
                            player.closeInventory();
                        } else {
                            player.sendMessage(MessageList.notEnoughArgent());
                        }
                    }
                    dataShop.setPayment_type(null);
                    dataShop.setRank(null);
                } else {
                    player.sendMessage(MessageList.lowerRank());
                }
            }
        } else if (displayName.equals(ItemManager.cancel.getItemMeta().getDisplayName())) {
            guiManager.open(player, MainMenu.class);
        } else if (displayName.equals(ItemManager.RETURN.getItemMeta().getDisplayName())) {
            if (shopRank.getName().equals(rank.getName())) {
                guiManager.open(player, PaymentVIPMenu.class);
            }
        }
    }
}
