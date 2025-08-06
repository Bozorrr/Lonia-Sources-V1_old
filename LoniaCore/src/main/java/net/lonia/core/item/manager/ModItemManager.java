package net.lonia.core.item.manager;

import net.lonia.core.item.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class ModItemManager {

    public static final ItemStack KB_SWORD = new ItemBuilder(Material.WOOD_SWORD).setName("§6KB-Sword").hideItemFlag().addEnchant(Enchantment.KNOCKBACK, 3).toItemStack();
    public static final ItemStack CHECK = new ItemBuilder(Material.PAPER).setName("§6Check").toItemStack();
    public static final ItemStack SPEC_MOD = new ItemBuilder(Material.ENDER_PEARL).setName("§6Spec-Mod").toItemStack();
    public static final ItemStack VANISH = new ItemBuilder(Material.INK_SACK, 1, 10).setName("§6Vanish: §a§lON").toItemStack();
    public static final ItemStack UN_VANISH = new ItemBuilder(Material.INK_SACK, 1, 8).setName("§6Vanish: §c§lOFF").toItemStack();
    public static final ItemStack FREEZE = new ItemBuilder(Material.WEB).setName("§6Freeze").toItemStack();
    public static final ItemStack SUPER_PICKAXE = new ItemBuilder(Material.GOLD_PICKAXE).setName("§6Super-Pickaxe").toItemStack();
    public static final ItemStack KILL_SWORD = new ItemBuilder(Material.DIAMOND_SWORD).setName("§6Kill-Sword").toItemStack();

}
