package io.yamyamiya.telegram.bot.repository;

import io.yamyamiya.telegram.bot.entity.Message;
import io.yamyamiya.telegram.bot.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.text.ParseException;
import java.text.SimpleDateFormat;


import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    String pattern = "yyyy-MM-dd HH:mm:ss";
    SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
    Message message1;
    Message message2;
    Message message3;

    @BeforeEach
    void init() throws ParseException{
        message1 = new Message(0, "Message 1", 5567, 5567, dateFormat.parse("2023-11-18 00:00:00"));
        message2 = new Message(0, "Message 1", 5567, 5567, dateFormat.parse("2023-12-18 00:00:00"));
        message3 = new Message(0, "Message 1", 5567, 5567, dateFormat.parse("2023-12-31 00:01:00"));
        userRepository.save(new User(0,"TestUser", "test", 5567, null));
    }

    @Test
    void shouldReturnCorrectCountByChatId() {
        assertEquals(0, messageRepository.getCountByChatId(5567));
        messageRepository.save(message1);
        messageRepository.save(message2);
        messageRepository.save(message3);
        assertEquals(3, messageRepository.getCountByChatId(5567));
    }

    @Test
    void shouldReturnCorrectCountAfterSomeDate() throws ParseException{
        messageRepository.save(message1);
        messageRepository.save(message2);
        messageRepository.save(message3);
        assertEquals(2, messageRepository.getCountByDateAfter(dateFormat.parse("2023-11-18 00:01:00")));
    }

    @Test
    void shouldReturnCorrectCountBeforeSomeDate() throws ParseException{
        messageRepository.save(message1);
        messageRepository.save(message2);
        messageRepository.save(message3);
        assertEquals(1, messageRepository.getCountByDateBefore(dateFormat.parse("2023-11-19 00:00:00")));
    }

    @Test
    void shouldReturnCorrectCountBetweenSomeDates() throws ParseException{
        messageRepository.save(message1);
        messageRepository.save(message2);
        messageRepository.save(message3);
        assertEquals(2, messageRepository.getCountByDateBetween(dateFormat.parse("2023-11-17 00:00:00"), dateFormat.parse("2023-12-30 00:00:00")));
    }


}