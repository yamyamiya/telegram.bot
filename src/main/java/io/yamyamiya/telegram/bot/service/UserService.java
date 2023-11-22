package io.yamyamiya.telegram.bot.service;

import io.yamyamiya.telegram.bot.entity.User;

import java.util.List;

/**
 * interface for implementation of service methods working with {@link io.yamyamiya.telegram.bot.repository.UserRepository}
 */
public interface UserService {
    /**
     * method for receiving all users provided by UserRepo
     * @return {@link List<User>}
     */
    List<User> getAll();

    /**
     * method for receiving certain user by its id
     * @param id of the user
     * @return user
     */
    User getById(int id);

    /**
     * method for user addition
     * @param user to add
     * @return added user
     */
    User add(User user);

    /**
     * method for deletion of user by its id
     * @param id of the user
     */
    void deleteById(int id);

    /**
     * method for deletion of user by its name
     * @param name of the user
     */
    void deleteByName(String name);

    /**
     * method for counting the total number of users in userRepo
     * @return number of users
     */
    int getCount();

    /**
     * method for receiving certain user by his chatId
     * @param chatId of user
     * @return found user
     */
    User getByChatId(long chatId);
}
