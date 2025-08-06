package net.lonia.rush.commands;

import net.lonia.core.commands.SimpleCommand;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;

public class CommandsManager {

    public CommandsManager() {
        registerCommand(new SimpleCommand("rushsetup", ".", new RushSetupCMD(), new RushSetupCMD(), "rsetup"));
        registerCommand(new SimpleCommand("but", ".", new ButCMD(), new ButCMD()));
        registerCommand(new SimpleCommand("gameinfo", ".", new GameInfoCMD(), new GameInfoCMD()));
    }

    private void registerCommand(SimpleCommand command) {
        CraftServer server = (CraftServer) Bukkit.getServer();
        server.getCommandMap().register(Bukkit.getName(), command);
    }
}

