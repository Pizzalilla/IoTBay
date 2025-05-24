package com.mycompany.labs.controller;

import com.mycompany.labs.DAO.UserDao;
import com.mycompany.labs.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.regex.Pattern;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[\\w.-]+@([\\w-]+\\.)+[A-Za-z]{2,6}$");
    private static final Pattern MOBILE_REGEX = Pattern.compile("^\\d+$");

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve form parameters
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String gender = request.getParameter("gender");
        String mobile = request.getParameter("mobile");
        String address = request.getParameter("address");
        String type = request.getParameter("type");
        String position = request.getParameter("position");

        // Validation
        String error = null;

        if (isEmpty(firstName) || isEmpty(lastName) || isEmpty(email) || isEmpty(password) ||
                isEmpty(gender) || isEmpty(mobile) || isEmpty(address) || isEmpty(type)) {
            error = "All fields except 'position' are required.";
        } else if (!EMAIL_REGEX.matcher(email).matches()) {
            error = "Invalid email format.";
        } else if (!MOBILE_REGEX.matcher(mobile).matches()) {
            error = "Mobile number must contain only digits.";
        } else if (!type.equals("customer") && !type.equals("staff")) {
            error = "User type must be either 'customer' or 'staff'.";
        } else if (type.equals("customer") && position != null && !position.trim().isEmpty()) {
            error = "Position should only be filled in for staff.";
        } else if (type.equals("staff") && (position == null || position.trim().isEmpty())) {
            error = "Position is required for staff.";
        }

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setGender(gender);
        user.setMobile(mobile);
        user.setAddress(address);
        user.setType(type);
        user.setPosition(type.equals("staff") ? position : null);

        if (error != null) {
            setFormValues(request, user);
            request.setAttribute("error", error);
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        try {
            UserDao userDao = new UserDao();
            userDao.addUser(user);
            response.sendRedirect("login.jsp");
        } catch (SQLIntegrityConstraintViolationException e) {
            setFormValues(request, user);
            request.setAttribute("error", "The email address is already registered.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            setFormValues(request, user);
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void setFormValues(HttpServletRequest request, User user) {
        request.setAttribute("firstName", user.getFirstName());
        request.setAttribute("lastName", user.getLastName());
        request.setAttribute("email", user.getEmail());
        request.setAttribute("password", user.getPassword());
        request.setAttribute("gender", user.getGender());
        request.setAttribute("mobile", user.getMobile());
        request.setAttribute("address", user.getAddress());
        request.setAttribute("type", user.getType());
        request.setAttribute("position", user.getPosition());
    }
}