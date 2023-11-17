package io.yamyamiya.telegram.bot.openAI;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import io.yamyamiya.telegram.bot.dto.Location;
import io.yamyamiya.telegram.bot.utils.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class OpenAILocatorTest {

    private OpenAiService service;
    private OpenAILocator openAILocator;


    @BeforeEach
    void init(){
        service = Mockito.mock(OpenAiService.class);
        openAILocator = new OpenAILocator(service);
    }

    @Test
    void shouldReturnCorrectLocation(){
        ChatCompletionResult chatCompletionMock = Mockito.mock(ChatCompletionResult.class);
        when(service.createChatCompletion(any())).thenReturn(chatCompletionMock);
        ChatCompletionChoice chatCompletionChoice = new ChatCompletionChoice();
        chatCompletionChoice.setMessage(new ChatMessage("human", "{ \"latitude\":45.464, \"longitude\":9.19, \"city\":\"Milan\"}"));
        List<ChatCompletionChoice> list = List.of(chatCompletionChoice);
        when(chatCompletionMock.getChoices()).thenReturn(list);
        Result<Location> result =  openAILocator.locate("Weather in Milan.");
        assertTrue(result instanceof Result.Success<Location>);
        assertEquals("Milan",((Result.Success<Location>) result).getValue().getCity());
    }

    @Test
    void shouldReturnInvalidJson(){
        ChatCompletionResult chatCompletionMock = Mockito.mock(ChatCompletionResult.class);
        when(service.createChatCompletion(any())).thenReturn(chatCompletionMock);
        ChatCompletionChoice chatCompletionChoice = new ChatCompletionChoice();
        chatCompletionChoice.setMessage(new ChatMessage("human", "{\"something wrong\"}"));
        List<ChatCompletionChoice> list = List.of(chatCompletionChoice);
        when(chatCompletionMock.getChoices()).thenReturn(list);
        Result<Location> result =  openAILocator.locate("Weather in Milan.");
        assertTrue(result instanceof Result.Failure<Location>);
    }

    @Test
    void shouldHandleUnexpectedException(){
        when(service.createChatCompletion(any())).thenThrow(new RuntimeException());
        Result<Location> result =  openAILocator.locate("Weather in Milan.");
        assertTrue(result instanceof Result.Failure<Location>);
    }

}