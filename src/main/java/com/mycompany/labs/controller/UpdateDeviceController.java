package com.mycompany.labs.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import com.mycompany.labs.DAO.DeviceDAO;
import com.mycompany.labs.model.Device;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/updateDevice")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,     // 1MB
        maxFileSize = 1024 * 1024 * 5,       // 5MB
        maxRequestSize = 1024 * 1024 * 10)   // 10MB
public class UpdateDeviceController extends HttpServlet {
    DeviceDAO dao;
    private static final AtomicInteger idCounter = new AtomicInteger(1);

    public UpdateDeviceController() throws SQLException {
        dao = new DeviceDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            Device device = dao.getDeviceById(id);
            request.setAttribute("device", device);
            request.getRequestDispatcher("deviceForm.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String deviceIdParam = request.getParameter("deviceId");
        String deviceName = request.getParameter("deviceName");
        double price = Double.parseDouble(request.getParameter("price"));
        String type = request.getParameter("type");
        int stockQty = Integer.parseInt(request.getParameter("stockQty"));

        Part imagePart = request.getPart("image");
        String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();

        // Save image to both runtime and source directories
        if (!fileName.isEmpty()) {
            // Runtime path (under target/)
            String runtimePath = getServletContext().getRealPath("/images");

            // Source path (src/main/webapp/images)
            String sourcePath = runtimePath.split("target")[0] + "src/main/webapp/images";

            File runtimeDir = new File(runtimePath);
            File sourceDir = new File(sourcePath);

            if (!runtimeDir.exists()) runtimeDir.mkdirs();
            if (!sourceDir.exists()) sourceDir.mkdirs();

            File runtimeFile = new File(runtimeDir, fileName);
            File sourceFile = new File(sourceDir, fileName);

            try (InputStream input = imagePart.getInputStream()) {
                Files.copy(input, runtimeFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }
            try (InputStream input2 = imagePart.getInputStream()) {
                Files.copy(input2, sourceFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }
        }

        Device device = null;
        try {
            if (deviceIdParam != null && !deviceIdParam.isEmpty()) {
                // Update flow
                int deviceId = Integer.parseInt(deviceIdParam);
                device = dao.getDeviceById(deviceId);
                device.setDeviceName(deviceName);
                device.setPrice(price);
                device.setStockQty(stockQty);
                device.setImageName(fileName);
                dao.updateDevice(device);
            } else {
                // Create flow (just in case)
                device = new Device(0, deviceName, price, type, stockQty, fileName);
                dao.addDevice(device);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect("devices");
    }
}
