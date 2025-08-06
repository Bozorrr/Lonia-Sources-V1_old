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

public class AdminGamePlaySanctionGui implements GuiBuilder {

    public static final ItemStack gamePlayIco = new ItemBuilder(Material.FLINT_AND_STEEL).setName("§cGamePlay").setLore("§67 sanctions-type").toItemStack();

    @Override
    public String name() {
        return "§c§l‣ GamePlay *";
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

        if (displayName.equals(ItemManager.RETURN.getItemMeta().getDisplayName())) {
            LoniaModeration.getInstance().getGuiManager().open(player, AdminModerationGui.class);
        }
    }
}
