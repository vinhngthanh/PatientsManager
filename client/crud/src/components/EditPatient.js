import React from "react";
import { useNavigate } from "react-router-dom";

function ComponentName() {
  const navigate = useNavigate();

  const cancel = () => {
    navigate(`/`);
  };

  return <button onClick={() => cancel()}>Cancel</button>;
}

export default ComponentName;
