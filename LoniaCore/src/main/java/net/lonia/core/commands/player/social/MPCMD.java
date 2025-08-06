package net.lonia.core.commands.player.social;

import net.lonia.core.LoniaCore;
import net.lonia.core.commands.AbstractCommand;
import net.lonia.core.message.MPMessage;
import net.lonia.core.message.MessageList;
import net.lonia.core.message.SanctionMessages;
import net.lonia.core.player.account.Account;
import net.lonia.core.player.sanction.SanctionManager;
import net.lonia.core.player.sanction.SanctionType;
import net.lonia.core.rank.PermissionLevel;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MPCMD extends AbstractCommand {

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageList.commandNotAccessibleHere());
            return false;
        }

        player = (Player) sender;
        account = accountManager.getAccount(player);

        if (!account.hasPermissionLevel(PermissionLevel.JOUEUR)) return true;
        
        final UUID uuid = player.getUniqueId();

        SanctionManager.updateMute(uuid);
        if (SanctionManager.isSanctionActive(uuid, SanctionType.MUTE)) {
            player.sendMessage(SanctionMessages.muted(SanctionManager.formatDuration(SanctionManager.getMuteRemainingTime(uuid))));
            return true;
        }

        if (account.getUserData().getDataSettings().enabled_mp == 0) {
            player.sendMessage(MPMessage.needMPEnable());
            return true;
        }

        if (args.length >= 2) {
            final String targetName = args[0];

            Account targetAccount = accountManager.getAccount(Account.getUUID(targetName));
            if (targetAccount == null) targetAccount = new Account(Account.getUUID(targetName));
            targetAccount.loadData();

            if (targetAccount.getUserData().getDataSettings().enabled_mp == 0) {
                player.sendMessage(MPMessage.playerDisabledMP());
                return true;
            }

            if (player.getDisplayName().equalsIgnoreCase(targetName)) {
                player.sendMessage(MPMessage.sendMPHimSelf());
                return true;
            }

            if (!targetAccount.getUserData().isConnected()) {
                player.sendMessage(MessageList.playerNotConnected());
                return true;
            }

            String msg;

            if (account.getUserData().getDataRank().getRank().hasPermissionLevel(PermissionLevel.RESPONSSABLE)) {
                msg = String.join(" ", Arrays.copyOfRange(args, 1, args.length)).replaceAll("&", "§");
            } else {
                msg = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
            }

            if (targetAccount.getUserData().getDataSettings().enabled_mp == 0) {

                if (account.getUserData().getDataRank().getRank().hasPermissionLevel(PermissionLevel.ANIMATEUR)) {
                    if (isIgnored(player, targetName)) {
                        player.sendMessage(MPMessage.ignored());
                    } else if (!isIgnored(player, targetName)) {
                        sendMessageRaw(player, player.getDisplayName(), MPMessage.messageSendTo(targetAccount.getUserData().getDataRank().getRank().getPrefix(), targetName, msg));
                        sendMessageRaw(player, targetName, MPMessage.messageReceivedFrom(account.getUserData().getDataRank().getRank().getPrefix(), player.getDisplayName(), msg));
                        LoniaCore.get().getMap().remove(targetName);

                        LoniaCore.get().getMap().put(targetName, player.getDisplayName());

                        LoniaCore.get().getMap().remove(player.getDisplayName());
                        LoniaCore.get().getMap().put(player.getDisplayName(), targetName);
                    } else if (hasIgnored(player, targetName)) {
                        player.sendMessage(MPMessage.isIgnored());
                    } else if (hasIgnored(player, targetName)) {
                        if (!isIgnored(player, targetName)) {
                            sendMessageRaw(player, player.getDisplayName(), MPMessage.messageSendTo(targetAccount.getUserData().getDataRank().getRank().getPrefix(), targetName, msg));

                            sendMessageRaw(player, targetName, MPMessage.messageReceivedFrom(account.getUserData().getDataRank().getRank().getPrefix(), player.getDisplayName(), msg));

                            LoniaCore.get().getMap().remove(targetName);

                            LoniaCore.get().getMap().put(targetName, player.getDisplayName());

                            LoniaCore.get().getMap().remove(player.getDisplayName());
                            LoniaCore.get().getMap().put(player.getDisplayName(), targetName);
                        }
                    }
                } else {
                    player.sendMessage("§9[MP] §3Erreur: Ce joueur a désactivé la réception de ses MP.");
                }
                return true;
            }
            if (isIgnored(player, targetName)) {
                player.sendMessage("§9[MP] §3Erreur: Ce joueur vous a ignoré.");
            } else if (!isIgnored(player, targetName)) {
                sendMessageRaw(player, player.getDisplayName(), MPMessage.messageSendTo(targetAccount.getUserData().getDataRank().getRank().getPrefix(), targetName, msg));
                sendMessageRaw(player, targetName, MPMessage.messageReceivedFrom(account.getUserData().getDataRank().getRank().getPrefix(), player.getDisplayName(), msg));

                LoniaCore.get().getMap().remove(targetName);

                LoniaCore.get().getMap().put(targetName, player.getDisplayName());

                LoniaCore.get().getMap().remove(player.getDisplayName());
                LoniaCore.get().getMap().put(player.getDisplayName(), targetName);
            } else if (hasIgnored(player, targetName)) {
                player.sendMessage("§9[MP] §3Erreur: Vous avez ignoré ce joueur.");
            } else if (hasIgnored(player, targetName)) {
                if (!isIgnored(player, targetName)) {
                    sendMessageRaw(player, player.getDisplayName(), MPMessage.messageSendTo(targetAccount.getUserData().getDataRank().getRank().getPrefix(), targetName, msg));
                    sendMessageRaw(player, targetName, MPMessage.messageReceivedFrom(account.getUserData().getDataRank().getRank().getPrefix(), player.getDisplayName(), msg));

                    LoniaCore.get().getMap().remove(targetName);

                    LoniaCore.get().getMap().put(targetName, player.getDisplayName());

                    LoniaCore.get().getMap().remove(player.getDisplayName());
                    LoniaCore.get().getMap().put(player.getDisplayName(), targetName);
                }
            }
        } else {
            player.sendMessage("§9[MP] §3Utilisation: §f/msg <pseudo> <message>");
            player.sendMessage("\n \n \n \n \n ");
            player.sendMessage("§9[MP] §3Utilisation: \n §3- §f/msg <pseudo> <message> \n §3- §f/mp <pseudo> <message> \n §3- §f/m <pseudo> <message>");
        }

        return true;
    }

    private boolean isIgnored(Player player, String targetName) {
        Account account = accountManager.getAccount(Account.getUUID(targetName));
        if (account == null) account = new Account(Account.getUUID(targetName));
        account.loadData();

        return account.getUserData().getDataMP().getIgnoredPlayers().contains(player.getDisplayName());
    }

    private boolean hasIgnored(Player player, String targetName) {
        Account account = accountManager.getAccount(player);

        return account.getUserData().getDataMP().getIgnoredPlayers().contains(targetName);
    }

    @Override
    protected List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length == 1) {
            String prefix = args[0].toLowerCase();
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getDisplayName)
                    .filter(name -> !name.equalsIgnoreCase(sender.getName()))
                    .filter(name -> name.toLowerCase().startsWith(prefix))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
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

}
