package net.lonia.core.player.account;

import net.lonia.core.item.builder.ItemBuilder;
import net.lonia.core.rank.Rank;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class DShop extends AbstractData {

    private ItemStack SKULL;

    private String payment_type;

    private Rank rank;

    public DShop(UUID uuid) {
        this.uuid = uuid;
        this.SKULL = new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("§9Profil").setSkullOwner(name).toItemStack();
    }

    public ItemStack getSKULL() {
        return SKULL;
    }

    public String getPayment_type() {
        return this.payment_type;
    }

    public Rank getRank() {
        return this.rank;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }
}
