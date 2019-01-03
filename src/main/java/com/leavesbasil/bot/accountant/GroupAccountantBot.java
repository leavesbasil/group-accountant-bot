package com.leavesbasil.bot.accountant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.logging.BotLogger;

import java.util.List;

@Component
public class GroupAccountantBot extends TelegramLongPollingCommandBot {

    private final String token;

    public GroupAccountantBot(@Value("${spring.telegram.bot.name}") String name,
                              @Value("${spring.telegram.bot.token}") String token,
                              @Autowired List<IBotCommand> commands) {
        super(name);
        this.token = token;
        commands.forEach(this::register);
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        Long chatId = update.getMessage().getChatId();
        String firstName = update.getMessage().getFrom().getFirstName();
        String text = update.getMessage().getText();
        String answer = String.format("Hello %s! I received your message:%s, but I work only by commands!", firstName, text);

        SendMessage message = new SendMessage()
                .setChatId(chatId)
                .setText(answer);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            BotLogger.error("GROUP_ACCOUNTANT_BOT", e);
        }
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
