package io.yamyamiya.telegram.bot.service;

import io.yamyamiya.telegram.bot.dto.Location;
import io.yamyamiya.telegram.bot.entity.City;
import io.yamyamiya.telegram.bot.entity.User;

import java.util.Collection;
import java.util.List;

/**
 * interface for implementation of service methods working with {@link io.yamyamiya.telegram.bot.repository.CityRepository}
 */
public interface CityService {
    /**
     * method for receiving all cities provided by CityRepo
     * @return List<City>
     */
    List<City> getAll();

    /**
     * method for receiving certain city by its id
     * @param id of the city
     * @return City
     */
    City getById(int id);

    /**
     * method for receiving all cities for certain user by his ChatId
     * @param chatId of user
     * @return Collection<City>
     */
    Collection<City> getAllCitiesForThisUserByChatId(long chatId);

    /**
     * method for addition of City by its location
     * @param location
     * @return City
     */
    City add(Location location);

    /**
     * method for addition of City using user
     * @param city given city
     * @param user given user
     * @return City (found or added)
     */
    City add(City city, User user);

    /**
     * method for deletion of city by its id
     * @param id of the city
     */
    void deleteById(int id);

    /**
     * method for deletion of city by its name
     * @param name of the city
     */
    void deleteByName(String name);

    /**
     * method for counting the total number of cities in cityRepo
     * @return number of cities
     */
    int getCount();

    /**
     * method for counting the number of users, having the same cityId in their DB
     * provides the useful statistic of getting the most popular city
     * @param id of the city
     * @return number of users
     */

    int getUserCountByCityId(int id);

    /**
     * method for counting the number of cities for one specific user
     * provides the useful statistic of frequency of requests with different cities for each user
     * @param id of the user
     * @return number of cities
     */
    int getCityCountByUserId(int id);
}
