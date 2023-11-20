package io.yamyamiya.telegram.bot.controllers.admin;

import io.yamyamiya.telegram.bot.entity.User;
import io.yamyamiya.telegram.bot.exception.exceptions.EntityValidationException;
import io.yamyamiya.telegram.bot.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Entry point for admin functionality related to users. Uses methods described in {@link UserService}
 */
@Slf4j
@RestController
@RequestMapping("/admin/user")
public class AdminUserController {
    @Autowired
    private UserService service;

    @GetMapping
    public List<User> getAll() {
        return service.getAll();
    }

    @GetMapping("/id/{id}")
    public User getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PostMapping
    public User add(@Valid @RequestBody User newUser) {
        try {
            service.add(newUser);
            return newUser;
        } catch (Exception e) {
            log.error(String.format("Could not save user %s", newUser.toString()), new EntityValidationException(e.getMessage()));
            return null;
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable int id) {
        service.deleteById(id);
    }

    @DeleteMapping("/deletename/{name}")
    public void deleteByName(@PathVariable String name){
        service.deleteByName(name);
    }

    @GetMapping("/count")
    public int getCount() {
        return service.getCount();
    }



}
