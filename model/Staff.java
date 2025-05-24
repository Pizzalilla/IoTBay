package com.mycompany.labs.model;

import java.io.Serializable;

public class Staff implements Serializable {

    private int staffID;
    private String name;
    private String email;
    private String password;
    private String role;

    public int getStaffID() { 
        return staffID; 
    }
    public void setStaffID(int staffID) { 
        this.staffID = staffID; 
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

    public String getRole() { 
        return role; 
    }
    public void setRole(String role) { 
        this.role = role; 
    }

}