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
/**
 * Entry point for user functionality related to city. Uses methods described in {@link CityService}
 */
@RestController
@RequestMapping("/user/city")
public class UserCityController {

    @Autowired
    private CityService cityService;

    /**
     * method provides the list of cities for current user, for that we need a user
     * that is currently authenticated.
     * @param authentication the object containing details for this specific session, provided by SpringBoot
     * @return list of cities
     */
    @GetMapping("/all")
    public Collection<City> getAllCitiesForThisUserByChatId(Authentication authentication){
        long chatId = ((User) authentication.getPrincipal()).getChatId();
        return cityService.getAllCitiesForThisUserByChatId(chatId);
    }
}
