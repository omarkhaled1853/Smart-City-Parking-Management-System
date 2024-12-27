export interface Notification {
  NotificationID: number;
    userId: number;
    message: string;
    sentAt: string;
    type?: 'success' | 'error';
    read?: boolean;
  }