package com.mycompany.labs.model;

import java.io.Serializable;
import java.util.Date;

public class Payment implements Serializable {

    private int paymentID;
    private int orderID;
    private double totalPrice;
    private String paymentMethod;
    private Date paymentDate;
    private String status;

    public int getPaymentID() { 
        return paymentID; 
    }
    public void setPaymentID(int paymentID) { 
        this.paymentID = paymentID; 
    }

    public int getOrderID() { 
        return orderID; 
    }
    public void setOrderID(int orderID) { 
        this.orderID = orderID; 
    }

    public double getTotalPrice() { 
        return totalPrice; 
    }
    public void setTotalPrice(double totalPrice) { 
        this.totalPrice = totalPrice; 
    }

    public String getPaymentMethod() { 
        return paymentMethod; 
    }
    public void setPaymentMethod(String paymentMethod) { 
        this.paymentMethod = paymentMethod; 
    }

    public Date getPaymentDate() { 
        return paymentDate; 
    }
    public void setPaymentDate(Date paymentDate) { 
        this.paymentDate = paymentDate; 
    }

    public String getStatus() { 
        return status; 
    }
    public void setStatus(String status) { 
        this.status = status; 
    }

}