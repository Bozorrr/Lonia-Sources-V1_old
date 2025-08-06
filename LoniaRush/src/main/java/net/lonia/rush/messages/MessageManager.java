package net.lonia.rush.messages;

import net.lonia.rush.LoniaRush;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MessageManager {

    public static String butMessage;
    public static String gameCancel;
    public static String gameStart;
    public static String gameStartIn;
    public static String selectTeam;
    public static String intermediateBedBreak;

    public static void loadMessages() {
        butMessage = loadMessage("but").replaceAll("&", "§");
        gameCancel = loadMessage("game.cancel").replaceAll("&", "§");
        gameStart = loadMessage("game.start").replaceAll("&", "§");
        gameStartIn = loadMessage("game.start-in").replaceAll("&", "§");
        selectTeam = loadMessage("game.select-team").replaceAll("&", "§");
        intermediateBedBreak = loadMessage("game.intermediate-bed-break").replaceAll("&", "§");
    }

    public static void reloadMessages() {
        loadMessages();
    }

    private static String loadMessage(String message) {
        final File file = new File(LoniaRush.INSTANCE.getDataFolder(), "config.yml");
        final YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        final String key = "messages.";

        return config.getString(key + message);
    }
}
