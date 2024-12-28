package com.example.Backend.DTO;



public class LoginDTO {
    private String email;
    private String password;
    private String role;


    public String getEmail(){
        return email;
    }
    public String getRole(){
        return role;
    }
    public String getPassword(){
        return password;
    }
}
