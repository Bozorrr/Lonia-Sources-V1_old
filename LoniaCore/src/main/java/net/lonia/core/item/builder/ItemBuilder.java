package net.lonia.core.item.builder;

import net.lonia.core.utils.Log;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;

public class ItemBuilder {
    private final ItemStack is;

    public ItemBuilder(Material m) {
        this(m, 1);
    }

    public ItemBuilder(ItemStack is) {
        this.is = is;
    }

    public ItemBuilder(Material m, int amount) {
        this.is = new ItemStack(m, amount);
    }

    public ItemBuilder(Material m, int amount, int meta) {
        this.is = new ItemStack(m, amount, (short) meta);
    }

    public ItemBuilder setDurability(short dur) {
        this.is.setDurability(dur);
        return this;
    }

    public ItemBuilder setName(String name) {
        ItemMeta im = this.is.getItemMeta();
        im.setDisplayName(name);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addUnsafeEnchantment(Enchantment ench, int level) {
        this.is.addUnsafeEnchantment(ench, level);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment ench) {
        this.is.removeEnchantment(ench);
        return this;
    }

    public ItemBuilder setSkullOwner(String owner) {
        try {
            SkullMeta im = (SkullMeta) this.is.getItemMeta();
            im.setOwner(owner);
            this.is.setItemMeta(im);
        } catch (ClassCastException e) {
            Log.log("§cCRITICAL : " + e.getMessage());
        }
        return this;
    }

    public ItemBuilder addEnchant(Enchantment ench, int level) {
        ItemMeta im = this.is.getItemMeta();
        im.addEnchant(ench, level, true);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag itemFlag) {
        ItemMeta im = this.is.getItemMeta();
        im.addItemFlags(itemFlag);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder hideItemFlag() {
        ItemMeta im = this.is.getItemMeta();
        im.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        im.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        im.removeItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        im.removeItemFlags(ItemFlag.HIDE_DESTROYS);
        im.removeItemFlags(ItemFlag.HIDE_PLACED_ON);
        im.removeItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setInfinityDurability() {
        this.is.setDurability(Short.MAX_VALUE);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        ItemMeta im = this.is.getItemMeta();
        im.setLore(Arrays.asList(lore));
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta im = this.is.getItemMeta();
        im.setLore(lore);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setBannerColor(DyeColor color) {
        BannerMeta meta = (BannerMeta) this.is.getItemMeta();
        meta.setBaseColor(color);
        this.is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addBannerPattern(DyeColor color, PatternType patternType) {
        BannerMeta meta = (BannerMeta) this.is.getItemMeta();
        meta.addPattern(new Pattern(color, patternType));
        this.is.setItemMeta(meta);
        return this;
    }


    public ItemBuilder setLeatherArmorColor(Color color) {
        try {
            LeatherArmorMeta im = (LeatherArmorMeta) this.is.getItemMeta();
            im.setColor(color);
            this.is.setItemMeta(im);
        } catch (ClassCastException e) {
            Log.log("§cCRITICAL : " + e.getMessage());
        }
        return this;
    }

    public ItemStack toItemStack() {
        return this.is;
    }

    public Material toMaterial() {
        return this.is.getType();
    }
}
