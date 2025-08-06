package net.lonia.core.message;

public class EtoileMessages {
    public static String etoileUse() {
        return MessageList.usage("etoile", "add <pseudo> <quantité>", "remove <pseudo> <quantité>");
    }

    public static String etoileAdd(long etoile, String player) {
        return MessageList.success("Vous avez ajouté {" + etoile + "} " + ((etoile < 0) ? "étoiles" : "étoile") + " à {" + player + "} !");
    }

    public static String etoileRemove(long etoile, String player) {
        return MessageList.success("Vous avez retiré {" + etoile + "} " + ((etoile < 0) ? "étoiles" : "étoile") + " à {" + player + "} !");
    }

    public static String etoileReceived(long etoile) {
        return MessageList.success("Vous avez reçu {" + etoile + "} " + ((etoile < 0) ? "étoiles" : "étoile") + " !");
    }

    public static String etoileLost(long etoile) {
        return MessageList.success("Vous avez perdu {" + etoile + "} " + ((etoile < 0) ? "étoiles" : "étoile") + " !");
    }
}
