package io.yamyamiya.telegram.bot.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="task")
public class ScheduledForecastTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="description")
    private String description;

    @Column(name="executed_at")
    private Timestamp executedAt;

    @Column(name = "chat_id")
    private long chatId;

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
