package com.mycompany.labs.model;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {

    private int orderID;
    private int customerID;
    private Date orderDate;
    private String status;

    public Order() {}

    public Order(int orderID, int customerID, Date orderDate, String status) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.orderDate = orderDate;
        this.status = status;
    }

    public int getOrderID() { 
        return orderID; 
    }
    public void setOrderID(int orderID) { 
        this.orderID = orderID; 
    }

    public int getCustomerID() { 
        return customerID; 
    }
    public void setCustomerID(int customerID) { 
        this.customerID = customerID; 
    }

    public Date getOrderDate() { 
        return orderDate; 
    }
    public void setOrderDate(Date orderDate) { 
        this.orderDate = orderDate; 
    }

    public String getStatus() { 
        return status; 
    }
    public void setStatus(String status) { 
        this.status = status; 
    }

}