package net.lonia.moderation.gui.staff.responsable;

import net.lonia.core.LoniaCore;
import net.lonia.core.item.builder.ItemBuilder;
import net.lonia.core.item.manager.ItemManager;
import net.lonia.core.message.SanctionMessages;
import net.lonia.core.player.account.Account;
import net.lonia.core.player.sanction.SanctionManager;
import net.lonia.core.rank.Rank;
import net.lonia.core.utils.GuiBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class UnBanGUI implements GuiBuilder {

    private final ItemStack ERRORJUGE = new ItemBuilder(Material.DIAMOND_SWORD).setName("§6Erreur de jugement").toItemStack();
    private final ItemStack ERRORNAME = new ItemBuilder(Material.GOLD_SWORD).setName("§6Erreur de pseudonyme").toItemStack();
    private final ItemStack CHANGEBAN = new ItemBuilder(Material.BOW).setName("§6Changement de sanction/durée").toItemStack();
    private final ItemStack ERRORPREUVES = new ItemBuilder(Material.BOOKSHELF).setName("§6Faute de preuves").toItemStack();
    private final ItemStack BUY = new ItemBuilder(Material.GOLD_INGOT).setName("§6Achat").toItemStack();
    private final ItemStack NAMECHANGED = new ItemBuilder(Material.NAME_TAG).setName("§6Pseudonyme changé").toItemStack();
    private final ItemStack NOREASON = new ItemBuilder(Material.GOLDEN_APPLE).setName("§6Non justifié - Autre").setLore("§5§l‣ §dAdministrateur").toItemStack();

    @Override
    public String name() {
        return "§6§lDé-bannir ";
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void contents(Player player, Inventory inv) {

        final Account account = LoniaCore.get().getAccountManager().getAccount(player);
        final String targetName = account.getDataModeration().getU();

        final UUID uuid = Account.getUUID(targetName);

        final String time = formatDuration(SanctionManager.getBanRemainingTime(uuid));
        final Rank rank = Account.getRank(uuid);
        final String reason = SanctionManager.getBanReason(uuid);


        inv.setItem(0, new ItemBuilder(Material.PAPER).setName("§c" + reason + "(" + time + ")").setLore(rank.getPrefix() + targetName).toItemStack());

        inv.setItem(11, ERRORJUGE);
        inv.setItem(12, ERRORNAME);
        inv.setItem(14, CHANGEBAN);
        inv.setItem(15, ERRORPREUVES);
        inv.setItem(21, BUY);
        inv.setItem(23, NAMECHANGED);
        inv.setItem(40, NOREASON);
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

        final Account account = LoniaCore.get().getAccountManager().getAccount(player);
        final UUID uuid = Account.getUUID(account.getDataModeration().getU());

        if (displayName.equals(ERRORJUGE.getItemMeta().getDisplayName())) {
            SanctionManager.unban(uuid, "Erreur de jugement");
            player.sendMessage(SanctionMessages.unban());
            player.closeInventory();
        } else if (displayName.equals(ERRORNAME.getItemMeta().getDisplayName())) {
            SanctionManager.unban(uuid, "Erreur de pseudonyme");
            player.sendMessage(SanctionMessages.unban());
            player.closeInventory();
        } else if (displayName.equals(CHANGEBAN.getItemMeta().getDisplayName())) {
            SanctionManager.unban(uuid, "Erreur de pseudonyme");
            player.sendMessage(SanctionMessages.unban());
            player.closeInventory();
        } else if (displayName.equals(ERRORPREUVES.getItemMeta().getDisplayName())) {
            SanctionManager.unban(uuid, "Faute de preuves");
            player.sendMessage(SanctionMessages.unban());
            player.closeInventory();
        } else if (displayName.equals(BUY.getItemMeta().getDisplayName())) {
            SanctionManager.unban(uuid, "Achat");
            player.sendMessage(SanctionMessages.unban());
            player.closeInventory();
        } else if (displayName.equals(NAMECHANGED.getItemMeta().getDisplayName())) {
            SanctionManager.unban(uuid, "Pseudonyme changé");
            player.sendMessage(SanctionMessages.unban());
            player.closeInventory();
        } else if (displayName.equals(NOREASON.getItemMeta().getDisplayName())) {
            SanctionManager.unban(uuid, "Non justifié - Autre");
            player.sendMessage(SanctionMessages.unban());
            player.closeInventory();
        }
    }

    String formatDuration(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if (days > 0) {
            return days + " d, " + (hours % 24) + " h";
        } else if (hours > 0) {
            return hours + " h, " + (minutes % 60) + " m";
        } else if (minutes > 0) {
            return minutes + "m, " + (seconds % 60) + " s";
        } else {
            return seconds + " s";
        }
    }
}
