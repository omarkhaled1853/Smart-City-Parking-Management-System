package com.ParkingSystem.Parking.System.service;

import com.ParkingSystem.Parking.System.dao.NotificationDao;
import com.ParkingSystem.Parking.System.dto.NotificationDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    private final NotificationDao notificationDao;
    public NotificationService(NotificationDao notificationDao){
        this.notificationDao = notificationDao;
    }

    public List<NotificationDTO> getAllNotifications(int userId){
        return notificationDao.getAllNotifications(userId);
    }
    public void sendNotification(int userId, String message){
        notificationDao.sendNotification(userId, message);
    }
}
