import React from 'react';
import { Calendar, Clock, CreditCard } from 'lucide-react';

interface Reservation {
  id: number;
  spotId: number;
  startDate: string;
  endDate: string;
  totalPrice: number;
  paymentStatus: 'PENDING' | 'COMPLETED';
}

// Mock data - replace with actual API call in production
const mockReservations: Reservation[] = [
  {
    id: 1,
    spotId: 101,
    startDate: '2024-03-20T10:00:00',
    endDate: '2024-03-21T10:00:00',
    totalPrice: 50.00,
    paymentStatus: 'COMPLETED'
  },
  {
    id: 2,
    spotId: 102,
    startDate: '2024-03-22T14:00:00',
    endDate: '2024-03-23T14:00:00',
    totalPrice: 45.00,
    paymentStatus: 'PENDING'
  }
];

interface ReservationListProps {
  userId: number;
}

export default function ReservationList({ userId }: ReservationListProps) {
  return (
    <div className="min-h-screen bg-gray-100 py-12 px-4 sm:px-6 lg:px-8">
      <div className="max-w-3xl mx-auto">
        <h1 className="text-3xl font-bold text-gray-900 mb-8">My Reservations</h1>
        <div className="space-y-4">
          {mockReservations.map((reservation) => (
            <div 
              key={reservation.id}
              className="bg-white rounded-lg shadow-md p-6"
            >
              <div className="flex justify-between items-start mb-4">
                <h3 className="text-lg font-semibold">
                  Spot #{reservation.spotId}
                </h3>
                <span className={`px-3 py-1 rounded-full text-sm ${
                  reservation.paymentStatus === 'COMPLETED' 
                    ? 'bg-green-100 text-green-800'
                    : 'bg-yellow-100 text-yellow-800'
                }`}>
                  {reservation.paymentStatus}
                </span>
              </div>
              
              <div className="space-y-2">
                <div className="flex items-center gap-2 text-gray-600">
                  <Calendar className="w-4 h-4" />
                  <span>Start: {new Date(reservation.startDate).toLocaleString()}</span>
                </div>
                <div className="flex items-center gap-2 text-gray-600">
                  <Clock className="w-4 h-4" />
                  <span>End: {new Date(reservation.endDate).toLocaleString()}</span>
                </div>
                <div className="flex items-center gap-2 text-gray-600">
                  <CreditCard className="w-4 h-4" />
                  <span>Total: ${reservation.totalPrice.toFixed(2)}</span>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}