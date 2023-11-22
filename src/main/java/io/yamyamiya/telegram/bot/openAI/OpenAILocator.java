package io.yamyamiya.telegram.bot.openAI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import io.yamyamiya.telegram.bot.dto.Location;
import io.yamyamiya.telegram.bot.service.location.Locator;
import io.yamyamiya.telegram.bot.utils.Result;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Class OpenAILocator using OpenAiService and objectMapper parameters,
 * implements methods of interface {@link Locator},
 * provides information about location from received data using OpenAI
 */
@Slf4j
public class OpenAILocator implements Locator {

    private final OpenAiService service;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OpenAILocator(OpenAiService service) {
        this.service = service;
    }

    /**
     * identifies location from provided string by using OpenAI API
     * @param message the data containing city mentioning
     * @return {@link Result<Location>}(Success or Failure)
     */
    @Override
    public Result<Location> locate(String message) {
        ChatCompletionRequest completionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(List.of(
                        new ChatMessage(ChatMessageRole.ASSISTANT.value(), "Act like a REST API endpoint. When you receive user input try to identify city mentioned in the message and respond in JSON format with city name, latitude and longitude."),
                        new ChatMessage(ChatMessageRole.USER.value(), message))
                )
                .build();
        try {
            List<ChatCompletionChoice> result = service.createChatCompletion(completionRequest).getChoices();


            if (!result.isEmpty()) {
                String locationJSON = result.get(0).getMessage().getContent();
                try {
                    Location location = objectMapper.readValue(locationJSON, Location.class);
                    return new Result.Success<>(location);
                } catch (JsonProcessingException e) {
                    log.info(String.format("Could not parse JSON: %s", locationJSON));
                    return new Result.Failure<>(e);
                }
            }
        } catch (RuntimeException e) {
            log.info("OpenAI error.", e);
            return new Result.Failure<>(e);

        }
        return new Result.Failure<>();

    }

}
