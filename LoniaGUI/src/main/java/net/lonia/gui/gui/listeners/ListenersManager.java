package net.lonia.gui.gui.listeners;

import net.lonia.gui.LoniaGui;
import org.bukkit.plugin.PluginManager;

public class ListenersManager {

    public ListenersManager(LoniaGui main) {
        PluginManager pm = main.getServer().getPluginManager();
        pm.registerEvents(new ItemsListeners(), main);
    }
}
