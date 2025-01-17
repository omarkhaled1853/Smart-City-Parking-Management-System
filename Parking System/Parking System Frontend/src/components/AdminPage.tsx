import { useState, useEffect } from "react";
import axios from "axios";


export function AdminPage() {
  
  const [priceAdjusted, setPriceAdjusted] = useState<Record<number, boolean>>({});
  const [parkingLots, setParkingLots] = useState<{
    parkingLotID: number;
    UserID:number;
    name: string;
    location: string;
    capacity: number;
    pricingModel: string;
    spots?: {
      spotID: number;
      spotType: string;
      status: string;
      pricePerHour: number;
      duration?: string;
    }[]; 
  }[]>([]);
  const [reports, setReports] = useState("");
  const [editModal, setEditModal] = useState<{
    show: boolean;
    lotId: number | null;
    spot: {
      spotID: number;
      spotType: string;
      status: string;
      pricePerHour: number;
      duration: string;
    } | null;
    editType: "status" | "pricing" | null;  // new field for tracking the edit type
  }>({ show: false, lotId: null, spot: null, editType: null });
  const [users, setUsers] = useState<{ UserID: string; name: string }[]>([]); // New state for users (managers)


  const [newLot, setNewLot] = useState({
    name: "",
    UserID : "",
    location: "",
    capacity: 0,
    pricingModel: "",
  });

  // New state for adding a new parking spot
  const [newSpot, setNewSpot] = useState({
    parkingLotID: 0,
    spotType: "",
    status: "AVAILABLE",
    pricePerHour: 0,
  });

  // New states to manage the visibility of the add forms
  const [showAddLotForm, setShowAddLotForm] = useState(false);
  const [showAddSpotForm, setShowAddSpotForm] = useState(false);



 



  // Fetch Parking Lots and their spots
  useEffect(() => {
    fetchUsers();
    fetchParkingLots();
    // const interval = setInterval(() => {
    //   fetchParkingLots();
    // }, 10000);

    // return () => clearInterval(interval);
  }, []);

  useEffect(() => {
    console.log("Updated users state:", users); // Log updated state when it changes
  }, [users]);
  

  const sendAlertToManager = async (message:String , userId: number) => {
    console.log(userId)
    try {
      const response = await axios.post('http://localhost:8080/Admin/Alerts', null, {
        params: {
          message: message,
          userId: userId,
        },
      });
  
      console.log('Notification sent successfully', response);
    } catch (error) {
      console.error('Error sending notification:', error);
    }
  };


  const fetchUsers = async () => {
    try {
      const response = await axios.get("http://localhost:8080/Admin/users"); // Fetch users
      const mappedUsers = response.data.map((user: any) => ({
        UserID: user.UserID,
        name: user.Name, // Ensure this matches the API response field
      }));
      setUsers(mappedUsers); // Set the corrected users data
      console.log("Mapped users:", mappedUsers);
    } catch (error) {
      console.error("Error fetching users:", error);
    }
  };
  


  const fetchParkingLots = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/manager/parkinglots");
      const lots = response.data;
      setParkingLots(lots);

      // For each parking lot, fetch the parking spots
      lots.forEach(async (lot: { parkingLotID: number }) => {
        await fetchParkingSpots(lot.parkingLotID);
      });
      console.log(" fetched parking lots:");

    } catch (error) {
      console.error("Error fetching parking lots:", (error as Error).message);
    }
  };
  const adjustPriceIfNeeded = (lotId: number, spots: any[]) => {
    const totalSpots = spots.length;
    const reservedOrOccupied = spots.filter(
      (spot) => spot.status === "RESERVED" || spot.status === "OCCUPIED"
    ).length;
  
    const percentageOccupied = (reservedOrOccupied / totalSpots) * 100;
    const pricingModel = parkingLots.find((lot) => lot.parkingLotID === lotId)?.pricingModel;
  
    // Check if the parking lot is completely occupied
    if (percentageOccupied === 100 && !priceAdjusted[lotId]) {
      const lot = parkingLots.find(lot => lot.parkingLotID === lotId);
      if (lot) {
        sendAlertToManager(`Parking lot "${lot.name}" is completely occupied.`, lot.UserID); // Send alert to the manager
      }
      setPriceAdjusted((prev) => ({ ...prev, [lotId]: true }));
    }
  
    // Adjust prices if needed based on occupancy
    if (pricingModel === "DYNAMIC") {
    if (percentageOccupied > 50 && !priceAdjusted[lotId]) {
      spots.forEach((spot) => {
        if (spot.status === "AVAILABLE" || spot.status === "RESERVED") {
          spot.pricePerHour = spot.pricePerHour * 1.1; // Increase price by 10%
        }
      });
      setPriceAdjusted((prev) => ({ ...prev, [lotId]: true }));
    } else if (percentageOccupied < 50 && priceAdjusted[lotId]) {
      spots.forEach((spot) => {
        if (spot.status === "AVAILABLE" || spot.status === "RESERVED") {
          spot.pricePerHour = spot.pricePerHour * 0.9; // Decrease price by 10%
        }
      });
      setPriceAdjusted((prev) => ({ ...prev, [lotId]: false }));
    }
  }
  else if (pricingModel === "STATIC") {
    // No price adjustment needed for fixed pricing
    console.log(`Lot ${lotId} has fixed pricing. No adjustments made.`);
  }
  
  };
  const fetchParkingSpots = async (lotId: number) => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/manager/parkingspots?lotId=${lotId}`
      );
      const spots = response.data;
  
      // Adjust price based on occupancy percentage
      adjustPriceIfNeeded(lotId, spots);
  
      // Update parking lots state
      setParkingLots((prevLots) =>
        prevLots.map((lot) =>
          lot.parkingLotID === lotId ? { ...lot, spots } : lot
        )
      );
    } catch (error) {
      console.error(`Error fetching parking spots for lot ${lotId}:`, error);
    }
  };
    
 
  const handleEditStatus = async () => {
    if (!editModal.spot || !editModal.lotId) return;

    try {
      const { spotID, status } = editModal.spot;

      // Update the parking spot status by sending it as a query parameter
      await axios.put(`http://localhost:8080/api/manager/parkingspots/${spotID}/status?status=${status}`);

      alert(`Spot #${spotID} status updated successfully!`);
      setEditModal({ show: false, lotId: null, spot: null, editType: null });
      fetchParkingLots(); // Refresh parking lots and spots
    } catch (error) {
      console.error("Error updating spot status:", (error as Error).message);
    }
    fetchParkingLots();
  };

  const handleEditPricing = async () => {
    if (!editModal.spot || !editModal.lotId) return;

    try {
      const { spotID, pricePerHour } = editModal.spot;

      // Update the spot pricing by sending the demand factor (price per hour)
      await axios.put(`http://localhost:8080/api/manager/parkingspots/${spotID}/pricing?demandFactor=${pricePerHour}`);

      alert(`Spot #${spotID} pricing updated successfully!`);
      setEditModal({ show: false, lotId: null, spot: null, editType: null });
      fetchParkingLots(); // Refresh parking lots and spots
    } catch (error) {
      console.error("Error updating spot pricing:", (error as Error).message);
    }
  };

  const generateReport = async (type: string) => {
    try {
      const response = await axios.get(`http://localhost:8080/Admin/reports/${type}`);
      setReports(response.data);
      console.log("Report generated successfully!");
    } catch (error) {
      console.error("Error generating report:", (error as Error).message);
    }
  };

  const GetReport = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/Admin/reports/analysis`);
      setReports(response.data);
      console.log("Report is displayed successfully!");
    } catch (error) {
      console.error("Error showing report:", (error as Error).message);
    }
  };

  const deleteParkingLot = async (lotId: number) => {
    try {
      await axios.delete(`http://localhost:8080/api/manager/parkinglots/${lotId}`);
      alert(`Parking Lot #${lotId} deleted successfully!`);
      console.log("Parking lot deleted successfully!");
      fetchParkingLots(); // Refresh the parking lots after deletion
    } catch (error) {
      console.error("Error deleting parking lot:", (error as Error).message);
    }
  };

  const deleteParkingSpot = async (spotId: number) => {
    try {
      await axios.delete(`http://localhost:8080/api/manager/parkingspots/${spotId}`);
      alert(`Parking Spot #${spotId} deleted successfully!`);
      fetchParkingLots(); // Refresh the parking spots after deletion
    } catch (error) {
      console.error("Error deleting parking spot:", (error as Error).message);
    }
  };

  const addParkingLot = async () => {
    try {
      await axios.post("http://localhost:8080/api/manager/parkinglots", newLot);
      alert("Parking Lot added successfully!");
      setNewLot({ name: "",UserID: "", location: "", capacity: 0, pricingModel: "" }); // Reset form
      console.log(newLot);
      setShowAddLotForm(false); // Close the form
      fetchParkingLots();
      } catch (error) {
      console.error("Error adding parking lot:", (error as Error).message);
    }
  };

  const addParkingSpot = async () => {
    try {
      console.log(newSpot);
      await axios.post("http://localhost:8080/api/manager/parkingspots", newSpot);
      alert("Parking Spot added successfully!");
      setNewSpot({ parkingLotID: 0, spotType: "", status: "AVAILABLE", pricePerHour: 0}); // Reset form
      setShowAddSpotForm(false); // Close the form
      fetchParkingLots();
      } catch (error) {
      console.error("Error adding parking spot:", (error as Error).message);
    }
  };
  

  return (
    <div className="p-8 bg-gray-50 min-h-screen">
      <h1 className="text-3xl font-bold text-blue-700 mb-6">Admin Dashboard</h1>

      {parkingLots.map((lot) => (
        <section key={lot.parkingLotID} className="mb-8 bg-white shadow-md rounded p-6">
          <h2 className="text-2xl font-semibold text-gray-800 mb-4">
            {lot.name} - {lot.location} (Capacity: {lot.capacity}, Pricing: {lot.pricingModel})
            <button
              onClick={() => deleteParkingLot(lot.parkingLotID)}
              className="bg-red-500 text-white py-1 px-3 rounded hover:bg-red-600 text-sm ml-4"
            >
              Delete
            </button>
          </h2>

          {lot.spots && lot.spots.length > 0 ? (
            <ul className="space-y-4">
              {lot.spots.map((spot) => (
                <li key={spot.spotID} className="border border-gray-300 rounded p-4 bg-gray-50 flex justify-between items-center">
                  <div className="text-sm text-gray-700">
                    <span className="font-semibold">Spot #: </span>{spot.spotID} &nbsp;|&nbsp;
                    <span className="font-semibold">Type: </span>{spot.spotType} &nbsp;|&nbsp;
                    <span className="font-semibold">Status: </span>
                    <span
                      className={
                        spot.status === "AVAILABLE"
                          ? "text-green-500 font-medium"
                          : spot.status === "OCCUPIED"
                          ? "text-red-500 font-medium"
                          : "text-yellow-500 font-medium"
                      }
                    >
                      {spot.status}
                    </span>
                    &nbsp;|&nbsp;
                    <span className="font-semibold">Price: </span>${spot.pricePerHour}/hour &nbsp;|&nbsp;
                    <span className="font-semibold">Duration: </span>{spot.duration || "N/A"}
                  </div>
                  <div className="flex space-x-2">
                    <button
                      onClick={() => setEditModal({ show: true, lotId: lot.parkingLotID, spot: { ...spot, duration: spot.duration || "" }, editType: "status" })}
                      className="bg-blue-500 text-white py-1 px-3 rounded hover:bg-blue-600 text-sm"
                    >
                      Edit Status
                    </button>
                    <button
                      onClick={() => setEditModal({ show: true, lotId: lot.parkingLotID, spot: { ...spot, duration: spot.duration || "" }, editType: "pricing" })}
                      className="bg-yellow-500 text-white py-1 px-3 rounded hover:bg-yellow-600 text-sm"
                    >
                      Edit Pricing
                    </button>
                    <button
                      onClick={() => deleteParkingSpot(spot.spotID)}
                      className="bg-red-500 text-white py-1 px-3 rounded hover:bg-red-600 text-sm"
                    >
                      Delete
                    </button>
                  </div>
                </li>
              ))}
            </ul>
          ) : (
            <p>No parking spots available</p>
          )}
          {/* Button to add new parking spot */}
          <button
            onClick={() => {
              setNewSpot({ ...newSpot, parkingLotID: lot.parkingLotID }); // Set lotId for the new spot
              setShowAddSpotForm(true);
            }}
            className="mt-4 text-blue-500 hover:underline"
          >
            Add Parking Spot
          </button>
        
        </section>

      ))}
      {/* Button to add new parking lot */}
      <button
        onClick={() => setShowAddLotForm(true)}
        className="text-blue-500 hover:underline"
      >
        Add New Parking Lot
      </button>


<section className="mb-8">
        <h2 className="text-xl font-bold text-gray-800 mb-4">Reports</h2>
        
        <button
          onClick={() => generateReport("parkingspots")}
          className="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600 mr-4"
        >
          Generate Parking analysis Report
        </button>

        <button
          onClick={() => GetReport()}
          className="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600 mr-4"
        >
          View Analysis
        </button>
        <div className="mt-4 bg-gray-100 p-4 rounded shadow-inner">
          <pre>{reports}</pre>
        </div>
      </section>
      

      

      {editModal.show && editModal.spot && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50">
          <div className="bg-white rounded p-6 shadow-lg w-96">
            <h2 className="text-xl font-bold mb-4">Edit Spot #{editModal.spot.spotID}</h2>
            <form
              onSubmit={(e) => {
                e.preventDefault();
              }}
            >
              {editModal.editType === "status" && (
                <div className="mb-4">
                  <label className="block font-medium mb-1">Status</label>
                  <select
                    value={editModal.spot.status}
                    onChange={(e) =>
                      setEditModal({ ...editModal, spot: { ...editModal.spot!, status: e.target.value } })
                    }
                    className="w-full border border-gray-300 rounded p-2"
                  >
                    <option value="AVAILABLE">Available</option>
                    <option value="OCCUPIED">Occupied</option>
                    <option value="RESERVED">Reserved</option>
                  </select>
                </div>
              )}
              {editModal.editType === "pricing" && (
                <div className="mb-4">
                  <label className="block font-medium mb-1">Price</label>
                  <input
                    type="number"
                    value={editModal.spot.pricePerHour}
                    onChange={(e) =>
                      setEditModal({ ...editModal, spot: { ...editModal.spot!, pricePerHour: parseFloat(e.target.value) } })
                    }
                    className="w-full border border-gray-300 rounded p-2"
                  />
                </div>
              )}
              <div className="flex justify-end space-x-2">
                <button
                  type="button"
                  onClick={() => setEditModal({ show: false, lotId: null, spot: null, editType: null })}
                  className="bg-gray-500 text-white py-1 px-3 rounded hover:bg-gray-600"
                >
                  Cancel
                </button>
                <button
                  onClick={editModal.editType === "status" ? handleEditStatus : handleEditPricing}
                  className="bg-blue-500 text-white py-1 px-3 rounded hover:bg-blue-600"
                >
                  Save Changes
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
       {/* Form for adding a new parking lot */}
       {showAddLotForm && (
        <div className="fixed inset-0 bg-gray-700 bg-opacity-50 flex justify-center items-center">
          <div className="bg-white p-6 rounded shadow-lg w-96">
            <h3 className="text-xl font-semibold mb-4">Add New Parking Lot</h3>
            <div className="space-y-4">
              <input
                type="text"
                className="border p-2 w-full"
                placeholder="Lot Name"
                value={newLot.name}
                onChange={(e) => setNewLot({ ...newLot, name: e.target.value })}
              />
              <input
                type="text"
                className="border p-2 w-full"
                placeholder="Location"
                value={newLot.location}
                onChange={(e) => setNewLot({ ...newLot, location: e.target.value })}
              />
              <input
                type="number"
                className="border p-2 w-full"
                placeholder="Capacity"
                value={newLot.capacity}
                onChange={(e) => setNewLot({ ...newLot, capacity: +e.target.value })}
              />
              <select
                value={newLot.pricingModel}
                onChange={(e) => setNewLot({ ...newLot, pricingModel: e.target.value })}
                className="border p-2 w-full"
              >
                <option value="">Select Pricing Model</option>
                <option value="STATIC">Static</option>
                <option value="DYNAMIC">Dynamic</option>
              </select>

              {/* Dropdown for selecting manager (UserID) */}
              <select
                value={newLot.UserID}
                onChange={(e) => setNewLot({ ...newLot, UserID: e.target.value })}
                className="border p-2 w-full"
              >
                <option value="">Select Manager</option>
                {users.map((user) => (
                  <option key={user.UserID} value={user.UserID}>
                    {user.name}
                  </option>
                ))}
              </select>
            </div>
            <div className="mt-4 flex justify-between">
              <button
                onClick={() => setShowAddLotForm(false)}
                className="bg-gray-500 text-white py-2 px-4 rounded hover:bg-gray-600"
              >
                Cancel
              </button>
              <button
                onClick={addParkingLot}
                className="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600"
              >
                Save
              </button>
            </div>
          </div>
        </div>
      )}


      {/* Modal for Adding a New Parking Spot */}
      {showAddSpotForm && (
        <div className="fixed inset-0 bg-gray-700 bg-opacity-50 flex justify-center items-center">
          <div className="bg-white p-6 rounded shadow-lg w-96">
            <h3 className="text-xl font-semibold mb-4">Add New Parking Spot</h3>
            <div className="space-y-4">
              {/* Spot Type Selector */}
              <select
                value={newSpot.spotType}
                onChange={(e) => setNewSpot({ ...newSpot, spotType: e.target.value })}
                className="border p-2 w-full"
              >
                <option value="">Select Spot Type</option>
                <option value="REGULAR">Regular</option>
                <option value="DISABLED">Disabled</option>
                <option value="EVCHARGING">EVCharging</option>
              </select>

              {/* Status Selector */}
              <select
                value={newSpot.status}
                onChange={(e) => setNewSpot({ ...newSpot, status: e.target.value })}
                className="border p-2 w-full"
              >
                <option value="AVAILABLE">Available</option>
                <option value="OCCUPIED">Occupied</option>
                <option value="RESERVED">Reserved</option>
              </select>

              {/* Price Per Hour */}
              <input
                type="number"
                className="border p-2 w-full"
                placeholder="Price per Hour"
                value={newSpot.pricePerHour}
                onChange={(e) => setNewSpot({ ...newSpot, pricePerHour: +e.target.value })}
              />

              {/* Duration (optional)
              <input
                type="text"
                className="border p-2 w-full"
                placeholder="Duration (optional)"
                value={newSpot.duration}
                onChange={(e) => setNewSpot({ ...newSpot, duration: e.target.value })}
              /> */}
            </div>

            <div className="mt-4 flex justify-between">
              <button
                onClick={() => setShowAddSpotForm(false)}
                className="bg-gray-500 text-white py-2 px-4 rounded hover:bg-gray-600"
              >
                Cancel
              </button>
              <button
                onClick={addParkingSpot}
                className="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600"
              >
                Save
              </button>
            </div>
          </div>
        </div>
      )}
      

    </div>
  );
}