package org.example.simpleart.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection cn = null;
    private Database db;

    public static Connection getConnection() throws SQLException {
        if (cn != null && !cn.isClosed()) {
            System.out.println("Connected");
        }
        else {
            String url = "jdbc:sqlite:SimpleArt.db";
            cn = DriverManager.getConnection(url);
            System.out.println("Connected");
        }
        return cn;
    }
}
