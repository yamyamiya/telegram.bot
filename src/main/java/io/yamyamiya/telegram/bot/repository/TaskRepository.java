package io.yamyamiya.telegram.bot.repository;

import io.yamyamiya.telegram.bot.entity.ScheduledForecastTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<ScheduledForecastTask, Integer> {
}
