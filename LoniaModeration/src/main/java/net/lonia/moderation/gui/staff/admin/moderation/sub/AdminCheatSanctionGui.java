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

public class AdminCheatSanctionGui implements GuiBuilder {

    public static final ItemStack cheatIco = new ItemBuilder(Material.DIAMOND_SWORD).setName("§cTriche").setLore("§63 sanctions-type").hideItemFlag().toItemStack();

    @Override
    public String name() {
        return "§c§l‣ Triche *";
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

        if (displayName.equals(ItemManager.RETURN.getItemMeta().getDisplayName())) {
            LoniaModeration.getInstance().getGuiManager().open(player, AdminModerationGui.class);
        }
    }
}
