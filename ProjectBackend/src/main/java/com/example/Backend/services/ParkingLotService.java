package com.example.Backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ParkingLotService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ParkingLotService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String updateSpotPricing(int spotID, double demandFactor) {
        try {
            // Call the stored procedure to update the pricing for a specific spot
            String sql = "{CALL UpdateSpotPricing(?, ?)}";
            jdbcTemplate.update(sql, spotID, demandFactor);
            return "Parking spot pricing updated successfully.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }


}