package net.lonia.core.player.sanction;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.lonia.core.LoniaCore;
import net.lonia.core.database.MySQL;
import net.lonia.core.message.SanctionMessages;
import net.lonia.core.player.account.Account;
import net.lonia.core.player.staff.StaffManager;
import net.lonia.core.rank.Ranks;
import net.lonia.core.utils.Log;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SanctionManager {
    private static final MySQL mySQL = LoniaCore.get().getDataBaseManager().getMySQL();

    public static void ban(UUID uuid, String name, long durationInSeconds, String date, String moderator, String reason) {
        if (isSanctionActive(uuid, SanctionType.BAN)) {
            Bukkit.getPlayer(moderator).sendMessage("§9[Sanction] §3Erreur: Le joueur est déjà banni.");
            return;
        }

        long durationInMillis = durationInSeconds * 1000;
        final String sanitizedReason = reason == null ? "" : reason;

        mySQL.update(String.format("INSERT INTO ban (uuid, pseudo, duration, date, moderator, reason, status) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', 'active')",
                uuid.toString(), name, durationInMillis, date, moderator, sanitizedReason));

        mySQL.update(String.format("INSERT INTO sanctions (uuid, pseudo, type, duration, date, moderator, reason, message) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '')",
                uuid.toString(), name, SanctionType.BAN.getName(), durationInMillis, date, moderator, sanitizedReason));

        Bukkit.getPlayer(moderator).sendMessage("§9[Sanction] §bLe joueur à été sanctionné.");


        StaffManager.getStaffNames(Ranks.FONDATEUR.getPower()).forEach(staff -> {
            final UUID staffUUID = Account.getUUID(staff);

            Account account = LoniaCore.get().getAccountManager().getAccount(staffUUID);

            if (account == null) {
                account = new Account(staffUUID);
                account.loadData();
            }

            if (account.getUserData().getDataSettings().enabled_show_all_sanctions == 1) {
                StaffManager.sendMessageToStaff(staff, SanctionMessages.banAlert(name, moderator, reason));
                return;
            }
        });

        kick(name, SanctionMessages.ban(reason));

        final Player p = Bukkit.getPlayer(name);
        if (p == null) return;

        LoniaCore.connectedPlayer.remove(p);
        LoniaCore.get().getServerAccount().getDataServer().setConnectedPlayerCount(LoniaCore.connectedPlayer.size());
    }

    public static void mute(UUID uuid, String name, long durationInSeconds, String date, String moderator, String reason, String message) {
        if (isSanctionActive(uuid, SanctionType.MUTE)) {
            Bukkit.getPlayer(moderator).sendMessage("§9[Sanction] §3Erreur: Le joueur est déjà réduit au silence.");
            return;
        }
        long durationInMillis = durationInSeconds * 1000;

        mySQL.update(String.format("INSERT INTO mute (uuid, pseudo, duration, date, moderator, reason, message, status) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', 'active')",
                uuid.toString(), name, durationInMillis, date, moderator, reason, message));

        mySQL.update(String.format("INSERT INTO sanctions (uuid, pseudo, type, duration, date, moderator, reason, message) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                uuid.toString(), name, SanctionType.MUTE.getName(), durationInMillis, date, moderator, reason, message));

        Bukkit.getPlayer(moderator).sendMessage("§9[Sanction] §bLe joueur à été sanctionné.");

        StaffManager.getStaffNames(Ranks.FONDATEUR.getPower()).forEach(staff -> {
            final UUID staffUUID = Account.getUUID(staff);

            Account account = LoniaCore.get().getAccountManager().getAccount(staffUUID);

            if (account == null) {
                account = new Account(staffUUID);
                account.loadData();
            }

            if (account.getUserData().getDataSettings().enabled_show_all_sanctions == 1) {
                StaffManager.sendMessageToStaff(staff, SanctionMessages.muteAlert(name, moderator, reason));
                return;
            }
        });

        StaffManager.getStaffNames(Ranks.ADMIN.getPower()).forEach(staff -> StaffManager.sendMessageToStaff(staff, SanctionMessages.muteAlert(name, moderator, reason)));

        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Message");
        out.writeUTF(name);
        out.writeUTF(SanctionMessages.mute(reason));

        final Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        if (player != null)
        player.sendPluginMessage(LoniaCore.get(), "BungeeCord", out.toByteArray());
    }

    public static void mute(UUID uuid, String name, long durationInSeconds, String date, String moderator, String reason) {
        mute(uuid, name, durationInSeconds, date, moderator, reason, "CUSTOM");
    }

    public static String formatDuration(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if (days > 0) {
            return days + ((days > 1) ? " jours et " : " jour et ") + (hours % 24) + ((hours % 24 > 1) ? " heures" : " heure");
        } else if (hours > 0) {
            return hours + ((hours > 1) ? " heures et " : " heure et ") + (minutes % 60) + ((minutes % 60 > 1) ? " minutes" : " minute");
        } else if (minutes > 0) {
            return minutes + ((minutes > 1) ? " minutes et " : " minute et ") + (seconds % 60) + ((seconds % 60 > 1) ? " secondes" : " seconde");
        } else {
            return seconds + ((seconds > 1) ? " secondes" : " seconde");
        }
    }

    public static void kick(String name, String message) {
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("KickPlayer");
        out.writeUTF(name);
        out.writeUTF(message);

        final Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        if (player != null) player.sendPluginMessage(LoniaCore.get(), "BungeeCord", out.toByteArray());
    }

    public static void kick(Player player, String moderator, String reason) {
        kick(player.getDisplayName(), SanctionMessages.kick(moderator, reason));
    }

    public static String getBanReason(UUID uuid) {
        return getActiveSanctionReason(uuid, SanctionType.BAN);
    }

    public static String getMuteReason(UUID uuid) {
        return getActiveSanctionReason(uuid, SanctionType.MUTE);
    }

    public static String getMessage(UUID uuid) {
        final AtomicReference<String> message = new AtomicReference<>();

        mySQL.query(String.format("SELECT message FROM sanctions WHERE uuid='%s' AND type='%s'", uuid.toString(), SanctionType.MUTE.getName()), rs -> {
            try {
                if (rs.next()) {
                    message.set(rs.getString("message"));
                }
            } catch (SQLException e) {
                Log.log("§cCRITICAL : " + e.getMessage());
            }
        });

        return message.get();
    }

    private static String getActiveSanctionReason(UUID uuid, SanctionType type) {
        final AtomicReference<String> reason = new AtomicReference<>();

        mySQL.query(String.format("SELECT reason FROM sanctions WHERE uuid='%s' AND type='%s'", uuid.toString(), type.getName() ), rs -> {
            try {
                if (rs.next()) {
                    reason.set(rs.getString("reason"));
                }
            } catch (SQLException e) {
                Log.log("§cCRITICAL : " + e.getMessage());
            }
        });
        return reason.get();
    }

    public static long getBanRemainingTime(UUID uuid) {
        return getSanctionRemainingTime(uuid, SanctionType.BAN);
    }

    public static long getMuteRemainingTime(UUID uuid) {
        return getSanctionRemainingTime(uuid, SanctionType.MUTE);
    }

    private static long getSanctionRemainingTime(UUID uuid, SanctionType type) {
        final long[] remainingTime = {0};

        mySQL.query("SELECT duration, date FROM sanctions WHERE uuid='" + uuid.toString() + "' AND type='" + type.getName() + "'", rs -> {
            try {
                if (rs.next()) {
                    long duration = rs.getLong("duration");
                    String date = rs.getString("date");

                    long startTime = parseDate(date);
                    long endTime = startTime + duration;
                    long currentTime = System.currentTimeMillis();

                    remainingTime[0] = Math.max(0, endTime - currentTime);
                }
            } catch (SQLException e) {
                Log.log("§cCRITICAL : " + e.getMessage());
            }
        });
        return remainingTime[0];
    }

    public static boolean isSanctionActive(UUID uuid, SanctionType type) {
        AtomicBoolean found = new AtomicBoolean(false);

        mySQL.query("SELECT * FROM sanctions WHERE uuid='" + uuid.toString() + "' AND type='" + type.getName() + "'", rs -> {
            try {
                if (rs.next()) {
                    found.set(true);
                }
            } catch (SQLException e) {
                Log.log("§cCRITICAL : " + e.getMessage());
            }
        });
        return found.get();
    }

    public static void unban(UUID uuid) {
        //unban(uuid, "expired");
    }

    public static void unban(UUID uuid, String status) {
        if (!isSanctionActive(uuid, SanctionType.BAN)) return;

        mySQL.update("UPDATE ban SET status='" + status + "' WHERE uuid='" + uuid.toString() + "'");

        mySQL.update("DELETE FROM sanctions WHERE uuid='" + uuid.toString() + "' AND type='" + SanctionType.BAN.getName() + "'");
    }

    public static void unmute(UUID uuid, String status) {
        if (!isSanctionActive(uuid, SanctionType.MUTE)) return;

        mySQL.update("UPDATE mute SET status='" + status + "' WHERE uuid='" + uuid.toString() + "'");

        mySQL.update("DELETE FROM sanctions WHERE uuid='" + uuid.toString() + "' AND type='" + SanctionType.MUTE.getName() + "'");

        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Message");
        out.writeUTF(Account.getName(uuid));
        out.writeUTF("§9[Sanction] §bVotre sanction actuelle a été retirée. Vous pouvez à présent parler.");

        final Player sender = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        assert sender != null;
        sender.sendPluginMessage(LoniaCore.get(), "BungeeCord", out.toByteArray());
    }


    public static void unmute(UUID uuid) {
        unmute(uuid, "expired");
    }

    private static long parseDate(String date) {
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(date).getTime();
        } catch (java.text.ParseException e) {
            Log.log("§cCRITICAL : " + e.getMessage());
        }
        return 0;
    }

    public static String getModerator(UUID uuid) {
        AtomicReference<String> moderator = new AtomicReference<>("");

        mySQL.query("SELECT moderator FROM sanctions WHERE uuid='" + uuid.toString() + "'", rs -> {
            try {
                if (rs.next()) {
                    moderator.set(rs.getString("moderator"));
                }
            } catch (SQLException e) {
                Log.log("§cCRITICAL : " + e.getMessage());
            }
        });

        return moderator.get();
    }

    public static void updateBan(UUID uuid) {
        long currentTime = System.currentTimeMillis();
        mySQL.query("SELECT duration, date FROM sanctions WHERE uuid='" + uuid.toString() + "' AND type='" + SanctionType.BAN.getName() + "'", rs -> {
            try {
                if (rs.next()) {
                    long duration = rs.getLong("duration");
                    String date = rs.getString("date");

                    long banStartTime = parseDate(date);
                    long banEndTime = banStartTime + duration;

                    if (currentTime > banEndTime) {
                        unban(uuid);
                    }
                }
            } catch (SQLException e) {
                Log.log("§cCRITICAL : " + e.getMessage());
            }
        });
    }

    public static void updateMute(UUID uuid) {
        long currentTime = System.currentTimeMillis();
        mySQL.query("SELECT duration, date FROM sanctions WHERE uuid='" + uuid.toString() + "' AND type='" + SanctionType.MUTE.getName() + "'", rs -> {
            try {
                if (rs.next()) {
                    long duration = rs.getLong("duration");
                    String date = rs.getString("date");

                    long muteStartTime = parseDate(date);
                    long muteEndTime = muteStartTime + duration;

                    if (currentTime > muteEndTime) {
                        unmute(uuid);
                    }
                }
            } catch (SQLException e) {
                Log.log("§cCRITICAL : " + e.getMessage());
            }
        });
    }

    public static int getTotalSanctions(UUID uuid) {
        AtomicInteger total = new AtomicInteger();
        String sql = String.format(
                "SELECT (SELECT COUNT(*) FROM ban WHERE uuid='%s' AND status='expired') + (SELECT COUNT(*) FROM mute WHERE uuid='%s' AND status='expired') AS total;",
                uuid.toString(), uuid.toString()
        );

        mySQL.query(sql, rs -> {
            try {
                if (rs.next()) {
                    total.set(rs.getInt("total"));
                }
            } catch (SQLException e) {
                Log.log("§cCRITICAL : " + e.getMessage());
            }
        });
        return total.get();

    }

    public static String getPlayerStatus(UUID uuid) {
        final AtomicReference<String> status = new AtomicReference<>("§fN.S.");

        mySQL.query("SELECT type FROM sanctions WHERE uuid='" + uuid.toString() + "'", rs -> {
            try {
                if (rs.next()) {
                    String type = rs.getString("type");
                    if (SanctionType.BAN.getName().equalsIgnoreCase(type)) {
                        status.set("§cBan");
                    } else if (SanctionType.MUTE.getName().equalsIgnoreCase(type)) {
                        status.set("§cMute");
                    }
                }
            } catch (SQLException e) {
                Log.log("§cCRITICAL : " + e.getMessage());
            }
        });

        return status.get();
    }

}
