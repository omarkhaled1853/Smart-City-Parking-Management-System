import express from 'express';
import { PrismaClient } from '@prisma/client';
import { z } from 'zod';

const prisma = new PrismaClient();
const router = express.Router();

const reservationSchema = z.object({
  userId: z.number(),
  spotId: z.number(),
  startTime: z.string().transform(str => new Date(str)),
  endTime: z.string().transform(str => new Date(str)),
});

// Create reservation
router.post('/', async (req, res) => {
  try {
    const data = reservationSchema.parse(req.body);
    
    // Check if spot is available
    const spot = await prisma.parkingSpot.findUnique({
      where: { id: data.spotId },
    });

    if (!spot || spot.status !== 'AVAILABLE') {
      return res.status(400).json({ error: 'Spot not available' });
    }

    const reservation = await prisma.reservation.create({
      data: {
        ...data,
        status: 'PENDING',
      },
      include: {
        parkingSpot: true,
        user: true,
      },
    });

    // Update spot status
    await prisma.parkingSpot.update({
      where: { id: data.spotId },
      data: { status: 'RESERVED' },
    });

    res.json(reservation);
  } catch (error) {
    res.status(400).json({ error: 'Invalid input data' });
  }
});

// Get user reservations
router.get('/user/:userId', async (req, res) => {
  try {
    const userId = parseInt(req.params.userId);
    const reservations = await prisma.reservation.findMany({
      where: { userId },
      include: {
        parkingSpot: {
          include: {
            parkingLot: true,
          },
        },
        payment: true,
      },
    });
    res.json(reservations);
  } catch (error) {
    res.status(500).json({ error: 'Server error' });
  }
});

export { router as reservationRouter };