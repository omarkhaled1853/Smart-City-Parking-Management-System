package jdbcDemo.example.demo.service;

import jdbcDemo.example.demo.dao.LotDao;
import jdbcDemo.example.demo.dao.SpotDao;
import jdbcDemo.example.demo.entity.Lot;
import jdbcDemo.example.demo.entity.Spot;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LotService {

    private final LotDao lotDao;
    private final SpotDao spotDao;

    public LotService(LotDao lotDao, SpotDao spotDao){
        this.lotDao = lotDao;
        this.spotDao = spotDao;
    }

    public List<Lot> getAllLots(){
        return lotDao.getAllLots();
    }

    public Lot getLotById(int id){
        return lotDao.getLotById(id);
    }

    public List<Spot> getAllSpotsByLotId(int lotId){
        return spotDao.getAllSpotsByLotId(lotId);
    }

    public Boolean updateSpotStatus(int spotId, String status){
        return spotDao.modifySpotStatus(spotId, status);
    }

}
