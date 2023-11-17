package io.yamyamiya.telegram.bot.weatherAPI;

import io.yamyamiya.telegram.bot.dto.Forecast;
import io.yamyamiya.telegram.bot.dto.Location;
import io.yamyamiya.telegram.bot.utils.Result;
import io.yamyamiya.telegram.bot.weatherAPI.JSON.Datum;
import io.yamyamiya.telegram.bot.weatherAPI.JSON.Weather;
import io.yamyamiya.telegram.bot.weatherAPI.JSON.WeatherbitForecastResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WeatherAPITest {

    private static final String MOCK_API_KEY = "Mock API key";
    RestTemplate restTemplate;
    Environment environment;
    WeatherAPI weatherAPI;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        environment = mock(Environment.class);
        weatherAPI = new WeatherAPI(restTemplate, environment);
        when(environment.getProperty("weather.api_key")).thenReturn(MOCK_API_KEY);
    }

    @Test
    void shouldProperlyHandleSuccessfulResponse() {
        Location location = new Location(0, 0, "City");
        String request = String.format(Locale.ENGLISH, "https://api.weatherbit.io/v2.0/current?lat=%f&lon=%f&key=%s&include=minutely\n", location.getLatitude(), location.getLongitude(), MOCK_API_KEY);
        WeatherbitForecastResponse forecastResponse = new WeatherbitForecastResponse();
        Datum datum = new Datum();
        datum.setObTime("07-01-1986T18:00");
        datum.setTemp(-25.0);
        Weather weather = new Weather();
        weather.setDescription("Sunny");
        datum.setWeather(weather);
        forecastResponse.setData(List.of(datum));
        ResponseEntity<WeatherbitForecastResponse> successResponse = new ResponseEntity<>(forecastResponse, HttpStatusCode.valueOf(200));

        when(restTemplate.getForEntity(request, WeatherbitForecastResponse.class)).thenReturn(successResponse);

        Result<Forecast> response = weatherAPI.forecast(location);

        assertTrue(response instanceof Result.Success<Forecast>);

        Forecast forecast = ((Result.Success<Forecast>) response).getValue();
        assertEquals("07-01-1986T18:00", forecast.getDate());
        assertEquals(-25, forecast.getTemperature().getValue());
        assertEquals("Sunny", forecast.getDescription());
    }

    @Test
    void shouldProperlyHandleUnsuccessfulResponse() {
        Location location = new Location(0, 0, "City");
        String request = String.format(Locale.ENGLISH, "https://api.weatherbit.io/v2.0/current?lat=%f&lon=%f&key=%s&include=minutely\n", location.getLatitude(), location.getLongitude(), MOCK_API_KEY);
        WeatherbitForecastResponse forecastResponse = new WeatherbitForecastResponse();

        ResponseEntity<WeatherbitForecastResponse> unsuccessfulResponse = new ResponseEntity<>(forecastResponse, HttpStatusCode.valueOf(400));

        when(restTemplate.getForEntity(request, WeatherbitForecastResponse.class)).thenReturn(unsuccessfulResponse);

        Result<Forecast> response = weatherAPI.forecast(location);

        assertTrue(response instanceof Result.Failure<Forecast>);
    }
}
