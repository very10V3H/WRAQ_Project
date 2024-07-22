import com.very.wraq.files.dataBases.DataBase;

import java.sql.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = DataBase.getDatabaseConnection();
        Statement statement = connection.createStatement();
        DataBase.put(statement, "testKey", "testValue");

        Map<String, String> map = new HashMap<>();
        String sql = "select * from world;";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            String key = resultSet.getString("world_key");
            String value = resultSet.getString("world_value");
            map.put(key, value);
        }

        map.forEach((key, value) -> {
            System.out.println(key + "=" + value);
        });

        statement.close();
        connection.close();

/*        Connection connection = DataBase.getDatabaseConnection();
        Statement statement = connection.createStatement();
        DataBase.put(statement, "Dev", "market0", "{Count:1b,id:\"vmd:gold_coin\"}*20.0", "market1");
        DataBase.put(connection, "Dev", "market0", "{Count:1b,id:\"vmd:gold_coin\"}*20.0", "market1");
        statement.close();
        connection.close();*/
/*        String sql1 = "insert into ? (name, ?) values (?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql1);
        preparedStatement.setString(1, "market1");
        preparedStatement.setString(2, "market0");
        preparedStatement.setString(3, "Dev");
        preparedStatement.setString(4, "{Count:1b,id:\"vmd:gold_coin\"}*20.0");
        preparedStatement.executeUpdate();
        preparedStatement.close();*/

/*        String sql2 = "insert into 'market1' ('name', 'market0') values ('Dev','{Count:1b,id:\"vmd:gold_coin\"}*20.0')";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql2);
        statement.close();
        connection.close();*/
/*        String string = "abc\"c\"";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0 ; i < string.length() ; i ++) {
            char ch = string.charAt(i);
            if (ch == '"') {
                stringBuilder.append("\\\"");
            } else stringBuilder.append(ch);
        }
        System.out.println(stringBuilder);*/
/*        String type = "long_attackup_potion";

        String mainType = "";
        int count = 0;
        int index = 0;
        for (int i = 0 ; i < type.length() ; i++) {
            if (type.charAt(i) == '_') {
                count ++;
                if (index == 0) index = i;
            }
        }
        StringBuilder stringBuilder = new StringBuilder(type);
        if (count == 1) mainType = stringBuilder.substring(0, type.length() - 7);
        else mainType = stringBuilder.substring(index + 1, type.length() - 7);
        System.out.println(mainType);*/

/*        double range = 1;
        int times = 0;
        int rewardTimes = 0;
        int simulateTimes = 100000;
        Random rand = new Random();
        for (int i = 0 ; i < simulateTimes ; i ++) {
            double finalRange = range - times * 0.005;
            if (rand.nextDouble(finalRange) <= 0.01) {
                rewardTimes ++;
                times = 0;
            }
            else {
                times ++;
            }
        }
        System.out.println(rewardTimes);
        System.out.println(rewardTimes * 100.0 / simulateTimes + "%");*/
    }
}

