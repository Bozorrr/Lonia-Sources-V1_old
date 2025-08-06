package net.lonia.core.tools;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;

import java.lang.reflect.Field;
import java.util.List;

public class ScoreboardTeam {

    private int power;
    private String prefix;

    public ScoreboardTeam(int power, String prefix) {
        this.power = power;
        this.prefix = prefix;
    }


    public static PacketPlayOutPlayerInfo updateDisplayName(EntityPlayer player) {

        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_DISPLAY_NAME, player);
        player.playerConnection.sendPacket(packet);
        return packet;
    }


    private PacketPlayOutScoreboardTeam createPacket(int mode) {

        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();


        setField(packet, "a", Integer.toString(this.power));
        setField(packet, "h", Integer.valueOf(mode));
        setField(packet, "b", "");
        setField(packet, "c", this.prefix);
        setField(packet, "d", "");
        setField(packet, "i", Integer.valueOf(0));
        setField(packet, "e", "always");
        setField(packet, "f", Integer.valueOf(0));

        return packet;
    }


    public PacketPlayOutScoreboardTeam createTeam() {

        return createPacket(0);
    }


    public PacketPlayOutScoreboardTeam updateTeam() {

        return createPacket(2);
    }


    public PacketPlayOutScoreboardTeam removeTeam() {

        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();
        setField(packet, "a", Integer.toString(this.power));
        setField(packet, "h", Integer.valueOf(1));

        return packet;
    }


    public PacketPlayOutScoreboardTeam setFriendlyFire(boolean v) {

        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();
        setField(packet, "i", Integer.valueOf(v ? 1 : 0));

        return packet;
    }


    public PacketPlayOutScoreboardTeam addOrRemovePlayer(int mode, String playerName) {

        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();
        setField(packet, "a", Integer.toString(this.power));
        setField(packet, "h", Integer.valueOf(mode));

        try {
            Field f = packet.getClass().getDeclaredField("g");
            f.setAccessible(true);
            ((List<String>) f.get(packet)).add(playerName);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return packet;
    }


    private void setField(Object edit, String fieldName, Object value) {

        try {
            Field field = edit.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(edit, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public int getPower() { return power; }

    public String getPrefix() { return prefix; }
}