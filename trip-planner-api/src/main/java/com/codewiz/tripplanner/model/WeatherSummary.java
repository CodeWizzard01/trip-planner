package com.codewiz.tripplanner.model;

public record WeatherSummary(String description,
                             double temperature,
                             double minTemperature,
                             double maxTemperature,
                             int humidity,
                             String date) {
}
