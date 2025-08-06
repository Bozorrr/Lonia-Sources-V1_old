package net.lonia.bungee.listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.lonia.bungee.LoniaBungee;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.concurrent.TimeUnit;

public class PluginMessage implements Listener {

    @EventHandler
    public void onPluginMessage(PluginMessageEvent event) {
        if (!event.getTag().equals("BungeeCord")) return;

        final byte[] data = event.getData();
        final ByteArrayDataInput in = ByteStreams.newDataInput(data);

        final String subChannel = in.readUTF();

        if (!subChannel.equals("JOIN")) return;

        final ProxiedPlayer playerA = ProxyServer.getInstance().getPlayer(in.readUTF());
        final ProxiedPlayer playerB = ProxyServer.getInstance().getPlayer(in.readUTF());

        final ServerInfo info = playerB.getServer().getInfo();

        playerA.connect(info, (success, error) -> {
            if (success) {
                ProxyServer.getInstance().getScheduler().schedule(LoniaBungee.getInstance(), () -> {
                    ByteArrayDataOutput out = ByteStreams.newDataOutput();
                    out.writeUTF("TELEPORT");
                    out.writeUTF(playerA.getName());
                    out.writeUTF(playerB.getName());

                    info.sendData("BungeeCord", out.toByteArray());
                }, 1, TimeUnit.SECONDS);
            } else {
                playerA.sendMessage(new TextComponent("Erreur lors de la connexion au serveur de " + playerB.getName() + "."));
            }
        });
    }
}
