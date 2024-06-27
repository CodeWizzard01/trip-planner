# Project Title

This is a simple trip planner API which will accept a location and date and will list the tourist attractions in that location along with the weather forecast for that date.

## API Integrations


### 1. Google Places API

```bash
    curl -X POST -d '{
    "textQuery" : "Top 5 Tourist places near Sydney",
    "pageSize":1
    }' \
    -H 'Content-Type: application/json' -H "X-Goog-Api-Key: $PLACES_API_KEY" \
    -H 'X-Goog-FieldMask: places.displayName,places.formattedAddress,places.priceLevel,places.photos,places.location,places.primaryTypeDisplayName,places.websiteUri,places.rating,places.currentOpeningHours,places.internationalPhoneNumber,places.reviews' \
    'https://places.googleapis.com/v1/places:searchText' \
    -o places-response.json
```


```bash
    curl "https://places.googleapis.com/v1/places/ChIJ3QHC54usEmsRx4j_ehfbPmI/photos/AUc7tXWlS4RntGGb50FCg6kilRbpUgx_zLMUCgrJOchd050CWlyR53eXQJ8SpDIGhHraS1sWF4GgVsEkWPP_1_RhtZs9Qka_DgZrI8SmovhxFiDZB71hyT_26jTT3CAIgASe1mxm1k8KJjHxyR9GnVokVnJtAL1dQ3vcfsf2/media?key=$PLACES_API_KEY&maxHeightPx=1200&skipHttpRedirect=true" \
    -o photo-details-response.json
```

### 2. OpenWeatherMap API
```bash
    curl "https://api.openweathermap.org/data/2.5/forecast?lat=-33.8731383&lon=151.2112757&appid=$WEATHER_API_KEY&units=metric" \
    -o weather-response.json

```