package net.lonia.rush.commands;

import net.lonia.core.commands.AbstractCommand;
import net.lonia.core.message.MessageList;
import net.lonia.core.rank.PermissionLevel;
import net.lonia.rush.LoniaRush;
import net.lonia.rush.gui.RushSetupGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class RushSetupCMD extends AbstractCommand {
    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageList.commandNotAccessibleHere());
            return false;
        }

        player = (Player) sender;
        account = accountManager.getAccount(player);

        if (!account.hasPermissionLevel(PermissionLevel.FONDATEUR)) return true;

        LoniaRush.INSTANCE.getGuiManager().open(player, RushSetupGUI.class);

        return false;
    }

    @Override
    protected List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return Collections.emptyList();
    }
}
