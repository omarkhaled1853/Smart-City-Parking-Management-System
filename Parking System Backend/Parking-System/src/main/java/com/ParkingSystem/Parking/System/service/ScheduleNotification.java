package com.ParkingSystem.Parking.System.service;

import com.ParkingSystem.Parking.System.dto.NearExpireReservationDTO;
import com.ParkingSystem.Parking.System.repository.NotificationRepository;
import com.ParkingSystem.Parking.System.service.impl.DriverProfileServiceImpl;
import com.ParkingSystem.Parking.System.service.interfaces.DriverProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class ScheduleNotification {
    @Autowired
    private final NotificationRepository notificationRepository;
    private final DriverProfileService driverProfileService;
    private final HashSet<Integer> notifiedReservations;

    public ScheduleNotification(NotificationRepository notificationRepository,
                               DriverProfileServiceImpl driverProfileService) {
        this.notificationRepository = notificationRepository;
        this.driverProfileService = driverProfileService;
        this.notifiedReservations = new HashSet<>();
    }

    @Scheduled(fixedRate = 60000)
    public void notifyAllDrivers() {
        // Get all near expiry reservations
        List<NearExpireReservationDTO> nearExpiryReservations = driverProfileService.getNearExpireReservations();

        // Print for debugging
        System.out.println(nearExpiryReservations);

        // Notify all users about near expiry reservations
        nearExpiryReservations.forEach(reservation -> {
            // Check if the reservation has already been notified
            if (!notifiedReservations.contains(reservation.getReservationId())) {
                // Send the notification to the driver
                notificationRepository.sendNotificationToDriver(
                        reservation.getUserId(),
                        "Your reservation for\n" +
                                "Spot#: " + reservation.getParkingSpot().getSpotId() + "\n" +
                                "Spot Type: " + reservation.getParkingSpot().getSpotType() + "\n" +
                                "Spot Status: " + reservation.getParkingSpot().getStatus() + "\n" +
                                "Lot Name: " + reservation.getParkingLot().getName() + "\n" +
                                "Location: " + reservation.getParkingLot().getLocation() + "\n" +
                                "Pricing Model: " + reservation.getParkingLot().getPricingModel() + "\n" +
                                "is nearing expiry. Please take action."
                );

                // Add the reservation to notifiedReservations to avoid sending again
                notifiedReservations.add(reservation.getReservationId());
            }
        });
    }
}
