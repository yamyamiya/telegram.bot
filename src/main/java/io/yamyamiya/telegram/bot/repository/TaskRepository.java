package io.yamyamiya.telegram.bot.repository;

import io.yamyamiya.telegram.bot.entity.ScheduledForecastTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<ScheduledForecastTask, Integer> {
    @Query(value = "SELECT count(*) FROM task WHERE chat_id=:chatId AND city_id= :cityId", nativeQuery = true)
    int getSubscriptionForCity(long chatId, int cityId);

}
