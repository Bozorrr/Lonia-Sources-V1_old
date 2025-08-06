package net.lonia.core.commands.player.navigation;

import net.lonia.core.commands.AbstractCommand;
import net.lonia.core.message.MessageList;
import net.lonia.core.rank.PermissionLevel;
import net.lonia.core.utils.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class SpawnCMD extends AbstractCommand {

    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageList.commandNotAccessibleHere());
            return false;
        }

        player = (Player) sender;
        account = accountManager.getAccount(player);

        if (!account.hasPermissionLevel(PermissionLevel.JOUEUR)) return true;

        YamlConfiguration configuration = FileManager.readFile("config");
        ConfigurationSection configurationSection = configuration.getConfigurationSection("spawn");

        if (configurationSection != null) {

            Location spawn = new Location(Bukkit.getWorld(configurationSection.getString("world")), configurationSection.getDouble("x"), configurationSection.getDouble("y"), configurationSection.getDouble("z"), (float) configurationSection.getDouble("yaw"), (float) configurationSection.getDouble("pitch"));

            player.teleport(spawn);
            player.sendMessage(MessageList.spawnTeleport());
        }
        return true;
    }

    @Override
    protected List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return null;
    }
}
