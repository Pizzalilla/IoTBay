package com.mycompany.labs.DAO;

import com.mycompany.labs.model.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LogDao {
    private Connection conn;

    public LogDao() throws SQLException, ClassNotFoundException {
        DBConnector c = new DBConnector();
        conn = c.getConnection();
    }

    public String insertLog(int userId) throws SQLException {
        String sql = "INSERT INTO Log (id, userId, loginTime, logoutTime) VALUES (?, ?, ?, ?)";
        String uuid = UUID.randomUUID().toString();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            stmt.setString(1, uuid);
            stmt.setInt(2, userId);
            stmt.setTimestamp(3, now);
            stmt.setNull(4, Types.TIMESTAMP);
            stmt.executeUpdate();
        }
        return uuid;
    }

    public void updateLogoutTime(String logId) throws SQLException {
        String sql = "UPDATE Log SET logoutTime = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            stmt.setTimestamp(1, now);
            stmt.setString(2, logId);
            stmt.executeUpdate();
        }
    }

    public List<Log> getLogsByUserId(int userId) throws SQLException {
        List<Log> logs = new ArrayList<>();
        String sql = "SELECT * FROM Log WHERE userId = ? ORDER BY loginTime DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Log log = new Log();
                    log.setId(rs.getString("id"));
                    log.setUserId(rs.getString("userId"));
                    log.setLoginTime(rs.getTimestamp("loginTime"));
                    log.setLogoutTime(rs.getTimestamp("logoutTime"));
                    logs.add(log);
                }
            }
        }
        return logs;
    }

    public List<Log> getLogsByUserIdAndDate(int userId, String dateStr) throws SQLException {
        List<Log> logs = new ArrayList<>();
        String sql = "SELECT * FROM Log WHERE userId = ? AND DATE(loginTime) = ? ORDER BY loginTime DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, dateStr);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Log log = new Log();
                    log.setId(rs.getString("id"));
                    log.setUserId(rs.getString("userId"));
                    log.setLoginTime(rs.getTimestamp("loginTime"));
                    log.setLogoutTime(rs.getTimestamp("logoutTime"));
                    logs.add(log);
                }
            }
        }
        return logs;
    }
}
