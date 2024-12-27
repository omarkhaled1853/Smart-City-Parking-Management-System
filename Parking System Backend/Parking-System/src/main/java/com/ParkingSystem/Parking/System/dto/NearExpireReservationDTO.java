package com.ParkingSystem.Parking.System.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NearExpireReservationDTO {
    private int userId;
    private ParkingSpotDTO parkingSpot;
    private ParkingLotDTO parkingLot;
}
