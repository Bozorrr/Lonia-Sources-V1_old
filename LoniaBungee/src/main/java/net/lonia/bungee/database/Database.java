package net.lonia.bungee.database;

import net.lonia.bungee.utils.Log;
import org.apache.commons.dbcp2.BasicDataSource;

public class Database {
    private static MySQL mySQL;

    private static BasicDataSource connectionPool;

    public MySQL getMySQL() {
        return mySQL;
    }

    public void connect() {
        Log.log("§6Connection to MySQL in progress..");
        connectionPool = new BasicDataSource();
        connectionPool.setDriverClassName("com.mysql.cj.jdbc.Driver");
        connectionPool.setUsername("root");
        connectionPool.setPassword("");
        connectionPool.setUrl("jdbc:mysql://localhost:3306/lonia?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true");
        connectionPool.setInitialSize(1);
        connectionPool.setMaxTotal(10);
        mySQL = new MySQL(connectionPool);
        Log.log("§aConnection to MySQL in successful !");
    }

    public void createTables() {
        mySQL.createTablesData_Servers();
        mySQL.createTablesData_Players();
        mySQL.createTablesData_Settings();
        mySQL.createTablesFriends();
        mySQL.createTablesGroups();
        mySQL.createTablesGroupMembers();
        mySQL.createTablesSanctions();
        mySQL.createTablesBans();
        mySQL.createTablesMutes();
        mySQL.createTablesModeration();
        mySQL.createTablesGames();
        mySQL.createTablesPlayersInGame();
    }

}