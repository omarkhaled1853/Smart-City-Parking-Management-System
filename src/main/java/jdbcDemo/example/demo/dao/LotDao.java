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
        String query = "SELECT * FROM parkingLot";

        return jdbcTemplate.query(query,  (rs, rowNum) ->{
            Lot lot = Lot.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .location(rs.getString("location"))
                    .capacity(rs.getInt("capacity"))
                    .build();

            return lot;

        } );
    }

    public Lot getLotById(int id){
        String query = "SELECT * FROM parkingLot WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(query, (rs, rowNum) ->{
                Lot lot = Lot.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .location(rs.getString("location"))
                        .capacity(rs.getInt("capacity"))
                        .build();

                return lot;
            }, id);

        }catch (DataAccessException e){
            return null;
        }
    }

}
