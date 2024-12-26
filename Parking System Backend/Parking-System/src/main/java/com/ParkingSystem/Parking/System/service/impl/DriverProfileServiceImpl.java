package com.ParkingSystem.Parking.System.service.impl;

import com.ParkingSystem.Parking.System.dto.ReservationDTO;
import com.ParkingSystem.Parking.System.repository.ReservationRepository;
import com.ParkingSystem.Parking.System.service.DriverProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

;

@Service
public class DriverProfileServiceImpl implements DriverProfileService {
    @Autowired
    private final ReservationRepository reservationRepository;

    public DriverProfileServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<ReservationDTO> getAllReservation(int userID)  {
        return reservationRepository.getAllReservations(userID);
    }

    @Override
    public List<ReservationDTO> getAllReservationsByLocation(int userId, String location) {
        return reservationRepository.getAllReservationsByLocation(userId, location);
    }
}
