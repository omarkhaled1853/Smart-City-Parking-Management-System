package jdbcDemo.example.demo.dao;

import jdbcDemo.example.demo.entity.ParkingLot;
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

    public List<ParkingLot> getAllLots(){
        String query = "SELECT * FROM ParkingLot";

        return jdbcTemplate.query(query,  (rs, rowNum) ->{
            ParkingLot parkingLot = ParkingLot.builder()
                    .ParkingLotID(rs.getInt("ParkingLotID"))
                    .Name(rs.getString("Name"))
                    .Location(rs.getString("Location"))
                    .Capacity(rs.getInt("Capacity"))
                    .build();

            return parkingLot;

        } );
    }

    public ParkingLot getLotById(int id){
        String query = "SELECT * FROM ParkingLot WHERE ParkingLotID = ?";
        try {
            return jdbcTemplate.queryForObject(query, (rs, rowNum) ->{
                ParkingLot parkingLot = ParkingLot.builder()
                        .ParkingLotID(rs.getInt("ParkingLotID"))
                        .Name(rs.getString("Name"))
                        .Location(rs.getString("Location"))
                        .Capacity(rs.getInt("Capacity"))
                        .build();

                return parkingLot;
            }, id);

        }catch (DataAccessException e){
            return null;
        }
    }

}
