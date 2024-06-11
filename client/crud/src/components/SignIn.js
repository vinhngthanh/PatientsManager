import React, { useState } from "react";
import axiosInstance from "../instances/AxiosInstance";
import { useNavigate } from "react-router-dom";

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

    axiosInstance
      .post("http://localhost:8080/auth/signin", loginInfo)
      .then((response) => {
        const token = response.data.accessToken;
        localStorage.setItem("jwtToken", token);
        navigate(`/`);
      })
      .catch((error) => {
        console.error("Error creating user:", error);
      });
  };

  return (
    <div>
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
  );
}

export default SignIn;
