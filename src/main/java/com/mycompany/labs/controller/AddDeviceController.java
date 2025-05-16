package com.mycompany.labs.controller;

import com.mycompany.labs.DAO.DeviceDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/addDevice")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,     // 1MB
        maxFileSize = 1024 * 1024 * 5,                // 5MB
        maxRequestSize = 1024 * 1024 * 10)            // 10MB
public class AddDeviceController extends HttpServlet {
    private DeviceDAO dao;

    public AddDeviceController() throws SQLException {
        dao = new DeviceDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("deviceForm.jsp").forward(request, response);
    }
}
