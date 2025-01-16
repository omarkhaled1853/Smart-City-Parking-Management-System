package com.ParkingSystem.Parking.System.dto;

import com.ParkingSystem.Parking.System.enums.PricingModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomeParkingLotDTO {
    private int ParkingLotID;
    private String Name;
    private String Location;
    private int Capacity;
    private PricingModel pricingModel;
    private LocalDateTime CreatedAt;
}
