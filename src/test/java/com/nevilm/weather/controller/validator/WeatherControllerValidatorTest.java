package com.nevilm.weather.controller.validator;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

class WeatherControllerValidatorTest {
    WeatherControllerValidator validator = new WeatherControllerValidator();

    @Test
    public void getCurrentWeatherValidatorNullInputs() {
        assertDoesNotThrow(() -> validator.getCurrentWeatherValidator(null, null, null, null));
    }

    @Test
    public void getCurrentWeatherValidatorCityEmpty() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> validator.getCurrentWeatherValidator("", null, null, null));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void getCurrentWeatherValidatorCityOkCountryNull() {
        assertDoesNotThrow(() -> validator.getCurrentWeatherValidator("test", null, null, null));
    }

    @Test
    public void getCurrentWeatherValidatorCityOkCountryEmpty() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> validator.getCurrentWeatherValidator("test", "", null, null));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void getCurrentWeatherValidatorCityOkCountryOk() {
        assertDoesNotThrow(() -> validator.getCurrentWeatherValidator("test", "test", null, null));
    }

    @Test
    public void getCurrentWeatherValidatorLonNotDoubleNoLat() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> validator.getCurrentWeatherValidator(null, null, "test", null));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void getCurrentWeatherValidatorLonNotDoubleOkLat() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> validator.getCurrentWeatherValidator(null, null, "test", "123.456"));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void getCurrentWeatherValidatorLonOkNoLat() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> validator.getCurrentWeatherValidator(null, null, "123.123", null));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void getCurrentWeatherValidatorLonOkLatOk() {
        assertDoesNotThrow(() -> validator.getCurrentWeatherValidator(null, null, "123.123","456.789"));
    }

    @Test
    public void getCurrentWeatherValidatorNoLonLatOk() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> validator.getCurrentWeatherValidator(null, null, null, "123.123"));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void getCurrentWeatherValidatorNoLonLatNotDouble() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> validator.getCurrentWeatherValidator(null, null, null, "test"));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void getCurrentWeatherValidatorLonOkLatNotDouble() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> validator.getCurrentWeatherValidator(null, null, "123.123", "test"));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void getCurrentWeatherValidatorLonOkLatOkCountryEmpty() {
        assertDoesNotThrow(() -> validator.getCurrentWeatherValidator(null, "", "123.123","456.789"));
    }

    @Test
    public void getCurrentWeatherValidatorLonOkLatOkCountryOk() {
        assertDoesNotThrow(() -> validator.getCurrentWeatherValidator(null, "", "123.123","456.789"));
    }
}