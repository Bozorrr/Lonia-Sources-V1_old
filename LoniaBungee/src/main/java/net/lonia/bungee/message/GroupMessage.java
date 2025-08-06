package net.lonia.bungee.message;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class GroupMessage {
    /**
     * Group Command
     */
    public static String helpGroup() {
        return "§9[Groupe] ==================== [Groupe]\n" +
                " §3- §b/group list\n" +
                " §3- §b/group invite §3<pseudo>\n" +
                " §3- §b/group kick §3<pseudo>\n" +
                " §3- §b/group accept §3<pseudo>\n" +
                " §3- §b/group decline §3<pseudo>\n" +
                " §3- §b/group leave\n" +
                " §3- §b/group disband\n" +
                " §3- §b/group tp §3<pseudo>\n" +
                " §3- §b/group lead §3<pseudo>\n" +
                "§9[Groupe] ==================== [Groupe]\n";
    }

    /**
     * Group Utils
     */
    public static String notInGroup() {
        return "§9[Groupe] " + MessageList.error("Vous n'êtes pas dans un groupe.");
    }

    /**
     * Invite SubCommand
     */
    public static String groupInviteUsage() {
        return "§9[Groupe] §3Utilisation: /g invite §b<pseudo>";
    }

    public static String groupIsFull() {
        return "§9[Groupe] §3Erreur: Le groupe est plein.";
    }

    public static String memberInvitedToGroup(String targetName) {
        return "§9[Groupe] §bVous avez invité §3" + targetName + "§b dans le groupe.";
    }

    public static TextComponent invitedToGroupMessage(String memberName) {
        TextComponent acceptButton = new TextComponent("[Accepter]");
        acceptButton.setColor(ChatColor.GREEN);
        acceptButton.setBold(true);
        acceptButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/g accept " + memberName));
        acceptButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("/g accept " + memberName).color(ChatColor.AQUA).create()));

        TextComponent refuseButton = new TextComponent(" [Refuser]");
        refuseButton.setColor(ChatColor.RED);
        refuseButton.setBold(true);
        refuseButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/g decline " + memberName));
        refuseButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("/g decline " + memberName).color(ChatColor.AQUA).create()));

        TextComponent message = new TextComponent("§9[Groupe] §3" + memberName + "§b Vous a invité dans un groupe \n");
        message.addExtra(acceptButton);
        message.addExtra(refuseButton);

        return message;
    }

    public static String invitationGroupExpiredMessage(String targetName) {
        return "§9[Groupe] §3L'invitation de groupe pour §b" + targetName + " §3a expiré.";
    }

    public static String invitationGroupFromPlayerExpired(String memberName) {
        return "§9[Groupe] §3L'invitation de groupe de §3" + memberName + " §ba expiré.";
    }

    public static String playerJoinedGroup(String member) {
        return "§9[Groupe] §3" + member + "§b a rejoint le groupe.";
    }

    public static String groupAcceptUsage() {
        return "§9[Groupe] §3Utilisation: /group accept §b<pseudo>";
    }

    public static String invitationDeclinedMessage(String leaderName) {
        return "§9[Groupe] §bVous venez de refuser l'invitation de §3" + leaderName + "§b.";
    }

    public static String memberDeclinedInvitation(String member) {
        return "§9[Groupe] §3Erreur: §b" + member + " §3vient de refuser votre invitation de groupe.";
    }

    public static String groupDeclineUsage() {
        return "§9[Groupe] §3Utilisation: /group decline §b<pseudo>";
    }

    public static String playerKickedFromGroupMessage(String memberName) {
        return "§9[Groupe] §3" + memberName + " §ba été expulsé du groupe.";
    }

    public static String groupKickUsage() {
        return "§9[Groupe] §3Utilisation: /group kick §b<pseudo>";
    }

    public static String playerLeftGroup(String name) {
        return "§9[Groupe] §3" + name + " §ba quitté le groupe.";
    }

    public static String groupLeft() {
        return "§9[Groupe] §3Vous avez quitté le groupe.";
    }

    public static String notGroupLeader() {
        return "§9[Groupe] §3Erreur: Vous n'êtes pas le chef du groupe.";
    }

    public static String groupDeleted() {
        return "§9[Groupe] §3Votre groupe a été supprimé.";
    }

    public static String cannotTeleportToSelfMessage() {
        return "§9[Groupe] §3Erreur: Vous ne pouvez pas vous téléporter à vous-même.";
    }

    public static String alreadyOnServerWithGroup() {
        return "§9[Groupe] §3Vous êtes déjà sur le même serveur que votre groupe.";
    }

    public static String teleportedToGroupLeader() {
        return "§9[Groupe] §3Vous venez de vous téléporter sur le même serveur que votre groupe.";
    }

    public static String groupLeadUsage() {
        return "§9[Groupe] §3Utilisation: /group promote §b<pseudo>";
    }

    public static String groupLeaderNotConnected() {
        return "§9[Groupe] §3Erreur: Le chef du groupe n'est pas connecté!";
    }

    public static String memberLeadedToLeaderMessage(String memberName) {
        return "§9[Groupe] §3" + memberName + " §ba été promu chef du groupe.";
    }

    public static String youAreNowGroupLeaderMessage() {
        return "§9[Groupe] §bVous êtes désormais chef du groupe.";
    }

    public static String alreadyInGroup(String memberName) {
        return "§9[Groupe] §3Erreur: §b" + memberName + " §3est déjà dans un groupe.";
    }

    public static String alreadySentInvitationMessage(String targetName) {
        return "§9[Groupe] §3Erreur: Vous avez déjà envoyé une invitation de groupe à §b" + targetName + "§3.";
    }

    public static String playerNotInGroup(String memberName) {
        return "§9[Groupe] §3Erreur: §b" + memberName + " §3n'est pas dans votre groupe.";
    }

    public static String youAreTheLeader() {
        return "§9[Groupe] §3Erreur: Vous êtes le chef du groupe.";
    }

    public static String noInvitationFromPlayerExpired(String leaderName) {
        return "§9[Groupe] §3Erreur: Aucune invitation de groupe de §3" + leaderName + "§b.";
    }

    public static String targetDisabledGroupRequest() {
        return "§9[Groupe] §3Erreur: Ce joueur à désactivé les demandes de groupe.";
    }
}
