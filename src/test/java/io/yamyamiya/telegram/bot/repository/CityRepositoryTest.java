package io.yamyamiya.telegram.bot.repository;

import io.yamyamiya.telegram.bot.entity.City;
import io.yamyamiya.telegram.bot.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class for testing {@link CityRepository} methods,
 * depends on {@link UserRepository} and because embedded H2 Database used in test has USER keyword
 * it was necessary to create application-test.properties with additional configuration for H2.
 * AutoConfigurationTestDatabase used to prevent replacement of application-test.properties by default
 * test implementation.
 */
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class CityRepositoryTest {
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldReturnCorrectCityByName() {
        cityRepository.save(new City(0, "TestCity", 1.36420, 1.1900));
        assertEquals("TestCity", cityRepository.findByName("TestCity").getName());
    }

    @Test
    void shouldDeleteByName() {
        cityRepository.save(new City(0, "TestCity", 1.36420, 1.1900));
        assertEquals("TestCity", cityRepository.findByName("TestCity").getName());
        cityRepository.deleteByName("TestCity");
        assertNull(cityRepository.findByName("TestCity"));
    }


    @Test
    void shouldReturnCorrectCountOfUsers() {
        City city = cityRepository.save(new City(0, "TestCity", 1.36420, 1.1900));
        assertEquals(0, cityRepository.getUserCountByCityId(city.getId()));
        User user = new User(0,"TestUser", "test", 5567, null);
        user = userRepository.save(user);
        city.getUsersForCities().add(user);
        assertEquals(1, cityRepository.getUserCountByCityId(city.getId()));
    }

    @Test
    void shouldReturnCorrectCountOfCities() {
        City city1 = cityRepository.save(new City(0, "TestCity1", 1.36420, 1.1900));
        City city2 = cityRepository.save(new City(0, "TestCity2", 2.36420, 2.1900));
        User user = new User(0,"TestUser", "test", 5567, null);
        user = userRepository.save(user);
        city1.getUsersForCities().add(user);
        city2.getUsersForCities().add(user);
        assertEquals(2, cityRepository.getCityCountByUserId(user.getId()));
    }
}