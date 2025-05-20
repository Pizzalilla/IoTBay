package com.mycompany.labs.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import com.mycompany.labs.DAO.DB;
import com.mycompany.labs.DAO.OrderDAO;
import com.mycompany.labs.model.Order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/OrderHistoryServlet")
public class OrderHistoryServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer customerID = (Integer) request.getSession().getAttribute("customerID");
        if (customerID == null) {
            customerID = 1; // fallback to hardcoded user for now
        }

        String orderIDParam = request.getParameter("orderID");
        String orderDate = request.getParameter("orderDate");

        try {
            Connection conn = DB.getConnection();
            OrderDAO dao = new OrderDAO(conn);

            ArrayList<Order> orders;
            if ((orderIDParam != null && !orderIDParam.isEmpty()) || (orderDate != null && !orderDate.isEmpty())) {
                orders = dao.searchOrders(customerID, orderIDParam, orderDate);
            } else {
                orders = dao.getOrderHistory(customerID);
            }

            request.setAttribute("orders", orders);
            request.getRequestDispatcher("orderHistory.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("main.jsp"); // fallback if something crashes
        }
    }
}
