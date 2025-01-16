import React from 'react';
import { Car } from 'lucide-react';

interface AuthCardProps {
  children: React.ReactNode;
  title: string;
}

export function AuthCard({ children, title }: AuthCardProps) {
  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-500 to-purple-600 flex items-center justify-center p-4">
      <div className="bg-white rounded-2xl shadow-xl w-full max-w-md p-8">
        <div className="flex flex-col items-center mb-8">
          <div className="bg-blue-100 p-3 rounded-full mb-4">
            <Car className="w-8 h-8 text-blue-600" />
          </div>
          <h1 className="text-2xl font-bold text-gray-800">Smart City Parking</h1>
          <h2 className="text-xl font-semibold text-gray-700 mt-2">{title}</h2>
        </div>
        {children}
      </div>
    </div>
  );
}