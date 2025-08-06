package net.lonia.core.message;

import net.lonia.core.player.account.Account;
import net.lonia.core.player.sanction.SanctionManager;

import java.security.cert.CertPathValidatorException;
import java.util.UUID;

public class SanctionMessages {

    public static String muted(String formattedTime) {
        return "§a \n§9[Sanction] §3Vous êtes réduit au silence ! §c(Mute)\n§3Il vous reste §f" + formattedTime + " §3avant de pouvoir re-parler.\n §b";
    }

    public static String mute(String reason) {
        return "\n§9[Sanction] §c[Mute] \n \n §cVous avez été réduit au silence !\n §cMotif : " + reason + "\n ";
    }

    public static String muteAlert(String name, String moderator, String reason) {
        return "\n§9[Staff] §d[Admin follow] \n §e" + moderator + " §f: §3" + name + " §cmute " + SanctionManager.formatDuration(SanctionManager.getMuteRemainingTime(Account.getUUID(name))) + "§6(" + reason + ") §a[Look]";
    }

    public static String banned(UUID uuid, String formattedTime) {
        return "§4✖ §cVous êtes banni du serveur ! §4✖\n \n" +
                "§c- Par " + SanctionManager.getModerator(uuid) + " : Infraction : " + SanctionManager.getBanReason(uuid) + "\n" +
                "§c§l● §cTemps restant : " + formattedTime + "\n" +
                "§a \n" +
                "§3Pour faire appel, rejoignez notre serveur discord:\n" +
                "§fhttps://discord.gg/lonia";
    }

    public static String ban(String reason) {
        return "§4✖ §cVous avez été banni du serveur ! §4✖\n \n§cInfraction : " + reason;
    }

    public static String banAlert(String name, String moderator, String reason) {
        return "\n§9[Staff] §d[Admin follow] \n §e" + moderator + " §f: §3" + name + " §cban " + SanctionManager.getMuteRemainingTime(Account.getUUID(name)) + "§6(" + reason + ") §a[Look]";
    }

    public static String kickAlert(String name, String moderator, String reason) {
        return "\n§9[Staff] §d[Admin follow] \n §e" + moderator + " §f: §3" + name + " §ckick " + SanctionManager.getMuteRemainingTime(Account.getUUID(name)) + "§6(" + reason + ")";
    }

    public static String kick(String moderator, String reason) {
        return "§cVous avez été éjecté du serveur par " + moderator + "\n \n● Motif : " + reason;
    }

    public static String unban() {
        return "§9[Sanction] §bLa sanction a été retirée, le joueur est dé-banni.";
    }

    public static String unmute() {
        return "§9[Sanction] §bLa sanction a été retirée, le joueur n'est plus mute.";
    }
}
