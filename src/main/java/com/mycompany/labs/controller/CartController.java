package com.mycompany.labs.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.mycompany.labs.DAO.CartDAO;
import com.mycompany.labs.DAO.DB;
import com.mycompany.labs.DAO.OrderDAO;
import com.mycompany.labs.model.CartItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CartController")
public class CartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer customerID = (Integer) session.getAttribute("customerID");
        if (customerID == null) customerID = 0;

        try (Connection conn = DB.getConnection()) {
            CartDAO cartDAO = new CartDAO(conn);
            List<CartItem> cartItems = cartDAO.getCartItems(customerID);
            request.setAttribute("cartItems", cartItems);

            double total = 0;
            for (CartItem item : cartItems) {
                total += item.getUnitPrice() * item.getQuantity();
            }
            request.setAttribute("totalPrice", total);

            request.getRequestDispatcher("cart.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer customerID = (Integer) session.getAttribute("customerID");
        if (customerID == null) customerID = 0;

        String action = request.getParameter("action");

        try (Connection conn = DB.getConnection()) {
            CartDAO cartDAO = new CartDAO(conn);

            if ("remove".equals(action)) {
                int cartItemID = Integer.parseInt(request.getParameter("cartItemID"));
                cartDAO.removeCartItem(cartItemID);

            } else if ("clear".equals(action)) {
                cartDAO.clearCart(customerID);

            } else if ("update".equals(action)) {
                int cartItemID = Integer.parseInt(request.getParameter("cartItemID"));
                int deviceID = Integer.parseInt(request.getParameter("deviceID"));
                int newQuantity = Integer.parseInt(request.getParameter("quantity"));

                String stockQuery = "SELECT stockQty FROM Device WHERE deviceId = ?";
                PreparedStatement ps = conn.prepareStatement(stockQuery);
                ps.setInt(1, deviceID);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    int stockQty = rs.getInt("stockQty");
                    if (newQuantity <= stockQty) {
                        cartDAO.updateCartItemQuantity(cartItemID, newQuantity);
                    } else {
                        System.out.println("Quantity exceeds stock — update skipped.");
                    }
                }

            } else if ("submit".equals(action)) {
                List<CartItem> cartItems = cartDAO.getCartItems(customerID);

                if (!cartItems.isEmpty()) {
                    OrderDAO orderDAO = new OrderDAO(conn);
                    orderDAO.insertOrder(cartItems, customerID);
                    cartDAO.clearCart(customerID);
                }

                response.sendRedirect("main.jsp?submitted=true");
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("CartController");
    }
}
