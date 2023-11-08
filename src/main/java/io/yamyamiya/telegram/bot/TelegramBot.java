package io.yamyamiya.telegram.bot;


import io.yamyamiya.telegram.bot.dto.Forecast;
import io.yamyamiya.telegram.bot.dto.Location;
import io.yamyamiya.telegram.bot.entity.User;
import io.yamyamiya.telegram.bot.service.CityService;
import io.yamyamiya.telegram.bot.service.UserService;
import io.yamyamiya.telegram.bot.service.location.Locator;
import io.yamyamiya.telegram.bot.service.weather.WeatherForecast;
import io.yamyamiya.telegram.bot.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    @Autowired
    private Locator locator;

    @Autowired
    private WeatherForecast weatherForecast;

    @Autowired
    private UserService userService;

    @Autowired
    private CityService cityService;

    private final Environment env;

    public TelegramBot(Environment env) {
        super(env.getProperty("telegram.token"));
        this.env = env;
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println();
        String message = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        Long userId = update.getMessage().getFrom().getId();
//        update.getMessage().getDate()
        String firstName = update.getMessage().getFrom().getFirstName();
        String lastName = update.getMessage().getFrom().getLastName();
        User user = new User(0, firstName, "$2a$10$NjNAiH6U9mSVC3IHeP85je1HFGGBII699yI7rKVAu9dHnpeNXioZC",chatId, null);

        userService.add(user);

        Result<Location> locatorResult = locator.locate(message);

        SendMessage sendMessage;

        if (locatorResult instanceof Result.Success<Location>) {

            Location location = ((Result.Success<Location>) locatorResult).getValue();
            cityService.add(location, user);

            Result<Forecast>  forecastResponse= weatherForecast.forecast(location);


            if (forecastResponse instanceof Result.Success<Forecast>) {

                Forecast forecast = ((Result.Success<Forecast>) forecastResponse).getValue();
                sendMessage = SendMessage.builder()
                        .chatId(chatId)
                        .parseMode("HTML")
                        .text(String.format("On %s temperature in %s is %.2f °C. %s. \n", forecast.getDate(),location.getCity(), forecast.getTemperature().getValue(), forecast.getDescription()))
                        .build();
            } else {
                sendMessage = SendMessage.builder()
                        .chatId(chatId)
                        .parseMode("HTML")
                        .text(String.format("Couldn't find forecast for the city %s. \n", location.getCity()))
                        .build();
            }
        } else {
            sendMessage = SendMessage.builder()
                    .chatId(chatId)
                    .text(String.format("Couldn't find location in '%s'. \n", message))
                    .build();
        }

        try {
            sendApiMethod(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }




    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    @Override
    public String getBotUsername() {
        return env.getProperty("telegram.name");
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }
}
