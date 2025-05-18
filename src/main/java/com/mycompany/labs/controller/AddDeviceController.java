package com.mycompany.labs.controller;

import java.io.IOException;
import java.sql.SQLException;

import com.mycompany.labs.DAO.DeviceDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
