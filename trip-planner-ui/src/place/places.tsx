import React, { useEffect, useState } from "react";

import Place from "./place";
import { PlaceData, TravelPlanItem } from '@/types/types';

function Places({
  location,
  startDate,
  endDate,
}: {
  location: string;
    startDate: Date | null;
    endDate: Date | null;
}) {
  const [places, setPlaces] = useState<PlaceData[]>([]);
  const [travelPlan, setTravelPlan] = useState<TravelPlanItem[]>([]);

  const getPlaceByAddress = (address: string) =>
    places.find((place) => place.place.formattedAddress === address) ||
    places.find((place) => address.startsWith(place.place.displayName.text));

  useEffect(() => {
    console.log(location, startDate);
    if (location && startDate && startDate) {
      const formattedStartDate = startDate.toISOString().split("T")[0]; 
      const formattedEndDate = startDate.toISOString().split("T")[0];
      const apiUrl = `http://localhost:8080/trip-planner/trip-recommendation/${encodeURIComponent(
        location
      )}/${formattedStartDate}/${formattedEndDate}`;

      fetch(apiUrl)
        .then((response) => response.json())
        .then((data) => {
          setPlaces(data.placesToVisit);
          setTravelPlan(data.travelPlan);
        })
        .catch((error) => console.error("Error fetching places:", error));

    }
  }, [location, startDate,endDate]); 

  return (
    <div>
      {travelPlan.map((travelPlanItem, index) => {
        const placeData = getPlaceByAddress(travelPlanItem.address);
        return placeData ? (
          <Place
            key={index}
            index={index}
            placeData={placeData}
            travelPlanItem={travelPlanItem}
          />
        ) : null;
      })}
    </div>
  );
}

export default Places;
