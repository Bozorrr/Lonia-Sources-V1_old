package net.lonia.core.listeners;

import net.lonia.core.LoniaCore;
import net.lonia.core.player.sanction.SanctionManager;
import net.lonia.core.player.sanction.SanctionType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class onQuit implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerQuitEvent(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final UUID uuid = player.getUniqueId();

        if (SanctionManager.isSanctionActive(uuid, SanctionType.BAN)) {
            LoniaCore.get().getServer().getOnlinePlayers().forEach(players -> {
                players.showPlayer(player);
                player.showPlayer(players);
            });
            return;
        }

        event.setQuitMessage(null);

        LoniaCore.connectedPlayer.remove(player);
        LoniaCore.get().getServerAccount().getDataServer().setConnectedPlayerCount(LoniaCore.connectedPlayer.size());

        LoniaCore.get().getAccountManager().getAccount(player).onLogout();
    }
}
