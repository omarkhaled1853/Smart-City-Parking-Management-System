package com.ParkingSystem.Parking.System.dao;

import com.ParkingSystem.Parking.System.dto.HomeParkingLotDTO;
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

    public List<HomeParkingLotDTO> getAllLots(){
        String query = "SELECT * FROM ParkingLot";

        return jdbcTemplate.query(query,  (rs, rowNum) ->{
            HomeParkingLotDTO homeParkingLotDTO = HomeParkingLotDTO.builder()
                    .ParkingLotID(rs.getInt("ParkingLotID"))
                    .Name(rs.getString("Name"))
                    .Location(rs.getString("Location"))
                    .Capacity(rs.getInt("Capacity"))
                    .build();

            return homeParkingLotDTO;

        } );
    }

    public HomeParkingLotDTO getLotById(int id){
        String query = "SELECT * FROM ParkingLot WHERE ParkingLotID = ?";
        try {
            return jdbcTemplate.queryForObject(query, (rs, rowNum) ->{
                HomeParkingLotDTO homeParkingLotDTO = HomeParkingLotDTO.builder()
                        .ParkingLotID(rs.getInt("ParkingLotID"))
                        .Name(rs.getString("Name"))
                        .Location(rs.getString("Location"))
                        .Capacity(rs.getInt("Capacity"))
                        .build();

                return homeParkingLotDTO;
            }, id);

        }catch (DataAccessException e){
            return null;
        }
    }

}
