package com.codewiz.tripplanner.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PlaceSummary(
        String address,
        double rating,
        List<String> reviews,
        String displayName,
        String type,
        int openingHour,
        int closingHour,
        PlaceRecords.Location location,
        @JsonProperty("weather") List<WeatherSummary> weatherSummaryList) {

    public PlaceSummary(PlaceRecommendation placeRecommendation) {
        var place = placeRecommendation.place();
        var weatherSummaryList = placeRecommendation.weatherDataList().stream()
                .map(weatherData -> new WeatherSummary(
                        weatherData.weather().get(0).description(),
                        weatherData.main().temp(),
                        weatherData.main().temp_min(),
                        weatherData.main().temp_max(),
                        weatherData.main().humidity(),
                        weatherData.dtTxt()
                )).toList();
        this(
                place.formattedAddress(),
                place.rating(),
                place.reviews()!=null?place.reviews().stream()
                        .filter(review -> review.text()!=null).map(review -> review.text().text()).toList():null,
                place.displayName().text(),
                place.primaryTypeDisplayName() != null ? place.primaryTypeDisplayName().text() : "",
                10,
                18,
                place.location(),
                weatherSummaryList
        );
    }
}
