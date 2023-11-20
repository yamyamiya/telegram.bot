package io.yamyamiya.telegram.bot.dto;

/**
 * DTO for Location, contains required parameters for correct receiving of the forecast
 * parameter of method forecast() {@link io.yamyamiya.telegram.bot.service.weather.WeatherForecast}
 * result of execution of locate() metod {@link io.yamyamiya.telegram.bot.service.location.Locator}
 */
public class Location {
    /**
     * latitude of the city
     */
    private double latitude;
    /**
     * longitude of the city
     */
    private double longitude;
    /**
     * name of the city
     */
    private String city;

    public Location(double latitude, double longitude, String city) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
    }

    public Location() {
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Location{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", city='" + city + '\'' +
                '}';
    }
}
