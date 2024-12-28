export interface Notification {
    notificationID: number;
    userId: number;
    message: string;
    sentAt: string;
    type?: 'success' | 'error';
    read?: boolean;
  }