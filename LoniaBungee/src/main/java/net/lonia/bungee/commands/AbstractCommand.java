package net.lonia.bungee.commands;

import net.lonia.bungee.LoniaBungee;
import net.lonia.bungee.message.MessageList;
import net.lonia.bungee.player.account.Account;
import net.lonia.bungee.player.account.AccountManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.List;
import java.util.UUID;

public abstract class AbstractCommand extends Command implements TabExecutor {

    public final LoniaBungee loniaBungee = LoniaBungee.getInstance();
    public final AccountManager accountManager = loniaBungee.getAccountManager();

    public ProxiedPlayer player;
    public Account account;

    public AbstractCommand(String name) {
        super(name);
    }

    public AbstractCommand(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new TextComponent(MessageList.commandNotAccessibleHere()));
            return;
        }
        onCommand(sender, getName(), args);
    }

    protected abstract void onCommand(CommandSender sender, String label, String[] args);

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return onTabComplete(sender, getName(), args);
    }

    protected abstract List<String> onTabComplete(CommandSender sender, String label, String[] args);

    public ProxiedPlayer getPlayer(UUID uuid) {
        return loniaBungee.getProxy().getPlayer(uuid);
    }

    public ProxiedPlayer getPlayer(String name) {
        return loniaBungee.getProxy().getPlayer(name);
    }

    public void sendMessage(ProxiedPlayer player, String message) {
        player.sendMessage(new TextComponent(message));
    }
}
