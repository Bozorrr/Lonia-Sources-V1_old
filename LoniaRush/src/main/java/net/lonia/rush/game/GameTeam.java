package net.lonia.rush.game;

import net.lonia.rush.LoniaRush;
import net.lonia.rush.game.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum GameTeam {

    ORANGE("Orange", 10, "§6Orange ", getBedLocation("Lit Orange"), getBedLocation("Lit Jaune")),
    VIOLET("Violet", 11, "§5Violet ", getBedLocation("Lit Violet"), getBedLocation("Lit Rose")),
    RANDOM("Aléatoire", 12, "§fAléatoire ", null, null);

    final String name;
    final int power;
    final String prefix;
    final Location mainBed, secondaryBed;

    GameTeam(String name, int power, String prefix, Location mainBed, Location secondaryBed) {
        this.name = name;
        this.power = power;
        this.prefix = prefix;
        this.mainBed = mainBed;
        this.secondaryBed = secondaryBed;
    }

    public static GameTeam getByName(String name) {
        return Arrays.stream(values()).filter(team -> team.getName().equalsIgnoreCase(name)).findFirst().orElse(GameTeam.RANDOM);
    }

    public static void teleportToSpawn(Player player) {
        PlayerData data = LoniaRush.INSTANCE.getGameManager().getGameByPlayer(player).getPlayerData(player);
        final Location loc = data.getTeam().getMainBed();
        loc.setY(loc.getY());
        player.teleport(loc);

    }

    public static List<String> getPlayersInTeam(GameTeam team, Game game) {
        if (team == GameTeam.ORANGE) return game.getOrangeTeamPlayers();
        else if (team == GameTeam.VIOLET) return game.getVioletTeamPlayers();
        return Collections.emptyList();
    }


    static Location getBedLocation(String name) {
        final FileConfiguration config = LoniaRush.INSTANCE.getConfig();
        final ConfigurationSection s = config.getConfigurationSection("beds." + name);

        final World world = Bukkit.getWorld(s.getString("world"));
        final int x = s.getInt("x");
        final int y = s.getInt("y");
        final int z = s.getInt("z");

        return new Location(world, x, y, z);
    }

    public String getName() {
        return this.name;
    }

    public int getPower() {
        return power;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public Location getMainBed() {
        return this.mainBed;
    }

    public Location getSecondaryBed() {
        return this.secondaryBed;
    }
}

