import { Card, CardContent } from '@/components/ui/card';
import Typography from '@/components/ui/typography';
import { TravelPlanItem } from '@/types/types';
import React from 'react'

function Recommendation({
  travelPlanItem,
  index,
}: {
  travelPlanItem: TravelPlanItem;
  index: number;
}) {
  return (
    <Card key={index} style={{ margin: "20px", width: "250px", height: "90%"}}>
      <CardContent className="grid gap-4">
        <Typography element="h3" as="h3">
          Recommendation
        </Typography>
        <Typography
          element="p"
          as="p"
        >{`Date: ${travelPlanItem.date}`}</Typography>
        <Typography
          element="p"
          as="p"
        >{`Time: ${travelPlanItem.time}`}</Typography>
        <Typography
          element="p"
          as="p"
        >{`Activity: ${travelPlanItem.activity}`}</Typography>
        <Typography
          element="p"
          as="p"
        >{`Reason for choosing: ${travelPlanItem.reasonForChoosing}`}</Typography>
      </CardContent>
    </Card>
  );
}

export default Recommendation
