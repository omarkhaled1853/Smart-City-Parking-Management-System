import { useState, useEffect } from "react";
import axios from "axios";

export function ManagerPage() {
  const [parkingLots, setParkingLots] = useState<
    { id: number; name: string; location: string; spots: { id: number; type: string; status: string; price: number; duration: string }[] }[]
  >([]);
  const [reports, setReports] = useState("");
  const [editModal, setEditModal] = useState<{
    show: boolean;
    lotId: number | null;
    spot: { id: number; type: string; status: string; price: number; duration: string } | null;
  }>({ show: false, lotId: null, spot: null });

  // Fetch Parking Lots
  useEffect(() => {
    fetchParkingLots();
  }, []);

  const fetchParkingLots = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/manager/parkinglots");
      setParkingLots(response.data);
      console.log("Parking lots fetched successfully!");
    } catch (error) {
      console.error("Error fetching parking lots:", (error as Error).message);
    }
  };

  const handleEditSpot = async () => {
    if (!editModal.spot || !editModal.lotId) return;

    try {
      const { id, type, status, price, duration } = editModal.spot;
      await axios.put(`http://localhost:8080/api/manager/parkingspots/${id}`, {
        type,
        status,
        price,
        duration,
      });
      alert(`Spot #${id} updated successfully!`);
      setEditModal({ show: false, lotId: null, spot: null });
      fetchParkingLots();
      console.log("Spot updated successfully!");
    } catch (error) {
      console.error("Error updating spot:", (error as Error).message);
    }
  };

  const generateReport = async (type: string) => {
    try {
      const response = await axios.get(`http://localhost:8080/api/manager/reports/${type}`);
      setReports(response.data);
      console.log("Report generated successfully!");
    } catch (error) {
      console.error("Error generating report:", (error as Error).message);
    }
  };

  return (
    <div className="p-8 bg-gray-50 min-h-screen">
      <h1 className="text-3xl font-bold text-blue-700 mb-6">Manager Dashboard</h1>

      {parkingLots.map((lot) => (
        <section key={lot.id} className="mb-8 bg-white shadow-md rounded p-6">
          <h2 className="text-2xl font-semibold text-gray-800 mb-4">
            {lot.name} - {lot.location}
          </h2>
          <ul className="space-y-4">
            {lot.spots.map((spot) => (
              <li key={spot.id} className="border border-gray-300 rounded p-4 bg-gray-50 flex justify-between items-center">
                <div className="text-sm text-gray-700">
                  <span className="font-semibold">Spot #: </span>{spot.id} &nbsp;|&nbsp; 
                  <span className="font-semibold">Type: </span>{spot.type} &nbsp;|&nbsp; 
                  <span className="font-semibold">Status: </span>
                  <span
                    className={
                      spot.status === "available" ? "text-green-500 font-medium" : "text-red-500 font-medium"
                    }
                  >
                    {spot.status}
                  </span>
                  &nbsp;|&nbsp; 
                  <span className="font-semibold">Price: </span>${spot.price}/hour &nbsp;|&nbsp; 
                  <span className="font-semibold">Duration: </span>{spot.duration}
                </div>
                <button
                  onClick={() => setEditModal({ show: true, lotId: lot.id, spot })}
                  className="bg-blue-500 text-white py-1 px-3 rounded hover:bg-blue-600 text-sm"
                >
                  Edit
                </button>
              </li>
            ))}
          </ul>
        </section>
      ))}

      <section className="mb-8">
        <h2 className="text-xl font-bold text-gray-800 mb-4">Reports</h2>
        <button
          onClick={() => generateReport("parkinglots")}
          className="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600 mr-4"
        >
          Generate Parking Lots Report
        </button>
        <button
          onClick={() => generateReport("parkingspots")}
          className="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600"
        >
          Generate Parking Spots Report
        </button>
        <div className="mt-4 bg-gray-100 p-4 rounded shadow-inner">
          <pre>{reports}</pre>
        </div>
      </section>

      {editModal.show && editModal.spot && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50">
          <div className="bg-white rounded p-6 shadow-lg w-96">
            <h2 className="text-xl font-bold mb-4">Edit Spot #{editModal.spot.id}</h2>
            <form
              onSubmit={(e) => {
                e.preventDefault();
                handleEditSpot();
              }}
            >
              <div className="mb-4">
                <label className="block font-medium mb-1">Type</label>
                <select
                  value={editModal.spot.type}
                  onChange={(e) =>
                    setEditModal({ ...editModal, spot: { ...editModal.spot!, type: e.target.value } })
                  }
                  className="w-full border border-gray-300 rounded p-2"
                >
                  <option value="Compact">Compact</option>
                  <option value="Regular">Regular</option>
                  <option value="Handicapped">Handicapped</option>
                  <option value="EV Charging">EV Charging</option>
                </select>
              </div>
              <div className="mb-4">
                <label className="block font-medium mb-1">Status</label>
                <select
                  value={editModal.spot.status}
                  onChange={(e) =>
                    setEditModal({ ...editModal, spot: { ...editModal.spot!, status: e.target.value } })
                  }
                  className="w-full border border-gray-300 rounded p-2"
                >
                  <option value="available">Available</option>
                  <option value="occupied">Occupied</option>
                </select>
              </div>
              <div className="mb-4">
                <label className="block font-medium mb-1">Price ($/hour)</label>
                <input
                  type="number"
                  value={editModal.spot.price}
                  onChange={(e) =>
                    setEditModal({ ...editModal, spot: { ...editModal.spot!, price: parseFloat(e.target.value) } })
                  }
                  className="w-full border border-gray-300 rounded p-2"
                />
              </div>
              <div className="mb-4">
                <label className="block font-medium mb-1">Duration</label>
                <input
                  type="text"
                  value={editModal.spot.duration}
                  onChange={(e) =>
                    setEditModal({ ...editModal, spot: { ...editModal.spot!, duration: e.target.value } })
                  }
                  className="w-full border border-gray-300 rounded p-2"
                />
              </div>
              <div className="flex justify-end">
                <button
                  type="button"
                  onClick={() => setEditModal({ show: false, lotId: null, spot: null })}
                  className="bg-gray-300 text-gray-700 py-1 px-3 rounded mr-2"
                >
                  Cancel
                </button>
                <button type="submit" className="bg-blue-500 text-white py-1 px-3 rounded">
                  Save
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}
