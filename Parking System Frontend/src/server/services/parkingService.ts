import { prisma } from '../config/database';
import { ParkingLot, ParkingSpot, SpotStatus } from '@prisma/client';

export class ParkingService {
  static async getAvailableSpots() {
    return prisma.parkingSpot.findMany({
      where: { status: SpotStatus.AVAILABLE },
      include: { parkingLot: true },
    });
  }

  static async calculatePrice(spotId: number, duration: number) {
    const spot = await prisma.parkingSpot.findUnique({
      where: { id: spotId },
      include: { parkingLot: true },
    });

    if (!spot) throw new Error('Spot not found');

    // Basic price calculation
    let price = spot.pricePerHour * duration;

    // Apply dynamic pricing if enabled
    if (spot.parkingLot.pricingModel === 'DYNAMIC') {
      // Add 20% during peak hours (9 AM - 5 PM)
      const hour = new Date().getHours();
      if (hour >= 9 && hour <= 17) {
        price *= 1.2;
      }
    }

    return price;
  }
}