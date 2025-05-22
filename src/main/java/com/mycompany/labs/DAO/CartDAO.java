package com.mycompany.labs.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mycompany.labs.model.CartItem;

public class CartDAO {
    private Connection conn;

    public CartDAO(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<CartItem> getCartItems(int customerID) throws SQLException {
        ArrayList<CartItem> cart = new ArrayList<>();
        String sql = "SELECT ci.*, d.deviceName, d.price AS unitPrice, d.stockQty AS maxStock " +
                     "FROM CartItem ci " +
                     "JOIN Device d ON ci.deviceId = d.deviceId " +
                     "WHERE ci.customerID = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            CartItem item = new CartItem(
                rs.getInt("cartItemID"),
                rs.getInt("customerID"),
                rs.getInt("deviceId"),
                rs.getInt("quantity"),
                rs.getDate("dateAdded")
            );
            item.setDeviceName(rs.getString("deviceName"));
            item.setUnitPrice(rs.getDouble("unitPrice"));
            item.setMaxStock(rs.getInt("maxStock"));
            cart.add(item);
        }
        return cart;
    }

    public void addCartItem(CartItem item) throws SQLException {
        String sql = "INSERT INTO CartItem (customerID, deviceId, quantity, dateAdded) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, item.getCustomerID());
        ps.setInt(2, item.getDeviceID());
        ps.setInt(3, item.getQuantity());
        ps.setDate(4, new java.sql.Date(item.getDateAdded().getTime()));
        ps.executeUpdate();
    }

    public void updateCartItemQuantity(int cartItemID, int newQuantity) throws SQLException {
        String sql = "UPDATE CartItem SET quantity = ? WHERE cartItemID = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, newQuantity);
        ps.setInt(2, cartItemID);
        ps.executeUpdate();
    }

    public void clearCart(int customerID) throws SQLException {
        String sql = "DELETE FROM CartItem WHERE customerID = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, customerID);
        ps.executeUpdate();
    }

    public void removeCartItem(int cartItemID) throws SQLException {
        String sql = "DELETE FROM CartItem WHERE cartItemID = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, cartItemID);
        ps.executeUpdate();
    }
}
