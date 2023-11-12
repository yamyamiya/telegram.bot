package io.yamyamiya.telegram.bot.service;

import io.yamyamiya.telegram.bot.dto.Location;
import io.yamyamiya.telegram.bot.entity.City;
import io.yamyamiya.telegram.bot.entity.User;
import io.yamyamiya.telegram.bot.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class JpaCityService implements CityService {
    @Autowired
    private CityRepository cityRepository;

    @Override
    public List<City> getAll() {
        return new ArrayList<>(cityRepository.findAll());
    }

    @Override
    public City getById(int id) {
        Optional<City> city = cityRepository.findById(id);
        return city.orElse(null);
    }

    @Override
    public City add(Location location, User user) {
        City foundCity = cityRepository.findByName(location.getCity());
        if (foundCity != null){
            if(foundCity.getUsersForCities().contains(user)){
                return foundCity;
            }
            else{
               foundCity.getUsersForCities().add(user);
               return cityRepository.save(foundCity);
            }
        }

        City newCity = new City(0, location.getCity(), location.getLatitude(), location.getLongitude());
            newCity.getUsersForCities().add(user);
        return cityRepository.save(newCity);
    }

    @Override
    public void add(Location location) {
        City foundCity = cityRepository.findByName(location.getCity());
        if ( foundCity != null) {
            return;
        }

        City newCity = new City(0, location.getCity(), location.getLatitude(), location.getLongitude());
        cityRepository.save(newCity);
    }

    @Override
    public void deleteById(int id) {
        cityRepository.deleteById(id);
    }

    @Override
    public void deleteByName(String name) {
        cityRepository.deleteByName(name);
    }

    @Override
    public int getCount() {
        return (int) cityRepository.count();
    }

    @Override
    public int getUserCountByCityId(int id) {
        return cityRepository.getUserCountByCityId(id);
    }

    @Override
    public int getCityCountByUserId(int id) {
        return cityRepository.getCityCountByUserId(id);
    }
}
