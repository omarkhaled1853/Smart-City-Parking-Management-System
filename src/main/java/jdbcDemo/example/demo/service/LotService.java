package jdbcDemo.example.demo.service;

import jdbcDemo.example.demo.dao.LotDao;
import jdbcDemo.example.demo.entity.Lot;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LotService {

    private final LotDao lotDao;

    public LotService(LotDao lotDao){
        this.lotDao = lotDao;
    }

    public List<Lot> getAllLots(){
        return lotDao.getAllLots();
    }

    public Lot getLotById(int id){
        return lotDao.getLotById(id);
    }
}
