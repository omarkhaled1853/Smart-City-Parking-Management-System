-- Get all reservations for specific user  
DELIMITER $$
 
CREATE PROCEDURE GetAllReservations(IN user_id INT)
BEGIN
	SELECT 
		r.ReservationID,
		r.StartTime,
		r.EndTime,
		r.Status AS ReservationStatus,
		ps.SpotID,
		ps.SpotType,
		ps.Status AS SpotStatus,
		pl.Name AS ParkingLotName,
		pl.Location AS ParkingLotLocation,
        pl.PricingModel
	FROM 
		Reservation r
	JOIN 
		ParkingSpot ps ON r.SpotID = ps.SpotID
	JOIN 
		ParkingLot pl ON ps.ParkingLotID = pl.ParkingLotID
	WHERE r.UserID = user_id;
END $$

DELIMITER ;

-- Get all reservations for specific user by location
DELIMITER $$

CREATE PROCEDURE GetAllReservationsByLocation(
    IN user_id INT,
    IN location VARCHAR(200)
)
BEGIN
    SELECT 
        r.ReservationID,
        r.StartTime,
        r.EndTime,
        r.Status AS ReservationStatus,
        ps.SpotID,
        ps.SpotType,
        ps.Status AS SpotStatus,
        pl.Name AS ParkingLotName,
        pl.Location AS ParkingLotLocation,
        pl.PricingModel
    FROM 
        Reservation r
    JOIN 
        ParkingSpot ps ON r.SpotID = ps.SpotID
    JOIN 
        ParkingLot pl ON ps.ParkingLotID = pl.ParkingLotID
    WHERE 
        r.UserID = user_id
        AND pl.Location LIKE CONCAT('%', location, '%');
END $$

DELIMITER ;

-- Get near expire reservations for all users
DELIMITER $$

CREATE PROCEDURE GetNearExpireReservations()
BEGIN
    SELECT
		r.ReservationID,
		r.UserID,
        ps.SpotID,
        ps.SpotType,
        ps.Status AS SpotStatus,
        pl.Name AS ParkingLotName,
        pl.Location AS ParkingLotLocation,
        pl.PricingModel
    FROM 
        Reservation r
    JOIN 
        ParkingSpot ps ON r.SpotID = ps.SpotID
    JOIN 
        ParkingLot pl ON ps.ParkingLotID = pl.ParkingLotID
    WHERE 
		r.endTime > NOW()
		AND r.endTime <= NOW() + INTERVAL 15 MINUTE;
END $$

DELIMITER ;

-- Enable schedualling
SET GLOBAL event_scheduler = ON;

-- Automatic relese the spot and change the reservation to COMPLETE and the spot to be AVAILABLE 
CREATE EVENT AutomaticRelease
ON SCHEDULE EVERY 1 MINUTE
DO
    UPDATE Reservation r
    JOIN ParkingSpot p ON r.SpotID = p.SpotID
    SET r.Status = 'COMPLETED', p.Status = 'AVAILABLE'
    WHERE r.EndTime < NOW() AND r.Status = 'ACTIVE';
    
SHOW VARIABLES LIKE 'event_scheduler';

SHOW EVENTS;





