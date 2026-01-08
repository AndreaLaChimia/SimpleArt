package org.example.simpleart.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private static Connection cn = null;

    public static Connection getConnection() throws SQLException {
        if (cn == null || cn.isClosed()) {
            String url = "jdbc:sqlite:SimpleArt.db";
            cn = DriverManager.getConnection(url);

            // Attiva le foreign key
            try (Statement st = cn.createStatement()) {
                st.execute("PRAGMA foreign_keys = ON;");
            }

            System.out.println("Connected to SQLite");
        }
        return cn;
    }
}
