package net.lonia.core.utils;

public class LogManager {

    String pluginName, pluginVersion;

    public LogManager(String pluginName, String pluginVersion) {
        this.pluginName = pluginName;
        this.pluginVersion = pluginVersion;
    }

    public void onEnable() {
        Log.log("§a#===============[ " + pluginName + " ]================#");
        Log.log("§a#           " + pluginName + " is activated.          #");
        Log.log("§a#          Plugin version: §b" + pluginVersion + "§a        #");
        Log.log("§a#          Plugin author: §bBozorr§a        #");
        Log.log("§a#===========================================#");
    }

    public void onDisable() {
        Log.log("§a#===============[ " + pluginName + " ]================#");
        Log.log("§a#           " + pluginName + " is disabled.           #");
        Log.log("§a#         Plugin version: §b" + pluginVersion + "§a         #");
        Log.log("§a#         Plugin author: §bBozorr§a         #");
        Log.log("§a#===========================================#");
    }
}
