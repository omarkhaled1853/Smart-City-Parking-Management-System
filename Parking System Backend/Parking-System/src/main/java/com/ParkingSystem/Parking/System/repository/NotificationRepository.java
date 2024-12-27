package com.ParkingSystem.Parking.System.repository;


import com.ParkingSystem.Parking.System.dto.NotificationDTO;
import com.ParkingSystem.Parking.System.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NotificationRepository {
    @Autowired
    private final JdbcTemplate jdbcTemplate;
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationRepository(JdbcTemplate jdbcTemplate, SimpMessagingTemplate messagingTemplate){
        this.jdbcTemplate = jdbcTemplate;
        this.messagingTemplate = messagingTemplate;
    }

    public List<NotificationDTO> getAllNotifications(int userId){
        String query = "SELECT message, sentAt FROM notification WHERE userID = ?";

        return jdbcTemplate.query(query, Mapper::toNotificationDto, userId);
    }


    private void addNotification(int userId, String message) {
        String query = "INSERT INTO notification (userID, message) VALUES(?, ?)";
        jdbcTemplate.update(query, userId, message);
    }

    public void sendNotificationToDriver(int userId, String message){
        System.out.println(userId);
        System.out.println(message);

        addNotification(userId, message);

        String selectQuery = "SELECT message, sentAt FROM notification WHERE userID = ? ORDER BY id DESC LIMIT 1";
        NotificationDTO notificationDTO = jdbcTemplate.queryForObject(selectQuery, Mapper::toNotificationDto, userId);

        messagingTemplate.convertAndSend("/notification/subscribe/" + userId, notificationDTO);
    }
}
