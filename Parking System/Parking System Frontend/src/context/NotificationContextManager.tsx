import React, { createContext, useContext, useState, useEffect } from 'react';
import { Notification } from '../types/type';
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
  managerId: number;
  setUserId: (id: number) => void;
}

const NotificationContext = createContext<NotificationContextType | undefined>(undefined);
const userId = Number(Cookies.get('userId'));

export function NotificationProvider({ children }: { children: React.ReactNode }) {
  const [notifications, setNotifications] = useState<(Notification & { read: boolean })[]>([]);
  const [showNotifications, setShowNotifications] = useState(false);
  const [managerId, setUserId] = useState<number>(userId);  // You can set userId dynamically

  const unreadCount = notifications.filter(n => !n.read).length;

  useEffect(() => {
    // Fetch initial notifications
    const loadNotifications = async () => {
      try {
        console.log(userId);
        const data = await fetchNotifications(managerId);  // Fetch notifications for the correct user
        setNotifications(data.map((n: Notification) => ({ ...n, read: false })));
      } catch (error) {
        console.error('Failed to fetch notifications:', error);
      }
    };

    loadNotifications();

    // Connect to WebSocket using the dynamic userId
    const cleanup = connectWebSocket(managerId, (notification: Notification) => {
      addNotification(notification);
    });

    return cleanup;
  }, [managerId]);  // Reconnect WebSocket if userId changes

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
        unreadCount,
        setUserId,
        managerId,  // Provide userId to the context
      }}
    >
      {children}
    </NotificationContext.Provider>
  );
}

export function useNotifications() {
  const context = useContext(NotificationContext);
  if (context === undefined) {
    throw new Error('useNotifications must be used within a NotificationProvider');
  }
  return context;
}
