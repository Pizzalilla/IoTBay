package com.mycompany.labs.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DB handles JDBC connection setup.
 * Works for both Tomcat and GlassFish.
 */
public class DB {
    
    private static final String URL = "jdbc:derby://localhost:1527/IoTBayDB2";
    private static final String USER = "test";     // your db username
    private static final String PASS = "test";     // your db password
    private static final String DRIVER = "org.apache.derby.jdbc.ClientDriver";

    public static Connection getConnection() throws SQLException {
        try {
            System.out.println("⏳ Loading Derby driver...");
            Class.forName(DRIVER);
            System.out.println("✅ Derby driver loaded.");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Derby driver NOT FOUND.");
            e.printStackTrace();
            throw new SQLException("Derby driver not found in classpath", e);
        }

        Connection conn = DriverManager.getConnection(URL, USER, PASS);
        System.out.println("✅ Connected to database!");
        return conn;
    }
}
