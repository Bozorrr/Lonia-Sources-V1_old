package net.lonia.core.player.sanction;

import net.lonia.core.LoniaCore;
import net.lonia.core.database.MySQL;
import org.bukkit.entity.Player;

public class ReportManager {

    private static final MySQL mySQL = LoniaCore.get().getDataBaseManager().getMySQL();

    public static void addReport(Player target, Player reporter, SanctionType type, String reason, String message) {
        mySQL.update(String.format("INSERT INTO reports (uuid, pseudo, reporter, type, reason, message) VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
                target.getUniqueId().toString(), target.getDisplayName(), reporter.getDisplayName(), type.getName(), reason, message));
    }

}
