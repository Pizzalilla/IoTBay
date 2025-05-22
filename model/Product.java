package com.mycompany.labs.model;

import java.io.Serializable;

public class Product implements Serializable {

    private int productID;
    private int supplierID;
    private String name;
    private String description;
    private double price;
    private int stock;
    private String category;

    public int getProductID() { 
        return productID; 
    }
    public void setProductID(int productID) { 
        this.productID = productID; 
    }

    public int getSupplierID() { 
        return supplierID; 
    }
    public void setSupplierID(int supplierID) { 
        this.supplierID = supplierID;
    }

    public String getName() { 
        return name; 
    }
    public void setName(String name) { 
        this.name = name; 
    }

    public String getDescription() { 
        return description; 
    }
    public void setDescription(String description) { 
        this.description = description; 
    }

    public double getPrice() { 
        return price; 
    }
    public void setPrice(double price) { 
        this.price = price; 
    }

    public int getStock() { 
        return stock; 
    }
    public void setStock(int stock) { 
        this.stock = stock; 
    }

    public String getCategory() { 
        return category; 
    }
    public void setCategory(String category) { 
        this.category = category; 
    }

}