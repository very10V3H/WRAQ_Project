package fun.wraq.files.dataBases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public volatile static Connection connection = null;

    public static Connection getDatabaseConnection() throws SQLException {
        if (connection == null) {
            synchronized (DBConnection.class) {
                if (connection == null) {
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wraq",
                            "root", "123456");
                }
            }
        }
        return connection;
    }
}
