package net.lonia.core.commands;

import net.lonia.core.LoniaCore;
import net.lonia.core.player.account.Account;
import net.lonia.core.player.account.AccountManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public abstract class AbstractCommand implements CommandExecutor, TabCompleter {

    public final LoniaCore loniaCore = LoniaCore.get();
    public final AccountManager accountManager = loniaCore.getAccountManager();

    public Player player;
    public Account account;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return onCommand(sender, label, args);
    }

    protected abstract boolean onCommand(CommandSender sender, String label, String[] args);

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return onTabComplete(sender, label, args);
    }

    protected abstract List<String> onTabComplete(CommandSender sender, String label, String[] args);

    public Player getPlayer(UUID uuid) {
        return Bukkit.getPlayer(uuid);
    }

    public Player getPlayer(String name) {
        return Bukkit.getPlayer(name);
    }
}
