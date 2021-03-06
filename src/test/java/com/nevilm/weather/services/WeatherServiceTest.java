package com.nevilm.weather.services;

import com.nevilm.weather.datamodel.Location;
import com.nevilm.weather.datamodel.LocationContainer;
import com.nevilm.weather.datamodel.Weather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class WeatherServiceTest {
    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    WeatherService weatherService = new WeatherService();

    final LocationContainer locationContainer = new LocationContainer();
    final Location location = new Location();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private void setupLocationContainer() {
        location.setCity_name("Cork");
        location.setCountry_code("IE");
        location.setLatitude("51.89797");
        location.setLongitude("-8.47061");
        location.setSunrise("07:05");
        location.setSunset("18:23");
        location.setTemp(10.4);
        location.setWeather(new Weather("Cloudy"));

        locationContainer.setData(List.of(location));
    }

    @Test
    public void testGetCurrentConditionsNullInputThrowsInternalServerError() throws ResponseStatusException {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> weatherService.getCurrentConditions(null));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
    }

    @Test
    public void testGetCurrentConditionsEmptyInputThrowsInternalServerError() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> weatherService.getCurrentConditions(""));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
    }

    @Test
    public void testGetCurrentConditionsWeatherContainerNullThrowsInternalServerError() {
        when(restTemplate.getForObject(ArgumentMatchers.anyString(), ArgumentMatchers.any())).thenReturn(null);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> weatherService.getCurrentConditions("test"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
    }

    @Test
    public void testGetCurrentConditionsWeatherContainerNothingReturnedThrowsInternalServerError() {
        when(restTemplate.getForObject(ArgumentMatchers.anyString(), ArgumentMatchers.any())).thenReturn(new LocationContainer());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> weatherService.getCurrentConditions("test"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
    }

    @Test
    public void testGetCurrentConditionsFailedCallThrowsInternalServerError() {
        when(restTemplate.getForObject(ArgumentMatchers.anyString(), ArgumentMatchers.any())).thenThrow(new RestClientException("Failed"));
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> weatherService.getCurrentConditions("test"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
    }

    @Test
    public void testGetCurrentConditions() {
        this.setupLocationContainer();
        when(restTemplate.getForObject(ArgumentMatchers.anyString(), ArgumentMatchers.any())).thenReturn(locationContainer);
        assertEquals(location, weatherService.getCurrentConditions("test"));
    }
}