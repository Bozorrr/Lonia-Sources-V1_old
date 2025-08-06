package net.lonia.bungee.commands;

import net.lonia.bungee.commands.player.FriendCMD;
import net.lonia.bungee.commands.player.GroupCMD;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class CommandMaganer {

    public CommandMaganer(Plugin plugin) {
        PluginManager pm = plugin.getProxy().getPluginManager();

        pm.registerCommand(plugin, new FriendCMD());
        pm.registerCommand(plugin, new GroupCMD());
    }
}
