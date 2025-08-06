package net.lonia.core.commands.player.utils;

import net.lonia.core.commands.AbstractCommand;
import net.lonia.core.message.HelpMessages;
import net.lonia.core.message.MessageList;
import net.lonia.core.rank.PermissionLevel;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class HelpCMD extends AbstractCommand {

    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageList.commandNotAccessibleHere());
            return false;
        }

        player = (Player) sender;
        account = accountManager.getAccount(player);

        if (!account.hasPermissionLevel(PermissionLevel.JOUEUR)) return true;

        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("friends")) {
                player.sendMessage(HelpMessages.helpFriends());
            } else if (args[0].equalsIgnoreCase("group")) {
                player.sendMessage(HelpMessages.helpGroup());
            } else if (args[0].equalsIgnoreCase("staff") && account.hasPermissionLevel(PermissionLevel.ANIMATEUR)) {
                if (args.length >= 2) {
                    try {
                        int page = Integer.parseInt(args[1]);
                        player.sendMessage(HelpMessages.helpStaff(page));
                    } catch (NumberFormatException e) {
                        player.sendMessage(HelpMessages.helpInvalidPage());
                    }
                } else {
                    player.sendMessage(HelpMessages.helpStaff(1));
                }
            } else if (args[0].matches("\\d+")) {
                int page = Integer.parseInt(args[0]);
                player.sendMessage(HelpMessages.help(page));
            } else {
                player.sendMessage(HelpMessages.help(1));
            }
        } else {
            player.sendMessage(HelpMessages.help(1));
        }

        return true;
    }

    @Override
    protected List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        player = (Player) sender;
        account = accountManager.getAccount(player);

        if (args.length == 1) {
            completions.add("friends");
            completions.add("group");
            if (account.getUserData().getDataRank().getRank().hasPermissionLevel(PermissionLevel.ANIMATEUR)) {
                completions.add("staff");
            }
        }

        return completions;
    }
}
