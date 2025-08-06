package net.lonia.moderation.gui.staff.admin.moderation;

import net.lonia.core.item.builder.ItemBuilder;
import net.lonia.core.item.manager.ItemManager;
import net.lonia.core.utils.GuiBuilder;
import net.lonia.moderation.LoniaModeration;
import net.lonia.moderation.gui.GuiManager;
import net.lonia.moderation.gui.staff.admin.AdminMenu;
import net.lonia.moderation.gui.staff.admin.moderation.sub.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AdminModerationGui implements GuiBuilder {

    public static final ItemStack Sanction_SanctionPanel_Ico = new ItemBuilder(Material.BOOK).setName("§c§lPanel de sanction").toItemStack();

    public static final ItemStack Sanction_Message = new ItemBuilder(Material.PAPER).setName("§cMessage").toItemStack();
    public static final ItemStack Sanction_Profile = new ItemBuilder(Material.NAME_TAG).setName("§cProfil").toItemStack();
    public static final ItemStack Sanction_GamePlay = new ItemBuilder(Material.FLINT_AND_STEEL).setName("§cGamePlay").toItemStack();
    public static final ItemStack Sanction_Cheat = new ItemBuilder(Material.DIAMOND_SWORD).setName("§cTriche").hideItemFlag().toItemStack();
    public static final ItemStack Sanction_Other = new ItemBuilder(Material.LAVA_BUCKET).setName("§cAutre").toItemStack();

    @Override
    public String name() {
        return "§c§lPanel de sanction *";
    }

    @Override
    public int getSize() {
        return 27;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        inv.setItem(0, Sanction_SanctionPanel_Ico);
        inv.setItem(11, Sanction_Message);
        inv.setItem(12, Sanction_Profile);
        inv.setItem(13, Sanction_GamePlay);
        inv.setItem(14, Sanction_Cheat);
        inv.setItem(15, Sanction_Other);

        inv.setItem(26, ItemManager.RETURN);
    }

    public void onClick(Player player, Inventory inv, InventoryView invView, ItemStack current, int slot) {
        if (!invView.getTitle().equals(name()))
            return;

        if (!current.hasItemMeta())
            return;
        final ItemMeta im = current.getItemMeta();

        if (!im.hasDisplayName())
            return;
        final String displayName = im.getDisplayName();
        final GuiManager guiManager = LoniaModeration.getInstance().getGuiManager();

        if (displayName.equals(Sanction_Message.getItemMeta().getDisplayName()))
            guiManager.open(player, AdminMessageSanctionGui.class);
        else if (displayName.equals(Sanction_Profile.getItemMeta().getDisplayName()))
            guiManager.open(player, AdminProfileSanctionGui.class);
        else if (displayName.equals(Sanction_GamePlay.getItemMeta().getDisplayName()))
            guiManager.open(player, AdminGamePlaySanctionGui.class);
        else if (displayName.equals(Sanction_Cheat.getItemMeta().getDisplayName()))
            guiManager.open(player, AdminCheatSanctionGui.class);
        else if (displayName.equals(Sanction_Other.getItemMeta().getDisplayName()))
            guiManager.open(player, AdminOtherSanctionGui.class);
        else if (displayName.equals(ItemManager.RETURN.getItemMeta().getDisplayName()))
            guiManager.open(player, AdminMenu.class);
    }
}