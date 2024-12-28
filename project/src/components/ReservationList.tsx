import React, { useEffect, useState } from 'react';
import { getDriverReservations, searchReservations } from '../api/api';
import { ReservationDTO } from '../types/reservation';
import { ReservationCard } from './ReservationCard';
import { SearchBar } from './ProfileSearchBar';
import { CarFront, AlertCircle } from 'lucide-react';

interface ReservationListProps {
  userId: number;
}

export const ReservationList: React.FC<ReservationListProps> = ({ userId }) => {
  const [reservations, setReservations] = useState<ReservationDTO[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const fetchReservations = async () => {
    try {
      const data = await getDriverReservations(userId);
      setReservations(data);
      setError(null);
    } catch (err) {
      setError('Failed to load reservations. Please try again later.');
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async (location: string) => {
    setLoading(true);
    try {
      const data = await searchReservations(userId, location);
      setReservations(data);
      setError(null);
    } catch (err) {
      setError('Failed to search reservations. Please try again later.');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchReservations();
  }, [userId]);

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="animate-pulse text-gray-600">Loading reservations...</div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="flex flex-col items-center justify-center min-h-screen text-red-600">
        <AlertCircle className="w-8 h-8 mb-2" />
        <p>{error}</p>
      </div>
    );
  }

  return (
    <div className="max-w-3xl mx-auto px-4 py-8">
      <h2 className="text-2xl font-bold mb-6">Your Reservations</h2>
      <SearchBar onSearch={handleSearch} />
      {reservations.length === 0 ? (
        <div className="flex flex-col items-center justify-center py-12 text-gray-600">
          <CarFront className="w-12 h-12 mb-4" />
          <p className="text-lg">No reservations found</p>
        </div>
      ) : (
        <div className="space-y-4">
          {reservations.map((reservation) => (
            <ReservationCard
              key={reservation.reservationId}
              reservation={reservation}
            />
          ))}
        </div>
      )}
    </div>
  );
};