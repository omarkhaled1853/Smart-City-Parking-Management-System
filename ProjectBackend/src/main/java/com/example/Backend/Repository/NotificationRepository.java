package com.example.Backend.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Repository;

import com.example.Backend.DTO.NotificationDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class NotificationRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationRepository(JdbcTemplate jdbcTemplate, SimpMessagingTemplate messagingTemplate){
        this.jdbcTemplate = jdbcTemplate;
        this.messagingTemplate = messagingTemplate;
    }

    public List<NotificationDTO> getAllNotifications(int userId){
        String query = "SELECT * FROM notification WHERE userId = ?";

        return jdbcTemplate.query(query, this::mapRowToNotification, userId);
    }
    public void sendNotificationToManager(int userId, String message){
        String query = "INSERT INTO notification (UserID, Message, sentAt) VALUES(?, ?, NOW())";
        jdbcTemplate.update(query, userId, message);

        String selectQuery = "SELECT * FROM notification WHERE UserID = ? ORDER BY NotificationID DESC LIMIT 1";
        NotificationDTO notification = jdbcTemplate.queryForObject(selectQuery, this::mapRowToNotification, userId);

        messagingTemplate.convertAndSend("/notification/subscribe/" + userId, notification);

    }
    private NotificationDTO mapRowToNotification(ResultSet rs, int rowNum) throws SQLException{
        NotificationDTO notification = NotificationDTO
                .builder()
                .NotificationID(rs.getInt("NotificationID"))
                .userId(rs.getInt("userId"))
                .message(rs.getString("message"))
                .sentAt(rs.getTimestamp("sentAt").toLocalDateTime())
                .build();

                return notification;
    }

    public void addNotification(NotificationDTO notification){
        String query = "INSERT INTO notification (userId, message, sentAt) VALUES (?, ?, ?)";

        jdbcTemplate.update(
                query,
                notification.getUserId(),
                notification.getMessage(),
                Timestamp.valueOf(notification.getSentAt()));
    }
}