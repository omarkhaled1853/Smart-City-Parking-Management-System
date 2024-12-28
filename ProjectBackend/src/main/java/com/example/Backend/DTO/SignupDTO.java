package com.example.Backend.DTO;

import java.util.List;

public class SignupDTO {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String role;
    private String licensePlate; // Optional for non-drivers
  
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public String getLicensePlate(){
        return licensePlate;
    }
    public String getPhone(){
        return phone;
    }

    public String getRole(){
        return role;
    }
}
