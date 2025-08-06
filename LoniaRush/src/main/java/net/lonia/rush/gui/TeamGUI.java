package net.lonia.rush.gui;

import net.lonia.core.item.builder.ItemBuilder;
import net.lonia.core.rank.Ranks;
import net.lonia.core.utils.GuiBuilder;
import net.lonia.rush.LoniaRush;
import net.lonia.rush.game.Game;
import net.lonia.rush.game.GameTeam;
import net.lonia.rush.game.player.PlayerData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.stream.Collectors;

public class TeamGUI implements GuiBuilder {

    ItemStack ORANGE;
    ItemStack VIOLET;
    final ItemStack RANDOM = new ItemBuilder(Material.WOOL).setName("§fAléatoire").toItemStack();

    @Override
    public String name() {
        return "§f§lÉquipes";
    }

    @Override
    public int getSize() {
        return 9;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        final Game game = LoniaRush.INSTANCE.getGameManager().getGameByPlayer(player);

        List<String> oL = game.getOrangeTeamPlayers().isEmpty() ? null : game.getOrangeTeamPlayers().stream().map(players -> "§7- " + players).collect(Collectors.toList());
        List<String> oV = game.getVioletTeamPlayers().isEmpty() ? null : game.getVioletTeamPlayers().stream().map(players -> "§7- " + players).collect(Collectors.toList());

        ORANGE = new ItemBuilder(Material.WOOL, 1, 1).setLore(oL).setName("§6Orange").toItemStack();
        VIOLET = new ItemBuilder(Material.WOOL, 1, 10).setLore(oV).setName("§5Violet").toItemStack();


        inv.setItem(0, ORANGE);
        inv.setItem(1, VIOLET);
        inv.setItem(8, RANDOM);
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
        final Game game = LoniaRush.INSTANCE.getGameManager().getGameByPlayer(player);
        final PlayerData data = game.getPlayerData(player);

        if (displayName.equals(ORANGE.getItemMeta().getDisplayName())) {
            if (game.getOrangeTeamPlayers().size() == 2) return;
            if (data.getTeam().equals(GameTeam.ORANGE)) return;

            data.setTeam(GameTeam.ORANGE);
            data.setRank(Ranks.ORANGE);
            player.sendMessage("§9[Rush] §bVous avez sélectionné §6l'Équipe Orange§b.");
        } else if (displayName.equals(VIOLET.getItemMeta().getDisplayName())) {
            if (game.getVioletTeamPlayers().size() == 2) return;
            if (data.getTeam().equals(GameTeam.VIOLET)) return;

            data.setTeam(GameTeam.VIOLET);
            data.setRank(Ranks.VIOLET);
            player.sendMessage("§9[Rush] §bVous avez sélectionné §5l'Équipe Violet§b.");
        } else if (displayName.equals(RANDOM.getItemMeta().getDisplayName())) {
            if (data.getTeam().equals(GameTeam.RANDOM)) return;

            data.setTeam(GameTeam.RANDOM);
            data.setRank(Ranks.RANDOM);
            player.sendMessage("§9[Rush] §bVous avez sélectionné §fÉquipe Aléatoire§b.");
        }
    }

    //void updateTablist() {
    //    LoniaRush.INSTANCE.getPlayers().forEach(name -> {
    //        final Player p = Bukkit.getPlayer(name);
    //        LoniaRush.INSTANCE.getScoreboardManager().onLogout(p);
    //        LoniaRush.INSTANCE.getTeams().forEach(team -> (((CraftPlayer) Bukkit.getPlayer(p.getUniqueId())).getHandle()).playerConnection.sendPacket(team.removeTeam()));
    //        LoniaRush.INSTANCE.getScoreboardManager().onLogin(p);
    //    });
    //}
}
