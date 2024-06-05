import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

//Need error handling
//Need constraint on input

function CreateUser() {
  const [name, setName] = useState("");
  const [gender, setGender] = useState("");
  const [age, setAge] = useState("");
  const [email, setEmail] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const navigate = useNavigate();

  const cancel = () => {
    navigate(`/`);
  };

  const save = () => {
    const newUser = {
      name,
      gender,
      age,
      email,
      phoneNumber,
    };

    axios
      .post("http://localhost:8080", newUser)
      .then(() => {
        navigate(`/`);
      })
      .catch((error) => {
        console.error("Error creating user:", error);
      });
  };

  return (
    <div>
      <h1>Create User</h1>
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
          <button type="submit">Save</button>
        </div>
      </form>
    </div>
  );
}

export default CreateUser;
