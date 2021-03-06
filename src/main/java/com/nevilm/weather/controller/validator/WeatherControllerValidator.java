package com.nevilm.weather.controller.validator;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Validation class where the inputs to the WeatherController can be validated.
 * @author Martin Neville
 */
public class WeatherControllerValidator {

    /**
     * Validator function for WeatherController.getCurrentWeather.
     * If a city is provided, ensure it contains data otherwise throw a bad request error. If a country is also provided
     * with a city then check to see if that is also populated. If longitude is provided and latitude is not or vice
     * versa, similarly a bad request error is thrown. If no fields are populated nothing happens.
     * @param city Value for city to be looked up.
     * @param country Value for country to be used with city.
     * @param longitude Value for longitude to be looked up.
     * @param latitude Value for latitude to be looked up.
     * @throws ResponseStatusException Throws exception is a field has been set but no data populated.
     */
    public void getCurrentWeatherValidator(final String city, String country, final String longitude, final String latitude) throws ResponseStatusException {
        String errorMessage = "Invalid parameters received: %s";
        if (city != null) {
            if (city.isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(errorMessage, "city"));
            } else if (country != null && country.isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(errorMessage, "country"));
            }
        } else {
            if ((longitude != null && latitude == null) || (longitude == null && latitude != null)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(errorMessage, "co-ordinates"));
            } else if (longitude != null) {
                try {
                    Double.parseDouble(longitude);
                    Double.parseDouble(latitude);
                } catch (NumberFormatException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(errorMessage, "co-ordinates"));
                }
            }
        }
    }
}
