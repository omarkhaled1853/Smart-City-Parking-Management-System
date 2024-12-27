package com.ParkingSystem.Parking.System.controller;

import com.ParkingSystem.Parking.System.dto.HomeParkingLotDTO;
import com.ParkingSystem.Parking.System.service.LotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<HomeParkingLotDTO> getAllLots(){
        return lotService.getAllLots();
    }
}
