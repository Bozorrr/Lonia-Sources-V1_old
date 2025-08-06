package net.lonia.core.rank;

import java.util.ArrayList;
import java.util.List;

public class Ranks {

    public static final Rank FONDATEUR = new Rank("Fondateur", "§4Fondateur ", "§4", 10, 20, 20, 0, 0, PermissionLevel.FONDATEUR);
    public static final Rank ADMIN = new Rank("Admin", "§4Admin ", "§4", 11, 20, 20, 0, 0, PermissionLevel.ADMIN);
    public static final Rank RESPONSSABLE = new Rank("Responsable", "§cResponsable ", "§c", 12, 20, 18, 0, 0, PermissionLevel.RESPONSSABLE);
    public static final Rank MODERATEUR = new Rank("Modérateur", "§cModérateur ", "§c", 13, 20, 17, 0, 0, PermissionLevel.MODERATEUR);
    public static final Rank MODERATEUR_CHAT = new Rank("Modérateur-chat", "§6Modérateur ", "§6", 14, 20, 17, 0, 0, PermissionLevel.MODERATEUR_CHAT);
    public static final Rank CONSTRUCTEUR = new Rank("Constructeur", "§6Constructeur ", "§6", 15, 20, 17, 0, 0, PermissionLevel.CONSTRUCTEUR);
    public static final Rank YOUTUBEUR = new Rank("Youtubeur", "§5Youtubeur ", "§5", 16, 19, 12, 0, 0, PermissionLevel.YOUTUBEUR);
    public static final Rank LEGENDY = new Rank("LégendeY", "§eLégende ", "§e", 17, 16, 8, 4000, 15, PermissionLevel.LEGEND);
    public static final Rank LEGENDV = new Rank("LégendeV", "§dLégende ", "§d", 18, 16, 8, 4000, 15, PermissionLevel.LEGEND);
    public static final Rank HEROS = new Rank("Héros", "§aHéros ", "§a", 19, 16, 8, 4000, 15, PermissionLevel.HEROS);
    public static final Rank VIPP = new Rank("VIP+", "§2VIP§a+ §2", "§2", 20, 16, 8, 4000, 15, PermissionLevel.VIPP);
    public static final Rank VIP = new Rank("VIP", "§bVIP ", "§b", 21, 12, 5, 2500, 9, PermissionLevel.VIP);
    public static final Rank MINIVIP = new Rank("Mini-VIP", "§9Mini-VIP ", "§9", 22, 7, 3, 1000, 3, PermissionLevel.MINIVIP);
    public static final Rank JOUEUR = new Rank("Joueur", "§7Joueur ", "§7", 23, 3, 2, 0, 0, PermissionLevel.JOUEUR);


    public static final Rank ORANGE = new Rank("Orange", "§6Orange ", "§6", 50, 3, 2, 0, 0, null);
    public static final Rank VIOLET = new Rank("Violet", "§5Violet ", "§5", 51, 3, 2, 0, 0, null);
    public static final Rank RANDOM = new Rank("Aléatoire", "§f ", "§f", 52, 3, 2, 0, 0, null);

    private static final List<Rank> ranks = new ArrayList<>();

    public void createRanks() {
        ranks.add(FONDATEUR);
        ranks.add(ADMIN);
        ranks.add(RESPONSSABLE);
        ranks.add(MODERATEUR);
        ranks.add(MODERATEUR_CHAT);
        ranks.add(CONSTRUCTEUR);
        ranks.add(YOUTUBEUR);
        ranks.add(LEGENDY);
        ranks.add(LEGENDV);
        ranks.add(HEROS);
        ranks.add(VIPP);
        ranks.add(VIP);
        ranks.add(MINIVIP);
        ranks.add(JOUEUR);

        ranks.add(ORANGE);
        ranks.add(VIOLET);
        ranks.add(RANDOM);

        ranks.forEach(Rank::createRank);
    }

    public void removeRanks() {
        ranks.forEach(Rank::delete);
    }

    public static List<Rank> getRanks() {
        return ranks;
    }
}
