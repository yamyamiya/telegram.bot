package io.yamyamiya.telegram.bot.service;

import io.yamyamiya.telegram.bot.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User getById(int id);

    User add(User user);

    void deleteById(int id);

    void deleteByName(String name);

    int getCount();

}
