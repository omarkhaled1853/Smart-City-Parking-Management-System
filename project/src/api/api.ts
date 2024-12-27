import { Notification } from '../types/type';

const API_BASE_URL = 'http://localhost:8080';
const WS_BASE_URL = 'ws://localhost:8080';


export const fetchNotifications = async (userId: number = 5) => {
  const response = await fetch(`${API_BASE_URL}/notification/${userId}`);
  if (!response.ok) throw new Error('Failed to fetch notifications');
  return response.json();
};

let websocket: WebSocket | null = null;
let currentUserId: number | null = null;

export const connectWebSocket = (userId: number = 5, onMessage: (notification: Notification) => void) => {
  // If the userId hasn't changed and the WebSocket is open, do nothing
  if (currentUserId === userId && websocket?.readyState === WebSocket.OPEN) return;

  // Close the previous WebSocket if it exists
  if (websocket) {
    websocket.close();
    websocket = null;
  }

  currentUserId = userId; // Update the current userId
  websocket = new WebSocket(`${WS_BASE_URL}/notification/subscribe/${userId}`);

  websocket.onopen = () => {
    console.log('WebSocket connected for user:', userId);
  };

  websocket.onmessage = (event) => {
    const notification = JSON.parse(event.data);
    onMessage(notification);
  };

  websocket.onerror = (error) => {
    console.error('WebSocket error:', error);
  };

  websocket.onclose = () => {
    console.log('WebSocket disconnected for user:', userId);
    // Attempt to reconnect after a delay
    setTimeout(() => connectWebSocket(userId, onMessage), 5000);
  };

  return () => {
    if (websocket) {
      websocket.close();
      websocket = null;
    }
    currentUserId = null; // Reset current userId on cleanup
  };
};