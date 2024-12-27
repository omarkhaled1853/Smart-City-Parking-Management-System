package jdbcDemo.example.demo.service;

import jdbcDemo.example.demo.dao.SpotDao;
import jdbcDemo.example.demo.entity.ParkingSpot;
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
