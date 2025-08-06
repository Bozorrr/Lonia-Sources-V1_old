package net.lonia.moderation.commands.player;

import net.lonia.core.commands.AbstractCommand;
import net.lonia.core.message.MessageList;
import net.lonia.core.rank.PermissionLevel;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class MuteHelpCMD extends AbstractCommand {

    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageList.commandNotAccessibleHere());
            return false;
        }

        player = (Player) sender;
        account = accountManager.getAccount(player);

        if (!account.hasPermissionLevel(PermissionLevel.JOUEUR)) return true;

        player.sendMessage("\n §9[Sanction] §3Mute HELP:\n " +
                "\n" +
                "§f ‣ §bILC ? (Infraction Liée au Chat\n" +
                "§3Afin de savoir à quoi cela correspond, rendez-vous sur\n" +
                "§f§nnotre serveur discord§r§3 dans le salon §f#règlement§3.\n " +
                "\n" +
                "§f ‣ §bUne erreur ?\n" +
                "§3Rejoignez un salon vocal §f#attente-staff§3, un membre de\n" +
                "notre équipe viendra vous aider !\n " +
                "\n" +
                "§4⚠ §cAttention, si vous vous amusez à déranger le staff inutilement, vous pourriez être davantage sanctionné !");
        return false;
    }

    @Override
    protected List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return Collections.emptyList();
    }
}
