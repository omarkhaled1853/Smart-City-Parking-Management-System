package com.ParkingSystem.Parking.System.utils;

import com.ParkingSystem.Parking.System.dto.*;
import com.ParkingSystem.Parking.System.enums.ParkingLotPricingModel;
import com.ParkingSystem.Parking.System.enums.ParkingSpotStatus;
import com.ParkingSystem.Parking.System.enums.ParkingSpotType;
import com.ParkingSystem.Parking.System.enums.ReservationStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Mapper {
    public static ReservationDTO toReservationDto (ResultSet resultSet) {
        try {
            int reservationId = resultSet.getInt(1);
            Timestamp startTime = resultSet.getTimestamp(2);
            Timestamp endTime = resultSet.getTimestamp(3);
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

    public static NotificationDTO toNotificationDto (ResultSet resultSet, int rowNum) {
        try {
            return NotificationDTO
                    .builder()
//                    .userId(resultSet.getInt("userID"))
                    .message(resultSet.getString("message"))
                    .sentAt(resultSet.getTimestamp("sentAt").toLocalDateTime())
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static NearExpireReservationDTO toNearExpireReservationDto (ResultSet resultSet) {
        try {
            int userId = resultSet.getInt(1);
            int spotId = resultSet.getInt(2);
            ParkingSpotType spotType = ParkingSpotType.valueOf(resultSet.getString(3));
            ParkingSpotStatus parkingSpotStatus = ParkingSpotStatus.valueOf(resultSet.getString(4));
            String name = resultSet.getString(5);
            String location = resultSet.getString(6);
            ParkingLotPricingModel pricingModel = ParkingLotPricingModel.valueOf(resultSet.getString(7));

            return NearExpireReservationDTO.builder()
                    .userId(userId)
                    .parkingSpot(new ParkingSpotDTO(spotId, spotType, parkingSpotStatus))
                    .parkingLot(new ParkingLotDTO(name, location, pricingModel))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
