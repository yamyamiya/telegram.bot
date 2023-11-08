package io.yamyamiya.telegram.bot.repository;

import io.yamyamiya.telegram.bot.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByName(String name);

    User findByChatId(long chatId);

    @Transactional
    void deleteByName(String name);


}
