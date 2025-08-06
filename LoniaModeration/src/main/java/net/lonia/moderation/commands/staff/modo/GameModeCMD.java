package net.lonia.moderation.commands.staff.modo;

import net.lonia.core.commands.AbstractCommand;
import net.lonia.core.message.MessageList;
import net.lonia.core.rank.PermissionLevel;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GameModeCMD extends AbstractCommand {

    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageList.commandNotAccessibleHere());
            return false;
        }

        player = (Player) sender;
        account = accountManager.getAccount(player);

        if (!account.hasPermissionLevel(PermissionLevel.CONSTRUCTEUR)) return true;

        if (args.length == 1) {
            String gm = args[0].toLowerCase();

            switch (gm) {
                case "0":
                case "s":
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage(MessageList.changeGamemode("survie"));
                    break;
                case "1":
                case "c":
                    player.setGameMode(GameMode.CREATIVE);
                    player.sendMessage(MessageList.changeGamemode("créatif"));
                    break;
                case "2":
                case "a":
                    player.setGameMode(GameMode.ADVENTURE);
                    player.sendMessage(MessageList.changeGamemode("aventure"));
                    break;
                case "3":
                case "spec":
                case "sp":
                    player.setGameMode(GameMode.SPECTATOR);
                    player.sendMessage(MessageList.changeGamemode("spectateur"));
                    break;
                default:
                    player.sendMessage(MessageList.gamemodeUsage());
                    break;
            }
        }
        else if (args.length >= 2) {
            String gm = args[0].toLowerCase();
            final Player target = getPlayer(args[1]);

            if (target == null) {
                player.sendMessage(MessageList.playerNotConnected());
                return true;
            }

            switch (gm) {
                case "0":
                case "s":
                    target.setGameMode(GameMode.SURVIVAL);
                    target.sendMessage(MessageList.changeGamemode("survie"));
                    break;
                case "1":
                case "c":
                    target.setGameMode(GameMode.CREATIVE);
                    target.sendMessage(MessageList.changeGamemode("créatif"));
                    break;
                case "2":
                case "a":
                    target.setGameMode(GameMode.ADVENTURE);
                    target.sendMessage(MessageList.changeGamemode("aventure"));
                    break;
                case "3":
                case "spec":
                case "sp":
                    target.setGameMode(GameMode.SPECTATOR);
                    target.sendMessage(MessageList.changeGamemode("spectateur"));
                    break;
                default:
                    target.sendMessage(MessageList.gamemodeUsage());
                    break;
            }
        }
        else player.sendMessage(MessageList.gamemodeUsage());
        return true;
    }

    @Override
    protected List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        player = (Player) sender;
        account = accountManager.getAccount(player);

        if (args.length == 0) {
            completions.add("0");
            completions.add("1");
            completions.add("2");
            completions.add("3");
            completions.add("s");
            completions.add("c");
            completions.add("a");
            completions.add("spec");
        }
        return completions;
    }
}
