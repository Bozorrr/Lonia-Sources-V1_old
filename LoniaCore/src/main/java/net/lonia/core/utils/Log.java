package net.lonia.core.utils;

import org.bukkit.Bukkit;

public class Log {

    public static void log(String log) {
        Bukkit.getConsoleSender().sendMessage(log);
    }
}
