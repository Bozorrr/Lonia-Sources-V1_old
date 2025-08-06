package net.lonia.core.commands;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.lonia.core.LoniaCore;
import net.lonia.core.message.MessageList;
import net.lonia.core.rank.PermissionLevel;
import net.lonia.core.server.ServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class StopCMD extends AbstractCommand {

    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            account = accountManager.getAccount(player);

            if (!account.hasPermissionLevel(PermissionLevel.FONDATEUR)) {
                player.sendMessage(MessageList.noPermission());
                return true;
            }

            player.sendMessage(MessageList.serverShutDown());
            Bukkit.getScheduler().runTaskLater(LoniaCore.get(), Bukkit::shutdown, 3 * 20L);
        } else {
            Bukkit.shutdown();
        }

        Bukkit.getOnlinePlayers().forEach(players -> {
            if (LoniaCore.get().getServerAccount().getId() == ServerManager.lobby1.getId()) {
                players.kickPlayer(MessageList.serverShutDown());
            } else {
                players.sendMessage(MessageList.serverShutDown());
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("Connect");
                out.writeUTF(ServerManager.lobby1.getBungeeName());
                players.sendPluginMessage(LoniaCore.get(), "BungeeCord", out.toByteArray());
            }
        });

        LoniaCore.get().getServerAccount().getDataServer().setConnectedPlayerCount(0);

        return true;
    }

    @Override
    protected List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return Collections.emptyList();
    }
}
