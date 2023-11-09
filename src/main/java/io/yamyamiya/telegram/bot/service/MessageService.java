package io.yamyamiya.telegram.bot.service;


import io.yamyamiya.telegram.bot.entity.Message;

import java.util.Date;
import java.util.List;

public interface MessageService {
    List<Message> getAll();

    Message getById(int id);

    void add(Message message);

    void deleteById(int id);

    List<Message> getAllByChatId(long id);

    int getCount();

    int getCountByChatId(long id);

    int getCountByDateAfter(Date date);

    int getCountByDateBefore(Date date);

//    int getCountByDate(Date date);

    int getCountByDateBetween(Date date1, Date date2);
}
