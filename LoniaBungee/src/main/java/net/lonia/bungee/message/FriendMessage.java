package net.lonia.bungee.message;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class FriendMessage {
    /**
     * Friend Command
     */
    public static String helpFriends() {
        return "§9[Amis] ==================== [Amis]\n" +
                " §3- §b/friends list\n" +
                " §3- §b/friends add §3<pseudo>\n" +
                " §3- §b/friends accept §3<pseudo>\n" +
                " §3- §b/friends decline §3<pseudo>\n" +
                " §3- §b/friends request\n" +
                " §3- §b/friends delete §3<pseudo>\n" +
                " §3- §b/friends join §3<pseudo>\n" +
                "§9[Amis] ==================== [Amis]\n";
    }

    /**
     * Friend utils
     */
    public static String noFriendRequests() {
        return "§9[Amis] §3Erreur: Aucune demande d'amis trouvé.";
    }

    public static String noFriendRequestFoundFrom(String friendName) {
        return "§9[Amis] §3Erreur: Aucune demande d'ami de §3" + friendName + "§b n'a été trouvée.";
    }

    public static String notInFriendsList(String friendName) {
        return "§9[Amis] §3Erreur: §b" + friendName + "§3 n'est pas dans votre liste d'amis.";
    }

    /**
     * List SubCommand
     */
    public static String noFriends() {
        return "§9[Amis] " + MessageList.error("Vous n'avez pas encore d'amis.");
    }

    /**
     * Add SubCommand
     */

    public static String friendAddUsage() {
        return "§9[Amis] §3Utilisation: /friend add §3<pseudo>";
    }

    public static String alreadyFriends() {
        return "§9[Amis] §3Erreur: Vous êtes déjà ami avec ce joueur.";
    }

    public static String friendRequestSent(String friendName) {
        return "§9[Amis] §bDemande d'ami envoyée à §3" + friendName + "§b.";
    }

    public static TextComponent friendRequestReceived(String playerName) {
        // Création du bouton "Accepter"
        TextComponent acceptButton = new TextComponent("[Accepter]");
        acceptButton.setColor(ChatColor.GREEN);
        acceptButton.setBold(true);
        acceptButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/f accept " + playerName));
        acceptButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("/f accept " + playerName).color(ChatColor.AQUA).create()));

        // Création du bouton "Refuser"
        TextComponent refuseButton = new TextComponent(" [Refuser]");
        refuseButton.setColor(ChatColor.RED);
        refuseButton.setBold(true);
        refuseButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/f decline " + playerName));
        refuseButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("/f decline " + playerName).color(ChatColor.AQUA).create()));

        // Création du message
        TextComponent message = new TextComponent("§9[Amis] §3" + playerName + " §bvous a envoyé une demande d'ami. \n");
        message.addExtra(acceptButton);
        message.addExtra(refuseButton);

        return message;
    }

    public static String invitationFriendExpiredMessage(String targetName) {
        return "§9[Amis] §3La demande d'amis a §b" + targetName + " §3a expiré.";
    }

    public static String invitationFriendFromPlayerExpired(String memberName) {
        return "§9[Amis] §3La demande d'amis de §3" + memberName + " §ba expiré.";
    }

    /**
     * Accept SubCommand
     */
    public static String friendAcceptUsage() {
        return "§9[Amis] §3Utilisation: /friend accept §b<pseudo>";
    }

    public static String friendAdded(String friendName) {
        return "§9[Amis] " + MessageList.success("{" + friendName + "} fait maintenant partie de vos amis.");
    }

    /**
     * Decline SubCommand
     */
    public static String friendDeclineUsage() {
        return "§9[Amis] §3Utilisation: /friend decline §b<pseudo>";
    }

    public static String friendDeclined(String friendName) {
        return "§9[Amis] " + MessageList.success("Vous venez de décliner la demande d'amis de {" + friendName + "}.");
    }

    /**
     * Delete SubCommand
     */
    public static String friendDeleteUsage() {
        return "§9[Amis] §3Utilisation: /friend delete §b<pseudo>";
    }

    public static String friendRemoved(String friendName) {
        return "§9[Amis] " + MessageList.success("Vous avez supprimé {" + friendName + "} de votre liste d'amis.");
    }

    /**
     * Join SubCommand
     */
    public static String friendJoinUsage() {
        return "§9[Amis] §3Utilisation: /friend join §b<pseudo>";
    }

    public static String tpToFriend(String friendName) {
        return "§9[Amis] " + MessageList.success("Vous venez de vous téléporter sur le serveur où se trouve {" + friendName + "}.");
    }

    public static String friendNotConnected() {
        return "§9[Amis] " + MessageList.error("Cette ami n'est pas connecté.");
    }

    public static String targetDisabledFriendRequest() {
        return "§9[Amis] §3Erreur: Ce joueur à désactivé les demandes d'amis.";
    }
}
