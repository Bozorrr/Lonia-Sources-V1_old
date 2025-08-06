package net.lonia.core.commands.player.navigation;

import net.lonia.core.commands.AbstractCommand;
import net.lonia.core.message.MessageList;
import net.lonia.core.rank.PermissionLevel;
import net.lonia.core.server.ServerManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class HubCMD extends AbstractCommand {

    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageList.commandNotAccessibleHere());
            return false;
        }

        player = (Player) sender;
        account = accountManager.getAccount(player);

        if (!account.hasPermissionLevel(PermissionLevel.JOUEUR)) return true;

        ServerManager.connectToHub(player);
        return true;
    }

    @Override
    protected List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return null;
    }
}

