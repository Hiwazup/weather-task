package com.nevilm.weather.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Location container object to match the output from the API used.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationContainer {
    private List<Location> data;

    public LocationContainer() {
    }

    public LocationContainer(List<Location> weatherList) {
        this.data = weatherList;
    }

    public List<Location> getData() {
        return data;
    }

    public void setData(List<Location> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Data{" +
                "locationList=" + data +
                '}';
    }
}
