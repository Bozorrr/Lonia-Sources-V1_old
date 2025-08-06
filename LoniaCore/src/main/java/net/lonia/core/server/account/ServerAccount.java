package net.lonia.core.server.account;

import net.lonia.core.LoniaCore;
import net.lonia.core.database.MySQL;
import net.lonia.core.server.status.ServerStatus;
import net.lonia.core.server.type.ServerType;
import org.bukkit.Server;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerAccount extends AbstractDataServer {
    private final MySQL mySQL = LoniaCore.get().getDataBaseManager().getMySQL();

    private final DataServer dataServer;
    private final int id;

    public ServerAccount(Server server) {
        this.server = server;
        this.dataServer = new DataServer(server);
        this.id = Integer.parseInt(server.getServerId());
    }

    private void getDataFromMySQL() {
        mySQL.query("SELECT * FROM data_servers WHERE id='" + id + "'", rs -> {
            try {
                if (rs.next()) {
                    dataServer.setBungeeName(rs.getString("bungee_name"));
                    dataServer.setServerType(ServerType.getByName(rs.getString("type")));
                    dataServer.setServerStatus(ServerStatus.getByName(rs.getString("status")));
                    dataServer.setConnectedPlayerCount(rs.getInt("connected_player"));
                    dataServer.setChatEnabled(rs.getInt("enabled_chat"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void updateData() {
        sendDataToMySQL();
        getDataFromMySQL();
    }

    private void sendDataToMySQL() {
        if (!hasAccount()) {
            mySQL.update(String.format("INSERT INTO data_servers (id, name, bungee_name, type, status, connected_player, enabled_chat) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                    id, server.getServerName(), dataServer.getBungeeName(), dataServer.getServerType().getName(),
                    dataServer.getServerStatus().getName(), 0, dataServer.getChatEnabled()));
        } else {
            String updateDataPlayersQuery = String.format("UPDATE data_servers SET name='%s', bungee_name='%s', type='%s', status='%s', connected_player='%s', enabled_chat='%s' WHERE id='%s'",
                    server.getServerName(), dataServer.getBungeeName(), dataServer.getServerType().getName(),
                    dataServer.getServerStatus().getName(), dataServer.getConnectedPlayerCount(), dataServer.getChatEnabled(), id);
            mySQL.update(updateDataPlayersQuery);
        }
    }

    private boolean hasAccount() {
        AtomicBoolean found = new AtomicBoolean(false);

        mySQL.query("SELECT * FROM data_servers WHERE id='" + this.id + "'", rs -> {
            try {
                if (rs.next()) found.set(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return found.get();
    }

    public void onLogin() {
        if (!hasAccount()) {
            dataServer.setBungeeName(server.getServerName().toLowerCase());
            dataServer.setServerType(ServerType.DEFAULT);
            dataServer.setServerStatus(ServerStatus.ONLINE);
            dataServer.setConnectedPlayerCount(server.getOnlinePlayers().size());
            dataServer.setChatEnabled(1);

            sendDataToMySQL();
        } else {
            dataServer.setBungeeName(dataServer.getBungeeName());
            dataServer.setServerType(dataServer.getServerType());
            dataServer.setServerStatus(dataServer.getServerStatus());
            dataServer.setConnectedPlayerCount(dataServer.getConnectedPlayerCount());
            dataServer.setChatEnabled(dataServer.getChatEnabled());
        }

        getDataFromMySQL();
        LoniaCore.get().getAccountManager().getServerAccounts().add(this);
    }

    public void onLogout() {
        sendDataToMySQL();
        LoniaCore.get().getAccountManager().getServerAccounts().remove(this);
    }

    public DataServer getDataServer() {
        return dataServer;
    }

    public int getId() {
        return id;
    }
}
