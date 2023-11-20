package io.yamyamiya.telegram.bot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;
import java.util.Objects;
/**
 * class Message represent the Message entity, which is an element in {@link io.yamyamiya.telegram.bot.repository.MessageRepository}
 * contains id of message, its content, chatId of TelegramBot, userId of TelegramBot, createdAt  parameters.
 * Objects of this class in DB represent table "message" , linked with "users" (using FK chat_id),
 */
@Entity
@Table(name="message")
public class Message {
    /**
     * id of message
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    /**
     * content of message
     */
    @Column(name="content")
    @NotBlank
    private String content;
    /**
     * chatId of message (provided by TelegramBot from which this message was sent)
     */
    @Column(name = "chat_id")
    private long chatId;
    /**
     * id of user who is the author of the message (provided by TelegramBot)
     */
    @Column(name = "user_id")
    private long userId;
    /**
     * time of creation
     */
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
