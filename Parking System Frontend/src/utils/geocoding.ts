import { Location } from '../types/location';

const NOMINATIM_API = 'https://nominatim.openstreetmap.org/search';
const DEFAULT_LOCATION: Location = {
  latitude: 40.7128,
  longitude: -74.0060, // New York City as fallback
};

export const geocodeAddress = async (address: string): Promise<Location> => {
  if (!address.trim()) {
    return DEFAULT_LOCATION;
  }

  try {
    const params = new URLSearchParams({
      format: 'json',
      q: address,
      limit: '1',
      addressdetails: '1',
    });

    const response = await fetch(`${NOMINATIM_API}?${params}`, {
      headers: {
        'Accept': 'application/json',
        'User-Agent': 'ParkingReservationApp', // Required by Nominatim ToS
      },
    });

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }

    const data = await response.json();
    
    if (!data || !data[0] || !data[0].lat || !data[0].lon) {
      console.warn('Location not found:', address);
      return DEFAULT_LOCATION;
    }

    return {
      latitude: parseFloat(data[0].lat),
      longitude: parseFloat(data[0].lon),
    };
  } catch (error) {
    console.warn('Geocoding error:', address, error);
    return DEFAULT_LOCATION;
  }
};