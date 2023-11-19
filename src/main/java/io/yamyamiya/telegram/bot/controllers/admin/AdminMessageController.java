package io.yamyamiya.telegram.bot.controllers.admin;

import io.yamyamiya.telegram.bot.entity.Message;
import io.yamyamiya.telegram.bot.exception.exceptions.EntityValidationException;
import io.yamyamiya.telegram.bot.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin/message")
public class AdminMessageController {
    @Autowired
    private MessageService messageService;

    /**
     * admin gets all messages
     * @return
     */
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
            throw new EntityValidationException(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable int id) {
        messageService.deleteById(id);
    }

    @GetMapping("/chatId/{id}")
    public List<Message> getAllByChatId(@PathVariable long id) {
        return messageService.getAllByChatId(id);
    }

    @GetMapping("/count")
    public int getCount() {
        return messageService.getCount();
    }

    @GetMapping("/count/chatId/{id}")
    public int getCountByChatId(@PathVariable long id) {
        return messageService.getCountByChatId(id);
    }

    @GetMapping("/count/after/{date}")
    public int getCountByDateAfter(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return messageService.getCountByDateAfter(date);
    }

    @GetMapping("/count/before/{date}")
    public int getCountByDateBefore(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return messageService.getCountByDateBefore(date);
    }

    @GetMapping("/count/between/{date1}/{date2}")
    public int getCountByDateBetween(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date1, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date2) {
        return messageService.getCountByDateBetween(date1, date2);
    }
}
