package com.example.Backend.controllers;

import com.example.Backend.services.NotificationService;
import com.example.Backend.services.ReportService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/Admin")
public class AdminController {
    private final ReportService reportService;
    private final NotificationService notificationService;

    public AdminController(ReportService reportService,NotificationService notificationService) {
        this.reportService = reportService;
        this.notificationService = notificationService;
    }


    @GetMapping("/reports/analysis")
    public String generateParkingSpotReport() {
        try {
            return reportService.generateParkingSpotReport();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    @PostMapping("/Alerts")
    public void updateMessageToManager(@RequestBody String message, @RequestBody int userId){
        notificationService.sendNotificationToManager(userId, message);
    }
}
