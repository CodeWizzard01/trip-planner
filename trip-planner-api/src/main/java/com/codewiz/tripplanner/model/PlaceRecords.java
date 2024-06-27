package com.codewiz.tripplanner.model;

import java.util.List;

public class PlaceRecords {

    public static record Location(double latitude, double longitude) {}

    public static record Date(int year, int month, int day) {}

    public static record OpenClose(Date date, int day, int hour, int minute) {}

    public static record Period(OpenClose open, OpenClose close) {}

    public static record CurrentOpeningHours(boolean openNow, List<Period> periods, List<String> weekdayDescriptions) {}

    public static record Text(String text, String languageCode) {}

    public static record AuthorAttribution(String displayName, String uri, String photoUri) {}

    public static record Review(String name, String relativePublishTimeDescription, int rating, Text text, Text originalText, AuthorAttribution authorAttribution, String publishTime) {}

    public static record Photo(String name) {}

    public static record Place(String internationalPhoneNumber, String formattedAddress, Location location, double rating, String websiteUri, Text displayName, Text primaryTypeDisplayName, CurrentOpeningHours currentOpeningHours, List<Review> reviews, List<Photo> photos) {}

    public record PlacesResponse(List<Place> places) {}

}
