package io.yamyamiya.telegram.bot;


import io.yamyamiya.telegram.bot.dto.Forecast;
import io.yamyamiya.telegram.bot.dto.Location;
import io.yamyamiya.telegram.bot.entity.City;
import io.yamyamiya.telegram.bot.entity.Message;
import io.yamyamiya.telegram.bot.entity.ScheduledForecastTask;
import io.yamyamiya.telegram.bot.entity.User;
import io.yamyamiya.telegram.bot.schedule.ScheduleExecutor;
import io.yamyamiya.telegram.bot.service.CityService;
import io.yamyamiya.telegram.bot.service.MessageService;
import io.yamyamiya.telegram.bot.service.TaskService;
import io.yamyamiya.telegram.bot.service.UserService;
import io.yamyamiya.telegram.bot.service.location.Locator;
import io.yamyamiya.telegram.bot.service.password.Password;
import io.yamyamiya.telegram.bot.service.password.RandomPasswordGenerator;
import io.yamyamiya.telegram.bot.service.weather.WeatherForecast;
import io.yamyamiya.telegram.bot.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
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

    @Autowired
    private MessageService messageService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ScheduleExecutor executor;

    private final Environment env;

    public TelegramBot(Environment env) {
        super(env.getProperty("telegram.token"));
        this.env = env;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasCallbackQuery()) {
            SendMessage sendMessage;
            String data = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            if(update.getCallbackQuery().getMessage().getReplyMarkup().getKeyboard().get(0).get(0).getText().equals("Unsubscribe?")){
                taskService.deleteById(Integer.parseInt(update.getCallbackQuery().getData()));
                sendMessage = SendMessage.builder()
                        .chatId(chatId)
                        .parseMode("HTML")
                        .text("You have successfully unsubscribed. \n")
                        .build();
                sendMessage(sendMessage);
                return;
            }


            int cityIndex = Integer.parseInt(data);
            City city = cityService.getById(cityIndex);
            if (taskService.getSubscriptionForCity(chatId, cityIndex) == 0) {
                ScheduledForecastTask task = new ScheduledForecastTask(String.format("Task scheduled for daily forecast for %s \n", city.getName()), chatId, cityIndex);
                taskService.add(task);

                executor.taskSchedulerTaskWithTrigger(task, this);
                sendMessage = SendMessage.builder()
                        .chatId(chatId)
                        .parseMode("HTML")
                        .text("You have successfully subscribed. \n")
                        .build();
            } else {
                sendMessage = SendMessage.builder()
                        .chatId(chatId)
                        .parseMode("HTML")
                        .text(String.format("You have already successfully subscribed for %s. \n", city.getName()))
                        .build();
            }


            try {
                sendApiMethod(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        User user = extractUser(update);

        String message = update.getMessage().getText();

        messageService.add(new Message(0, message, user.getChatId(), user.getChatId(), null));


        Result<Location> locatorResult = locator.locate(message);
        SendMessage sendMessage;
        SendMessage subscriptionMessage = null;
        InlineKeyboardMarkup subscriptionKeybord = null;
        boolean sentSubscription = false;


        if (locatorResult instanceof Result.Success<Location>) {

            Location location = ((Result.Success<Location>) locatorResult).getValue();

            City city = cityService.add(location);
            if (city.getUsersForCities().contains(user) && taskService.getSubscriptionForCity(user.getChatId(), city.getId()) == 0) {
                sentSubscription = true;
            }

            cityService.add(city, user);

            Result<Forecast> forecastResponse = weatherForecast.forecast(location);


            if (forecastResponse instanceof Result.Success<Forecast>) {
                if (sentSubscription) {
                    subscriptionKeybord = new InlineKeyboardMarkup();
                    List<InlineKeyboardButton> keyboard = new ArrayList<>();
                    InlineKeyboardButton yesButton = new InlineKeyboardButton("Subscribe for daily forecast?");
                    yesButton.setCallbackData(String.valueOf(city.getId()));
                    List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();


                    keyboard.add(yesButton);

                    keyboardRows.add(keyboard);

                    subscriptionKeybord.setKeyboard(keyboardRows);
                }

                Forecast forecast = ((Result.Success<Forecast>) forecastResponse).getValue();
                sendMessage = SendMessage.builder()
                        .chatId(user.getChatId())
                        .parseMode("HTML")
                        .replyMarkup(subscriptionKeybord)
                        .text(String.format("On %s temperature in %s is %.2f °C. %s. \n", forecast.getDate(), location.getCity(), forecast.getTemperature().getValue(), forecast.getDescription()))
                        .build();

            } else {
                sendMessage = SendMessage.builder()
                        .chatId(user.getChatId())
                        .parseMode("HTML")
                        .text(String.format("Couldn't find forecast for the city %s. \n", location.getCity()))
                        .build();
            }
        } else {
            Exception exception = ((Result.Failure<?>) locatorResult).getException();
            if (exception != null) {
                sendMessage = SendMessage.builder()
                        .chatId(user.getChatId())
                        .text("Location service is not responding at the moment. Please repeat your request later.")
                        .build();
            } else {
                sendMessage = SendMessage.builder()
                        .chatId(user.getChatId())
                        .text(String.format("Couldn't find location in '%s'. \n", message))
                        .build();
            }
        }
        try {
            sendApiMethod(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(SendMessage sendMessage) {
        try {
            sendApiMethod(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private User extractUser(Update update) {
        User userFoundByChatId = userService.getByChatId(update.getMessage().getChatId());
        if (userFoundByChatId == null) {
            RandomPasswordGenerator randomPasswordGenerator = new RandomPasswordGenerator();
            Password password = randomPasswordGenerator.generatePassword();

            Long chatId = update.getMessage().getChatId();
            String firstName = update.getMessage().getFrom().getFirstName();

            User user = new User(0, firstName, password.getEncryptedPassword(), chatId, null);
            User savedUser = userService.add(user);

            SendMessage passwordAnswer = SendMessage.builder()
                    .chatId(user.getChatId())
                    .text(String.format("Your password is '%s'. \n", password.getRawPassword()))
                    .build();
            try {
                sendApiMethod(passwordAnswer);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
            return savedUser;
        } else {
            return userFoundByChatId;
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
