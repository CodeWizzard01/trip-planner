import { Card, CardContent } from "@/components/ui/card";
import Typography from "@/components/ui/typography";
import { PlaceData } from "@/types/types";
import React from "react";

function getWeatherIcon(icon: string) {
  return `http://openweathermap.org/img/wn/${icon}.png`;
}

function Weather({ placeData, index }: { placeData: PlaceData, index: number}) {
  const weatherData = placeData.weatherDataList[0];
  return (
    <Card key={index} style={{ margin: "20px", width: "250px", height: "90%" }}>
      <CardContent className="grid gap-4">
        <div>
          <div>
            <Typography element="h3" as="h3">
              Weather
            </Typography>
            <br />
            <Typography
              element="p"
              as="p"
            >{`Temperature: ${weatherData.main.temp}°C`}</Typography>
            <Typography
              element="p"
              as="p"
            >{`Feels Like: ${weatherData.main.feels_like}°C`}</Typography>
            <Typography
              element="p"
              as="p"
            >{`Condition: ${weatherData.weather[0].main} - ${weatherData.weather[0].description}`}</Typography>
            <Typography
              element="p"
              as="p"
            >{`Humidity: ${weatherData.main.humidity}%`}</Typography>
            <Typography
              element="p"
              as="p"
            >{`Wind: ${weatherData.wind.speed} m/s`}</Typography>
            {/* Display weather icon */}
            <img
              src={getWeatherIcon(weatherData.weather[0].icon)}
              alt="Weather Icon"
              style={{ width: "50px", height: "50px" }}
            />
          </div>
        </div>
      </CardContent>
    </Card>
  );
}

export default Weather;
