import { Notification } from '../types/type';

const API_BASE_URL = 'http://localhost:8080';
const WS_BASE_URL = 'ws://localhost:8080';


export const fetchNotifications = async (userId: number = 5) => {
  const response = await fetch(`${API_BASE_URL}/notification/${userId}`);
  if (!response.ok) throw new Error('Failed to fetch notifications');
  return response.json();
};

let websocket: WebSocket | null = null;

export const connectWebSocket = (userId: number = 5, onMessage: (notification: Notification) => void) => {
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
    userId=5;
    setTimeout(() => connectWebSocket(userId, onMessage), 5000);
  };

  return () => {
    if (websocket) {
      websocket.close();
      websocket = null;
    }
  };
};