export enum ReservationStatus {
  ACTIVE = 'ACTIVE',
  COMPLETED = 'COMPLETED',
  CANCELLED = 'CANCELLED'
}

export enum ParkingSpotType {
  REGULAR = 'REGULAR',
  DISABLED = 'DISABLED',
  EVCHARGING = 'EVCHARGING'
}

export enum ParkingSpotStatus {
  AVAILABLE = 'AVAILABLE',
  OCCUPIED = 'OCCUPIED',
  RESERVED = 'RESERVED'
}

export enum ParkingLotPricingModel {
  STATIC = 'STATIC',
  DYNAMIC = 'DYNAMIC',
}

export interface ParkingSpotDTO {
  spotId: number;
  spotType: ParkingSpotType;
  status: ParkingSpotStatus;
}

export interface ParkingLotDTO {
  name: string;
  location: string;
  pricingModel: ParkingLotPricingModel;
}

export interface ReservationDTO {
  reservationId: number;
  startTime: Date;
  endTime: Date;
  reservationStatus: ReservationStatus;
  parkingSpot: ParkingSpotDTO;
  parkingLot: ParkingLotDTO;
}