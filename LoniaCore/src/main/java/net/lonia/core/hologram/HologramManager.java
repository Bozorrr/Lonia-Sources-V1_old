package net.lonia.core.hologram;

import net.lonia.core.LoniaCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HologramManager {
    private File hologramsFile;
    private FileConfiguration hologramsConfig;
    private Map<String, Hologram> holograms;

    private final LoniaCore core;

    public HologramManager(LoniaCore core) {
        this.core = core;
        this.holograms = new HashMap<>();
        this.hologramsFile = new File(core.getDataFolder(), "holograms.yml");

        if (!hologramsFile.exists()) {
            core.saveResource("holograms.yml", false);
        }
        this.hologramsConfig = YamlConfiguration.loadConfiguration(hologramsFile);
        loadHolograms();
    }

    public Hologram createHologram(String id, Location location, List<String> lines) {
        Hologram hologram = new Hologram(id, location, lines);
        holograms.put(id, hologram);
        saveHologram(hologram);
        return hologram;
    }

    public void removeHologram(String id) {
        Hologram hologram = holograms.get(id);
        if (hologram != null) {
            hologram.delete();
            holograms.remove(id);
            deleteHologram(id);
        }
    }

    public Hologram getHologram(String id) {
        return holograms.get(id);
    }

    public void updateHolograms() {
        for (Hologram hologram : holograms.values()) {
            hologram.refresh();
        }
    }

    private void loadHolograms() {
        for (String hologramId : hologramsConfig.getKeys(false)) {
            Location location = getLocationFromString(hologramsConfig.getString(hologramId + ".location"));
            List<String> lines = hologramsConfig.getStringList(hologramId + ".lines");

            Hologram hologram = new Hologram(hologramId, location, lines);
            holograms.put(hologramId, hologram);
        }
    }

    private void saveHologram(Hologram hologram) {
        hologramsConfig.set(hologram.getId() + ".location", getStringFromLocation(hologram.getLocation()));
        hologramsConfig.set(hologram.getId() + ".lines", hologram.getLines());
        saveConfig();
    }

    private void deleteHologram(String id) {
        hologramsConfig.set(id, null);
        saveConfig();
    }

    private void saveConfig() {
        try {
            hologramsConfig.save(hologramsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Location getLocationFromString(String locationString) {
        String[] parts = locationString.split(",");
        double x = Double.parseDouble(parts[0]);
        double y = Double.parseDouble(parts[1]);
        double z = Double.parseDouble(parts[2]);
        String worldName = parts[3];
        return new Location(Bukkit.getWorld(worldName), x, y, z);
    }

    private String getStringFromLocation(Location location) {
        return location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getWorld().getName();
    }

    public Map<String, Hologram> getHolograms() {
        return holograms;
    }

    public void onDisable() {
        for (Hologram hologram : holograms.values()) {
            hologram.delete();
        }
    }

}
