package net.lonia.moderation.commands.staff.modo;

import net.lonia.core.LoniaCore;
import net.lonia.core.commands.AbstractCommand;
import net.lonia.core.message.MessageList;
import net.lonia.core.player.account.Account;
import net.lonia.core.player.account.UserData;
import net.lonia.core.player.sanction.SanctionManager;
import net.lonia.core.rank.PermissionLevel;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CheckCMD extends AbstractCommand {
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
            final UUID uuid = Account.getUUID(targetName);

            if (uuid != null) {

                Account account = LoniaCore.get().getAccountManager().getAccount(uuid);
                if (account == null) account = new Account(uuid);
                account.loadData();

                final UserData userData = account.getUserData();

                SanctionManager.updateMute(uuid);
                SanctionManager.updateBan(uuid);

                player.sendMessage(
                        "\n§c " +
                                "\n§9[Staff] §bCheck de " + userData.getDataRank().getRank().getColor() + targetName + " §b:" +
                                "\n§c " +
                                "\n§b● §3Pseudo: " + userData.getDataRank().getRank().getColor() + targetName +
                                "\n§b● §3Grade: " + userData.getDataRank().getRank().getPrefix() +
                                "\n§b● §3Pearls: §f" + userData.getDataPearl().getPearl() +
                                "\n§c " +
                                "\n§b● §3Étoiles: §f" + userData.getDataEtoile().getEtoile() +
                                "\n§b● §3Niveau global: §f10" +
                                "\n§c " +
                                "\n§b● §3Nb de sanctions: §f" + SanctionManager.getTotalSanctions(uuid) +
                                "\n§b● §3Statuts: " + SanctionManager.getPlayerStatus(uuid) + " §f● " + (account.getUserData().isConnected() ? "§aConnecté" : "§cDéconnecté") +
                                "\n§c "
                );
            } else {
                player.sendMessage("§9[Sanction] §3Erreur: Ce pseudonyme n'existe pas.");
            }
        }

        return false;
    }

    @Override
    protected List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length == 1) {
            String prefix = args[0].toLowerCase();
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> !name.equalsIgnoreCase(sender.getName()))
                    .filter(name -> name.toLowerCase().startsWith(prefix))
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }
}
