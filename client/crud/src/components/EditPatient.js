import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";

function EditPatient() {
  const { patientId } = useParams();
  const [name, setName] = useState("");
  const [gender, setGender] = useState("");
  const [age, setAge] = useState("");
  const [email, setEmail] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    axios
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
        console.error("Error creating user:", error);
        if (error.response) {
          setError(`${error.response.data.error}`);
        } else if (error.request) {
          setError("No response received from server. Please try again later.");
        } else {
          setError(`Error in setting up request: ${error.message}`);
        }
      });
  }, [patientId]);

  const cancel = () => {
    navigate(`/`);
  };

  const save = () => {
    if (isNaN(age)) {
      setError("Age must be a number");
      return;
    }

    const updatedUser = {
      name,
      gender,
      age,
      email,
      phoneNumber,
    };

    axios
      .put(`http://localhost:8080/patients/${patientId}`, updatedUser)
      .then(() => {
        navigate(`/`);
      })
      .catch((error) => {
        console.error("Error creating user:", error);
        if (error.response) {
          setError(`${error.response.data.error}`);
        } else if (error.request) {
          setError("No response received from server. Please try again later.");
        } else {
          setError(`Error in setting up request: ${error.message}`);
        }
      });
  };

  return (
    <div>
      <h1>Edit User</h1>
      {error && <div>{error}</div>}
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
            <input
              type="text"
              value={gender}
              onChange={(e) => setGender(e.target.value)}
              required
            />
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
          <button type="submit">Save</button>
        </div>
      </form>
    </div>
  );
}

export default EditPatient;
