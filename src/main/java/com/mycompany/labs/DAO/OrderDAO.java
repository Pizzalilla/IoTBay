package com.mycompany.labs.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.labs.model.CartItem;
import com.mycompany.labs.model.Order;
import com.mycompany.labs.model.OrderProduct;

public class OrderDAO {
    private Connection conn;

    public OrderDAO(Connection conn) {
        this.conn = conn;
    }

    public int createOrder(Integer customerID, java.sql.Date orderDate, String status) throws SQLException {
        String sql = "INSERT INTO Orders (customerID, orderDate, status) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        if (customerID != null) {
            ps.setInt(1, customerID);
        } else {
            ps.setNull(1, Types.INTEGER);
        }

        ps.setDate(2, orderDate);
        ps.setString(3, status);
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            throw new SQLException("Failed to create order.");
        }
    }

    public void addOrderProduct(int orderID, int deviceID, int quantity) throws SQLException {
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
        int orderID = createOrder(customerID, sqlDate, "submitted");

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

    public ArrayList<OrderProduct> getOrderDetails(int orderID) throws SQLException {
        ArrayList<OrderProduct> details = new ArrayList<>();
        String sql = 
            "SELECT d.deviceName, op.deviceId, op.quantity, op.unitPrice " +
            "FROM OrderProduct op " +
            "JOIN Device d ON op.deviceId = d.deviceId " +
            "WHERE op.orderID = ?";


        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, orderID);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            OrderProduct detail = new OrderProduct();
            detail.setDeviceID(rs.getInt("deviceId"));
            detail.setDeviceName(rs.getString("deviceName"));
            detail.setQuantity(rs.getInt("quantity"));
            detail.setUnitPrice(rs.getDouble("unitPrice"));
            details.add(detail);
        }

        return details;
    }
}
