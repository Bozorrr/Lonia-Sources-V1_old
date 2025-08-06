package net.lonia.rush.game.player;

import net.lonia.core.rank.Rank;
import net.lonia.core.rank.Ranks;
import net.lonia.rush.game.GameTeam;
import org.bukkit.entity.Player;

public class PlayerData {
    private final String player;
    private GameTeam team;
    private Rank rank;
    private int kills;
    private int deaths;
    private int beds;

    public PlayerData(Player player) {
        this.player = player.getPlayerListName();
        this.team = GameTeam.RANDOM;
        this.rank = Ranks.RANDOM;
        this.kills = 0;
        this.deaths = 0;
        this.beds = 0;
    }

    public String getPlayer() {
        return player;
    }

    public GameTeam getTeam() { return this.team; }
    public Rank getRank() { return this.rank; }
    public int getKills() { return this.kills; }
    public int getDeaths() { return this.deaths; }
    public int getBeds() { return this.beds; }

    public void setTeam(GameTeam team) { this.team = team; }
    public void setRank(Rank rank) { this.rank = rank; }

    public void addKill() { this.kills++; }
    public void addDeath() { this.deaths++; }
    public void addBed() { this.beds++; }

}
