package com.codewiz.tripplanner.model;

import java.util.List;

public record TripRecommendationResponse(
        List<PlaceRecommendation> placesToVisit,
        List<TravelItineraryItem> travelPlan) {
}
