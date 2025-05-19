package com.mycompany.labs.DAO;

import com.mycompany.labs.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private Connection conn;

    public UserDao() throws SQLException, ClassNotFoundException {
        DBConnector c = new DBConnector();
        conn = c.getConnection();
    }

    // Create
    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO Users (firstName, lastName, email, password, gender, mobile, address, type, position, active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getGender());
            stmt.setString(6, user.getMobile());
            stmt.setString(7, user.getAddress());
            stmt.setString(8, user.getType());
            stmt.setString(9, user.getPosition());
            stmt.setBoolean(10, true); // 默认激活
            stmt.executeUpdate();
        }
    }

    // Read by ID
    public User getUserById(int userId) throws SQLException {
        String sql = "SELECT * FROM Users WHERE userID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractUser(rs);
            }
        }
        return null;
    }

    // Read by email
    public User getUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM Users WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractUser(rs);
            }
        }
        return null;
    }

    // Update
    public void updateUser(User user) throws SQLException, ClassNotFoundException {
        boolean updatePassword = user.getPassword() != null && !user.getPassword().isEmpty();

        String sql = updatePassword
                ? "UPDATE Users SET firstName = ?, lastName = ?, email = ?, gender = ?, mobile = ?, address = ?, position = ?, password = ? WHERE userID = ?"
                : "UPDATE Users SET firstName = ?, lastName = ?, email = ?, gender = ?, mobile = ?, address = ?, position = ? WHERE userID = ?";

        DBConnector db = new DBConnector();
        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getGender());
            stmt.setString(5, user.getMobile());
            stmt.setString(6, user.getAddress());
            stmt.setString(7, user.getPosition());

            if (updatePassword) {
                stmt.setString(8, user.getPassword());
                stmt.setInt(9, user.getUserID());
            } else {
                stmt.setInt(8, user.getUserID());
            }

            stmt.executeUpdate();
        }
    }

    // Delete
    public void deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM Users WHERE userID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }

    // Logical delete (for self-cancelled account)
    public void nullifyEmail(int userId) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Users SET email = NULL WHERE userID = ?";
        DBConnector db = new DBConnector();
        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }

    // Get all users
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(extractUser(rs));
            }
        }
        return users;
    }

    // Toggle user active status
    public void toggleUserStatus(int userId, boolean active) throws SQLException {
        String sql = "UPDATE Users SET active = ? WHERE userID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, active);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        }
    }

    // Search users by name or mobile (admin function)
    public List<User> searchUsers(String keyword) throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users WHERE (firstName || ' ' || lastName) LIKE ? OR mobile LIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            String likeKeyword = "%" + keyword + "%";
            stmt.setString(1, likeKeyword);
            stmt.setString(2, likeKeyword);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(extractUser(rs));
            }
        }
        return users;
    }

    // Helper: map ResultSet to User object
    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserID(rs.getInt("userID"));
        user.setFirstName(rs.getString("firstName"));
        user.setLastName(rs.getString("lastName"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setGender(rs.getString("gender"));
        user.setMobile(rs.getString("mobile"));
        user.setAddress(rs.getString("address"));
        user.setType(rs.getString("type"));
        user.setPosition(rs.getString("position"));
        user.setActive(rs.getBoolean("active")); // Make sure column exists
        return user;
    }
}
