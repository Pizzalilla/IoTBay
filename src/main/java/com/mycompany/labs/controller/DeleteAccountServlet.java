package com.mycompany.labs.controller;

import com.mycompany.labs.dao.UserDao;
import com.mycompany.labs.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/DeleteAccountServlet")
public class DeleteAccountServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("currentUser");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            UserDao userDao = new UserDao();
            userDao.deleteUser(user.getUserID());

            session.invalidate();
            response.sendRedirect("login.jsp");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to delete account: " + e.getMessage());
            request.getRequestDispatcher("main.jsp").forward(request, response);
        }
    }
}
