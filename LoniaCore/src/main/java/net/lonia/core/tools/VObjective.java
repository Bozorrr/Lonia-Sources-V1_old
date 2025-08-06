package net.lonia.core.tools;

import net.minecraft.server.v1_8_R3.IScoreboardCriteria;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardObjective;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardScore;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class VObjective {

    protected String name;
    protected IScoreboardCriteria.EnumScoreboardHealthDisplay format = IScoreboardCriteria.EnumScoreboardHealthDisplay.INTEGER;
    protected String displayName;
    protected ObjectiveLocation location = ObjectiveLocation.SIDEBAR;


    protected List<OfflinePlayer> receivers;


    protected ConcurrentLinkedQueue<VScore> scores;


    public VObjective(String name, String displayName) {

        this.receivers = new ArrayList<>();
        this.scores = new ConcurrentLinkedQueue<>();
        this.name = name;
        this.displayName = displayName;
    }


    protected String toggleName() {

        String old = this.name;

        if (this.name.endsWith("1")) {
            this.name = this.name.substring(0, this.name.length() - 1);
        } else {
            this.name += "1";
        }
        return old;
    }


    public boolean addReceiver(OfflinePlayer offlinePlayer) {

        if (!offlinePlayer.isOnline()) {
            return false;
        }
        this.receivers.add(offlinePlayer);

        Player p = offlinePlayer.getPlayer();

        init(p);
        updateScore(p);

        return true;
    }


    public void removeReceiver(OfflinePlayer offlinePlayer) {

        this.receivers.remove(offlinePlayer);

        if (offlinePlayer.isOnline()) {
            remove(offlinePlayer.getPlayer());
        }
    }


    public void init(Player receiver) {

        create(receiver);
        displayTo(receiver, this.location.getLocation());
    }


    protected void create(Player receiver) {

        RawObjective.createObjective(receiver, this);
    }


    protected void displayTo(Player receiver, int location) {

        RawObjective.displayObjective(receiver, getName(), location);
    }


    protected void remove(Player receiver) {

        RawObjective.removeObjective(receiver, this);
    }


    public void updateScore(String score) {

        updateScore(getScore(score));
    }


    protected void updateScore(VScore score) {

        this.receivers.stream().filter(OfflinePlayer::isOnline).forEach(op -> RawObjective.updateScoreObjective(op.getPlayer(), this, score));
    }


    public ObjectiveLocation getLocation() {

        return this.location;
    }


    public void setLocation(ObjectiveLocation location) {

        this.location = location;
    }


    protected void updateScore(Player p) {

        RawObjective.updateScoreObjective(p, this, false);
    }


    protected void updateScore(Player p, boolean inverse) {

        RawObjective.updateScoreObjective(p, this, inverse);
    }


    public void updateScore(boolean forceRefresh) {

        if (forceRefresh) {
            String old = toggleName();

            this.receivers.stream().filter(OfflinePlayer::isOnline).forEach(op -> {
                create(op.getPlayer());

                RawObjective.updateScoreObjective(op.getPlayer(), this, false);

                displayTo(op.getPlayer(), this.location.getLocation());
                RawObjective.removeObjective(op.getPlayer(), old);
            });
        } else {
            this.receivers.stream().filter(OfflinePlayer::isOnline).forEach(op -> RawObjective.updateScoreObjective(op.getPlayer(), this, false));
        }
    }


    protected void update() {

        this.receivers.stream().filter(OfflinePlayer::isOnline).forEach(op -> RawObjective.updateObjective(op.getPlayer(), this));
    }


    public void removeScore(String score) {

        VScore score1 = getScore(score);
        removeScore(score1);
    }


    public void clearScores() {

        this.scores.clear();
    }


    public void removeScore(VScore score) {

        this.scores.remove(score);
        this.receivers.stream().filter(OfflinePlayer::isOnline).forEach(op -> RawObjective.removeScoreObjective(op.getPlayer(), this, score));
    }


    public VScore getScore(String player) {

        for (VScore vScore : this.scores) {
            if (vScore.getPlayerName().equals(player))
                return vScore;
        }
        VScore score = new VScore(player, 0);
        this.scores.add(score);

        return score;
    }


    public ConcurrentLinkedQueue<VScore> getScores() {

        return this.scores;
    }


    public boolean containsScore(String player) {

        for (VScore score : this.scores) {
            if (score.getPlayerName().equals(player))
                return true;
        }
        return false;
    }


    public String getName() {

        return this.name;
    }


    public String getDisplayName() {

        return this.displayName;
    }


    public void setDisplayName(String displayName) {

        this.displayName = displayName;
    }


    public Object getFormat() {

        return this.format;
    }

    public enum ObjectiveLocation {
        LIST(0),
        SIDEBAR(1),
        BELOWNAME(2);

        private final int location;


        ObjectiveLocation(int location) {

            this.location = location;
        }


        public int getLocation() {

            return this.location;
        }
    }


    public static class RawObjective {

        public static void createObjective(Player p, VObjective objective) {

            Reflection.sendPacket(p, makeScoreboardObjectivePacket(0, objective.getName(), objective.getDisplayName(), objective.getFormat()));
        }


        public static void updateObjective(Player p, VObjective objective) {

            Reflection.sendPacket(p, makeScoreboardObjectivePacket(2, objective.getName(), objective.getDisplayName(), objective.getFormat()));
        }


        public static void removeObjective(Player p, VObjective objective) {

            Reflection.sendPacket(p, makeScoreboardObjectivePacket(1, objective.getName(), objective.getDisplayName(), objective.getFormat()));
        }


        public static void removeObjective(Player p, String name) {

            Reflection.sendPacket(p, makeScoreboardObjectivePacket(1, name, "", IScoreboardCriteria.EnumScoreboardHealthDisplay.INTEGER));
        }


        public static void displayObjective(Player p, String name, int location) {

            Reflection.sendPacket(p, makeScoreboardDisplayPacket(name, location));
        }


        public static void createScoreObjective(Player p, VObjective objective) {

            updateScoreObjective(p, objective, false);
        }


        public static void createScoreObjective(Player p, VObjective objective, VScore score) {

            updateScoreObjective(p, objective, score);
        }


        public static void updateScoreObjective(Player p, VObjective objective, boolean inverse) {

            if (!inverse) {
                for (VScore score : objective.getScores()) {
                    updateScoreObjective(p, objective, score);
                }

                return;
            }
            for (VScore score : objective.getScores()) {
                updateScoreObjective(p, objective, score, objective.getScores().size() - score.getScore() - 1);
            }
        }

        public static void updateScoreObjective(Player p, VObjective objective, VScore score) {

            Reflection.sendPacket(p, makeScoreboardScorePacket(objective.getName(), PacketPlayOutScoreboardScore.EnumScoreboardAction.CHANGE, score.getPlayerName(), score.getScore()));
        }


        public static void updateScoreObjective(Player p, VObjective objective, VScore score, int scoreValue) {

            Reflection.sendPacket(p, makeScoreboardScorePacket(objective.getName(), PacketPlayOutScoreboardScore.EnumScoreboardAction.CHANGE, score.getPlayerName(), scoreValue));
        }


        public static void removeScoreObjective(Player p, VObjective objective) {

            for (VScore score : objective.getScores()) {
                removeScoreObjective(p, objective, score);
            }
        }

        public static void removeScoreObjective(Player p, VObjective objective, VScore score) {

            Reflection.sendPacket(p, makeScoreboardScorePacket(objective.getName(), PacketPlayOutScoreboardScore.EnumScoreboardAction.REMOVE, score.getPlayerName(), 0));
        }


        public static PacketPlayOutScoreboardScore makeScoreboardScorePacket(String objectiveName, Object action, String scoreName, int scoreValue) {

            if (objectiveName == null) {
                objectiveName = "";
            }
            try {
                PacketPlayOutScoreboardScore packet = new PacketPlayOutScoreboardScore();

                Reflection.setValue(packet, "a", scoreName);
                Reflection.setValue(packet, "b", objectiveName);
                Reflection.setValue(packet, "c", Integer.valueOf(scoreValue));
                Reflection.setValue(packet, "d", action);

                return packet;
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();


                return null;
            }
        }

        public static PacketPlayOutScoreboardObjective makeScoreboardObjectivePacket(int action, String objectiveName, String objectiveDisplayName, Object format) {

            try {
                PacketPlayOutScoreboardObjective packet = new PacketPlayOutScoreboardObjective();

                Reflection.setValue(packet, "a", objectiveName);
                Reflection.setValue(packet, "b", objectiveDisplayName);
                Reflection.setValue(packet, "c", format);
                Reflection.setValue(packet, "d", Integer.valueOf(action));

                return packet;
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();


                return null;
            }
        }

        public static PacketPlayOutScoreboardDisplayObjective makeScoreboardDisplayPacket(String objectiveName, int location) {

            try {
                PacketPlayOutScoreboardDisplayObjective packet = new PacketPlayOutScoreboardDisplayObjective();
                Reflection.setValue(packet, "a", Integer.valueOf(location));
                Reflection.setValue(packet, "b", objectiveName);

                return packet;
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();


                return null;
            }
        }
    }

    public class VScore {

        private final String playerName;
        private int score;

        public VScore(String player, int score) {

            this.playerName = player;
            this.score = score;
        }


        public int getScore() {

            return this.score;
        }


        public void setScore(int score) {

            this.score = score;
        }


        public void removeScore(int score) {

            setScore(getScore() - score);
        }


        public void incrementScore() {

            setScore(getScore() + 1);
        }


        public void addScore(int score) {

            setScore(getScore() + score);
        }


        public String getPlayerName() {

            return this.playerName;
        }
    }
}