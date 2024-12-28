import { Request, Response } from 'express';
import { UserService } from '../services/userService';
import { z } from 'zod';

const userSchema = z.object({
  email: z.string().email(),
  password: z.string().min(6),
  name: z.string(),
  phone: z.string().optional(),
  licensePlate: z.string().optional(),
});

export class UserController {
  static async register(req: Request, res: Response) {
    try {
      const data = userSchema.parse(req.body);
      const user = await UserService.createUser(data);
      res.json(user);
    } catch (error) {
      res.status(400).json({ error: 'Invalid input data' });
    }
  }

  static async login(req: Request, res: Response) {
    try {
      const { email, password } = req.body;
      const user = await UserService.validateUser(email, password);
      
      if (!user) {
        return res.status(401).json({ error: 'Invalid credentials' });
      }

      const token = UserService.generateToken(user);
      res.json({ token, user: { id: user.id, email: user.email, role: user.role } });
    } catch (error) {
      res.status(500).json({ error: 'Server error' });
    }
  }
}