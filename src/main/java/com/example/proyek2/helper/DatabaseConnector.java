package com.example.proyek2.helper;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnector {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/proyek2";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static java.sql.Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static void closeConnection(java.sql.Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
