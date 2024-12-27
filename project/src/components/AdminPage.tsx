import { useState, useEffect } from "react";
import axios from "axios";


export function AdminPage() {
  const [priceAdjusted, setPriceAdjusted] = useState<Record<number, boolean>>({});
  const [parkingLots, setParkingLots] = useState<{
    parkingLotID: number;
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



 



  // Fetch Parking Lots and their spots
  useEffect(() => {
    fetchParkingLots();
    // const interval = setInterval(() => {
    //   fetchParkingLots();
    // }, 10000);

    // return () => clearInterval(interval);
  }, []);

  

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
      (spot) => spot.status === "Reserved" || spot.status === "Occupied"
    ).length;
  
    const percentageOccupied = (reservedOrOccupied / totalSpots) * 100;
  
    if (percentageOccupied > 50 && !priceAdjusted[lotId]) {
      // Increase price by 10% once
      spots.forEach((spot) => {
        if (spot.status === "Available" || spot.status === "Reserved") {
          spot.pricePerHour = spot.pricePerHour * 1.1;
        }
      });
      setPriceAdjusted((prev) => ({ ...prev, [lotId]: true }));
    } else if (percentageOccupied < 50 && priceAdjusted[lotId]) {
      // Decrease price by 10% once
      spots.forEach((spot) => {
        if (spot.status === "Available" || spot.status === "Reserved") {
          spot.pricePerHour = spot.pricePerHour * 0.9;
        }
      });
      setPriceAdjusted((prev) => ({ ...prev, [lotId]: false }));
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

  const GetReport = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/api/Admin/reports/analysis`);
      setReports(response.data);
      console.log("OK");
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

//   const addParkingLot = async () => {
//     try {
//       await axios.post("http://localhost:8080/api/manager/parkinglots", newLot);
//       alert("Parking Lot added successfully!");
//       setNewLot({ name: "", location: "", capacity: 0, pricingModel: "" }); // Reset form
//       setShowAddLotForm(false); // Close the form
//       fetchParkingLots(); // Refresh the parking lots
//     } catch (error) {
//       console.error("Error adding parking lot:", (error as Error).message);
//     }
//   };

//   const addParkingSpot = async () => {
//     try {
//       console.log(newSpot);
//       await axios.post("http://localhost:8080/api/manager/parkingspots", newSpot);
//       alert("Parking Spot added successfully!");
//       setNewSpot({ parkingLotID: 0, spotType: "", status: "Available", pricePerHour: 0}); // Reset form
//       setShowAddSpotForm(false); // Close the form
//       fetchParkingLots(); // Refresh the parking spots
//     } catch (error) {
//       console.error("Error adding parking spot:", (error as Error).message);
//     }
//   };

  

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
                        spot.status === "Available"
                          ? "text-green-500 font-medium"
                          : spot.status === "Occupied"
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
        
        </section>
      ))}
      
      <section className="mb-8">
        <h2 className="text-xl font-bold text-gray-800 mb-4">Reports</h2>
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
                    <option value="Available">Available</option>
                    <option value="Occupied">Occupied</option>
                    <option value="Reserved">Reserved</option>
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
      

    </div>
  );
}