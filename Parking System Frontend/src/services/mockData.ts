import { ParkingLotPricingModel, ParkingSpotStatus, ParkingSpotType, ReservationDTO, ReservationStatus } from '../types/reservation';

export const mockReservations: ReservationDTO[] = [
  {
    reservationId: 1,
    startTime: new Date('2024-03-10T10:00:00'),
    endTime: new Date('2024-03-10T18:00:00'),
    reservationStatus: ReservationStatus.ACTIVE,
    parkingSpot: {
      spotId: 101,
      spotType: ParkingSpotType.STANDARD,
      status: ParkingSpotStatus.OCCUPIED
    },
    parkingLot: {
      name: "Downtown Parking Complex",
      location: "123 Main Street",
      pricingModel: ParkingLotPricingModel.HOURLY
    }
  },
  {
    reservationId: 2,
    startTime: new Date('2024-03-09T09:00:00'),
    endTime: new Date('2024-03-09T17:00:00'),
    reservationStatus: ReservationStatus.COMPLETED,
    parkingSpot: {
      spotId: 102,
      spotType: ParkingSpotType.ELECTRIC,
      status: ParkingSpotStatus.AVAILABLE
    },
    parkingLot: {
      name: "City Center Garage",
      location: "456 Park Avenue",
      pricingModel: ParkingLotPricingModel.DAILY
    }
  }
];