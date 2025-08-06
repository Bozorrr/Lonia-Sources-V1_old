package net.lonia.moderation.gui.player;

import net.lonia.core.LoniaCore;
import net.lonia.core.item.builder.ItemBuilder;
import net.lonia.core.item.manager.ItemManager;
import net.lonia.core.player.account.Account;
import net.lonia.core.utils.GuiBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ConfirmChatReport implements GuiBuilder {

    @Override
    public String name() {
        return "§7§lConfirmer le signalement du message";
    }

    @Override
    public int getSize() {
        return 27;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        Account account = LoniaCore.get().getAccountManager().getAccount(player);
        String targetName = account.getDataModeration().getReportedPlayer();
        String message = account.getDataModeration().getReportedMessage();

        inv.setItem(0, new ItemBuilder(Material.PAPER).setLore("§c" + targetName, "", "§f\"" + message + "§f\"").toItemStack());

        inv.setItem(12, ItemManager.confirm);
        inv.setItem(14, ItemManager.cancel);

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

        if (displayName.equals(ItemManager.confirm.getItemMeta().getDisplayName())) {
            //TODO: signaler le message et le joueur
            player.sendMessage("§bLe message a bien été signalé et sera vérifié prochainement par l'équipe de Lonia. Merci de votre signalement.");
        } else if (displayName.equals(ItemManager.cancel.getItemMeta().getDisplayName()) || displayName.equals(ItemManager.CLOSE.getItemMeta().getDisplayName())) {
            player.closeInventory();
            player.sendMessage("§3Vous avez abandonné le signalement du message.");
        }
    }
}
