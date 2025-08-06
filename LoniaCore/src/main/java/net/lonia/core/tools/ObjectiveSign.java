package net.lonia.core.tools;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ObjectiveSign extends VObjective {

    public HashMap<Integer, String> lines;


    public ObjectiveSign(String name, String displayName) {

        super(name, displayName);

        this.lines = new HashMap<>();

        for (int i = 0; i < 19; i++) {
            this.lines.put(Integer.valueOf(i), null);
        }
    }


    public boolean addReceiver(OfflinePlayer offlinePlayer) {

        if (!offlinePlayer.isOnline()) {
            return false;
        }
        this.receivers.add(offlinePlayer);

        Player p = offlinePlayer.getPlayer();

        init(p);
        updateScore(p, true);

        return true;
    }


    public void setLine(int nb, String line) {

        VScore remove = getScore(this.lines.get(Integer.valueOf(nb)));
        this.scores.remove(remove);

        VScore add = getScore(line);
        add.setScore(nb);

        this.lines.put(Integer.valueOf(nb), line);
    }


    public void updateLines() {

        updateLines(true);
    }


    public void updateLines(boolean inverse) {

        String old = toggleName();

        this.receivers.stream().filter(OfflinePlayer::isOnline).forEach(op -> {
            create(op.getPlayer());
            updateScore(op.getPlayer(), inverse);
            displayTo(op.getPlayer(), this.location.getLocation());
            RawObjective.removeObjective(op.getPlayer(), old);
        });
    }


    private void replaceScore(VScore remove, VScore add) {

        this.scores.remove(remove);

        this.receivers.stream().filter(OfflinePlayer::isOnline).forEach(op -> {
            RawObjective.updateScoreObjective(op.getPlayer(), this, add);
            RawObjective.removeScoreObjective(op.getPlayer(), this, remove);
        });
    }
}
