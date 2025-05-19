package com.mycompany.labs.controller;

import com.mycompany.labs.dao.LogDao;
import com.mycompany.labs.dao.UserDao;
import com.mycompany.labs.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String isAdminLogin = request.getParameter("adminLogin");

        // Handle empty input
        if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
            request.setAttribute("loginError", "Email and password are required.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // Admin login
        if ("true".equals(isAdminLogin)) {
            if ("admin".equals(email) && "123456".equals(password)) {
                HttpSession session = request.getSession();
                session.setAttribute("isAdmin", true);

                try {
                    UserDao userDao = new UserDao();
                    request.setAttribute("userList", userDao.getAllUsers());
                    request.getRequestDispatcher("admin.jsp").forward(request, response);
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                    request.setAttribute("loginError", "Unable to load users for admin.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
                return;
            } else {
                request.setAttribute("loginError", "Invalid admin credentials.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
        }

        // Normal user login
        try {
            UserDao userDao = new UserDao();
            User user = userDao.getUserByEmail(email);

            if (user != null && user.getPassword().equals(password)) {
                HttpSession session = request.getSession();
                session.setAttribute("currentUser", user);

                LogDao logDao = new LogDao();
                String logId = logDao.insertLog(user.getUserID());
                session.setAttribute("logId", logId);

                response.sendRedirect("main.jsp");
            } else {
                request.setAttribute("loginError", "Invalid email or password.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("loginError", "Database error: " + e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
