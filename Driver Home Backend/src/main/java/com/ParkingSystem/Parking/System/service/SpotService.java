package com.ParkingSystem.Parking.System.service;

import com.ParkingSystem.Parking.System.dao.SpotDao;
import com.ParkingSystem.Parking.System.dto.ParkingSpot;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpotService {
    private final SpotDao spotDao;

    public SpotService(SpotDao spotDao){
        this.spotDao = spotDao;
    }
    public List<ParkingSpot> getAllSpotsByLotId(int lotId){
        return spotDao.getAllSpotsByLotId(lotId);
    }

    public Boolean updateSpotStatus(int spotId, String status){
        return spotDao.modifySpotStatus(spotId, status);
    }
}
