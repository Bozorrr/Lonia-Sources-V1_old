package net.lonia.moderation.commands.staff.modo;

import net.lonia.core.commands.AbstractCommand;
import net.lonia.core.message.MessageList;
import net.lonia.core.rank.PermissionLevel;
import net.lonia.moderation.listeners.ModListeners;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FreezeCMD extends AbstractCommand {
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
            final String targetName = args[0];
            final Player target = getPlayer(targetName);

            if (target != null) {
                if (ModListeners.freezePlayers.contains(target)) {
                    player.sendMessage("§9[Mod.] §f" + target.getDisplayName() + " §bn'est plus en mode freeze.");
                    ModListeners.freezePlayers.remove(target);
                } else {
                    player.sendMessage("§9[Mod.] §f" + target.getDisplayName() + " §best en mode freeze durant 30 secondes.");
                    ModListeners.freezePlayers.add(target);
                    ModListeners.executeTaskInLoop(target);
                }
            } else player.sendMessage(MessageList.playerNotConnected());

        }

        return false;
    }

    @Override
    protected List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length == 1) {
            String prefix = args[0].toLowerCase();
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.toLowerCase().startsWith(prefix))
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }
}
