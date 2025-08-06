package net.lonia.gui.runnable;

import net.lonia.gui.LoniaGui;

public class RunnableManager {

    public RunnableManager() {
        new PluginRunnable().runTaskTimer(LoniaGui.getInstance(), 40L, 40L);
        new ItemsRunnable().runTaskTimer(LoniaGui.getInstance(), 40L, 40L);
    }
}
