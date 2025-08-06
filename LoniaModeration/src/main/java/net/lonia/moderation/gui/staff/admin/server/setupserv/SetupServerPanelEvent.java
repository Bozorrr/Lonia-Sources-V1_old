package net.lonia.moderation.gui.staff.admin.server.setupserv;

import net.lonia.core.LoniaCore;
import net.lonia.core.item.builder.ItemBuilder;
import net.lonia.core.item.manager.ItemManager;
import net.lonia.core.message.MessageList;
import net.lonia.core.player.account.Account;
import net.lonia.core.rank.Ranks;
import net.lonia.core.server.account.ServerAccount;
import net.lonia.core.server.type.ServerType;
import net.lonia.core.utils.GuiBuilder;
import net.lonia.moderation.LoniaModeration;
import net.lonia.moderation.gui.staff.admin.AdminMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SetupServerPanelEvent implements GuiBuilder {

    public static final ItemStack SETUPSERV_ICO = new ItemBuilder(Material.COMMAND).setName("§bServer-setup").toItemStack();

    public static final ItemStack LOBBY = new ItemBuilder(Material.BEACON).setName("§9Lobby").toItemStack();
    public static final ItemStack DUEL = new ItemBuilder(Material.IRON_SWORD).setName("§9Duel").hideItemFlag().toItemStack();
    public static final ItemStack ARENEC = new ItemBuilder(Material.DIAMOND_CHESTPLATE).setName("§9Arène PVP - Classique").toItemStack();
    public static final ItemStack ARENEE = new ItemBuilder(Material.POTION, 1, 16385).setName("§9Arène PVP - Effects").hideItemFlag().toItemStack();
    public static final ItemStack FREECUBE = new ItemBuilder(Material.GRASS).setName("§9Freecube").toItemStack();
    public static final ItemStack UNEXUS = new ItemBuilder(Material.BLAZE_ROD).setName("§9U-Nexus").toItemStack();
    public static final ItemStack RUNORKILL = new ItemBuilder(Material.SNOW_BALL).setName("§9RUN or KILL").toItemStack();
    public static final ItemStack RUSH = new ItemBuilder(Material.BED).setName("§9Rush").toItemStack();
    public static final ItemStack JUMPADVENTURE = new ItemBuilder(Material.BED).setName("§9Rush").toItemStack();
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
        inv.setItem(35, PIXELWAR);
        inv.setItem(43, BUILD);

        inv.setItem(53, ItemManager.CLOSE);
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
        final ServerAccount serverAccount = LoniaCore.get().getServerAccount();

        if (displayName.equals(ItemManager.RETURN.getItemMeta().getDisplayName())) {
            LoniaModeration.getInstance().getGuiManager().open(player, AdminMenu.class);
            return;
        }

        if (displayName.equals(LOBBY.getItemMeta().getDisplayName()))
            sendChangeServerTypeMessage(ServerType.LOBBY, serverAccount);
        else if (displayName.equals(DUEL.getItemMeta().getDisplayName()))
            sendChangeServerTypeMessage(ServerType.DUEL, serverAccount);
        else if (displayName.equals(ARENEC.getItemMeta().getDisplayName()))
            sendChangeServerTypeMessage(ServerType.ARENEC, serverAccount);
        else if (displayName.equals(ARENEE.getItemMeta().getDisplayName()))
            sendChangeServerTypeMessage(ServerType.ARENEE, serverAccount);
        else if (displayName.equals(FREECUBE.getItemMeta().getDisplayName()))
            sendChangeServerTypeMessage(ServerType.FREECUBE, serverAccount);
        else if (displayName.equals(UNEXUS.getItemMeta().getDisplayName()))
            sendChangeServerTypeMessage(ServerType.UNEXUS, serverAccount);
        else if (displayName.equals(RUNORKILL.getItemMeta().getDisplayName()))
            sendChangeServerTypeMessage(ServerType.RUNORKILL, serverAccount);
        else if (displayName.equals(RUSH.getItemMeta().getDisplayName()))
            sendChangeServerTypeMessage(ServerType.RUSH, serverAccount);
        else if (displayName.equals(JUMPADVENTURE.getItemMeta().getDisplayName()))
            sendChangeServerTypeMessage(ServerType.JUMPADVENTURE, serverAccount);
        else if (displayName.equals(PIXELWAR.getItemMeta().getDisplayName()))
            sendChangeServerTypeMessage(ServerType.PIXELWAR, serverAccount);
        else if (displayName.equals(BUILD.getItemMeta().getDisplayName()))
            sendChangeServerTypeMessage(ServerType.BUILD, serverAccount);
        player.closeInventory();
    }

    private void sendChangeServerTypeMessage(ServerType type, ServerAccount serverAccount) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Account account = LoniaCore.get().getAccountManager().getAccount(player);
            if (account.getUserData().getDataRank().getRank().getPower() <= Ranks.ADMIN.getPower())
                player.sendMessage(MessageList.setServerTypeSuccessful(type.getName()));
        }
        serverAccount.getDataServer().setServerType(type);
    }
}
