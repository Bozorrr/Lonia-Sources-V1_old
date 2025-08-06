package net.lonia.moderation.commands.staff.modo;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.lonia.core.commands.AbstractCommand;
import net.lonia.core.message.MessageList;
import net.lonia.core.player.account.Account;
import net.lonia.core.rank.PermissionLevel;
import net.lonia.moderation.LoniaModeration;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class TPCMD extends AbstractCommand {
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
            Player target = getPlayer(targetName);

            if (player.getDisplayName().equals(targetName)) {
                player.sendMessage("Arrête d'être con.");
                return true;
            }

            if (!Account.isConnected(targetName)) {
                player.sendMessage(MessageList.playerNotConnected());
                return true;
            }

            if (target != null) {
                player.teleport(target.getLocation());
                player.sendMessage("§9[Mod.] §bVous avez été téléporté sur §f" + target.getDisplayName() + "§b.");
            } else {
                final ByteArrayDataOutput out = ByteStreams.newDataOutput();

                out.writeUTF("JOIN");
                out.writeUTF(player.getDisplayName());
                out.writeUTF(targetName);

                player.sendPluginMessage(LoniaModeration.getInstance(), "BungeeCord", out.toByteArray());
            }
        }
        return true;
    }

    @Override
    protected List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return Collections.emptyList();
    }
}
