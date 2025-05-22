package com.mycompany.labs.model;

import java.io.Serializable;

public class OrderProduct implements Serializable {

    private int deviceID;
    private int orderID;
    private int quantity;
    private double unitPrice;

    // Optional display fields
    private String deviceName;

    public int getDeviceID() {
        return deviceID;
    }
    public void setDeviceID(int deviceID) {
        this.deviceID = deviceID;
    }

    public int getOrderID() {
        return orderID;
    }
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getDeviceName() {
        return deviceName;
    }
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public double getSubtotal() {
        return unitPrice * quantity;
    }
}
