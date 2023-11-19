package io.yamyamiya.telegram.bot.service;

import io.yamyamiya.telegram.bot.dto.Location;
import io.yamyamiya.telegram.bot.entity.City;
import io.yamyamiya.telegram.bot.entity.User;
import io.yamyamiya.telegram.bot.repository.CityRepository;
import io.yamyamiya.telegram.bot.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class JpaCityServiceTest {

    private UserRepository userRepository;
    private CityRepository cityRepository;

    private JpaCityService cityService;

    @BeforeEach
    void init() {
        userRepository = Mockito.mock();
        cityRepository = Mockito.mock();
        cityService = new JpaCityService(cityRepository, userRepository);

    }

    @Test
    void shouldReturnAllCities() {
        when(cityRepository.findAll()).thenReturn(List.of(new City(0, "TestCityOne", 1.36420, 1.1900), new City(0, "TestCityTwo", 2.36420, 2.1900)));

        List<City> citiesList = cityService.getAll();
        assertEquals(2, citiesList.size());

        List<String> cityNames = citiesList.stream().map(city -> city.getName()).toList();

        assertTrue(cityNames.contains("TestCityOne"));
        assertTrue(cityNames.contains("TestCityTwo"));
    }

    @Test
    void shouldReturnCorrectCityById() {
        when(cityRepository.findById(1)).thenReturn(Optional.of(new City(1, "TestCityOne", 1.36420, 1.1900)));
        assertEquals(1, cityService.getById(1).getId());
    }
    @Test
    void shouldGetCorrectCitiesForThisUserByChatId(){
        User testUser = new User(0, "TestUser");
        testUser.setCities(Set.of(new City(1, "TestCityOne", 1.36420, 1.1900), new City(2, "TestCityTwo", 2.36420, 2.1900)));
        when(userRepository.findByChatId(45566)).thenReturn(testUser);
        Collection<City> cities = cityService.getAllCitiesForThisUserByChatId(45566);
        assertNotNull(cities);
        assertEquals(2, cities.size());

        List<String> cityNames = cities.stream().map(City::getName).toList();

        assertTrue(cityNames.contains("TestCityOne"));
        assertTrue(cityNames.contains("TestCityTwo"));
    }

    @Test
    void shouldCreateNewCity(){
        when(cityRepository.findByName("TestCity")).thenReturn(null);
        cityService.add(new Location(1.0000,2.0000,"TestCity"));

        ArgumentCaptor<City> cityCaptor = ArgumentCaptor.forClass(City.class);
        verify(cityRepository).save(cityCaptor.capture());
        assertEquals("TestCity", cityCaptor.getValue().getName());
    }
    @Test
    void shouldReturnExistingCity(){
        when(cityRepository.findByName("TestCity")).thenReturn(new City(1, "TestCity",1.0000, 2.0000));
        City city = cityService.add(new Location(1.0000,2.0000,"TestCity"));
        assertEquals("TestCity", city.getName());
    }

    @Test
    void shouldReturnExistingCityForUser(){
        City city = new City(0, "TestCity",1.0000, 2.0000);
        User user = new User(0, "TestUser");
        city.getUsersForCities().add(user);
        assertEquals("TestCity", cityService.add(city, user).getName());
    }

    @Test
    void shouldAddNewUserForThisCity(){
        City city = new City(0, "TestCity",1.0000, 2.0000);
        User user = new User(0, "TestUser");
        ArgumentCaptor<City> cityCaptor = ArgumentCaptor.forClass(City.class);
        cityService.add(city, user);
        verify(cityRepository).save(cityCaptor.capture());
        assertTrue(cityCaptor.getValue().getUsersForCities().contains(user));
    }

    @Test
    void shouldDeleteById() {
        cityService.deleteById(2);
        verify(cityRepository).deleteById(2);
    }

    @Test
    void shouldDeleteByName() {
        cityService.deleteByName("TestCity");
        verify(cityRepository).deleteByName("TestCity");
    }

    @Test
    void shouldReturnCorrectNumberOfCities() {
        when(cityRepository.count()).thenReturn(3L);
        assertEquals(3, cityService.getCount());
    }

    @Test
    void shouldReturnCorrectNumberOfUsersByCityId(){
        when(cityRepository.getUserCountByCityId(1)).thenReturn(3);
        assertEquals(3, cityService.getUserCountByCityId(1));
    }

    @Test
    void shouldReturnCorrectNumberOfCitiesByUserId(){
        when(cityRepository.getCityCountByUserId(3)).thenReturn(1);
        assertEquals(1, cityService.getCityCountByUserId(3));
    }
}