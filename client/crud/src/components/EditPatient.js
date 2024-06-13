import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import axiosInstance from "../instances/AxiosInstance";
import "../css/AllPatients.css";

function EditPatient() {
  const { patientId } = useParams();
  const [name, setName] = useState("");
  const [gender, setGender] = useState("");
  const [age, setAge] = useState("");
  const [email, setEmail] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [error, setError] = useState("");
  const [showConfirmEdit, setShowConfirmEdit] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    axiosInstance
      .get(`http://localhost:8080/patients/${patientId}`)
      .then((response) => {
        const patientData = response.data;
        setName(patientData.name);
        setGender(patientData.gender);
        setAge(patientData.age);
        setEmail(patientData.email);
        setPhoneNumber(patientData.phoneNumber);
      })
      .catch((error) => {
        console.error("Error retrieving patient:", error);
        if (error.response) {
          setError(`${error.response.data.error}`);
        } else if (error.request) {
          setError("No response received from server. Please try again later.");
        } else {
          setError(`Error in setting up request: ${error.message}`);
        }
      });
  }, [patientId]);

  const validateFields = () => {
    if (!name || !gender || !age || !phoneNumber) {
      setError("Please fill in all fields.");
      return false;
    }

    if (isNaN(age)) {
      setError("Age must be a number.");
      return false;
    }

    const phoneRegex = /^\+\d{1,3}-\d{3}-\d{3}-\d{4}$/;
    if (!phoneRegex.test(phoneNumber)) {
      setError("Phone number must be in the format '+xxx-xxx-xxx-xxxx'.");
      return false;
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
      setError("Please enter a valid email address.");
      return false;
    }

    setError("");
    return true;
  };

  const cancel = () => {
    navigate(`/patients`);
  };

  const handleBackEdit = () => {
    setShowConfirmEdit(false);
  };

  const handleSaveClick = () => {
    if (validateFields()) {
      setShowConfirmEdit(true);
    }
  };

  const save = () => {
    const updatedUser = {
      name,
      gender,
      age,
      email,
      phoneNumber,
    };

    axiosInstance
      .put(`http://localhost:8080/patients/${patientId}`, updatedUser)
      .then(() => {
        navigate(`/patients`);
      })
      .catch((error) => {
        console.error("Error updating patient:", error);
        if (error.response) {
          console.error("Response data:", error.response.data);
          setError(`${error.response.data.error}`);
        } else if (error.request) {
          console.error("No response received from server.");
          setError("No response received from server. Please try again later.");
        } else {
          console.error("Error setting up request:", error.message);
          setError(`Error in setting up request: ${error.message}`);
        }
      });
  };

  return (
    <div className="form-container">
      <div className="form">
        <h1>Edit Patient</h1>
        {error && <div className="error-box">{error}</div>}
        <form
          onSubmit={(e) => {
            e.preventDefault();
            save();
          }}
        >
          <div>
            <label>
              Name:
              <input
                type="text"
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
              />
            </label>
          </div>
          <div>
            <label>
              Gender:
              <select
                value={gender}
                onChange={(e) => setGender(e.target.value)}
                required
              >
                <option value="Male">Male</option>
                <option value="Female">Female</option>
              </select>
            </label>
          </div>
          <div>
            <label>
              Age:
              <input
                type="text"
                value={age}
                onChange={(e) => setAge(e.target.value)}
                required
              />
            </label>
          </div>
          <div>
            <label>
              Email:
              <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
            </label>
          </div>
          <div>
            <label>
              Phone Number:
              <input
                type="text"
                value={phoneNumber}
                onChange={(e) => setPhoneNumber(e.target.value)}
                required
              />
            </label>
          </div>
          <div>
            <button type="button" onClick={cancel}>
              Cancel
            </button>
            <button type="button" onClick={handleSaveClick}>
              Save
            </button>
          </div>
        </form>
      </div>
      {showConfirmEdit && (
        <div className="confirmation-dialog">
          <div className="dialog-content">
            <p>Are you sure you want to edit the following patient details?</p>
            <p>Name: {name}</p>
            <p>Gender: {gender}</p>
            <p>Age: {age}</p>
            <p>Email: {email}</p>
            <p>Phone Number: {phoneNumber}</p>
            <button onClick={handleBackEdit}>Back</button>
            <button onClick={save}>Confirm</button>
          </div>
        </div>
      )}
    </div>
  );
}

export default EditPatient;
