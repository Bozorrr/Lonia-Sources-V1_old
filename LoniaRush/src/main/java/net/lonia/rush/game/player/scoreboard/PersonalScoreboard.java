package net.lonia.rush.game.player.scoreboard;

import net.lonia.core.LoniaCore;
import net.lonia.core.player.account.Account;
import net.lonia.core.tools.ObjectiveSign;
import net.lonia.core.tools.ScoreboardTeam;
import net.lonia.rush.LoniaRush;
import net.lonia.rush.game.Game;
import net.lonia.rush.game.GameManager;
import net.lonia.rush.game.GameState;
import net.lonia.rush.game.GameTeam;
import net.lonia.rush.game.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class PersonalScoreboard {

    private final UUID uuid;
    private final Player player;
    private final ObjectiveSign objectiveSign;

    private GameManager gameManager;
    private Game game;
    private PlayerData data;

    int kill = 0;
    int death = 0;
    int bed = 0;
    int o = 0;
    int v = 0;
    String oS = "";
    String oV = "";
    private String teamName;
    private int chronos;

    public PersonalScoreboard(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
        this.objectiveSign = new ObjectiveSign("sidebar", "Lonia");

        this.gameManager = LoniaRush.INSTANCE.getGameManager();
        this.game = gameManager.getGameByPlayer(player);
        this.data = game.getPlayerData(player);

        reloadData();
        this.objectiveSign.addReceiver(player);

        for (ScoreboardTeam team : LoniaRush.INSTANCE.getTeams())
            (((CraftPlayer) Bukkit.getPlayer(this.uuid)).getHandle()).playerConnection.sendPacket(team.createTeam());

        if (game.isState(GameState.NOT_ENOUGH_PLAYER) || game.isState(GameState.ENOUGH_PLAYER)) {
            for (String players1 : game.getPlayerInGame()) {
                for (String players2 : game.getPlayerInGame()) {
                    Account account = LoniaCore.get().getAccountManager().getAccount(Bukkit.getPlayer(players2));
                    Optional<ScoreboardTeam> team = LoniaRush.INSTANCE.getSbTeam(account.getUserData().getDataRank().getRank());
                    team.ifPresent(t -> (((CraftPlayer) Bukkit.getPlayer(players1)).getHandle()).playerConnection.sendPacket(t.addOrRemovePlayer(3, Bukkit.getPlayer(players2).getName())));
                }
            }
        }
        else if (game.isState(GameState.PLAYING) || game.isState(GameState.ENDING)) {

            final Set<String> totalPlayers = new HashSet<>();

            totalPlayers.addAll(game.getPlayerInGame());
            totalPlayers.addAll(game.getSpecPlayers());
            totalPlayers.addAll(game.getDeadPlayers());

            for (String players1 : totalPlayers) {
                for (String players2 : totalPlayers) {
                    PlayerData d = game.getPlayerData(Bukkit.getPlayer(players2));
                    Optional<ScoreboardTeam> team = LoniaRush.INSTANCE.getSbTeam(d.getRank());
                    team.ifPresent(t -> (((CraftPlayer) Bukkit.getPlayer(players1)).getHandle()).playerConnection.sendPacket(t.addOrRemovePlayer(3, Bukkit.getPlayer(players2).getName())));
                }
            }
        }
    }

    public void reloadData() {
        if (game.isState(GameState.NOT_ENOUGH_PLAYER)) {
            this.teamName = data.getTeam().getPrefix();
        }
        else if (game.isState(GameState.ENOUGH_PLAYER)) {
            this.teamName = data.getTeam().getPrefix();
            this.chronos = game.getAutoStartRunnable().timer;
        }
        else if (game.isState(GameState.PLAYING)) {
            if (game.getPlayerInGame().contains(player.getDisplayName())) {
                this.teamName = data.getTeam().getPrefix();
                this.kill = data.getKills();
                this.death = data.getDeaths();
                this.bed = data.getBeds();
            }

            o = game.getOrangeTeamPlayers().size();
            v = game.getVioletTeamPlayers().size();

            oS = game.hasOrangeBed() ? "§a§l✔" : "§c§l✖";
            oV = game.hasVioletBed() ? "§a§l✔" : "§c§l✖";
        }
    }

    public void setLines(String ip) {

        this.objectiveSign.setDisplayName("§9§lLonia");

        this.objectiveSign.setLine(0, "§a ");
        this.objectiveSign.setLine(1, "§b● §3Équipe: " + teamName);
        this.objectiveSign.setLine(2, "§b ");
        this.objectiveSign.setLine(3, "§b● §cAttente d'1 joueur");
        this.objectiveSign.setLine(4, "§c ");
        this.objectiveSign.setLine(5, "§b● §3Points: §f");
        this.objectiveSign.setLine(6, "§d ");
        this.objectiveSign.setLine(7, "§b● §3Map: §fClassique");
        this.objectiveSign.setLine(8, "§b● §3Jeu: §cRush 2v2");
        this.objectiveSign.setLine(9, "§e");

        this.objectiveSign.updateLines();


        if (game.isState(GameState.NOT_ENOUGH_PLAYER))
            notEnoughPlayer();
        else if (game.isState(GameState.ENOUGH_PLAYER))
            enoughPlayer();
        else if (game.isState(GameState.PLAYING)) {
            if (game.getPlayerInGame().contains(player.getDisplayName())) inGame();
            else spec();
        }
    }

    void notEnoughPlayer() {
        this.objectiveSign.setDisplayName("§9§lLonia");

        this.objectiveSign.setLine(0, "§a ");
        this.objectiveSign.setLine(1, "§b● §3Équipe: " + teamName);
        this.objectiveSign.setLine(2, "§b ");
        this.objectiveSign.setLine(3, "§b● §cAttente d'1 joueur");
        this.objectiveSign.setLine(4, "§c ");
        this.objectiveSign.setLine(5, "§b● §3Points: §f");
        this.objectiveSign.setLine(6, "§d ");
        this.objectiveSign.setLine(7, "§b● §3Map: §fClassique");
        this.objectiveSign.setLine(8, "§b● §3Jeu: §cRush 2v2");
        this.objectiveSign.setLine(9, "§e");

        this.objectiveSign.updateLines();
    }

    void enoughPlayer() {
        this.objectiveSign.setDisplayName("§9§lLonia");

        this.objectiveSign.setLine(0, "§a");
        this.objectiveSign.setLine(1, "§b● §3Équipe: " + teamName);
        this.objectiveSign.setLine(2, "§b ");
        this.objectiveSign.setLine(3, "§b● §aDébut dans: §f" + chronos + "s");
        this.objectiveSign.setLine(4, "§c ");
        this.objectiveSign.setLine(5, "§b● §3Points: §f");
        this.objectiveSign.setLine(6, "§d ");
        this.objectiveSign.setLine(7, "§b● §3Map: §fClassique");
        this.objectiveSign.setLine(8, "§b● §3Jeu: §cRush 2v2");
        this.objectiveSign.setLine(9, "§e");

        this.objectiveSign.updateLines();
    }

    void inGame() {
        this.objectiveSign.setDisplayName("§9§lLonia");

        this.objectiveSign.setLine(0, "§a ");
        this.objectiveSign.setLine(1, "§b● §3Équipe: " + teamName);
        this.objectiveSign.setLine(2, "§b● §3Chrono: §fs");
        this.objectiveSign.setLine(3, "§b ");
        this.objectiveSign.setLine(4, "§b● §6O: " + oS + " §7- §f" + o + "/2");
        this.objectiveSign.setLine(5, "§b● §5V: " + oV + " §7- §f" + v + "/2");
        this.objectiveSign.setLine(6, "§c ");
        this.objectiveSign.setLine(7, "§b● §3Kills: §f" + kill);
        this.objectiveSign.setLine(8, "§b● §3Morts: §f" + death);
        this.objectiveSign.setLine(9, "§b● §3Lits détruits: §f" + bed);
        this.objectiveSign.setLine(10, "§d");

        this.objectiveSign.updateLines();
    }

    void spec() {
        this.objectiveSign.setDisplayName("§9§lLonia");

        this.objectiveSign.setLine(0, "§a");
        this.objectiveSign.setLine(1, "§b● §3Mode: §fSpectateur");
        this.objectiveSign.setLine(2, "§b● §3Chrono: §fs");
        this.objectiveSign.setLine(3, "§b");
        this.objectiveSign.setLine(4, "§b● §6O: " + oS + " §7- §f" + o + "/2");
        this.objectiveSign.setLine(5, "§b● §5V: " + oV + " §7- §f" + v + "/2");
        this.objectiveSign.setLine(10, "§c");

        this.objectiveSign.updateLines();
    }

    public void onLogout() {
        this.objectiveSign.removeReceiver(Bukkit.getServer().getOfflinePlayer(this.uuid));
    }
}

