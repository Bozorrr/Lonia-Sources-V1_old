package net.lonia.rush.listeners;

import net.lonia.rush.LoniaRush;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class ListenersManager {

    public ListenersManager(LoniaRush plugin) {
        final PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new PlayerJoinListener(), plugin);
        pm.registerEvents(new PlayerQuitListener(), plugin);
        pm.registerEvents(new PlayerDeathListener(), plugin);
        pm.registerEvents(new PlayerMoveListener(), plugin);
        pm.registerEvents(new GameListeners(), plugin);
        pm.registerEvents(new ItemsListeners(), plugin);
    }
}
