package com.codewiz.tripplanner.service;

import com.codewiz.tripplanner.model.PlaceRecommendation;
import com.codewiz.tripplanner.model.PlaceRecords;
import com.codewiz.tripplanner.model.WeatherRecords;
import io.micrometer.observation.annotation.Observed;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;

@Service
@AllArgsConstructor
@Slf4j
public class TripPlannerService {

    private final PlacesService placesService;
    private final WeatherService weatherService;

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
                WeatherRecords.WeatherData weather = weatherDataSubtask.get();
                return new PlaceRecommendation(place, photos, weather);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        })
        .toList();
    }
}
