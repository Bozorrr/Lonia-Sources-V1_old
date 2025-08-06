package net.lonia.moderation.commands.staff.responsable.server;

import net.lonia.core.commands.AbstractCommand;
import net.lonia.core.message.MessageList;
import net.lonia.core.rank.PermissionLevel;
import net.lonia.moderation.LoniaModeration;
import net.lonia.moderation.gui.staff.admin.server.setupserv.SetupServerPanelEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SetupServerCMD extends AbstractCommand {

    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageList.commandNotAccessibleHere());
            return false;
        }

        player = (Player) sender;
        account = accountManager.getAccount(player);

        if (!account.hasPermissionLevel(PermissionLevel.ADMIN)) return true;

        LoniaModeration.getInstance().getGuiManager().open(player, SetupServerPanelEvent.class);
        return true;
    }


    @Override
    protected List<String> onTabComplete(CommandSender commandSender, String s, String[] strings) {
        return null;
    }


}
