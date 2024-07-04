package com.codewiz.tripplanner.model;

import java.util.List;

public record PlaceRecommendation(PlaceRecords.Place place,
                                  List<String> photos,List<WeatherRecords.WeatherData> weatherDataList) {
}
