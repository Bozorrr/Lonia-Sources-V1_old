package net.lonia.core.player.staff;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.lonia.core.LoniaCore;
import net.lonia.core.database.MySQL;
import net.lonia.core.utils.Log;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class StaffManager {

    private final static MySQL mySQL = LoniaCore.get().getDataBaseManager().getMySQL();

    public static int enabled_show_ban = 0; //ban

    public static void setEnabled_show_ban(int enabled_show_ban) {
        StaffManager.enabled_show_ban = enabled_show_ban;
    }

    public static Set<String> getStaffNamesWithPower(int power) {
        final Set<String> staffs = new HashSet<>();

        mySQL.query("SELECT name FROM data_players WHERE power>='" + power + "'", rs -> {
            try {
                while (rs.next()) {
                    staffs.add(rs.getString("name"));
                }
            } catch (SQLException e) {
                Log.log("§cCRITICAL" + e.getMessage());
            }
        });

        return staffs;
    }

    public static Set<String> getStaffNames(int power) {
        final Set<String> staffs = new HashSet<>();

        mySQL.query("SELECT name FROM data_players WHERE power='" + power + "'", rs -> {
            try {
                while (rs.next()) {
                    staffs.add(rs.getString("name"));
                }
            } catch (SQLException e) {
                Log.log("§cCRITICAL" + e.getMessage());
            }
        });

        return staffs;
    }

    public static void sendMessageToStaff(String name, String message) {
        final ByteArrayDataOutput o = ByteStreams.newDataOutput();
        o.writeUTF("Message");
        o.writeUTF(name);
        o.writeUTF(message);
        final Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        assert player != null;
        player.sendPluginMessage(LoniaCore.get(), "BungeeCord", o.toByteArray());
    }

    public static boolean isInMod(UUID uuid) {
        AtomicBoolean bool = new AtomicBoolean(false);
        mySQL.query("SELECT pseudo FROM moderation WHERE uuid='" + uuid.toString() + "'", rs -> {
            try {
                if (rs.next()) {
                    bool.set(true);
                }
            } catch (SQLException e) {
                Log.log("§cCRITICAL : " + e.getMessage());
            }
        });

        return bool.get();
    }

    public static void setInMod(UUID uuid) {
        if (isInMod(uuid)) return;
        mySQL.update(String.format("INSERT INTO moderation (uuid, pseudo, vanish) VALUES ('%s', '%s', '%s')", uuid.toString(), Bukkit.getPlayer(uuid).getDisplayName(), 1));
    }

    public static void removeInMod(UUID uuid) {
        if (!isInMod(uuid)) return;
        mySQL.update("DELETE FROM moderation WHERE uuid='" + uuid.toString() + "'");
    }

    public static boolean isInVanish(UUID uuid) {
        if (!isInMod(uuid)) return false;
        AtomicBoolean bool = new AtomicBoolean(false);
        mySQL.query("SELECT vanish FROM moderation WHERE uuid='" + uuid.toString() + "'", rs -> {
            try {
                if (rs.next()) {
                    if (rs.getInt("vanish") == 1) {
                        bool.set(true);
                    }
                }
            } catch (SQLException e) {
                Log.log("§cCRITICAL : " + e.getMessage());
            }
        });

        return bool.get();
    }

    public static void setInVanish(UUID uuid, int i) {
        if (!isInMod(uuid)) return;
        mySQL.update(String.format("UPDATE moderation SET vanish='%s' WHERE uuid='%s'", i, uuid.toString()));
    }
}
