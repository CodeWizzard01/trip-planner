import { Card, CardContent } from '@/components/ui/card';
import Typography from '@/components/ui/typography';
import { PlaceData } from '@/types/types';
import React from 'react'
import Weather from './weather';

function Place({ index, placeData }: { index: number, placeData: PlaceData }) {
  return (
    <Card key={index} style={{ margin: "20px", width: "300px" }}>
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
          {placeData.place.displayName.text} -{" "}
          {placeData.place.primaryTypeDisplayName?.text}
        </Typography>
        <Typography element="p" as="p">
          {placeData.place.formattedAddress}
        </Typography>
        <Typography
          element="p"
          as="p"
              >{`Rating: ${placeData.place.rating}`}</Typography>
        <Weather placeData={placeData} />
      </CardContent>
    </Card>
  );
}

export default Place;
