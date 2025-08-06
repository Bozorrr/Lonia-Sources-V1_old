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
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class RCMD extends AbstractCommand {

    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {

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
            player.sendMessage("§9[MP] §3Erreur: Vous devez activer vos MP afin d'en envoyer.");
            return true;
        }

        if (!LoniaCore.get().getMap().containsKey(player.getDisplayName())) {
            player.sendMessage("§9[MP] §3Erreur: Vous n'avez personne a qui répondre.");
            return true;
        }


        if (args.length >= 1) {

            final String targetName = LoniaCore.get().getMap().get(player.getDisplayName());
            Account targetAccount = accountManager.getAccount(Account.getUUID(targetName));

            if (targetAccount == null) {
                account = new Account(Account.getUUID(targetName));
                account.loadData();
            }

            if (targetAccount.getUserData().getDataSettings().enabled_mp == 0) {
                player.sendMessage("§9[MP] §3Erreur: Ce joueur à désactivé ses MP.");
                return true;
            }

            StringBuilder sb = new StringBuilder();
            for (String arg : args) {
                sb.append(arg).append(" ");
            }

            String msg;
            if (account.getUserData().getDataRank().getRank().hasPermissionLevel(PermissionLevel.RESPONSSABLE)) {
                msg = sb.toString().replaceAll("&", "§");
            } else {
                msg = sb.toString();
            }

            if (targetAccount.getUserData().getDataSettings().enabled_mp == 0) {
                if (account.getUserData().getDataRank().getRank().hasPermissionLevel(PermissionLevel.ANIMATEUR)) {
                    if (hasIgnored(player, targetName)) {
                        player.sendMessage("§9[MP] §3Erreur: Vous avez ignoré ce joueur.");
                        return true;
                    }
                    if (isIgnored(player, targetName)) {
                        player.sendMessage("§9[MP] §3Erreur: Ce joueur vous à ignoré.");
                        return true;
                    }
                    sendMessageRaw(player, targetName, MPMessage.messageReceivedFrom(account.getUserData().getDataRank().getRank().getPrefix(), player.getDisplayName(), msg));
                    sendMessageRaw(player, player.getDisplayName(), MPMessage.messageSendTo(targetAccount.getUserData().getDataRank().getRank().getPrefix(), targetName, msg));

                    if (LoniaCore.get().getMap().containsKey(targetName))
                        if (!LoniaCore.get().getMap().get(targetName).equalsIgnoreCase(player.getDisplayName())) {
                            LoniaCore.get().getMap().remove(targetName);
                            LoniaCore.get().getMap().put(targetName, player.getDisplayName());
                        }
                    if (!LoniaCore.get().getMap().containsKey(targetName)) {
                        LoniaCore.get().getMap().put(targetName, player.getDisplayName());
                    }
                } else player.sendMessage("§9[PM] §3Erreur: Ce joueur à désactivé la réception de ses MP.");
                return true;
            }

            if (hasIgnored(player, targetName)) {
                player.sendMessage("§9[MP] §3Erreur: Vous avez ignoré ce joueur.");
                return true;
            }
            if (isIgnored(player, targetName)) {
                player.sendMessage("§9[MP] §3Erreur: Ce joueur vous à ignoré.");
                return true;
            }
            sendMessageRaw(player, targetName, MPMessage.messageReceivedFrom(account.getUserData().getDataRank().getRank().getPrefix(), player.getDisplayName(), msg));
            sendMessageRaw(player, player.getDisplayName(), MPMessage.messageSendTo(targetAccount.getUserData().getDataRank().getRank().getPrefix(), targetName, msg));

            if (LoniaCore.get().getMap().containsKey(targetName))
                if (!LoniaCore.get().getMap().get(targetName).equalsIgnoreCase(player.getDisplayName())) {
                    LoniaCore.get().getMap().remove(targetName);
                    LoniaCore.get().getMap().put(targetName, player.getDisplayName());
                }
            if (!LoniaCore.get().getMap().containsKey(targetName)) {
                LoniaCore.get().getMap().put(targetName, player.getDisplayName());
            }
        } else {
            player.sendMessage("§9[MP] §3Utilisation: §f/r <message>");
        }

        return true;

    }

    @Override
    protected List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return Collections.emptyList();
    }

    private boolean isIgnored(Player player, String targetName) {
        Account account = accountManager.getAccount(Account.getUUID(targetName));
        if (account == null) account = new Account(Account.getUUID(targetName));
        account.loadData();

        return account.getUserData().getDataMP().getIgnoredPlayers().contains(player.getDisplayName());
    }

    private boolean hasIgnored(Player player, String targetName) {
        Account account = LoniaCore.get().getAccountManager().getAccount(player);

        return account.getUserData().getDataMP().getIgnoredPlayers().contains(targetName);
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

