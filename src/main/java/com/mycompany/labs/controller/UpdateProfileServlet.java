package com.mycompany.labs.controller;

import com.mycompany.labs.dao.UserDao;
import com.mycompany.labs.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.regex.Pattern;

@WebServlet("/UpdateProfileServlet")
public class UpdateProfileServlet extends HttpServlet {

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[\\w.-]+@([\\w-]+\\.)+[A-Za-z]{2,6}$");
    private static final Pattern MOBILE_REGEX = Pattern.compile("^\\d+$");

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String gender = request.getParameter("gender");
        String mobile = request.getParameter("mobile");
        String address = request.getParameter("address");
        String position = request.getParameter("position");
        String password = request.getParameter("password");
        String type = currentUser.getType();

        String error = null;

        if (isEmpty(firstName) || isEmpty(lastName) || isEmpty(email) || isEmpty(address)) {
            error = "First name, last name, email, and address are required.";
        } else if (!EMAIL_REGEX.matcher(email).matches()) {
            error = "Invalid email format.";
        } else if (!isEmpty(mobile) && !MOBILE_REGEX.matcher(mobile).matches()) {
            error = "Mobile number must contain only digits.";
        } else if ("staff".equals(type) && isEmpty(position)) {
            error = "Position is required for staff.";
        } else if (!isEmpty(password) && password.length() < 6) {
            error = "Password must be at least 6 characters.";
        }

        if (error != null) {
            request.setAttribute("error", error);
            request.getRequestDispatcher("editProfile.jsp").forward(request, response);
            return;
        }

        User updatedUser = new User();
        updatedUser.setUserID(currentUser.getUserID());
        updatedUser.setType(currentUser.getType());
        updatedUser.setFirstName(firstName);
        updatedUser.setLastName(lastName);
        updatedUser.setEmail(email);
        updatedUser.setGender(gender);
        updatedUser.setMobile(mobile);
        updatedUser.setAddress(address);
        updatedUser.setPosition("staff".equals(type) ? position : null);
        updatedUser.setPassword(!isEmpty(password) ? password : currentUser.getPassword());

        try {
            UserDao userDao = new UserDao();
            userDao.updateUser(updatedUser);
            session.setAttribute("currentUser", updatedUser);
            response.sendRedirect("main.jsp");
        } catch (SQLIntegrityConstraintViolationException e) {
            request.setAttribute("error", "The email address is already registered.");
            request.getRequestDispatcher("editProfile.jsp").forward(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("editProfile.jsp").forward(request, response);
        }
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
