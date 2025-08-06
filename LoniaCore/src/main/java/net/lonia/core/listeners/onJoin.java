package net.lonia.core.listeners;

import net.lonia.core.LoniaCore;
import net.lonia.core.message.SanctionMessages;
import net.lonia.core.player.account.Account;
import net.lonia.core.player.sanction.SanctionManager;
import net.lonia.core.player.sanction.SanctionType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class onJoin implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void playerJoinEvent(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final UUID uuid = player.getUniqueId();

        event.setJoinMessage(null);

        SanctionManager.updateBan(uuid);

        if (SanctionManager.isSanctionActive(uuid, SanctionType.BAN)) {
            long remainingTimeMillis = SanctionManager.getBanRemainingTime(uuid);
            String formattedTime = SanctionManager.formatDuration(remainingTimeMillis);
            SanctionManager.kick(player.getDisplayName(), SanctionMessages.banned(uuid, formattedTime));
            LoniaCore.get().getServer().getOnlinePlayers().forEach(players -> {
                players.hidePlayer(player);
                player.hidePlayer(players);
            });
            return;
        }

        final Account account = new Account(player);
        account.onLogin();
        account.getUserData().setConnected(1);

        LoniaCore.connectedPlayer.add(player);
        LoniaCore.get().getServerAccount().getDataServer().setConnectedPlayerCount(LoniaCore.connectedPlayer.size());
    }
}
