package io.yamyamiya.telegram.bot.repository;

import io.yamyamiya.telegram.bot.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * UserRepository interface extends {@link JpaRepository<User, Integer>}.
 * contains objects of {@link User class}
 * linked with table "user" in DB
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * method for getting the user from UserRepository by the name
     * @param name of the user
     * @return User found by the name
     */
    User findByName(String name);

    /**
     * method for getting the user from UserRepository by his chatId
     * @param chatId - user's chatId provided by TelegramBot
     * @return User found by chatId
     */
    User findByChatId(long chatId);

    /**
     * method for deletion of the user from UserRepository by the name
     * @param name of user
     */
    @Transactional
    void deleteByName(String name);


}
