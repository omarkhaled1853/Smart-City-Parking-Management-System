package com.ParkingSystem.Parking.System.service;

import com.ParkingSystem.Parking.System.dao.LotDao;
import com.ParkingSystem.Parking.System.dto.HomeParkingLotDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LotService {

    private final LotDao lotDao;

    public LotService(LotDao lotDao){
        this.lotDao = lotDao;
    }

    public List<HomeParkingLotDTO> getAllLots(){
        return lotDao.getAllLots();
    }

    public HomeParkingLotDTO getLotById(int id){
        return lotDao.getLotById(id);
    }
}
