import { Router } from 'express';
import { userRouter } from './userRoutes';
import { parkingRouter } from './parkingRoutes';
import { reservationRouter } from './reservationRoutes';

const router = Router();

router.use('/users', userRouter);
router.use('/parking', parkingRouter);
router.use('/reservations', reservationRouter);

export { router };