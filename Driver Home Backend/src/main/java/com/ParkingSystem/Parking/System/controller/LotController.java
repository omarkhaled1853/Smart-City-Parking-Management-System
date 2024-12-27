package com.ParkingSystem.Parking.System.controller;

import com.ParkingSystem.Parking.System.dto.ParkingLot;
import com.ParkingSystem.Parking.System.service.LotService;
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
    public List<ParkingLot> getAllLots(){
        return lotService.getAllLots();
    }
}
