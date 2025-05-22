package com.mycompany.labs.DAO;

// PaymentDAO.java

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.labs.model.Payment;

public class PaymentDAO {
    private Connection connectToDB() throws SQLException {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Derby JDBC Driver not found", e);
        }
        String dbURL = "jdbc:derby://localhost:1527/IoTBayDB2";
        String user = "test2";
        String password = "test2";
        return DriverManager.getConnection(dbURL, user, password);
    }
    public void createPayment(Payment payment) throws SQLException {
        String sql = "INSERT INTO Payment (orderID, paymentMethod, paymentDate, amount, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connectToDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, payment.getOrderID());
            stmt.setString(2, payment.getPaymentMethod());
            stmt.setDate(3, new java.sql.Date(payment.getPaymentDate().getTime()));
            stmt.setDouble(4, payment.getTotalPrice());
            stmt.setString(5, payment.getStatus());
            stmt.executeUpdate();
        }
    }

    public List<Payment> getAllPayments() throws SQLException {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM Payment";
        try (Connection conn = connectToDB();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                payments.add(extractPaymentFromResultSet(rs));
            }
        }
        return payments;
    }

    public Payment getPaymentById(int paymentId) throws SQLException {
        String sql = "SELECT * FROM Payment WHERE paymentID = ?";
        try (Connection conn = connectToDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, paymentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractPaymentFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public void updatePayment(Payment payment) throws SQLException {
        String sql = "UPDATE Payment SET orderID=?, paymentMethod=?, paymentDate=?, amount=?, status=? WHERE paymentID=?";
        try (Connection conn = connectToDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, payment.getOrderID());
            stmt.setString(2, payment.getPaymentMethod());
            stmt.setDate(3, new java.sql.Date(payment.getPaymentDate().getTime()));
            stmt.setDouble(4, payment.getTotalPrice());
            stmt.setString(5, payment.getStatus());
            stmt.setInt(6, payment.getPaymentID());
            stmt.executeUpdate();
        }
    }

    public void deletePayment(int paymentId) throws SQLException {
        String sql = "DELETE FROM Payment WHERE paymentID = ?";
        try (Connection conn = connectToDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, paymentId);
            stmt.executeUpdate();
        }
    }

    public List<Payment> searchPayments(Integer paymentId, Date paymentDate) throws SQLException {
        List<Payment> payments = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Payment WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (paymentId != null) {
            sql.append(" AND paymentID = ?");
            params.add(paymentId);
        }
        if (paymentDate != null) {
            sql.append(" AND paymentDate = ?");
            params.add(paymentDate);
        }

        try (Connection conn = connectToDB();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    payments.add(extractPaymentFromResultSet(rs));
                }
            }
        }
        return payments;
    }

    private Payment extractPaymentFromResultSet(ResultSet rs) throws SQLException {
        Payment payment = new Payment();
        payment.setPaymentID(rs.getInt("paymentID"));
        payment.setOrderID(rs.getInt("orderID"));
        payment.setPaymentMethod(rs.getString("paymentMethod"));
        payment.setPaymentDate(rs.getDate("paymentDate"));
        payment.setTotalPrice(rs.getDouble("amount"));
        payment.setStatus(rs.getString("status"));
        return payment;
    }
}