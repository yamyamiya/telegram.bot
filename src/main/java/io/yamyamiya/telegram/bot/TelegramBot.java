package io.yamyamiya.telegram.bot;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TelegramBot extends TelegramLongPollingBot {
   private final Environment env;

    public TelegramBot(Environment env) {
        super(env.getProperty("telegram.token"));
        this.env = env;
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println();
        String message = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        OpenAiService service = new OpenAiService(env.getProperty("openai.token"));
        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(List.of(new ChatMessage(ChatMessageRole.USER.value(), message)))
                .build();
        List<ChatCompletionChoice> result = service.createChatCompletion(completionRequest).getChoices();


        SendMessage sentMessage = SendMessage.builder()
                .chatId(chatId)
                .text(result.stream().map(x -> x.getMessage().getContent()).collect(Collectors.joining(", ")))
                .build();

        try {
            sendApiMethod(sentMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    @Override
    public String getBotUsername() {
        return env.getProperty("telegram.name");
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }
}
