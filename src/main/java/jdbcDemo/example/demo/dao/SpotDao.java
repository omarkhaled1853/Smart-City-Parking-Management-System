package jdbcDemo.example.demo.dao;

import jdbcDemo.example.demo.entity.Spot;
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

    public List<Spot> getAllSpotsByLotId(int lotId){
        String query = "SELECT * FROM ParkingSpot WHERE ParkingLotID = ?";
        return jdbcTemplate.query(query, (rs, rowNum) -> {
           Spot spot = Spot.builder()
                   .id(rs.getInt("SpotID"))
                   .lotId(rs.getInt("ParkingLotID"))
                   .status(SpotStatus.valueOf(rs.getString("Status")))
                   .type(SpotType.valueOf(rs.getString("SpotType")))
                   .pricePerHour(rs.getInt("PricePerHour"))
                   .build();

           return spot;
        }, lotId);
    }
    public Spot getSpotById(int id){
        String query = "SELECT * FROM ParkingSpot WHERE SpotID = ?";

        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
            Spot spot = Spot.builder()
                    .id(rs.getInt("SpotID"))
                    .lotId(rs.getInt("ParkingLotID"))
                    .status(SpotStatus.valueOf(rs.getString("Status")))
                    .type(SpotType.valueOf(rs.getString("SpotType")))
                    .pricePerHour(rs.getInt("PricePerHour"))
                    .build();

            return spot;
        }, id);
    }
    public Boolean modifySpotStatus(int id, String status){
        String query = "UPDATE ParkingSpot SET Status = ? WHERE SpotID = ?";

        int rowAffected = jdbcTemplate.update(query, status, id);

        return rowAffected > 0;
    }
}
