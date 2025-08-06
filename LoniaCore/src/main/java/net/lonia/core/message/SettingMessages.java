package net.lonia.core.message;

public class SettingMessages {
    public static String mpToggle(boolean isEnabled) {
        return isEnabled ? "§9[MP] §3Votre réception de MP est désactivé." : "§9[MP] §3Votre réception de MP est activé.";
    }

    public static String friendsRequestToggle(boolean isEnabled) {
        return isEnabled ? "§9[MP] §3Vous venez de désactiver la réception de demande d'amis." : "§9[MP] §3Vous venez d'activer la réception de demande d'amis.";
    }

    public static String groupRequestToggle(boolean isEnabled) {
        return isEnabled ? "§9[MP] §3Vous venez de désactiver la réception de demande de groupe." : "§9[MP] §3Vous venez d'activer la réception de demande de groupe.";
    }

    public static String followGroupToggle(boolean isEnabled) {
        return isEnabled ? "§9[MP] §3Vous venez de désactiver le suivi du groupe." : "§9[MP] §3Vous venez d'activer le suivi du groupe.";
    }

    public static String particleEffectsToggle(boolean isEnabled) {
        return isEnabled ? "§9[MP] §3Vous venez de désactiver l'affichage des particules." : "§9[MP] §3Vous venez d'activer l'affichage des particules.";
    }
}
