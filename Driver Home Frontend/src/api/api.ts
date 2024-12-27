import { Notification, SpotUpdateRequest } from '../types/types';

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

export const fetchNotifications = async (userId: number = 1) => {
  const response = await fetch(`${API_BASE_URL}/notification/${userId}`);
  if (!response.ok) throw new Error('Failed to fetch notifications');
  return response.json();
};

let websocket: WebSocket | null = null;

export const connectWebSocket = (userId: number = 1, onMessage: (notification: Notification) => void) => {
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