package com.example.Backend.services;

import com.example.Backend.DTO.NotificationDTO;
import com.example.Backend.Repository.NotificationRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationDao;
    public NotificationService(NotificationRepository notificationDao){
        this.notificationDao = notificationDao;
    }

    public List<NotificationDTO> getAllNotifications(int userId){
        return notificationDao.getAllNotifications(userId);
    }
    public void sendNotificationToManager(int userId, String message){
        notificationDao.sendNotificationToManager(userId, message);
    }

//    public void addNotification(Notification notification){
//        notificationDao.addNotification(notification);
//    }
}