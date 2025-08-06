package net.lonia.core.item.manager;

import net.lonia.core.item.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemManager {

    /**
     * Item in hot bar
     */
    public static final ItemStack Item_StaffMenu = new ItemBuilder(Material.FEATHER).setName("§c§lStaff").toItemStack();
    public static final ItemStack Main_Menu = new ItemBuilder(Material.NETHER_STAR).setName("§9§lMenu principal").toItemStack();
    public static final ItemStack Shop = new ItemBuilder(Material.GOLD_INGOT).setName("§e§lBoutique").toItemStack();

    /**
     * Utilities
     */
    public static final ItemStack CLOSE = new ItemBuilder(Material.BARRIER).setName("§cFermer").toItemStack();

    public static final ItemStack RETURN = new ItemBuilder(Material.BARRIER).setName("§cRetour").toItemStack();

    public static final ItemStack UNOCCUPIED = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 8).setName("§7Inoccupé").toItemStack();

    public static final ItemStack confirm = new ItemBuilder(Material.WOOL, 1, 5).setName("§a§lConfirmer").toItemStack();
    public static final ItemStack cancel = new ItemBuilder(Material.WOOL, 1, 14).setName("§c§lAnnuler").toItemStack();

    /**
     * Shop item
     */
    public static final ItemStack PaymentType_IG = new ItemBuilder(Material.WOOL, 1, 3).setName("§9Payer en Argent IG").toItemStack();
    public static final ItemStack PaymentType_IRL = new ItemBuilder(Material.WOOL, 1, 11).setName("§9Payer en Argent IRL").toItemStack();


}
