package com.ParkingSystem.Parking.System.dao;

import com.ParkingSystem.Parking.System.dto.HomeParkingSpotDTO;
import com.ParkingSystem.Parking.System.enums.SpotStatus;
import com.ParkingSystem.Parking.System.enums.SpotType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SpotDao {
    private final JdbcTemplate jdbcTemplate;

    public SpotDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<HomeParkingSpotDTO> getAllSpotsByLotId(int lotId){
        String query = "SELECT * FROM ParkingSpot WHERE ParkingLotID = ?";
        return jdbcTemplate.query(query, (rs, rowNum) -> {
           HomeParkingSpotDTO homeParkingSpotDTO = HomeParkingSpotDTO.builder()
                   .SpotID(rs.getInt("SpotID"))
                   .ParkingLotID(rs.getInt("ParkingLotID"))
                   .Status(SpotStatus.valueOf(rs.getString("Status")))
                   .SpotType(SpotType.valueOf(rs.getString("SpotType")))
                   .PricePerHour(rs.getInt("PricePerHour"))
                   .build();

           return homeParkingSpotDTO;
        }, lotId);
    }
    public HomeParkingSpotDTO getSpotById(int id){
        String query = "SELECT * FROM ParkingSpot WHERE SpotID = ?";

        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
            HomeParkingSpotDTO homeParkingSpotDTO = HomeParkingSpotDTO.builder()
                    .SpotID(rs.getInt("SpotID"))
                    .ParkingLotID(rs.getInt("ParkingLotID"))
                    .Status(SpotStatus.valueOf(rs.getString("Status")))
                    .SpotType(SpotType.valueOf(rs.getString("SpotType")))
                    .PricePerHour(rs.getInt("PricePerHour"))
                    .build();

            return homeParkingSpotDTO;
        }, id);
    }
    public Boolean modifySpotStatus(int id, String status){
        String query = "UPDATE ParkingSpot SET Status = ? WHERE SpotID = ?";

        int rowAffected = jdbcTemplate.update(query, status, id);

        return rowAffected > 0;
    }
}
