package com.example.Backend.controllers;

import com.example.Backend.Repository.UserRepository;
import com.example.Backend.services.NotificationService;
import com.example.Backend.services.ReportService;
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
    public void updateMessageToManager(@RequestBody String message, @RequestBody int userId){
        notificationService.sendNotificationToManager(userId, message);
    }
}
