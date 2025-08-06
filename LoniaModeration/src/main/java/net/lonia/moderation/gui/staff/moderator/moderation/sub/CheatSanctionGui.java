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

public class CheatSanctionGui implements GuiBuilder {

    public static final ItemStack cheatIco = new ItemBuilder(Material.DIAMOND_SWORD).setName("§cTriche").setLore("§63 sanctions-type").hideItemFlag().toItemStack();


    @Override
    public String name() {
        return "§c§l‣ Triche";
    }

    @Override
    public int getSize() {
        return 27;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        inv.setItem(0, cheatIco);
        inv.setItem(12, CPSLimitExceededI);
        inv.setItem(13, CPSLimitExceededII);
        inv.setItem(14, HackClient);
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

        if (displayName.equals(CPSLimitExceededI.getItemMeta().getDisplayName())) {
            SanctionManager.ban(targetUUID, targetName, Sanctions.CPS_LIMIT.getDuration(), getCurrentDate(), player.getDisplayName(), Sanctions.CPS_LIMIT.getName());
        } else if (displayName.equals(CPSLimitExceededII.getItemMeta().getDisplayName())) {
            SanctionManager.ban(targetUUID, targetName, Sanctions.CPS_LIMIT2.getDuration(), getCurrentDate(), player.getDisplayName(), Sanctions.CPS_LIMIT2.getName());
        } else if (displayName.equals(HackClient.getItemMeta().getDisplayName())) {
            SanctionManager.ban(targetUUID, targetName, Sanctions.CHEAT.getDuration(), getCurrentDate(), player.getDisplayName(), Sanctions.CHEAT.getName());
        }
        if (displayName.equals(ItemManager.RETURN.getItemMeta().getDisplayName()))
            LoniaModeration.getInstance().getGuiManager().open(player, ModerationGui.class);
    }
}
