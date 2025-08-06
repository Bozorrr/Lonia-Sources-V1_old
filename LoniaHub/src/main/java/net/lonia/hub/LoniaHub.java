package net.lonia.hub;

import net.lonia.core.LoniaCore;
import net.lonia.core.rank.Rank;
import net.lonia.core.tools.ScoreboardTeam;
import net.lonia.core.utils.FileManager;
import net.lonia.core.utils.LogManager;
import net.lonia.hub.listeners.EventsManager;
import net.lonia.hub.runable.RunnableManager;
import net.lonia.hub.scoreboard.ScoreboardManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class LoniaHub extends JavaPlugin {
    private static LoniaHub instance;
    private final LogManager logManager = new LogManager("LoniaHub", "1.0.0");
    private ScheduledExecutorService scheduledExecutorService;
    private ScheduledExecutorService executorMonoThread;
    private ScoreboardManager scoreboardManager;
    private List<ScoreboardTeam> teams;

    public static LoniaHub getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        this.scheduledExecutorService = Executors.newScheduledThreadPool(16);
        this.executorMonoThread = Executors.newScheduledThreadPool(1);

        this.teams = new ArrayList<>();

        this.scoreboardManager = new ScoreboardManager();
        LoniaCore.get().getRankManager().getRanks().forEach(rank -> this.teams.add(new ScoreboardTeam(rank.getPower(), rank.getPrefix())));

        new EventsManager();
        new RunnableManager();

        new FileManager().createFile("config");

        this.logManager.onEnable();
    }

    @Override
    public void onDisable() {
        getScoreboardManager().onDisable();
        this.logManager.onDisable();
    }

    public ScheduledExecutorService getExecutorMonoThread() {
        return this.executorMonoThread;
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return this.scheduledExecutorService;
    }

    public ScoreboardManager getScoreboardManager() {
        return this.scoreboardManager;
    }

    public List<ScoreboardTeam> getTeams() {
        return this.teams;
    }

    public Optional<ScoreboardTeam> getSbTeam(Rank rank) {
        return this.teams.stream().filter(t -> (t.getPower() == rank.getPower())).findFirst();
    }
}
