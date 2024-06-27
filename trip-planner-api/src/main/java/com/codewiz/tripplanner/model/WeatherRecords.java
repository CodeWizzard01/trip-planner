package com.codewiz.tripplanner.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WeatherRecords {

    public static record Main(double temp, double feels_like, double temp_min, double temp_max, int humidity) {}

    public static record Weather(String main, String description, String icon) {}

    public static record Clouds(int all) {}

    public static record Wind(double speed, int deg, double gust) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static record WeatherData(Main main, List<Weather> weather, Clouds clouds, Wind wind, @JsonProperty("dt_txt") String dtTxt) {}

    public static record WeatherResponse(List<WeatherData> list) {}
}
