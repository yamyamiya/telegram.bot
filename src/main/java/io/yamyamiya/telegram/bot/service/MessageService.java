package io.yamyamiya.telegram.bot.service;


import io.yamyamiya.telegram.bot.entity.Message;

import java.util.Date;
import java.util.List;

/**
 * interface for implementation of service methods working with {@link io.yamyamiya.telegram.bot.repository.MessageRepository}
 */
public interface MessageService {
    /**
     * method for receiving all messages provided by MessageRepo
     * @return {@link List<Message>}
     */
    List<Message> getAll();

    /**
     * method for receiving certain message by its id
     * @param id of the message
     * @return message
     */
    Message getById(int id);

    /**
     * method for addition of message
     * @param message - given message
     */
    void add(Message message);

    /**
     * method for deletion of message by id
     * @param id of message
     */
    void deleteById(int id);

    /**
     * method for receiving all messages of one particular user
     * @param id chatId
     * @return list of messages
     */
    List<Message> getAllByChatId(long id);


    /**
     * method for counting the total number of messages in messageRepo
     * @return number of messages
     */
    int getCount();
    /**
     * method for counting the number of messages, having the same chatId in their DB
     * provides the useful statistic of getting the most active users
     * @param id - chatId
     * @return number of messages
     */
    int getCountByChatId(long id);

    /**
     * method for receiving the number of messages sent after certain date
     * provides the useful statistic about users activity
     * @param date required day
     * @return number of messages
     */
    int getCountByDateAfter(Date date);

    /**
     * method for receiving the number of messages sent before certain date
     * provides the useful statistic about users activity
     * @param date required day
     * @return number of messages
     */
    int getCountByDateBefore(Date date);

    /**
     * method for receiving the number of messages sent in some period ot time
     * provides the useful statistic about users activity
     * @param date1 start day
     * @param date2 end day
     * @return number of messages
     */
    int getCountByDateBetween(Date date1, Date date2);
}
