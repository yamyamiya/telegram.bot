package io.yamyamiya.telegram.bot.repository;

import io.yamyamiya.telegram.bot.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
/**
 * MessageRepository interface extends {@link JpaRepository<Message, Integer>}.
 * contains objects of {@link Message class}
 * linked with table "message" in DB
 */
public interface MessageRepository extends JpaRepository<Message, Integer> {
    /**
     * method for counting the number of messages, having the same chatId in their DB
     * provides the useful statistic of getting the most active users
     * @param id - chatId
     * @return number of messages
     */
    @Query(value = "SELECT count(*) FROM message WHERE chat_id= :id", nativeQuery = true)
    int getCountByChatId(long id);

    /**
     * method for receiving all messages of one particular user
     * @param id chatId
     * @return list of messages
     */
    @Query(value = "SELECT * FROM message WHERE chat_id= :id", nativeQuery = true)
    List<Message> getAllByChatId(long id);

    /**
     * method for receiving the number of messages sent after certain date
     * provides the useful statistic about users activity
     * @param date required day
     * @return number of messages
     */
    @Query(value = "SELECT count(*) FROM message WHERE created_at> :date", nativeQuery = true)
    int getCountByDateAfter(Date date);

    /**
     * method for receiving the number of messages sent before certain date
     * provides the useful statistic about users activity
     * @param date required day
     * @return number of messages
     */
    @Query(value = "SELECT count(*) FROM message WHERE created_at< :date", nativeQuery = true)
    int getCountByDateBefore(Date date);

    /**
     * method for receiving the number of messages sent in some period ot time
     * provides the useful statistic about users activity
     * @param date1 start day
     * @param date2 end day
     * @return number of messages
     */
    @Query(value = "SELECT count(*) FROM message WHERE created_at between :date1 and :date2", nativeQuery = true)
    int getCountByDateBetween(Date date1, Date date2);
}
