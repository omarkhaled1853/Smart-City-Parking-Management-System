package com.ParkingSystem.Parking.System.repository;

import com.ParkingSystem.Parking.System.dto.SignupDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void checkJdbcTemplate() {
    System.out.println("JdbcTemplate bean initialized: " + (jdbcTemplate != null));
    }
    
    // Insert user into the database
    public int insertUser(SignupDTO signupDTO) {
        String sql = "INSERT INTO User (Name, Email, Password, Phone, LicensePlate) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                signupDTO.getName(),
                signupDTO.getEmail(),
                signupDTO.getPassword(), // NOTE: Hash password in real apps
                signupDTO.getPhone(),
                signupDTO.getLicensePlate()
        );
    }

    // Fetch role ID based on role name
    public Integer getRoleIdByRoleName(String roleName) {
        String sql = "SELECT RoleID FROM Role WHERE RoleName = ?";

        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, roleName);
        } catch (EmptyResultDataAccessException e) {
            // If no result is found, return null or throw a custom exception
            return null;  // or throw new RoleNotFoundException("Role not found: " + roleName);
        }
    }

    public boolean roleExists(int userid, int roleid) {
        String sql = "SELECT COUNT(*) FROM UserRoles WHERE UserID = ? AND RoleID = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userid,roleid);
        return count != null && count > 0;
    }
    public boolean nameisright(String roleName) {
        String sql = "SELECT COUNT(*) FROM Role WHERE RoleName = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, roleName);
        return count != null && count > 0;
    }

    // i want function return only managers
    public List<Map<String, Object>> getAllManagers() {
    String sql = "SELECT * FROM User WHERE UserID IN (SELECT UserID FROM UserRoles WHERE RoleID = 2)";
    return jdbcTemplate.queryForList(sql);
}

    




    // Check if email already exists
    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM User WHERE Email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }
    public Integer getUserIdByEmail(String email) {
        String sql = "SELECT UserID FROM User WHERE Email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{email}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            // No user found with the given email
            return 0;
        }
    }


    // Authenticate user
    public boolean authenticateUser(String email, String password) {
        String sql = "SELECT COUNT(*) FROM User WHERE Email = ? AND Password = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email, password);
        return count != null && count > 0;
    }
}
