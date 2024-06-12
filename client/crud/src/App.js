import React, { useState, useEffect } from "react";
import {
  BrowserRouter as Router,
  Route,
  Routes,
  Navigate,
} from "react-router-dom";
import AllPatients from "./components/AllPatients";
import EditPatient from "./components/EditPatient";
import CreatePatient from "./components/CreatePatient";
import SignIn from "./components/SignIn";
import SignUp from "./components/SignUp";

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [isAdmin, setIsAdmin] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem("jwtToken");
    setIsLoggedIn(!!token);
    const role = localStorage.getItem("role");
    setIsAdmin(role === "ADMIN");
  }, []);

  return (
    <Router>
      <Routes>
        {!isLoggedIn ? (
          <>
            <Route path="/" element={<SignIn />} />
            <Route path="/signup" element={<SignUp />} />
            <Route path="*" element={<Navigate to="/" />} />
          </>
        ) : (
          <>
            <Route path="/patients" element={<AllPatients />} />
            {isAdmin && (
              <Route path="/edit/:patientId" element={<EditPatient />} />
            )}
            {isAdmin && <Route path="/create" element={<CreatePatient />} />}
            <Route path="*" element={<Navigate to="/patients" />} />
          </>
        )}
      </Routes>
    </Router>
  );
}

export default App;
