package net.lonia.moderation.commands.staff.modo;

import net.lonia.core.commands.AbstractCommand;
import net.lonia.core.message.MessageList;
import net.lonia.core.message.SanctionMessages;
import net.lonia.core.player.account.Account;
import net.lonia.core.player.sanction.SanctionManager;
import net.lonia.core.rank.PermissionLevel;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class KickCMD extends AbstractCommand {

    private static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new java.util.Date());
    }

    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageList.commandNotAccessibleHere());
            return false;
        }

        player = (Player) sender;
        account = accountManager.getAccount(player);

        if (!account.hasPermissionLevel(PermissionLevel.MODERATEUR)) return true;

        if (args.length >= 2) {
            final String targetName = args[0];
            final UUID uuid = Account.getUUID(targetName);

            if (uuid != null) {
                Account a = accountManager.getAccount(uuid);
                if (a == null) a = new Account(uuid);
                a.loadData();

                if (a.getUserData().isConnected()) {
                    final String reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

                    SanctionManager.kick(targetName, SanctionMessages.kick(player.getDisplayName(), reason));
                } else {
                    player.sendMessage(MessageList.playerNotConnected());
                }

            } else player.sendMessage("§9[Sanction] §3Erreur: Ce pseudonyme n'existe pas.");
        } else player.sendMessage("§9[Sanction] §3Utilisation: §f/kick (Pseudo) (Raison)");

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

    private long parseDurationInSeconds(String timeArg) {
        Pattern pattern = Pattern.compile("^(\\d+)([smhd])$");
        Matcher matcher = pattern.matcher(timeArg);

        if (matcher.matches()) {
            int value = Integer.parseInt(matcher.group(1));
            String unit = matcher.group(2);

            switch (unit) {
                case "s":
                    return value;
                case "m":
                    return value * 60L;
                case "h":
                    return value * 60L * 60L;
                case "d":
                    return value * 24L * 60L * 60L;
                default:
                    return -1;
            }
        }
        return -1;
    }

}
