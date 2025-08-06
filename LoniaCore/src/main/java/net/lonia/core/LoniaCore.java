package net.lonia.core;

import net.lonia.core.commands.CommandsManager;
import net.lonia.core.database.Database;
import net.lonia.core.listeners.ListenersManager;
import net.lonia.core.player.account.AccountManager;
import net.lonia.core.rank.RankManager;
import net.lonia.core.runable.RunnableManager;
import net.lonia.core.server.ServerManager;
import net.lonia.core.server.account.ServerAccount;
import net.lonia.core.server.status.ServerStatus;
import net.lonia.core.utils.LogManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoniaCore extends JavaPlugin {

    private final HashMap<String, String> map = new HashMap<>();

    public static List<Player> connectedPlayer = new ArrayList<>();

    private static LoniaCore instance;

    private Database database;
    private AccountManager accountManager;

    private ServerAccount serverAccount;

    private RankManager rankManager;
    private final LogManager logManager = new LogManager("LoniaCore", "1.0.0");

    @Override
    public void onEnable() {
        instance = this;

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        this.database = new Database();
        this.database.connect();

        this.accountManager = new AccountManager();

        this.rankManager = new RankManager();
        this.rankManager.onEnable();

        final File file = new File("D:\\Lonia\\data", "messages.yml");

        if (!file.exists()) try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.serverAccount = new ServerAccount(getServer());
        this.serverAccount.onLogin();
        this.serverAccount.getDataServer().setServerStatus(ServerStatus.ONLINE);

        new ServerManager();

        new ListenersManager(this);
        new CommandsManager();
        new RunnableManager();

        this.logManager.onEnable();
    }

    @Override
    public void onDisable() {
        this.rankManager.onDisable();

        this.serverAccount.getDataServer().setConnectedPlayerCount(0);
        this.serverAccount.getDataServer().setServerStatus(ServerStatus.OFFLINE);
        this.serverAccount.onLogout();

        this.logManager.onDisable();
    }

    public static LoniaCore get() {
        return instance;
    }

    public Database getDataBaseManager() {
        return this.database;
    }

    public AccountManager getAccountManager() {
        return this.accountManager;
    }

    public ServerAccount getServerAccount() {
        return this.serverAccount;
    }

    public RankManager getRankManager() {
        return this.rankManager;
    }

    public HashMap<String, String> getMap() {
        return map;
    }
}
