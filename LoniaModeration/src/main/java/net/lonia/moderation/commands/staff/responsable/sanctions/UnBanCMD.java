package net.lonia.moderation.commands.staff.responsable.sanctions;

import net.lonia.core.commands.AbstractCommand;
import net.lonia.core.message.MessageList;
import net.lonia.core.player.account.Account;
import net.lonia.core.player.sanction.SanctionManager;
import net.lonia.core.player.sanction.SanctionType;
import net.lonia.core.rank.PermissionLevel;
import net.lonia.moderation.LoniaModeration;
import net.lonia.moderation.gui.staff.responsable.UnBanGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class UnBanCMD extends AbstractCommand {

    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageList.commandNotAccessibleHere());
            return false;
        }

        player = (Player) sender;
        account = accountManager.getAccount(player);

        if (!account.hasPermissionLevel(PermissionLevel.RESPONSSABLE)) return true;

        if (args.length >= 1) {
            final String targetName = args[0];
            final UUID uuid = Account.getUUID(targetName);

            if (uuid != null) {
                SanctionManager.updateBan(uuid);
                if (!SanctionManager.isSanctionActive(uuid, SanctionType.BAN)) {
                    player.sendMessage("§9[Sanction] §3Erreur: Le joueur n'est pas banni.");
                    return true;
                }

                account.getDataModeration().setU(targetName);
                LoniaModeration.getInstance().getGuiManager().open(player, UnBanGUI.class);
            } else player.sendMessage("§9[Sanction] §3Erreur: Ce pseudonyme n'existe pas.");
        } else player.sendMessage("§9[Sanction] §3Utilisation: /unban (Pseudo)");

        return true;
    }

    @Override
    protected List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return null;
    }
}
