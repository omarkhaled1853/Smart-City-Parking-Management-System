package jdbcDemo.example.demo.service;

import jdbcDemo.example.demo.dao.LotDao;
import jdbcDemo.example.demo.entity.ParkingLot;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LotService {

    private final LotDao lotDao;

    public LotService(LotDao lotDao){
        this.lotDao = lotDao;
    }

    public List<ParkingLot> getAllLots(){
        return lotDao.getAllLots();
    }

    public ParkingLot getLotById(int id){
        return lotDao.getLotById(id);
    }
}
