import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "../css/AllPatients.css";

function SignIn() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleSignUp = () => {
    navigate("/signup");
  };

  const save = async () => {
    const loginInfo = {
      username,
      password,
    };

    try {
      const response = await axios.post(
        "http://localhost:8080/auth/signin",
        loginInfo
      );
      const token = response.data.accessToken;
      const role = response.data.role;
      localStorage.setItem("jwtToken", token);
      localStorage.setItem("role", role);
      navigate(`/patients`);
      window.location.reload();
    } catch (error) {
      console.error("Error logging in:", error);
      if (error.response && error.response.data) {
        setError(
          error.response.data.message || "Login failed. Please try again."
        );
      } else {
        setError("Login failed. Please check your credentials and try again.");
      }
    }
  };

  return (
    <div className="form-container">
      <div className="form">
        <h1>Login</h1>
        {error && <div className="error-box">{error}</div>}
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
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </label>
          </div>
          <div>
            <button type="submit">Login</button>
            <button type="button" onClick={handleSignUp}>
              Sign Up
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default SignIn;
