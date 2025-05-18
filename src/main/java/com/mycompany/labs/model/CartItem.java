package com.mycompany.labs.model;

import java.io.Serializable;
import java.util.Date;

public class CartItem implements Serializable {
    private int cartItemID;
    private Integer customerID;
    private int deviceID;
    private int quantity;
    private Date dateAdded;

    public CartItem() {}

    public CartItem(int cartItemID, Integer customerID, int deviceID, int quantity, Date dateAdded) {
        this.cartItemID = cartItemID;
        this.customerID = customerID;
        this.deviceID = deviceID;
        this.quantity = quantity;
        this.dateAdded = dateAdded;
    }

    public int getCartItemID() { 
        return cartItemID; 
    }
    public void setCartItemID(int cartItemID) { 
        this.cartItemID = cartItemID; 
    }

    public Integer getCustomerID() { 
        return customerID; 
    }
    public void setCustomerID(Integer customerID) { 
        this.customerID = customerID; 
    }

    public int getDeviceID() { 
        return deviceID; 
    }
    public void setDeviceID(int deviceID) { 
        this.deviceID = deviceID; 
    }

    public int getQuantity() { 
        return quantity;
    }
    public void setQuantity(int quantity) { 
        this.quantity = quantity; 
    }

    public Date getDateAdded() {
        return dateAdded; 
    }
    public void setDateAdded(Date dateAdded) {
         this.dateAdded = dateAdded; 
    }
}
