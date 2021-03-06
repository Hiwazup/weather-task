package com.nevilm.weather.controller;

import com.nevilm.weather.controller.validator.WeatherControllerValidator;
import com.nevilm.weather.datamodel.Location;
import com.nevilm.weather.datamodel.Weather;
import com.nevilm.weather.services.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class WeatherControllerTest {
    @Spy
    WeatherControllerValidator validator = new WeatherControllerValidator();

    @Mock
    WeatherService weatherService = new WeatherService();

    @InjectMocks
    WeatherController controller = new WeatherController();

    Location location = new Location();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        location.setCity_name("Cork");
        location.setCountry_code("IE");
        location.setLatitude("51.89797");
        location.setLongitude("-8.47061");
        location.setSunrise("07:05");
        location.setSunset("18:23");
        location.setTemp(10.4);
        location.setWeather(new Weather("Cloudy"));
        when(weatherService.getCurrentConditions(ArgumentMatchers.anyString())).thenReturn(location);
    }

    @Test
    public void getCurrentWeatherInvalidCity()
    {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> controller.getCurrentWeather("", null, null, null));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void getCurrentWeatherByCity()
    {
        assertEquals(location, controller.getCurrentWeather("Cork", null, null, null));
    }

    @Test
    public void getCurrentWeatherByCityAndCountry()
    {
        assertEquals(location, controller.getCurrentWeather("Cork", "Ireland", null, null));
    }

    @Test
    public void getCurrentWeatherByLatLon()
    {
        assertEquals(location, controller.getCurrentWeather(null, null, "51.89797", "-8.47061"));
    }

    @Test
    public void getCurrentWeatherDefault()
    {
        assertEquals(location, controller.getCurrentWeather(null, null, null, null));
    }
}