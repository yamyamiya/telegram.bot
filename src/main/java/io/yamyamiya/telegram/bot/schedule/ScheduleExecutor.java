package io.yamyamiya.telegram.bot.schedule;

import io.yamyamiya.telegram.bot.TelegramBot;
import io.yamyamiya.telegram.bot.dto.Forecast;
import io.yamyamiya.telegram.bot.entity.City;
import io.yamyamiya.telegram.bot.entity.ScheduledForecastTask;
import io.yamyamiya.telegram.bot.service.CityService;
import io.yamyamiya.telegram.bot.service.TaskService;
import io.yamyamiya.telegram.bot.service.weather.WeatherForecast;
import io.yamyamiya.telegram.bot.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Component
@EnableScheduling
public class ScheduleExecutor {

    @Autowired
    private TaskService taskService;

    @Autowired
    private CityService cityService;

    @Autowired
    private WeatherForecast weatherForecast;

    private Map<String, ScheduledFuture<?>> futures = new HashMap<>();

    private static Logger logger = LoggerFactory.getLogger(ScheduleExecutor.class);

    public void taskSchedulerTaskWithTrigger(ScheduledForecastTask task, TelegramBot bot) {
        TaskScheduler scheduler = new DefaultManagedTaskScheduler();
        String scheduledFutureKey = String.format("%d %d", task.getChatId(), task.getCityId());

        ScheduledFuture<?> scheduledFuture = scheduler.schedule(() -> {
                    if (taskService.getSubscriptionForCity(task.getChatId(), task.getCityId()) == 0) {
                        ScheduledFuture<?> futureToCancel = futures.get(scheduledFutureKey);
                        if (futureToCancel != null) {
                            futures.remove(scheduledFutureKey);
                            futureToCancel.cancel(false);
                        }
                        return;
                    }
                    City city = cityService.getById(task.getCityId());
                    if (city != null) {
                        Result<Forecast> forecastResult = weatherForecast.forecast(city);

                        if (forecastResult instanceof Result.Success<Forecast>) {
                            Forecast forecast = ((Result.Success<Forecast>) forecastResult).getValue();

                            InlineKeyboardMarkup unSubscriptionKeybord = new InlineKeyboardMarkup();
                            List<InlineKeyboardButton> keyboard = new ArrayList<>();
                            InlineKeyboardButton yesButton = new InlineKeyboardButton("Unsubscribe?");
                            yesButton.setCallbackData(String.valueOf(task.getId()));
                            List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();
                            keyboard.add(yesButton);
                            keyboardRows.add(keyboard);
                            unSubscriptionKeybord.setKeyboard(keyboardRows);

                            SendMessage sendMessage = SendMessage.builder()
                                    .chatId(task.getChatId())
                                    .parseMode("HTML")
                                    .replyMarkup(unSubscriptionKeybord)
                                    .text(String.format("On %s temperature in %s is %.2f °C. %s. \n", forecast.getDate(), city.getName(), forecast.getTemperature().getValue(), forecast.getDescription()))
                                    .build();

                            bot.sendMessage(sendMessage);
                        }
                    }
                },
                new CronTrigger("0 0 12 * * *"));
        futures.put(scheduledFutureKey, scheduledFuture);
    }

}
