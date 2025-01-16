-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema smartcityparking
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema smartcityparking
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `smartcityparking` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `smartcityparking` ;

insert into smartcityparking.Role (RoleID,RoleName) VALUES (1,'Driver'),(2,'ParkingLotManager'),(3,'Admin');


-- -----------------------------------------------------
-- Table `smartcityparking`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `smartcityparking`.`user` (
  `UserID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(100) NOT NULL,
  `Email` VARCHAR(150) NOT NULL,
  `Phone` VARCHAR(15) NULL DEFAULT NULL,
  `LicensePlate` VARCHAR(20) NULL DEFAULT NULL,
  `CreatedAt` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `Password` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`UserID`),
  UNIQUE INDEX `Email` (`Email` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `smartcityparking`.`notification`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `smartcityparking`.`notification` (
  `NotificationID` INT NOT NULL AUTO_INCREMENT,
  `UserID` INT NULL DEFAULT NULL,
  `Message` TEXT NOT NULL,
  `SentAt` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`NotificationID`),
  INDEX `UserID` (`UserID` ASC) VISIBLE,
  CONSTRAINT `notification_ibfk_1`
    FOREIGN KEY (`UserID`)
    REFERENCES `smartcityparking`.`user` (`UserID`))
ENGINE = InnoDB
AUTO_INCREMENT = 91
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `smartcityparking`.`parkinglot`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `smartcityparking`.`parkinglot` (
  `ParkingLotID` INT NOT NULL AUTO_INCREMENT,
  `UserID` INT NOT NULL,
  `Name` VARCHAR(100) NOT NULL,
  `Location` VARCHAR(200) NOT NULL,
  `Capacity` INT NOT NULL,
  `PricingModel` ENUM('STATIC', 'DYNAMIC') NULL DEFAULT 'DYNAMIC',
  `CreatedAt` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ParkingLotID`),
  INDEX `fk_parkinglot_user_idx` (`UserID` ASC) VISIBLE,
  CONSTRAINT `fk_parkinglot_user`
    FOREIGN KEY (`UserID`)
    REFERENCES `smartcityparking`.`user` (`UserID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 27
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `smartcityparking`.`parkingspot`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `smartcityparking`.`parkingspot` (
  `SpotID` INT NOT NULL AUTO_INCREMENT,
  `ParkingLotID` INT NULL DEFAULT NULL,
  `SpotType` ENUM('REGULAR', 'DISABLED', 'EVCHARGING') NOT NULL,
  `Status` ENUM('AVAILABLE', 'OCCUPIED', 'RESERVED') NULL DEFAULT 'AVAILABLE',
  `PricePerHour` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`SpotID`),
  INDEX `ParkingLotID` (`ParkingLotID` ASC) VISIBLE,
  CONSTRAINT `parkingspot_ibfk_1`
    FOREIGN KEY (`ParkingLotID`)
    REFERENCES `smartcityparking`.`parkinglot` (`ParkingLotID`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `smartcityparking`.`reservation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `smartcityparking`.`reservation` (
  `ReservationID` INT NOT NULL AUTO_INCREMENT,
  `SpotID` INT NULL DEFAULT NULL,
  `UserID` INT NULL DEFAULT NULL,
  `StartTime` DATETIME NOT NULL,
  `EndTime` DATETIME NOT NULL,
  `Status` ENUM('ACTIVE', 'COMPLETED', 'CANCELED') NULL DEFAULT 'ACTIVE',
  `CreatedAt` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ReservationID`),
  INDEX `SpotID` (`SpotID` ASC) VISIBLE,
  INDEX `UserID` (`UserID` ASC) VISIBLE,
  CONSTRAINT `reservation_ibfk_1`
    FOREIGN KEY (`SpotID`)
    REFERENCES `smartcityparking`.`parkingspot` (`SpotID`),
  CONSTRAINT `reservation_ibfk_2`
    FOREIGN KEY (`UserID`)
    REFERENCES `smartcityparking`.`user` (`UserID`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `smartcityparking`.`payment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `smartcityparking`.`payment` (
  `PaymentID` INT NOT NULL AUTO_INCREMENT,
  `ReservationID` INT NULL DEFAULT NULL,
  `Amount` DECIMAL(10,2) NOT NULL,
  `PaymentTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `PaymentStatus` ENUM('Pending', 'Completed', 'Failed') NULL DEFAULT 'Pending',
  PRIMARY KEY (`PaymentID`),
  INDEX `ReservationID` (`ReservationID` ASC) VISIBLE,
  CONSTRAINT `payment_ibfk_1`
    FOREIGN KEY (`ReservationID`)
    REFERENCES `smartcityparking`.`reservation` (`ReservationID`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `smartcityparking`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `smartcityparking`.`role` (
  `RoleID` INT NOT NULL AUTO_INCREMENT,
  `RoleName` ENUM('Driver', 'ParkingLotManager', 'Admin') NOT NULL,
  PRIMARY KEY (`RoleID`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `smartcityparking`.`userroles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `smartcityparking`.`userroles` (
  `UserID` INT NOT NULL,
  `RoleID` INT NOT NULL,
  PRIMARY KEY (`UserID`, `RoleID`),
  INDEX `RoleID` (`RoleID` ASC) VISIBLE,
  CONSTRAINT `userroles_ibfk_1`
    FOREIGN KEY (`UserID`)
    REFERENCES `smartcityparking`.`user` (`UserID`),
  CONSTRAINT `userroles_ibfk_2`
    FOREIGN KEY (`RoleID`)
    REFERENCES `smartcityparking`.`role` (`RoleID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

USE `smartcityparking` ;

-- -----------------------------------------------------
-- Placeholder table for view `smartcityparking`.`admindashboard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `smartcityparking`.`admindashboard` (`ParkingLotID` INT, `OccupiedSpots` INT, `AvailableSpots` INT, `ReservedSpots` INT);

-- -----------------------------------------------------
-- procedure UpdateSpotPricing
-- -----------------------------------------------------

DELIMITER $$
USE `smartcityparking`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `UpdateSpotPricing`(IN spotID INT, IN demandFactor DECIMAL(5, 2))
BEGIN
    UPDATE ParkingSpot
    SET PricePerHour = PricePerHour * demandFactor
    WHERE SpotID = spotID;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- View `smartcityparking`.`admindashboard`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartcityparking`.`admindashboard`;
USE `smartcityparking`;
CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `smartcityparking`.`admindashboard` AS select `smartcityparking`.`parkingspot`.`ParkingLotID` AS `ParkingLotID`,count((case when (`smartcityparking`.`parkingspot`.`Status` = 'Occupied') then 1 end)) AS `OccupiedSpots`,count((case when (`smartcityparking`.`parkingspot`.`Status` = 'Available') then 1 end)) AS `AvailableSpots`,count((case when (`smartcityparking`.`parkingspot`.`Status` = 'Reserved') then 1 end)) AS `ReservedSpots` from `smartcityparking`.`parkingspot` group by `smartcityparking`.`parkingspot`.`ParkingLotID`;
USE `smartcityparking`;

DELIMITER $$
USE `smartcityparking`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `smartcityparking`.`PreventDoubleBooking`
BEFORE INSERT ON `smartcityparking`.`reservation`
FOR EACH ROW
BEGIN
    DECLARE spotStatus ENUM('Available', 'Occupied', 'Reserved');
    SELECT Status INTO spotStatus FROM ParkingSpot WHERE SpotID = NEW.SpotID;
    IF spotStatus <> 'Available' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Spot is already reserved or occupied';
    END IF;
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
