package net.lonia.hub.runable;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class HubRunnable extends BukkitRunnable {

    public void run() {
        setDay();
        setWeatherClear();
    }

    private void setDay() {
        Bukkit.getServer().getWorlds().get(0).setTime(0L);
    }

    private void setWeatherClear() {
        Bukkit.getWorlds().get(0).setWeatherDuration(0);
        Bukkit.getWorlds().get(0).setThunderDuration(0);
        Bukkit.getWorlds().get(0).setStorm(false);
        Bukkit.getWorlds().get(0).setThundering(false);
    }
}
