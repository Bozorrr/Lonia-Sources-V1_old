package net.lonia.core.commands;

import net.lonia.core.commands.player.navigation.HubCMD;
import net.lonia.core.commands.player.navigation.LobbyCMD;
import net.lonia.core.commands.player.navigation.SpawnCMD;
import net.lonia.core.commands.player.social.IgnoreCMD;
import net.lonia.core.commands.player.social.MPCMD;
import net.lonia.core.commands.player.social.RCMD;
import net.lonia.core.commands.player.utils.DiscordCMD;
import net.lonia.core.commands.player.utils.HelpCMD;
import net.lonia.core.commands.player.utils.PluginCMD;
import net.lonia.core.commands.player.utils.SiteCMD;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;

import java.lang.reflect.Field;
import java.util.Map;

public class CommandsManager {

    public CommandsManager() {

        removeCommand(((CraftServer) Bukkit.getServer()).getCommandMap(), "pl");
        removeCommand(((CraftServer) Bukkit.getServer()).getCommandMap(), "plugins");
        removeCommand(((CraftServer) Bukkit.getServer()).getCommandMap(), "me");

        registerCommand(new SimpleCommand("stop", ".", new StopCMD(), new StopCMD()));

        registerCommand(new SimpleCommand("hub", ".", new HubCMD(), new HubCMD()));
        registerCommand(new SimpleCommand("lobby", ".", new LobbyCMD(), new LobbyCMD()));

        registerCommand(new SimpleCommand("spawn", ".", new SpawnCMD(), new SpawnCMD()));
        registerCommand(new SimpleCommand("site", ".", new SiteCMD(), new SiteCMD()));
        registerCommand(new SimpleCommand("discord", ".", new DiscordCMD(), new DiscordCMD()));
        registerCommand(new SimpleCommand("help", ".", new HelpCMD(), new HelpCMD()));
        registerCommand(new SimpleCommand("plugins", ".", new PluginCMD(), new PluginCMD(), "pl"));

        registerCommand(new SimpleCommand("ignore", ".", new IgnoreCMD(), new IgnoreCMD(), "ign"));
        registerCommand(new SimpleCommand("msg", ".", new MPCMD(), new MPCMD(), "m", "mp"));
        registerCommand(new SimpleCommand("r", ".", new RCMD(), new RCMD()));
    }

    public void registerCommand(SimpleCommand command) {
        CraftServer server = (CraftServer) Bukkit.getServer();
        server.getCommandMap().register(Bukkit.getName(), command);
    }

    public void removeCommand(SimpleCommandMap commandMap, String alias) {
        Command existingCommand = commandMap.getCommand(alias);
        if (existingCommand != null) {
            try {
                Field knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");
                knownCommandsField.setAccessible(true);
                Map<String, Command> knownCommands = (Map<String, Command>) knownCommandsField.get(commandMap);

                knownCommands.remove(alias);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
