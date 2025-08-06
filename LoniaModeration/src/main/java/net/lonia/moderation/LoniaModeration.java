package net.lonia.moderation;

import net.lonia.core.player.account.Account;
import net.lonia.core.player.staff.StaffManager;
import net.lonia.core.tools.Title;
import net.lonia.core.utils.LogManager;
import net.lonia.moderation.commands.CommandsManager;
import net.lonia.moderation.gui.GuiManager;
import net.lonia.moderation.listeners.ListenersManager;
import net.lonia.moderation.listeners.ModListeners;
import net.lonia.moderation.listeners.PluginMessage;
import net.lonia.moderation.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class LoniaModeration extends JavaPlugin {
    private static LoniaModeration instance;
    private final LogManager logManager = new LogManager("LoniaModeration", "1.0.0");
    private final Set<UUID> moderators = new HashSet<>();
    private final Map<UUID, PlayerManager> players = new HashMap<>();
    private final Set<UUID> vanishPlayers = new HashSet<>();
    private GuiManager guiManager;

    public static LoniaModeration getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        this.guiManager = new GuiManager();
        this.guiManager.loadGui();

        getServer().getPluginManager().registerEvents(new ModListeners(), this);

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new PluginMessage());

        new ListenersManager(this);
        new CommandsManager();
        this.logManager.onEnable();

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            Bukkit.getOnlinePlayers().stream().filter(player -> StaffManager.isInMod(player.getUniqueId()) && Account.isConnected(player.getDisplayName())).forEach(player -> moderators.add(player.getUniqueId()));
            moderators.forEach(moderator -> new Title().sendActionBar(Bukkit.getPlayer(moderator), "§dModération"));
        }, 0, 30);

    }

    @Override
    public void onDisable() {
        this.logManager.onDisable();
    }

    public GuiManager getGuiManager() {
        return this.guiManager;
    }

    public Set<UUID> getModerators() {
        return this.moderators;
    }

    public Map<UUID, PlayerManager> getPlayers() {
        return this.players;
    }

    public Set<UUID> getVanishPlayers() {
        return this.vanishPlayers;
    }
}