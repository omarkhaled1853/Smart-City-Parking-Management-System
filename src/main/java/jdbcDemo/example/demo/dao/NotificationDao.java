package jdbcDemo.example.demo.dao;

import jdbcDemo.example.demo.entity.Notification;
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

    public List<Notification> getAllNotifications(int userId){
        String query = "SELECT * FROM Notification WHERE UserID = ?";

        return jdbcTemplate.query(query, this::mapRowToNotification, userId);
    }
    public void sendNotificationToDriver(int userId, String message){
        String query = "INSERT INTO Notification (UserID, Message, SentAt) VALUES(?, ?, NOW())";
        jdbcTemplate.update(query, userId, message);

        String selectQuery = "SELECT * FROM Notification WHERE UserID = ? ORDER BY NotificationID DESC LIMIT 1";
        Notification notification = jdbcTemplate.queryForObject(selectQuery, this::mapRowToNotification, userId);

        messagingTemplate.convertAndSend("/notification/subscribe/" + userId, notification);

    }
    private Notification mapRowToNotification(ResultSet rs, int rowNum) throws SQLException{
        Notification notification = Notification
                .builder()
                .id(rs.getInt("NotificationID"))
                .userId(rs.getInt("UserID"))
                .message(rs.getString("Message"))
                .sentAt(rs.getTimestamp("SentAt").toLocalDateTime())
                .build();

                return notification;
    }

    public void addNotification(Notification notification){
        String query = "INSERT INTO Notification (UserID, Message, SentAt) VALUES (?, ?, ?)";

        jdbcTemplate.update(
                query,
                notification.getUserId(),
                notification.getMessage(),
                Timestamp.valueOf(notification.getSentAt()));
    }
}
