package net.lonia.core.runable;

import net.lonia.core.LoniaCore;

public class RunnableManager {

    public RunnableManager() {
        new CoreRunnable().runTaskTimer(LoniaCore.get(), 40L, 40L);
    }
}
