package com.mycompany.labs.controller;

import com.mycompany.labs.DAO.LogDao;
import com.mycompany.labs.model.Log;
import com.mycompany.labs.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/ReadLogServlet")
public class ReadLogServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("currentUser");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String logDate = request.getParameter("logDate");

        try {
            LogDao logDao = new LogDao();
            List<Log> logList;

            if (logDate != null && !logDate.isEmpty()) {
                logList = logDao.getLogsByUserIdAndDate(user.getUserID(), logDate);
            } else {
                logList = logDao.getLogsByUserId(user.getUserID());
            }

            session.setAttribute("logList", logList);
            response.sendRedirect("log.jsp");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to load logs.");
        }
    }
}

