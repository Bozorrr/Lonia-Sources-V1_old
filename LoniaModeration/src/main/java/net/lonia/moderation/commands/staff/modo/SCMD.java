package net.lonia.moderation.commands.staff.modo;

import net.lonia.core.commands.AbstractCommand;
import net.lonia.core.message.MessageList;
import net.lonia.core.player.account.Account;
import net.lonia.core.rank.PermissionLevel;
import net.lonia.moderation.LoniaModeration;
import net.lonia.moderation.gui.staff.moderator.moderation.ModerationGui;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SCMD extends AbstractCommand {

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageList.commandNotAccessibleHere());
            return false;
        }

        player = (Player) sender;
        account = accountManager.getAccount(player);

        if (!account.hasPermissionLevel(PermissionLevel.MODERATEUR_CHAT)) return true;

        if (args.length >= 1) {
            String playerName = args[0];

            if (Account.getUUID(playerName) == null) {
                player.sendMessage("§9[Sanction] §3Erreur: Ce pseudonyme n'existe pas.");
                return true;
            }

            account.getDataModeration().setReportedPlayer(playerName);
            LoniaModeration.getInstance().getGuiManager().open(player, ModerationGui.class);
        }

        return true;
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
