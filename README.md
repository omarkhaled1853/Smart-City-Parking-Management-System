# Smart City Parking Management System

## Overview

The **Smart City Parking Management System** is designed to optimize parking in urban areas by leveraging IoT technologies and centralized database connectivity. This system connects parking lots, parking meters, and users (drivers) in real-time to improve parking availability, reservations, pricing, and traffic flow. It aims to enhance smart city applications with seamless parking operations.

## Features

### General Features:

- **Real-Time Updates:** Monitor and report parking spot status (Available, Occupied, Reserved) in real-time.
- **Centralized Database:** Manages all parking-related data and interactions efficiently.
- **Role-Based Functionality:** Supports three user roles: **Driver**, **Manager**, and **Admin**.

### Driver:

- **Sign Up / Log In:** Drivers can create an account or log in to manage their reservations.
- **Search for Parking Lots:** Search for available parking lots and view parking spot statuses (Available, Occupied, Reserved).
- **Reserve Spots:** Choose an available spot in a selected parking lot, complete the reservation form, and make payments.
- **Manage Reservations:** View and filter reservations by status (Active, Completed, Cancelled) and location.
- **Profile Page:** Manage personal information and reservation history.
- **Notifications:** Receive updates about reservation status.

### Manager:

- **Log In:** Managers can log in to access their assigned lots.
- **Manage Lots:** Update parking spot statuses (Available, Occupied, Reserved).
- **Generate Reports:** Create detailed reports about parking spot usage and send them to the Admin.
- **Notifications:** Receive notifications about new reservations in assigned lots.

### Admin:

- **Log In:** Admins can log in to perform administrative tasks.
- **Manage Parking Lots:** Add or remove parking lots and update the number of spots in existing lots.
- **Assign Managers:** Allocate specific parking lots to managers.
- **Generate Reports:** Create and share comprehensive parking data reports with managers.

## Technical Details

- **Database:** Centralized database for storing parking lot, user, and reservation data.
- **Real-Time Monitoring:** IoT-based integration for real-time spot monitoring.
- **Notification System:** Alerts for reservation updates and management actions.
- **Search and Filter:** Advanced search functionality for drivers to locate and reserve parking spots efficiently.

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/omarkhaled1853/Smart-City-Parking-Management-System
   ```
2. Navigate to the project directory:
   ```bash
   cd Parking System
   ```
3. Set up the database:
   - Create a schema named `smartcityparking`.
   - Use the username `SmartCityParking` and password `SmartCityParking` for database access.
   - Run the provided SQL setup files included in the repository to create tables and seed initial data.
4. Install dependencies:
   ```bash
   npm install
   ```
   *(or the relevant package manager for your project)*
5. Start the application:
   ```bash
   npm start
   ```

## Usage

1. **Driver:**

   - Sign up or log in.
   - Search for parking lots, reserve spots, and manage reservations.

2. **Manager:**

   - Log in to access assigned lots.
   - Update spot statuses and manage lots.
   - Generate and share reports with the Admin.

3. **Admin:**

   - Log in to add or remove lots and assign managers.
   - Generate comprehensive system reports.

## License

This project is licensed under the [MIT License](LICENSE).

