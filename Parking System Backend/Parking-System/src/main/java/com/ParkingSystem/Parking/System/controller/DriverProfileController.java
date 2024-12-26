package com.ParkingSystem.Parking.System.controller;

import com.ParkingSystem.Parking.System.dto.ReservationDTO;
import com.ParkingSystem.Parking.System.service.DriverProfileService;
import com.ParkingSystem.Parking.System.service.impl.DriverProfileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/driver/profile")
public class DriverProfileController {
    @Autowired
    private final DriverProfileService driverProfileService;

    public DriverProfileController(DriverProfileServiceImpl driverProfileServiceImpl) {
        this.driverProfileService = driverProfileServiceImpl;
    }

    @GetMapping("/reservations/{userId}")
    public ResponseEntity<?> getAllReservations(@PathVariable int userId) {
        List<ReservationDTO> reservationDTOList = driverProfileService.getAllReservation(userId);
        if (reservationDTOList != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(reservationDTOList);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }
}
