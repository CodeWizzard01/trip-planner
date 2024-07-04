import { PlaceData, TravelPlanItem } from '@/types/types';
import React from 'react'
import Weather from './weather';
import Recommendation from './recommendation';
import PlaceDetails from './place-details';

function Place({
  index,
  placeData,
  travelPlanItem,
}: {
  index: number;
  placeData: PlaceData;
  travelPlanItem: TravelPlanItem;
  }) {
  return (
    <div
      style={{
        display: "inline-grid",
        gridTemplateColumns: "repeat(5, 1fr)",
        gap: "1px",
        padding: "1px",
        border: "1px solid #ccc",
        borderRadius: "10px",
        margin: "5px",
        width: "48%",
        height: "530px",
      }}
    >
      <PlaceDetails index={index} placeData={placeData} />
      <Recommendation index={index} travelPlanItem={travelPlanItem} />
      <Weather placeData={placeData} index={index} />
    </div>
  );
}

export default Place;
