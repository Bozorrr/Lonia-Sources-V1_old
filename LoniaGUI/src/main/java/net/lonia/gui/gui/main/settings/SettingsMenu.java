package net.lonia.gui.gui.main.settings;

import net.lonia.core.LoniaCore;
import net.lonia.core.item.builder.ItemBuilder;
import net.lonia.core.item.manager.ItemManager;
import net.lonia.core.message.SettingMessages;
import net.lonia.core.player.account.Account;
import net.lonia.core.player.account.UserData;
import net.lonia.core.utils.GuiBuilder;
import net.lonia.gui.LoniaGui;
import net.lonia.gui.gui.GuiManager;
import net.lonia.gui.gui.main.MainMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SettingsMenu implements GuiBuilder {

    ItemStack Settings_All = null;
    ItemStack Settings_MP = null;
    ItemStack Settings_Friends_Request = null;
    ItemStack Settings_Group_Request = null;
    ItemStack Settings_Follow_Group = null;
    ItemStack Settings_Particles_Effects = null;

    @Override
    public String name() {
        return "§7§lParamètres";
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        final Account account = LoniaCore.get().getAccountManager().getAccount(player);
        final UserData.DataSettings dataSettings = account.getUserData().getDataSettings();

        Settings_All = new ItemBuilder(Material.BEACON).setName("§bTout").toItemStack();
        Settings_MP = new ItemBuilder(Material.PAPER).setName("§9Messages privés").setLore("Actuellement " + (dataSettings.enabled_mp == 1 ? "activé" : "désactivé")).toItemStack();
        Settings_Friends_Request = new ItemBuilder(Material.ARROW).setName("§9Demandes d'amis").setLore("Actuellement " + (dataSettings.enabled_friends_request == 1 ? "activé" : "désactivé")).toItemStack();
        Settings_Group_Request = new ItemBuilder(Material.BLAZE_ROD).setName("§9Demandes de groupe").setLore("Actuellement " + (dataSettings.enabled_group_request == 1 ? "activé" : "désactivé")).toItemStack();
        Settings_Follow_Group = new ItemBuilder(Material.FEATHER).setName("§9Suivre la capitaine du groupe").setLore("Actuellement " + (dataSettings.enabled_following_group == 1 ? "activé" : "désactivé")).toItemStack();
        Settings_Particles_Effects = new ItemBuilder(Material.GLOWSTONE_DUST).setName("§9Effets de particules").setLore("Actuellement " + (dataSettings.enabled_show_particle_effects == 1 ? "activé" : "désactivé")).toItemStack();


        inv.setItem(0, Settings_All);
        inv.setItem(53, ItemManager.RETURN);

        inv.setItem(11, Settings_MP);
        inv.setItem(12, Settings_Friends_Request);
        inv.setItem(13, Settings_Group_Request);
        inv.setItem(14, Settings_Follow_Group);
        inv.setItem(15, Settings_Particles_Effects);
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
        final GuiManager guiManager = LoniaGui.getInstance().getGuiManager();
        final Account account = LoniaCore.get().getAccountManager().getAccount(player);

        if (displayName.equals(Settings_MP.getItemMeta().getDisplayName())) {
            if (account.getUserData().getDataSettings().enabled_mp == 1) {
                account.getUserData().getDataSettings().setEnabled_mp(0);
                player.sendMessage(SettingMessages.mpToggle(true));
                player.closeInventory();
            } else {
                account.getUserData().getDataSettings().setEnabled_mp(1);
                player.sendMessage(SettingMessages.mpToggle(false));
                player.closeInventory();
            }
        } else if (displayName.equals(Settings_Friends_Request.getItemMeta().getDisplayName())) {
            if (account.getUserData().getDataSettings().enabled_friends_request == 1) {
                account.getUserData().getDataSettings().setEnabled_friends_request(0);
                player.sendMessage(SettingMessages.friendsRequestToggle(true));
                player.closeInventory();
            } else {
                account.getUserData().getDataSettings().setEnabled_friends_request(1);
                player.sendMessage(SettingMessages.friendsRequestToggle(false));
                player.closeInventory();
            }
        } else if (displayName.equals(Settings_Group_Request.getItemMeta().getDisplayName())) {
            if (account.getUserData().getDataSettings().enabled_group_request == 1) {
                account.getUserData().getDataSettings().setEnabled_group_request(0);
                player.sendMessage(SettingMessages.groupRequestToggle(true));
                player.closeInventory();
            } else {
                account.getUserData().getDataSettings().setEnabled_group_request(1);
                player.sendMessage(SettingMessages.groupRequestToggle(false));
                player.closeInventory();
            }
        } else if (displayName.equals(Settings_Follow_Group.getItemMeta().getDisplayName())) {
            if (account.getUserData().getDataSettings().enabled_following_group == 1) {
                account.getUserData().getDataSettings().setEnabled_following_group(0);
                player.sendMessage(SettingMessages.followGroupToggle(true));
                player.closeInventory();
            } else {
                account.getUserData().getDataSettings().setEnabled_following_group(1);
                player.sendMessage(SettingMessages.followGroupToggle(false));
                player.closeInventory();
            }
        } else if (displayName.equals(Settings_Particles_Effects.getItemMeta().getDisplayName())) {
            if (account.getUserData().getDataSettings().enabled_show_particle_effects == 1) {
                account.getUserData().getDataSettings().setEnabled_show_particle_effects(0);
                player.sendMessage(SettingMessages.particleEffectsToggle(true));
                player.closeInventory();
            } else {
                account.getUserData().getDataSettings().setEnabled_show_particle_effects(1);
                player.sendMessage(SettingMessages.particleEffectsToggle(false));
                player.closeInventory();
            }
        } else if (displayName.equals(ItemManager.RETURN.getItemMeta().getDisplayName())) {
            guiManager.open(player, MainMenu.class);
        }
        account.updateData();
    }
}
