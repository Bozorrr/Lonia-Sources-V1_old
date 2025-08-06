package net.lonia.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.List;

public class SimpleCommand extends Command {

    private final CommandExecutor executor;
    private final TabCompleter completer;

    public SimpleCommand(String name, String description, CommandExecutor executor, TabCompleter completer, String... aliases) {
        super(name, description, "", Arrays.asList(aliases));
        this.executor = executor;
        this.completer = completer;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        return this.executor.onCommand(sender, this, label, args);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
        if (this.completer != null) {
            return this.completer.onTabComplete(sender, this, alias, args);
        }
        return super.tabComplete(sender, alias, args);
    }
}