package com.mycompany.labs.controller;

import java.io.IOException;
import java.sql.SQLException;

import com.mycompany.labs.DAO.DeviceDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/deleteDevice")
public class DeleteDeviceController extends HttpServlet {
    private DeviceDAO dao;

    public DeleteDeviceController() throws SQLException {
        dao = new DeviceDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String deviceIdParam = request.getParameter("id");

        if (deviceIdParam != null && !deviceIdParam.isEmpty()) {
            try {
                int deviceId = Integer.parseInt(deviceIdParam);
                dao.deleteDevice(deviceId);
            } catch (NumberFormatException e) {
                System.err.println("Invalid device ID format: " + deviceIdParam);
            } catch (SQLException e) {
                throw new ServletException("Database error while deleting device", e);
            }
        }

        response.sendRedirect("devices");
    }
}


