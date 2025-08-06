package net.lonia.core.commands.player.social;

import net.lonia.core.commands.AbstractCommand;
import net.lonia.core.message.MPMessage;
import net.lonia.core.message.MessageList;
import net.lonia.core.rank.PermissionLevel;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IgnoreCMD extends AbstractCommand {

    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageList.commandNotAccessibleHere());
            return false;
        }

        player = (Player) sender;
        account = accountManager.getAccount(player);

        if (!account.hasPermissionLevel(PermissionLevel.JOUEUR)) return true;

        if (args.length != 1) {
            player.sendMessage(MPMessage.ignoreUsage());
            return true;
        }

        final Player target = getPlayer(args[0]);

        if (target == null) {
            player.sendMessage(MessageList.playerNotConnected());
            return true;
        }

        if (player.getDisplayName().equals(target.getDisplayName())) {
            player.sendMessage(MPMessage.ignoreHimSelf());
        } else if (isIgnored(player, target)) {
            player.sendMessage(MPMessage.unIgnored());
            account.getUserData().getDataMP().removeIgnoredPlayer(target.getDisplayName());
        } else if (!isIgnored(player, target)) {
            player.sendMessage(MPMessage.ignore());
            account.getUserData().getDataMP().addIgnoredPlayer(target.getDisplayName());
        }

        return true;
    }

    @Override
    protected List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length == 1) {
            String prefix = args[0].toLowerCase();
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> !name.equalsIgnoreCase(sender.getName()))
                    .filter(name -> name.toLowerCase().startsWith(prefix))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private boolean isIgnored(Player player, Player target) {
        account = accountManager.getAccount(target);
        return account.getUserData().getDataMP().getIgnoredPlayers().contains(player.getDisplayName());
    }
}

