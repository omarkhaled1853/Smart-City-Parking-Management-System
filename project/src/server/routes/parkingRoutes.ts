import express from 'express';
import { PrismaClient } from '@prisma/client';
import { z } from 'zod';

const prisma = new PrismaClient();
const router = express.Router();

const parkingLotSchema = z.object({
  name: z.string(),
  location: z.string(),
  capacity: z.number(),
  pricingModel: z.string(),
});

// Get all parking lots
router.get('/lots', async (req, res) => {
  try {
    const lots = await prisma.parkingLot.findMany({
      include: {
        parkingSpots: true,
      },
    });
    res.json(lots);
  } catch (error) {
    res.status(500).json({ error: 'Server error' });
  }
});

// Create parking lot
router.post('/lots', async (req, res) => {
  try {
    const data = parkingLotSchema.parse(req.body);
    const lot = await prisma.parkingLot.create({
      data,
      include: {
        parkingSpots: true,
      },
    });
    res.json(lot);
  } catch (error) {
    res.status(400).json({ error: 'Invalid input data' });
  }
});

// Get available spots
router.get('/spots/available', async (req, res) => {
  try {
    const spots = await prisma.parkingSpot.findMany({
      where: {
        status: 'AVAILABLE',
      },
      include: {
        parkingLot: true,
      },
    });
    res.json(spots);
  } catch (error) {
    res.status(500).json({ error: 'Server error' });
  }
});

export { router as parkingRouter };