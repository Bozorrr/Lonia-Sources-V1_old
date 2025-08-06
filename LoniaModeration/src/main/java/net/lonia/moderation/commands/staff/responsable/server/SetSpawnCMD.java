package net.lonia.moderation.commands.staff.responsable.server;

import net.lonia.core.LoniaCore;
import net.lonia.core.commands.AbstractCommand;
import net.lonia.core.message.MessageList;
import net.lonia.core.rank.PermissionLevel;
import net.lonia.core.utils.FileManager;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;

public class SetSpawnCMD extends AbstractCommand {

    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageList.commandNotAccessibleHere());
            return false;
        }

        player = (Player) sender;
        account = accountManager.getAccount(player);

        if (!account.hasPermissionLevel(PermissionLevel.ADMIN)) return true;

        if (args.length == 0) {
            Location location = player.getLocation();
            File file = new File(LoniaCore.get().getDataFolder(), "config.yml");
            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

            configuration.set("spawn.world", location.getWorld().getName());
            configuration.set("spawn.x", location.getX());
            configuration.set("spawn.y", location.getY());
            configuration.set("spawn.z", location.getZ());
            configuration.set("spawn.yaw", location.getYaw());
            configuration.set("spawn.pitch", location.getPitch());

            FileManager.saveFile(configuration, file);
            player.sendMessage(MessageList.setSpawnSuccessful());
        }
        return true;
    }

    @Override
    protected List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return null;
    }
}
