package com.mycompany.labs.model;

import java.io.Serializable;
import java.util.Date;

public class Shipment implements Serializable {

    private int shipmentID;
    private int orderID;
    private String trackingNumber;
    private String carrier;
    private Date shippedDate;
    private String status;

    public int getShipmentID() { 
        return shipmentID; 
    }
    public void setShipmentID(int shipmentID) { 
        this.shipmentID = shipmentID; 
    }

    public int getOrderID() { 
        return orderID; 
    }
    public void setOrderID(int orderID) { 
        this.orderID = orderID; 
    }

    public String getTrackingNumber() { 
        return trackingNumber; 
    }
    public void setTrackingNumber(String trackingNumber) { 
        this.trackingNumber = trackingNumber; 
    }

    public String getCarrier() { 
        return carrier; 
    }
    public void setCarrier(String carrier) { 
        this.carrier = carrier; 
    }

    public Date getShippedDate() { 
        return shippedDate; 
    }
    public void setShippedDate(Date shippedDate) { 
        this.shippedDate = shippedDate; 
    }

    public String getStatus() { 
        return status; 
    }
    public void setStatus(String status) { 
        this.status = status; 
    }

}