package net.lonia.bungee.player.account;

import net.lonia.bungee.LoniaBungee;
import net.lonia.bungee.database.MySQL;
import net.lonia.bungee.rank.PermissionLevel;
import net.lonia.bungee.rank.Rank;
import net.lonia.bungee.rank.Ranks;
import net.lonia.bungee.utils.Log;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;

import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Account extends AbstractData {

    final MySQL mySQL = LoniaBungee.getInstance().getDataBaseManager().getMySQL();

    final UserData userData;
    final DFriend dataFriend;
    final DGroup dataGroup;

    public Account(UUID uuid) {
        this.uuid = uuid;
        this.name = ProxyServer.getInstance().getPlayer(uuid).getDisplayName();

        this.userData = new UserData(uuid);
        this.dataFriend = new DFriend(uuid);
        this.dataGroup = new DGroup(uuid);
    }

    public UserData getUserData() { return this.userData; }
    public DFriend getDataFriend() { return this.dataFriend; }
    public DGroup getDataGroup() { return this.dataGroup; }

    public void updateData() {
        //sendDataToMySQL();
        getDataFromMySQL();
    }

    public void onLogin() {
        loadData();

        LoniaBungee.getInstance().getAccountManager().getAccounts().add(this);
    }

    public void loadData() {
        if (noAccount())
            setDefaultValues();
        else getDataFromMySQL();
    }

    public void onLogout() {
        LoniaBungee.getInstance().getAccountManager().getAccounts().remove(this);
    }

    public boolean hasPermissionLevel(PermissionLevel permissionLevel) {
        if (!userData.getDataRank().getRank().hasPermissionLevel(permissionLevel))
            getPlayer().sendMessage(new TextComponent("§3Erreur: Vous n'avez pas la permission d'utiliser cette commande."));
        return userData.getDataRank().getRank().hasPermissionLevel(permissionLevel);
    }

    private void getDataFromMySQL() {
        mySQL.query(String.format("SELECT * FROM data_players WHERE uuid='%s'", getUUID()), rs -> {
            try {
                if (rs.next()) {
                    userData.getDataRank().setRank(Rank.getByName(rs.getString("grade")));
                    userData.getDataEtoile().setEtoile(Long.parseLong(rs.getString("etoile")));
                    userData.getDataArgent().setArgent(Long.parseLong(rs.getString("argent")));
                    userData.setConnected(rs.getInt("connected"));
                    userData.setIsInGame(rs.getInt("isInGame"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
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
                }
            } catch (SQLException e) {
                e.printStackTrace();
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
        mySQL.update(String.format("INSERT INTO data_players (name, uuid, grade, power, etoile, argent, server, connected, isInGame) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')", getName(), getUUID(), userData.getDataRank().getRank().getName(), userData.getDataRank().getRank().getPower(), userData.getDataEtoile().getEtoile(), userData.getDataArgent().getArgent(), userData.getServer(), userData.getConnected(), 0));

        mySQL.update(String.format("INSERT INTO data_settings " + "(uuid, enabled_mp, enabled_friends_request, enabled_group_request, enabled_following_group, enabled_show_particle_effects) " + "VALUES ('%s', '%s', '%s', '%s', '%s', '%s')", getUUID(), userData.getDataSettings().enabled_mp, userData.getDataSettings().enabled_friends_request, userData.getDataSettings().enabled_group_request, userData.getDataSettings().enabled_following_group, userData.getDataSettings().enabled_show_particle_effects));
    }

    private void updateExistingAccount() {
        mySQL.update(String.format("UPDATE data_players SET name='%s', grade='%s', power='%s', etoile='%s', argent='%s', server='%s', connected='%s', isInGame='%s' WHERE uuid='%s'", getName(), userData.getDataRank().getRank().getName(), userData.getDataRank().getRank().getPower(), userData.getDataEtoile().getEtoile(), userData.getDataArgent().getArgent(), userData.getServer(), userData.getConnected(), userData.getIsInGame(), getUUID()));

        mySQL.update(String.format("UPDATE data_settings SET enabled_mp='%s', enabled_friends_request='%s', enabled_group_request='%s', enabled_following_group='%s', enabled_show_particle_effects='%s' WHERE uuid='%s'", userData.getDataSettings().enabled_mp, userData.getDataSettings().enabled_friends_request, userData.getDataSettings().enabled_group_request, userData.getDataSettings().enabled_following_group, userData.getDataSettings().enabled_show_particle_effects, getUUID()));
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
        this.userData.getDataArgent().setArgent(0L);
        this.userData.setConnected(1);

        this.userData.getDataSettings().setEnabled_mp(1);
        this.userData.getDataSettings().setEnabled_friends_request(1);
        this.userData.getDataSettings().setEnabled_group_request(1);
        this.userData.getDataSettings().setEnabled_following_group(1);
        this.userData.getDataSettings().setEnabled_show_particle_effects(1);
    }


    public static String getServer(String username) {
        AtomicReference<String> server = new AtomicReference<String>();

        LoniaBungee.getInstance().getDataBaseManager().getMySQL().query("SELECT server FROM data_players WHERE name='" + username + "'", rs -> {
            try {
                if (rs.next()) {
                    server.set(rs.getString("server"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return server.get();
    }

    public static UUID getUUID(String username) {
        AtomicReference<UUID> uuid = new AtomicReference<>();

        LoniaBungee.getInstance().getDataBaseManager().getMySQL().query("SELECT server FROM data_players WHERE name='" + username + "'", rs -> {
            try {
                if (rs.next()) {
                    uuid.set(UUID.fromString(rs.getString("uuid")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return uuid.get();
    }
}


