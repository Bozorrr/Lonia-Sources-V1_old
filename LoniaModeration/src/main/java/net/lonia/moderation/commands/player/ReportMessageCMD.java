package net.lonia.moderation.commands.player;

import net.lonia.core.commands.AbstractCommand;
import net.lonia.core.message.MessageList;
import net.lonia.core.rank.PermissionLevel;
import net.lonia.core.utils.Log;
import net.lonia.moderation.LoniaModeration;
import net.lonia.moderation.gui.player.ConfirmChatReport;
import net.lonia.moderation.gui.staff.moderator.moderation.sub.sub.SubMessageSanctionGui;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ReportMessageCMD extends AbstractCommand {

    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageList.commandNotAccessibleHere());
            return false;
        }

        player = (Player) sender;
        account = accountManager.getAccount(player);

        String playerNameWhoSendMessage = args[0];
        StringBuilder messageBuilder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            messageBuilder.append(args[i]).append(" ");
        }
        String reportedMessage = messageBuilder.toString().trim();

        reportedMessage = reportedMessage.replace("&", "§");

        account.getDataModeration().setReportedPlayer(playerNameWhoSendMessage);
        account.getDataModeration().setReportedMessage(reportedMessage);

        if (account.getUserData().getDataRank().getRank().hasPermissionLevel(PermissionLevel.MODERATEUR_CHAT)) {
            LoniaModeration.getInstance().getGuiManager().open(player, SubMessageSanctionGui.class);
        } else {
            LoniaModeration.getInstance().getGuiManager().open(player, ConfirmChatReport.class);
        }

        return true;
    }

    @Override
    protected List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return null;
    }
}
