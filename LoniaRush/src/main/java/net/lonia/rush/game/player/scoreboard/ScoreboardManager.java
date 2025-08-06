package net.lonia.rush.game.player.scoreboard;

import net.lonia.rush.LoniaRush;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScoreboardManager {

    private final Map<UUID, PersonalScoreboard> scoreboards;
    private final ScheduledFuture glowingTask;
    private final ScheduledFuture reloadingTask;
    private int ipCharIndex;
    private int cooldown;

    public ScoreboardManager() {

        scoreboards = new HashMap<>();
        ipCharIndex = 0;
        cooldown = 0;

        glowingTask = LoniaRush.INSTANCE.getScheduledExecutorService().scheduleAtFixedRate(() -> {
            String ip = colorIpAt();
            for (PersonalScoreboard scoreboard : scoreboards.values())
                LoniaRush.INSTANCE.getExecutorMonoThread().execute(() -> scoreboard.setLines(ip));
        }, 80, 80, TimeUnit.MILLISECONDS);

        reloadingTask = LoniaRush.INSTANCE.getScheduledExecutorService().scheduleAtFixedRate(() -> {
            for (PersonalScoreboard scoreboard : scoreboards.values())
                LoniaRush.INSTANCE.getExecutorMonoThread().execute(scoreboard::reloadData);
        }, 500, 500, TimeUnit.MILLISECONDS);
    }

    public void onDisable() {
        if (scoreboards.isEmpty()) return;
        scoreboards.values().forEach(PersonalScoreboard::onLogout);
    }

    public void onLogin(Player player) {

        if (scoreboards.containsKey(player.getUniqueId())) {
            return;
        }
        scoreboards.put(player.getUniqueId(), new PersonalScoreboard(player));
    }

    public void onLogout(Player player) {

        if (scoreboards.containsKey(player.getUniqueId())) {
            scoreboards.get(player.getUniqueId()).onLogout();
            scoreboards.remove(player.getUniqueId());
        }
    }

    public void update(Player player) {

        if (scoreboards.containsKey(player.getUniqueId())) {
            scoreboards.get(player.getUniqueId()).reloadData();
        }
    }

    private String colorIpAt() {

        String ip = "play.lonia.fr";

        if (cooldown > 0) {
            cooldown--;
            return ChatColor.YELLOW + ip;
        }

        StringBuilder formattedIp = new StringBuilder();

        if (ipCharIndex > 0) {
            formattedIp.append(ip, 0, ipCharIndex - 1);
            formattedIp.append(ChatColor.GOLD).append(ip.charAt(ipCharIndex - 1));
        } else {
            formattedIp.append(ip, 0, ipCharIndex);
        }

        formattedIp.append(ChatColor.RED).append(ip.charAt(ipCharIndex));

        if (ipCharIndex + 1 < ip.length()) {
            formattedIp.append(ChatColor.GOLD).append(ip.charAt(ipCharIndex + 1));

            if (ipCharIndex + 2 < ip.length())
                formattedIp.append(ChatColor.YELLOW).append(ip.substring(ipCharIndex + 2));

            ipCharIndex++;
        } else {
            ipCharIndex = 0;
            cooldown = 50;
        }

        return ChatColor.YELLOW + formattedIp.toString();
    }

}