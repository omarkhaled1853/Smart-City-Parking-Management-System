import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { AuthCard } from './components/auth/AuthCard';
import { LoginForm } from './components/auth/LoginForm';
import { SignupForm } from './components/auth/SignupForm';
import { ManagerPage } from './components/ManagerPage';

function App() {
  return (
    <Router>
      <div className="flex flex-col gap-8 py-8">
        <Routes>
          <Route path="/login" element={<AuthCard title="Sign In"><LoginForm /></AuthCard>} />
          <Route path="/signup" element={<AuthCard title="Create Account"><SignupForm /></AuthCard>} />
          <Route path="/manager" element={<ManagerPage />} />
          <Route path="/" element={<AuthCard title="Sign In"><LoginForm /></AuthCard>} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;