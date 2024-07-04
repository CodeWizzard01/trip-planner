package com.codewiz.tripplanner.service;

import com.codewiz.tripplanner.exception.NoDataFoundException;
import com.codewiz.tripplanner.model.PlaceRecords;
import com.codewiz.tripplanner.model.WeatherRecords;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WeatherService {

    private RestClient weatherClient;

    private String apiKey;

    public WeatherService(@Value("${weather.api.key}") String apiKey){
        this.apiKey = apiKey;


        weatherClient = RestClient.builder()
                .baseUrl("https://api.openweathermap.org/data/2.5")
                .requestInterceptor((request, body, execution) -> {
                    var time1 = System.currentTimeMillis();
                    var response = execution.execute(request, body);
                    var time2 = System.currentTimeMillis();
                    log.debug("Time taken for request "+request.getURI()+" : "+(time2 - time1)+"ms");
                    return response;
                })
                .build();
        //https://github.com/making/retryable-client-http-request-interceptor
    }

    @Retryable(retryFor = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public List<WeatherRecords.WeatherData> getWeather(PlaceRecords.Location location, String travelDate){
        log.info("Getting weather data for location: " + location + " and date: " + travelDate);
        String uriString = UriComponentsBuilder.fromUriString("/forecast?lat=-{lat}&lon={long}&appid={apiKey}&units=metric")
                .buildAndExpand(location.latitude(), location.longitude(), apiKey)
                .toUriString();
        var weatherResponse = weatherClient.get()
                .uri(uriString)
                .retrieve()
                .body(WeatherRecords.WeatherResponse.class);
        return weatherResponse.list().stream()
                .filter(weatherData -> weatherData.dtTxt().endsWith("12:00:00"))
                .collect(Collectors.toList());
    }

    @Recover
    public WeatherRecords.WeatherData recover(Exception e,PlaceRecords.Location location, String travelDate){
        log.info("Recovering from exception for locaton "+location);
        WeatherRecords.WeatherData weatherData = new WeatherRecords.WeatherData(
                new WeatherRecords.Main(0, 0, 0, 0, 0),
                List.of(new WeatherRecords.Weather("Cached Date","","'")), new WeatherRecords.Clouds(0),
                new WeatherRecords.Wind(0, 0, 0), "No Data");
        return weatherData;
    }

}
