package net.lonia.bungee.utils;

public class LogManager {

    public void onEnable() {
        Log.log("§a#=============[ LoniaBungee ]==============#");
        Log.log("§a#          LoniaBungee is activated.       #");
        Log.log("§a#          Plugin version: §b1.0.0§a       #");
        Log.log("§a#          Plugin author: §bBozorr§a       #");
        Log.log("§a#==========================================#");
    }

    public void onDisable() {
        Log.log("§a#=============[ LoniaBungee ]==============#");
        Log.log("§a#         LoniaBungee is disabled.         #");
        Log.log("§a#         Plugin version: §b1.0.0§a        #");
        Log.log("§a#         Plugin author: §bBozorr§a        #");
        Log.log("§a#==========================================#");
    }
}
