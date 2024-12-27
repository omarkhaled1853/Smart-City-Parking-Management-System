export interface Lot {
  parkingLotID: number;
  name: string;
  location: string;
  capacity: number;
  pricingModel: 'STATIC'|'DYNAMIC';
  createdAt:string;
}

export interface Spot {
  spotID: number;
  parkingLotID: number;
  spotType: 'REGULAR' | 'EVCHARGING' | 'DISABLED';
  status: 'AVAILABLE' | 'OCCUPIED' | 'RESERVED';
  pricePerHour: number;
}

export interface Notification {
  notificationID: number;
  userId: number;
  message: string;
  sentAt: string;
  type?: 'success' | 'error';
  read?: boolean;
}

export interface ReservationDetails {
  spotId: number;
  duration: number;
  paymentMethod: 'cash' | 'card';
  totalPrice: number;
  startDate: string;
  endDate: string;
  paymentStatus: 'PENDING' | 'COMPLETED';
}

export interface SpotUpdateRequest {
  id: number;
  status: string;
  userId: number;
  startDate: string;
  endDate: string;
  totalPrice: number;
  paymentStatus: 'PENDING' | 'COMPLETED';
}