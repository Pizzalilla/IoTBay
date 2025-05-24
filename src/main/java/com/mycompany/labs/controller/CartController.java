package com.mycompany.labs.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        if ("add".equals(action)) {
            String deviceIDStr = request.getParameter("deviceID");
            String quantityStr = request.getParameter("quantity");

            if (deviceIDStr != null && !deviceIDStr.isEmpty() &&
                quantityStr != null && !quantityStr.isEmpty()) {

                try {
                    int deviceID = Integer.parseInt(deviceIDStr);
                    int quantity = Integer.parseInt(quantityStr);

                    boolean found = false;
                    for (CartItem item : cart) {
                        if (item.getDeviceID() == deviceID) {
                            item.setQuantity(item.getQuantity() + quantity);
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        CartItem item = new CartItem();
                        item.setDeviceID(deviceID);
                        item.setQuantity(quantity);
                        cart.add(item);
                    }
                } catch (NumberFormatException e) {
                    // Log or ignore invalid inputs
                }
            }

        } else if ("remove".equals(action)) {
            String deviceIDStr = request.getParameter("deviceID");

            if (deviceIDStr != null && !deviceIDStr.isEmpty()) {
                try {
                    int deviceID = Integer.parseInt(deviceIDStr);
                    cart.removeIf(item -> item.getDeviceID() == deviceID);
                } catch (NumberFormatException e) {
                    // Invalid input, skip
                }
            }

        } else if ("clear".equals(action)) {
            session.removeAttribute("cart");
            response.sendRedirect("cart.jsp");
            return;

        }else if ("submit".equals(action)) {
            try {
                Integer customerID = (Integer) session.getAttribute("customerID");
                if (customerID == null) {
                    System.out.println("🟡 No customer logged in — using NULL for anonymous order.");
                }

                Connection conn = DB.getConnection();
                OrderDAO dao = new OrderDAO(conn);
                dao.insertOrder(cart, customerID); // it's okay to pass null now
                System.out.println("✅ Order saved to database.");
            } catch (Exception e) {
                e.printStackTrace();
            }

            session.removeAttribute("cart");
            response.sendRedirect("main.jsp?submitted=true");
            return;
        }





        session.setAttribute("cart", cart);
        response.sendRedirect("cart.jsp");
    }
}
