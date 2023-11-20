package io.yamyamiya.telegram.bot.service;

import io.yamyamiya.telegram.bot.entity.ScheduledForecastTask;
import io.yamyamiya.telegram.bot.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * JpaUserService class implements methods of {@link TaskService}
 * contains taskRepository {@link TaskRepository}
 */
@Service
public class JpaTaskService implements TaskService{
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public int getSubscriptionForCity(long chatId, int cityId) {
        return taskRepository.getSubscriptionForCity(chatId, cityId);
    }

    @Override
    public void add(ScheduledForecastTask task) {
        taskRepository.save(task);
    }

    @Override
    public void deleteById(int id) {
        taskRepository.deleteById(id);
    }
}
