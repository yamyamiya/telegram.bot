package io.yamyamiya.telegram.bot.repository;

import io.yamyamiya.telegram.bot.entity.City;
import io.yamyamiya.telegram.bot.entity.ScheduledForecastTask;
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
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CityRepository cityRepository;

    @Test
    void shouldReturnCorrectNumberOfSubscriptionsToTheCity() {
        City city = cityRepository.save(new City(0, "TestCity", 1.36420, 1.1900));
        User user = userRepository.save(new User(0, "TestUser", "test", 5567, null));
        city.getUsersForCities().add(user);
        taskRepository.save(new ScheduledForecastTask("Test task", user.getChatId(), city.getId()));
        assertEquals(1, taskRepository.getSubscriptionForCity(user.getChatId(), city.getId()));
    }



}