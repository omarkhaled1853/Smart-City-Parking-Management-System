package com.ParkingSystem.Parking.System.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomeNotificationDTO {
    private int NotificationID;
    private int UserID;
    private String Message;
    private LocalDateTime SentAt;
}
