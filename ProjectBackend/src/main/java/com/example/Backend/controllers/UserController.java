package com.example.Backend.controllers;
import com.example.Backend.DTO.LoginDTO;
import com.example.Backend.DTO.SignupDTO;
import com.example.Backend.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    @CrossOrigin(origins = "http://localhost:5173") // Allow specific origin
    public ResponseEntity<String> signup(@RequestBody SignupDTO signupDTO) {
        if (signupDTO == null || signupDTO.getEmail() == null || signupDTO.getPassword() == null ||signupDTO.getName()==null ||signupDTO.getPhone()==null ||signupDTO.getRole()==null) {
            return ResponseEntity.badRequest().body("Request body or required fields are missing.");
        }
        String response = userService.registerUser(signupDTO);
        if (response.equals("User registered successfully.")) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }
    
    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:5173") // Allow specific origin
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        if (loginDTO == null || loginDTO.getEmail() == null || loginDTO.getPassword() == null) {
        return ResponseEntity.badRequest().body("Request body or required fields are missing.");
    }
        boolean isAuthenticated = userService.loginUser(loginDTO);
        if (isAuthenticated) {
            return ResponseEntity.ok("Login successful.");
        }
        return ResponseEntity.badRequest().body("Invalid email or password.");
    }
}
