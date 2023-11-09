package io.yamyamiya.telegram.bot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name="content")
    @NotBlank
    private String content;

    @Column(name = "chat_id")
    private long chatId;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "created_at")
    @NotBlank
    private Date createdAt;

    public Message() {
    }

    public Message(int id, String content, long chatId, long userId, Date createdAt) {
        this.id = id;
        this.content = content;
        this.chatId = chatId;
        this.userId = userId;
        this.createdAt = Objects.requireNonNullElseGet(createdAt, Date::new);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", chatId=" + chatId +
                ", userId=" + userId +
                ", createdAt=" + createdAt +
                '}';
    }
}
