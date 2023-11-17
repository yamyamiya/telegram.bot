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

public class WeatherAPI implements WeatherForecast {



    private final RestTemplate restTemplate;
    private final Environment environment;

    public WeatherAPI(RestTemplate restTemplate, Environment environment) {
        this.restTemplate = restTemplate;
        this.environment = environment;
    }

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

    @Override
    public Result<Forecast> forecast(City city) {
        Location location = new Location(city.getLatitude(), city.getLongitude(), city.getName());
        return forecast(location);
    }
}
