import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import AllPatients from "./components/AllPatients";
import EditPatient from "./components/EditPatient";
import CreatePatient from "./components/CreatePatient";
import SignIn from "./components/SignIn";
import SignUp from "./components/SignUp";

function App() {
  return (
    <Router>
      <Routes>
        <Route exact path="/" element={<AllPatients />} />
        <Route exact path="/edit/:patientId" element={<EditPatient />} />
        <Route exact path="/create" element={<CreatePatient />} />
        <Route exact path="/signin" element={<SignIn />} />
        <Route exact path="/signup" element={<SignUp />} />
      </Routes>
    </Router>
  );
}

export default App;
