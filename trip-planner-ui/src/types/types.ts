export type PlaceData = {
    place: {
      internationalPhoneNumber: string | null;
      formattedAddress: string;
      location: {
        latitude: number;
        longitude: number;
      };
      rating: number;
      websiteUri: string | null;
      displayName: {
        text: string;
      };
      primaryTypeDisplayName?: { text?: string } | null;
      currentOpeningHours: {
        openNow: boolean;
        weekdayDescriptions: string[];
      };
      photos: unknown[];
    };
    photos: string[];
    weatherData: WeatherData;
  };
  
  type WeatherData = {
    main: {
      temp: number;
      feels_like: number;
      temp_min: number;
      temp_max: number;
      humidity: number;
    };
    weather: {
      main: string;
      description: string;
      icon: string;
    }[];
    clouds: {
      all: number;
    };
    wind: {
      speed: number;
      deg: number;
      gust: number;
    };
    dt_txt: string;
  };