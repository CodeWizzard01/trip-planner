package com.codewiz.tripplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class TripPlannerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TripPlannerApiApplication.class, args);
	}

}
