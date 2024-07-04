import { Card, CardContent } from "@/components/ui/card";
import Typography from "@/components/ui/typography";
import { PlaceData } from "@/types/types";
import React from "react";

function PlaceDetails({
  index,
  placeData,
}: {
  index: number;
  placeData: PlaceData;
}) {
  return (
    <Card key={index} style={{ margin: "20px", width: "280px", height: "90%" }}>
      <div
        style={{
          backgroundImage: `url(${placeData.photos[0]})`,
          backgroundSize: "cover",
          height: "200px",
          width: "100%",
        }}
      />
      <CardContent className="grid gap-4">
        <Typography element="h3" as="h3">
          {index + 1}. {placeData.place.displayName.text} -{" "}
          {placeData.place.primaryTypeDisplayName?.text}
        </Typography>
        <Typography element="p" as="p">
          {placeData.place.formattedAddress}
        </Typography>
        <Typography
          element="p"
          as="p"
        >{`Rating: ${placeData.place.rating}`}</Typography>
      </CardContent>
    </Card>
  );
}

export default PlaceDetails;
