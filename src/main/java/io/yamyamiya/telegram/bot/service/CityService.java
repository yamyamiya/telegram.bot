package io.yamyamiya.telegram.bot.service;

import io.yamyamiya.telegram.bot.dto.Location;
import io.yamyamiya.telegram.bot.entity.City;
import io.yamyamiya.telegram.bot.entity.User;

import java.util.Collection;
import java.util.List;

public interface CityService {
    List<City> getAll();

    City getById(int id);

    Collection<City> getAllCitiesForThisUserByChatId(long chatId);

    City add(Location location);

    City add(City city, User user);

    void deleteById(int id);

    void deleteByName(String name);

    int getCount();

    int getUserCountByCityId(int id);

    int getCityCountByUserId(int id);
}
