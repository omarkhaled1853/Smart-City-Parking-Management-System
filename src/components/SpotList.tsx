import React from 'react';
import { Spot } from '../types/types';
import SpotCard from './SpotCard';

interface SpotListProps {
  title: string;
  spots: Spot[];
  showReservation?: boolean;
  onSpotUpdate: (spot: Spot) => void;
}

export default function SpotList({ title, spots, showReservation = false, onSpotUpdate }: SpotListProps) {
  return (
    <div className="bg-white rounded-lg shadow-md p-6">
      <h2 className="text-xl font-semibold mb-4">{title}</h2>
      <div className="space-y-4">
        {spots.map((spot) => (
          <SpotCard 
            key={spot.spotID} 
            spot={spot} 
            showReservation={showReservation && spot.status === 'AVAILABLE'}
            onSpotUpdate={onSpotUpdate}
          />
        ))}
      </div>
    </div>
  );
}