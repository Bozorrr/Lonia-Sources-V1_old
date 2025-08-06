package net.lonia.bungee.rank;

import java.util.ArrayList;
import java.util.List;

public class RankManager {
    private final List<Rank> ranks = new ArrayList<>();

    public void onEnable() {
        new Ranks().createRanks();
    }

    public void onDisable() {
        new Ranks().removeRanks();
    }

    public List<Rank> getRanks() {
        return this.ranks;
    }
}
