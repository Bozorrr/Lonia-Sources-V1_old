package net.lonia.core.server;


import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.lonia.core.LoniaCore;
import net.lonia.core.database.MySQL;
import net.lonia.core.message.MessageList;
import net.lonia.core.server.status.ServerStatus;
import net.lonia.core.server.type.ServerType;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerManager {
    private static final MySQL mySQL = LoniaCore.get().getDataBaseManager().getMySQL();

    public static final Server lobby1 = new Server(11, "Lobby#1", "lobby#1", ServerType.LOBBY.getName(), ServerStatus.OFFLINE.getName(), 0, 1);

    public static final Server build1 = new Server(21, "Build#1", "build#1", ServerType.BUILD.getName(), ServerStatus.OFFLINE.getName(), 0, 1);
    public static final Server build2 = new Server(22, "Build#2", "build#2", ServerType.BUILD.getName(), ServerStatus.OFFLINE.getName(), 0, 1);
    public static final Server build3 = new Server(23, "Build#3", "build#3", ServerType.BUILD.getName(), ServerStatus.OFFLINE.getName(), 0, 1);
    public static final Server build4 = new Server(24, "Build#4", "build#4", ServerType.BUILD.getName(), ServerStatus.OFFLINE.getName(), 0, 1);
    public static final Server build5 = new Server(25, "Build#5", "build#5", ServerType.BUILD.getName(), ServerStatus.OFFLINE.getName(), 0, 1);
    public static final Server build6 = new Server(26, "Build#6", "build#6", ServerType.BUILD.getName(), ServerStatus.OFFLINE.getName(), 0, 1);
    public static final Server build7 = new Server(27, "Build#7", "build#7", ServerType.BUILD.getName(), ServerStatus.OFFLINE.getName(), 0, 1);
    public static final Server build8 = new Server(28, "Build#8", "build#8", ServerType.BUILD.getName(), ServerStatus.OFFLINE.getName(), 0, 1);
    public static final Server build9 = new Server(29, "Build#9", "build#9", ServerType.BUILD.getName(), ServerStatus.OFFLINE.getName(), 0, 1);
    public static final Server build10 = new Server(30, "Build#10", "build#10", ServerType.BUILD.getName(), ServerStatus.OFFLINE.getName(), 0, 1);
    public static final Server build11 = new Server(31, "Build#11", "build#11", ServerType.BUILD.getName(), ServerStatus.OFFLINE.getName(), 0, 1);
    public static final Server build12 = new Server(32, "Build#12", "build#12", ServerType.BUILD.getName(), ServerStatus.OFFLINE.getName(), 0, 1);

    public static final Server duel_pvp = new Server(41, "DuelPVP#1", "duelpvp#1", ServerType.DUEL.getName(), ServerStatus.OFFLINE.getName(), 0, 1);
    public static final Server arenec = new Server(42, "Arène Classique#1", "arenec#1", ServerType.ARENEC.getName(), ServerStatus.OFFLINE.getName(), 0, 1);
    public static final Server arenee = new Server(43, "Arène Effects#1", "arenee#1", ServerType.ARENEE.getName(), ServerStatus.OFFLINE.getName(), 0, 1);

    public static final Server freecube = new Server(51, "Freecube#1", "freecube#1", ServerType.FREECUBE.getName(), ServerStatus.OFFLINE.getName(), 0, 1);

    public static final Server unexus1 = new Server(61, "U-Nexus#1", "unexus#1", ServerType.UNEXUS.getName(), ServerStatus.OFFLINE.getName(), 0, 1);
    public static final Server unexus2 = new Server(62, "U-Nexus#2", "unexus#2", ServerType.UNEXUS.getName(), ServerStatus.OFFLINE.getName(), 0, 1);
    public static final Server unexus3 = new Server(63, "U-Nexus#3", "unexus#3", ServerType.UNEXUS.getName(), ServerStatus.OFFLINE.getName(), 0, 1);
    public static final Server unexus4 = new Server(64, "U-Nexus#4", "unexus#4", ServerType.UNEXUS.getName(), ServerStatus.OFFLINE.getName(), 0, 1);
    public static final Server unexus5 = new Server(65, "U-Nexus#5", "unexus#5", ServerType.UNEXUS.getName(), ServerStatus.OFFLINE.getName(), 0, 1);

    public static final Server runorkill1 = new Server(71, "RUN or KILL#1", "unorkill#1", ServerType.RUNORKILL.getName(), ServerStatus.OFFLINE.getName(), 0, 1);
    public static final Server runorkill2 = new Server(72, "RUN or KILL#2", "unorkill#2", ServerType.RUNORKILL.getName(), ServerStatus.OFFLINE.getName(), 0, 1);
    public static final Server runorkill3 = new Server(73, "RUN or KILL#3", "unorkill#3", ServerType.RUNORKILL.getName(), ServerStatus.OFFLINE.getName(), 0, 1);
    public static final Server runorkill4 = new Server(74, "RUN or KILL#4", "unorkill#4", ServerType.RUNORKILL.getName(), ServerStatus.OFFLINE.getName(), 0, 1);
    public static final Server runorkill5 = new Server(75, "RUN or KILL#5", "unorkill#5", ServerType.RUNORKILL.getName(), ServerStatus.OFFLINE.getName(), 0, 1);

    public static final Server rush1 = new Server(81, "Rush#1", "rush#1", ServerType.DUEL.getName(), ServerStatus.OFFLINE.getName(), 0, 1);
    public static final Server rush2 = new Server(82, "Rush#2", "rush#2", ServerType.DUEL.getName(), ServerStatus.OFFLINE.getName(), 0, 1);
    public static final Server rush3 = new Server(83, "Rush#3", "rush#3", ServerType.DUEL.getName(), ServerStatus.OFFLINE.getName(), 0, 1);
    public static final Server rush4 = new Server(84, "Rush#4", "rush#4", ServerType.DUEL.getName(), ServerStatus.OFFLINE.getName(), 0, 1);
    public static final Server rush5 = new Server(85, "Rush#5", "rush#5", ServerType.DUEL.getName(), ServerStatus.OFFLINE.getName(), 0, 1);

    public static final Server jumpaddv1 = new Server(101, "Jump/Aventure#1", "jumpaddv#1", ServerType.JUMPADVENTURE.getName(), ServerStatus.OFFLINE.getName(), 0, 1);
    public static final Server jumpaddv2 = new Server(102, "Jump/Aventure#2", "jumpaddv#2", ServerType.JUMPADVENTURE.getName(), ServerStatus.OFFLINE.getName(), 0, 1);
    public static final Server jumpaddv3 = new Server(103, "Jump/Aventure#3", "jumpaddv#3", ServerType.JUMPADVENTURE.getName(), ServerStatus.OFFLINE.getName(), 0, 1);

    public static final Server pixelwar = new Server(111, "Pixel War#1", "pixelwar#1", ServerType.PIXELWAR.getName(), ServerStatus.OFFLINE.getName(), 0, 1);

    private static final List<Server> servers = new ArrayList<>();

    public ServerManager() {
        loadServers();
        reloadServer();
    }

    public static void loadServers() {
        servers.add(lobby1);

        servers.add(build1);
        servers.add(build2);
        servers.add(build3);
        servers.add(build4);
        servers.add(build5);
        servers.add(build6);
        servers.add(build7);
        servers.add(build8);
        servers.add(build9);
        servers.add(build10);
        servers.add(build11);
        servers.add(build12);

        servers.add(duel_pvp);

        servers.add(freecube);

        servers.add(unexus1);
        servers.add(unexus2);
        servers.add(unexus3);
        servers.add(unexus4);
        servers.add(unexus5);

        servers.add(runorkill1);
        servers.add(runorkill2);
        servers.add(runorkill3);
        servers.add(runorkill4);
        servers.add(runorkill5);

        servers.add(rush1);
        servers.add(rush2);
        servers.add(rush3);
        servers.add(rush4);
        servers.add(rush5);

        servers.add(jumpaddv1);
        servers.add(jumpaddv2);
        servers.add(jumpaddv3);

        servers.add(pixelwar);
    }

    public static ServerType getType(int id) {
        return (ServerType) mySQL.query("SELECT * FROM data_servers WHERE id='" + id + "'", rs -> {
            try {
                if (rs.next()) {
                    return ServerType.getByName(rs.getString("type"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return ServerType.NOTFOUND;
        });
    }

    public static ServerStatus getStatus(int id) {
        return (ServerStatus) mySQL.query("SELECT * FROM data_servers WHERE id='" + id + "'", rs -> {
            try {
                if (rs.next()) {
                    return ServerStatus.getByName(rs.getString("status"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return ServerStatus.NOTFOUND;
        });
    }

    public static int getConnectedPlayers(int id) {
        return (int) mySQL.query("SELECT * FROM data_servers WHERE id='" + id + "'", rs -> {
            try {
                if (rs.next()) {
                    return rs.getInt("connected_player");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return 0;
        });
    }

    public static int getConnectedPlayersByType(ServerType type) {
        AtomicInteger connectedPlayers = new AtomicInteger(0);
        mySQL.query("SELECT * FROM data_servers WHERE type='" + type + "'", rs -> {
            try {
                while (rs.next()) {
                    connectedPlayers.addAndGet(rs.getInt("connected_player"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return 0;
        });
        return connectedPlayers.get();
    }

    public static int getEnabledChat(int id) {
        return (int) mySQL.query("SELECT * FROM data_servers WHERE id='" + id + "'", rs -> {
            try {
                if (rs.next()) {
                    return rs.getInt("enabled_chat");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return 0;
        });
    }

    public static void setType(int id, ServerType type) {
        mySQL.update("UPDATE data_servers SET type='" + type.getName() + "' WHERE id='" + id + "'");
    }

    public static void setStatus(int id, ServerStatus status) {
        mySQL.update("UPDATE data_servers SET status='" + status.getName() + "' WHERE id='" + id + "'");
    }

    public static void setConnectedPlayers(int id, int connectedPlayers) {
        mySQL.update("UPDATE data_servers SET connected_player='" + connectedPlayers + "' WHERE id='" + id + "'");
    }

    public static void setEnabledChat(int id, int enabledChat) {
        mySQL.update("UPDATE data_servers SET enabled_chat='" + enabledChat + "' WHERE id='" + id + "'");
    }

    public static int getTotalPlayer() {
        reloadServer();
        int i = 0;
        for (Server s : ServerManager.servers) {
            i = i + getConnectedPlayers(s.getId());
        }
        return i;
    }

    public static void reloadServer() {
        for (Server s : ServerManager.servers) {
            int id = s.getId();
            s.setType(getType(id));
            s.setStatus(getStatus(id));
            s.setConnectedPlayers(getConnectedPlayers(id));
            s.setEnabledChat(getEnabledChat(id));
        }
    }

    public static void connectToServer(Server server, Player player) {
        if (getStatus(server.getId()).getName().equals(ServerStatus.OFFLINE.getName())) {
            player.sendMessage(MessageList.serversOfflineServer());
            player.closeInventory();
            return;
        }

        if (LoniaCore.get().getServer().getServerName().equalsIgnoreCase(server.getServerName())) {
            player.sendMessage(MessageList.serversAlreadyOnServer(LoniaCore.get().getServer().getServerName()));
            player.closeInventory();
            return;
        }
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("Connect");
        out.writeUTF(server.getBungeeName());

        player.sendPluginMessage(LoniaCore.get(), "BungeeCord", out.toByteArray());
        player.sendMessage(MessageList.serversConnectedToServer(server.getServerName()));
        player.closeInventory();

    }

    public static void connectToLobby(Player player) {
        if (getStatus(lobby1.getId()).getName().equals(ServerStatus.OFFLINE.getName())) {
            player.sendMessage(MessageList.serversOfflineServer());
            return;
        }

        if (LoniaCore.get().getServer().getServerName().equalsIgnoreCase(lobby1.getServerName())) {
            player.sendMessage(MessageList.lobbyAlreadyOnLobby());
            return;
        }

        final ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("Connect");
        out.writeUTF(lobby1.getBungeeName());

        player.sendPluginMessage(LoniaCore.get(), "BungeeCord", out.toByteArray());
        player.sendMessage(MessageList.lobbyConnectToLobby());
    }

    public static void connectToHub(Player player) {
        if (getStatus(lobby1.getId()).getName().equals(ServerStatus.OFFLINE.getName())) {
            player.sendMessage(MessageList.serversOfflineServer());
            return;
        }

        if (LoniaCore.get().getServer().getServerName().equalsIgnoreCase(lobby1.getServerName())) {
            player.sendMessage(MessageList.hubAlreadyOnHub());
            return;
        }

        final ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("Connect");
        out.writeUTF(lobby1.getBungeeName());

        player.sendPluginMessage(LoniaCore.get(), "BungeeCord", out.toByteArray());
        player.sendMessage(MessageList.hubConnectToHub());
    }

    public static List<Server> getServers() {
        return servers;
    }
}

