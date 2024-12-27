package jdbcDemo.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {
    private int NotificationID;
    private int UserID;
    private String Message;
    private LocalDateTime SentAt;
}
