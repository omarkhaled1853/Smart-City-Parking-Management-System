import React from 'react';
import { Bell, User } from 'lucide-react';
import { useNavigate } from 'react-router-dom';
import { useNotifications } from '../context/NotificationContext';

export default function AppBar() {
  const navigate = useNavigate();
  const { 
    notifications, 
    showNotifications, 
    setShowNotifications, 
    unreadCount,
    markNotificationsAsRead 
  } = useNotifications();

  const handleNotificationClick = () => {
    if (!showNotifications) {
      markNotificationsAsRead();
    }
    setShowNotifications(!showNotifications);
  };

  return (
    <div className="bg-white shadow-md px-4 py-2 fixed top-0 left-0 right-0 z-50">
      <div className="max-w-7xl mx-auto flex justify-between items-center">
        <h1 
          onClick={() => navigate('/')} 
          className="text-xl font-semibold cursor-pointer"
        >
          Parking Garage
        </h1>
        <div className="flex items-center gap-4">
          <div className="relative">
            <button
              onClick={handleNotificationClick}
              className="p-2 hover:bg-gray-100 rounded-full relative"
            >
              <Bell className="w-6 h-6" />
              {unreadCount > 0 && (
                <span className="absolute top-0 right-0 bg-red-500 text-white rounded-full w-5 h-5 text-xs flex items-center justify-center">
                  {unreadCount}
                </span>
              )}
            </button>
            {showNotifications && (
              <div className="absolute right-0 mt-2 w-80 bg-white rounded-lg shadow-lg overflow-hidden max-h-[400px] overflow-y-auto">
                {notifications.length > 0 ? (
                  notifications.map((notification) => (
                    <div
                      key={notification.notificationID}
                      className={`p-4 border-b hover:bg-gray-50 ${
                        !notification.read ? 'bg-blue-50' : ''
                      }`}
                    >
                      <p className={`text-sm ${notification.type === 'error' ? 'text-red-600' : 'text-green-600'}`}>
                        {notification.message}
                      </p>
                      <p className="text-xs text-gray-500 mt-1">
                        {new Date(notification.sentAt).toLocaleString()}
                      </p>
                    </div>
                  ))
                ) : (
                  <div className="p-4 text-center text-gray-500">
                    No notifications
                  </div>
                )}
              </div>
            )}
          </div>
          <button
            onClick={() => navigate('/profile')}
            className="p-2 hover:bg-gray-100 rounded-full flex items-center gap-2"
          >
            <User className="w-6 h-6" />
          </button>
        </div>
      </div>
    </div>
  );
}