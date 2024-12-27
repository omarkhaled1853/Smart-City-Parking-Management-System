package com.ParkingSystem.Parking.System.dao;

import com.ParkingSystem.Parking.System.dto.HomeNotificationDTO;
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

    public List<HomeNotificationDTO> getAllNotifications(int userId){
        String query = "SELECT * FROM Notification WHERE UserID = ?";

        return jdbcTemplate.query(query, this::mapRowToNotification, userId);
    }
    public void sendNotificationToDriver(int userId, String message){
        String query = "INSERT INTO Notification (UserID, Message, SentAt) VALUES(?, ?, NOW())";
        jdbcTemplate.update(query, userId, message);

        String selectQuery = "SELECT * FROM Notification WHERE UserID = ? ORDER BY NotificationID DESC LIMIT 1";
        HomeNotificationDTO homeNotificationDTO = jdbcTemplate.queryForObject(selectQuery, this::mapRowToNotification, userId);

        messagingTemplate.convertAndSend("/notification/subscribe/" + userId, homeNotificationDTO);

    }
    private HomeNotificationDTO mapRowToNotification(ResultSet rs, int rowNum) throws SQLException{
        HomeNotificationDTO homeNotificationDTO = HomeNotificationDTO
                .builder()
                .NotificationID(rs.getInt("NotificationID"))
                .UserID(rs.getInt("UserID"))
                .Message(rs.getString("Message"))
                .SentAt(rs.getTimestamp("SentAt").toLocalDateTime())
                .build();

                return homeNotificationDTO;
    }

    public void addNotification(HomeNotificationDTO homeNotificationDTO){
        String query = "INSERT INTO Notification (UserID, Message, SentAt) VALUES (?, ?, ?)";

        jdbcTemplate.update(
                query,
                homeNotificationDTO.getUserID(),
                homeNotificationDTO.getMessage(),
                Timestamp.valueOf(homeNotificationDTO.getSentAt()));
    }
}
