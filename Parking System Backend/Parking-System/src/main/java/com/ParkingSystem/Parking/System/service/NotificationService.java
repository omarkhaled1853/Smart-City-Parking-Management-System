package com.ParkingSystem.Parking.System.service;


import com.ParkingSystem.Parking.System.dto.NotificationDTO;
import com.ParkingSystem.Parking.System.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository){
        this.notificationRepository = notificationRepository;
    }

    public List<NotificationDTO> getAllNotifications(int userId){
        return notificationRepository.getAllNotifications(userId);
    }
    public void sendNotificationToDriver(int userId, String message){
        notificationRepository.sendNotificationToDriver(userId, message);
    }
}
