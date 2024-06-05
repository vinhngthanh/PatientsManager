import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import AllPatients from "./components/AllPatients";

function App() {
  return (
    <Router>
      <Routes>
        <Route exact path="/" element={<AllPatients />} />
      </Routes>
    </Router>
  );
}

export default App;
