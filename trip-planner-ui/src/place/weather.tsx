import Typography from '@/components/ui/typography';
import { PlaceData } from '@/types/types'
import React from 'react'


function getWeatherIcon(icon: string) {
  return `http://openweathermap.org/img/wn/${icon}.png`;
}

function Weather({ placeData }: { placeData: PlaceData }) {
  return (
    <div>
      <div>
        <Typography
          element="p"
          as="p"
        >{`Temperature: ${placeData.weatherData.main.temp}°C`}</Typography>
        <Typography
          element="p"
          as="p"
        >{`Feels Like: ${placeData.weatherData.main.feels_like}°C`}</Typography>
        <Typography
          element="p"
          as="p"
        >{`Condition: ${placeData.weatherData.weather[0].main} - ${placeData.weatherData.weather[0].description}`}</Typography>
        <Typography
          element="p"
          as="p"
        >{`Humidity: ${placeData.weatherData.main.humidity}%`}</Typography>
        <Typography
          element="p"
          as="p"
        >{`Wind: ${placeData.weatherData.wind.speed} m/s`}</Typography>
        {/* Display weather icon */}
        <img
          src={getWeatherIcon(placeData.weatherData.weather[0].icon)}
          alt="Weather Icon"
          style={{ width: "50px", height: "50px" }}
        />
      </div>
    </div>
  );
}

export default Weather
