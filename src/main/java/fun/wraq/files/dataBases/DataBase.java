package fun.wraq.files.dataBases;

import fun.wraq.common.Compute;
import fun.wraq.common.util.Utils;
import fun.wraq.files.MarketItemInfo;
import fun.wraq.files.MarketPlayerInfo;
import fun.wraq.files.dataBases.DBConnection;
import fun.wraq.process.system.WorldRecordInfo;
import net.minecraft.world.entity.player.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBase {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getDatabaseConnection() throws SQLException {
        return DBConnection.getDatabaseConnection();
    }

    public static void CreatePlayerDataTable(Connection connection) throws SQLException {
        if (!containsTable("playerdata1")) {
            String sql = "create table playerdata1(name VARCHAR(100), " +
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
        } else {
            String sql1 = "INSERT into world (world_key,world_value) values ('" + worldKey + "', '" + worldValue + "');";
            statement.executeUpdate(sql1);
        }
        statement.close();
    }

    public static void put(Statement statement, String worldKey, String worldValue) throws SQLException {
        if (containsWorldKey(worldKey)) {
            String sql2 = "update world set " + "world_value" + " = '" + worldValue + "' where world_key = '" + worldKey + "';";
            statement.executeUpdate(sql2);
        } else {
            String sql1 = "INSERT into world (world_key,world_value) values ('" + worldKey + "', '" + worldValue + "');";
            statement.executeUpdate(sql1);
        }
    }

    public static void put(String playerName, String key, String value) throws SQLException {
        Connection connection = getDatabaseConnection();
        Statement statement = connection.createStatement();
        if (!containsKey(key)) {
            String sql0 = "alter table playerdata1 add " + key + " VARCHAR(100)";
            statement.executeUpdate(sql0);
        }
        if (containsPlayer(playerName)) {
            String sql2 = "update playerdata1 set " + key + " = '" + value + "' where name = '" + playerName + "';";
            statement.executeUpdate(sql2);
        } else {
            String sql1 = "INSERT into playerdata1 (name," + key + ") values ('" + playerName + "', '" + value + "');";
            statement.executeUpdate(sql1);
        }
        statement.close();
    }

    public static void put(Statement statement, String playerName, String key, String value) throws SQLException {
        if (!containsKey(key)) {
            String sql0 = "alter table playerdata1 add " + key + " VARCHAR(100)";
            statement.executeUpdate(sql0);
        }
        if (containsPlayer(playerName)) {
            String sql2 = "update playerdata1 set " + key + " = '" + value + "' where name = '" + playerName + "';";
            statement.executeUpdate(sql2);
        } else {
            String sql1 = "INSERT into playerdata1 (name," + key + ") values ('" + playerName + "', '" + value + "');";
            statement.executeUpdate(sql1);
        }
    }

    public static void put(Player player, String key, String value) throws SQLException {
        String playerName = player.getName().getString();
        Connection connection = getDatabaseConnection();
        Statement statement = connection.createStatement();
        if (!containsKey(key)) {
            String sql0 = "alter table playerdata1 add " + key + " VARCHAR(100)";
            statement.executeUpdate(sql0);
        }
        if (containsPlayer(playerName)) {
            String sql2 = "update playerdata1 set " + key + " = '" + value + "' where name = '" + playerName + "';";
            statement.executeUpdate(sql2);
        } else {
            String sql1 = "INSERT into playerdata1 (name," + key + ") values ('" + playerName + "', '" + value + "');";
            statement.executeUpdate(sql1);
        }
        statement.close();
    }

    public static void put(Player player, String key, String value, String tableName) throws SQLException {
        String playerName = player.getName().getString();
        Connection connection = getDatabaseConnection();
        Statement statement = connection.createStatement();
        put(statement, playerName, key, value, tableName);
        statement.close();
    }

    public static void put(Statement statement, Player player, String key, String value, String tableName) throws SQLException {
        String playerName = player.getName().getString();
        put(statement, playerName, key, value, tableName);
    }

    public static void put(Statement statement, String playerName, String key, String value, String tableName) throws SQLException {
        if (!containsKey(key, tableName)) {
            String sql0 = "alter table " + tableName + " add " + key + " VARCHAR(100)";
            statement.executeUpdate(sql0);
        }
        if (containsPlayer(playerName, tableName)) {
            String sql2 = "update " + tableName + " set " + key + " = '" + value + "' where name = '" + playerName + "';";
            statement.executeUpdate(sql2);
        } else {
            String sql1 = "INSERT into " + tableName + " (name," + key + ") values ('" + playerName + "', '" + value + "');";
            statement.executeUpdate(sql1);
        }
    }

    public static void put(Connection connection, String playerName, String key, String value, String tableName) throws SQLException {
        if (!containsKey(key, tableName)) {
            String sql0 = "alter table ? add ? VARCHAR(100)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql0);
            preparedStatement.setString(1, tableName);
            preparedStatement.setString(2, key);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        if (containsPlayer(playerName, tableName)) {
            String sql2 = "update ? set ? = ? where name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.setString(1, tableName);
            preparedStatement.setString(2, key);
            preparedStatement.setString(3, value);
            preparedStatement.setString(4, playerName);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } else {
            String sql1 = "insert into ? (name, ?) values (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.setString(1, tableName);
            preparedStatement.setString(2, key);
            preparedStatement.setString(3, playerName);
            preparedStatement.setString(4, value);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
    }


    public static String get(String playerName, String key) throws SQLException {
        String result = null;
        Connection connection = getDatabaseConnection();
        Statement statement = connection.createStatement();
        if (containsPlayer(playerName) && containsKey(key)) {
            String sql = "select * from playerdata1 where name = '" + playerName + "';";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                result = resultSet.getString(key);
            }
        }
        String resultString = result;
        statement.close();
        return resultString;
    }

    public static String get(Player player, String key) throws SQLException {
        String playerName = player.getName().getString();
        String result = null;
        Connection connection = getDatabaseConnection();
        Statement statement = connection.createStatement();
        if (containsPlayer(playerName) && containsKey(key)) {
            String sql = "select * from playerdata1 where name = '" + playerName + "';";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                result = resultSet.getString(key);
            }
        }
        String resultString = result;
        statement.close();
        return resultString;
    }

    public static String get(Statement statement, Player player, String key) throws SQLException {
        String playerName = player.getName().getString();
        String result = null;
        if (containsPlayer(playerName) && containsKey(key)) {
            String sql = "select * from playerdata1 where name = '" + playerName + "';";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                result = resultSet.getString(key);
            }
        }
        return result;
    }

    public static String get(Statement statement, String playerName, String key) throws SQLException {
        String result = null;
        if (containsPlayer(playerName) && containsKey(key)) {
            String sql = "select * from playerdata1 where name = '" + playerName + "';";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                result = resultSet.getString(key);
            }
        }
        return result;
    }

    public static String get(Statement statement, String playerName, String key, String tableName) throws SQLException {
        String result = null;
        if (containsPlayer(playerName, tableName) && containsKey(key, tableName)) {
            String sql = "select * from " + tableName + " where name = '" + playerName + "';";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                result = resultSet.getString(key);
            }
        }
        return result;
    }

    public static String get(String worldKey) throws SQLException {
        String result = null;
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
        return resultString;
    }

    public static List<String> getKeyList(String keyName) throws SQLException {
        if (containsKey(keyName)) {
            Connection connection = getDatabaseConnection();
            Statement statement = connection.createStatement();
            List<String> list = new ArrayList<>();
            String sql = "select * from playerdata1;";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String s = resultSet.getString(keyName);
                if (!list.contains(s)) list.add(resultSet.getString(keyName));
            }
            statement.close();
            return list;
        }
        return null;
    }

    public static List<String> getColumnNameList(String tableName) throws SQLException {
        List<String> list = new ArrayList<>();
        Connection connection = getDatabaseConnection();
        String sql = "select * from ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, tableName);
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        while (resultSet.next()) {
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                list.add(resultSetMetaData.getColumnName(i));
            }
        }
        preparedStatement.close();
        return list;
    }

    public static Map<String, String> readWorldInfo() throws SQLException {
        Connection connection = getDatabaseConnection();
        Statement statement = connection.createStatement();
        Map<String, String> map = new HashMap<>();
        String sql = "select * from world;";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            String key = resultSet.getString("world_key");
            String value = resultSet.getString("world_value");
            map.put(key, value);
        }
        statement.close();
        return map;
    }

    public static void writeWorldInfo(Statement statement) throws SQLException {
        WorldRecordInfo.recordInfoMap.forEach((key, value) -> {
            try {
                put(statement, key, value);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static List<String> getAllMarketItemInfo(Statement statement) throws SQLException {
        List<String> list = new ArrayList<>();
        String sql = "select * from market1;";
        ResultSet resultSet = statement.executeQuery(sql);
        Map<String, Boolean> map = new HashMap<>();
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            if (map.containsKey(name)) continue;
            else map.put(name, true);
            for (int i = 0; i < 15; i++) {
                String s = resultSet.getString("market" + i);
                if (s != null && !s.equals("null")) list.add(name + "#" + s);
            }
        }
        return list;
    }

    public static List<MarketPlayerInfo> getAllMarketPlayerInfo(Statement statement) throws SQLException {
        List<MarketPlayerInfo> list = new ArrayList<>();
        String sql = "select * from market1;";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            String profit = resultSet.getString("profit");
            if (profit != null && !profit.equals("null")) {
                list.add(new MarketPlayerInfo(name, Double.parseDouble(profit)));
            }
        }
        return list;
    }

    public static void putAllMarketPlayerInfo(Statement statement) {
        Utils.marketPlayerInfos.forEach((key, value) -> {
            try {
                put(statement, key, "profit", value.toString(), "market1");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void putAllMarketItemInfo(Statement statement) throws SQLException {
        String sql = "truncate table market1";
        statement.executeUpdate(sql);
        Map<String, List<MarketItemInfo>> map = new HashMap<>();

        Utils.marketItemInfos.forEach(marketItemInfo -> {
            if (!map.containsKey(marketItemInfo.getPlayer())) map.put(marketItemInfo.getPlayer(), new ArrayList<>());
            List<MarketItemInfo> marketItemInfos = map.get(marketItemInfo.getPlayer());
            marketItemInfos.add(marketItemInfo);
        });

        map.forEach((key, value) -> {
            for (int i = 0; i < value.size(); i++) {
                MarketItemInfo marketItemInfo = value.get(i);
                try {
                    put(statement, key, "market" + i, Compute.getItemStackString(marketItemInfo.getItemStack()) + "*" + marketItemInfo.getPrice(), "market1");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public static boolean containsTable(String table) throws SQLException {
        Connection connection = getDatabaseConnection();
        String sql = "select count(*) from information_schema.columns where table_schema = 'wraq' and table_name = '" + table + "';";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        boolean result = resultSet.getInt(1) > 0;
        preparedStatement.close();
        return result;
    }

    public static boolean containsKey(String key) throws SQLException {
        Connection connection = getDatabaseConnection();
        String sql = "select count(*) from information_schema.columns where table_schema = 'wraq' and table_name = 'playerdata1' and column_name = '" + key + "'";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        boolean result = resultSet.getInt(1) > 0;
        preparedStatement.close();
        return result;
    }

    public static boolean containsKey(String key, String tableName) throws SQLException {
        Connection connection = getDatabaseConnection();
        String sql = "select count(*) from information_schema.columns where table_schema = 'wraq' and table_name = '" + tableName + "' and column_name = '" + key + "'";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        boolean result = resultSet.getInt(1) > 0;
        preparedStatement.close();
        return result;
    }

    public static boolean containsPlayer(String playerName) throws SQLException {
        Connection connection = getDatabaseConnection();
        String sql = "select count(*) from playerdata1 where name = '" + playerName + "';";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        boolean result = resultSet.getInt(1) > 0;
        preparedStatement.close();
        return result;
    }

    public static boolean containsPlayer(String playerName, String tableName) throws SQLException {
        Connection connection = getDatabaseConnection();
        String sql = "select count(*) from " + tableName + " where name = '" + playerName + "';";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        boolean result = resultSet.getInt(1) > 0;
        preparedStatement.close();
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
        return result;
    }


}
