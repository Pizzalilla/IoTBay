package com.mycompany.labs.model;

import java.io.Serializable;

public class Customer implements Serializable {

    private int customerID;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;

    public int getCustomerID() { 
        return customerID; 
    }
    public void setCustomerID(int customerID) { 
        this.customerID = customerID; 
    }

    public String getName() { 
        return name; 
    }
    public void setName(String name) { 
        this.name = name; 
    }

    public String getEmail() { 
        return email; 
    }
    public void setEmail(String email) { 
        this.email = email; 
    }

    public String getPassword() { 
        return password; 
    }
    public void setPassword(String password) { 
        this.password = password; 
    }

    public String getPhone() { 
        return phone; 
    }
    public void setPhone(String phone) { 
        this.phone = phone; 
    }

    public String getAddress() { 
        return address; 
    }
    public void setAddress(String address) { 
        this.address = address; 
    }

}