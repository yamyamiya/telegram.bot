package io.yamyamiya.telegram.bot.weatherAPI;

import io.yamyamiya.telegram.bot.dto.Forecast;
import io.yamyamiya.telegram.bot.dto.Location;
import io.yamyamiya.telegram.bot.dto.Temperature;
import io.yamyamiya.telegram.bot.entity.City;
import io.yamyamiya.telegram.bot.service.weather.WeatherForecast;
import io.yamyamiya.telegram.bot.utils.Result;
import io.yamyamiya.telegram.bot.weatherAPI.JSON.WeatherbitForecastResponse;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;
/**
 * Class WeatherAPI using restTemplate and environment parameters,
 * implements methods of interface {@link WeatherForecast},
 * provides information about forecast from received location using any Forecast service
 */
public class WeatherAPI implements WeatherForecast {

    private final RestTemplate restTemplate;
    private final Environment environment;

    public WeatherAPI(RestTemplate restTemplate, Environment environment) {
        this.restTemplate = restTemplate;
        this.environment = environment;
    }

    /**
     * provides forecast from given location by using weatherbit API
     * @param location - location of the city
     * @return {@link Result<Forecast>}(Success or Failure)
     */
    @Override
    public Result<Forecast> forecast(Location location) {
        String apiKey = environment.getProperty("weather.api_key");
        String request = String.format(Locale.ENGLISH, "https://api.weatherbit.io/v2.0/current?lat=%f&lon=%f&key=%s&include=minutely\n", location.getLatitude(), location.getLongitude(), apiKey);
        ResponseEntity<WeatherbitForecastResponse> response = restTemplate.getForEntity(request, WeatherbitForecastResponse.class);

        if(response.getStatusCode().is2xxSuccessful()){

            String date = response.getBody().getData().get(0).getObTime();
            Double temp = response.getBody().getData().get(0).getTemp();
            String description = response.getBody().getData().get(0).getWeather().getDescription();
            Forecast forecast = new Forecast(date, new Temperature(temp), description);

            return new Result.Success<>(forecast);
        }
        return new Result.Failure<>();
    }

    /**
     * provides forecast for given city by using weatherbit API
     * implementation uses forecast(location) method
     * @param city - the city for forecast
     * @return {@link Result<Forecast>}(Success or Failure)
     */
    @Override
    public Result<Forecast> forecast(City city) {
        Location location = new Location(city.getLatitude(), city.getLongitude(), city.getName());
        return forecast(location);
    }
}
