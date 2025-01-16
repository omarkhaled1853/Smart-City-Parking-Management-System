package com.ParkingSystem.Parking.System.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NearExpireReservationDTO {
    private int reservationId;
    private int userId;
    private ParkingSpotDTO parkingSpot;
    private ParkingLotDTO parkingLot;
}
