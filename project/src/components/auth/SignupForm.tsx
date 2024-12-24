import React, { useState } from 'react';
import { UserPlus } from 'lucide-react';
import type { SignupFormData } from '../../types/auth';
import { useNavigate } from 'react-router-dom';

export function SignupForm() {
  const [formData, setFormData] = useState<SignupFormData>({
    name: '',
    email: '',
    password: '',
    phone: '',
    role: 'Driver',
    licensePlate: '',
  });
  const navigate = useNavigate();

  const [errors, setErrors] = useState({
    phone: '',
  });

  const validatePhone = (phone: string) => {
    const phoneRegex = /^01\d{9}$/; // Regex to check for 12 digits starting with "01"
    if (!phoneRegex.test(phone)) {
      return 'Phone number must be 11 digits and start with "01".';
    }
    return '';
  };

  const handleSubmit = async(e: React.FormEvent) => {
    e.preventDefault();

    // Validate phone number
    const phoneError = validatePhone(formData.phone);
    if (phoneError) {
      setErrors((prev) => ({ ...prev, phone: phoneError }));
      return;
    }

    setErrors({ phone: '' }); // Clear any previous errors
    try {
      const response = await fetch('http://localhost:8080/api/users/signup', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData),
      });
      if (response.ok) {
        console.log('Login successful!');
        if (formData.role === 'ParkingLotManager') {
          navigate('/manager');
        } else if (formData.role === 'Admin') {
          navigate('/admin');
        } else if (formData.role === 'Driver') {
          navigate('/driver');
        }
       
      } else {
 
        console.error('Login failed');
      }
    } catch (error) {
      console.error('Error:', error);
    }
    // TODO: Implement actual signup logic
    console.log('Signup:', formData);
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div>
        <label htmlFor="name" className="block text-sm font-medium text-gray-700">
          Full Name
        </label>
        <input
          type="text"
          id="name"
          className="mt-1 block w-full rounded-md border border-gray-300 px-3 py-2 shadow-sm focus:border-blue-500 focus:outline-none focus:ring-1 focus:ring-blue-500"
          value={formData.name}
          onChange={(e) => setFormData({ ...formData, name: e.target.value })}
          required
        />
      </div>
      <div>
        <label htmlFor="phone" className="block text-sm font-medium text-gray-700">
          Phone Number
        </label>
        <input
          type="text"
          id="phone"
          className="mt-1 block w-full rounded-md border border-gray-300 px-3 py-2 shadow-sm focus:border-blue-500 focus:outline-none focus:ring-1 focus:ring-blue-500"
          value={formData.phone}
          onChange={(e) => setFormData({ ...formData, phone: e.target.value })}
          required
        />
        {errors.phone && <p className="text-sm text-red-500">{errors.phone}</p>}
      </div>
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
        <label htmlFor="role" className="block text-sm font-medium text-gray-700">
          Role
        </label>
        <select
          id="role"
          className="mt-1 block w-full rounded-md border border-gray-300 px-3 py-2 shadow-sm focus:border-blue-500 focus:outline-none focus:ring-1 focus:ring-blue-500"
          value={formData.role}
          onChange={(e) => setFormData({ ...formData, role: e.target.value as 'Driver' | 'ParkingLotManager' | 'Admin' })}
        >
          <option value="driver">Driver</option>
          <option value="parking_manager">Parking Lot Manager</option>
          <option value="admin">Admin</option>
        </select>
      </div>
      {formData.role === 'Driver' && (
        <div>
          <label htmlFor="licensePlate" className="block text-sm font-medium text-gray-700">
            License Plate Number
          </label>
          <input
            type="text"
            id="licensePlate"
            className="mt-1 block w-full rounded-md border border-gray-300 px-3 py-2 shadow-sm focus:border-blue-500 focus:outline-none focus:ring-1 focus:ring-blue-500"
            value={formData.licensePlate}
            onChange={(e) => setFormData({ ...formData, licensePlate: e.target.value })}
          />
        </div>
      )}
      <button
        type="submit"
        className="w-full flex items-center justify-center gap-2 bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700 transition-colors"
      >
        <UserPlus className="w-4 h-4" />
        Create Account
      </button>
      <p className="text-center text-sm text-gray-600">
        Already have an account?{' '}
        <a href="/login" className="text-blue-600 hover:text-blue-800">
          Sign in
        </a>
      </p>
    </form>
  );
}
