package net.lonia.bungee.utils;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;

public class Log {

    public static void log(String msg) {
        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(msg));
    }
}
