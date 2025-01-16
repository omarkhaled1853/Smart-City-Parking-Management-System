package com.ParkingSystem.Parking.System.controller;

import com.ParkingSystem.Parking.System.dto.LoginDTO;
import com.ParkingSystem.Parking.System.dto.SignupDTO;
import com.ParkingSystem.Parking.System.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginDTO loginDTO) {
        Map<String, Object> response = new HashMap<>();
        if (loginDTO == null || loginDTO.getEmail() == null || loginDTO.getPassword() == null || loginDTO.getRole() == null) {
            response.put("status", "error");
            response.put("message", "Request body or required fields are missing.");
            return ResponseEntity.badRequest().body(response);
        }
    
        Map<String, Object> loginResult = userService.loginUser(loginDTO);
        String status = (String) loginResult.get("status");
        String message = (String) loginResult.get("message");
    
        response.put("status", status);
        response.put("message", message);
    
        if ("success".equals(status)) {
            response.put("userId", loginResult.get("userId")); // Include UserID in the response
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
    
}
