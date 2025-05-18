package com.mycompany.labs.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.labs.model.CartItem;
import com.mycompany.labs.model.Order;

public class OrderDAO {
    private Connection conn;

    public OrderDAO(Connection conn) {
        this.conn = conn;
    }

    public int createOrder(Integer customerID, Date orderDate, String status) throws SQLException {
        String sql = "INSERT INTO Orders (customerID, orderDate, status) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        if (customerID != null) {
            ps.setInt(1, customerID);
        } else {
            ps.setNull(1, java.sql.Types.INTEGER); // ✅ handle anonymous
        }

        ps.setDate(2, new java.sql.Date(orderDate.getTime()));
        ps.setString(3, status);
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1); // return generated orderID
        } else {
            throw new SQLException("Failed to create order.");
        }
    }

    public void addOrderProduct(int orderID, int deviceID, int quantity) throws SQLException {
        // Optional: fetch unit price from Device table
        String priceQuery = "SELECT price FROM Device WHERE deviceId = ?";
        PreparedStatement priceStmt = conn.prepareStatement(priceQuery);
        priceStmt.setInt(1, deviceID);
        ResultSet priceRs = priceStmt.executeQuery();
        double unitPrice = 0;

        if (priceRs.next()) {
            unitPrice = priceRs.getDouble("price");
        }

        String sql = "INSERT INTO OrderProduct (orderID, deviceId, quantity, unitPrice) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, orderID);
        ps.setInt(2, deviceID);
        ps.setInt(3, quantity);
        ps.setDouble(4, unitPrice);
        ps.executeUpdate();
    }

    public void insertOrder(List<CartItem> cart, Integer customerID) throws SQLException {
        java.util.Date now = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(now.getTime());

        // Create the order and get the generated order ID
        int orderID = createOrder(customerID, sqlDate, "Pending");

        // Insert each cart item into OrderProduct
        for (CartItem item : cart) {
            addOrderProduct(orderID, item.getDeviceID(), item.getQuantity());
        }
    }

    public ArrayList<Order> getOrderHistory(int customerID) throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM Orders WHERE customerID = ? ORDER BY orderDate DESC";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Order order = new Order(
                rs.getInt("orderID"),
                rs.getInt("customerID"),
                rs.getDate("orderDate"),
                rs.getString("status")
            );
            orders.add(order);
        }

        return orders;
    }

    public ArrayList<Order> searchOrders(int customerID, String orderID, String orderDate) throws SQLException {
        ArrayList<Order> results = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Orders WHERE customerID = ?");
        if (orderID != null && !orderID.isEmpty()) {
            sql.append(" AND orderID = ").append(orderID);
        }
        if (orderDate != null && !orderDate.isEmpty()) {
            sql.append(" AND orderDate = '").append(orderDate).append("'");
        }
        sql.append(" ORDER BY orderDate DESC");

        PreparedStatement ps = conn.prepareStatement(sql.toString());
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Order order = new Order(
                rs.getInt("orderID"),
                rs.getInt("customerID"),
                rs.getDate("orderDate"),
                rs.getString("status")
            );
            results.add(order);
        }

        return results;
    }
}
