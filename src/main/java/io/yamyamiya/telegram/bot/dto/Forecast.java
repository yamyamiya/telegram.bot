package io.yamyamiya.telegram.bot.dto;

/**
 * DTO for Forecast
 * result of execution WeatherForecast.locate() method
 * {@link io.yamyamiya.telegram.bot.service.weather.WeatherForecast}
 */
public class Forecast {
    /**
     * time of the provided forecast
     */
private final String date;
    /**
     * provided temperature
     */
    private final Temperature temperature;
    /**
     * forecast description. Example:"Sunny, no clouds."
     */
    private final String description;

    public Forecast(String date, Temperature temperature, String description) {
        this.date = date;
        this.temperature = temperature;
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public String getDescription() {
        return description;
    }
}
