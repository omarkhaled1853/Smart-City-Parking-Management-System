package com.ParkingSystem.Parking.System.service;

import com.ParkingSystem.Parking.System.dto.LoginDTO;
import com.ParkingSystem.Parking.System.dto.SignupDTO;
import com.ParkingSystem.Parking.System.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class 
UserService {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Autowired
    private UserRepository userRepository;

    public String registerUser(SignupDTO signupDTO) {
        // Check if email already exists
        if (userRepository.emailExists(signupDTO.getEmail())) {
            // Get the user ID by email
            int userID = userRepository.getUserIdByEmail(signupDTO.getEmail());
            String roleName = signupDTO.getRole(); // The role passed from the frontend
            if (!userRepository.nameisright(roleName)){
                return "Role is wrong";
            }
            Integer roleId = userRepository.getRoleIdByRoleName(roleName); // Get RoleID for the role
            // Check if the user already has the role
            if (userRepository.roleExists(userID, roleId)) {
                return "User with this role already exists.";
            }
            System.out.println(roleId +"ggggggggggggggg");

            // Insert new UserRole if role is missing
            String insertUserRoleSql = "INSERT INTO UserRoles (UserID, RoleID) VALUES (?, ?)";
            jdbcTemplate.update(insertUserRoleSql, userID, roleId);

            return "Role added to existing user successfully.";
        } else {
            // Insert the user into the User table and get the UserID
            String insertUserSql = "INSERT INTO User (Name, Email, Password, Phone, LicensePlate) VALUES (?, ?, ?, ?, ?)";
            int rowsInserted = jdbcTemplate.update(insertUserSql,
                    signupDTO.getName(),
                    signupDTO.getEmail(),
                    signupDTO.getPassword(),
                    signupDTO.getPhone(),
                    signupDTO.getLicensePlate()
            );

            // If the user is inserted successfully
            if (rowsInserted > 0) {
                // Get the user ID after insertion
                Integer userId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
                String roleName = signupDTO.getRole(); // Role passed in the DTO
                Integer roleId = userRepository.getRoleIdByRoleName(roleName); // Get the role ID from the database

                // Insert the role into UserRoles
                String insertUserRoleSql = "INSERT INTO UserRoles (UserID, RoleID) VALUES (?, ?)";
                jdbcTemplate.update(insertUserRoleSql, userId, roleId);

                return "User registered successfully with role:";
            } else {
                return "Failed to register user.";
            }
        }
    }


    public Map<String, Object> loginUser(LoginDTO loginDTO) {
    Map<String, Object> result = new HashMap<>();
    if (userRepository.authenticateUser(loginDTO.getEmail(), loginDTO.getPassword())) {
        int userId = userRepository.getUserIdByEmail(loginDTO.getEmail());
        String roleName = loginDTO.getRole(); // The role passed from the frontend
        Integer roleId = userRepository.getRoleIdByRoleName(roleName); // Get RoleID for the role

        // Check if the user already has the role
        if (userRepository.roleExists(userId, roleId)) {
            result.put("status", "success");
            result.put("message", "Login successful");
            result.put("userId", userId); // Include UserID in the result
        } else {
            result.put("status", "error");
            result.put("message", "Role is wrong");
        }
    } else {
        result.put("status", "error");
        result.put("message", "Password or email is incorrect.");
    }
    return result;
}

}
