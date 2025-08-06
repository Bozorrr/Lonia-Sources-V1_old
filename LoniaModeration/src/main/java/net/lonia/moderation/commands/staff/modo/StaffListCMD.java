package net.lonia.moderation.commands.staff.modo;

import net.lonia.core.LoniaCore;
import net.lonia.core.commands.AbstractCommand;
import net.lonia.core.database.MySQL;
import net.lonia.core.message.MessageList;
import net.lonia.core.player.account.Account;
import net.lonia.core.rank.PermissionLevel;
import net.lonia.core.rank.Ranks;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StaffListCMD extends AbstractCommand {
    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageList.commandNotAccessibleHere());
            return false;
        }

        player = (Player) sender;
        account = accountManager.getAccount(player);

        final MySQL mySQL = LoniaCore.get().getDataBaseManager().getMySQL();

        assert account != null;
        if (!account.hasPermissionLevel(PermissionLevel.CONSTRUCTEUR)) return true;

        StringBuilder msg = new StringBuilder("\n§9[Staff] §bMembres du staff connectés:\n \n");

        List<String> staff = new ArrayList<>();

        mySQL.query("SELECT name FROM data_players WHERE power<='" + Ranks.CONSTRUCTEUR.getPower() + "'", rs -> {
            try {
                while (rs.next()) {
                    String name = rs.getString("name");
                    if (Account.isConnected(name)) {
                        staff.add(name);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        staff.forEach(staffName -> {
            final UUID uuid = Account.getUUID(staffName);
            Account a = accountManager.getAccount(uuid);
            if (a == null) {
                a = new Account(uuid);
                a.loadData();
            }

            msg.append("\n§f- ").append(a.getUserData().getDataRank().getRank().getPrefix()).append(staffName).append(" §f● §3").append(a.getUserData().getServer());
        });

        msg.append("\n ");


        player.sendMessage(msg.toString());

        return true;
    }

    @Override
    protected List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return null;
    }
}
