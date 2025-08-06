package net.lonia.core.message;

public class MessageList {

    public static String error(String message) {
        return "§3Erreur: " + message.replaceAll("\\{([^}]+)}", "§b$1§3");
    }

    public static String usage(String command, String... subcommands) {
        StringBuilder usageMessage = new StringBuilder("§3Utilisation: \n");

        for (String subcommand : subcommands) {
            usageMessage.append("§3- §b/").append(command).append(" ").append(subcommand).append("\n");
        }
        return usageMessage.toString();
    }

    public static String success(String message) {
        return "§b" + message.replaceAll("\\{([^}]+)}", "§3$1§b");
    }

    public static String prefix(String prefix) {
        return "§9[" + prefix + "] ";
    }

    public static String noPermission() {
        return "§3Erreur: Vous n'avez pas la permission d'utiliser cette commande.";
    }

    public static String commandNotAccessibleHere() {
        return error("Impossible d'utiliser cette commande ici.");
    }

    public static String serversAlreadyOnServer(String server) {
        return error("Vous êtes déjà sur le serveur {" + server + "}.");
    }

    public static String serversConnectedToServer(String server) {
        return success("Vous avez été connecté sur {" + server + "}.");
    }

    public static String serversOfflineServer() {
        return error("Le serveur sur lequel vous avez essayé de vous connecter n'est pas en ligne.");
    }

    public static String setSpawnSuccessful() {
        return "§9[Staff] §d[Admin syst.] §bLe spawn a été défini à cet endroit.";
    }

    public static String setServerTypeSuccessful(String servertype) {
        return "§9[Staff] §d[Admin syst.] §bLe type de serveur a été défini sur §3" + servertype + "§b.";
    }

    public static String pvpNotEnabled() {
        return error("Le PvP n'est pas activé ici.");
    }

    public static String discordMessage() {
        return success("Rejoignez-nous sur Discord à : §9§n§ohttps://discord.gg/lonia");
    }

    public static String hubAlreadyOnHub() {
        return error("Vous êtes déjà connecté au hub.");
    }

    public static String hubConnectToHub() {
        return success("Vous avez été téléporté au hub.");
    }

    public static String lobbyAlreadyOnLobby() {
        return error("Vous êtes déjà connecté au lobby.");
    }

    public static String lobbyConnectToLobby() {
        return success("Vous avez été téléporté au lobby.");
    }

    public static String pluginsMessage() {
        String[] internalPlugins = {
                "LoniaCore",
                "LoniaHub",
                "LoniaGUI",
                "LoniaModeration",
        };

        String[] externalPlugins = {
                "worldedit"
        };

        StringBuilder helpMessage = new StringBuilder("§9Voici la liste de nos plugins:\n");

        for (String str : internalPlugins) {
            helpMessage.append(" §3- §b").append(str).append("\n");
        }

        helpMessage.append("§9Plugins tiers\n");

        for (String str : externalPlugins) {
            helpMessage.append(" §3- §b").append(str).append("\n");
        }

        return helpMessage.toString();

    }

    public static String siteMessage() {
        return error("Le site n'est pas encore accessible.");
    }

    public static String spawnTeleport() {
        return success("Vous avez été téléporté au centre du spawn.");
    }

    public static String staffVanishOn() {
        return success("Vous êtes désormais invisible pour tous les joueurs sauf le staff !");
    }

    public static String staffVanishOff() {
        return success("Vous êtes désormais visible pour tout le monde !");
    }

    public static String notEnoughEtoile() {
        return error("Vous ne possédez pas assez d'étoiles !");
    }

    public static String notEnoughArgent() {
        return error("Vous ne possédez pas assez d'argent !");
    }

    public static String lowerRank() {
        return error("Vous possédez déjà un grade supérieur à celui-ci.");
    }

    public static String gamemodeUsage() {
        return "§3Utilisation: \n \n§3- §b/gamemode 0 ou s (Survie) \n§3- §b/gamemode 1 ou c (Créatif) \n§3- §b/gamemode 2 ou a (Aventure) \n§3- §b/gamemode 3 ou spec (Spectateur)";
    }

    public static String changeGamemode(String gamemode) {
        return "§bVous êtes à présent en §3" + gamemode + "§b.";
    }


    public static String invalidOption() {
        return "§9[Boutique]" + error("Cette option n'est pas disponible pour le moment.");
    }

    public static String playerNotConnected() {
        return "§3Erreur: Ce joueur n'est pas connecté.";
    }

    public static String serverShutDown() {
        return "§cLe serveur sur lequel vous étiez a été éteint !";
    }

    public static String waitBetweenMessages() {
        return "§9[Message] §3Veuillez patienter entre chaque message.";
    }
}
