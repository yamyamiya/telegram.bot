package io.yamyamiya.telegram.bot.service;

import io.yamyamiya.telegram.bot.entity.ScheduledForecastTask;
/**
 * interface for implementation of service methods working with {@link io.yamyamiya.telegram.bot.repository.TaskRepository}
 */
public interface TaskService {

    /**
     * method to determine if user has already been subscribed for certain city
     * @param chatId user's chatId
     * @param cityId id of the city from DB
     * @return number of subscriptions of user for certain city
     */
    int getSubscriptionForCity(long chatId, int cityId);

    /**
     * method for task addition
     * @param task to add
     */
    void add(ScheduledForecastTask task);

    /**
     * method for task deletion
     * @param id of task to be deleted
     */
    void deleteById(int id);

}
