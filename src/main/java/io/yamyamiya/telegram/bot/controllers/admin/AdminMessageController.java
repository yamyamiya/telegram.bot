package io.yamyamiya.telegram.bot.controllers.admin;

import io.yamyamiya.telegram.bot.entity.Message;
import io.yamyamiya.telegram.bot.exception.exceptions.EntityValidationException;
import io.yamyamiya.telegram.bot.service.MessageService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
/**
 * Entry point for admin functionality related to messages. Uses methods described in {@link MessageService}
 */
@Slf4j
@RestController
@RequestMapping("/admin/message")
public class AdminMessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping
    public List<Message> getAll() {
        return messageService.getAll();
    }

    @GetMapping("/id/{id}")
    public Message getById(@PathVariable int id) {
        return messageService.getById(id);
    }

    @PostMapping
    public Message add(@Valid @RequestBody Message message) {
        try {
            messageService.add(message);
            return message;
        } catch (Exception e) {
            log.error(String.format("Could not save message with content %s", message.getContent()), new EntityValidationException(e.getMessage()));
            return null;
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable int id) {
        messageService.deleteById(id);
    }

    /**
     * method for receiving all messages of one particular user
     * @param id chatId
     * @return list of messages
     */
    @GetMapping("/chatId/{id}")
    public List<Message> getAllByChatId(@PathVariable long id) {
        return messageService.getAllByChatId(id);
    }

    @GetMapping("/count")
    public int getCount() {
        return messageService.getCount();
    }

    /**
     * method for counting the number of messages, having the same chatId in their DB
     * provides the useful statistic of getting the most active users
     * @param id - chatId
     * @return number of messages
     */
    @GetMapping("/count/chatId/{id}")
    public int getCountByChatId(@PathVariable long id) {
        return messageService.getCountByChatId(id);
    }

    /**
     * method for receiving the number of messages sent after certain date
     * provides the useful statistic about users activity
     * @param date required day
     * @return number of messages
     */
    @GetMapping("/count/after/{date}")
    public int getCountByDateAfter(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return messageService.getCountByDateAfter(date);
    }

    /**
     * method for receiving the number of messages sent before certain date
     * provides the useful statistic about users activity
     * @param date required day
     * @return number of messages
     */
    @GetMapping("/count/before/{date}")
    public int getCountByDateBefore(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return messageService.getCountByDateBefore(date);
    }

    /**
     * method for receiving the number of messages sent in some period ot time
     * provides the useful statistic about users activity
     * @param date1 start day
     * @param date2 end day
     * @return number of messages
     */
    @GetMapping("/count/between/{date1}/{date2}")
    public int getCountByDateBetween(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date1, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date2) {
        return messageService.getCountByDateBetween(date1, date2);
    }
}
