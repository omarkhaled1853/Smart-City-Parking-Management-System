package com.ParkingSystem.Parking.System.controller;

import com.ParkingSystem.Parking.System.dto.Notification;
import com.ParkingSystem.Parking.System.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService){
        this.notificationService = notificationService;
    }

    @GetMapping("/{userId}")
    public List<Notification> getAllNotification(@PathVariable int userId){
        return notificationService.getAllNotifications(userId);
    }

//    @PostMapping("/add")
//    public void addNotification(@RequestBody Notification notification){
//        notificationService.addNotification(notification);
//    }
}
