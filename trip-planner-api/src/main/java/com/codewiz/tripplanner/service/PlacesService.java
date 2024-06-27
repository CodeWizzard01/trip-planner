package com.codewiz.tripplanner.service;

import com.codewiz.tripplanner.model.PlaceRecords;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class PlacesService {

    private RestClient placesClient;

    private String apiKey;

    public PlacesService(@Value("${places.api.key}") String apiKey) {
        this.apiKey = apiKey;
        this.placesClient = RestClient.builder()
                .baseUrl("https://places.googleapis.com/v1/")
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.set("Content-Type", "application/json");
                    httpHeaders.set("X-Goog-Api-Key", apiKey);
                })
                .build();
    }

    public List<PlaceRecords.Place> getTopTouristPlaces(String location){
        return placesClient.post()
                .uri("/places:searchText")
                .header("X-Goog-FieldMask","places.displayName,places.formattedAddress,places.priceLevel,places.photos,places.location,places.primaryTypeDisplayName,places.websiteUri,places.rating,places.currentOpeningHours,places.internationalPhoneNumber,places.reviews")
                .body("""
                        {
                            "textQuery" : "Top 5 Tourist places near %s",
                            "pageSize":5
                        }
                        """.formatted(location)
                )
                .retrieve()
                .body(PlaceRecords.PlacesResponse.class)
                .places();
    }

    public String getPhotoURI(String photoId){
        record PhotoResponse(String photoUri){}
        String uriString = UriComponentsBuilder.fromUriString("/{photoId}/media?key={apiKey}&maxHeightPx=1200&skipHttpRedirect=true")
                .buildAndExpand(photoId, apiKey)
                .toUriString();
        return placesClient.get()
                .uri(uriString)
                .retrieve()
                .body(PhotoResponse.class)
                .photoUri();
    }
}
