package net.lonia.gui;

import net.lonia.core.utils.LogManager;
import net.lonia.gui.gui.GuiManager;
import net.lonia.gui.gui.listeners.ListenersManager;
import net.lonia.gui.runnable.RunnableManager;
import org.bukkit.plugin.java.JavaPlugin;

public class LoniaGui extends JavaPlugin {
    private static LoniaGui instance;
    private final LogManager logManager = new LogManager("LoniaGUI", "1.0.0");
    private GuiManager guiManager;

    public static LoniaGui getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        this.guiManager = new GuiManager();

        this.guiManager.loadGui();

        new ListenersManager(this);

        new RunnableManager();

        this.logManager.onEnable();
    }

    @Override
    public void onDisable() {
        this.logManager.onDisable();
    }

    public GuiManager getGuiManager() {
        return this.guiManager;
    }
}
