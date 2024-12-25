package com.ParkingSystem.Parking.System.dto;

import com.ParkingSystem.Parking.System.enums.ParkingLotPricingModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingLotDTO {
    private String name;
    private String location;
    private ParkingLotPricingModel pricingModel;
}
