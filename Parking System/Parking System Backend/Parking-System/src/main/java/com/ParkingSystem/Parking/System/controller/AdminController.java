package com.ParkingSystem.Parking.System.controller;


import com.ParkingSystem.Parking.System.repository.UserRepository;
import com.ParkingSystem.Parking.System.service.NotificationService;
import com.ParkingSystem.Parking.System.service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Admin")
public class AdminController {
    private final ReportService reportService;
    private final NotificationService notificationService;
    private final UserRepository userRepository;

    public AdminController(ReportService reportService, NotificationService notificationService, UserRepository userRepository) {
        this.reportService = reportService;
        this.notificationService = notificationService;
        this.userRepository = userRepository;
    }


    @GetMapping("/reports/analysis")
    public String TopUsersAndLotReport() {
        try {
            return reportService.TopUsersAndLotsReport();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // Report Endpoints
    @GetMapping("/reports/parkinglots")
    public String generateParkingLotReport() {
        try {
            return reportService.generateParkingLotReport();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/reports/parkingspots")
    public String generateParkingSpotReport() {
        try {
            return reportService.generateParkingSpotReport();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/users")

    public List<Map<String, Object>> getAllManagers(){
        System.out.println("here");
        try {
            return userRepository.getAllManagers();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching managers: " + e.getMessage());
        }
    }



    @PostMapping("/Alerts")
    public void updateMessageToManager(@RequestParam String message, @RequestParam int userId){
        notificationService.sendNotification(userId, message);
    }
}
