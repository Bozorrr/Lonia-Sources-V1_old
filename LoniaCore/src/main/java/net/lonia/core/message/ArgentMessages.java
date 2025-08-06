package net.lonia.core.message;

public class ArgentMessages {
    public static String argentUse() {
        return MessageList.usage("argent", "add <pseudo> <quantité>", "remove <pseudo> <quantité>");
    }

    public static String argentAdd(long argent, String player) {
        return MessageList.success("Vous avez ajouté {" + argent + "} " + ((argent < 0) ? "argents" : "argent") + " à {" + player + "} !");
    }

    public static String argentRemove(long argent, String player) {
        return MessageList.success("Vous avez retiré {" + argent + "} " + ((argent < 0) ? "argents" : "argent") + " à {" + player + "}!");
    }

    public static String argentReceived(long argent) {
        return MessageList.success("Vous avez reçu {" + argent + "} " + ((argent < 0) ? "argents" : "argent") + " !");
    }

    public static String argentLost(long argent) {
        return MessageList.success("Vous avez perdu {" + argent + "} " + ((argent < 0) ? "argents" : "argent") + " !");
    }
}
