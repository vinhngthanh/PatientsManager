import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "../css/AllPatients.css";

function SignIn() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const cancel = () => {
    navigate(`/`);
  };

  const save = () => {
    const loginInfo = {
      username,
      password,
    };

    axios
      .post("http://localhost:8080/auth/signin", loginInfo)
      .then((response) => {
        const token = response.data.accessToken;
        const role = response.data.role;
        localStorage.setItem("jwtToken", token);
        localStorage.setItem("role", role);
        navigate(`/`);
      })
      .catch((error) => {
        console.error("Error creating user:", error);
      });
  };

  return (
    <div className="form-container">
      <div className="form">
        <h1>Login</h1>
        <form
          onSubmit={(e) => {
            e.preventDefault();
            save();
          }}
        >
          <div>
            <label>
              Username:
              <input
                type="text"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                required
              />
            </label>
          </div>
          <div>
            <label>
              Password:
              <input
                type="text"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </label>
          </div>
          <div>
            <button type="button" onClick={cancel}>
              Cancel
            </button>
            <button type="submit">Login</button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default SignIn;
