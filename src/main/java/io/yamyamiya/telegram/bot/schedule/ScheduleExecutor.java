package io.yamyamiya.telegram.bot.schedule;

import io.yamyamiya.telegram.bot.TelegramBot;
import io.yamyamiya.telegram.bot.dto.Forecast;
import io.yamyamiya.telegram.bot.entity.City;
import io.yamyamiya.telegram.bot.entity.ScheduledForecastTask;
import io.yamyamiya.telegram.bot.repository.TaskRepository;
import io.yamyamiya.telegram.bot.service.CityService;
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

@Component
@EnableScheduling
public class ScheduleExecutor {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CityService cityService;

    @Autowired
    private WeatherForecast weatherForecast;

    private static Logger logger = LoggerFactory.getLogger(ScheduleExecutor.class);

    public void taskSchedulerTaskWithTrigger(ScheduledForecastTask task, TelegramBot bot) {
        TaskScheduler scheduler = new DefaultManagedTaskScheduler();
        scheduler.schedule(() -> {
                    City city = cityService.getById(task.getCityId());
                    if (city != null) {
                        Result<Forecast> forecastResult = weatherForecast.forecast(city);

                        if (forecastResult instanceof Result.Success<Forecast>) {
                            Forecast forecast = ((Result.Success<Forecast>) forecastResult).getValue();
                            SendMessage sendMessage = SendMessage.builder()
                                    .chatId(task.getChatId())
                                    .parseMode("HTML")
                                    .text(String.format(String.format("On %s temperature in %s is %.2f °C. %s. \n", forecast.getDate(), city.getName(), forecast.getTemperature().getValue(), forecast.getDescription())))
                                    .build();

                            bot.sendMessage(sendMessage);
                        }
                    }
                },
                new CronTrigger("0 12 * * * *"));
    }

}
