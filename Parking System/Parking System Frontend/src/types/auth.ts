export interface User {
  id: string;
  email: string;
  name: string;
  role: 'Driver' | 'ParkingLotManager' | 'Admin';
  licensePlate?: string;
}

export interface LoginFormData {
  email: string;
  password: string;
}

export interface SignupFormData extends LoginFormData {
  name: string;
  role: 'Driver' | 'ParkingLotManager' | 'Admin';
  licensePlate?: string;
  phone: string;
}