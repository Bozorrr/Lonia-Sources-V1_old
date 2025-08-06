package net.lonia.core.server.account;

import net.lonia.core.server.status.ServerStatus;
import net.lonia.core.server.type.ServerType;
import org.bukkit.Server;

public class DataServer extends AbstractDataServer {

    private String bungeeName;
    private ServerType serverType;
    private ServerStatus serverStatus;

    private int connectedPlayerCount;
    private int chatEnabled;

    public DataServer(Server server) {
        this.server = server;
    }

    public String getBungeeName() {
        return bungeeName;
    }

    public void setBungeeName(String bungeeName) {
        this.bungeeName = bungeeName;
    }

    public ServerType getServerType() {
        return serverType;
    }

    public void setServerType(ServerType serverType) {
        this.serverType = serverType;
    }

    public ServerStatus getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(ServerStatus serverStatus) {
        this.serverStatus = serverStatus;
    }

    public int getConnectedPlayerCount() {
        return connectedPlayerCount;
    }

    public void setConnectedPlayerCount(int connectedPlayerCount) {
        this.connectedPlayerCount = connectedPlayerCount;
    }

    public void setChatEnabled(int chatEnabled) {
        this.chatEnabled = chatEnabled;
    }

    public int getChatEnabled() {
        return chatEnabled;
    }

    public boolean isChatEnabled() {
        return chatEnabled == 1;
    }
}
