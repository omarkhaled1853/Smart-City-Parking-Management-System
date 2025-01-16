import express from 'express';
import { PrismaClient } from '@prisma/client';
import bcrypt from 'bcryptjs';
import jwt from 'jsonwebtoken';
import { z } from 'zod';

const prisma = new PrismaClient();
const router = express.Router();

const userSchema = z.object({
  email: z.string().email(),
  password: z.string().min(6),
  name: z.string(),
  phone: z.string().optional(),
  licensePlate: z.string().optional(),
  roleId: z.number(),
});

// Register new user
router.post('/register', async (req, res) => {
  try {
    const data = userSchema.parse(req.body);
    const hashedPassword = await bcrypt.hash(data.password, 10);

    const user = await prisma.user.create({
      data: {
        ...data,
        password: hashedPassword,
      },
      select: {
        id: true,
        email: true,
        name: true,
        role: true,
      },
    });

    res.json(user);
  } catch (error) {
    res.status(400).json({ error: 'Invalid input data' });
  }
});

// Login
router.post('/login', async (req, res) => {
  try {
    const { email, password } = req.body;
    const user = await prisma.user.findUnique({
      where: { email },
      include: { role: true },
    });

    if (!user || !await bcrypt.compare(password, user.password)) {
      return res.status(401).json({ error: 'Invalid credentials' });
    }

    const token = jwt.sign(
      { userId: user.id, role: user.role.name },
      process.env.JWT_SECRET || 'secret',
      { expiresIn: '24h' }
    );

    res.json({ token, user: { id: user.id, email: user.email, role: user.role.name } });
  } catch (error) {
    res.status(500).json({ error: 'Server error' });
  }
});

export { router as userRouter };