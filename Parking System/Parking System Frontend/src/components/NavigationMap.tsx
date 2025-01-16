import React, { useEffect, useState, useRef } from 'react';
import { MapContainer, TileLayer, Marker, Popup, useMap } from 'react-leaflet';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import { useGeolocation } from '../hooks/useGeolocation';
import { Location } from '../types/location';

// Fix for default marker icons
delete (L.Icon.Default.prototype as any)._getIconUrl;
L.Icon.Default.mergeOptions({
  iconRetinaUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon-2x.png',
  iconUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon.png',
  shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-shadow.png',
});

interface NavigationMapProps {
  destination: Location;
  onClose: () => void;
}

function RouteLayer({ source, destination }: { source: L.LatLng, destination: L.LatLng }) {
  const map = useMap();
  const initialFitRef = useRef(false);

  useEffect(() => {
    // Create a line between source and destination
    const routeLine = L.polyline([source, destination], {
      color: '#3B82F6',
      weight: 4,
      opacity: 0.8,
      dashArray: '10, 10',
      lineCap: 'round'
    }).addTo(map);

    // Only fit bounds once when the route is first drawn
    if (!initialFitRef.current) {
      map.fitBounds(L.latLngBounds([source, destination]), {
        padding: [50, 50]
      });
      initialFitRef.current = true;
    }
    
    return () => {
      routeLine.remove();
    };
  }, [map, source, destination]);

  return null;
}

function LocationMarker({ onLocationUpdate }: { onLocationUpdate: (pos: L.LatLng) => void }) {
  const [position, setPosition] = useState<L.LatLng | null>(null);
  const { location } = useGeolocation();

  useEffect(() => {
    if (location) {
      const newPos = new L.LatLng(location.latitude, location.longitude);
      setPosition(newPos);
      onLocationUpdate(newPos);
    }
  }, [location, onLocationUpdate]);

  return position === null ? null : (
    <Marker position={position}>
      <Popup>Your Location</Popup>
    </Marker>
  );
}

export const NavigationMap: React.FC<NavigationMapProps> = ({ destination, onClose }) => {
  const [currentPosition, setCurrentPosition] = useState<L.LatLng | null>(null);
  const destinationLatLng = new L.LatLng(destination.latitude, destination.longitude);

  return (
    <div className="fixed inset-0 z-50 bg-black bg-opacity-50 flex items-center justify-center p-4">
      <div className="bg-white rounded-lg w-full max-w-4xl h-[80vh] flex flex-col">
        <div className="p-4 border-b flex justify-between items-center">
          <h2 className="text-xl font-semibold">Navigation</h2>
          <button
            onClick={onClose}
            className="text-gray-500 hover:text-gray-700"
          >
            ✕
          </button>
        </div>
        <div className="flex-1 relative">
          <MapContainer
            center={destinationLatLng}
            zoom={13}
            className="h-full w-full"
            scrollWheelZoom={true}
          >
            <TileLayer
              attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
              url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            />
            <LocationMarker onLocationUpdate={setCurrentPosition} />
            <Marker position={destinationLatLng}>
              <Popup>Destination</Popup>
            </Marker>
            {currentPosition && (
              <RouteLayer 
                source={currentPosition} 
                destination={destinationLatLng} 
              />
            )}
          </MapContainer>
        </div>
      </div>
    </div>
  );
};