package com.example.Backend.controllers;
import com.example.Backend.DTO.LoginDTO;
import com.example.Backend.DTO.SignupDTO;
import com.example.Backend.services.UserService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")

   public ResponseEntity<Map<String, String>> signup(@RequestBody SignupDTO signupDTO) {
    Map<String, String> response = new HashMap<>();

    if (signupDTO == null || signupDTO.getEmail() == null || signupDTO.getPassword() == null || 
        signupDTO.getName() == null || signupDTO.getPhone() == null || signupDTO.getRole() == null) {
        response.put("status", "error");
        response.put("message", "Request body or required fields are missing.");
        return ResponseEntity.badRequest().body(response);
    }

    String serviceResponse = userService.registerUser(signupDTO);

    switch (serviceResponse) {
        case "User registered successfully with role:":
        case "Role added to existing user successfully.":
            response.put("status", "success");
            response.put("message", serviceResponse);
            return ResponseEntity.ok(response);

        case "Role is wrong":
        case "User with this role already exists.":
        case "Failed to register user.":
            response.put("status", "error");
            response.put("message", serviceResponse);
            return ResponseEntity.badRequest().body(response);

        default:
            response.put("status", "error");
            response.put("message", "Unknown error occurred.");
            return ResponseEntity.badRequest().body(response);
    }
}
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO loginDTO) {
        Map<String, String> response = new HashMap<>();
        if (loginDTO == null || loginDTO.getEmail() == null || loginDTO.getPassword() == null||loginDTO.getRole() == null) {
            response.put("status", "error");
            response.put("message", "Request body or required fields are missing.");
        return ResponseEntity.badRequest().body(response);
    }
    String isAuthenticated = userService.loginUser(loginDTO);
        switch (isAuthenticated) {
            case "Login successful":
                response.put("status", "success");
                response.put("message", isAuthenticated);
                return ResponseEntity.ok(response);
    
            case "Role is wrong":
            case "Password or email is incorrect.":
                response.put("status", "error");
                response.put("message", isAuthenticated);
                return ResponseEntity.badRequest().body(response);
    
            default:
                response.put("status", "error");
                response.put("message", "Unknown error occurred.");
                return ResponseEntity.badRequest().body(response);
        }
    }
}
