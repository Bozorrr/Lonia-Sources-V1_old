package net.lonia.bungee;

import net.lonia.bungee.commands.CommandMaganer;
import net.lonia.bungee.database.Database;
import net.lonia.bungee.listener.EventManager;
import net.lonia.bungee.player.account.Account;
import net.lonia.bungee.player.account.AccountManager;
import net.lonia.bungee.rank.RankManager;
import net.lonia.bungee.utils.LogManager;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class LoniaBungee extends Plugin implements Listener {

    private static LoniaBungee instance;

    private final LogManager logManager = new LogManager();

    private final HashMap<String, String> map = new HashMap<>();

    private Database database;
    private AccountManager accountManager;

    private RankManager rankManager;


    @Override
    public void onEnable() {
        instance = this;

        getProxy().registerChannel("BungeeCord");



        this.database = new Database();
        this.database.connect();
        this.database.createTables();

        this.accountManager = new AccountManager();

        this.rankManager = new RankManager();
        this.rankManager.onEnable();

        new EventManager(this);
        new CommandMaganer(this);

        this.logManager.onEnable();

        getProxy().getScheduler().schedule(this, () -> accountManager.getAccounts().forEach(Account::updateData), 2, 2, TimeUnit.SECONDS);
    }

    @Override
    public void onDisable() {
        this.rankManager.onDisable();

        this.logManager.onDisable();

        this.logManager.onDisable();
    }

    public static LoniaBungee getInstance() {
        return instance;
    }

    public Database getDataBaseManager() {
        return this.database;
    }

    public AccountManager getAccountManager() {
        return this.accountManager;
    }

    public RankManager getRankManager() {
        return this.rankManager;
    }

    public HashMap<String, String> getMap() {
        return this.map;
    }
}
