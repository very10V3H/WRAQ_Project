package com.very.wraq.files.dataBases;

import net.minecraft.world.entity.player.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public static Connection getDatabaseConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/wraq",
                "root", "123456");
    }

    public static void CreatePlayerDataTable(Connection connection) throws SQLException {
        if (!containsTable("playerData")) {
            String sql = "create table playerData(name VARCHAR(100), " +
                    "PRIMARY KEY(name))ENGINE=InnoDB DEFAULT CHARSET=utf8;";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        }
    }

    public static void CreateWorldDataTable(Connection connection) throws SQLException {
        if (!containsTable("world")) {
            String sql = "create table world(world_key VARCHAR(100), world_value VARCHAR(100), " +
                    "PRIMARY KEY(world_key))ENGINE=InnoDB DEFAULT CHARSET=utf8;";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        }
    }

    public static void put(String worldKey, String worldValue) throws SQLException {
        Connection connection = getDatabaseConnection();
        Statement statement = connection.createStatement();
        if (containsWorldKey(worldKey)) {
            String sql2 = "update world set " + "world_value" + " = '" + worldValue + "' where world_key = '" + worldKey + "';";
            statement.executeUpdate(sql2);
        }
        else {
            String sql1 = "INSERT into world (world_key,world_value) values ('" + worldKey + "', '" + worldValue + "');";
            statement.executeUpdate(sql1);
        }
        statement.close();
        connection.close();
    }

    public static void put(String playerName, String key, String value) throws SQLException {
        Connection connection = getDatabaseConnection();
        Statement statement = connection.createStatement();
        if (!containsKey(key)) {
            String sql0 = "alter table playerData add " + key + " VARCHAR(100)";
            statement.executeUpdate(sql0);
        }
        if (containsPlayer(playerName)) {
            String sql2 = "update playerData set " + key + " = '" + value + "' where name = '" + playerName + "';";
            statement.executeUpdate(sql2);
        }
        else {
            String sql1 = "INSERT into playerData (name," + key + ") values ('" + playerName + "', '" + value + "');";
            statement.executeUpdate(sql1);
        }
        statement.close();
        connection.close();
    }

    public static void put(Statement statement, String playerName, String key, String value) throws SQLException {
        if (!containsKey(key)) {
            String sql0 = "alter table playerData add " + key + " VARCHAR(100)";
            statement.executeUpdate(sql0);
        }
        if (containsPlayer(playerName)) {
            String sql2 = "update playerData set " + key + " = '" + value + "' where name = '" + playerName + "';";
            statement.executeUpdate(sql2);
        }
        else {
            String sql1 = "INSERT into playerData (name," + key + ") values ('" + playerName + "', '" + value + "');";
            statement.executeUpdate(sql1);
        }
    }

    public static void put(Player player, String key, String value) throws SQLException {
        String playerName = player.getName().getString();
        Connection connection = getDatabaseConnection();
        Statement statement = connection.createStatement();
        if (!containsKey(key)) {
            String sql0 = "alter table playerData add " + key + " VARCHAR(100)";
            statement.executeUpdate(sql0);
        }
        if (containsPlayer(playerName)) {
            String sql2 = "update playerData set " + key + " = '" + value + "' where name = '" + playerName + "';";
            statement.executeUpdate(sql2);
        }
        else {
            String sql1 = "INSERT into playerData (name," + key + ") values ('" + playerName + "', '" + value + "');";
            statement.executeUpdate(sql1);
        }
        statement.close();
        connection.close();
    }

    public static String get(String playerName, String key) throws SQLException {
        String result = null;
        Connection connection = getDatabaseConnection();
        Statement statement = connection.createStatement();
        if (containsPlayer(playerName) && containsKey(key)) {
            String sql = "select * from playerData where name = '" + playerName + "';";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                result = resultSet.getString(key);
            }
        }
        String resultString = result;
        statement.close();
        connection.close();
        return resultString;
    }

    public static String get(Player player, String key) throws SQLException {
        String playerName = player.getName().getString();
        String result = null;
        Connection connection = getDatabaseConnection();
        Statement statement = connection.createStatement();
        if (containsPlayer(playerName) && containsKey(key)) {
            String sql = "select * from playerData where name = '" + playerName + "';";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                result = resultSet.getString(key);
            }
        }
        String resultString = result;
        statement.close();
        connection.close();
        return resultString;
    }

    public static String get(Statement statement, Player player, String key) throws SQLException {
        String playerName = player.getName().getString();
        String result = null;
        if (containsPlayer(playerName) && containsKey(key)) {
            String sql = "select * from playerData where name = '" + playerName + "';";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                result = resultSet.getString(key);
            }
        }
        return result;
    }

    public static String get(String worldKey) throws SQLException {
        String result = "null";
        Connection connection = getDatabaseConnection();
        Statement statement = connection.createStatement();
        if (containsWorldKey(worldKey)) {
            String sql = "select * from world where world_key = '" + worldKey + "';";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                result = resultSet.getString("world_value");
            }
        }
        String resultString = result;
        statement.close();
        connection.close();
        return resultString;
    }

    public static List<String> getKeyList(String keyName) throws SQLException {
        if (containsKey(keyName)) {
            Connection connection = getDatabaseConnection();
            Statement statement = connection.createStatement();
            List<String> list = new ArrayList<>();
            String sql = "select * from playerData;";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String s = resultSet.getString(keyName);
                if (!list.contains(s)) list.add(resultSet.getString(keyName));
            }
            statement.close();
            connection.close();
            return list;
        }
        return null;
    }


    public static boolean containsTable(String table) throws SQLException {
        Connection connection = getDatabaseConnection();
        String sql = "select count(*) from information_schema.columns where table_schema = 'wraq' and table_name = '" + table + "';";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        boolean result = resultSet.getInt(1) > 0;
        preparedStatement.close();
        connection.close();
        return result;
    }

    public static boolean containsKey(String key) throws SQLException {
        Connection connection = getDatabaseConnection();
        String sql = "select count(*) from information_schema.columns where table_schema = 'wraq' and table_name = 'playerData' and column_name = '" + key + "'";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        boolean result = resultSet.getInt(1) > 0;
        preparedStatement.close();
        connection.close();
        return result;
    }

    public static boolean containsPlayer(String playerName) throws SQLException {
        Connection connection = getDatabaseConnection();
        String sql = "select count(*) from playerData where name = '" + playerName + "';";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        boolean result = resultSet.getInt(1) > 0;
        preparedStatement.close();
        connection.close();
        return result;
    }

    public static boolean containsWorldKey(String worldKey) throws SQLException {
        Connection connection = getDatabaseConnection();
        String sql = "select count(*) from world where world_key = '" + worldKey + "';";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        boolean result = resultSet.getInt(1) > 0;
        preparedStatement.close();
        connection.close();
        return result;
    }
}
