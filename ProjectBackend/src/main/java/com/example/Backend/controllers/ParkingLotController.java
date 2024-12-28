package com.example.Backend.controllers;



import com.example.Backend.DTO.LotDTO;
import com.example.Backend.DTO.SpotDTO;
import com.example.Backend.Repository.ParkingLotRepository;
import com.example.Backend.Repository.ParkingSpotRepository;
import com.example.Backend.services.ParkingLotService;
import com.example.Backend.services.ReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager")
public class ParkingLotController {
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingSpotRepository parkingSpotRepository;
    private final ReportService reportService;
    private final ParkingLotService parkingLotService;

    public ParkingLotController(ParkingLotService parkingLotService,ParkingLotRepository parkingLotDAO, ParkingSpotRepository parkingSpotDAO, ReportService reportService) {
        this.parkingLotRepository = parkingLotDAO;
        this.parkingSpotRepository = parkingSpotDAO;
        this.reportService = reportService;
        this.parkingLotService = parkingLotService;
    }

    // Parking Lot Endpoints
    @PostMapping("/parkinglots")
    public String addParkingLot(@RequestBody LotDTO parkingLot) {
        try {
            parkingLotRepository.addParkingLot(parkingLot);
            return "Parking lot added successfully.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/parkinglots")
    public List<LotDTO> getAllParkingLots() {
        try {
            return parkingLotRepository.getAllParkingLots();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching parking lots: " + e.getMessage());
        }
    }
    @GetMapping("/parkinglots/{id}")
    public List<LotDTO> getSpesficParkingLots(@PathVariable int id) {
        try {
            return parkingLotRepository.getSpesficParkingLots(id);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching parking lots: " + e.getMessage());
        }
    }

    @DeleteMapping("/parkinglots/{id}")
    public String deleteParkingLot(@PathVariable int id) {
        try {
            parkingLotRepository.deleteParkingLot(id);
            System.out.println("Parking lot deleted successfully.");
            return "Parking lot deleted successfully.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // Parking Spot Endpoints
    @PostMapping("/parkingspots")
    public String addParkingSpot(@RequestBody SpotDTO parkingSpot) {
        try {
            parkingSpotRepository.addParkingSpot(parkingSpot);
            return "Parking spot added successfully.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // @GetMapping("/parkingspots")
    // public List<ParkingSpotDTO> getAllParkingSpots() {
    //     try {
    //         return parkingSpotRepository.getAllParkingSpots();
    //     } catch (Exception e) {
    //         throw new RuntimeException("Error fetching parking spots: " + e.getMessage());
    //     }
    // }
    @GetMapping("/parkingspots")
    public List<SpotDTO> getSpecificParkingSpots(@RequestParam(required = false) Integer lotId) {
        try {
            return parkingSpotRepository.getSpecificParkingSpots(lotId);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching parking spots: " + e.getMessage());
        }
    }

    @PutMapping("/parkingspots/{id}/status")
    public String updateParkingSpotStatus(@PathVariable int id, @RequestParam String status) {
        try {
            SpotDTO.Status newStatus = SpotDTO.Status.valueOf(status);
            parkingSpotRepository.updateParkingSpotStatus(id, newStatus);
            return "Parking spot status updated successfully.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @PutMapping("/parkingspots/{spotID}/pricing")
    public String updateSpotPricing(@PathVariable int spotID, @RequestParam double demandFactor) {
        try {
            // Call the service method to update pricing
            parkingSpotRepository.updateSpotPricing(spotID, demandFactor);
            return "Parking spot pricing updated successfully.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @DeleteMapping("/parkingspots/{id}")
    public String deleteParkingSpot(@PathVariable int id) {
        try {
            parkingSpotRepository.deleteParkingSpot(id);
            return "Parking spot deleted successfully.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
   
    // Report Endpoints
    @GetMapping("/reports/parkinglots")
    public String showParkingLotReport() {
        try {
            return reportService.showParkingLotReport();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/reports/parkingspots")
    public String showParkingSpotReport() {
        try {
            return reportService.showParkingSpotReport();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}