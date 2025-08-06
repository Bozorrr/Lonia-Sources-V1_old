package net.lonia.rush.game;

import net.lonia.core.tools.ScoreboardTeam;
import net.lonia.core.tools.Title;
import net.lonia.rush.LoniaRush;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameAutoStart extends BukkitRunnable {

    private final LoniaRush main;
    private final Game game;

    public int timer = 15;

    public GameAutoStart(LoniaRush main, Game game) {
        this.main = main;
        this.game = game;
    }


    @Override
    public void run() {
        switch (game.getPlayerInGame().size()) {
            case 1:
                game.setState(GameState.NOT_ENOUGH_PLAYER);
                game.getPlayerInGame().forEach(players -> Bukkit.getPlayer(players).sendMessage("§9[Rush] §3Début de partie annulée, il manque désormais §c1 joueur§3."));
                game.stopAutoStart();
                break;
            case 3:
                if (timer > 30) {
                    timer = 30;
                }
                break;
            case 4:
                timer = 5;
                break;
        }

        switch (timer) {
            case 15:
                sendChrono();
                break;
            case 5:
                sendChrono();
                sendTitle();
                break;
            case 4:
            case 3:
            case 2:
            case 1:
                sendTitle();
                break;
            case 0:
                game.setState(GameState.PLAYING);
                //Team.distributePlayers();

                game.getPlayerInGame().forEach(players -> {
                    Player p = Bukkit.getPlayer(players);
                    p.sendMessage("§9[Rush] §bDébut de la partie, bonne chance les fdp !");
                    p.getInventory().clear();
                    p.setGameMode(GameMode.SURVIVAL);
                    //Team.teleportToSpawn(p);
                    main.getScoreboardManager().onLogout(p);

                    for (ScoreboardTeam team : LoniaRush.INSTANCE.getTeams())
                        (((CraftPlayer) p).getHandle()).playerConnection.sendPacket(team.removeTeam());
                    main.getScoreboardManager().onLogin(p);
                });

                break;

            default:
                break;
        }
        timer--;

    }

    public Game getGame() {
        return game;
    }

    void sendChrono() {
        game.getPlayerInGame().forEach(players -> Bukkit.getPlayer(players).sendMessage(String.format("§9[Rush] §bLa partie va commencer dans §f%d secondes §b!", timer)));
    }

    void sendTitle() {
        game.getPlayerInGame().forEach(players -> new Title().sendTitle(Bukkit.getPlayer(players), 10, 20, 10, "§c" + timer));
    }
}
