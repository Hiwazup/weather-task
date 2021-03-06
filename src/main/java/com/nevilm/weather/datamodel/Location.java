package com.nevilm.weather.datamodel;

import com.fasterxml.jackson.annotation.*;

import java.util.Objects;

/**
 * Object which holds all information relating to a location.
 * @author Martin Neville
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
    @JsonProperty("city")
    @JsonAlias({"city_name"})
    private String city_name;

    @JsonProperty("country")
    @JsonAlias({"country_code"})
    private String country_code;

    @JsonProperty("latitude")
    @JsonAlias({"lat"})
    private String latitude;

    @JsonProperty("longitude")
    @JsonAlias({"lon"})
    private String longitude;

    @JsonProperty("sunrise")
    @JsonAlias({"sunrise"})
    private String sunrise;

    @JsonProperty("sunset")
    @JsonAlias({"sunset"})
    private String sunset;

    @JsonProperty("temperature")
    @JsonAlias({"temp"})
    private Double temp;

    @JsonProperty("weather")
    @JsonAlias({"weather"})
    private Weather weather;

    public Location() {
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    @Override
    public String toString() {
        return "Location{" +
                "city_name='" + city_name + '\'' +
                ", country_code='" + country_code + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", sunrise='" + sunrise + '\'' +
                ", sunset='" + sunset + '\'' +
                ", temp=" + temp +
                ", weather=" + weather +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(city_name, location.city_name) && Objects.equals(country_code, location.country_code) && Objects.equals(latitude, location.latitude) && Objects.equals(longitude, location.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city_name, country_code, latitude, longitude);
    }
}