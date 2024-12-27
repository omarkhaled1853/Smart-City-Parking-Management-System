import React, { useState } from 'react';
import { Car, BatteryCharging, Bike, Ambulance } from 'lucide-react';
import { Spot, ReservationDetails } from '../types/types';
import { updateSpotStatus } from '../api/api';
import ReservationModal from './ReservationModal';
import { useNotifications } from '../context/NotificationContext';

interface SpotCardProps {
  spot: Spot;
  showReservation?: boolean;
  onSpotUpdate: (spot: Spot) => void;
}

const SpotTypeIcon = ({ type }: { type: string }) => {
  switch (type) {
    case 'EVCHARGING':
      return <BatteryCharging className="w-5 h-5" />;
    case 'MOTOR':
      return <Bike className="w-5 h-5" />;
    case 'DISABLED':
      return <Ambulance className="w-5 h-5" />;
    default:
      return <Car className="w-5 h-5" />;
  }
};

export default function SpotCard({ spot, showReservation = false, onSpotUpdate }: SpotCardProps) {
  const [showModal, setShowModal] = useState(false);
  const [isReserving, setIsReserving] = useState(false);
  const { addNotification } = useNotifications();

  const handleReserve = async (details: ReservationDetails) => {
    if (isReserving) return;
    
    try {
      setIsReserving(true);
      const success = await updateSpotStatus({
        id: spot.id,
        status: 'Reserved',
        userId: 1,
        startDate: details.startDate,
        endDate: details.endDate,
        totalPrice: details.totalPrice,
        paymentStatus: details.paymentStatus
      });
      
      if (success) {
        const updatedSpot: Spot = { 
          ...spot, 
          status: 'RESERVED' as const 
        };
        onSpotUpdate(updatedSpot);
        setShowModal(false);
        
        addNotification({
          id: Date.now(),
          userId: 1,
          message: `Successfully reserved spot #${spot.id} for ${details.duration} hours`,
          type: 'success',
          sentAt: new Date().toISOString()
        });
      } else {
        throw new Error('Failed to reserve spot');
      }
    } catch (error) {
      addNotification({
        id: Date.now(),
        userId: 1,
        message: 'Failed to reserve spot - Please try again',
        type: 'error',
        sentAt: new Date().toISOString()
      });
    } finally {
      setIsReserving(false);
    }
  };

  return (
    <>
      <div className="border rounded-lg p-4 flex items-center justify-between hover:bg-gray-50 transition-colors">
        <div className="flex items-center gap-3">
          <SpotTypeIcon type={spot.type} />
          <div>
            <p className="font-medium">Spot #{spot.id}</p>
            <p className="text-sm text-gray-600">{spot.type}</p>
          </div>
        </div>
        <div className="flex items-center gap-4">
          <div className="text-right">
            <p className="font-medium">${spot.pricePerHour}/hour</p>
            <p className={`text-sm ${
              spot.status === 'AVAILABLE' ? 'text-green-600' : 'text-red-600'
            }`}>
              {spot.status}
            </p>
          </div>
          {showReservation && (
            <button
              onClick={() => setShowModal(true)}
              disabled={isReserving}
              className="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors disabled:bg-blue-300"
            >
              {isReserving ? 'Reserving...' : 'Reserve'}
            </button>
          )}
        </div>
      </div>

      {showModal && (
        <ReservationModal
          spot={spot}
          onClose={() => setShowModal(false)}
          onReserve={handleReserve}
        />
      )}
    </>
  );
}