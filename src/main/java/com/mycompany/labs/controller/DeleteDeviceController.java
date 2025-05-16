package com.mycompany.labs.controller;

import com.mycompany.labs.DAO.DeviceDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

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


