import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Spot } from '../types/types';
import { fetchSpotsByLotId } from '../api/api';
import SpotList from '../components/SpotList';

export default function GaragePage() {
  const { id } = useParams<{ id: string }>();
  const [spots, setSpots] = useState<Spot[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    loadSpots();
  }, [id]);

  const loadSpots = async () => {
    try {
      const data = await fetchSpotsByLotId(Number(id));
      setSpots(data);
    } catch (err) {
      setError('Failed to load spots');
    } finally {
      setLoading(false);
    }
  };

  const handleSpotUpdate = (updatedSpot: Spot) => {
    setSpots(currentSpots => 
      currentSpots.map(spot => 
        spot.spotID === updatedSpot.spotID ? updatedSpot : spot
      )
    );
  };

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="text-xl">Loading spots...</div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="text-xl text-red-600">{error}</div>
      </div>
    );
  }

  const occupiedSpots = spots.filter(spot => 
    spot.status === 'OCCUPIED' || spot.status === 'RESERVED'
  );
  const availableSpots = spots.filter(spot => 
    spot.status === 'AVAILABLE'
  );

  return (
    <div className="min-h-screen bg-gray-100 py-12 px-4 sm:px-6 lg:px-8">
      <div className="max-w-7xl mx-auto">
        <h1 className="text-3xl font-bold text-gray-900 mb-8">Garage #{id} Spots</h1>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <SpotList 
            title="Occupied/Reserved Spots" 
            spots={occupiedSpots} 
            showReservation={false}
            onSpotUpdate={handleSpotUpdate}
          />
          <SpotList 
            title="Available Spots" 
            spots={availableSpots} 
            showReservation={true}
            onSpotUpdate={handleSpotUpdate}
          />
        </div>
      </div>
    </div>
  );
}