package net.lonia.core.database;

import net.lonia.core.utils.Log;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Function;

public class MySQL {
    private final BasicDataSource connectionPool;

    public MySQL(BasicDataSource connectionPool) {
        this.connectionPool = connectionPool;
    }

    public Connection getConnection() throws SQLException {
        return this.connectionPool.getConnection();
    }

    public void update(String qry) {
        try (Connection c = getConnection(); PreparedStatement s = c.prepareStatement(qry)) {
            s.executeUpdate();
        } catch (Exception e) {
            Log.log("§cCRITICAL : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Object query(String qry, Function<ResultSet, Object> consumer) {
        try (Connection c = getConnection(); PreparedStatement s = c.prepareStatement(qry); ResultSet rs = s.executeQuery()) {
            return consumer.apply(rs);
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public void query(String qry, Consumer<ResultSet> consumer) {
        try (Connection c = getConnection(); PreparedStatement s = c.prepareStatement(qry); ResultSet rs = s.executeQuery()) {
            consumer.accept(rs);
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public void createTableID(String table, String values) {
        update("CREATE TABLE IF NOT EXISTS " + table + " (`#` INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " + values + ")");
        log("§aCreation of the missing '" + table + "' table");
    }

    public void createTablesData_Servers() {
        createTableID("data_servers", "id int, name VARCHAR(255), bungee_name VARCHAR(255), type VARCHAR(255), status VARCHAR(255), connected_player INT, enabled_chat INT");
    }

    public void createTablesData_Players() {
        createTableID("data_players", "name VARCHAR(255), uuid VARCHAR(255), grade VARCHAR(255), power INT, etoile BIGINT, argent BIGINT, server TEXT, connected INT, isInGame INT");
    }

    public void createTablesData_Settings() {
        createTableID("data_settings", "uuid VARCHAR(255), enabled_mp INT, enabled_friends_request INT, enabled_group_request INT, enabled_following_group INT, enabled_show_particle_effects INT, enabled_show_all_sanctions INT");
    }

    public void createTablesFriends() {
        createTableID("friend", "player_uuid VARCHAR(255), player_name VARCHAR(255), friend_uuid VARCHAR(255), friend_name VARCHAR(255), status VARCHAR(255)");
    }


    public void createTablesGroups() {
        createTableID("data_groups", "leader_uuid VARCHAR(255), leader_name VARCHAR(255), max_members INT");
    }

    public void createTablesGroupMembers() {
        createTableID("group_members", "leader_uuid VARCHAR(255), leader_name VARCHAR(255), member_uuid VARCHAR(255), member_name VARCHAR(255), status VARCHAR(255)");
    }

    public void createTablesSanctions() {
        createTableID("sanctions", "uuid VARCHAR(255), pseudo VARCHAR(255), type VARCHAR(50), duration BIGINT, date DATETIME, moderator VARCHAR(255), reason TEXT, message TEXT");
    }

    public void createTablesBans() {
        createTableID("ban", "uuid VARCHAR(255), pseudo VARCHAR(255), duration BIGINT, date DATETIME, moderator VARCHAR(255), reason TEXT, status TEXT");
    }

    public void createTablesMutes() {
        createTableID("mute", "uuid VARCHAR(255), pseudo VARCHAR(255), duration BIGINT, date DATETIME, moderator VARCHAR(255), reason TEXT, message TEXT, status TEXT");
    }

    public void createTablesModeration() {
        createTableID("moderation", "uuid VARCHAR(255), pseudo VARCHAR(255)");
    }

    public void createTablesGames() {createTableID("games", "type VARCHAR(50), status VARCHAR(50), player_count TINYINT, start_time DATETIME, end_time DATETIME");}

    public void createTablesPlayersInGame() {createTableID("playersingame", "uuid VARCHAR(255), game_id TINYINT, team VARCHAR(50), kills TINYINT, deaths TINYINT");}


    private void log(String msg) {
        Log.log(msg);
    }
}