package net.lonia.core.message;

public class HelpMessages {
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

    public static String help(int page) {
        int commandsPerPage = 4;
        String[] commands = {
                "/hub §9■",
                "/lobby §9■",
                "/help friends §9■",
                "/help group §9■",
                "/msg §3<pseudo> <message> §9■",
                "/r §3<message> §9■",
                "/ignore §3<pseudo> §o■"
//                "/report §3(Pseudo)\n",
        };

        int totalPages = (int) Math.ceil((double) commands.length / commandsPerPage);

        if (page < 1 || page > totalPages) {
            return helpInvalidPage();
        } else {
            StringBuilder helpMessage = new StringBuilder("§9[Help] ==================== [Help]\n");
            int startIndex = (page - 1) * commandsPerPage;
            int endIndex = Math.min(startIndex + commandsPerPage, commands.length);

            for (int i = startIndex; i < endIndex; i++) {
                helpMessage.append(" §3- §b").append(commands[i]).append("\n");
            }

            helpMessage.append("§3§oPage ").append(page).append(" sur ").append(totalPages);
            helpMessage.append("\n§9[Help] ==================== [Help]");
            return helpMessage.toString();
        }
    }

    public static String helpInvalidPage() {
        return "§3Erreur: Numéro de page incorrecte.";
    }

    public static String helpStaff(int page) {
        int commandsPerPage = 7;

        String[] staffCommands = {
                "/gm",
                "/fly",
                "/vanish",
                "/chat",
                "/mod",
                "/servers",
                "/argent",
                "/etoile",
                "/setspawn",
                "/setrank",
                "/s",
                "/ban",
                "/setupserver"
        };

        int totalPages = (int) Math.ceil((double) staffCommands.length / commandsPerPage);

        if (page < 1 || page > totalPages) {
            return helpInvalidPage();
        } else {
            StringBuilder helpMessage = new StringBuilder("§9[Staff] ==================== [Staff]\n");
            int startIndex = (page - 1) * commandsPerPage;
            int endIndex = Math.min(startIndex + commandsPerPage, staffCommands.length);

            for (int i = startIndex; i < endIndex; i++) {
                helpMessage.append(" §3- §b").append(staffCommands[i]).append("\n");
            }

            helpMessage.append("§3§oPage ").append(page).append(" sur ").append(totalPages);
            helpMessage.append("\n§9[Staff] ==================== [Staff]");
            return helpMessage.toString();
        }
    }
}
