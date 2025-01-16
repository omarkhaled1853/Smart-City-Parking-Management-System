import React, { useEffect, useState } from 'react';
import { Lot } from '../types/types';
import { fetchGarages } from '../api/api';
import GarageCard from '../components/GarageCard';
import SearchBar from '../components/SearchBar';

export default function HomePage() {
  const [garages, setGarages] = useState<Lot[]>([]);
  const [filteredGarages, setFilteredGarages] = useState<Lot[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const loadGarages = async () => {
      try {
        const data = await fetchGarages();
        setGarages(data);
        setFilteredGarages(data);
        setError(null);
      } catch (err) {
        setError('Failed to load garages');
      } finally {
        setLoading(false);
      }
    };

    loadGarages();
  }, []);

  const handleSearch = (searchTerm: string) => {
    const normalizedSearch = searchTerm.toLowerCase().trim();
    const filtered = garages.filter(garage => 
      garage.location.toLowerCase().includes(normalizedSearch)
    );
    setFilteredGarages(filtered);
  };

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="text-xl">Loading garages...</div>
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

  return (
    <div className="min-h-screen bg-gray-100 py-12 px-4 sm:px-6 lg:px-8">
      <div className="max-w-7xl mx-auto">
        <h1 className="text-3xl font-bold text-gray-900 mb-8">Available Parking Garages</h1>
        <SearchBar onSearch={handleSearch} />
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {filteredGarages.map((garage) => (
            <GarageCard key={garage.parkingLotID} garage={garage} />
          ))}
        </div>
        {filteredGarages.length === 0 && (
          <div className="text-center text-gray-500 mt-8">
            No garages found matching your search.
          </div>
        )}
      </div>
    </div>
  );
}