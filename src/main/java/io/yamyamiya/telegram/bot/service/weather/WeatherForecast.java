package io.yamyamiya.telegram.bot.service.weather;

import io.yamyamiya.telegram.bot.dto.Forecast;
import io.yamyamiya.telegram.bot.dto.Location;
import io.yamyamiya.telegram.bot.utils.Result;

public interface WeatherForecast {
    Result<Forecast> forecast(Location location);

}
