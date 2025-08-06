package net.lonia.core.listeners;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.lonia.core.LoniaCore;
import net.lonia.core.message.ChatMessages;
import net.lonia.core.message.MessageList;
import net.lonia.core.message.SanctionMessages;
import net.lonia.core.player.account.Account;
import net.lonia.core.player.account.DGroup;
import net.lonia.core.player.sanction.SanctionManager;
import net.lonia.core.player.sanction.SanctionType;
import net.lonia.core.rank.PermissionLevel;
import net.lonia.core.rank.Rank;
import net.lonia.core.rank.Ranks;
import net.lonia.core.utils.Log;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class onChat implements Listener {

    private final List<Player> antiFlood = new ArrayList<>();

    @EventHandler(priority = EventPriority.LOWEST)
    public void asyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        final UUID uuid = player.getUniqueId();

        SanctionManager.updateMute(uuid);
        if (SanctionManager.isSanctionActive(uuid, SanctionType.MUTE)) {
            long remainingTimeMillis = SanctionManager.getMuteRemainingTime(uuid);
            String formattedTime = SanctionManager.formatDuration(remainingTimeMillis);
            player.sendMessage(SanctionMessages.muted(formattedTime));
            event.setCancelled(true);
            return;
        }

        final Account account = LoniaCore.get().getAccountManager().getAccount(player);
        final Rank rank = account.getUserData().getDataRank().getRank();

        if (DGroup.isInGroup(player.getUniqueId().toString())) {
            if ((rank.hasPermissionLevel(PermissionLevel.RESPONSSABLE) && event.getMessage().startsWith("!")) ||
                    (!rank.hasPermissionLevel(PermissionLevel.RESPONSSABLE) && event.getMessage().startsWith("&"))) {

                TextComponent prefix = new TextComponent("§9[Groupe]");

                TextComponent reportButton = new TextComponent("⚠");
                reportButton.setColor(net.md_5.bungee.api.ChatColor.RED);
                reportButton.setBold(true);
                reportButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/reportMessage " + player.getName() + " " + event.getMessage()));
                reportButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Cliquez pour signaler ce message").color(net.md_5.bungee.api.ChatColor.RED).create()));

                TextComponent mainMessage = new TextComponent("§3&§b" + player.getDisplayName() + ": " + event.getMessage().substring(1));

                TextComponent fullMessage = new TextComponent("");
                fullMessage.addExtra(prefix);
                fullMessage.addExtra(" ");
                fullMessage.addExtra(reportButton);
                fullMessage.addExtra(" ");
                fullMessage.addExtra(mainMessage);

                String jsonMessage = ComponentSerializer.toString(fullMessage);

                DGroup.getMembersList(DGroup.getLeaderUUID(player.getUniqueId().toString(), DGroup.getLeaderName(player.getUniqueId().toString())))
                        .forEach(memberName -> sendMessageRaw(player, memberName, jsonMessage));

                event.setCancelled(true);
                return;
            }
        }
        if (rank.hasPermissionLevel(PermissionLevel.CONSTRUCTEUR) && event.getMessage().startsWith("$")) {
            String msg = "§9[Staff] §5$§d" + player.getDisplayName() + ": " + event.getMessage().substring(1);
            List<String> staff = new ArrayList<>();
            LoniaCore.get().getDataBaseManager().getMySQL().query("SELECT name FROM data_players WHERE power<='" + Ranks.CONSTRUCTEUR.getPower() + "'", rs -> {
                try {
                    while (rs.next()) {
                        if (Account.isConnected(rs.getString("name"))) staff.add(rs.getString("name"));
                    }
                } catch (SQLException e) {
                    Log.log("§cCRITICAL : " + e.getMessage());
                }
            });
            staff.forEach(name -> sendMessageToPlayer(name, msg));
            event.setCancelled(true);
            return;
        }

        if (!LoniaCore.get().getServerAccount().getDataServer().isChatEnabled() && !rank.hasPermissionLevel(PermissionLevel.RESPONSSABLE)) {
            player.sendMessage(ChatMessages.chatDisabled());
            event.setCancelled(true);
            return;
        }

        if (this.antiFlood.contains(player)) {
            player.sendMessage(MessageList.waitBetweenMessages());
            event.setCancelled(true);
            return;
        }

        event.setCancelled(true);

        this.antiFlood.add(player);
        Bukkit.getScheduler().runTaskLater(LoniaCore.get(), () -> this.antiFlood.remove(player), 20L);

    }

    public void sendMessageRaw(Player sender, String targetPlayer, String rawJsonMessage) {
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             DataOutputStream out = new DataOutputStream(byteOut)) {
            out.writeUTF("MessageRaw");

            out.writeUTF(targetPlayer);

            out.writeUTF(rawJsonMessage);

            sender.sendPluginMessage(LoniaCore.get(), "BungeeCord", byteOut.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void sendMessageToPlayer(String playerName, String message) {
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Message");
        out.writeUTF(playerName);
        out.writeUTF(message);
        final Player player = Iterables.getFirst(LoniaCore.connectedPlayer, null);
        assert player != null;
        player.sendPluginMessage(LoniaCore.get(), "BungeeCord", out.toByteArray());
    }
}
