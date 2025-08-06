package net.lonia.bungee.player.account;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

abstract class AbstractData {

    public UUID uuid;

    public String name;

    public String getUUID() {
        return this.uuid.toString();
    }

    ProxiedPlayer getPlayer() { return ProxyServer.getInstance().getPlayer(uuid); }

    public String getName() {
        return name;
    }
}