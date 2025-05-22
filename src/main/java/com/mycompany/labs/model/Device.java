package com.mycompany.labs.model;

public class Device {
    private int deviceId;
    private String deviceName;
    private double price;
    private String type;
    private int stockQty;
    private String imageName;

    // Constructor with all fields
    public Device(int deviceId, String deviceName, double price, String type, int stockQty, String imageName) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.price = price;
        this.type = type;
        this.stockQty = stockQty;
        this.imageName = imageName;
}
    public Device(String deviceName, double price, String type, int stockQty, String imageName) {
        this.deviceName = deviceName;
        this.price = price;
        this.type = type;
        this.stockQty = stockQty;
        this.imageName = imageName;
    }

    // Getters and Setters
    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStockQty() {
        return stockQty;
    }

    public void setStockQty(int stockQty) {
        this.stockQty = stockQty;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public String toString() {
        return "Device{" +
                "deviceId=" + deviceId +
                ", deviceName='" + deviceName + '\'' +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", stockQty=" + stockQty +
                ", imageName='" + imageName + '\'' +
                '}';
    }
}