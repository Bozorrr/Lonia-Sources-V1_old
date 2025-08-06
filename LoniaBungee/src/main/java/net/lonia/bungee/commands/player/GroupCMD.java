package net.lonia.bungee.commands.player;

import net.lonia.bungee.LoniaBungee;
import net.lonia.bungee.commands.AbstractCommand;
import net.lonia.bungee.message.GroupMessage;
import net.lonia.bungee.message.MessageList;
import net.lonia.bungee.player.account.Account;
import net.lonia.bungee.player.account.DGroup;
import net.lonia.bungee.rank.PermissionLevel;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class GroupCMD extends AbstractCommand {

    private static final List<String> SUBCOMMANDS = Arrays.asList(
            "help", "list", "invite", "kick", "accept", "decline", "leave", "disband", "tp", "lead"
    );

    public GroupCMD() {
        super("group", "", "g");
    }

    @Override
    protected void onCommand(CommandSender sender, String label, String[] args) {
        player = (ProxiedPlayer) sender;
        account = accountManager.getAccount(player);

        if (!account.hasPermissionLevel(PermissionLevel.JOUEUR)) return;

        if (args.length < 1) {
            sendMessage(player, GroupMessage.helpGroup());
            return;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "list":
                handleListCommand(player, account);
                break;

            case "invite":
                if (args.length >= 2) handleInviteCommand(player, account, args[1]);
                else sendMessage(player, GroupMessage.groupInviteUsage());
                break;

            case "kick":
                if (args.length >= 2) handleKickCommand(player, account, args[1]);
                else sendMessage(player, GroupMessage.groupKickUsage());
                break;

            case "accept":
                if (args.length >= 2) handleAcceptCommand(player, account, args[1]);
                else sendMessage(player, GroupMessage.groupAcceptUsage());
                break;

            case "decline":
                if (args.length >= 2) handleDeclineCommand(player, account, args[1]);
                else sendMessage(player, GroupMessage.groupDeclineUsage());
                break;

            case "leave":
                handleLeaveCommand(player, account);
                break;

            case "disband":
                handleDisbandCommand(player, account);
                break;

            case "tp":
                handleTpCommand(player, account);
                break;

            case "lead":
                if (args.length >= 2) handleLeadCommand(player, account, args[1]);
                else sendMessage(player, GroupMessage.groupLeadUsage());
                break;

            case "help":
            default:
                sendMessage(player, GroupMessage.helpGroup());
                break;
        }
    }

    private void handleListCommand(ProxiedPlayer player, Account account) {
        String playerUUID = player.getUniqueId().toString();

        if (!DGroup.isInGroup(playerUUID)) {
            sendMessage(player, GroupMessage.notInGroup());
            return;
        }

        String leaderName = DGroup.getLeaderName(playerUUID);
        String leaderUUID = DGroup.getLeaderUUID(playerUUID, leaderName);

        List<String> members = DGroup.getMembersList(leaderUUID);
        members.remove(leaderName);

        ProxiedPlayer leader = getPlayer(leaderName);

        List<String> onlineMembers = new ArrayList<>();
        List<String> offlineMembers = new ArrayList<>();

        for (String name : members) {
            ProxiedPlayer member = getPlayer(name);
            if (member != null && member.isConnected()) {
                onlineMembers.add("§3- §a⬤ §b" + name + " §3(" + member.getServer().getInfo().getName() + ")");
            } else {
                offlineMembers.add("§3- §c⬤ §7" + name);
            }
        }

        StringBuilder membersList = new StringBuilder();

        if (leaderName != null) {
            String leaderStatus;
            if (leader != null && leader.isConnected()) {
                leaderStatus = "§3- §a⬤ §6♚ §b" + leaderName + " §3(" + leader.getServer().getInfo().getName() + ")";
            } else {
                leaderStatus = "§3- §c⬤ §6♚ §b" + leaderName;
            }
            membersList.append(leaderStatus).append("\n");
        }

        if (!onlineMembers.isEmpty()) {
            membersList.append(String.join("\n", onlineMembers)).append("\n");
        }

        if (!offlineMembers.isEmpty()) {
            membersList.append(String.join("\n", offlineMembers)).append("\n");
        }

        sendMessage(player, "§9[Groupe] §bListe du groupe :\n" + membersList.toString().trim());
    }


    private void handleInviteCommand(ProxiedPlayer player, Account account, String targetName) {
        final String playerUUID = player.getUniqueId().toString();

        final ProxiedPlayer target = getPlayer(targetName);

        if (target == null || !target.isConnected()) {
            sendMessage(player, MessageList.playerNotConnected());
            return;
        }

        Account tAccount = accountManager.getAccount(target);
        if (tAccount.getUserData().getDataSettings().enabled_group_request == 0) {
            sendMessage(player, GroupMessage.targetDisabledGroupRequest());
            return;
        }

        final String targetUUID = target.getUniqueId().toString();

        final DGroup dataGroup = account.getDataGroup();

        if (DGroup.isInGroup(targetUUID)) {
            sendMessage(player, GroupMessage.alreadyInGroup(target.getDisplayName()));
            return;
        }

        if (DGroup.isInGroup(playerUUID)) {
            String leaderName = DGroup.getLeaderName(playerUUID);
            String leaderUUID = DGroup.getLeaderUUID(playerUUID, leaderName);

            if (DGroup.getGroupRequests(targetUUID).contains(leaderUUID)) {
                sendMessage(player, GroupMessage.alreadySentInvitationMessage(target.getDisplayName()));
                return;
            }
            int maxMembers = dataGroup.getMaxMembers(leaderUUID);
            int actualMembers = DGroup.getMembersList(leaderUUID).size();

            if (actualMembers >= maxMembers) {
                sendMessage(player, GroupMessage.groupIsFull());
                return;
            }

        } else {
            dataGroup.createGroup(playerUUID, player.getDisplayName(), account.getUserData().getDataRank().getRank().getMax_per_group());
        }

        String leaderName = DGroup.getLeaderName(playerUUID);
        String leaderUUID = DGroup.getLeaderUUID(playerUUID, leaderName);

        dataGroup.addGroupRequest(leaderUUID, leaderName, targetUUID, target.getDisplayName());

        sendMessage(player, GroupMessage.memberInvitedToGroup(targetName));

        target.sendMessage(GroupMessage.invitedToGroupMessage(player.getDisplayName()));

        AtomicBoolean stopLoop = new AtomicBoolean(false);


        LoniaBungee.getInstance().getProxy().getScheduler().schedule(LoniaBungee.getInstance(), () -> {
            if (!DGroup.getGroupRequests(targetUUID).contains(leaderName))
                stopLoop.set(true);

            if (!stopLoop.get()) {
                accountManager.getAccount(target).getDataGroup().removeGroupRequest(leaderUUID, targetUUID);
                account.getDataGroup().deleteGroup(leaderUUID);
                sendMessage(player, GroupMessage.invitationGroupExpiredMessage(target.getName()));
                sendMessage(target, GroupMessage.invitationGroupFromPlayerExpired(player.getDisplayName()));
            }
        }, 1, TimeUnit.MINUTES);
    }


    private void handleKickCommand(ProxiedPlayer player, Account account, String memberName) {
        String playerUUID = player.getUniqueId().toString();

        if (!DGroup.isInGroup(playerUUID)) {
            sendMessage(player, GroupMessage.notInGroup());
            return;
        }

        final String leaderName = DGroup.getLeaderName(playerUUID);
        final String leaderUUID = DGroup.getLeaderUUID(playerUUID, leaderName);

        if (!playerUUID.equals(leaderUUID)) {
            sendMessage(player, GroupMessage.notGroupLeader());
            return;
        }

        if (DGroup.getMembersList(leaderUUID).contains(memberName)) {
            ProxiedPlayer member = getPlayer(memberName);

            if (member != null && member.isConnected()) sendMessage(member, "§9[Groupe] §3Vous avez été éjecté du groupe.");
            account.getDataGroup().removeGroupMemberByName(leaderUUID, memberName);

            notifyGroupMembers(account, leaderUUID, GroupMessage.playerKickedFromGroupMessage(memberName));

            if (DGroup.getMembersList(leaderUUID).size() == 1) {
                account.getDataGroup().deleteGroup(leaderUUID);
                sendMessage(player, GroupMessage.groupDeleted());
            }
        } else {
            sendMessage(player, GroupMessage.playerNotInGroup(memberName));
        }
    }


    private void handleAcceptCommand(ProxiedPlayer player, Account account, String leaderName) {
        String playerUUID = player.getUniqueId().toString();

        String leaderUUID = DGroup.getLeaderUUID(playerUUID, leaderName);

        if (DGroup.getGroupRequests(playerUUID).contains(leaderName)) {
            account.getDataGroup().acceptGroupRequest(leaderUUID, playerUUID);
            notifyGroupMembers(account, leaderUUID, GroupMessage.playerJoinedGroup(player.getDisplayName()));
        } else sendMessage(player, GroupMessage.invitationGroupFromPlayerExpired(leaderName));
    }

    private void handleDeclineCommand(ProxiedPlayer player, Account account, String leaderName) {
        String playerUUID = player.getUniqueId().toString();

        if (DGroup.getGroupRequests(playerUUID).contains(leaderName)) {

            String leaderUUID = DGroup.getLeaderUUID(playerUUID, leaderName);
            ProxiedPlayer leader = getPlayer(leaderName);

            account.getDataGroup().removeGroupRequest(leaderUUID, playerUUID);

            sendMessage(player, GroupMessage.memberDeclinedInvitation(leaderName));
            sendMessage(leader, GroupMessage.invitationDeclinedMessage(player.getDisplayName()));

            List<String> remainingMembers = DGroup.getMembersList(leaderUUID);
            if (remainingMembers.size() == 1) {
                account.getDataGroup().deleteGroup(leaderUUID);
                sendMessage(player, GroupMessage.groupDeleted());
            }
        } else sendMessage(player, GroupMessage.invitationGroupFromPlayerExpired(leaderName));
    }

    private void handleLeaveCommand(ProxiedPlayer player, Account account) {
        String playerUUID = player.getUniqueId().toString();

        if (!DGroup.isInGroup(playerUUID)) {
            sendMessage(player, GroupMessage.notInGroup());
            return;
        }

        String leaderName = DGroup.getLeaderName(playerUUID);
        String leaderUUID = DGroup.getLeaderUUID(playerUUID, leaderName);

        account.getDataGroup().removeGroupMember(leaderUUID, playerUUID);
        sendMessage(player, GroupMessage.groupLeft());
        notifyGroupMembers(account, leaderUUID, GroupMessage.playerLeftGroup(player.getName()));
        

        if (DGroup.getMembersList(leaderUUID).size() == 1) {
            account.getDataGroup().deleteGroup(leaderUUID);

            ProxiedPlayer leader = getPlayer(leaderName);

            if (leader != null && leader.isConnected()) {
                sendMessage(leader, GroupMessage.groupDeleted());
            }
        }
    }


    private void handleDisbandCommand(ProxiedPlayer player, Account account) {
        String playerUUID = player.getUniqueId().toString();

        if (!DGroup.isInGroup(playerUUID)) {
            sendMessage(player, GroupMessage.notInGroup());
            return;
        }

        String leaderName = DGroup.getLeaderName(playerUUID);
        String leaderUUID = DGroup.getLeaderUUID(playerUUID, leaderName);

        if (!leaderUUID.equals(playerUUID)) {
            sendMessage(player, GroupMessage.notGroupLeader());
            return;
        }

        account.getDataGroup().deleteGroup(leaderUUID);
        notifyGroupMembers(account, leaderUUID, GroupMessage.groupDeleted());
    }

    private void handleTpCommand(ProxiedPlayer player, Account account) {
        String playerUUID = player.getUniqueId().toString();

        if (!DGroup.isInGroup(playerUUID)) {
            sendMessage(player, GroupMessage.notInGroup());
            return;
        }

        String leaderName = DGroup.getLeaderName(playerUUID);
        String leaderUUID = DGroup.getLeaderUUID(playerUUID, leaderName);

        if (playerUUID.equals(leaderUUID)) {
            sendMessage(player, GroupMessage.youAreTheLeader());
            return;
        }

        ProxiedPlayer leader = getPlayer(DGroup.getLeaderName(playerUUID));
        if (leader == null || !leader.isConnected()) {
            sendMessage(player, GroupMessage.groupLeaderNotConnected());
            return;
        }
        ServerInfo serverInfo = leader.getServer().getInfo();
        if (player.getServer().getInfo().equals(serverInfo)) {
            sendMessage(player, GroupMessage.alreadyOnServerWithGroup());
            return;
        }

        player.connect(serverInfo);
        sendMessage(player, GroupMessage.teleportedToGroupLeader());

    }

    public void handleLeadCommand(ProxiedPlayer player, Account account, String memberName) {
        String playerUUID = player.getUniqueId().toString();

        if (!DGroup.isInGroup(playerUUID)) {
            sendMessage(player, GroupMessage.notInGroup());
            return;
        }

        String leaderName = DGroup.getLeaderName(playerUUID);
        String leaderUUID = DGroup.getLeaderUUID(playerUUID, leaderName);

        if (!playerUUID.equals(leaderUUID)) {
            sendMessage(player, GroupMessage.notGroupLeader());
            return;
        }

        final ProxiedPlayer member = getPlayer(memberName);

        if (DGroup.getMembersList(leaderUUID).contains(memberName)) {
            Account mAccount = LoniaBungee.getInstance().getAccountManager().getAccount(member);
            int maxMembers = mAccount.getUserData().getDataRank().getRank().getMax_per_group();

            account.getDataGroup().updateGroup(leaderUUID, member.getUniqueId().toString(), memberName, maxMembers);
            notifyGroupMembers(account, member.getUniqueId().toString(), GroupMessage.memberLeadedToLeaderMessage(memberName));
            sendMessage(member, GroupMessage.youAreNowGroupLeaderMessage());
        }
    }

    private void notifyGroupMembers(Account account, String leaderUUID, String message) {
        DGroup.getMembersList(leaderUUID).forEach(name -> {
            ProxiedPlayer p = getPlayer(name);
            if (p != null && p.isConnected()) {
                sendMessage(p, message);
            }
        });
    }

    private List<String> getTabCompletionForSubCommand(String subCommand, Account account, ProxiedPlayer player, String arg) {
        switch (subCommand) {
            case "invite":
                return loniaBungee.getProxy().getPlayers().stream()
                        .filter(p -> !p.equals(player) && p.getName().toLowerCase().startsWith(arg.toLowerCase()))
                        .map(ProxiedPlayer::getName)
                        .collect(Collectors.toList());
            case "kick":
            case "lead":
                if (DGroup.getLeaderName(player.getUniqueId().toString()).equals(player.getDisplayName())) {
                    return DGroup.getMembersList(player.getUniqueId().toString()).stream()
                            .filter(m -> m.toLowerCase().startsWith(arg.toLowerCase()))
                            .collect(Collectors.toList());
                }
            case "accept":
            case "decline":
                return DGroup.getGroupRequests(player.getUniqueId().toString()).stream()
                        .filter(name -> name.toLowerCase().startsWith(arg.toLowerCase()))
                        .collect(Collectors.toList());
            default:
                return new ArrayList<>();
        }
    }

    @Override
    protected List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        if (!(sender instanceof ProxiedPlayer)) return completions;

        player = (ProxiedPlayer) sender;
        account = accountManager.getAccount(player);

        if (args.length == 1) {
            completions = SUBCOMMANDS.stream()
                    .filter(cmd -> cmd.startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        } else if (args.length == 2) {
            String subCommand = args[0].toLowerCase();
            completions = getTabCompletionForSubCommand(subCommand, account, player, args[1]);
        }
        return completions;
    }
}
