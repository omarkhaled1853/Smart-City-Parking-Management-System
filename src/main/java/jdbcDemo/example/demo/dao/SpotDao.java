package jdbcDemo.example.demo.dao;

import jdbcDemo.example.demo.entity.Spot;
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
        String query = "SELECT * FROM parkingSpot WHERE lotId = ?";
        return jdbcTemplate.query(query, (rs, rowNum) -> {
           Spot spot = Spot.builder()
                   .id(rs.getInt("id"))
                   .lotId(rs.getInt("lotId"))
                   .status(rs.getString("status"))
                   .type(rs.getString("type"))
                   .pricePerHour(rs.getInt("pricePerHour"))
                   .build();

           return spot;
        }, lotId);
    }
    public Spot getSpotById(int id){
        String query = "SELECT * FROM parkingSpot WHERE id = ?";

        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
            Spot spot = Spot.builder()
                    .id(rs.getInt("id"))
                    .lotId(rs.getInt("lotId"))
                    .status(rs.getString("status"))
                    .type(rs.getString("type"))
                    .pricePerHour(rs.getInt("pricePerHour"))
                    .build();

            return spot;
        }, id);
    }
    public Boolean modifySpotStatus(int id, String status){
        String query = "UPDATE parkingSpot SET status = ? WHERE id = ?";

        int rowAffected = jdbcTemplate.update(query, status, id);

        return rowAffected > 0;
    }
}
