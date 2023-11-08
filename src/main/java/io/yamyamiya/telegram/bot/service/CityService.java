package io.yamyamiya.telegram.bot.service;

import io.yamyamiya.telegram.bot.dto.Location;
import io.yamyamiya.telegram.bot.entity.City;

import java.util.List;

public interface CityService {
    List<City> getAll();

    City getById(int id);

    void add(Location location);

    void deleteById(int id);

    void deleteByName(String name);

    int getCount();

    int getUserCountById(int id);
}
