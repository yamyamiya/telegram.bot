package io.yamyamiya.telegram.bot.controllers;

import io.yamyamiya.telegram.bot.dto.Location;
import io.yamyamiya.telegram.bot.entity.City;
import io.yamyamiya.telegram.bot.exception.exceptions.EntityValidationException;
import io.yamyamiya.telegram.bot.service.CityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/city")
public class CityController {
    @Autowired
    private CityService cityService;

    @GetMapping
    public List<City> getAll(){
        return cityService.getAll();
    }
    @GetMapping("/id/{id}")
    public City getById(@PathVariable int id){
        return cityService.getById(id);
    }

    @PostMapping
    public Location add(@Valid @RequestBody Location location){
        try {
            cityService.add(location);
            return location;
        } catch (Exception e) {
            throw new EntityValidationException(e.getMessage());
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

    @GetMapping("/usercount/{id}")
    public int getUserCountById(@PathVariable int id){
        return cityService.getUserCountById(id);
    }


}
