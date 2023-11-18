package io.yamyamiya.telegram.bot.repository;

import io.yamyamiya.telegram.bot.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldReturnCorrectUserByName() {
        userRepository.save(new User(0,"TestUser", "test", 5567, null));
        assertEquals("TestUser", userRepository.findByName("TestUser").getName());
    }

    @Test
    void shouldReturnCorrectUserByChatId() {
        userRepository.save(new User(0,"TestUser", "test", 5567, null));
        assertEquals("TestUser", userRepository.findByChatId(5567).getName());
    }

    @Test
    void shouldDeleteByName() {
        userRepository.save(new User(0,"TestUser", "test", 5567, null));
        assertEquals("TestUser", userRepository.findByChatId(5567).getName());
        userRepository.deleteByName("TestUser");
        assertNull(userRepository.findByName("TestUser"));
    }
}