package net.lonia.core.player.account;

import net.lonia.core.LoniaCore;
import net.lonia.core.database.MySQL;
import net.lonia.core.message.MessageList;
import net.lonia.core.rank.PermissionLevel;
import net.lonia.core.rank.Rank;
import net.lonia.core.rank.Ranks;
import net.lonia.core.utils.Log;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Account extends AbstractData {

    private final MySQL mySQL = LoniaCore.get().getDataBaseManager().getMySQL();

    final UserData userData;
    final DShop dataShop;
    final DModeration dataModeration;
    final DFriend dataFriend;
    final DGroup dataGroup;

    public Account(Player player) {
        this.uuid = player.getUniqueId();
        this.name = player.getDisplayName();

        this.userData = new UserData(uuid);
        this.dataShop = new DShop(uuid);
        this.dataModeration = new DModeration(uuid);
        this.dataFriend = new DFriend(uuid);
        this.dataGroup = new DGroup(uuid);
    }

    public Account(UUID uuid) {
        this.uuid = uuid;
        this.name = getName(uuid);

        this.userData = new UserData(this.uuid);
        this.dataShop = new DShop(uuid);
        this.dataModeration = new DModeration(uuid);
        this.dataFriend = new DFriend(uuid);
        this.dataGroup = new DGroup(uuid);
    }

    public UserData getUserData() {
        return this.userData;
    }

    public DShop getDataShop() {
        return this.dataShop;
    }

    public DModeration getDataModeration() {
        return this.dataModeration;
    }

    public DFriend getDataFriend() {
        return this.dataFriend;
    }

    public DGroup getDataGroup() {
        return this.dataGroup;
    }

    public void updateData() {
        sendDataToMySQL();
        getDataFromMySQL();
    }

    public void onLogin() {

        loadData();

        LoniaCore.get().getAccountManager().getAccounts().add(this);

        userData.setServer(LoniaCore.get().getServer().getServerName());
    }

    public void loadData() {
        if (noAccount())
            setDefaultValues();
        else getDataFromMySQL();
    }

    public void onLogout() {
        userData.setIsInGame(0);
        userData.setConnected(0);
        userData.setServer(null);
        sendDataToMySQL();
        LoniaCore.get().getAccountManager().getAccounts().remove(this);
    }

    public boolean hasPermissionLevel(PermissionLevel permissionLevel) {
        if (!userData.getDataRank().getRank().hasPermissionLevel(permissionLevel))
            getPlayer().sendMessage(MessageList.noPermission());
        return userData.getDataRank().getRank().hasPermissionLevel(permissionLevel);
    }

    private void getDataFromMySQL() {
        mySQL.query(String.format("SELECT * FROM data_players WHERE uuid='%s'", getUUID()), rs -> {
            try {
                if (rs.next()) {
                    userData.getDataRank().setRank(Rank.getByName(rs.getString("grade")));
                    userData.getDataEtoile().setEtoile(Long.parseLong(rs.getString("etoile")));
                    userData.getDataPearl().setPearl(Long.parseLong(rs.getString("argent")));
                    userData.setConnected(rs.getInt("connected"));
                    userData.setIsInGame(rs.getInt("isInGame"));
                }
            } catch (SQLException e) {
                Log.log("§cCRITICAL : " + e.getMessage());
            }
        });

        mySQL.query(String.format("SELECT * FROM data_settings WHERE uuid='%s'", getUUID()), rs -> {
            try {
                if (rs.next()) {
                    userData.getDataSettings().setEnabled_mp(rs.getInt("enabled_mp"));
                    userData.getDataSettings().setEnabled_friends_request(rs.getInt("enabled_friends_request"));
                    userData.getDataSettings().setEnabled_group_request(rs.getInt("enabled_group_request"));
                    userData.getDataSettings().setEnabled_following_group(rs.getInt("enabled_following_group"));
                    userData.getDataSettings().setEnabled_show_particle_effects(rs.getInt("enabled_show_particle_effects"));
                    userData.getDataSettings().setEnabled_show_all_sanctions(rs.getInt("enabled_show_all_sanctions"));

                }
            } catch (SQLException e) {
                Log.log("§cCRITICAL : " + e.getMessage());
            }
        });
    }

    private void sendDataToMySQL() {
        if (noAccount()) {
            createNewAccount();
        } else {
            updateExistingAccount();
        }
    }

    private void createNewAccount() {
        mySQL.update(String.format("INSERT INTO data_players (name, uuid, grade, power, etoile, argent, server, connected, isInGame) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')", getName(), getUUID(), userData.getDataRank().getRank().getName(), userData.getDataRank().getRank().getPower(), userData.getDataEtoile().getEtoile(), userData.getDataPearl().getPearl(), userData.getServer(), userData.getConnected(), 0));

        mySQL.update(String.format("INSERT INTO data_settings " + "(uuid, enabled_mp, enabled_friends_request, enabled_group_request, enabled_following_group, enabled_show_particle_effects, enabled_show_all_sanctions) " + "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s')", getUUID(), userData.getDataSettings().enabled_mp, userData.getDataSettings().enabled_friends_request, userData.getDataSettings().enabled_group_request, userData.getDataSettings().enabled_following_group, userData.getDataSettings().enabled_show_particle_effects, userData.getDataSettings().enabled_show_all_sanctions));
    }

    private void updateExistingAccount() {
        mySQL.update(String.format("UPDATE data_players SET name='%s', grade='%s', power='%s', etoile='%s', argent='%s', server='%s', connected='%s', isInGame='%s' WHERE uuid='%s'", getName(), userData.getDataRank().getRank().getName(), userData.getDataRank().getRank().getPower(), userData.getDataEtoile().getEtoile(), userData.getDataPearl().getPearl(), userData.getServer(), userData.getConnected(), userData.getIsInGame(), getUUID()));

        mySQL.update(String.format("UPDATE data_settings SET enabled_mp='%s', enabled_friends_request='%s', enabled_group_request='%s', enabled_following_group='%s', enabled_show_particle_effects='%s', enabled_show_all_sanctions='%s' WHERE uuid='%s'", userData.getDataSettings().enabled_mp, userData.getDataSettings().enabled_friends_request, userData.getDataSettings().enabled_group_request, userData.getDataSettings().enabled_following_group, userData.getDataSettings().enabled_show_particle_effects, userData.getDataSettings().enabled_show_all_sanctions, getUUID()));
    }

    private boolean hasAccount() {
        AtomicBoolean found = new AtomicBoolean(false);

        mySQL.query("SELECT * FROM data_players WHERE uuid='" + this.uuid + "'", rs -> {
            try {
                if (rs.next()) {
                    found.set(true);
                }
            } catch (SQLException e) {
                Log.log("§cCRITICAL : " + e.getMessage());
            }
        });
        return found.get();
    }

    public boolean noAccount() {
        return !hasAccount();
    }

    private void setDefaultValues() {
        this.userData.getDataRank().setRank(Ranks.JOUEUR);
        this.userData.getDataEtoile().setEtoile(0L);
        this.userData.getDataPearl().setPearl(0L);
        this.userData.setServer(LoniaCore.get().getServer().getServerName());

        this.userData.getDataSettings().setEnabled_mp(1);
        this.userData.getDataSettings().setEnabled_friends_request(1);
        this.userData.getDataSettings().setEnabled_group_request(1);
        this.userData.getDataSettings().setEnabled_following_group(1);
        this.userData.getDataSettings().setEnabled_show_particle_effects(1);
        this.userData.getDataSettings().setEnabled_show_all_sanctions(1);
    }

    public static UUID getUUID(String username) {
        AtomicReference<UUID> uuid = new AtomicReference<>();

        LoniaCore.get().getDataBaseManager().getMySQL().query("SELECT uuid FROM data_players WHERE name='" + username + "'", rs -> {
            try {
                if (rs.next()) {
                    uuid.set(UUID.fromString(rs.getString("uuid")));
                }
            } catch (SQLException e) {
                Log.log("§cCRITICAL : " + e.getMessage());
            }
        });

        return uuid.get();
    }

    public static String getName(UUID uuid) {
        AtomicReference<String> targetName = new AtomicReference<>();

        LoniaCore.get().getDataBaseManager().getMySQL().query("SELECT name FROM data_players WHERE uuid='" + uuid.toString() + "'", rs -> {
            try {
                if (rs.next()) {
                    targetName.set(rs.getString("name"));
                }
            } catch (SQLException e) {
                Log.log("§cCRITICAL : " + e.getMessage());
            }
        });

        return targetName.get();
    }

    public static Rank getRank(UUID uuid) {
        AtomicReference<Rank> rank = new AtomicReference<>();

        LoniaCore.get().getDataBaseManager().getMySQL().query("SELECT grade FROM data_players WHERE uuid='" + uuid.toString() + "'", rs -> {
            try {
                if (rs.next()) {
                    rank.set(Rank.getByName(rs.getString("grade")));
                }
            } catch (SQLException e) {
                Log.log("§cCRITICAL : " + e.getMessage());
            }
        });

        return rank.get();
    }

    public static boolean isConnected(String username) {
        AtomicBoolean isConnected = new AtomicBoolean(false);

        LoniaCore.get().getDataBaseManager().getMySQL().query("SELECT connected FROM data_players WHERE name='" + username + "'", rs -> {
            try {
                if (rs.next()) {
                    isConnected.set(rs.getInt("connected") == 1);
                }
            } catch (SQLException e) {
                Log.log("§cCRITICAL : " + e.getMessage());
            }
        });

        return isConnected.get();
    }
}


