package com.ParkingSystem.Parking.System.service;

import com.ParkingSystem.Parking.System.dto.ReservationDTO;

import java.util.List;

public interface DriverProfileService {
    List<ReservationDTO> getAllReservation(int userID);
}
