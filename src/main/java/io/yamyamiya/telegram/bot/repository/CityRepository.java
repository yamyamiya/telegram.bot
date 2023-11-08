package io.yamyamiya.telegram.bot.repository;

import io.yamyamiya.telegram.bot.entity.City;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CityRepository extends JpaRepository<City, Integer> {
    City findByName(String name);

    @Transactional
    void deleteByName(String name);

    @Query(value = "SELECT count(*) FROM user_city WHERE city_id= :id", nativeQuery = true)
    int getUserCountByCityId(int id);

    @Query(value = "SELECT count(*) FROM user_city WHERE user_id= :id", nativeQuery = true)
    int getCityCountByUserId(int id);
}
