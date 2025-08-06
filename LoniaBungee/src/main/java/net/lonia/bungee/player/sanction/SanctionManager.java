package net.lonia.bungee.player.sanction;

import net.lonia.bungee.LoniaBungee;
import net.lonia.bungee.database.MySQL;

import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class SanctionManager {
    private static final MySQL mySQL = LoniaBungee.getInstance().getDataBaseManager().getMySQL();


    /*
    public static void ban(UUID uuid, String name, long durationInSeconds, String date, String moderator, String reason) {
        if (isSanctionActive(uuid, SanctionType.BAN)) return;

        long durationInMillis = durationInSeconds * 1000;
        final String sanitizedReason = reason == null ? "" : reason;

        mySQL.update("INSERT INTO ban (uuid, pseudo, duration, date, moderator, reason, status) VALUES ('"
                + uuid.toString() + "', '" + name + "', '" + durationInMillis + "', '" + date + "', '"
                + moderator + "', '" + sanitizedReason + "', 'enable')");

        mySQL.update("INSERT INTO sanctions (uuid, pseudo, type, duration, date, moderator, reason, message) VALUES ('"
                + uuid.toString() + "', '" + name + "', '" + SanctionType.BAN.getName()
                + "', '" + durationInMillis + "', '" + date + "', '" + moderator + "', '" + sanitizedReason + "', 'BAN')");

        final ProxiedPlayer modo = ProxyServer.getInstance().getPlayer(moderator);
        List<String> staff = new ArrayList<>();

        mySQL.query("SELECT name FROM data_players WHERE grade='" + Ranks.FONDATEUR.getName() + "'", rs -> {
            try {
                while (rs.next()) {
                    staff.add(rs.getString("name"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        staff.forEach(n -> {
            final ByteArrayDataOutput o = ByteStreams.newDataOutput();
            o.writeUTF("Message");
            o.writeUTF(n);
            o.writeUTF("\n§9[Sanction] §cBan\n§3Le joueur " + name + " à été banni par " + moderator + " pour " + reason);
            modo.sendData(LoniaCore.get(), "BungeeCord", o.toByteArray());
        });

        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("KickPlayer");
        out.writeUTF(name);
        out.writeUTF("§4✖ §cVous avez été banni du serveur ! \n§4✖\n§cInfraction : " + reason);

        modo.sendPluginMessage(LoniaCore.get(), "BungeeCord", out.toByteArray());

        final Player p = Bukkit.getPlayer(name);
        if(p==null) return;

        LoniaCore.connectedPlayer.remove(p);
        LoniaCore.get().getServerAccount().getDataServer().setConnectedPlayerCount(LoniaCore.connectedPlayer.size());
    }*/
/*
    public static void mute(UUID uuid, String name, long durationInSeconds, String date, String moderator, String reason, String message) {
        if (isSanctionActive(uuid, SanctionType.MUTE)) return;

        long durationInMillis = durationInSeconds * 1000;

        mySQL.update("INSERT INTO mute (uuid, pseudo, duration, date, moderator, reason, message, status) VALUES ('"
                + uuid.toString() + "', '" + name + "', '" + durationInMillis + "', '" + date + "', '"
                + moderator + "', '" + reason + "', '" + message + "', 'enable')");

        mySQL.update("INSERT INTO sanctions (uuid, pseudo, type, duration, date, moderator, reason, message) VALUES ('"
                + uuid.toString() + "', '" + name + "', '" + SanctionType.MUTE.getName()
                + "', '" + durationInMillis + "', '" + date + "', '" + moderator + "', '" + reason + "', '" + message + "')");

        final Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            player.sendMessage("\n§9[Sanction] §c[Mute] \n \n§3Vous avez été réduit au silence !\nMotif : " + reason + "\n ");
        }

        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Message");
        out.writeUTF(name);
        out.writeUTF("\n§9[Sanction] §c[Mute] \n \n§3Vous avez été réduit au silence !\nMotif : " + reason + "\n ");

        final Player sender = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        sender.sendPluginMessage(LoniaCore.get(), "BungeeCord", out.toByteArray());

        List<String> staff = new ArrayList<>();

        mySQL.query("SELECT name FROM data_players WHERE grade='" + Ranks.FONDATEUR.getName() + "'", rs -> {
            try {
                while (rs.next()) {
                    staff.add(rs.getString("name"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        staff.forEach(n -> {
            final ByteArrayDataOutput o = ByteStreams.newDataOutput();
            o.writeUTF("Message");
            o.writeUTF(n);
            o.writeUTF("\n§9[Sanction] §cMute\n§3Le joueur " + name + " à été réduit au silence par " + moderator + " pour " + reason + ".");
            sender.sendPluginMessage(LoniaCore.get(), "BungeeCord", o.toByteArray());
        });
    }*/
/*
    public static void mute(UUID uuid, String name, long durationInSeconds, String date, String moderator, String reason) {
        mute(uuid, name, durationInSeconds, date, moderator, reason, "CUSTOM");
    }*/

    public static String formatDuration(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if (days > 0) {
            return days + ((days > 1) ? " jours et" : "jour et ") + (hours % 24) + ((hours % 24 > 1) ? " heures" : " heure");
        } else if (hours > 0) {
            return hours + ((hours > 1) ? " heures et" : "heure et ") + (minutes % 60) + ((minutes % 60 > 1) ? " minutes" : " minute");
        } else if (minutes > 0) {
            return minutes + ((minutes > 1) ? " minutes et" : "minute et ") + (seconds % 60) + ((seconds % 60 > 1) ? " secondes" : " seconde");
        } else {
            return seconds + ((seconds > 1) ? " secondes" : " seconde");
        }
    }
/*
    public static void kick(Player player, String moderator, String reason) {
        player.kickPlayer("§cVous avez été éjecté du serveur par " + moderator + " !\n§cRaison : " + reason);
    }*/

    public static String getBanReason(UUID uuid) {
        return getSanctionReason(uuid, SanctionType.BAN);
    }

    public static String getMuteReason(UUID uuid) {
        return getSanctionReason(uuid, SanctionType.MUTE);
    }

    private static String getSanctionReason(UUID uuid, SanctionType type) {
        final String[] reason = {""};

        mySQL.query("SELECT reason FROM sanctions WHERE uuid='" + uuid.toString() + "' AND type='" + type.getName() + "'", rs -> {
            try {
                if (rs.next()) {
                    reason[0] = rs.getString("reason");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return reason[0];
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

                    // Calculer le temps restant
                    long startTime = parseDate(date); // Convertir la date en millisecondes
                    long endTime = startTime + duration;
                    long currentTime = System.currentTimeMillis();

                    remainingTime[0] = Math.max(0, endTime - currentTime); // Retourner le temps restant en millisecondes
                }
            } catch (SQLException e) {
                e.printStackTrace();
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
                e.printStackTrace();
            }
        });
        return found.get();
    }

    public static void unban(UUID uuid) {
        if (!isSanctionActive(uuid, SanctionType.BAN)) return;
        mySQL.update("DELETE FROM sanctions WHERE uuid='" + uuid.toString() + "' AND type='" + SanctionType.BAN.getName() + "'");
    }

    public static void unmute(UUID uuid) {
        if (!isSanctionActive(uuid, SanctionType.MUTE)) return;
        mySQL.update("DELETE FROM sanctions WHERE uuid='" + uuid.toString() + "' AND type='" + SanctionType.MUTE.getName() + "'");
    }

    private static long parseDate(String date) {
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(date).getTime();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getModerator(UUID uuid) {
        AtomicReference<String> moderator = new AtomicReference<>("");

        mySQL.query("SELECT moderator FROM ban WHERE uuid='" + uuid.toString() + "'", rs -> {
            try {
                if (rs.next()) {
                    moderator.set(rs.getString("moderator"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
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
                    mySQL.update("UPDATE ban SET status='expired' WHERE uuid='" + uuid + "'");
                }
            } catch (SQLException e) {
                e.printStackTrace();
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
                    mySQL.update("UPDATE mute SET status='expired' WHERE uuid='" + uuid + "'");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
