package com.mycompany.labs.controller;

import com.mycompany.labs.DAO.PaymentDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/PaymentDeleteServlet")
public class PaymentDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IOException {
        PaymentDAO dao = null;
        try {
            dao = new PaymentDAO();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        HttpSession session = request.getSession();

        try {
            int paymentId = Integer.parseInt(request.getParameter("paymentId"));
            dao.deletePayment(paymentId);
            session.setAttribute("message", "Payment deleted successfully");
        } catch (Exception e) {
            session.setAttribute("error", "Deletion failed: " + e.getMessage());
        }
        response.sendRedirect("payment.jsp");
    }
}
