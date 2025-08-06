package net.lonia.core.listeners;

import net.lonia.core.LoniaCore;
import org.bukkit.plugin.PluginManager;

public class ListenersManager {

    public ListenersManager(LoniaCore core) {
        PluginManager pm = core.getServer().getPluginManager();
        pm.registerEvents(new onJoin(), core);
        pm.registerEvents(new onChat(), core);
        pm.registerEvents(new onQuit(), core);
    }
}
