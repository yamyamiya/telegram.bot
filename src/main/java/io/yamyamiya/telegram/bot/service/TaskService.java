package io.yamyamiya.telegram.bot.service;

import io.yamyamiya.telegram.bot.entity.ScheduledForecastTask;

public interface TaskService {
    int getSubscriptionForCity(long chatId, int cityId);

    void add(ScheduledForecastTask task);

}
