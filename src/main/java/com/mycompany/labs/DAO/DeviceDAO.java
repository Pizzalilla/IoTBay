package com.mycompany.labs.DAO;

import com.mycompany.labs.model.Device;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeviceDAO {
    private Connection conn;

    public DeviceDAO() throws SQLException {
        this.conn = DB.getConnection();
    }

    // Create - insert a new device
    public void addDevice(Device device) throws SQLException {
        String sql = "INSERT INTO Device (deviceName, price, type, stockQty, imageName) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, device.getDeviceName());
            stmt.setDouble(2, device.getPrice());
            stmt.setString(3, device.getType());
            stmt.setInt(4, device.getStockQty());
            stmt.setString(5, device.getImageName());
            stmt.executeUpdate();
        }
    }

    // Read - get all devices
    public List<Device> getAllDevices() throws SQLException {
        List<Device> devices = new ArrayList<>();
        String sql = "SELECT * FROM Device";
        try  {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Device d = new Device(
                        rs.getInt("deviceId"),
                        rs.getString("deviceName"),
                        rs.getDouble("price"),
                        rs.getString("type"),
                        rs.getInt("stockQty"),
                        rs.getString("imageName")
                );
                devices.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return devices;
    }

    // Update - modify a device
    public void updateDevice(Device device) throws SQLException {
        String sql = "UPDATE Device SET deviceName = ?, price = ?, type = ?, stockQty = ?, imageName = ? WHERE deviceId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, device.getDeviceName());
            stmt.setDouble(2, device.getPrice());
            stmt.setString(3, device.getType());
            stmt.setInt(4, device.getStockQty());
            stmt.setString(5, device.getImageName());
            stmt.setInt(6, device.getDeviceId());
            stmt.executeUpdate();
        }
    }

    // Delete - remove a device
    public void deleteDevice(int deviceId) throws SQLException {
        String sql = "DELETE FROM Device WHERE deviceId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, deviceId);
            stmt.executeUpdate();
        }
    }

    public Device getDeviceById(int deviceId) throws SQLException {
        String sql = "SELECT * FROM Device WHERE deviceId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, deviceId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Device(
                            rs.getInt("deviceId"),
                            rs.getString("deviceName"),
                            rs.getDouble("price"),
                            rs.getString("type"),
                            rs.getInt("stockQty"),
                            rs.getString("imageName")
                    );
                }
            }
        }
        return null;
    }

    public static void main(String[] args) throws SQLException {
        DeviceDAO dao = new DeviceDAO();

        // Add a new device
        Device newDevice = new Device("Smart Plug", 29.99, "Power", 40, "smart_plug.png");
        dao.addDevice(newDevice);
        System.out.println("Device added.");

        // List all devices
        List<Device> devices = dao.getAllDevices();
        System.out.println("All Devices:");
        for (Device d : devices) {
            System.out.println(d);
        }

        // Get device by ID
        Device one = dao.getDeviceById(1);
        if (one != null) {
            System.out.println("Found device ID 1: " + one);
        }

        // Update a device
        if (one != null) {
            one.setStockQty(one.getStockQty() + 10);
            dao.updateDevice(one);
            System.out.println("Updated device stockQty.");

            Device updated = dao.getDeviceById(one.getDeviceId());
            System.out.println("Updated Device: " + updated);
        }

        // Delete last added device
        if (!devices.isEmpty()) {
            int lastId = devices.get(devices.size() - 1).getDeviceId();
            dao.deleteDevice(lastId);
            System.out.println("Deleted device with ID: " + lastId);
        }

    }
}