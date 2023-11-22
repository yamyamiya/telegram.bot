package io.yamyamiya.telegram.bot.repository;

import io.yamyamiya.telegram.bot.entity.City;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * CityRepository interface extends {@link JpaRepository<City, Integer>}.
 * contains objects of {@link City class}
 * linked with table "city" in DB
 */
public interface CityRepository extends JpaRepository<City, Integer> {
    /**
     * method for getting the city from CityRepository by the name
     * @param name of the city
     * @return City found by the name
     */
    City findByName(String name);

    /**
     * method for deletion of the city from CityRepository by the name
     * @param name of the city
     */
    @Transactional
    void deleteByName(String name);

    /**
     * method for counting the number of users, having the same cityId in their DB
     * provides the useful statistic of getting the most popular city
     * @param id of the city
     * @return number of users
     */

    @Query(value = "SELECT count(*) FROM user_city WHERE city_id= :id", nativeQuery = true)
    int getUserCountByCityId(int id);

    /**
     * method for counting the number of cities for one specific user
     * provides the useful statistic of frequency of requests with different cities for each user
     * @param id of the user
     * @return number of cities
     */

    @Query(value = "SELECT count(*) FROM user_city WHERE user_id= :id", nativeQuery = true)
    int getCityCountByUserId(int id);
}
