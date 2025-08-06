package net.lonia.core.player.account;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

abstract class AbstractData {

    public UUID uuid;

    public String name;

    public String getUUID() {
        return this.uuid.toString();
    }

    Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public String getName() {
        return name;
    }
}