package com.ParkingSystem.Parking.System.controller;

import com.ParkingSystem.Parking.System.dto.NotificationDTO;
import com.ParkingSystem.Parking.System.dto.ReservationDTO;
import com.ParkingSystem.Parking.System.service.impl.DriverProfileServiceImpl;
import com.ParkingSystem.Parking.System.service.interfaces.DriverProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/driver")
public class DriverProfileController {
    @Autowired
    private final DriverProfileService driverProfileService;

    public DriverProfileController(DriverProfileServiceImpl driverProfileService) {
        this.driverProfileService = driverProfileService;
    }

    @GetMapping("/profile/reservations/{userId}")
    public ResponseEntity<?> getAllReservations(@PathVariable int userId) {
        List<ReservationDTO> reservationDTOList = driverProfileService.getAllReservation(userId);
        if (reservationDTOList != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(reservationDTOList);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping("/profile/reservations/search/{userId}")
    public ResponseEntity<?> getAllReservations(@PathVariable int userId, @RequestParam String location) {
        List<ReservationDTO> reservationDTOList =
                driverProfileService.getAllReservationsByLocation(userId, location);
        if (reservationDTOList != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(reservationDTOList);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping("/notification/{userId}")
    public List<NotificationDTO> getAllNotification(@PathVariable int userId){
        return driverProfileService.getAllNotifications(userId);
    }
}
