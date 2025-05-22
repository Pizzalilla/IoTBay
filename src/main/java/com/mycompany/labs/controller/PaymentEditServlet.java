package com.mycompany.labs.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mycompany.labs.DAO.PaymentDAO;
import com.mycompany.labs.model.Payment;

@WebServlet("/PaymentEditServlet")
public class PaymentEditServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int PAYMENTID = Integer.parseInt(request.getParameter("PAYMENTID"));
            Payment payment = new PaymentDAO().getPaymentById(PAYMENTID);

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
        PaymentDAO dao = new PaymentDAO();
        HttpSession session = request.getSession();

        try {
            Payment payment = new Payment();
            payment.setPaymentID(Integer.parseInt(request.getParameter("PAYMENTID")));
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
