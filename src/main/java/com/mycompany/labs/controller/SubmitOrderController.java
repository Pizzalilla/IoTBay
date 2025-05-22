package com.mycompany.labs.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

import com.mycompany.labs.DAO.CartDAO;
import com.mycompany.labs.DAO.DB;
import com.mycompany.labs.DAO.OrderDAO;
import com.mycompany.labs.model.CartItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SubmitOrderController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Connection conn = DB.getConnection();
            CartDAO cartDAO = new CartDAO(conn);
            OrderDAO orderDAO = new OrderDAO(conn);

            HttpSession session = request.getSession();
            Object cid = session.getAttribute("customerID");
            Integer customerID = (cid != null) ? (int) cid : null;


            ArrayList<CartItem> cartItems = cartDAO.getCartItems(customerID);

            if (cartItems.isEmpty()) {
                request.setAttribute("message", "Cart is empty!");
                request.getRequestDispatcher("cart.jsp").forward(request, response);
                return;
            }

            Date orderDate = new Date(System.currentTimeMillis());
            int orderID = orderDAO.createOrder(customerID, orderDate, "Submitted");

            for (CartItem item : cartItems) {
                orderDAO.addOrderProduct(orderID, item.getDeviceID(), item.getQuantity());
            }

            cartDAO.clearCart(customerID);

            session.setAttribute("cartItems", null);
            session.setAttribute("message", "✅ Order submitted successfully!");
            response.sendRedirect("main.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "❌ Failed to submit order.");
            response.sendRedirect("main.jsp");
        }
    }
}
