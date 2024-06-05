import React, { useEffect, useState } from "react";
import axios from "axios";

function AllPatients() {
  const [patients, setPatients] = useState([]);

  useEffect(() => {
    axios
      .get("http://localhost:8080")
      .then((response) => setPatients(response.data))
      .catch((error) => console.error("Error fetching patients:", error));
  }, []);

  return (
    <div>
      <h1>All Patients</h1>
      <ul>
        {patients.map((patient) => (
          <li key={patient.id}>
            <a href={`/patient/${patient.id}`}>{patient.name}</a>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default AllPatients;
