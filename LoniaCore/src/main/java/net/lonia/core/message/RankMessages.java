package net.lonia.core.message;

public class RankMessages {
    public static String rankUse() {
        return MessageList.usage("setrank", "<pseudo> <grade>");
    }

    public static String rankSuccessful(String player, String rank) {
        return "§9[Grade] " + MessageList.success("Le joueur {" + player + "} est passé {" + rank + "}.");
    }

    public static String rankRankChangeMessage(String rank) {
        return "§9[Grade] " + MessageList.success("Vous êtes maintenant passé {" + rank + "}. Félicitations à vous !");
    }
}
