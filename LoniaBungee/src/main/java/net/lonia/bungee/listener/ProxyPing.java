package net.lonia.bungee.listener;

import net.md_5.bungee.api.Favicon;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class ProxyPing implements Listener {

    @EventHandler
    public void onPing(ProxyPingEvent event) {

        ServerPing serverPing = event.getResponse();

        serverPing.setDescription("            §b§lLONIA §f● §7[1.8.8+] §f● §98 Mini-jeux et plus !\n  §aFreecube§f, §bArènes PVP§f, §eNexus 2v2§f, §cRush 2v2§f, §2Jump§f, ...");

        serverPing.setPlayers(new ServerPing.Players(1000, ProxyServer.getInstance().getOnlineCount(), serverPing.getPlayers().getSample()));

        serverPing.setVersion(new ServerPing.Protocol("§c1.8.8", serverPing.getVersion().getProtocol()));

        try {

            serverPing.setFavicon(Favicon.create(ImageIO.read(new File("favicon.png"))));

        } catch (IOException e) {

            e.printStackTrace();

        }
        event.setResponse(serverPing);
    }
}
