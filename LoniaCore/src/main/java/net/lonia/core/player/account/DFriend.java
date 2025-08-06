package net.lonia.core.player.account;


import net.lonia.core.LoniaCore;
import net.lonia.core.database.MySQL;
import net.lonia.core.utils.Log;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DFriend extends AbstractData {

    private final MySQL mySQL = LoniaCore.get().getDataBaseManager().getMySQL();

    public DFriend(UUID uuid) {
        this.uuid = uuid;
    }

    public void addFriendRequest(UUID friendUUID) {
        String query1 = String.format("INSERT INTO friend (player_uuid, player_name, friend_uuid, friend_name, status) VALUES ('%s', '%s', '%s', '%s', 'pending')", uuid.toString(), getPlayer(uuid).getDisplayName(), friendUUID.toString(), getPlayer(friendUUID).getDisplayName());
        mySQL.update(query1);

        String query2 = String.format("INSERT INTO friend (player_uuid, player_name, friend_uuid, friend_name, status) VALUES ('%s', '%s', '%s', '%s', 'pending')", friendUUID.toString(), getPlayer(friendUUID).getDisplayName(), uuid.toString(), getPlayer(uuid).getDisplayName());
        mySQL.update(query2);
    }

    public void addFriendRequest(String friendName) {
        String query1 = String.format("INSERT INTO friend (player_uuid, player_name, friend_uuid, friend_name, status) VALUES ('%s', '%s', '%s', '%s', 'pending')", uuid.toString(), getPlayer(uuid).getDisplayName(), getPlayer(friendName).getUniqueId().toString(), friendName);
        mySQL.update(query1);

        String query2 = String.format("INSERT INTO friend (player_uuid, player_name, friend_uuid, friend_name, status) VALUES ('%s', '%s', '%s', '%s', 'pending')", getPlayer(friendName).getUniqueId().toString(), friendName, uuid.toString(), getPlayer(uuid).getDisplayName());
        mySQL.update(query2);
    }

    public void removeFriendRequest(UUID friendUUID) {
        String query1 = String.format("DELETE FROM friend WHERE player_uuid='%s' AND friend_uuid='%s' AND status='pending'", uuid.toString(), friendUUID.toString());
        mySQL.update(query1);

        String query2 = String.format("DELETE FROM friend WHERE player_uuid='%s' AND friend_uuid='%s' AND status='pending'", friendUUID.toString(), uuid.toString());
        mySQL.update(query2);
    }

    public void removeFriendRequest(String friendName) {
        String query1 = String.format("DELETE FROM friend WHERE player_name='%s' AND friend_name='%s' AND status='pending'", name, friendName);
        mySQL.update(query1);

        String query2 = String.format("DELETE FROM friend WHERE player_name='%s' AND friend_name='%s' AND status='pending'", friendName, name);
        mySQL.update(query2);
    }

    public void addFriend(UUID friendUUID) {
        String query1 = String.format("UPDATE friend SET status='accepted' WHERE player_uuid='%s' AND friend_uuid='%s'", uuid.toString(), friendUUID.toString());
        mySQL.update(query1);

        String query2 = String.format("UPDATE friend SET status='accepted' WHERE player_uuid='%s' AND friend_uuid='%s'", friendUUID.toString(), uuid.toString());
        mySQL.update(query2);
    }

    public void addFriend(String friendName) {
        String query1 = String.format("UPDATE friend SET status='accepted' WHERE player_name='%s' AND friend_name='%s'", name, friendName);
        mySQL.update(query1);

        String query2 = String.format("UPDATE friend SET status='accepted' WHERE player_name='%s' AND friend_name='%s'", friendName, name);
        mySQL.update(query2);
    }

    public void removeFriend(UUID friendUUID) {
        String query1 = String.format("DELETE FROM friend WHERE player_uuid='%s' AND friend_uuid='%s' AND status='accepted'", uuid.toString(), friendUUID.toString());
        mySQL.update(query1);

        String query2 = String.format("DELETE FROM friend WHERE player_uuid='%s' AND friend_uuid='%s' AND status='accepted'", friendUUID.toString(), uuid.toString());
        mySQL.update(query2);
    }

    public void removeFriend(String friendName) {
        String query1 = String.format("DELETE FROM friend WHERE player_uuid='%s' AND friend_name='%s' AND status='accepted'", uuid.toString(), friendName);
        mySQL.update(query1);

        String query2 = String.format("DELETE FROM friend WHERE player_name='%s' AND friend_uuid='%s' AND status='accepted'", friendName, uuid.toString());
        mySQL.update(query2);
    }


    public List<String> getFriendNames() {
        List<String> acceptedfriends = new ArrayList<>();

        String query1 = String.format("SELECT friend_name FROM friend WHERE player_uuid='%s' AND status='accepted'", uuid.toString());

        mySQL.query(query1, rs -> {
            try {
                while (rs.next()) {
                    String friendName = rs.getString("friend_name");
                    acceptedfriends.add(friendName);

                }
            } catch (SQLException e) {
                Log.log("§cCRITICAL : " + e.getMessage());
            }
        });

        return acceptedfriends;
    }

    public List<String> getRequestedFriend() {
        List<String> requestedFriends = new ArrayList<>();

        String query1 = String.format("SELECT friend_name FROM friend WHERE player_uuid='%s' AND status='pending'", uuid.toString());

        mySQL.query(query1, rs -> {
            try {
                while (rs.next()) {
                    String friendName = rs.getString("friend_name");
                    requestedFriends.add(friendName);

                }
            } catch (SQLException e) {
                Log.log("§cCRITICAL : " + e.getMessage());
            }
        });

        return requestedFriends;
    }

    public Player getPlayer() {
        return getPlayer(uuid);
    }

    public Player getPlayer(UUID uuid) {
        return Bukkit.getPlayer(uuid);
    }

    public Player getPlayer(String name) {
        return Bukkit.getPlayer(uuid);
    }
}

