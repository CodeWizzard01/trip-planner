package com.codewiz.tripplanner.model;

public record TravelItineraryItem(String address, String date, String time,
                                  String activity, String reasonForChoosing) {
}
