import React from 'react';
import { BrowserRouter as Router, Route, Routes, useLocation } from 'react-router-dom';
import { AuthCard } from './components/auth/AuthCard';
import { LoginForm } from './components/auth/LoginForm';
import { SignupForm } from './components/auth/SignupForm';
import { ManagerPage } from './components/ManagerPage';
import { AdminPage } from './components/AdminPage';
import { NotificationProvider } from './context/NotificationContextManager';
import HomePage from './pages/HomePage';
import GaragePage from './pages/GaragePage';
import { ReservationList } from './components/ReservationList';
import AppBar from './components/AppBar';

// Layout component to conditionally render AppBar
const Layout = ({ children }: { children: React.ReactNode }) => {
  const location = useLocation();
  const isDriverRoute = location.pathname.startsWith('/driver');

  return (
    <>
      {isDriverRoute && <AppBar />}
      <div className={isDriverRoute ? "pt-16" : ""}>
        {children}
      </div>
    </>
  );
};

function App() {
  return (
    <NotificationProvider>
      <Router>
        <Layout>
          <div className="flex flex-col gap-8 py-8">
            <Routes>
              <Route path="/login" element={<AuthCard title="Sign In"><LoginForm /></AuthCard>} />
              <Route path="/signup" element={<AuthCard title="Create Account"><SignupForm /></AuthCard>} />
              <Route path="/manager" element={<ManagerPage />} />
              <Route path="/admin" element={<AdminPage />} />
              <Route path="/driver" element={<HomePage />} />
              <Route path="/driver/garage/:id" element={<GaragePage />} />
              <Route path="/driver/profile" element={<ReservationList userId={6} />} />
              <Route path="/" element={<AuthCard title="Sign In"><LoginForm /></AuthCard>} />
            </Routes>
          </div>
        </Layout>
      </Router>
    </NotificationProvider>
  );
}

export default App;