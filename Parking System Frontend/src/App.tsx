import React from 'react';
import { ReservationList } from './components/ReservationList';

export const App: React.FC = () => {
  // In a real application, this would come from your authentication context
  const userId = 1;

  return (
    <div className="min-h-screen bg-gray-100">
      <header className="bg-white shadow">
        <div className="max-w-7xl mx-auto py-6 px-4">
          <h1 className="text-3xl font-bold text-gray-900">Driver Profile</h1>
        </div>
      </header>
      <main>
        <ReservationList userId={userId} />
      </main>
    </div>
  );
};

export default App;