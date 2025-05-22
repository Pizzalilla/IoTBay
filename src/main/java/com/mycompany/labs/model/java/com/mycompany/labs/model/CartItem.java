package com.mycompany.labs.model;

import java.io.Serializable;
import java.util.Date;

public class CartItem implements Serializable {

    private int cartItemID;
    private int customerID;
    private int productID;
    private int quantity;
    private Date dateAdded;

    public int getCartItemID() { 
        return cartItemID; 
    }
    public void setCartItemID(int cartItemID) { 
        this.cartItemID = cartItemID; 
    }

    public int getCustomerID() {
        return customerID;
    }
    public void setCustomerID(int customerID) { 
        this.customerID = customerID;
    }

    public int getProductID() { 
        return productID; 
    }
    public void setProductID(int productID) { 
        this.productID = productID; 
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