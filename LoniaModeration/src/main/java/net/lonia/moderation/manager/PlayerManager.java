package net.lonia.moderation.manager;

import net.lonia.core.LoniaCore;
import net.lonia.core.item.manager.ItemManager;
import net.lonia.core.item.manager.ModItemManager;
import net.lonia.core.player.account.Account;
import net.lonia.core.player.staff.StaffManager;
import net.lonia.core.rank.PermissionLevel;
import net.lonia.core.server.type.ServerType;
import net.lonia.moderation.LoniaModeration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PlayerManager {

    private final Player player;
    private ItemStack[] items = new ItemStack[40];

    public PlayerManager(Player player) {
        this.player = player;
    }

    public static PlayerManager getPlayerManager(Player player) {
        return LoniaModeration.getInstance().getPlayers().get(player.getUniqueId());
    }

    public static void vanish(UUID uuid) {
        LoniaModeration.getInstance().getVanishPlayers().add(uuid);
        Bukkit.getOnlinePlayers().stream().filter(players -> !Account.getRank(players.getUniqueId()).hasPermissionLevel(PermissionLevel.MODERATEUR)).forEach(players -> players.hidePlayer(Bukkit.getPlayer(uuid)));
        StaffManager.setInVanish(uuid, 1);
    }

    public static void unvanish(UUID uuid) {
        LoniaModeration.getInstance().getVanishPlayers().remove(uuid);
        Bukkit.getOnlinePlayers().stream().filter(players -> !Account.getRank(players.getUniqueId()).hasPermissionLevel(PermissionLevel.MODERATEUR)).forEach(players -> players.showPlayer(Bukkit.getPlayer(uuid)));
        StaffManager.setInVanish(uuid, 0);
    }

    public void init() {
        LoniaModeration.getInstance().getPlayers().put(player.getUniqueId(), this);
    }

    public void destroy() {
        LoniaModeration.getInstance().getPlayers().remove(player.getUniqueId());
    }

    public ItemStack[] getItems() {
        return items;
    }

    public void saveInventory() {
        for (int slot = 0; slot < 36; slot++) {
            ItemStack item = player.getInventory().getItem(slot);
            if (item != null) {
                items[slot] = item;
            }
        }

        items[36] = player.getInventory().getHelmet();
        items[37] = player.getInventory().getChestplate();
        items[38] = player.getInventory().getLeggings();
        items[39] = player.getInventory().getBoots();

        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);

        player.getInventory().setItem(0, ModItemManager.KB_SWORD);
        player.getInventory().setItem(1, ModItemManager.CHECK);
        player.getInventory().setItem(2, ModItemManager.SPEC_MOD);
        player.getInventory().setItem(4, ModItemManager.VANISH);
        player.getInventory().setItem(6, ModItemManager.FREEZE);
        player.getInventory().setItem(7, ModItemManager.SUPER_PICKAXE);
        player.getInventory().setItem(8, ModItemManager.KILL_SWORD);
    }

    public void getInventory() {
        player.getInventory().clear();
        for (int slot = 0; slot < 36; slot++) {
            if (items[slot] != null) {
                player.getInventory().setItem(slot, items[slot]);
            }
        }

        player.getInventory().setHelmet(items[36]);
        player.getInventory().setChestplate(items[37]);
        player.getInventory().setLeggings(items[38]);
        player.getInventory().setBoots(items[39]);

        if (LoniaCore.get().getServerAccount().getDataServer().getServerType().equals(ServerType.LOBBY)) {
            player.getInventory().setItem(1, ItemManager.Main_Menu);
            player.getInventory().setItem(4, ItemManager.Shop);
            player.getInventory().setItem(7, LoniaCore.get().getAccountManager().getAccount(player).getDataShop().getSKULL());
            player.getInventory().setItem(2, ItemManager.Item_StaffMenu);
        }
    }
}
