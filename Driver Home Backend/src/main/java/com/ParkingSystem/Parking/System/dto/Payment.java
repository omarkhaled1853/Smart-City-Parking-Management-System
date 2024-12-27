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
public class Payment {
    private int PaymentID;
    private int ReservationID;
    private float Amount;
    private LocalDateTime PaymentTime;
    private com.ParkingSystem.Parking.System.enums.PaymentStatus PaymentStatus;
}
