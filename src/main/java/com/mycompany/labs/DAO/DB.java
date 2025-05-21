package com.mycompany.labs.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private static final String DB_URL = "jdbc:sqlite:";

    // Method to get a connection to the SQLite database
    public static Connection getConnection() throws SQLException {
        String dbPath = "C:\\Users\\20828\\IdeaProjects\\IoTBay\\db.sqlite";
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
        }

        // Establish and return connection
        return DriverManager.getConnection(DB_URL+dbPath);
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("Connection to SQLite has been established.");
            }
        } catch (SQLException e) {
            System.err.println("Connection failed.");
            e.printStackTrace();
        }
    }
}

