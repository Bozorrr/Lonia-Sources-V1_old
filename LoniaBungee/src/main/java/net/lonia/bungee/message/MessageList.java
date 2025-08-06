package net.lonia.bungee.message;

public class MessageList {

    public static String error(String message) {
        return "§3Erreur: " + message.replaceAll("\\{([^}]+)}", "§b$1§3");
    }


    public static String success(String message) {
        return "§b" + message.replaceAll("\\{([^}]+)}", "§3$1§b");
    }

    /**
     * Utils All
     */

    public static String commandNotAccessibleHere() {
        return "§3Erreur: Impossible d'utiliser cette commande ici.";
    }

    public static String playerNotConnected() {
        return "§3Erreur: Ce joueur n'est pas connecté !";
    }
}
