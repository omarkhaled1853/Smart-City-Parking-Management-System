package jdbcDemo.example.demo.dao;

import jdbcDemo.example.demo.entity.Lot;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LotDao {

    private final JdbcTemplate jdbcTemplate;
    public LotDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Lot> getAllLots(){
        String query = "SELECT * FROM ParkingLot";

        return jdbcTemplate.query(query,  (rs, rowNum) ->{
            Lot lot = Lot.builder()
                    .id(rs.getInt("ParkingLotID"))
                    .name(rs.getString("Name"))
                    .location(rs.getString("Location"))
                    .capacity(rs.getInt("Capacity"))
                    .build();

            return lot;

        } );
    }

    public Lot getLotById(int id){
        String query = "SELECT * FROM ParkingLot WHERE ParkingLotID = ?";
        try {
            return jdbcTemplate.queryForObject(query, (rs, rowNum) ->{
                Lot lot = Lot.builder()
                        .id(rs.getInt("ParkingLotID"))
                        .name(rs.getString("Name"))
                        .location(rs.getString("Location"))
                        .capacity(rs.getInt("Capacity"))
                        .build();

                return lot;
            }, id);

        }catch (DataAccessException e){
            return null;
        }
    }

}
