import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { NotificationProvider } from './context/NotificationContext';
import AppBar from './components/AppBar';
import HomePage from './pages/HomePage';
import GaragePage from './pages/GaragePage';
import { ReservationList } from './components/ReservationList';
function App() {
  return (
    <NotificationProvider>
      <Router>
        <AppBar />
        <div className="pt-16"> {/* Add padding for AppBar */}
          <Routes>
            <Route path="/" element={<Navigate to="/driver" replace />} />
            <Route path="/driver" element={<HomePage />} />
            <Route path="/driver/garage/:id" element={<GaragePage />} />
            <Route path="/driver/profile" element={<ReservationList userId={6} />} />
          </Routes>
        </div>
      </Router>
    </NotificationProvider>
  );
}

export default App;