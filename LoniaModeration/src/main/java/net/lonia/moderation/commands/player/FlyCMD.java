package net.lonia.moderation.commands.player;

import net.lonia.core.commands.AbstractCommand;
import net.lonia.core.message.MessageList;
import net.lonia.core.rank.PermissionLevel;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class FlyCMD extends AbstractCommand {

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageList.commandNotAccessibleHere());
            return false;
        }

        player = (Player) sender;
        account = accountManager.getAccount(player);

        if (account.hasPermissionLevel(PermissionLevel.MODERATEUR_CHAT)) {
            if (!player.getAllowFlight()) {
                player.setAllowFlight(true);
                player.setFlying(true);
                player.sendMessage("§9[Staff] §d[Mod info.] §bVous venez d'activer le mode fly");
            } else if (player.getAllowFlight()) {
                player.setAllowFlight(false);
                player.sendMessage("§9[Staff] §d[Mod info.] §3Vous venez de désactiver le mode fly");
            }
        }
        return true;
    }

    @Override
    protected List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return null;
    }
}
