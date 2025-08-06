package net.lonia.hub.scoreboard;

import net.lonia.core.LoniaCore;
import net.lonia.core.player.account.Account;
import net.lonia.core.server.ServerManager;
import net.lonia.core.tools.ObjectiveSign;
import net.lonia.core.tools.ScoreboardTeam;
import net.lonia.hub.LoniaHub;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public class PersonalScoreboard {

    private final UUID uuid;
    private final ObjectiveSign objectiveSign;
    private final Player player;
    private final Account account;
    private int playerCount;
    private String pseudo;
    private String grade;
    private long etoile;
    private long argent;

    public PersonalScoreboard(Player player) {

        this.player = player;
        this.uuid = player.getUniqueId();
        this.objectiveSign = new ObjectiveSign("sidebar", "Lonia");
        this.account = LoniaCore.get().getAccountManager().getAccount(player);

        reloadData();
        this.objectiveSign.addReceiver(player);

        for (ScoreboardTeam team : LoniaHub.getInstance().getTeams())
            (((CraftPlayer) Bukkit.getPlayer(this.uuid)).getHandle()).playerConnection.sendPacket(team.createTeam());

        for (Player players1 : Bukkit.getOnlinePlayers()) {
            for (Player players2 : Bukkit.getOnlinePlayers()) {
                Account account = LoniaCore.get().getAccountManager().getAccount(players2);
                Optional<ScoreboardTeam> team = LoniaHub.getInstance().getSbTeam(account.getUserData().getDataRank().getRank());
                team.ifPresent(t -> (((CraftPlayer) players1).getHandle()).playerConnection.sendPacket(t.addOrRemovePlayer(3, players2.getName())));
            }
        }
    }

    public void reloadData() {
        this.playerCount = ServerManager.getTotalPlayer();

        this.pseudo = account.getUserData().getDataRank().getRank().getColor() + this.player.getDisplayName();
        this.grade = account.getUserData().getDataRank().getRank().getPrefix();
        this.etoile = account.getUserData().getDataEtoile().getEtoile();
        this.argent = account.getUserData().getDataPearl().getPearl();
    }


    public void setLines(String ip) {
        this.objectiveSign.setDisplayName("§9§lLonia");

        this.objectiveSign.setLine(0, "§1");
        this.objectiveSign.setLine(1, "§b● §3Connectés: §f" + this.playerCount + "§f");
        this.objectiveSign.setLine(2, "§b● §3Niveau global: §f8");
        this.objectiveSign.setLine(3, "§2");
        this.objectiveSign.setLine(4, "§b● §3Pseudo: " + this.pseudo);
        this.objectiveSign.setLine(5, "§b● §3Grade: " + this.grade);
        this.objectiveSign.setLine(6, "§3");
        this.objectiveSign.setLine(7, "§b● §3Étoiles: §e" + this.etoile + "§e§l⭐");
        this.objectiveSign.setLine(8, "§b● §3Pearls: §d" + this.argent + "§d§l⨀");
        this.objectiveSign.setLine(9, "§4");
        this.objectiveSign.setLine(10, "§b● §3Lobby:§b #1");
        this.objectiveSign.setLine(11, "§5");
        this.objectiveSign.updateLines();
    }


    public void onLogout() {
        this.objectiveSign.removeReceiver(Bukkit.getServer().getOfflinePlayer(this.uuid));
    }
}

