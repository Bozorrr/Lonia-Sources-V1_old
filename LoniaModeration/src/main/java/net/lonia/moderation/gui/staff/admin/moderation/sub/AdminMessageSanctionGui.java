package net.lonia.moderation.gui.staff.admin.moderation.sub;

import net.lonia.core.item.builder.ItemBuilder;
import net.lonia.core.item.manager.ItemManager;
import net.lonia.core.utils.GuiBuilder;
import net.lonia.moderation.LoniaModeration;
import net.lonia.moderation.gui.staff.admin.moderation.AdminModerationGui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static net.lonia.core.item.manager.ModerationItemManager.*;

public class AdminMessageSanctionGui implements GuiBuilder {

    public static final ItemStack messageIco = new ItemBuilder(Material.PAPER).setName("§cMessage").setLore("§617 sanctions-type").toItemStack();

    @Override
    public String name() {
        return "§c§l‣ Message *";
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

        if (displayName.equals(ItemManager.RETURN.getItemMeta().getDisplayName())) {
            LoniaModeration.getInstance().getGuiManager().open(player, AdminModerationGui.class);
        }
    }
}
