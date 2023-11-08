package io.yamyamiya.telegram.bot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

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
    private int chatId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "created_at")
    @NotBlank
    private Date createdAt;
}
