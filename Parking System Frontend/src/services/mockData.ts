import { ParkingLotPricingModel, ParkingSpotStatus, ParkingSpotType, ReservationDTO, ReservationStatus } from '../types/reservation';

export const mockReservations: ReservationDTO[] = [
  {
    reservationId: 1,
    startTime: new Date('2024-03-10T10:00:00'),
    endTime: new Date('2024-03-10T18:00:00'),
    reservationStatus: ReservationStatus.ACTIVE,
    parkingSpot: {
      spotId: 101,
      spotType: ParkingSpotType.DISABLED,
      status: ParkingSpotStatus.OCCUPIED
    },
    parkingLot: {
      name: "Downtown Parking Complex",
      location: "سيدي بشر, شارع ملك حفنى قبلى, مدينة الضباط بسيدي بشر, ميامي, المندرة, الإسكندرية, 21634, مصر",
      pricingModel: ParkingLotPricingModel.DYNAMIC
    }
  },
  {
    reservationId: 2,
    startTime: new Date('2024-03-09T09:00:00'),
    endTime: new Date('2024-03-09T17:00:00'),
    reservationStatus: ReservationStatus.COMPLETED,
    parkingSpot: {
      spotId: 102,
      spotType: ParkingSpotType.EVCHARGING,
      status: ParkingSpotStatus.AVAILABLE
    },
    parkingLot: {
      name: "City Center Garage",
      location: "456 Park Avenue",
      pricingModel: ParkingLotPricingModel.STATIC
    }
  }
];