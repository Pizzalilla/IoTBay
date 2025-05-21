package com.mycompany.labs.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.mycompany.labs.DAO.DeviceDAO;
import com.mycompany.labs.model.Device;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/devices")
public class DeviceController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            DeviceDAO dao = new DeviceDAO();
            List<Device> deviceList = dao.getAllDevices();

            String keyword = request.getParameter("keyword");
            if (keyword != null) {
                deviceList = deviceList.stream().filter(device ->
                        device.getDeviceName().toLowerCase().contains(keyword.toLowerCase()) ||
                        device.getType().toLowerCase().contains(keyword.toLowerCase()))
                        .collect(Collectors.toList());
            }

            // 将设备列表添加到 request 属性中
            request.setAttribute("devices", deviceList);

            // 跳转到 JSP 页面显示数据
            request.getRequestDispatcher("/devices.jsp").forward(request, response);

        } catch (Exception e) {
            // 错误处理：转到错误页或打印日志
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
