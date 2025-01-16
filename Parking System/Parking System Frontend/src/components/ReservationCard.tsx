import React, { useState } from 'react';
import { Calendar, Clock, MapPin, Car, Navigation } from 'lucide-react';
import { ReservationDTO } from '../types/reservation';
import { formatDate, formatTime } from '../utils/dateFormatters';
import { getStatusColor } from '../utils/statusHelpers';
import { NavigationMap } from './NavigationMap';
import { geocodeAddress } from '../utils/geocoding';
import { Location } from '../types/location';

interface ReservationCardProps {
  reservation: ReservationDTO;
}

export const ReservationCard: React.FC<ReservationCardProps> = ({ reservation }) => {
  const [showNavigation, setShowNavigation] = useState(false);
  const [destination, setDestination] = useState<Location | null>(null);

  const handleNavigationClick = async () => {
    const location = await geocodeAddress(reservation.parkingLot.location);
    setDestination(location);
    setShowNavigation(true);
  };

  return (
    <>
      <div className="bg-white rounded-lg shadow-md p-6 mb-4">
        <div className="flex justify-between items-start mb-4">
          <h3 className="text-lg font-semibold">{reservation.parkingLot.name}</h3>
          <span className={`px-3 py-1 rounded-full text-sm font-medium ${getStatusColor(reservation.reservationStatus)}`}>
            {reservation.reservationStatus}
          </span>
        </div>
        
        <div className="space-y-3">
          <div className="flex items-center text-gray-600">
            <MapPin className="w-5 h-5 mr-2" />
            <span>{reservation.parkingLot.location}</span>
          </div>
          
          <div className="flex items-center text-gray-600">
            <Calendar className="w-5 h-5 mr-2" />
            <span>{formatDate(reservation.startTime)} - {formatDate(reservation.endTime)}</span>
          </div>
          
          <div className="flex items-center text-gray-600">
            <Clock className="w-5 h-5 mr-2" />
            <span>{formatTime(reservation.startTime)} - {formatTime(reservation.endTime)}</span>
          </div>
          
          <div className="flex items-center text-gray-600">
            <Car className="w-5 h-5 mr-2" />
            <span>Spot #{reservation.parkingSpot.spotId} ({reservation.parkingSpot.spotType})</span>
          </div>
        </div>
        
        <div className="mt-4 pt-4 border-t border-gray-200 flex justify-between items-center">
          <p className="text-sm text-gray-600">
            Pricing Model: {reservation.parkingLot.pricingModel}
          </p>
          <button
            onClick={handleNavigationClick}
            className="flex items-center px-4 py-2 text-sm text-white bg-blue-600 rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2"
          >
            <Navigation className="w-4 h-4 mr-2" />
            Navigate
          </button>
        </div>
      </div>

      {showNavigation && destination && (
        <NavigationMap
          destination={destination}
          onClose={() => setShowNavigation(false)}
        />
      )}
    </>
  );
};