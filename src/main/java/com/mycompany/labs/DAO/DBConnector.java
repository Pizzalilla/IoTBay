package com.mycompany.labs.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    String URL = "jdbc:derby://localhost:1527/";
    String db = "APP";
    String dbuser = "root";
    String dbpass = "123456";
    String driver = "org.apache.derby.jdbc.ClientDriver";
    Connection conn;

    public DBConnector() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        conn = DriverManager.getConnection(URL + db,dbuser,dbpass);
    }

    public Connection getConnection(){
        return this.conn;
    }

    public void closeConnection() throws SQLException {
        this.conn.close();
    }
}
