package com.ParkingSystem.Parking.System.dao;

import com.ParkingSystem.Parking.System.dto.NotificationDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class NotificationDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationDao(JdbcTemplate jdbcTemplate, SimpMessagingTemplate messagingTemplate){
        this.jdbcTemplate = jdbcTemplate;
        this.messagingTemplate = messagingTemplate;
    }

    public List<NotificationDTO> getAllNotifications(int userId){
        String query = "SELECT * FROM Notification WHERE UserID = ?";

        return jdbcTemplate.query(query, this::mapRowToNotification, userId);
    }
    public void sendNotification(int userId, String message){
        String query = "INSERT INTO Notification (UserID, Message, SentAt) VALUES(?, ?, NOW())";
        jdbcTemplate.update(query, userId, message);

        String selectQuery = "SELECT * FROM Notification WHERE UserID = ? ORDER BY NotificationID DESC LIMIT 1";
        NotificationDTO notificationDTO = jdbcTemplate.queryForObject(selectQuery, this::mapRowToNotification, userId);

        messagingTemplate.convertAndSend("/notification/subscribe/" + userId, notificationDTO);

    }
    private NotificationDTO mapRowToNotification(ResultSet rs, int rowNum) throws SQLException{
        NotificationDTO notificationDTO = NotificationDTO
                .builder()
                .NotificationID(rs.getInt("NotificationID"))
                .userId(rs.getInt("UserID"))
                .message(rs.getString("Message"))
                .sentAt(rs.getTimestamp("SentAt").toLocalDateTime())
                .build();

                return notificationDTO;
    }

    public void addNotification(NotificationDTO notificationDTO){
        String query = "INSERT INTO Notification (UserID, Message, SentAt) VALUES (?, ?, ?)";

        jdbcTemplate.update(
                query,
                notificationDTO.getUserId(),
                notificationDTO.getMessage(),
                Timestamp.valueOf(notificationDTO.getSentAt()));
    }
}
