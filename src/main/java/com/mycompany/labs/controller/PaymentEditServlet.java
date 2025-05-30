package com.mycompany.labs.controller;

import com.mycompany.labs.DAO.PaymentDAO;
import com.mycompany.labs.model.Payment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet("/PaymentEditServlet")
public class PaymentEditServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int paymentId = Integer.parseInt(request.getParameter("paymentId"));
            Payment payment = new PaymentDAO().getPaymentById(paymentId);

            if (payment != null) {
                request.setAttribute("payment", payment);
                request.getRequestDispatcher("/editPayment.jsp").forward(request, response);
            } else {
                request.getSession().setAttribute("error", "Payment not found");
                response.sendRedirect("payment.jsp");
            }
        } catch (Exception e) {
            handleError(request, response, e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, IOException {
        PaymentDAO dao = null;
        try {
            dao = new PaymentDAO();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        HttpSession session = request.getSession();

        try {
            Payment payment = new Payment();
            payment.setPaymentID(Integer.parseInt(request.getParameter("paymentId")));
            payment.setOrderID(Integer.parseInt(request.getParameter("orderId")));
            payment.setPaymentMethod(request.getParameter("paymentMethod"));
            payment.setTotalPrice(Double.parseDouble(request.getParameter("amount")));
            payment.setPaymentDate(Date.valueOf(request.getParameter("paymentDate")));
            payment.setStatus(request.getParameter("status"));

            dao.updatePayment(payment);
            session.setAttribute("message", "Payment updated successfully");
        } catch (Exception e) {
            session.setAttribute("error", "Update failed: " + e.getMessage());
        }
        response.sendRedirect("payment.jsp");
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        request.getSession().setAttribute("error", e.getMessage());
        response.sendRedirect("payment.jsp");
    }
}
