import React from "react";
import { useNavigate } from "react-router-dom";

function ComponentName() {
  const navigate = useNavigate();

  const cancel = () => {
    navigate(`/`);
  };

  const save = () => {};

  return (
    <div>
      <button onClick={() => cancel()}>Cancel</button>
      <button onClick={() => save()}>Save</button>
    </div>
  );
}

export default ComponentName;
