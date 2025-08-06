package net.lonia.moderation.commands.staff.modo;

import net.lonia.core.commands.AbstractCommand;
import net.lonia.core.message.MessageList;
import net.lonia.core.rank.PermissionLevel;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class KillInstantCMD extends AbstractCommand {
    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageList.commandNotAccessibleHere());
            return false;
        }

        player = (Player) sender;
        account = accountManager.getAccount(player);

        if (!account.hasPermissionLevel(PermissionLevel.MODERATEUR)) return true;

        if (args.length >= 1) {
            final Player target = getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(MessageList.playerNotConnected());
                return true;
            }

            target.setHealth(0.0);
            target.sendMessage("§9[Sanction] §3Vous venez d'être tué par un membre de notre équipe.");
        }
        return true;
    }

    @Override
    protected List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return Collections.emptyList();
    }
}
