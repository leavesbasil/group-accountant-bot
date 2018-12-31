package com.leavesbasil.bot.accountant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class GroupAccountantBot extends TelegramLongPollingBot {

    @Value("${spring.telegram.bot.name}")
    private String name;

    @Value("${spring.telegram.bot.token}")
    private String token;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String firstName = update.getMessage().getFrom().getFirstName();
            String text = update.getMessage().getText();
            String answer = String.format("Hello %s! I received your message:%s, but I don't work yet!", firstName, text);
            SendMessage message = new SendMessage()
                    .setChatId(update.getMessage().getChatId())
                    .setText(answer);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
