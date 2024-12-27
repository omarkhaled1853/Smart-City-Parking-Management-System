export interface Lot {
  id: number;
  name: string;
  location: string;
  capacity: number;
}

export interface Spot {
  id: number;
  lotId: number;
  type: 'REGULAR' | 'EVCHARGING' | 'DISABLED';
  status: 'AVAILABLE' | 'OCCUPIED' | 'RESERVED';
  pricePerHour: number;
}

export interface Notification {
  id: number;
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