export type TravelPlanItem = {
  address: string;
  date: string;
  time: string;
  activity: string;
  reasonForChoosing: string;
};

export type TripRecommendation = {
  placesToVisit: PlaceData[];
  travelPlan: TravelPlanItem[];
};


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
    weatherDataList: WeatherData[];
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