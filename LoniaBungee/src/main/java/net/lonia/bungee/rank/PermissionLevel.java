package net.lonia.bungee.rank;

public enum PermissionLevel {

    FONDATEUR("Fondateur", 14),
    ADMIN("Développeur", 13),
    RESPONSSABLE("Responsable", 12),
    MODERATEUR("Modérateur", 11),
    MODERATEUR_CHAT("Modérateur-chat", 10),
    CONSTRUCTEUR("Constructeur", 9),
    ANIMATEUR("Animateur", 8),
    YOUTUBEUR("Youtubeur", 7),
    LEGEND("Légende", 6),
    HEROS("Héros", 5),
    VIPP("VIP+", 4),
    VIP("VIP", 3),
    MINIVIP("MiniVIP", 2),
    JOUEUR("Joueur", 1);

    private final String name;
    private final int power;

    PermissionLevel(String name, int power) {
        this.name = name;
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }
}
