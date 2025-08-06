package net.lonia.core.tools;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class HeaderFooter {

    private final String title;
    private final String subTitle;

    public HeaderFooter(String title, String subTitle) {
        this.title = title;
        this.subTitle = subTitle;
    }

    public void send(Player p) {
        final IChatBaseComponent tabTitle = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + this.title + "\"}");
        final IChatBaseComponent tabSubTitle = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + this.subTitle + "\"}");
        final PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(tabTitle);

        try {
            final Field field = packet.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(packet, tabSubTitle);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            (((CraftPlayer) p).getHandle()).playerConnection.sendPacket(packet);
        }
    }
}
