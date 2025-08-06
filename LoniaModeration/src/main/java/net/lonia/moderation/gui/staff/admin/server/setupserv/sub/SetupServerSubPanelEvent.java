package net.lonia.moderation.gui.staff.admin.server.setupserv.sub;

import net.lonia.core.item.builder.ItemBuilder;
import net.lonia.core.item.manager.ItemManager;
import net.lonia.core.utils.GuiBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class SetupServerSubPanelEvent implements GuiBuilder {

    public static final ItemStack SETUPSERV_ICO = new ItemBuilder(Material.COMMAND).setName("§bServer-setup").toItemStack();

    public static final ItemStack LOBBY = new ItemBuilder(Material.BEACON).setName("§9Lobby").toItemStack();
    public static final ItemStack DUEL = new ItemBuilder(Material.IRON_SWORD).setName("§9Duel").hideItemFlag().toItemStack();
    public static final ItemStack ARENEC = new ItemBuilder(Material.DIAMOND_CHESTPLATE).setName("§9Arène PVP - Classique").toItemStack();
    public static final ItemStack ARENEE = new ItemBuilder(Material.POTION, 1, 16385).setName("§9Arène PVP - Effects").hideItemFlag().toItemStack();
    public static final ItemStack FREECUBE = new ItemBuilder(Material.GRASS).setName("§9Freecube").toItemStack();
    public static final ItemStack UNEXUS = new ItemBuilder(Material.BLAZE_ROD).setName("§9U-Nexus").toItemStack();
    public static final ItemStack RUNORKILL = new ItemBuilder(Material.SNOW_BALL).setName("§9RUN or KILL").toItemStack();
    public static final ItemStack RUSH = new ItemBuilder(Material.BED).setName("§9Rush").toItemStack();
    public static final ItemStack JUMPADVENTURE = new ItemBuilder(Material.LADDER).setName("§9Jump/Aventure").toItemStack();
    public static final ItemStack PIXELWAR = new ItemBuilder(Material.WOOL).setName("§9Pixel War").toItemStack();
    public static final ItemStack BUILD = new ItemBuilder(Material.BRICK).setName("§3Build").toItemStack();

    @Override
    public String name() {
        return "§7§lServer-setup";
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        inv.setItem(0, SETUPSERV_ICO);

        inv.setItem(11, DUEL);
        inv.setItem(13, ARENEC);
        inv.setItem(15, ARENEE);
        inv.setItem(21, LOBBY);
        inv.setItem(23, FREECUBE);
        inv.setItem(29, UNEXUS);
        inv.setItem(31, RUNORKILL);
        inv.setItem(33, RUSH);
        inv.setItem(39, JUMPADVENTURE);
        inv.setItem(41, PIXELWAR);
        inv.setItem(49, BUILD);

        inv.setItem(53, ItemManager.RETURN);
    }

    @Override
    public void onClick(Player player, Inventory inv, InventoryView invView, ItemStack current, int slot) {
    }
}
