import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axiosInstance from "../instances/AxiosInstance";
import axios from "axios";
import "../css/AllPatients.css";

function AllPatients() {
  const [patients, setPatients] = useState([]);
  const [showConfirm, setShowConfirm] = useState(false);
  const [selectedPatientId, setSelectedPatientId] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [pageSize, setPageSize] = useState(20);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [isAdmin, setIsAdmin] = useState(false);
  const [criteria, setCriteria] = useState({
    patientId: "",
    name: "",
    gender: "",
    age: "",
    email: "",
    phoneNumber: "",
  });
  const [filterCriteria, setFilterCriteria] = useState({
    patientId: "",
    name: "",
    gender: "",
    age: "",
    email: "",
    phoneNumber: "",
  });
  const navigate = useNavigate();

  const fetchPatients = (page, size, filterCriteria) => {
    const params = new URLSearchParams();
    for (const key in filterCriteria) {
      if (filterCriteria[key]) {
        params.append(key, filterCriteria[key]);
      }
    }
    params.append("page", page - 1);
    params.append("size", size);

    axiosInstance
      .get(`http://localhost:8080/patients?${params.toString()}`)
      .then((response) => {
        setPatients(response.data.content);
        setTotalPages(response.data.totalPages);
      })
      .catch((error) => console.error("Error fetching patients:", error));
  };

  useEffect(() => {
    fetchPatients(currentPage, pageSize, filterCriteria);
  }, [currentPage, pageSize, filterCriteria]);

  useEffect(() => {
    const token = localStorage.getItem("jwtToken");
    setIsLoggedIn(!!token);
    const role = localStorage.getItem("role");
    if (role === "ADMIN") {
      setIsAdmin(true);
    } else {
      setIsAdmin(false);
    }
  }, []);

  const handleEdit = (patientId) => {
    navigate(`/edit/${patientId}`);
  };

  const handleDeleteClick = (patientId) => {
    setSelectedPatientId(patientId);
    setShowConfirm(true);
  };

  const handleConfirmDelete = () => {
    axiosInstance
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

  const handleFilter = () => {
    setCurrentPage(1);
    setFilterCriteria(criteria);
  };

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setCriteria((prevCriteria) => ({
      ...prevCriteria,
      [name]: value,
    }));
  };

  const handleCancelDelete = () => {
    setShowConfirm(false);
    setSelectedPatientId(null);
  };

  const handleCreate = () => {
    navigate(`/create`);
  };

  const handlePageClick = (page) => {
    setCurrentPage(page);
  };

  const handleLogin = () => {
    navigate("/signin");
  };
  const handleLogout = () => {
    axios
      .post(`http://localhost:8080/auth/logout`)
      .then(() => {
        localStorage.removeItem("jwtToken");
        setIsAdmin(false);
        setIsLoggedIn(false);
        window.location.reload();
      })
      .catch((error) => {
        console.error("Error logging out:", error);
      });
  };
  const handleNewUser = () => {
    navigate("/signup");
  };

  const renderPageButtons = () => {
    const pageButtons = [];
    const maxPageToShow = Math.min(totalPages, currentPage + 4);
    const startPage = Math.max(1, maxPageToShow - 4);

    for (let i = startPage; i <= maxPageToShow; i++) {
      pageButtons.push(
        <button
          key={i}
          onClick={() => handlePageClick(i)}
          disabled={currentPage === i}
        >
          {i}
        </button>
      );
    }
    return pageButtons;
  };

  const handlePageSizeChange = (event) => {
    setPageSize(Number(event.target.value));
  };

  return (
    <div className="page-container">
      <h1>All Patients</h1>
      <div>
        {!isLoggedIn && (
          <>
            <button onClick={() => handleLogin()}>Login</button>
          </>
        )}
        {isLoggedIn && (
          <>
            <button onClick={() => handleLogout()}>Logout</button>
          </>
        )}
        {!isLoggedIn && (
          <>
            <button onClick={() => handleNewUser()}>New User</button>
          </>
        )}
      </div>
      <div className="filter-container">
        <input
          placeholder="ID"
          type="text"
          name="patientId"
          value={criteria.patientId}
          onChange={handleInputChange}
        />
        <input
          placeholder="Name"
          type="text"
          name="name"
          value={criteria.name}
          onChange={handleInputChange}
        />
        <input
          placeholder="Gender"
          type="text"
          name="gender"
          value={criteria.gender}
          onChange={handleInputChange}
        />
        <input
          placeholder="Age"
          type="text"
          name="age"
          value={criteria.age}
          onChange={handleInputChange}
        />
        <input
          placeholder="Email"
          type="email"
          name="email"
          value={criteria.email}
          onChange={handleInputChange}
        />
        <input
          placeholder="Phone Number"
          type="text"
          name="phoneNumber"
          value={criteria.phoneNumber}
          onChange={handleInputChange}
        />
        <button onClick={handleFilter}>Filter</button>
      </div>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Gender</th>
            <th>Age</th>
            <th>Email</th>
            <th>Phone Number</th>
            {isAdmin && (
              <>
                <th>Actions</th>
              </>
            )}
          </tr>
        </thead>
        <tbody>
          {isLoggedIn &&
            patients.map((patient) => (
              <tr key={patient.patientId}>
                <td>{patient.patientId}</td>
                <td>{patient.name}</td>
                <td>{patient.gender}</td>
                <td>{patient.age}</td>
                <td>{patient.email}</td>
                <td>{patient.phoneNumber}</td>
                {isAdmin && (
                  <>
                    <td>
                      <button onClick={() => handleEdit(patient.patientId)}>
                        Edit
                      </button>
                      <button
                        onClick={() => handleDeleteClick(patient.patientId)}
                      >
                        Delete
                      </button>
                    </td>
                  </>
                )}
              </tr>
            ))}
        </tbody>
      </table>
      {isLoggedIn && isAdmin && (
        <>
          <button onClick={handleCreate}>Create Patient</button>
        </>
      )}
      <div className="navigator">
        <button
          onClick={() => setCurrentPage(currentPage - 1)}
          disabled={currentPage === 1}
        >
          {"<"}
        </button>
        {renderPageButtons()}
        <button
          onClick={() => setCurrentPage(currentPage + 1)}
          disabled={currentPage === totalPages}
        >
          {">"}
        </button>

        <select
          id="sizeSelect"
          name="sizeSelect"
          onChange={handlePageSizeChange}
          defaultValue="20"
        >
          <option value="10">10</option>
          <option value="20">20</option>
          <option value="50">50</option>
        </select>
      </div>

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
