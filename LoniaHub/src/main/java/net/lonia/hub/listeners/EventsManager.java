package net.lonia.hub.listeners;

import net.lonia.hub.LoniaHub;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class EventsManager {

    public EventsManager() {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new PlayerListener(), LoniaHub.getInstance());
    }
}
