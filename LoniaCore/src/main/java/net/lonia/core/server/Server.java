package net.lonia.core.server;

import net.lonia.core.server.status.ServerStatus;
import net.lonia.core.server.type.ServerType;

public class Server {
    private final int id;
    private final String serverName;
    private final String bungeeName;
    private String type;
    private String status;
    private final String prefix1;
    private final String prefix2;
    private final String prefix3;
    private int connectedPlayers;
    private int enabledChat;

    public Server(int id, String serverName, String bungeeName, String type, String status, int connectedPlayers, int enabledChat) {
        this.id = id;
        this.serverName = serverName;
        this.bungeeName = bungeeName;
        this.type = type;
        this.status = status;
        this.prefix1 = "§9" + serverName;
        this.prefix2 = "§b" + serverName;
        this.prefix3 = "§3" + serverName;
        this.connectedPlayers = connectedPlayers;
        this.enabledChat = enabledChat;
    }

    public int getId() {
        return id;
    }

    public String getServerName() {
        return serverName;
    }

    public String getBungeeName() {
        return bungeeName;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getPrefix1() {
        return prefix1;
    }

    public String getPrefix2() {
        return prefix2;
    }

    public String getPrefix3() {
        return prefix3;
    }

    public int getConnectedPlayers() {
        return connectedPlayers;
    }

    public int getEnabledChat() {
        return enabledChat;
    }

    public void setType(ServerType type) {
        this.type = type.getName();
    }

    public void setStatus(ServerStatus status) {
        this.status = status.getName();
    }

    public void setConnectedPlayers(int connectedPlayers) {
        this.connectedPlayers = connectedPlayers;
    }

    public void setEnabledChat(int enabledChat) {
        this.enabledChat = enabledChat;
    }
}
