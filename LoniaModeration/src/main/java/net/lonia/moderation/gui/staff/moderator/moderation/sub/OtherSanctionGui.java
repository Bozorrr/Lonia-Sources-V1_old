package net.lonia.moderation.gui.staff.moderator.moderation.sub;

import net.lonia.core.LoniaCore;
import net.lonia.core.item.builder.ItemBuilder;
import net.lonia.core.item.manager.ItemManager;
import net.lonia.core.player.account.Account;
import net.lonia.core.player.sanction.SanctionManager;
import net.lonia.core.utils.GuiBuilder;
import net.lonia.moderation.LoniaModeration;
import net.lonia.moderation.gui.staff.moderator.moderation.ModerationGui;
import net.lonia.moderation.manager.Sanctions;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

import static net.lonia.core.item.manager.ModerationItemManager.*;
import static net.lonia.moderation.manager.Sanctions.getCurrentDate;

public class OtherSanctionGui implements GuiBuilder {

    public static final ItemStack otherIco = new ItemBuilder(Material.LAVA_BUCKET).setName("§cAutre").setLore("§63 sanctions-type").toItemStack();


    @Override
    public String name() {
        return "§c§l‣ Autre";
    }


    @Override
    public int getSize() {
        return 27;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        inv.setItem(0, otherIco);
        inv.setItem(11, spamReportI);
        inv.setItem(12, spamReportII);
        inv.setItem(15, HPDA);
        inv.setItem(26, ItemManager.RETURN);
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

        final String targetName = LoniaCore.get().getAccountManager().getAccount(player).getDataModeration().getReportedPlayer();
        final UUID targetUUID = Account.getUUID(targetName);

        if (displayName.equals(spamReportI.getItemMeta().getDisplayName())) {
            SanctionManager.ban(targetUUID, targetName, Sanctions.REPORT_ABUSE.getDuration(), getCurrentDate(), player.getDisplayName(), Sanctions.REPORT_ABUSE.getName());
        } else if (displayName.equals(spamReportII.getItemMeta().getDisplayName())) {
            SanctionManager.ban(targetUUID, targetName, Sanctions.REPORT_ABUSE2.getDuration(), getCurrentDate(), player.getDisplayName(), Sanctions.REPORT_ABUSE2.getName());
        } else if (displayName.equals(HPDA.getItemMeta().getDisplayName())) {
            SanctionManager.ban(targetUUID, targetName, Sanctions.DDOS_HACK.getDuration(), getCurrentDate(), player.getDisplayName(), Sanctions.DDOS_HACK.getName());
        } else if (displayName.equals(ItemManager.RETURN.getItemMeta().getDisplayName()))
            LoniaModeration.getInstance().getGuiManager().open(player, ModerationGui.class);
    }
}
