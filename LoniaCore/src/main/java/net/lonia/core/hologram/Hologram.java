package net.lonia.core.hologram;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class Hologram {
    private String id;
    private Location location;
    private List<ArmorStand> armorStands;
    private static final double LINE_SPACING = 0.25;

    public Hologram(String id, Location location, List<String> lines) {
        this.id = id;
        this.location = location.clone();
        this.armorStands = new ArrayList<>();
        create(lines);
    }

    public String getId() {
        return id;
    }

    public Location getLocation() {
        return location.clone();
    }

    public List<String> getLines() {
        List<String> lines = new ArrayList<>();
        for (ArmorStand armorStand : armorStands) {
            lines.add(armorStand.getCustomName());
        }
        return lines;
    }

    public void setLines(List<String> lines) {
        delete();
        create(lines);
    }

    public void setLine(int index, String line) {
        if (index >= 0 && index < armorStands.size() && line != null) {
            armorStands.get(index).setCustomName(line);
        }
    }

    public void addLine(int index, String line) {
        if (index >= 0 && index <= armorStands.size() && line != null) {
            Location spawnLocation = location.clone().add(0.0, (armorStands.size() - index) * LINE_SPACING, 0.0);
            ArmorStand armorStand = spawnArmorStand(spawnLocation, line);
            armorStands.add(index, armorStand);
            for (int i = index + 1; i < armorStands.size(); i++) {
                ArmorStand stand = armorStands.get(i);
                stand.teleport(stand.getLocation().add(0.0, LINE_SPACING, 0.0));
            }
        }
    }

    public void removeLine(int index) {
        if (index >= 0 && index < armorStands.size()) {
            armorStands.get(index).remove();
            armorStands.remove(index);
            for (int i = index; i < armorStands.size(); i++) {
                ArmorStand stand = armorStands.get(i);
                stand.teleport(stand.getLocation().subtract(0.0, LINE_SPACING, 0.0));
            }
        }
    }

    public void refresh() {
        delete();
        create(getLines());
    }

    private void create(List<String> lines) {
        Location spawnLocation = location.clone().add(0.0, lines.size() * LINE_SPACING, 0.0);
        for (String line : lines) {
            String[] splitLines = line.split("\\n");
            for (int i = 0; i < splitLines.length; i++) {
                String singleLine = splitLines[i];
                ArmorStand armorStand = spawnArmorStand(spawnLocation, singleLine);
                armorStands.add(armorStand);
                spawnLocation.subtract(0.0, LINE_SPACING, 0.0);
            }
        }
    }

    public void delete() {
        for (ArmorStand armorStand : armorStands) {
            armorStand.remove();
        }
        armorStands.clear();
    }

    private ArmorStand spawnArmorStand(Location location, String line) {
        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        if (armorStand != null) {
            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName(line);
            armorStand.setVisible(false);
            armorStand.setGravity(false);
            armorStand.setBasePlate(false);
            armorStand.setMarker(true);
        }
        return armorStand;
    }
}
