package net.lonia.rush.commands;

import net.lonia.core.commands.AbstractCommand;
import net.lonia.core.message.MessageList;
import net.lonia.core.rank.PermissionLevel;

import net.lonia.core.utils.Log;
import net.lonia.rush.LoniaRush;
import net.lonia.rush.game.Game;

import net.lonia.rush.game.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class GameInfoCMD extends AbstractCommand {
    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageList.commandNotAccessibleHere());
            return false;
        }

        player = (Player) sender;
        account = accountManager.getAccount(player);

        if (!account.hasPermissionLevel(PermissionLevel.FONDATEUR)) return true;

        Game game = LoniaRush.INSTANCE.getGameManager().getGameByPlayer(player);

        player.sendMessage(game.getGameName() + game.getGameId() + game.getState().getName());
        game.getPlayerInGame().forEach(players -> {
            Log.log(players);
            PlayerData data = game.getPlayerData(Bukkit.getPlayer(players));
            player.sendMessage(players + data.getTeam().getName() + data.getRank().getName() + data.getKills() + data.getDeaths() + data.getBeds());
        });
        return false;
    }

    @Override
    protected List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return Collections.emptyList();
    }
}
