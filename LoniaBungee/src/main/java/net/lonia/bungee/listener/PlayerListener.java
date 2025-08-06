package net.lonia.bungee.listener;

import net.lonia.bungee.LoniaBungee;
import net.lonia.bungee.player.account.Account;
import net.lonia.bungee.player.account.DGroup;
import net.lonia.bungee.player.sanction.SanctionManager;
import net.lonia.bungee.player.sanction.SanctionType;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PostLoginEvent event) {
        final ProxiedPlayer player = event.getPlayer();

        final UUID uuid = player.getUniqueId();
        SanctionManager.updateBan(uuid);
        if (SanctionManager.isSanctionActive(uuid, SanctionType.BAN)) {
            return;
        }

        final Account account = new Account(player.getUniqueId());
        account.onLogin();

        account.getDataFriend().getFriendNames().forEach(friendName -> {
            final ProxiedPlayer friend = ProxyServer.getInstance().getPlayer(friendName);
            if (friend != null && friend.isConnected()) {
                friend.sendMessage(new TextComponent("§9[Amis] §3" + player.getDisplayName() + " §fs'est §aconnecté"));
            }
        });

        if (DGroup.isInGroup(uuid.toString())) {
            DGroup.getMembersList(DGroup.getLeaderUUID(uuid.toString(), DGroup.getLeaderName(uuid.toString()))).forEach(memberName -> {
                final ProxiedPlayer member = ProxyServer.getInstance().getPlayer(memberName);
                if (member != null && member.isConnected()) {
                    member.sendMessage(new TextComponent("§9[Groupe] §3" + player.getDisplayName() + " §fs'est §aconnecté"));
                }
            });
        }
    }

    @EventHandler
    public void onFollow(ServerSwitchEvent event) {
        ProxiedPlayer player = event.getPlayer();

        if (player == null) return;

        if (DGroup.isInGroup(player.getUniqueId().toString())) {
            if (!DGroup.getLeaderName(player.getUniqueId().toString()).equals(player.getDisplayName())) {return;}

            DGroup.getMembersList(player.getUniqueId().toString()).forEach(memberName -> {
                final ProxiedPlayer member = ProxyServer.getInstance().getPlayer(memberName);
                if (member == null) return;

                final Account account = LoniaBungee.getInstance().getAccountManager().getAccount(player);

                if (player.getServer().getInfo().equals(member.getServer().getInfo())) return;

                if (account.getUserData().getDataSettings().enabled_following_group == 1 || !account.getUserData().isInGame() || !member.getServer().getInfo().getName().contains("Build#")) member.connect(player.getServer().getInfo());
                else member.sendMessage(new TextComponent(String.format("§9[Groupe] §3%s §ba rejoint §f%s§b. Faites §f/g tp §bsi vous souhaitez le rejoindre.", player.getDisplayName(), player.getServer().getInfo().getName())));
            });
        }
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent event) {
        final ProxiedPlayer player = event.getPlayer();

        final UUID uuid = player.getUniqueId();

        SanctionManager.updateBan(uuid);
        if (SanctionManager.isSanctionActive(uuid, SanctionType.BAN)) {
            return;
        }

        final Account account = LoniaBungee.getInstance().getAccountManager().getAccount(uuid);

        account.getDataFriend().getFriendNames().forEach(friendName -> {
            final ProxiedPlayer friend = ProxyServer.getInstance().getPlayer(friendName);
            if (friend != null && friend.isConnected()) {
                friend.sendMessage(new TextComponent("§9[Amis] §3" + player.getDisplayName() + " §fs'est §cdéconnecté"));
            }
        });

        if (DGroup.isInGroup(uuid.toString())) {
            DGroup.getMembersList(DGroup.getLeaderUUID(uuid.toString(), DGroup.getLeaderName(uuid.toString()))).forEach(memberName -> {
                final ProxiedPlayer member = ProxyServer.getInstance().getPlayer(memberName);
                if (member != null && member.isConnected()) {
                    member.sendMessage(new TextComponent("§9[Groupe] §3" + player.getDisplayName() + " §fs'est §cdéconnecté"));
                }
            });
        }

        account.onLogout();
    }
}
