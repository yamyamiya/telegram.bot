package io.yamyamiya.telegram.bot.controllers.admin;

import io.yamyamiya.telegram.bot.dto.Location;
import io.yamyamiya.telegram.bot.entity.City;
import io.yamyamiya.telegram.bot.exception.exceptions.EntityValidationException;
import io.yamyamiya.telegram.bot.service.CityService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Entry point for admin functionality related to city. Uses methods described in {@link CityService}
 */
@Slf4j
@RestController
@RequestMapping("/admin/city")
public class AdminCityController {
    @Autowired
    private CityService cityService;

    @GetMapping("/id/{id}")
    public City getById(@PathVariable int id){
        return cityService.getById(id);
    }

    @PostMapping
    public boolean add(@Valid @RequestBody Location location){
        try {
            cityService.add(location);
            return true;
        } catch (Exception e) {
            log.error(String.format("Could not save city with location %s", location), new EntityValidationException(e.getMessage()));
            return false;
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable int id){
        cityService.deleteById(id);
    }


    @DeleteMapping("/deletename/{name}")
    public void deleteByName(@PathVariable String name){
        cityService.deleteByName(name);
    }


    @GetMapping("/count")
    public int getCount(){
        return cityService.getCount();
    }

    /**
     * method for counting the number of users, having the same cityId in their DB
     * provides the useful statistic of getting the most popular city
     * @param id of the city
     * @return number of users
     */

    @GetMapping("/usercount/{id}")
    public int getUserCountByCityId(@PathVariable int id){
        return cityService.getUserCountByCityId(id);
    }

    /**
     * method for counting the number of cities for one specific user
     * provides the useful statistic of frequency of requests with different cities for each user
     * @param id of the user
     * @return number of cities
     */
    @GetMapping("/citycount/{id}")
    public int getCityCountByUserId(@PathVariable int id){
        return cityService.getCityCountByUserId(id);
    }

}
