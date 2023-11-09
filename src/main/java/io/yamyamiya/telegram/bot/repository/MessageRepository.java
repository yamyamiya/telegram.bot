package io.yamyamiya.telegram.bot.repository;

import io.yamyamiya.telegram.bot.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query(value = "SELECT count(*) FROM message WHERE chat_id= :id", nativeQuery = true)
    int getCountByChatId(long id);

    @Query(value = "SELECT * FROM message WHERE chat_id= :id", nativeQuery = true)
    List<Message> getAllByChatId(long id);

    @Query(value = "SELECT count(*) FROM message WHERE created_at> :date", nativeQuery = true)
    int getCountByDateAfter(Date date);

    @Query(value = "SELECT count(*) FROM message WHERE created_at< :date", nativeQuery = true)
    int getCountByDateBefore(Date date);

//    @Query(value = "SELECT count(*) FROM message WHERE created_at= :date", nativeQuery = true)
//    int getCountByDate(Date date);

    @Query(value = "SELECT count(*) FROM message WHERE created_at between :date1 and :date2", nativeQuery = true)
    int getCountByDateBetween(Date date1, Date date2);
}
