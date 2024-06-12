import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "../css/AllPatients.css";

function SignUp() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleSignIn = () => {
    navigate(`/`);
  };

  const handleRoleChange = (event) => {
    setRole(event.target.value);
  };

  const save = () => {
    const newUser = {
      username,
      password,
      role,
    };

    axios
      .post("http://localhost:8080/auth/signup", newUser)
      .then(() => {
        navigate(`/`);
      })
      .catch((error) => {
        console.error("Error logging in:", error);
        if (error.response && error.response.data) {
          setError(
            error.response.data.message || "Sign up failed. Please try again."
          );
        } else {
          setError("Sign up failed. Username taken.");
        }
      });
  };

  return (
    <div className="form-container">
      <div className="form">
        <h1>New Account</h1>
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
                type="text"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </label>
          </div>
          <div>
            <select
              id="roleSelect"
              name="roleSelect"
              onChange={handleRoleChange}
            >
              <option value="ADMIN">ADMIN</option>
              <option value="USER">USER</option>
            </select>
          </div>
          <div>
            <button type="submit">Sign Up</button>
            <button type="button" onClick={handleSignIn}>
              Sign In
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default SignUp;
