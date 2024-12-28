import React, { useState } from 'react';
import { LogIn } from 'lucide-react';
import { useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';

export function LoginForm() {
  const [formData, setFormData] = useState({ email: '', password: '', role: '' });
  const navigate = useNavigate();
  const [errors, setErrors] = useState({ api: '' });

  const handleRoleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, role: e.target.value });
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      const response = await fetch('http://localhost:8080/api/users/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData),
      });
      if (response.ok) {
        const data = await response.json(); // Assuming response includes the UserID in the JSON payload
        document.cookie = `UserID=${data.userId}; path=/; Secure; SameSite=Strict`;
        console.log('Login successful!');
        // Navigate based on role
        const userId = data.userId;
        
        Cookies.set('userId', userId);

        if (formData.role === 'ParkingLotManager') {
          navigate(`/manager?userId=${userId}`);
        } else if (formData.role === 'Admin') {
          navigate(`/admin?userId=${userId}`);
        } else if (formData.role === 'Driver') {
          navigate(`/driver?userId=${userId}`);
        }
      } else {
        const errorData = await response.json(); // Parse JSON error response
        setErrors((prev) => ({
          ...prev,
          api: errorData.message || 'Login failed. Please try again.', // Display backend message if available
        }));
      }
    } catch (error) {
      console.error('Error:', error);
      setErrors((prev) => ({
        ...prev,
        api: 'An unexpected error occurred. Please try again later.', // Handle network or other unexpected errors
      }));
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div>
        <label htmlFor="email" className="block text-sm font-medium text-gray-700">
          Email
        </label>
        <input
          type="email"
          id="email"
          className="mt-1 block w-full rounded-md border border-gray-300 px-3 py-2 shadow-sm focus:border-blue-500 focus:outline-none focus:ring-1 focus:ring-blue-500"
          value={formData.email}
          onChange={(e) => setFormData({ ...formData, email: e.target.value })}
          required
        />
      </div>
      <div>
        <label htmlFor="password" className="block text-sm font-medium text-gray-700">
          Password
        </label>
        <input
          type="password"
          id="password"
          className="mt-1 block w-full rounded-md border border-gray-300 px-3 py-2 shadow-sm focus:border-blue-500 focus:outline-none focus:ring-1 focus:ring-blue-500"
          value={formData.password}
          onChange={(e) => setFormData({ ...formData, password: e.target.value })}
          required
        />
      </div>
      <div>
        <label className="block text-sm font-medium text-gray-700">
          Select Role
        </label>
        <div className="flex gap-4 mt-2">
          <label className="flex items-center gap-2">
            <input
              type="radio"
              name="role"
              value="Driver"
              onChange={handleRoleChange}
              required
            />
            Driver
          </label>
          <label className="flex items-center gap-2">
            <input
              type="radio"
              name="role"
              value="ParkingLotManager"
              onChange={handleRoleChange}
              required
            />
            ParkingLotManager
          </label>
          <label className="flex items-center gap-2">
            <input
              type="radio"
              name="role"
              value="Admin"
              onChange={handleRoleChange}
              required
            />
            Admin
          </label>
        </div>
      </div>
      {errors.api && <p className="text-sm text-red-500">{errors.api}</p>}
      <button
        type="submit"
        className="w-full flex items-center justify-center gap-2 bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700 transition-colors"
      >
        <LogIn className="w-4 h-4" />
        Sign In
      </button>
      <p className="text-center text-sm text-gray-600">
        Don't have an account?{' '}
        <a href="/signup" className="text-blue-600 hover:text-blue-800">
          Sign up
        </a>
      </p>
    </form>
  );
}
