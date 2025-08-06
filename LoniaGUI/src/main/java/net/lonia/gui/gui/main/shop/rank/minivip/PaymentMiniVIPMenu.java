package net.lonia.gui.gui.main.shop.rank.minivip;

import net.lonia.core.LoniaCore;
import net.lonia.core.item.builder.ItemBuilder;
import net.lonia.core.item.manager.ItemManager;
import net.lonia.core.player.account.Account;
import net.lonia.core.player.account.DShop;
import net.lonia.core.rank.Rank;
import net.lonia.core.rank.Ranks;
import net.lonia.core.utils.GuiBuilder;
import net.lonia.gui.LoniaGui;
import net.lonia.gui.gui.GuiManager;
import net.lonia.gui.gui.main.shop.ShopMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PaymentMiniVIPMenu implements GuiBuilder {

    final ItemStack Payment_MiniVIP_Ico = new ItemBuilder(Material.REDSTONE).setName("§bMini-VIP").toItemStack();

    @Override
    public String name() {
        return "§7§lType de paiement";
    }

    @Override
    public int getSize() {
        return 27;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        inv.setItem(0, Payment_MiniVIP_Ico);

        inv.setItem(12, ItemManager.PaymentType_IG);
        inv.setItem(14, ItemManager.PaymentType_IRL);

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
        final Rank rank = dataShop.getRank();

        if (rank.getName().equals(Ranks.MINIVIP.getName())) {
            if (displayName.equals(ItemManager.PaymentType_IG.getItemMeta().getDisplayName())) {
                dataShop.setPayment_type("IG");
                guiManager.open(player, ConfirmationPaymentMiniVIP.class);
            } else if (displayName.equals(ItemManager.PaymentType_IRL.getItemMeta().getDisplayName())) {
                dataShop.setPayment_type("IRL");
                guiManager.open(player, ConfirmationPaymentMiniVIP.class);
            } else if (displayName.equals(ItemManager.RETURN.getItemMeta().getDisplayName())) {
                guiManager.open(player, ShopMenu.class);
            }
        }

    }
}
