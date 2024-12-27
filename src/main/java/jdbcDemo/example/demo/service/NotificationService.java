package jdbcDemo.example.demo.service;

import jdbcDemo.example.demo.dao.NotificationDao;
import jdbcDemo.example.demo.entity.Notification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    private final NotificationDao notificationDao;
    public NotificationService(NotificationDao notificationDao){
        this.notificationDao = notificationDao;
    }

    public List<Notification> getAllNotifications(int userId){
        return notificationDao.getAllNotifications(userId);
    }
    public void sendNotificationToDriver(int userId, String message){
        notificationDao.sendNotificationToDriver(userId, message);
    }

//    public void addNotification(Notification notification){
//        notificationDao.addNotification(notification);
//    }
}
