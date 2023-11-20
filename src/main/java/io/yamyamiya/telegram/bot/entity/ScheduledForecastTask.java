package io.yamyamiya.telegram.bot.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
/**
 * class ScheduledForecastTask represent the ScheduledForecastTask entity, which is an element in {@link io.yamyamiya.telegram.bot.repository.TaskRepository}
 * contains id, description, executedAt, chatId, cityId parameters.
 * Objects of this class with corresponding properties represent table "task" in DB.
 * @see io.yamyamiya.telegram.bot.schedule.ScheduleExecutor
 */
@Entity
@Table(name="task")
public class ScheduledForecastTask {
    /**
     * id of the task
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    /**
     * task description
     */
    @Column(name="description")
    private String description;
    /**
     * time of task addition
     */
    @Column(name="executed_at")
    private Timestamp executedAt;
    /**
     * chatId (provided by TelegramBot)
     */
    @Column(name = "chat_id")
    private long chatId;
    /**
     * cityId from {@link io.yamyamiya.telegram.bot.repository.CityRepository}
     */
    @Column(name = "city_id")
    private int cityId;

    public ScheduledForecastTask() {
        executedAt = new Timestamp(System.currentTimeMillis());
    }

    public ScheduledForecastTask(String description, long chatId, int cityId) {
        this.description = description;
        this.chatId = chatId;
        this.cityId = cityId;
        executedAt = new Timestamp(System.currentTimeMillis());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getExecutedAt() {
        return executedAt;
    }

    public void setExecutedAt(Timestamp executedAt) {
        this.executedAt = executedAt;
    }

    public long getChatId() {
        return chatId;
    }

    public int getCityId() {
        return cityId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", executedAt=" + executedAt +
                '}';
    }
}
