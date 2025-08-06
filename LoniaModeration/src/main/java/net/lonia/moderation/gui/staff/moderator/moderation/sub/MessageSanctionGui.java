package net.lonia.moderation.gui.staff.moderator.moderation.sub;

import net.lonia.core.LoniaCore;
import net.lonia.core.item.builder.ItemBuilder;
import net.lonia.core.item.manager.ItemManager;
import net.lonia.core.player.account.Account;
import net.lonia.core.player.sanction.SanctionManager;
import net.lonia.core.rank.PermissionLevel;
import net.lonia.core.utils.GuiBuilder;
import net.lonia.core.utils.Log;
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


public class MessageSanctionGui implements GuiBuilder {

    public static final ItemStack messageIco = new ItemBuilder(Material.PAPER).setName("§cMessage").setLore("§617 sanctions-type").toItemStack();

    @Override
    public String name() {
        return "§c§l‣ Message";
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        inv.setItem(0, messageIco);

        inv.setItem(11, BADMSG);
        inv.setItem(12, LIE);
        inv.setItem(13, FLOOD);
        inv.setItem(14, PROVOC);
        inv.setItem(15, INCIT);

        inv.setItem(20, BADLANGAGE);
        inv.setItem(21, INSULT);
        inv.setItem(22, TRASH);
        inv.setItem(24, CONTOURN);

        inv.setItem(29, SELL);
        inv.setItem(30, PUB);
        inv.setItem(32, DOOM);
        inv.setItem(33, DOOMBLOOD);

        inv.setItem(38, BADLINK);
        inv.setItem(39, TRASHLINK);
        inv.setItem(40, LIELINK);
        inv.setItem(42, DOX);

        inv.setItem(53, ItemManager.RETURN);
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

        if (!Account.getRank(player.getUniqueId()).hasPermissionLevel(PermissionLevel.RESPONSSABLE)) return;

        final String targetName = LoniaCore.get().getAccountManager().getAccount(player).getDataModeration().getReportedPlayer();
        final UUID targetUUID = Account.getUUID(targetName);

        if (displayName.equals(BADMSG.getItemMeta().getDisplayName())) {
            SanctionManager.mute(targetUUID, targetName, Sanctions.BAD_MSG.getDuration(), getCurrentDate(), player.getDisplayName(), Sanctions.BAD_MSG.getName());
            player.closeInventory();
        }
        else if (displayName.equals(LIE.getItemMeta().getDisplayName())) {
            SanctionManager.mute(targetUUID, targetName, Sanctions.LIE.getDuration(), getCurrentDate(), player.getDisplayName(), Sanctions.LIE.getName());
            player.closeInventory();
        }
        else if (displayName.equals(FLOOD.getItemMeta().getDisplayName())) {
            SanctionManager.mute(targetUUID, targetName, Sanctions.FLOOD.getDuration(), getCurrentDate(), player.getDisplayName(), Sanctions.FLOOD.getName());
            player.closeInventory();
        }
        else if (displayName.equals(PROVOC.getItemMeta().getDisplayName())) {
            SanctionManager.mute(targetUUID, targetName, Sanctions.PROVOCATION.getDuration(), getCurrentDate(), player.getDisplayName(), Sanctions.PROVOCATION.getName());
            player.closeInventory();
        }
        else if (displayName.equals(INCIT.getItemMeta().getDisplayName())) {
            SanctionManager.mute(targetUUID, targetName, Sanctions.INCITE_INFRACTION.getDuration(), getCurrentDate(), player.getDisplayName(), Sanctions.INCITE_INFRACTION.getName());
            player.closeInventory();
        }
        else if (displayName.equals(BADLANGAGE.getItemMeta().getDisplayName())) {
            SanctionManager.mute(targetUUID, targetName, Sanctions.BAD_LANG.getDuration(), getCurrentDate(), player.getDisplayName(), Sanctions.BAD_LANG.getName());
            player.closeInventory();
        }
        else if (displayName.equals(INSULT.getItemMeta().getDisplayName())) {
            SanctionManager.mute(targetUUID, targetName, Sanctions.INSULT.getDuration(), getCurrentDate(), player.getDisplayName(), Sanctions.INSULT.getName());
            player.closeInventory();
        }
        else if (displayName.equals(TRASH.getItemMeta().getDisplayName())) {
            SanctionManager.ban(targetUUID, targetName, Sanctions.TRASH.getDuration(), getCurrentDate(), player.getDisplayName(), Sanctions.TRASH.getName());
            player.closeInventory();
        }
        else if (displayName.equals(CONTOURN.getItemMeta().getDisplayName())) {
            SanctionManager.mute(targetUUID, targetName, Sanctions.BYPASS_ATTEMPT.getDuration(), getCurrentDate(), player.getDisplayName(), Sanctions.BYPASS_ATTEMPT.getName());
            player.closeInventory();
        }
        else if (displayName.equals(SELL.getItemMeta().getDisplayName())) {
            SanctionManager.ban(targetUUID, targetName, Sanctions.SELL.getDuration(), getCurrentDate(), player.getDisplayName(), Sanctions.SELL.getName());
            player.closeInventory();
        }
        else if (displayName.equals(PUB.getItemMeta().getDisplayName())) {
            SanctionManager.ban(targetUUID, targetName, Sanctions.ILLEGAL_AD.getDuration(), getCurrentDate(), player.getDisplayName(), Sanctions.ILLEGAL_AD.getName());
            player.closeInventory();
        }
        else if (displayName.equals(DOOM.getItemMeta().getDisplayName())) {
            SanctionManager.ban(targetUUID, targetName, Sanctions.TRASHDDOS1.getDuration(), getCurrentDate(), player.getDisplayName(), Sanctions.TRASHDDOS1.getName());
            player.closeInventory();
        }
        else if (displayName.equals(DOOMBLOOD.getItemMeta().getDisplayName())) {
            SanctionManager.ban(targetUUID, targetName, Sanctions.TRASHDDOS2.getDuration(), getCurrentDate(), player.getDisplayName(), Sanctions.TRASHDDOS2.getName());
            player.closeInventory();
        }
        else if (displayName.equals(BADLINK.getItemMeta().getDisplayName())) {
            SanctionManager.ban(targetUUID, targetName, Sanctions.BAD_LINK.getDuration(), getCurrentDate(), player.getDisplayName(), Sanctions.BAD_LINK.getName());
            player.closeInventory();
        }
        else if (displayName.equals(TRASHLINK.getItemMeta().getDisplayName())) {
            SanctionManager.ban(targetUUID, targetName, Sanctions.BAD_LINK2.getDuration(), getCurrentDate(), player.getDisplayName(), Sanctions.BAD_LINK2.getName());
            player.closeInventory();
        }
        else if (displayName.equals(LIELINK.getItemMeta().getDisplayName())) {
            SanctionManager.ban(targetUUID, targetName, Sanctions.BAD_LINK3.getDuration(), getCurrentDate(), player.getDisplayName(), Sanctions.BAD_LINK3.getName());
            player.closeInventory();
        }
        else if (displayName.equals(DOX.getItemMeta().getDisplayName())) {
            SanctionManager.ban(targetUUID, targetName, Sanctions.PRIVATE_INFO_SHARE.getDuration(), getCurrentDate(), player.getDisplayName(), Sanctions.PRIVATE_INFO_SHARE.getName());
            player.closeInventory();
        }
        else if (displayName.equals(ItemManager.RETURN.getItemMeta().getDisplayName())) {
            LoniaModeration.getInstance().getGuiManager().open(player, ModerationGui.class);
        }
        else if (displayName.equals(ItemManager.CLOSE.getItemMeta().getDisplayName())) {
            player.closeInventory();
        }
    }
}
