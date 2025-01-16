import React, { createContext, useContext, useState, useEffect } from 'react';
import { Notification } from '../types/types';
import { fetchNotifications, connectWebSocket } from '../api/api';
import Cookies from 'js-cookie';

interface NotificationContextType {
  notifications: (Notification & { read: boolean })[];
  showNotifications: boolean;
  setShowNotifications: (show: boolean) => void;
  addNotification: (notification: Notification) => void;
  clearNotifications: () => void;
  markNotificationsAsRead: () => void;
  unreadCount: number;
}

const NotificationContext = createContext<NotificationContextType | undefined>(undefined);
const userId = Number(Cookies.get('userId'));

export function NotificationProvider({ children }: { children: React.ReactNode }) {
  const [notifications, setNotifications] = useState<(Notification & { read: boolean })[]>([]);
  const [showNotifications, setShowNotifications] = useState(false);

  const unreadCount = notifications.filter(n => !n.read).length;

  useEffect(() => {
    // Fetch initial notifications
    // const userId = Number(Cookies.get('userId'));
    console.log(userId)
    const loadNotifications = async () => {
      try {
        const data = await fetchNotifications(userId);
        setNotifications(data.map((n: Notification) => ({ ...n, read: false })));
      } catch (error) {
        console.error('Failed to fetch notifications:', error);
      }
    };

    loadNotifications();
    // Connect to WebSocket
    const cleanup = connectWebSocket(userId, (notification: Notification) => {
      addNotification(notification);
    });

    return cleanup;
  }, []);

  const addNotification = (notification: Notification) => {
    setNotifications(prev => [{
      ...notification,
      read: false
    }, ...prev]);
  };

  const clearNotifications = () => {
    setNotifications([]);
  };

  const markNotificationsAsRead = () => {
    setNotifications(prev => prev.map(n => ({ ...n, read: true })));
  };

  return (
    <NotificationContext.Provider
      value={{
        notifications,
        showNotifications,
        setShowNotifications,
        addNotification,
        clearNotifications,
        markNotificationsAsRead,
        unreadCount
      }}
    >
      {children}
    </NotificationContext.Provider>
  );
}

export function useNotifications() {
  console.log(userId)
  const context = useContext(NotificationContext);
  if (context === undefined) {
    throw new Error('useNotifications must be used within a NotificationProvider');
  }
  return context;
}