package io.yamyamiya.telegram.bot;

import io.yamyamiya.telegram.bot.entity.City;
import io.yamyamiya.telegram.bot.entity.ScheduledForecastTask;
import io.yamyamiya.telegram.bot.entity.User;
import io.yamyamiya.telegram.bot.schedule.ScheduleExecutor;
import io.yamyamiya.telegram.bot.service.CityService;
import io.yamyamiya.telegram.bot.service.MessageService;
import io.yamyamiya.telegram.bot.service.TaskService;
import io.yamyamiya.telegram.bot.service.UserService;
import io.yamyamiya.telegram.bot.service.location.Locator;
import io.yamyamiya.telegram.bot.service.weather.WeatherForecast;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito.*;
import org.mockito.internal.verification.Times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TelegramBotTest {
    private Locator locator;
    private WeatherForecast weatherForecast;
    private UserService userService;
    private CityService cityService;
    private MessageService messageService;
    private TaskService taskService;
    private ScheduleExecutor executor;
    private BCryptPasswordEncoder encoder;

    private Environment env;
    Update telegramUpdate;
    Message telegramMessage;
    org.telegram.telegrambots.meta.api.objects.User telegramUser;

    User user;

    TelegramBot telegramBot;

    @BeforeEach
    void init(){
        locator = mock();
        weatherForecast = mock();
        userService = mock();
        cityService = mock();
        messageService = mock();
        taskService = mock();
        executor = mock();
        encoder = mock();
        env = mock();
        telegramUpdate = mock();
        telegramMessage = mock();
        telegramUser = mock();
        user = mock();
        telegramBot = spy(new TelegramBot(
                locator,
                weatherForecast,
                userService,
                cityService,
                messageService,
                taskService,
                executor,
                encoder,
                env));

        doNothing().when(telegramBot).sendMessage(any());
    }

    @Test
    void shouldProvidePasswordForTheNewUser(){
        when(telegramMessage.getChatId()).thenReturn(1L);
        when(telegramMessage.getFrom()).thenReturn(telegramUser);
        when(telegramUser.getFirstName()).thenReturn("TestFirstName");
        when(telegramMessage.getText()).thenReturn("Weather in Tokyo");
        when(telegramUpdate.getMessage()).thenReturn(telegramMessage);
        when(userService.getByChatId(1L)).thenReturn(null);

        try {
            telegramBot.onUpdateReceived(telegramUpdate);
        } catch (NullPointerException nullPointerException) {
            // Do nothing because user has not been saved and this cause null pointer exception.
            // But we expect that what concerns this test has been already happened.
        }

        ArgumentCaptor<SendMessage> sentMessageArgumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBot).sendMessage(sentMessageArgumentCaptor.capture());
        assertTrue(sentMessageArgumentCaptor.getValue().getText().startsWith("Your password is "));
    }

    @Test
    void shouldHandleSubscribeCommandForNewCity() {
        CallbackQuery query = mock();
        InlineKeyboardMarkup markup = mock();
        InlineKeyboardButton keyboard = mock();
        when(query.getData()).thenReturn("123"); // City id
        when(query.getMessage()).thenReturn(telegramMessage);
        when(markup.getKeyboard()).thenReturn(List.of(List.of(keyboard)));
        when(keyboard.getText()).thenReturn("Subscribe for daily forecast?");
        when(telegramMessage.getChatId()).thenReturn(1L);
        when(telegramMessage.getReplyMarkup()).thenReturn(markup);
        when(telegramUpdate.getCallbackQuery()).thenReturn(query);
        when(telegramUpdate.getMessage()).thenReturn(telegramMessage);
        when(telegramUpdate.hasCallbackQuery()).thenReturn(true);
        when(cityService.getById(123)).thenReturn(new City(123, "Tokyo", 1.1, 1.2));
        when(taskService.getSubscriptionForCity(1L, 123)).thenReturn(0);

        telegramBot.onUpdateReceived(telegramUpdate);

        ArgumentCaptor<ScheduledForecastTask> taskArgumentCaptor = ArgumentCaptor.forClass(ScheduledForecastTask.class);
        verify(taskService).add(taskArgumentCaptor.capture());
        assertEquals(1L, taskArgumentCaptor.getValue().getChatId());
        assertEquals(123, taskArgumentCaptor.getValue().getCityId());

        verify(executor).taskSchedulerTaskWithTrigger(taskArgumentCaptor.getValue(), telegramBot);

        ArgumentCaptor<SendMessage> sentMessageArgumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBot).sendMessage(sentMessageArgumentCaptor.capture());
        assertEquals("You have successfully subscribed. \n", sentMessageArgumentCaptor.getValue().getText());
    }

    @Test
    void shouldHandleSubscribeCommandForExistingCity() {
        CallbackQuery query = mock();
        InlineKeyboardMarkup markup = mock();
        InlineKeyboardButton keyboard = mock();
        when(query.getData()).thenReturn("123"); // City id
        when(query.getMessage()).thenReturn(telegramMessage);
        when(markup.getKeyboard()).thenReturn(List.of(List.of(keyboard)));
        when(keyboard.getText()).thenReturn("Subscribe for daily forecast?");
        when(telegramMessage.getChatId()).thenReturn(1L);
        when(telegramMessage.getReplyMarkup()).thenReturn(markup);
        when(telegramUpdate.getCallbackQuery()).thenReturn(query);
        when(telegramUpdate.getMessage()).thenReturn(telegramMessage);
        when(telegramUpdate.hasCallbackQuery()).thenReturn(true);
        when(cityService.getById(123)).thenReturn(new City(123, "Tokyo", 1.1, 1.2));
        when(taskService.getSubscriptionForCity(1L, 123)).thenReturn(1);

        telegramBot.onUpdateReceived(telegramUpdate);

        verify(taskService, times(0)).add(any());
        verify(executor, times(0)).taskSchedulerTaskWithTrigger(any(), any());

        ArgumentCaptor<SendMessage> sentMessageArgumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBot).sendMessage(sentMessageArgumentCaptor.capture());
        assertEquals("You have already successfully subscribed for Tokyo. \n", sentMessageArgumentCaptor.getValue().getText());
    }

    @Test
    void shouldHandleUnsubscribeCommand() {
        CallbackQuery query = mock();
        InlineKeyboardMarkup markup = mock();
        InlineKeyboardButton keyboard = mock();
        when(query.getData()).thenReturn("123"); // Task id
        when(query.getMessage()).thenReturn(telegramMessage);
        when(markup.getKeyboard()).thenReturn(List.of(List.of(keyboard)));
        when(keyboard.getText()).thenReturn("Unsubscribe?");
        when(telegramMessage.getChatId()).thenReturn(1L);
        when(telegramMessage.getReplyMarkup()).thenReturn(markup);
        when(telegramUpdate.getCallbackQuery()).thenReturn(query);
        when(telegramUpdate.getMessage()).thenReturn(telegramMessage);
        when(telegramUpdate.hasCallbackQuery()).thenReturn(true);

        telegramBot.onUpdateReceived(telegramUpdate);

        verify(taskService).deleteById(123);

        ArgumentCaptor<SendMessage> sentMessageArgumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBot).sendMessage(sentMessageArgumentCaptor.capture());
        assertEquals("You have successfully unsubscribed. \n", sentMessageArgumentCaptor.getValue().getText());
    }

}