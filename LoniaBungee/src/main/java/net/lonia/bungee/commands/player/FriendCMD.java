package net.lonia.bungee.commands.player;

import net.lonia.bungee.LoniaBungee;
import net.lonia.bungee.commands.AbstractCommand;
import net.lonia.bungee.message.FriendMessage;
import net.lonia.bungee.message.MessageList;
import net.lonia.bungee.player.account.Account;
import net.lonia.bungee.rank.PermissionLevel;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class FriendCMD extends AbstractCommand {

    private static final List<String> SUBCOMMANDS = Arrays.asList(
            "list", "add", "accept", "decline", "delete", "join", "request", "help", "h"
    );

    public FriendCMD() {
        super("friend", "", "f");
    }


    private void handleListCommand(ProxiedPlayer player, Account account) {
        List<String> friends = account.getDataFriend().getFriendNames();
        if (friends.isEmpty()) {
            sendMessage(player, FriendMessage.noFriends());
            return;
        }

        String onlineFriendsList = friends.stream()
                .filter(friendName -> ProxyServer.getInstance().getPlayer(friendName) != null && ProxyServer.getInstance().getPlayer(friendName).isConnected())
                .map(friendName -> "\n§3- §a⬤ §b" + friendName + " §3(" + ProxyServer.getInstance().getPlayer(friendName).getServer().getInfo().getName() + ")")
                .collect(Collectors.joining());

        String offlineFriendsList = friends.stream()
                .filter(friendName -> ProxyServer.getInstance().getPlayer(friendName) == null || !ProxyServer.getInstance().getPlayer(friendName).isConnected())
                .map(friendName -> "\n§3- §c⬤ §7" + friendName)
                .collect(Collectors.joining());

        sendMessage(player, "§9[Amis] §bListe de vos amis :\n " + onlineFriendsList + offlineFriendsList + "\n ");
    }

    private void handleAddCommand(ProxiedPlayer player, String[] args) {
        if (args.length < 2) {
            sendMessage(player, FriendMessage.friendAddUsage());
            return;
        }
        String friendName = args[1];
        ProxiedPlayer friend = ProxyServer.getInstance().getPlayer(friendName);
        if (friend != null && friend.isConnected()) {
            Account fAccount = LoniaBungee.getInstance().getAccountManager().getAccount(friend);
            if (fAccount.getDataFriend().getFriendNames().contains(player.getDisplayName())) {
                sendMessage(player, FriendMessage.alreadyFriends());
                return;
            }
            Account tAccount = accountManager.getAccount(friend);
            if (tAccount.getUserData().getDataSettings().enabled_group_request == 0) {
                sendMessage(player, FriendMessage.targetDisabledFriendRequest());
                return;
            }
            fAccount.getDataFriend().addFriendRequest(player.getUniqueId());
            sendMessage(player, FriendMessage.friendRequestSent(friend.getDisplayName()));
            friend.sendMessage(FriendMessage.friendRequestReceived(player.getDisplayName()));

            AtomicBoolean stopLoop = new AtomicBoolean(false);

            LoniaBungee.getInstance().getProxy().getScheduler().schedule(LoniaBungee.getInstance(), () -> {
                if (!account.getDataFriend().getRequestedFriend().contains(friendName))
                    stopLoop.set(true);

                if (!stopLoop.get()) {
                    account.getDataFriend().removeFriendRequest(friend.getUniqueId());
                    sendMessage(player, FriendMessage.invitationFriendExpiredMessage(friend.getName()));
                    sendMessage(friend, FriendMessage.invitationFriendFromPlayerExpired(player.getName()));
                }
            }, 1, TimeUnit.MINUTES);
        } else sendMessage(player, MessageList.playerNotConnected());
    }

    private void handleAcceptCommand(ProxiedPlayer player, Account account, String[] args) {
        if (args.length < 2) {
            sendMessage(player, FriendMessage.friendAcceptUsage());
            return;
        }
        String friendName = args[1];
        ProxiedPlayer friend = ProxyServer.getInstance().getPlayer(friendName);
        if (friend != null && friend.isConnected() && account.getDataFriend().getRequestedFriend().contains(friend.getDisplayName())) {
            account.getDataFriend().addFriend(friend.getUniqueId());
            sendMessage(player, FriendMessage.friendAdded(friendName));
            sendMessage(friend, FriendMessage.friendAdded(player.getDisplayName()));
        } else sendMessage(player, FriendMessage.noFriendRequestFoundFrom(friendName));
    }

    private void handleDeclineCommand(ProxiedPlayer player, Account account, String[] args) {
        if (args.length < 2) {
            sendMessage(player, FriendMessage.friendDeclineUsage());
            return;
        }
        String friendName = args[1];
        if (account.getDataFriend().getRequestedFriend().contains(friendName)) {
            account.getDataFriend().removeFriendRequest(friendName);
            sendMessage(player, FriendMessage.friendDeclined(friendName));
        } else sendMessage(player, FriendMessage.noFriendRequestFoundFrom(friendName));
    }

    private void handleRequestCommand(ProxiedPlayer player, Account account) {
        if (account.getDataFriend().getRequestedFriend().isEmpty()) {
            sendMessage(player, FriendMessage.noFriendRequests());
        } else sendMessage(player, "§9[Amis] §bListe de vos demandes d'amis" +
                account.getDataFriend().getRequestedFriend().stream()
                        .map(request -> "\n§3- §b" + request)
                        .collect(Collectors.joining()));
    }

    private void handleDeleteCommand(ProxiedPlayer player, Account account, String[] args) {
        if (args.length < 2) {
            sendMessage(player, FriendMessage.friendDeleteUsage());
            return;
        }
        String friendName = args[1];
        if (account.getDataFriend().getFriendNames().contains(friendName)) {
            account.getDataFriend().removeFriend(friendName);
            sendMessage(player, FriendMessage.friendRemoved(friendName));
        } else sendMessage(player, FriendMessage.notInFriendsList(friendName));
    }

    private void handleJoinCommand(ProxiedPlayer player, Account account, String[] args) {
        if (args.length < 2) {
            sendMessage(player, FriendMessage.friendJoinUsage());
            return;
        }
        String friendName = args[1];
        ProxiedPlayer friend = ProxyServer.getInstance().getPlayer(friendName);

        if (friend != null && friend.isConnected() && account.getDataFriend().getFriendNames().contains(friendName)) {
            ServerInfo serverFriend = friend.getServer().getInfo();
            player.connect(serverFriend);
            sendMessage(player, FriendMessage.tpToFriend(friendName));
        } else sendMessage(player, FriendMessage.friendNotConnected());

    }

    private List<String> getTabCompletionForSubCommand(String subCommand, Account account, ProxiedPlayer player, String arg) {
        switch (subCommand) {
            case "add":
                return ProxyServer.getInstance().getPlayers().stream()
                        .filter(p -> !p.equals(player) && p.getName().toLowerCase().startsWith(arg.toLowerCase()))
                        .map(ProxiedPlayer::getName)
                        .collect(Collectors.toList());
            case "accept":
            case "decline":
                return account.getDataFriend().getRequestedFriend().stream()
                        .filter(name -> name.toLowerCase().startsWith(arg.toLowerCase()))
                        .collect(Collectors.toList());
            case "delete":
            case "join":
                return account.getDataFriend().getFriendNames().stream()
                        .filter(name -> name.toLowerCase().startsWith(arg.toLowerCase()))
                        .collect(Collectors.toList());
            default:
                return new ArrayList<>();
        }
    }

    @Override
    protected void onCommand(CommandSender sender, String label, String[] args) {
        player = (ProxiedPlayer) sender;
        account = LoniaBungee.getInstance().getAccountManager().getAccount(player);

        if (!account.hasPermissionLevel(PermissionLevel.JOUEUR)) return;

        if (args.length < 1) {
            sendMessage(player, FriendMessage.helpFriends());
            return;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "list":
                handleListCommand(player, account);
                break;
            case "add":
                handleAddCommand(player, args);
                break;
            case "accept":
                handleAcceptCommand(player, account, args);
                break;
            case "decline":
                handleDeclineCommand(player, account, args);
                break;
            case "request":
                handleRequestCommand(player, account);
                break;
            case "delete":
                handleDeleteCommand(player, account, args);
                break;
            case "join":
                handleJoinCommand(player, account, args);
                break;
            default:
                sendMessage(player, FriendMessage.helpFriends());
                break;
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
