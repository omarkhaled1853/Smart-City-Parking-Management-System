import React from 'react';

interface ReservationSummaryProps {
  startDate: Date;
  endDate: Date;
  duration: number;
  totalPrice: number;
  onContinue: () => void;
  onBack: () => void;
}

export default function ReservationSummary({
  startDate,
  endDate,
  duration,
  totalPrice,
  onContinue,
  onBack
}: ReservationSummaryProps) {
  return (
    <div className="space-y-4">
      <h3 className="text-lg font-semibold mb-4">Reservation Summary</h3>
      
      <div className="space-y-2">
        <div className="flex justify-between">
          <span className="text-gray-600">Start Date:</span>
          <span className="font-medium">{startDate.toLocaleString()}</span>
        </div>
        
        <div className="flex justify-between">
          <span className="text-gray-600">End Date:</span>
          <span className="font-medium">{endDate.toLocaleString()}</span>
        </div>
        
        <div className="flex justify-between">
          <span className="text-gray-600">Duration:</span>
          <span className="font-medium">{duration} hours</span>
        </div>
        
        <div className="flex justify-between">
          <span className="text-gray-600">Total Price:</span>
          <span className="font-medium">${totalPrice.toFixed(2)}</span>
        </div>
      </div>

      <div className="flex gap-3 mt-6">
        <button
          onClick={onBack}
          className="flex-1 px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-50"
        >
          Back
        </button>
        <button
          onClick={onContinue}
          className="flex-1 bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700"
        >
          Continue to Payment
        </button>
      </div>
    </div>
  );
}