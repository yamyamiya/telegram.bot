package io.yamyamiya.telegram.bot.dto;

public class Forecast {
private final String date;

private final Temperature temperature;

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
