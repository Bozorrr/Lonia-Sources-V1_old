package net.lonia.moderation.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.lonia.moderation.LoniaModeration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class PluginMessage implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) return;

        final ByteArrayDataInput in = ByteStreams.newDataInput(message);
        final String subchannel = in.readUTF();

        if (subchannel.equals("TELEPORT")) {
            final String playerAName = in.readUTF();
            final String playerBName = in.readUTF();

            final Player playerA = Bukkit.getPlayer(playerAName);
            final Player playerB = Bukkit.getPlayer(playerBName);

            if (playerA == null || playerB == null) {
                if (playerA != null) {
                    playerA.sendMessage("Erreur : le joueur cible n'est pas disponible.");
                }
                return;
            }

            // Téléportation
            Bukkit.getScheduler().runTask(LoniaModeration.getInstance(), () -> {
                playerA.teleport(playerB.getLocation());
                playerA.sendMessage("§9[Mod.] §bVous avez été téléporté sur §f" + playerB.getDisplayName() + "§b.");
            });
        }

    }
}
