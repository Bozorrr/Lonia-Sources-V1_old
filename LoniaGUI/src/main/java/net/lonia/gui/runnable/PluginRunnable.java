package net.lonia.gui.runnable;

import net.lonia.core.server.ServerManager;
import org.bukkit.scheduler.BukkitRunnable;

public class PluginRunnable extends BukkitRunnable {

    @Override
    public void run() {
        ServerManager.reloadServer();
    }
}
