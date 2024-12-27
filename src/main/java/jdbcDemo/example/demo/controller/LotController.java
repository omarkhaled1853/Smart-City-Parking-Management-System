package jdbcDemo.example.demo.controller;

import jdbcDemo.example.demo.service.LotService;
import jdbcDemo.example.demo.entity.Lot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/garages")
public class LotController {
    private final LotService lotService;

    @Autowired
    public LotController(LotService lotService){
        this.lotService = lotService;
    }

    @GetMapping
    public List<Lot> getAllLots(){
        return lotService.getAllLots();
    }
}
