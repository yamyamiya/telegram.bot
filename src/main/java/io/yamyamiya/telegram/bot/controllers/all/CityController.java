package io.yamyamiya.telegram.bot.controllers.all;

import io.yamyamiya.telegram.bot.entity.City;
import io.yamyamiya.telegram.bot.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
