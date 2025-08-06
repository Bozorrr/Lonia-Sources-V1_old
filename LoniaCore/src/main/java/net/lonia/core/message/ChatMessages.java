package net.lonia.core.message;

public class ChatMessages {
    public static String chatUsage() {
        return MessageList.usage("chat", "clear", "off", "on");
    }

    public static String chatOffUsage() {
        return "§3Utilisation: /chat off (durée s, m, h)";
    }

    public static String chatClear() {
        return "§bL'ensemble du chat a été supprimé.";
    }

    public static String chatAlreadyDisabled() {
        return "§3Erreur: Le chat est déjà désactivé.";
    }

    public static String chatAlreadyEnabled() {
        return "§3Erreur: Le chat est déjà activé.";
    }

    public static String chatEnabled() {
        return "§bLe chat a été à nouveau activé.";
    }

    public static String chatDisabled() {
        return "§9[Lonia] §bLe chat a été désactivé.";
    }

    public static String chatDisabledFor(String time) {
        return "§bLe chat a été temporairement désactivé !\nIl sera de nouveau actif dans §3" + time + "§b.";
    }
}
