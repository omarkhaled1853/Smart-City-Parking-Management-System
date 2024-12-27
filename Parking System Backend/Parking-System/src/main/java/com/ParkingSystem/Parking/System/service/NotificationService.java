package com.ParkingSystem.Parking.System.service;

import com.ParkingSystem.Parking.System.dao.NotificationDao;
import com.ParkingSystem.Parking.System.dto.HomeNotificationDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    private final NotificationDao notificationDao;
    public NotificationService(NotificationDao notificationDao){
        this.notificationDao = notificationDao;
    }

    public List<HomeNotificationDTO> getAllNotifications(int userId){
        return notificationDao.getAllNotifications(userId);
    }
    public void sendNotificationToDriver(int userId, String message){
        notificationDao.sendNotificationToDriver(userId, message);
    }
}
