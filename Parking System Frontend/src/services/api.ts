import { ReservationDTO } from '../types/reservation';
import { mockReservations } from './mockData';

const API_BASE_URL = 'http://localhost:8080';
const USE_MOCK_DATA = false; // Toggle this for development

export const getDriverReservations = async (userId: number): Promise<ReservationDTO[]> => {
  if (USE_MOCK_DATA) {
    // Simulate API delay
    await new Promise(resolve => setTimeout(resolve, 500));
    return mockReservations;
  }

  try {
    const response = await fetch(`${API_BASE_URL}/driver/profile/reservations/${userId}`);
    
    if (response.status === 204) {
      return [];
    }

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    
    const data = await response.json();
    return data.map((reservation: any) => ({
      ...reservation,
      startTime: new Date(reservation.startTime),
      endTime: new Date(reservation.endTime)
    }));
  } catch (error) {
    console.error('Error fetching reservations:', error);
    throw new Error('Failed to fetch reservations. Please try again later.');
  }
};