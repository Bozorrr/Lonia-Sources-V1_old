package net.lonia.moderation.commands.player;

import net.lonia.core.commands.AbstractCommand;
import net.lonia.core.message.MessageList;
import net.lonia.core.player.account.Account;
import net.lonia.core.rank.PermissionLevel;
import net.lonia.moderation.LoniaModeration;
import net.lonia.moderation.gui.player.ReportGUI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReportCMD extends AbstractCommand {
    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageList.commandNotAccessibleHere());
            return false;
        }

        player = (Player) sender;
        account = accountManager.getAccount(player);

        if (!account.hasPermissionLevel(PermissionLevel.JOUEUR)) return true;

        if (args.length >= 1) {
            final String targetName = args[0];
            final UUID uuid = Account.getUUID(targetName);

            if (uuid != null) {

            } else {
                player.sendMessage("§9[Sanction] §3Erreur: Ce pseudonyme n'existe pas.");
                return true;
            }

            LoniaModeration.getInstance().getGuiManager().open(player, ReportGUI.class);
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
