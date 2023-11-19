package io.yamyamiya.telegram.bot.controllers.user;

import io.yamyamiya.telegram.bot.entity.City;
import io.yamyamiya.telegram.bot.entity.User;
import io.yamyamiya.telegram.bot.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/user/city")
public class UserCityController {

    @Autowired
    private CityService cityService;

    @GetMapping("/all")
    public Collection<City> getAllCitiesForThisUserByChatId(Authentication authentication){
        long chatId = ((User) authentication.getPrincipal()).getChatId();
        return cityService.getAllCitiesForThisUserByChatId(chatId);
    }
}
