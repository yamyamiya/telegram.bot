package io.yamyamiya.telegram.bot.service.weather;

import io.yamyamiya.telegram.bot.dto.Forecast;
import io.yamyamiya.telegram.bot.dto.Location;
import io.yamyamiya.telegram.bot.entity.City;
import io.yamyamiya.telegram.bot.utils.Result;
/**
 * Interface providing the method forecast
 * @see io.yamyamiya.telegram.bot.weatherAPI.WeatherAPI
 */
public interface WeatherForecast {
    /**
     * method for providing forecast from the received location
     * @param location - location of the city
     * @return Result<Forecast> (Success or Failure)
     */
    Result<Forecast> forecast(Location location);
    /**
     * method for providing forecast from the received city
     * @param city - the city for forecast
     * @return Result<Forecast> (Success or Failure)
     */
    Result<Forecast> forecast(City city);

}
