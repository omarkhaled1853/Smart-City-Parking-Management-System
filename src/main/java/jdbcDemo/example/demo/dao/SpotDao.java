package jdbcDemo.example.demo.dao;

import jdbcDemo.example.demo.entity.ParkingSpot;
import jdbcDemo.example.demo.enums.SpotStatus;
import jdbcDemo.example.demo.enums.SpotType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SpotDao {
    private final JdbcTemplate jdbcTemplate;

    public SpotDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ParkingSpot> getAllSpotsByLotId(int lotId){
        String query = "SELECT * FROM ParkingSpot WHERE ParkingLotID = ?";
        return jdbcTemplate.query(query, (rs, rowNum) -> {
           ParkingSpot parkingSpot = ParkingSpot.builder()
                   .SpotID(rs.getInt("SpotID"))
                   .ParkingLotID(rs.getInt("ParkingLotID"))
                   .Status(SpotStatus.valueOf(rs.getString("Status")))
                   .SpotType(SpotType.valueOf(rs.getString("SpotType")))
                   .PricePerHour(rs.getInt("PricePerHour"))
                   .build();

           return parkingSpot;
        }, lotId);
    }
    public ParkingSpot getSpotById(int id){
        String query = "SELECT * FROM ParkingSpot WHERE SpotID = ?";

        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
            ParkingSpot parkingSpot = ParkingSpot.builder()
                    .SpotID(rs.getInt("SpotID"))
                    .ParkingLotID(rs.getInt("ParkingLotID"))
                    .Status(SpotStatus.valueOf(rs.getString("Status")))
                    .SpotType(SpotType.valueOf(rs.getString("SpotType")))
                    .PricePerHour(rs.getInt("PricePerHour"))
                    .build();

            return parkingSpot;
        }, id);
    }
    public Boolean modifySpotStatus(int id, String status){
        String query = "UPDATE ParkingSpot SET Status = ? WHERE SpotID = ?";

        int rowAffected = jdbcTemplate.update(query, status, id);

        return rowAffected > 0;
    }
}
