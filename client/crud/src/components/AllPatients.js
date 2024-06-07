import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function AllPatients() {
  const [patients, setPatients] = useState([]);
  const [showConfirm, setShowConfirm] = useState(false);
  const [selectedPatientId, setSelectedPatientId] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    axios
      .get("http://localhost:8080/patients")
      .then((response) => setPatients(response.data.content))
      .catch((error) => console.error("Error fetching patients:", error));
  }, []);

  const handleEdit = (patientId) => {
    navigate(`/edit/${patientId}`);
  };

  const handleDeleteClick = (patientId) => {
    setSelectedPatientId(patientId);
    setShowConfirm(true);
  };

  const handleConfirmDelete = () => {
    axios
      .delete(`http://localhost:8080/patients/${selectedPatientId}`)
      .then(() => {
        setPatients((prevPatients) =>
          prevPatients.filter(
            (patient) => patient.patientId !== selectedPatientId
          )
        );
        setShowConfirm(false);
        setSelectedPatientId(null);
      })
      .catch((error) => {
        console.error("Error deleting patient:", error);
        setShowConfirm(false);
        setSelectedPatientId(null);
      });
  };

  const handleCancelDelete = () => {
    setShowConfirm(false);
    setSelectedPatientId(null);
  };

  const handleCreate = () => {
    navigate(`/create`);
  };

  return (
    <div>
      <h1>All Patients</h1>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Gender</th>
            <th>Age</th>
            <th>Email</th>
            <th>Phone Number</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {patients.map((patient) => (
            <tr key={patient.patientId}>
              <td>{patient.patientId}</td>
              <td>{patient.name}</td>
              <td>{patient.gender}</td>
              <td>{patient.age}</td>
              <td>{patient.email}</td>
              <td>{patient.phoneNumber}</td>
              <td>
                <button onClick={() => handleEdit(patient.patientId)}>
                  Edit
                </button>
                <button onClick={() => handleDeleteClick(patient.patientId)}>
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <button onClick={handleCreate}>Create Patient</button>

      {showConfirm && (
        <div className="confirmation-dialog">
          <div className="dialog-content">
            <p>Are you sure you want to delete this patient?</p>
            <button onClick={handleConfirmDelete}>Confirm</button>
            <button onClick={handleCancelDelete}>Cancel</button>
          </div>
        </div>
      )}
    </div>
  );
}

export default AllPatients;
