package com.mycompany.labs.model;

import java.io.Serializable;

public class OrderProduct implements Serializable {

    private int productID;
    private int orderID;
    private int quantity;
    private double unitPrice;

    public int getProductID() { 
        return productID; 
    }
    public void setProductID(int productID) { 
        this.productID = productID; 
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

}