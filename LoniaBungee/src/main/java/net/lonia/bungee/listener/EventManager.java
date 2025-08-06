package net.lonia.bungee.listener;

import net.lonia.bungee.LoniaBungee;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class EventManager {


    public EventManager(LoniaBungee plugin) {
        PluginManager pm = LoniaBungee.getInstance().getProxy().getPluginManager();

        pm.registerListener(plugin, new ProxyPing());
        pm.registerListener(plugin, new PlayerListener());
        pm.registerListener(plugin, new PluginMessage());
    }
}
