package com.codewiz.tripplanner.service;

import com.codewiz.tripplanner.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.micrometer.observation.annotation.Observed;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.StructuredTaskScope;

@Service
@Slf4j
public class TripPlannerService {

    private final PlacesService placesService;
    private final WeatherService weatherService;
    private final ChatClient chatClient;
    private final Resource systemPromptTemplate;
    private final Resource userPromptTemplate;

    public TripPlannerService(PlacesService placesService, WeatherService weatherService
        , ChatClient.Builder chatClientBuilder,@Value("classpath:/templates/system.st") Resource systemPromptTemplate,
         @Value("classpath:/templates/user.st") Resource userPromptTemplate) {
        this.placesService = placesService;
        this.weatherService = weatherService;
        this.systemPromptTemplate = systemPromptTemplate;
        this.userPromptTemplate = userPromptTemplate;
        this.chatClient = chatClientBuilder
            .defaultAdvisors(new SimpleLoggerAdvisor())
            .build();
    }


    @Observed(name="tripPlannerService")
    public List<PlaceRecommendation> getPlaceRecommendation(String location, String travelDate){

        List<PlaceRecords.Place> places = placesService.getTopTouristPlaces(location);

        return places.stream()
                .sorted(Comparator.comparing((PlaceRecords.Place place) -> place.rating()).reversed())
                .parallel().map(place -> {
            try(var scope = new StructuredTaskScope.ShutdownOnFailure()) {
                var photoSubTasks = place.photos().stream().limit(2)
                        .map((PlaceRecords.Photo photo) -> scope.fork(()->placesService.getPhotoURI(photo.name())))
                        .toList();
                log.info(STR."Current Thread : \{Thread.currentThread()}");
                var weatherDataSubtask = scope.fork(()->weatherService.getWeather(place.location(), travelDate));
                scope.join();
                scope.throwIfFailed();
                List<String> photos = photoSubTasks.stream().map(StructuredTaskScope.Subtask::get).toList();
                var weather = weatherDataSubtask.get();
                return new PlaceRecommendation(place, photos, weather);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        })
        .toList();
    }

    public List<TravelItineraryItem> getTripRecommendation(List<PlaceRecommendation> placeRecommendations, String startDate,
                                                           String endDate) throws JsonProcessingException {
        List<PlaceSummary> placeSummaryList = placeRecommendations.stream().map(PlaceSummary::new).toList();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        String placeSummaryJson = objectWriter.writeValueAsString(placeSummaryList);
        Map<String,Object> variablesMap = Map.of(
                "placeSummaryJson", placeSummaryJson,
                "startDate", startDate,
                "endDate", endDate);
        return this.chatClient.prompt()
                .system(systemPromptTemplate)
                .user(u -> u.text(userPromptTemplate).params(variablesMap))
                .call()
                .entity(new ParameterizedTypeReference<List<TravelItineraryItem>>() {
                });

    }
}
