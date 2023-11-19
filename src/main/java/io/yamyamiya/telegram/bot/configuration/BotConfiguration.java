package io.yamyamiya.telegram.bot.configuration;

import com.theokanning.openai.service.OpenAiService;
import io.yamyamiya.telegram.bot.openAI.OpenAILocator;
import io.yamyamiya.telegram.bot.service.JpaUserService;
import io.yamyamiya.telegram.bot.service.UserService;
import io.yamyamiya.telegram.bot.service.location.Locator;
import io.yamyamiya.telegram.bot.service.weather.WeatherForecast;
import io.yamyamiya.telegram.bot.weatherAPI.WeatherAPI;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Configuration
public class BotConfiguration {
    @Bean
    public OpenAiService openAiService(Environment environment) {
        return new OpenAiService(Objects.requireNonNull(environment.getProperty("openai.token")));
    }
    @Bean
    public Locator openAILocator(OpenAiService service){
        return new OpenAILocator(service);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    WeatherForecast weatherForecast(RestTemplate restTemplate, Environment environment) {
        return new WeatherAPI(restTemplate, environment);
    }



}
