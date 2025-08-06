package net.lonia.core.utils;

import net.lonia.core.LoniaCore;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {

    static File dataFolder = LoniaCore.get().getDataFolder();

    public static void saveFile(YamlConfiguration configuration, File file) {
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createFile(String fileName) {
        if (!dataFolder.exists())
            dataFolder.mkdir();

        final File file = new File(dataFolder, fileName + ".yml");

        if (!file.exists()) try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteFile(String fileName) {
        if (dataFolder.exists()) {
            final File file = new File(dataFolder, fileName + ".yml");
            if (file.exists()) file.delete();
        }
    }

    public static YamlConfiguration readFile(String fileName) {
        final File file = new File(dataFolder, fileName + ".yml");
        return YamlConfiguration.loadConfiguration(file);
    }
}
