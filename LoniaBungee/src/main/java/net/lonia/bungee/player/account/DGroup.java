package net.lonia.bungee.player.account;


import net.lonia.bungee.LoniaBungee;
import net.lonia.bungee.database.MySQL;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class DGroup extends AbstractData {

    private static final MySQL mySQL = LoniaBungee.getInstance().getDataBaseManager().getMySQL();

    public DGroup(UUID uuid) {
        this.uuid = uuid;
    }

    public void createGroup(String leaderUUID, String leaderName, int maxMembers) {
        mySQL.update("INSERT INTO data_groups (leader_uuid, leader_name, max_members) VALUES ('" + leaderUUID + "', '" + leaderName + "', '" + maxMembers + "')");

        addGroupRequest(leaderUUID, leaderName, leaderUUID, leaderName);
        acceptGroupRequest(leaderUUID, leaderUUID);
    }

    public void deleteGroup(String leaderUUID) {
        String query = String.format("DELETE FROM data_groups WHERE leader_uuid='%s'", leaderUUID);
        mySQL.update(query);

        String query2 = String.format("DELETE FROM group_members WHERE leader_uuid='%s'", leaderUUID);
        mySQL.update(query2);
    }

    public void updateGroup(String leaderUUID, String newLeaderUUID, String newLeaderName, int maxMembers) {

        String query = String.format("UPDATE data_groups SET leader_uuid='%s', leader_name='%s', max_members=%s WHERE leader_uuid='%s'", newLeaderUUID, newLeaderName, maxMembers, leaderUUID);
        mySQL.update(query);

        String query2 = String.format("UPDATE group_members SET leader_uuid='%s', leader_name='%s' WHERE leader_uuid='%s'", newLeaderUUID, newLeaderName, uuid.toString());
        mySQL.update(query2);
    }

    public void addGroupRequest(String leaderUUID, String leaderName, String memberUUID, String memberName) {
        String query = String.format("INSERT INTO group_members (leader_uuid, leader_name, member_uuid, member_name, status) VALUES ('%s', '%s', '%s', '%s', 'pending')", leaderUUID, leaderName, memberUUID, memberName);
        mySQL.update(query);
    }

    public void removeGroupRequest(String leaderUUID, String memberUUID) {
        String query = String.format("DELETE FROM group_members WHERE leader_uuid='%s' AND member_uuid='%s' AND status='pending'", leaderUUID, memberUUID);
        mySQL.update(query);
    }

    public void acceptGroupRequest(String leaderUUID, String memberUUID) {
        String query = String.format("UPDATE group_members SET status='accepted' WHERE leader_uuid='%s' AND member_uuid='%s'", leaderUUID, memberUUID);
        mySQL.update(query);
    }

    public void removeGroupMember(String leaderUUID, String memberUUID) {
        String query = String.format("DELETE FROM group_members WHERE leader_uuid='%s' AND member_uuid='%s' AND status='accepted'", leaderUUID, memberUUID);
        mySQL.update(query);
    }

    public void removeGroupMemberByName(String leaderUUID, String memberName) {
        String query = String.format("DELETE FROM group_members WHERE leader_uuid='%s' AND member_name='%s' AND status='accepted'", leaderUUID, memberName);
        mySQL.update(query);
    }

    public static List<String> getMembersList(String leaderUUID) {
        List<String> members = new ArrayList<>();

        String query = String.format("SELECT member_name FROM group_members WHERE leader_uuid='%s' AND status='accepted'", leaderUUID);

        mySQL.query(query, rs -> {
            try {
                while (rs.next()) {
                    String memberName = rs.getString("member_name");
                    members.add(memberName);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return members;
    }

    public static List<String> getGroupRequests(String targetUUID) {
        List<String> groupsRequest = new ArrayList<>();

        String query = String.format("SELECT leader_name FROM group_members WHERE member_uuid='%s' AND status='pending'", targetUUID);

        mySQL.query(query, rs -> {
            try {
                while (rs.next()) {
                    String data_groups = rs.getString("leader_name");
                    groupsRequest.add(data_groups);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return groupsRequest;
    }

    public static String getLeaderUUID(String memberUUID, String leaderName) {
        final String[] leaderUUID = {null};

        mySQL.query("SELECT leader_uuid FROM group_members WHERE member_uuid='" + memberUUID + "' AND    leader_name='" + leaderName + "'", rs -> {
            try {
                while (rs.next()) {
                    leaderUUID[0] = rs.getString("leader_uuid");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return leaderUUID[0];
    }

    public static String getLeaderName(String memberUUID) {
        final String[] leaderName = {null};

        mySQL.query("SELECT leader_name FROM group_members WHERE member_uuid='" + memberUUID + "'", rs -> {
            try {
                while (rs.next()) {
                    leaderName[0] = rs.getString("leader_name");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return leaderName[0];
    }

    public int getMaxMembers(String leaderUUID) {
        AtomicReference<Integer> max = new AtomicReference<>();

        mySQL.query("SELECT max_members FROM data_groups WHERE leader_uuid='" + leaderUUID + "'", rs -> {
            try {
                if (rs.next()) {
                    max.set(rs.getInt("max_members"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return max.get();
    }

    public static boolean isInGroup(String memberUUID) {
        AtomicBoolean isInGroup = new AtomicBoolean(false);

        mySQL.query(String.format("SELECT * FROM group_members WHERE member_uuid='%s'", memberUUID), rs -> {
            try {
                if (rs.next()) {
                    isInGroup.set(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return isInGroup.get();
    }

    public ProxiedPlayer getPlayer(UUID uuid) {
        return ProxyServer.getInstance().getPlayer(uuid);
    }

    public ProxiedPlayer getPlayer(String name) {
        return ProxyServer.getInstance().getPlayer(uuid);
    }
}
