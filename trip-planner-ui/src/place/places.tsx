import React, { useEffect, useState } from "react";

import { placesData } from "../mock/mockData";
import Place from "./place";

function Places({
  location,
  travelDate,
}: {
  location: string;
  travelDate: Date | null;
}) {
  const [places, setPlaces] = useState([]);

  useEffect(() => {
    console.log(location, travelDate);
    if (location && travelDate) {
      const formattedDate = travelDate.toISOString().split("T")[0]; // Format date as YYYY-MM-DD
      const apiUrl = `http://localhost:8080/trip-planner/${encodeURIComponent(
        location
      )}/${formattedDate}`;

      fetch(apiUrl)
        .then((response) => response.json())
        .then((data) => {
          console.log(data);
          setPlaces(data);
        })
        .catch((error) => console.error("Error fetching places:", error));
    }
  }, [location, travelDate]); // Dependency array, re-run effect if these values change

  return (
    <div
      style={{
        display: "grid",
        gridTemplateColumns: "repeat(5, 1fr)",
        gap: "20px", // Adjust the gap as needed
        padding: "20px", // Adds some padding around the grid
      }}
    >
      {places.map((placeData, index) => (
        <Place key={index} index={index} placeData={placeData} />
      ))}
    </div>
  );
}

export default Places;
