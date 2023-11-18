package io.yamyamiya.telegram.bot.service;

import io.yamyamiya.telegram.bot.entity.ScheduledForecastTask;
import io.yamyamiya.telegram.bot.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JpaTaskService implements TaskService{
    @Autowired
    private TaskRepository taskRepository;

    /**
     * method is used in {@link io.yamyamiya.telegram.bot.TelegramBot} to check if user has subscription for certain city
     * @param chatId
     * @param cityId
     * @return 1 if user has subscription and 0 if not
     */
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
