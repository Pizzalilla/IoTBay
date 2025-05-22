package com.mycompany.labs.controller;

import com.mycompany.labs.DAO.PaymentDAO;
import com.mycompany.labs.model.Payment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/PaymentSearchServlet")
public class PaymentSearchServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServletException, IOException {
        PaymentDAO dao = new PaymentDAO();

        try {
            Integer paymentId = parseInteger(request.getParameter("paymentId"));
            Date paymentDate = parseDate(request.getParameter("paymentDate"));

            List<Payment> results = dao.searchPayments(paymentId, paymentDate);
            request.setAttribute("payments", results);
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Search error: " + e.getMessage());
        }
        request.getRequestDispatcher("/payment.jsp").forward(request, response);
    }

    private Integer parseInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Date parseDate(String dateStr) {
        try {
            return Date.valueOf(dateStr);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}