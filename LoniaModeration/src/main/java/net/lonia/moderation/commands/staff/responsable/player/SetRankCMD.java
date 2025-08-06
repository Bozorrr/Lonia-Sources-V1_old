package net.lonia.moderation.commands.staff.responsable.player;

import net.lonia.core.commands.AbstractCommand;
import net.lonia.core.item.manager.ItemManager;
import net.lonia.core.message.MessageList;
import net.lonia.core.message.RankMessages;
import net.lonia.core.player.account.Account;
import net.lonia.core.rank.PermissionLevel;
import net.lonia.core.rank.Rank;
import net.lonia.core.rank.Ranks;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class SetRankCMD extends AbstractCommand {

    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageList.commandNotAccessibleHere());
            return false;
        }

        player = (Player) sender;
        account = accountManager.getAccount(player);

        if (!(player.isOp() || account.hasPermissionLevel(PermissionLevel.RESPONSSABLE))) {
            return true;
        }

        if (args.length == 0)
            player.sendMessage(RankMessages.rankUse());
        else if (args.length == 2) {
            Player target = getPlayer(args[0]);

            if (target != null && target.isOnline()) {
                Rank rank = Rank.getByName(args[1]);
                Account targetAccount = accountManager.getAccount(target);

                player.sendMessage(RankMessages.rankSuccessful(target.getDisplayName(), rank.getPrefix()));
                target.sendMessage(RankMessages.rankRankChangeMessage(rank.getPrefix()));

                targetAccount.getUserData().getDataRank().setRank(rank);

                if (rank.hasPermissionLevel(PermissionLevel.CONSTRUCTEUR))
                    target.getInventory().setItem(2, ItemManager.Item_StaffMenu);
            } else {
                player.sendMessage(MessageList.playerNotConnected());
            }
        }
        return true;
    }

    @Override
    protected List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        player = (Player) sender;
        account = accountManager.getAccount(player);

        if (args.length == 1) {
            String prefix = args[0].toLowerCase();
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.toLowerCase().startsWith(prefix))
                    .collect(Collectors.toList());
        } else if (args.length == 2) {
            return Ranks.getRanks().stream()
                    .map(Rank::getName)
                    .filter(name -> name.toLowerCase().startsWith(args[1].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return null;
    }
}
