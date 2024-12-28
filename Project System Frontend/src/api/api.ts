import { Notification, SpotUpdateRequest } from '../types/types';
import { ReservationDTO } from '../types/reservation';
import Cookies from 'js-cookie';

const API_BASE_URL = 'http://localhost:8080';
const WS_BASE_URL = 'ws://localhost:8080';

export const fetchGarages = async () => {
  const response = await fetch(`${API_BASE_URL}/garages`);
  if (!response.ok) throw new Error('Failed to fetch garages');
  return response.json();
};

export const fetchSpotsByLotId = async (lotId: number) => {
  const response = await fetch(`${API_BASE_URL}/garages/${lotId}`);
  if (!response.ok) throw new Error('Failed to fetch spots');
  return response.json();
};

export const updateSpotStatus = async (updateRequest: SpotUpdateRequest) => {
  const response = await fetch(`${API_BASE_URL}/garages/update`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(updateRequest),
  });
  if (!response.ok) throw new Error('Failed to update spot status');
  return response.json();
};

export const fetchNotifications = async (userId: number) => {
  console.log(userId)
  const response = await fetch(`${API_BASE_URL}/notification/${userId}`);
  if (!response.ok) throw new Error('Failed to fetch notifications');
  return response.json();
};

let websocket: WebSocket | null = null;

export const connectWebSocket = (userId: number, onMessage: (notification: Notification) => void) => {
  if (websocket?.readyState === WebSocket.OPEN) return;

  websocket = new WebSocket(`${WS_BASE_URL}/notification/subscribe/${userId}`);

  websocket.onopen = () => {
    console.log('WebSocket connected');
  };

  websocket.onmessage = (event) => {
    const notification = JSON.parse(event.data);
    onMessage(notification);
  };

  websocket.onerror = (error) => {
    console.error('WebSocket error:', error);
  };

  websocket.onclose = () => {
    console.log('WebSocket disconnected');
    // Attempt to reconnect after 5 seconds
    setTimeout(() => connectWebSocket(userId, onMessage), 5000);
  };

  return () => {
    if (websocket) {
      websocket.close();
      websocket = null;
    }
  };
};

export const getDriverReservations = async (userId: number): Promise<ReservationDTO[]> => {
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

export const searchReservations = async (userId: number, location: string): Promise<ReservationDTO[]> => {
  try {
    const response = await fetch(`${API_BASE_URL}/driver/profile/reservations/search/${userId}?location=${encodeURIComponent(location)}`, {
      method: 'GET',
      headers: {
        'Accept': 'application/json'
      }
    });

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
    console.error('Error searching reservations:', error);
    throw new Error('Failed to search reservations. Please try again later.');
  }
}