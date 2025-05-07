package com.mycompany.labs.controller;

import com.mycompany.labs.model.Order;
import com.mycompany.labs.DAO.OrderDAO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/CreateOrderServlet")
public class CreateOrderServlet extends HttpServlet {

    private Connection connectToDB() throws SQLException {
        String dbURL = "jdbc:derby://localhost:1527/usersdb";
        String user = "app";
        String password = "app";
        return DriverManager.getConnection(dbURL, user, password);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int customerID = Integer.parseInt(request.getParameter("customerID"));
        int staffID = Integer.parseInt(request.getParameter("staffID"));
        Date orderDate = java.sql.Date.valueOf(request.getParameter("orderDate"));
        String status = request.getParameter("status");

        Order order = new Order();
        order.setCustomerID(customerID);
        order.setStaffID(staffID);
        order.setOrderDate(orderDate);
        order.setStatus(status);

        try (Connection conn = connectToDB()) {
            OrderDAO orderDAO = new OrderDAO(conn);
            orderDAO.createOrder(order);
            request.setAttribute("message", "Order created successfully with ID: " + order.getOrderID());
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Error: " + e.getMessage());
        }

        request.getRequestDispatcher("createOrder.jsp").forward(request, response);
    }
}
