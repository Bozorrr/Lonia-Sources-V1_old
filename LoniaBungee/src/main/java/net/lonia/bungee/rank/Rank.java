package net.lonia.bungee.rank;


import net.lonia.bungee.LoniaBungee;

public class Rank {

    private static final RankManager rankManager = LoniaBungee.getInstance().getRankManager();

    private final String name;
    private final String prefix;
    private final String color;
    private final int power;
    private final int max_friends;
    private final int max_per_group;
    private final long etoile;
    private final long argent;
    private PermissionLevel permissionLevel;

    public Rank(String name, String prefix, String color, int power, int max_friends, int max_per_group, long etoile, long argent, PermissionLevel permissionLevel) {
        this.name = name;
        this.prefix = prefix;
        this.color = color;
        this.power = power;
        this.max_friends = max_friends;
        this.max_per_group = max_per_group;
        this.etoile = etoile;
        this.argent = argent;
        this.permissionLevel = permissionLevel;
    }

    public static Rank getByName(String name) {
        return rankManager.getRanks().stream().filter(r -> r.getName().equalsIgnoreCase(name)).findAny().orElse(Ranks.JOUEUR);
    }

    public static Rank getByPower(int power) {
        return rankManager.getRanks().stream().filter(r -> r.getPower() == power).findAny().orElse(Ranks.JOUEUR);
    }

    public void createRank() {
        rankManager.getRanks().add(this);
//        LoniaCore.getInstance().getDataBaseManager().getMySQL().query("SELECT * FROM data_ranks WHERE name='" + this.name + "'", rs -> {
//            try {
//                if (!rs.next()) {
//                    LoniaCore.getInstance().getDataBaseManager().getMySQL().update("INSERT INTO data_ranks (name, prefix, color, power, max_friends, max_per_group, etoile, argent) VALUES ('" + this.name + "', '" + this.prefix + "', '" + this.color + "', '" + this.power + "', '" + this.max_friends + "', '" + this.max_per_group + "', '" + this.etoile + "', '" + this.argent + "')");
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        });
    }

    public void delete() {
        rankManager.getRanks().remove(this);

    }


    public String getName() {
        return this.name;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getColor() {
        return this.color;
    }

    public int getPower() {
        return this.power;
    }

    public int getMax_friends() {
        return this.max_friends;
    }

    public int getMax_per_group() {
        return this.max_per_group;
    }

    public long getEtoile() {
        return etoile;
    }

    public long getArgent() {
        return argent;
    }

    public void setPermissionLevel(PermissionLevel permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public boolean hasPermissionLevel(PermissionLevel permissionLevel) {
        return this.permissionLevel.getPower() >= permissionLevel.getPower();
    }

    public PermissionLevel getPermissionLevel() {
        return permissionLevel;
    }

}
