package net.lonia.moderation.commands.staff.modo;

import net.lonia.core.commands.AbstractCommand;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class TPHCMD extends AbstractCommand {
    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        return false;
    }

    @Override
    protected List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return Collections.emptyList();
    }
}
