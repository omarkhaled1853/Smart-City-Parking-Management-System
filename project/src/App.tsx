import React from 'react';
import { AuthCard } from './components/auth/AuthCard';
import { LoginForm } from './components/auth/LoginForm';
import { SignupForm } from './components/auth/SignupForm';

function App() {
  // For demo purposes, let's show both forms
  // In a real app, you'd use a router to switch between them
  return (
    <div className="flex flex-col gap-8 py-8">
      <AuthCard title="Sign In">
        <LoginForm />
      </AuthCard>
      
      <AuthCard title="Create Account">
        <SignupForm />
      </AuthCard>
    </div>
  );
}

export default App;