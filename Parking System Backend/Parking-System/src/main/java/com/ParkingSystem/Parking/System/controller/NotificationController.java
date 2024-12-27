package com.ParkingSystem.Parking.System.controller;

import com.ParkingSystem.Parking.System.dto.NearExpireReservationDTO;
import com.ParkingSystem.Parking.System.dto.NotificationDTO;
import com.ParkingSystem.Parking.System.service.NotificationService;
import com.ParkingSystem.Parking.System.service.impl.DriverProfileServiceImpl;
import com.ParkingSystem.Parking.System.service.interfaces.DriverProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/driver/notification")
public class NotificationController {
    @Autowired
    private final DriverProfileService driverProfileService;
    private final NotificationService notificationService;

    public NotificationController(DriverProfileServiceImpl driverProfileService,
                                  NotificationService notificationService) {
        this.driverProfileService = driverProfileService;
        this.notificationService = notificationService;
    }

    @GetMapping("/{userId}")
    public List<NotificationDTO> getAllNotification(@PathVariable int userId){
        return notificationService.getAllNotifications(userId);
    }

    @Scheduled(fixedRate = 5000) // Run every 60 seconds
    public void notifyAllDrivers() {
//        get all near expire reservations
        List<NearExpireReservationDTO> nearExpiryReservations = driverProfileService.getNearExpireReservations();

//        notify all users
        nearExpiryReservations.forEach(reservation ->
                notificationService.sendNotificationToDriver(
                        reservation.getUserId(),
                        "Your reservation for\n" +
                                reservation.getParkingSpot().getSpotId() + "\n" +
                                reservation.getParkingSpot().getSpotType() + "\n" +
                                reservation.getParkingSpot().getStatus() + "\n" +
                                reservation.getParkingLot().getName() + "\n" +
                                reservation.getParkingLot().getLocation() + "\n" +
                                reservation.getParkingLot().getPricingModel() + "\n" +
                                " is nearing expiry. Please take action."
                )
        );
    }
}
