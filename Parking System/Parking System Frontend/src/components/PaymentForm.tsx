import React, { useState } from 'react';

interface PaymentFormProps {
  onSubmit: (method: 'cash' | 'card') => Promise<void>;
  totalPrice: number;
}

export default function PaymentForm({ onSubmit, totalPrice }: PaymentFormProps) {
  const [paymentMethod, setPaymentMethod] = useState<'cash' | 'card'>('cash');
  const [cardNumber, setCardNumber] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    
    if (paymentMethod === 'card') {
      // Simulate card payment processing
      await new Promise(resolve => setTimeout(resolve, 1500));
    }
    
    await onSubmit(paymentMethod);
    setLoading(false);
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className="mb-4">
        <p className="text-lg font-semibold mb-2">
          Total Amount: ${totalPrice.toFixed(2)}
        </p>
        <div className="space-y-2">
          <label className="flex items-center">
            <input
              type="radio"
              value="cash"
              checked={paymentMethod === 'cash'}
              onChange={(e) => setPaymentMethod(e.target.value as 'cash')}
              className="mr-2"
            />
            Cash Payment
          </label>
          <label className="flex items-center">
            <input
              type="radio"
              value="card"
              checked={paymentMethod === 'card'}
              onChange={(e) => setPaymentMethod(e.target.value as 'card')}
              className="mr-2"
            />
            Card Payment
          </label>
        </div>
      </div>

      {paymentMethod === 'card' && (
        <div className="mb-4">
          <label className="block text-sm font-medium text-gray-700 mb-2">
            Card Number
          </label>
          <input
            type="text"
            value={cardNumber}
            onChange={(e) => setCardNumber(e.target.value)}
            placeholder="1234 5678 9012 3456"
            className="w-full px-3 py-2 border rounded-lg"
            required
          />
        </div>
      )}

      <button
        type="submit"
        disabled={loading}
        className="w-full bg-blue-600 text-white py-2 rounded-lg hover:bg-blue-700 disabled:bg-blue-300"
      >
        {loading ? 'Processing...' : 'Complete Reservation'}
      </button>
    </form>
  );
}