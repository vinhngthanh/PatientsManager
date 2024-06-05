import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function AllPatients() {
  const [patients, setPatients] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    axios
      .get("http://localhost:8080")
      .then((response) => setPatients(response.data))
      .catch((error) => console.error("Error fetching patients:", error));
  }, []);

  const handleEdit = (patientId) => {
    navigate(`/edit/${patientId}`);
  };

  const handleDelete = (patientId) => {
    navigate(`/delete/${patientId}`);
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
                <button onClick={() => handleDelete(patient.patientId)}>
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <button onClick={() => handleCreate()}>Create Patient</button>
    </div>
  );
}

export default AllPatients;
