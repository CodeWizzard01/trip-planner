```top tourist places``` : ```{placeSummaryJson}```
Create itinerary strictly for dates between ```{startDate}``` and ```{endDate}```.
You should cover all the places in the list above.
Place should not be repeated in the itinerary.
```reasonForChoosing``` should state why you have chosen that place. Include things like reviews, weather etc.
Based on reviews and your knowledge about the place populate ```activity``` field in the output.
Limit ```activity``` to 120 characters.
Limit ```reasonForChoosing``` to 120 characters.
In the output, the ```address``` field should match the ```address``` field in the input.
```time``` should be populated based on the time taken to visit the previous place and time to reach second one.
Your answer should only be based on the data in input. Dont assume anything.