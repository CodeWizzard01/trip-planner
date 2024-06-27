package com.codewiz.tripplanner.controller;

import com.codewiz.tripplanner.model.PlaceRecommendation;
import com.codewiz.tripplanner.service.TripPlannerService;
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

}
