package net.lonia.hub.runable;

import net.lonia.hub.LoniaHub;

public class RunnableManager {

    public RunnableManager() {
        new HubRunnable().runTaskTimer(LoniaHub.getInstance(), 60L, 60L);
    }
}
