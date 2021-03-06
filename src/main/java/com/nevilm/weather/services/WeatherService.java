package com.nevilm.weather.services;

import com.nevilm.weather.datamodel.LocationContainer;
import com.nevilm.weather.datamodel.Location;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

/**
 * Service which uses the weatherbit API to get weather conditions.
 * @author Martin Neville
 */
@Service
public class WeatherService {
    private RestTemplate restTemplate;
    private final static String WEATHERBIT_BASE_URI = "https://api.weatherbit.io/v2.0/current?";
    private final static String WEATHERBIT_API_KEY = "FILL_ME_IN";

    public WeatherService() {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        if (restTemplate == null) {
            this.restTemplate = builder.build();
        }
    }

    /**
     * Takes in a string made up of location query params which are to be used in the get to the API. If no params are
     * provided or the API fails a Internal Server Error occurs.
     * @param locationParams Query params of the location to be looked up.
     * @return Mapped object Location from the API response.
     */
    public Location getCurrentConditions(String locationParams) {
        final String failedErrorMessage = "Unable to retrieve current conditions";
        if (locationParams == null || locationParams.isBlank()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, failedErrorMessage);
        }

        final String uri = getURI(locationParams);
        try {
            LocationContainer locationContainer = restTemplate.getForObject(uri, LocationContainer.class);
            if (locationContainer == null || locationContainer.getData() == null || locationContainer.getData().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, failedErrorMessage);
            }

            return locationContainer.getData().get(0);
        } catch (RestClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, failedErrorMessage);
        }
    }

    /**
     * Takes in the query params and generates the API call to be made.
     * @param params Query params to be used in the API call.
     * @return URI of API call.
     */
    private String getURI(String params) {
        StringBuilder builder = new StringBuilder(WEATHERBIT_BASE_URI);
        if (params != null) {
            builder.append(params);
        }

        builder.append("&key=" + WEATHERBIT_API_KEY);
        return builder.toString();
    }
}
