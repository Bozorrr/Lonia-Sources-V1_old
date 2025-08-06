package net.lonia.moderation.listeners;

import net.lonia.moderation.LoniaModeration;
import org.bukkit.plugin.PluginManager;

public class ListenersManager {

    public ListenersManager(LoniaModeration main) {
        PluginManager pm = main.getServer().getPluginManager();
        pm.registerEvents(new ModListeners(), main);
    }
}
