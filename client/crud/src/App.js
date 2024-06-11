import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import AllPatients from "./components/AllPatients";
import EditPatient from "./components/EditPatient";
import CreatePatient from "./components/CreatePatient";

function App() {
  return (
    <Router>
      <Routes>
        <Route exact path="/" element={<AllPatients />} />
        <Route exact path="/edit/:patientId" element={<EditPatient />} />
        <Route exact path="/create" element={<CreatePatient />} />
        <Route exact path="/auth/signin" element={<CreatePatient />} />
        <Route exact path="/auth/signup" element={<CreatePatient />} />
      </Routes>
    </Router>
  );
}

export default App;
