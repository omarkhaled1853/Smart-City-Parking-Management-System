package com.ParkingSystem.Parking.System.utils;

import com.ParkingSystem.Parking.System.dto.ParkingLotDTO;
import com.ParkingSystem.Parking.System.dto.ParkingSpotDTO;
import com.ParkingSystem.Parking.System.dto.ReservationDTO;
import com.ParkingSystem.Parking.System.enums.ParkingLotPricingModel;
import com.ParkingSystem.Parking.System.enums.ParkingSpotStatus;
import com.ParkingSystem.Parking.System.enums.ParkingSpotType;
import com.ParkingSystem.Parking.System.enums.ReservationStatus;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class mapper {
    public static ReservationDTO toReservationDto (ResultSet resultSet) {
        try {
            int reservationId = resultSet.getInt(1);
            Date startTime = resultSet.getDate(2);
            Date endTime = resultSet.getDate(3);
            ReservationStatus reservationStatus = ReservationStatus.valueOf(resultSet.getString(4));
            int spotId = resultSet.getInt(5);
            ParkingSpotType spotType = ParkingSpotType.valueOf(resultSet.getString(6));
            ParkingSpotStatus parkingSpotStatus = ParkingSpotStatus.valueOf(resultSet.getString(7));
            String name = resultSet.getString(8);
            String location = resultSet.getString(9);
            ParkingLotPricingModel pricingModel = ParkingLotPricingModel.valueOf(resultSet.getString(10));

            return ReservationDTO.builder()
                    .reservationId(reservationId)
                    .startTime(startTime)
                    .endTime(endTime)
                    .reservationStatus(reservationStatus)
                    .parkingSpot(new ParkingSpotDTO(spotId, spotType, parkingSpotStatus))
                    .parkingLot(new ParkingLotDTO(name, location, pricingModel))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
