package com.nevilm.weather.controller;

import com.nevilm.weather.controller.validator.WeatherControllerValidator;
import com.nevilm.weather.datamodel.Coordinates;
import com.nevilm.weather.datamodel.Location;
import com.nevilm.weather.services.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * Simple RestController to retrieve weather conditions.
 * @author Martin Neville
 */
@RestController
public class WeatherController {
    WeatherService weatherService = new WeatherService();
    WeatherControllerValidator validator = new WeatherControllerValidator();

    /**
     * REST call to get the current weather conditions of a particular location. If no location provided a random
     * set of co-ordinates are generated and their weather conditions are returned.
     * @param city Optional field to get the current weather of a city.
     * @param country Optional field to get the weather for a country. Used in conjunction with city. Will be ignored
     *                if city is not provided.
     * @param lon Optional field to get the weather at a particular longitude. lat field must also be populated.
     * @param lat Optional field to get the weather at a particular latitude. lon field must also be populated.
     * @return The weather conditions for the particular location.
     */
    @GetMapping("/weather/current")
    public Location getCurrentWeather(@RequestParam(value = "city", required = false) final String city, @RequestParam(value = "country", required = false) final String country, @RequestParam(value = "lon", required = false) final String lon, @RequestParam(value = "lat", required = false) final String lat) {
        final StringBuilder locationBuilder = new StringBuilder();
        validator.getCurrentWeatherValidator(city, country, lon, lat);

        if (city != null) {
            locationBuilder.append("city=").append(city);
            if(country != null) {
                locationBuilder.append("&country=").append(country);
            }
        } else if (lat != null) {
            final double latitude = Double.parseDouble(lat);
            final double longitude = Double.parseDouble(lon);
            final Coordinates latLong = new Coordinates(longitude, latitude);
            locationBuilder.append(latLong.getUriComponent());
        } else {
            Coordinates randomCoordinates = getRandomCoordinates();
            locationBuilder.append(randomCoordinates.getUriComponent());
        }

        final String location = locationBuilder.toString();
        return weatherService.getCurrentConditions(location);
    }

    /**
     * Generates a random pair of geo co-ordinates.
     * @return Random geo co-ordinates.
     */
    private static Coordinates getRandomCoordinates() {
        Random r = new Random();
        double upperLimit = 180;
        double lowerLimit = -180;
        double lon = r.nextDouble() * (upperLimit - lowerLimit) + lowerLimit;
        double lat = r.nextDouble() * (upperLimit - lowerLimit) + lowerLimit;
        return new Coordinates(lat, lon);
    }
}
