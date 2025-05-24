package com.mycompany.labs.model;

import java.io.Serializable;
import java.util.Date;

public class Invoice implements Serializable {

    private int invoiceID;
    private int orderID;
    private double totalAmount;
    private Date issuedDate;
    private String status;

    public int getInvoiceID() { 
        return invoiceID; 
    }
    public void setInvoiceID(int invoiceID) { 
        this.invoiceID = invoiceID; 
    }

    public int getOrderID() { 
        return orderID; 
    }
    public void setOrderID(int orderID) { 
        this.orderID = orderID; 
    }

    public double getTotalAmount() { 
        return totalAmount; 
    }
    public void setTotalAmount(double totalAmount) { 
        this.totalAmount = totalAmount; 
    }

    public Date getIssuedDate() { 
        return issuedDate; 
    }
    public void setIssuedDate(Date issuedDate) { 
        this.issuedDate = issuedDate; 
    }

    public String getStatus() { 
        return status; 
    }
    public void setStatus(String status) { 
        this.status = status; 
    }

}