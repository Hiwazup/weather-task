package com.nevilm.weather.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Weather object to match the response from the API.
 * @author Martin Neville
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {
    private String description;

    public Weather() {
    }

    public Weather(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "description='" + description + '\'' +
                '}';
    }
}
