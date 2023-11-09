package io.yamyamiya.telegram.bot.service;

import io.yamyamiya.telegram.bot.entity.Message;
import io.yamyamiya.telegram.bot.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class JpaMessageService implements MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public List<Message> getAll() {
        return messageRepository.findAll();
    }

    @Override
    public Message getById(int id) {

        Optional<Message> message = messageRepository.findById(id);
        return message.orElse(null);
    }

    @Override
    public void add(Message message) {
        messageRepository.save(message);
    }

    @Override
    public void deleteById(int id) {
        messageRepository.deleteById(id);
    }

    @Override
    public List<Message> getAllByChatId(long id) {
        return messageRepository.getAllByChatId(id);
    }

    @Override
    public int getCount() {
        return (int) messageRepository.count();
    }

    @Override
    public int getCountByChatId(long id) {
        return messageRepository.getCountByChatId(id);
    }

    @Override
    public int getCountByDateAfter(Date date) {
        return messageRepository.getCountByDateAfter(date);
    }

    @Override
    public int getCountByDateBefore(Date date) {
        return messageRepository.getCountByDateBefore(date);
    }

//    @Override
//    public int getCountByDate(Date date) {
//        return messageRepository.getCountByDate(date);
//    }

    @Override
    public int getCountByDateBetween(Date date1, Date date2) {
        return messageRepository.getCountByDateBetween(date1, date2);
    }
}
