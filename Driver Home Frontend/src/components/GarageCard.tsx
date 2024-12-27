import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Lot } from '../types/types';
import { Building2 } from 'lucide-react';

interface GarageCardProps {
  garage: Lot;
}

export default function GarageCard({ garage }: GarageCardProps) {
  const navigate = useNavigate();

  return (
    <div 
      onClick={() => navigate(`/garage/${garage.parkingLotID}`)}
      className="bg-white rounded-lg shadow-lg overflow-hidden cursor-pointer transform transition-transform hover:scale-105"
    >
      <img 
        src={`/images/garage${garage.parkingLotID}.png`}
        alt={garage.name}
        className="w-full h-48 object-cover"
      />
      <div className="p-6">
        <div className="flex items-center gap-2 mb-3">
          <Building2 className="w-6 h-6 text-blue-600" />
          <h3 className="text-xl font-semibold text-gray-800">{garage.name}</h3>
        </div>
        <div className="space-y-2">
          <p className="text-gray-600">
            <span className="font-medium">Location:</span> {garage.location}
          </p>
          <p className="text-gray-600">
            <span className="font-medium">Capacity:</span> {garage.capacity} spots
          </p>
        </div>
      </div>
    </div>
  );
}