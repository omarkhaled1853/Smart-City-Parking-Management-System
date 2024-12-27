import React, { useState } from 'react';
import { X } from 'lucide-react';
import { Spot, ReservationDetails } from '../types/types';
import { calculatePrice } from '../utils/priceCalculator';
import PaymentForm from './PaymentForm';
import { useNotifications } from '../context/NotificationContext';
import ReservationSummary from './ReservationSummary';

interface ReservationModalProps {
  spot: Spot;
  onClose: () => void;
  onReserve: (details: ReservationDetails) => Promise<void>;
}

export default function ReservationModal({ spot, onClose, onReserve }: ReservationModalProps) {
  const [step, setStep] = useState<'duration' | 'summary' | 'payment'>('duration');
  const [duration, setDuration] = useState(1);
  const [totalPrice, setTotalPrice] = useState(0);
  const [startDate] = useState(new Date());
  const [endDate, setEndDate] = useState(new Date());
  const { addNotification } = useNotifications();

  const handleDurationChange = (hours: number) => {
    const price = calculatePrice(spot.pricePerHour, startDate, hours);
    const end = new Date(startDate.getTime() + hours * 60 * 60 * 1000);
    setDuration(hours);
    setTotalPrice(price);
    setEndDate(end);
  };

  const handlePayment = async (method: 'cash' | 'card') => {
    try {
      const reservationDetails: ReservationDetails = {
        spotId: spot.id,
        duration,
        paymentMethod: method,
        totalPrice,
        startDate: startDate.toISOString(),
        endDate: endDate.toISOString(),
        paymentStatus: method === 'card' ? 'COMPLETED' : 'PENDING'
      };

      await onReserve(reservationDetails);
      
      addNotification({
        id: Date.now(),
        userId: 1,
        message: `Successfully reserved spot #${spot.id} for ${duration} hours`,
        type: 'success',
        sentAt: new Date().toISOString()
      });
      onClose();
    } catch (error) {
      addNotification({
        id: Date.now(),
        userId: 1,
        message: 'Failed to reserve spot',
        type: 'error',
        sentAt: new Date().toISOString()
      });
    }
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4">
      <div className="bg-white rounded-lg max-w-md w-full p-6">
        <div className="flex justify-between items-center mb-4">
          <h2 className="text-xl font-semibold">Reserve Spot #{spot.id}</h2>
          <button onClick={onClose} className="p-2 hover:bg-gray-100 rounded-full">
            <X className="w-5 h-5" />
          </button>
        </div>

        {step === 'duration' && (
          <>
            <div className="mb-4">
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Duration (max 7 days)
              </label>
              <input
                type="range"
                min="1"
                max="168"
                value={duration}
                onChange={(e) => handleDurationChange(parseInt(e.target.value))}
                className="w-full"
              />
              <p className="text-sm text-gray-600 mt-2">
                Selected duration: {duration} hours (${totalPrice.toFixed(2)})
              </p>
            </div>
            <button
              onClick={() => setStep('summary')}
              className="w-full bg-blue-600 text-white py-2 rounded-lg hover:bg-blue-700"
            >
              Continue to Summary
            </button>
          </>
        )}

        {step === 'summary' && (
          <ReservationSummary
            startDate={startDate}
            endDate={endDate}
            duration={duration}
            totalPrice={totalPrice}
            onContinue={() => setStep('payment')}
            onBack={() => setStep('duration')}
          />
        )}

        {step === 'payment' && (
          <PaymentForm onSubmit={handlePayment} totalPrice={totalPrice} />
        )}
      </div>
    </div>
  );
}