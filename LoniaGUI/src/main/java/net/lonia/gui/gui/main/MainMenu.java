package net.lonia.gui.gui.main;

import net.lonia.core.item.builder.ItemBuilder;
import net.lonia.core.item.manager.ItemManager;
import net.lonia.core.utils.GuiBuilder;
import net.lonia.gui.LoniaGui;
import net.lonia.gui.gui.GuiManager;
import net.lonia.gui.gui.main.settings.SettingsMenu;
import net.lonia.gui.gui.main.stats.StatsMenu;
import net.lonia.gui.runnable.ItemsRunnable;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MainMenu implements GuiBuilder {

    private final ItemStack settings = new ItemBuilder(Material.DIODE).setName("§bParamètres").toItemStack();
    private final ItemStack stats = new ItemBuilder(Material.PAPER).setName("§bStatistiques").toItemStack();


    @Override
    public String name() {
        return "§9§lMenu principal";
    }

    @Override
    public int getSize() {
        return 36;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        inv.setItem(0, ItemsRunnable.LOBBY);
        inv.setItem(35, ItemManager.CLOSE);

        inv.setItem(11, ItemsRunnable.PVP);
        inv.setItem(12, ItemsRunnable.Freecube);
        inv.setItem(15, ItemsRunnable.Nexus);
        inv.setItem(14, ItemsRunnable.Rush);
        inv.setItem(21, ItemsRunnable.JumpAdventure);
        inv.setItem(23, ItemsRunnable.PixelWar);
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

        if (displayName.equals(ItemsRunnable.LOBBY.getItemMeta().getDisplayName())) {
            //guiManager.open(player, LobbyMenu.class);
        } else if (displayName.equals(stats.getItemMeta().getDisplayName())) {
            guiManager.open(player, StatsMenu.class);
        } else if (displayName.equals(settings.getItemMeta().getDisplayName())) {
            guiManager.open(player, SettingsMenu.class);
        } else if (displayName.equals(ItemsRunnable.PVP.getItemMeta().getDisplayName())) {
            guiManager.open(player, DUELMenu.class);
        } else if (displayName.equals(ItemsRunnable.Freecube.getItemMeta().getDisplayName())) {
            guiManager.open(player, FreecubeMenu.class);
        } else if (displayName.equals(ItemsRunnable.Nexus.getItemMeta().getDisplayName())) {
            guiManager.open(player, UNexusMenu.class);
        } else if (displayName.equals(ItemsRunnable.RunOrKill.getItemMeta().getDisplayName())) {
            guiManager.open(player, RunOrKillMenu.class);
        } else if (displayName.equals(ItemsRunnable.Rush.getItemMeta().getDisplayName())) {
            guiManager.open(player, RushMenu.class);
        } else if (displayName.equals(ItemsRunnable.JumpAdventure.getItemMeta().getDisplayName())) {
            guiManager.open(player, JumpAdventureMenu.class);
        } else if (displayName.equals(ItemsRunnable.PixelWar.getItemMeta().getDisplayName())) {
            //guiManager.open(player, PixelWarMenu.class);
        } else if (displayName.equals(ItemManager.CLOSE.getItemMeta().getDisplayName())) {
            player.closeInventory();
        }
    }
}

