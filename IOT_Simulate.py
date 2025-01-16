import random
import time
import mysql.connector
import logging

# Configure logging
logging.basicConfig(filename="sensor_updates.log", level=logging.INFO, format="%(asctime)s - %(message)s")

# Database connection
db = mysql.connector.connect(
    host="localhost",
    user="SmartCityParking",
    password="SmartCityParking",
    database="smartcityparking"
)
cursor = db.cursor()

# Simulate IoT sensor updates
def update_spot_status():
    while True:
        try:
            # Fetch all parking spots
            cursor.execute("SELECT SpotID FROM parkingspot")
            spots = cursor.fetchall()

            # Randomly update status for a spot
            for spot in spots:
                spot_id = spot[0]
                new_status = random.choice(['AVAILABLE', 'OCCUPIED'])
                cursor.execute(
                    "UPDATE parkingspot SET Status = %s WHERE SpotID = %s",
                    (new_status, spot_id)
                )
                db.commit()

            logging.info("Parking spot statuses updated.")
            time.sleep(10)  # Run every 10 seconds

        except Exception as e:
            logging.error(f"Error updating spot statuses: {e}")

if __name__ == "__main__":
    update_spot_status()
