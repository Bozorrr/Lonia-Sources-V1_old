package net.lonia.rush;

import net.lonia.core.LoniaCore;
import net.lonia.core.rank.Rank;
import net.lonia.core.tools.ScoreboardTeam;
import net.lonia.core.utils.LogManager;
import net.lonia.rush.commands.CommandsManager;
import net.lonia.rush.game.GameManager;
import net.lonia.rush.game.player.scoreboard.ScoreboardManager;
import net.lonia.rush.gui.GuiManager;
import net.lonia.rush.listeners.ListenersManager;
import net.lonia.rush.messages.MessageManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class LoniaRush extends JavaPlugin {

    public static LoniaRush INSTANCE;
    private final LogManager logManager = new LogManager("LoniaRush", "1.0.0");

    private GameManager gameManager;

    private ScheduledExecutorService scheduledExecutorService;
    private ScheduledExecutorService executorMonoThread;
    private ScoreboardManager scoreboardManager;
    private List<ScoreboardTeam> teams;

    private FileConfiguration gameConfig;
    private File gameConfigFile;

    private GuiManager guiManager;

    @Override
    public void onEnable() {
        INSTANCE = this;

        this.gameManager = new GameManager(this);

        scheduledExecutorService = Executors.newScheduledThreadPool(16);
        executorMonoThread = Executors.newScheduledThreadPool(1);
        scoreboardManager = new ScoreboardManager();
        teams = new ArrayList<>();

        LoniaCore.get().getRankManager().getRanks().forEach(rank -> this.teams.add(new ScoreboardTeam(rank.getPower(), rank.getPrefix())));

        this.guiManager = new GuiManager();
        this.guiManager.loadGui();

        new ListenersManager(this);
        new CommandsManager();

        MessageManager.loadMessages();

        logManager.onEnable();
    }

    @Override
    public void onDisable() {
        getScoreboardManager().onDisable();

        logManager.onDisable();
    }

    public GameManager getGameManager() {
        return gameManager;
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

    public GuiManager getGuiManager() {
        return this.guiManager;
    }
}
