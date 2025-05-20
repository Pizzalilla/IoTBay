package com.mycompany.labs.controller;

import com.mycompany.labs.DAO.PaymentDAO;
import com.mycompany.labs.model.Payment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

@WebServlet("/PaymentCreateServlet")
public class PaymentCreateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PaymentDAO dao = new PaymentDAO();
        HttpSession session = request.getSession();

        try {
            Payment payment = new Payment();
            payment.setOrderID(Integer.parseInt(request.getParameter("orderId")));
            payment.setPaymentMethod(request.getParameter("paymentMethod"));
            payment.setTotalPrice(Double.parseDouble(request.getParameter("amount")));
            payment.setPaymentDate(Date.valueOf(request.getParameter("paymentDate")));
            payment.setStatus("Pending");

            dao.createPayment(payment);
            session.setAttribute("message", "Payment created successfully");
        } catch (Exception e) {
            session.setAttribute("error", "Creation failed: " + e.getMessage());
        }
        response.sendRedirect("payment.jsp");
    }
}