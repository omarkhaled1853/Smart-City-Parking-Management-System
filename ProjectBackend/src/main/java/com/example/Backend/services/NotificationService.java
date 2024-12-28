package com.example.Backend.services;

import com.example.Backend.DTO.NotificationDTO;
import com.example.Backend.Repository.NotificationDao;

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
    public void sendNotificationToManager(int userId, String message){
        notificationDao.sendNotification(userId, message);
    }

//    public void addNotification(Notification notification){
//        notificationDao.addNotification(notification);
//    }
}