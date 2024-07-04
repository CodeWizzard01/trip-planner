package com.codewiz.tripplanner.controller;

import com.codewiz.tripplanner.model.PlaceRecommendation;
import com.codewiz.tripplanner.model.TravelItineraryItem;
import com.codewiz.tripplanner.model.TripRecommendationResponse;
import com.codewiz.tripplanner.service.TripPlannerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/trip-planner")
public class TripPlannerController {

    private final TripPlannerService tripPlannerService;

    @GetMapping("/{location}/{travelDate}")
    @CrossOrigin(origins = "http://localhost:5173")
    public List<PlaceRecommendation> getPlaceRecommendation(@PathVariable String location,
                                                            @PathVariable String travelDate){
        return tripPlannerService.getPlaceRecommendation(location,travelDate);
    }

    @GetMapping("/trip-recommendation/{location}/{startDate}/{endDate}")
    @CrossOrigin(origins = "http://localhost:5173")
    public TripRecommendationResponse getTripRecommendation(@PathVariable String location,
                                                            @PathVariable String startDate,
                                                            @PathVariable String endDate) throws JsonProcessingException {
        List<PlaceRecommendation> placesToVisit = tripPlannerService.getPlaceRecommendation(location,startDate);
        List<TravelItineraryItem> travelPlan = tripPlannerService.getTripRecommendation(placesToVisit,startDate,endDate);
        return new TripRecommendationResponse(placesToVisit,travelPlan);
    }

}
