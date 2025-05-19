package com.mycompany.labs.controller;

import com.mycompany.labs.DAO.UserDao;

import com.mycompany.labs.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/AdminUserServlet")
public class AdminUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            response.sendRedirect("login.jsp");
            return;
        }

        String keyword = request.getParameter("keyword");

        try {
            UserDao userDao = new UserDao();
            List<User> userList;

            if (keyword != null && !keyword.trim().isEmpty()) {
                userList = userDao.searchUsers(keyword.trim());
            } else {
                userList = userDao.getAllUsers();
            }

            request.setAttribute("userList", userList);
            request.getRequestDispatcher("admin.jsp").forward(request, response);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to load users.");
            request.getRequestDispatcher("admin.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        String userIdStr = request.getParameter("userId");

        if (action == null || userIdStr == null) {
            response.sendRedirect("AdminUserServlet");
            return;
        }

        try {
            int userId = Integer.parseInt(userIdStr);
            UserDao userDao = new UserDao();

            if (action.equals("toggleStatus")) {
                User user = userDao.getUserById(userId);
                if (user != null) {
                    userDao.toggleUserStatus(userId, !user.isActive());
                }
            } else if (action.equals("delete")) {
                userDao.deleteUser(userId);
            }

        } catch (Exception e) {
            e.printStackTrace(); // Optionally log
        }

        response.sendRedirect("AdminUserServlet"); // Refresh user list after update
    }
}
