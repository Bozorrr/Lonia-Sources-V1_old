package net.lonia.rush.gui;

import net.lonia.core.item.builder.ItemBuilder;
import net.lonia.core.utils.GuiBuilder;
import net.lonia.rush.LoniaRush;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;

public class RushSetupGUI implements GuiBuilder {

    final ItemStack SO1 = new ItemBuilder(Material.GOLD_BLOCK).setName("Spawner Orange 1").toItemStack();
    final ItemStack SO2 = new ItemBuilder(Material.GOLD_BLOCK).setName("Spawner Orange 2").toItemStack();
    final ItemStack SJ1 = new ItemBuilder(Material.GOLD_BLOCK).setName("Spawner Jaune 1").toItemStack();
    final ItemStack SJ2 = new ItemBuilder(Material.GOLD_BLOCK).setName("Spawner Jaune 2").toItemStack();
    final ItemStack SV1 = new ItemBuilder(Material.GOLD_BLOCK).setName("Spawner Violet 1").toItemStack();
    final ItemStack SV2 = new ItemBuilder(Material.GOLD_BLOCK).setName("Spawner Violet 2").toItemStack();
    final ItemStack SR1 = new ItemBuilder(Material.GOLD_BLOCK).setName("Spawner Rose 1").toItemStack();
    final ItemStack SR2 = new ItemBuilder(Material.GOLD_BLOCK).setName("Spawner Rose 2").toItemStack();

    final ItemStack BO = new ItemBuilder(Material.BED).setName("Lit Orange").toItemStack();
    final ItemStack BJ = new ItemBuilder(Material.BED).setName("Lit Jaune").toItemStack();
    final ItemStack BV = new ItemBuilder(Material.BED).setName("Lit Violet").toItemStack();
    final ItemStack BR = new ItemBuilder(Material.BED).setName("Lit Rose").toItemStack();

    @Override
    public String name() {
        return "§cRush Setup";
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        inv.setItem(11, SO1);
        inv.setItem(12, SO2);
        inv.setItem(14, SV1);
        inv.setItem(15, SV2);
        inv.setItem(20, SJ1);
        inv.setItem(21, SJ2);
        inv.setItem(23, SR1);
        inv.setItem(24, SR2);

        inv.setItem(38, BO);
        inv.setItem(39, BJ);
        inv.setItem(41, BV);
        inv.setItem(42, BR);
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

        final Location loc = player.getLocation();
        if (displayName.equals(SO1.getItemMeta().getDisplayName())) saveSpawner(player, "Spawner Orange 1", loc);
        else if (displayName.equals(SO2.getItemMeta().getDisplayName())) saveSpawner(player, "Spawner Orange 2", loc);
        else if (displayName.equals(SJ1.getItemMeta().getDisplayName())) saveSpawner(player, "Spawner Jaune 1", loc);
        else if (displayName.equals(SJ2.getItemMeta().getDisplayName())) saveSpawner(player, "Spawner Jaune 2", loc);
        else if (displayName.equals(SV1.getItemMeta().getDisplayName())) saveSpawner(player, "Spawner Violet 1", loc);
        else if (displayName.equals(SV2.getItemMeta().getDisplayName())) saveSpawner(player, "Spawner Violet 2", loc);
        else if (displayName.equals(SR1.getItemMeta().getDisplayName())) saveSpawner(player, "Spawner Rose 1", loc);
        else if (displayName.equals(SR2.getItemMeta().getDisplayName())) saveSpawner(player, "Spawner Rose 2", loc);
        else if (displayName.equals(BO.getItemMeta().getDisplayName())) saveBed(player, "Lit Orange", loc);
        else if (displayName.equals(BJ.getItemMeta().getDisplayName())) saveBed(player, "Lit Jaune", loc);
        else if (displayName.equals(BV.getItemMeta().getDisplayName())) saveBed(player, "Lit Violet", loc);
        else if (displayName.equals(BR.getItemMeta().getDisplayName())) saveBed(player, "Lit Rose", loc);
    }

    void saveSpawner(Player player, String name, Location loc) {
        save("spawners", name, loc);
        player.sendMessage("§9[Rush] §bSpawner enregistré avec succès.");
        player.closeInventory();
    }

    void saveBed(Player player, String name, Location loc) {
        save("beds", name, loc);
        player.sendMessage("§9[Rush] §bLit enregistré avec succès.");
        player.closeInventory();
    }

    void save(String s, String name, Location loc) {
        final File f = new File(LoniaRush.INSTANCE.getDataFolder(), "config.yml");
        if (!f.exists()) f.mkdir();

        final YamlConfiguration config = YamlConfiguration.loadConfiguration(f);

        config.set(s + "." + name + ".world", loc.getWorld().getName());
        config.set(s + "." + name + ".x", loc.getX());
        config.set(s + "." + name + ".y", loc.getY());
        config.set(s + "." + name + ".z", loc.getZ());

        try {
            config.save(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
