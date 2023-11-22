package io.yamyamiya.telegram.bot.repository;

import io.yamyamiya.telegram.bot.entity.ScheduledForecastTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
/**
 * TaskRepository interface extends {@link JpaRepository<ScheduledForecastTask, Integer>}.
 * contains objects of {@link ScheduledForecastTask class}
 * linked with table "task" in DB
 */
public interface TaskRepository extends JpaRepository<ScheduledForecastTask, Integer> {
    /**
     * method to determine if user has already been subscribed for certain city
     * @param chatId user's chatId
     * @param cityId id of the city from DB
     * @return number of subscriptions of user for certain city
     */
    @Query(value = "SELECT count(*) FROM task WHERE chat_id=:chatId AND city_id= :cityId", nativeQuery = true)
    int getSubscriptionForCity(long chatId, int cityId);

}
