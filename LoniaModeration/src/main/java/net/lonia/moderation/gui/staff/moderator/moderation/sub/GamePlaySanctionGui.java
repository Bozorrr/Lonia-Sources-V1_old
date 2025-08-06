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


public class GamePlaySanctionGui implements GuiBuilder {

    public static final ItemStack gamePlayIco = new ItemBuilder(Material.FLINT_AND_STEEL).setName("§cGamePlay").setLore("§67 sanctions-type").toItemStack();

    @Override
    public String name() {
        return "§c§l‣ GamePlay";
    }

    @Override
    public int getSize() {
        return 36;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        inv.setItem(0, gamePlayIco);
        inv.setItem(11, invalidBuild1);
        inv.setItem(12, invalidBuild2);
        inv.setItem(14, antiGamePlay);
        inv.setItem(15, crossTeam);
        inv.setItem(20, invalidConnection);
        inv.setItem(22, grief);
        inv.setItem(24, boosting);
        inv.setItem(35, ItemManager.RETURN);
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

        if (displayName.equals(invalidBuild1.getItemMeta().getDisplayName())) {
            SanctionManager.ban(targetUUID, targetName, Sanctions.BAD_BUILD.getDuration(), getCurrentDate(), player.getDisplayName(), Sanctions.BAD_BUILD.getName());
        } else if (displayName.equals(invalidBuild2.getItemMeta().getDisplayName())) {
            SanctionManager.ban(targetUUID, targetName, Sanctions.BAD_BUILD2.getDuration(), getCurrentDate(), player.getDisplayName(), Sanctions.BAD_BUILD2.getName());
        } else if (displayName.equals(antiGamePlay.getItemMeta().getDisplayName())) {
            SanctionManager.ban(targetUUID, targetName, Sanctions.SABOTAGE.getDuration(), getCurrentDate(), player.getDisplayName(), Sanctions.SABOTAGE.getName());
        } else if (displayName.equals(crossTeam.getItemMeta().getDisplayName())) {
            SanctionManager.ban(targetUUID, targetName, Sanctions.CROSSTEAM.getDuration(), getCurrentDate(), player.getDisplayName(), Sanctions.CROSSTEAM.getName());
        } else if (displayName.equals(invalidConnection.getItemMeta().getDisplayName())) {
            SanctionManager.ban(targetUUID, targetName, Sanctions.BAD_CO.getDuration(), getCurrentDate(), player.getDisplayName(), Sanctions.BAD_CO.getName());
        } else if (displayName.equals(grief.getItemMeta().getDisplayName())) {
            SanctionManager.ban(targetUUID, targetName, Sanctions.GRIEF.getDuration(), getCurrentDate(), player.getDisplayName(), Sanctions.GRIEF.getName());
        } else if (displayName.equals(boosting.getItemMeta().getDisplayName())) {
            SanctionManager.ban(targetUUID, targetName, Sanctions.BOOST_STATS.getDuration(), getCurrentDate(), player.getDisplayName(), Sanctions.BOOST_STATS.getName());
        }
        if (displayName.equals(ItemManager.RETURN.getItemMeta().getDisplayName()))
            LoniaModeration.getInstance().getGuiManager().open(player, ModerationGui.class);
    }
}
