package net.lonia.moderation.commands.staff.responsable.player;


import net.lonia.core.commands.AbstractCommand;
import net.lonia.core.message.ArgentMessages;
import net.lonia.core.message.MessageList;
import net.lonia.core.player.account.Account;
import net.lonia.core.rank.PermissionLevel;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArgentCMD extends AbstractCommand {

    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageList.commandNotAccessibleHere());
            return false;
        }

        player = (Player) sender;
        account = accountManager.getAccount(player);

        if (!account.hasPermissionLevel(PermissionLevel.RESPONSSABLE)) return false;

        if (args.length < 3) {
            player.sendMessage(ArgentMessages.argentUse());
            return true;
        }

        final Player target = getPlayer(args[1]);
        long argent = Integer.parseInt(args[2]);

        if (!targetIsConnect(target, player))
            return true;

        final Account targetAccount = accountManager.getAccount(player);

        if (args[0].equals("add")) {
            targetAccount.getUserData().getDataPearl().addPearl(argent);

            target.sendMessage(ArgentMessages.argentReceived(argent));
            player.sendMessage(ArgentMessages.argentAdd(argent, target.getDisplayName()));
            return true;
        }
        if (args[0].equals("remove")) {

            if (argent > targetAccount.getUserData().getDataPearl().getPearl()) {
                argent = targetAccount.getUserData().getDataPearl().getPearl();
            }

            targetAccount.getUserData().getDataPearl().removePearl(argent);

            target.sendMessage(ArgentMessages.argentLost(argent));
            player.sendMessage(ArgentMessages.argentRemove(argent, target.getDisplayName()));
            return true;
        }
        player.sendMessage(ArgentMessages.argentUse());
        return true;
    }

    public boolean targetIsConnect(Player target, Player player) {
        if (target != null) return true;
        player.sendMessage(MessageList.playerNotConnected());
        return false;
    }

    @Override
    protected List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        player = (Player) sender;
        account = accountManager.getAccount(player);

        if (args.length == 1) {
            completions.add("add");
            completions.add("remove");
        } else if (args.length == 2 && (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove"))) {
            String prefix = args[1].toLowerCase();
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.toLowerCase().startsWith(prefix))
                    .collect(Collectors.toList());

        }
        return completions;
    }
}
