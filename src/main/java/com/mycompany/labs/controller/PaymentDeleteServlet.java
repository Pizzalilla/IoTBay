package com.mycompany.labs.controller;

import com.mycompany.labs.DAO.PaymentDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/PaymentDeleteServlet")
public class PaymentDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IOException {
        PaymentDAO dao = new PaymentDAO();
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