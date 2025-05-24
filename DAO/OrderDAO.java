package com.mycompany.labs.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.labs.model.Order;

public class OrderDAO {
    private Connection conn;

    public OrderDAO(Connection conn) throws SQLException {
        this.conn = DB.getConnection();
    }

    // CREATE a new order
    public void createOrder(Order order) throws SQLException {
        String sql = "INSERT INTO Orders (customerID, staffID, orderDate, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, order.getCustomerID());
            stmt.setInt(2, order.getStaffID());
            stmt.setDate(3, new java.sql.Date(order.getOrderDate().getTime()));
            stmt.setString(4, order.getStatus());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                order.setOrderID(rs.getInt(1));
            }
        }
    }

    // READ all orders for a given customer
    public List<Order> findOrdersByCustomer(int customerID) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM Orders WHERE customerID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setOrderID(rs.getInt("orderID"));
                order.setCustomerID(rs.getInt("customerID"));
                order.setStaffID(rs.getInt("staffID"));
                order.setOrderDate(rs.getDate("orderDate"));
                order.setStatus(rs.getString("status"));
                orders.add(order);
            }
        }
        return orders;
    }

    // READ a single order by orderID
    public Order getOrderById(int orderID) throws SQLException {
        String sql = "SELECT * FROM Orders WHERE orderID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Order order = new Order();
                order.setOrderID(rs.getInt("orderID"));
                order.setCustomerID(rs.getInt("customerID"));
                order.setStaffID(rs.getInt("staffID"));
                order.setOrderDate(rs.getDate("orderDate"));
                order.setStatus(rs.getString("status"));
                return order;
            }
        }
        return null;
    }

    // UPDATE order status or staff assignment
    public void updateOrder(Order order) throws SQLException {
        String sql = "UPDATE Orders SET staffID = ?, status = ? WHERE orderID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, order.getStaffID());
            stmt.setString(2, order.getStatus());
            stmt.setInt(3, order.getOrderID());
            stmt.executeUpdate();
        }
    }

    // DELETE an unsubmitted order
    public void deleteOrder(int orderID) throws SQLException {
        String sql = "DELETE FROM Orders WHERE orderID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderID);
            stmt.executeUpdate();
        }
    }
}
